/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.celine.scorm.rteApi;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.dataModel.DataModelCommandManager;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.dataModel.cmi.Exit;
import br.univali.celine.scorm.dataModel.cmi.SessionTime;
import br.univali.celine.scorm.dataModel.cmi.SuspendData;
import br.univali.celine.scorm.dataModel.cmi.TotalTime;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Resource;
import br.univali.celine.scorm.model.dataTypes.Duration;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.ActivityTreeFactory;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.NavigationRequestType;
import br.univali.celine.scorm.sn.model.NodeTableOfContent;
import br.univali.celine.scorm.sn.model.TableOfContentsFactory;
import br.univali.celine.scorm.sn.model.TrackModelManager;
import br.univali.celine.scorm.sn.op.OverallSequencingProcess;

public class APIImplementation implements API {

	private Logger logger = Logger.getLogger("global");
	
	private enum CommunicationSessionStateModel {
		notInitialized, running //, terminated -- eliminei esse estado. Depois que executa o terminated a API fica como notInitialized.
								//               justificaria manter esse estado se houvesse um objeto APIImplementation por SCO.
								//				 Nessa implementacao, é um objeto para a sessao do usuario.
	}
	
	private ActivityTree tree;
	private CommunicationSessionStateModel stateModel = CommunicationSessionStateModel.notInitialized;
	private ErrorManager errorManager;
	private ContentPackage contentPackage;
	private String courseFolder;
	private boolean attrHasNavRequest = false;
	private boolean needsReloadTOC = false;
	private User user;
	private DAO dao;
	private Set<ListenerRequest> listeners = new HashSet<ListenerRequest>();
	private int version;
	
	public ActivityTree getActivityTree() {
		return tree;
	}
	
	public APIImplementation(String courseFolder, ContentPackage contentPackage, User user, DAO dao, 
							 ListenerDataModel listenerDataModel, ListenerRequest listenerRequest) throws Exception {

		this.version = contentPackage.getContentPackageReader().buildVersion().getVersion();
		
		this.courseFolder = courseFolder;
		
		this.contentPackage = contentPackage;
		this.tree = ActivityTreeFactory.build(contentPackage);
		this.user = user;
		this.dao = dao;
		
		DataModelCommandManager.getGlobalInstance().registerListener(listenerDataModel);
		registerListener(listenerRequest);
		
		//NavigationRequestType nrt = NavigationRequestType.Choice; 
		// os testes de compatibilidade me fizeram trocar p/ choice :( NavigationRequestType.Start;
		
		NavigationRequest nr = null;
		TrackModelManager tm = new TrackModelManager(tree, user.getName(), courseFolder);
		String[] suspendedAct = tm.load(dao);
		if (suspendedAct[0] != null) {
			
			tree.setSuspendActivity(tree.getLearningActivity(suspendedAct[0]));
			tree.putDataModel(SuspendData.name, suspendedAct[1]);
			
			nr = new NavigationRequest(NavigationRequestType.ResumeAll);
			if (tree.getSuspendActivity() != null) {}
				
			// se suspendedact == null é porque a atividade deixou de existir na árvore :(
		} else {
			if (contentPackage.getOrganizations().getDefaultOrganization().getImsssSequencing().getControlMode().isFlow()) {
				nr = new NavigationRequest(NavigationRequestType.Start);
			} else {
				String identif = contentPackage.getOrganizations().getDefaultOrganization().getChildren().next().getIdentifier();
				nr = new NavigationRequest(tree.getLearningActivity(identif));
			}
		}
		
		ProcessProvider.getInstance().getOverallSequencingProcess().run(nr, tree);
		this.errorManager = new ErrorManager(tree, user, courseFolder, version);
		
		if (suspendedAct[0] != null)
			DataModelCommandManager.getGlobalInstance().setValue(SuspendData.name, suspendedAct[1], errorManager);
		
	}
	
	public String getCourseFolder() {
		return this.courseFolder;
	}
	
	public String getResource() {
		
		Resource res = this.contentPackage.getResource(((Item)tree.getCurrentActivity().getItem()).getIdentifierref());
		return res.getXmlBase() +  res.getHref() + ((Item)tree.getCurrentActivity().getItem()).getParameters();
	}
	
	
	public boolean initialize(String parameter) {
		logger.info("Initialize " + stateModel);
		
		if (stateModel == CommunicationSessionStateModel.running) {
			if (version == 12)
				errorManager.attribError(ErrorManager.GeneralException);
			else
				errorManager.attribError(ErrorManager.AlreadyInitialized);
			return false;
		}
		
		if (parameter.length() > 0) {
			errorManager.attribError(ErrorManager.GeneralArgumentError);
			return false;
		}
		
		stateModel = CommunicationSessionStateModel.running;
		errorManager.success();
		
		try {
			DataModelCommandManager.getGlobalInstance().initialize(errorManager);
		} catch (Exception e) {
			e.printStackTrace();
			errorManager.attribError(ErrorManager.GeneralInitializationFailure);
			return false;
		}
		
		return true;
	}

	
	public boolean commit(String parameter) {
		
		logger.info("Commit " + stateModel);
		
		if (stateModel != CommunicationSessionStateModel.running) {
			errorManager.attribError(ErrorManager.CommitBeforeInitialization);
			return false;
		}
	
		if (parameter.length() > 0) {
			errorManager.attribError(ErrorManager.GeneralArgumentError);
			return false;
		}
		
		//if (Request.isTerminationRequest(tree)) {
			try {
				TrackModelManager tm = new TrackModelManager(errorManager.getTree(), user.getName(), courseFolder);
				tm.save(dao);
				errorManager.success();
				
				errorManager.getTree().clearInteractions(); // depois que salvou, pode eliminar os interactions
			} catch (Exception e) {
				e.printStackTrace();
				errorManager.attribError(ErrorManager.GeneralCommitFailure);
				return false;
			}
		//}

		return true;

	}

	
	public boolean terminate(String parameter) {
		
		logger.info("Terminate " + stateModel);
		if (isLastWasSCO() && stateModel == CommunicationSessionStateModel.notInitialized) {
			errorManager.attribError(ErrorManager.TerminationBeforeInitialization);
			return false;
		}

		if (parameter.length() > 0) {
			errorManager.attribError(ErrorManager.GeneralArgumentError);
			return false;
		}

		if (!processAdlNavRequest())
			return false;
		
		if (!commit(""))
			return false;
			
		updateTotalTime();
		
		
		logger.info("Request.name : "  + tree.getDataModel(Request.name));
		
		try {
			DataModelCommandManager.getGlobalInstance().clear(this.errorManager);
		} catch (Exception e) {
			 e.printStackTrace();
			 errorManager.attribError(ErrorManager.GeneralTerminationFailure);
			 return false;
		}
		
		stateModel = CommunicationSessionStateModel.notInitialized; // CommunicationSessionStateModel.terminated;
		errorManager.success();
		
		return true;
	}

	@Override
	public boolean processRequest(String page) {
		try {
			DataModelCommandManager.getGlobalInstance().setValue("adl.nav.request", "{target="+page+"}choice", this.errorManager);
		} catch (Exception e) {
			return false;
		}
		return processAdlNavRequest();
	}

	private boolean processAdlNavRequest() {
		// SN Book 5.6.6 
		// Essa secao indica que após terminate é que precisa executar a informacao do adl.nav.request
		
		String dm = tree.getDataModel(Request.name);
		
		if (dm != null) {
			// RTE 4.2.8 Se for um suspendAll entao ignora o cmi.exit
			if (!dm.equalsIgnoreCase(NavigationRequestType.SuspendAll.toString()) && !cmiExitTreatment()) {
				return false;
			}
			attrHasNavRequest =  
				(dm.endsWith(NavigationRequestType.Choice.toString().toLowerCase()) || 
						dm.equalsIgnoreCase(NavigationRequestType.Continue.toString()) || 
						dm.equalsIgnoreCase(NavigationRequestType.Previous.toString()));

			try {
				processInternalRequest();
			} catch (Exception e) {
				 e.printStackTrace();
				 errorManager.attribError(ErrorManager.GeneralTerminationFailure);
				 return false;
			}
		} else {
			attrHasNavRequest = false;
		}
		
		return true;
	}

	public void registerListener(ListenerRequest listener) {
		if (listener != null)
			listeners.add(listener);
	}
	
	public void removeListener(ListenerRequest listener) {
		listeners.remove(listener);
	}
	
	private void processInternalRequest() throws Exception {

		String request = tree.getDataModel(Request.name);
		String ativSelec = "";
		if (request.indexOf("{") == 0) { // choice
			ativSelec = request.substring(1, request.indexOf("}"));
			ativSelec = ativSelec.split("=")[1];
			request = request.substring(request.indexOf("}")+1);
		}
		
		request = request.replaceFirst("["+request+"]", request.substring(0, 1).toUpperCase());

		NavigationRequestType nrt = NavigationRequestType.valueOf(request);
		
		NavigationRequest nr = null;
		if (nrt == NavigationRequestType.Choice) {
			nr = new NavigationRequest(tree.getLearningActivity(ativSelec));  
		} else {
			nr = new NavigationRequest(nrt);
		}
		
		logger.info("adl.nav.request : " + tree.getCurrentActivity());
		LearningActivity act = tree.getCurrentActivity();
		
		if (listeners.size() > 0) {
			
			String actId = null;
			if (act != null)
				actId = act.getItem().getIdentifier();

			notifyListeners(nrt, actId, tree, errorManager.getUser(), errorManager.getCourseFolder());
		}
		OverallSequencingProcess overProcess = ProcessProvider.getInstance().getOverallSequencingProcess();
		
		overProcess.run(nr, tree);

		
	}

	private void notifyListeners(NavigationRequestType type, String nextActivity, ActivityTree tree, User user, String courseName) throws Exception {
		
		for (ListenerRequest listener:listeners) {
			listener.request(type, nextActivity, tree, user, courseName);
		}
		
	}

	private void updateTotalTime() {
		String totalTime = errorManager.getTree().getDataModel(TotalTime.name);
		if (totalTime == null)
			totalTime = "0";
		String sessionTime = errorManager.getTree().getDataModel(SessionTime.name);
		if (sessionTime == null) {
			sessionTime = "PT0S";
		}
		
		long sec = (new Duration(sessionTime)).toSeconds();
		sec += Long.parseLong(totalTime);
		errorManager.getTree().putDataModel(TotalTime.name, String.valueOf(sec));
	}

	private boolean cmiExitTreatment() {
		// precisa dar uma boa revisada nisso !!!
		/*
		// RTE Book 4.2.8
		String dmExit = errorManager.getTree().getDataModel(Exit.name);
		
		logger.info("cmi.exit.treatment : " + dmExit);
		
		if (dmExit == null)
			return true;
		
		if ("suspend".equals(dmExit)) {
			errorManager.getTree().getCurrentActivity().setSuspended(true);
		} else {
			NavigationRequest nr = null;
			if ("logout".equals(dmExit) || "time-out".equals(dmExit)) {
				nr = new NavigationRequest(NavigationRequestType.ExitAll);
			} else {
				nr = new NavigationRequest(NavigationRequestType.Exit);
			}
				
			try {
				ProcessProvider.getInstance().getOverallSequencingProcess().run(
						nr, tree);
			} catch (NavigationRequestException e) {
				e.printStackTrace();
				errorManager.attribError(ErrorManager.GeneralTerminationFailure);
				return false;
			}
			
		}
		*/
		return true;
	}

	
	public String getValue(String dataModelElement) {
		
		
		errorManager.success(); // coloco aqui para zerar o codigo de erro
		logger.info("GetValue : " + dataModelElement + " " + stateModel);
		if (stateModel == CommunicationSessionStateModel.notInitialized) {
			errorManager.attribError(ErrorManager.RetrieveDataBeforeInitialization);
			return "";
		}
		
		if (dataModelElement.length() == 0) {
			errorManager.attribError(ErrorManager.GeneralGetFailure);
			return "";
		}
		
		String value = "";
		try {
			
			value = DataModelCommandManager.getGlobalInstance().getValue(dataModelElement, this.errorManager);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			errorManager.attribError(ErrorManager.GeneralGetFailure);

		}
		logger.info("GetValue : " + dataModelElement + " => " + value);
		return value;
	}

	
	public boolean setValue(String dataModelElement, String value) {
		
		logger.info("SetValue : " + dataModelElement + "=" + value);
		
		errorManager.success(); // coloco aqui para zerar o codigo de erro
		
		if (isLastWasSCO() && stateModel != CommunicationSessionStateModel.running) {
			logger.info("SetValue : " + false);
			errorManager.attribError(ErrorManager.StoreDataBeforInitialization);
			return false;
		}
		
		if (dataModelElement.length() == 0) {
			errorManager.attribError(ErrorManager.GeneralSetFailure);
			return false;
		}

		boolean ret = false;
		try {
			ret = DataModelCommandManager.getGlobalInstance().setValue(dataModelElement, value, this.errorManager);
		} catch (Exception e) {
			e.printStackTrace();
			errorManager.attribError(ErrorManager.GeneralSetFailure);
		}
		
		if (ret && dataModelElement.equals(Exit.name) && !value.equals(Exit.suspend)) { 
			// se for cmi.exit e nao for suspend, entao é o fim de um attempt, e com isso reconstruir a arvore
			needsReloadTOC = true;
			
		}
		
		logger.info("SetValue (ret) : " + ret);
		
		return ret;
	}

	
	public String getDiagnostic(String errorCode) {

		return "";
	}

	
	public String getErrorString(String errorCode) {

		if (errorCode == null || errorCode.equals("")) // can be enter that some errorCode == null 
			return null;       // it must accept, either is not in specification
		
		return errorManager.getErrorString(version, Integer.parseInt(errorCode));
	}

	
	public int getLastError() {

		logger.info("GetLastError : " +  errorManager.getNumError());
		
		return errorManager.getNumError();
	}

	
	public boolean hasNavRequest() {

		boolean b = attrHasNavRequest;
		
		this.attrHasNavRequest = false;
		this.needsReloadTOC = false;

		logger.info("HasNavRequest : " + b);
		
		return b;
	}

	public void removeDataElement(String key) {
		tree.removeDataModel(key);
	}

	
	public NodeTableOfContent getTOC() {
		return TableOfContentsFactory.build(tree).getRoot();
	}

	
	public boolean needsReloadTOC() {
		
		boolean b = needsReloadTOC;
		needsReloadTOC = false;
		
		return attrHasNavRequest || b;
	}

	private boolean isLastWasSCO() {
		
		Resource res = this.contentPackage.getResource(((Item)tree.getCurrentActivity().getItem()).getIdentifierref());
		return res.getScormType().equals("sco");
		
	}

	@Override
	public boolean isTerminated() {
		
		return stateModel == CommunicationSessionStateModel.notInitialized;
	}

}
