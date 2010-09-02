package br.univali.celine.scorm.dataModel.adl.nav;

import java.util.Arrays;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequestType;

/**
 * SN Book. Section 5.6.6. Request
 * 
 * @author Adilson Vahldick
 *
 */

public class Request implements DataModelCommand {

	public static final String name = "adl.nav.request";
	
	private static NavigationRequestType getNavigationRequestType(ActivityTree tree) {
		String navReq = tree.getDataModel(Request.name);
		if (navReq == null)
			return null;
		
		if (navReq.indexOf("{") == 0) { // choice
			navReq = navReq.substring(navReq.indexOf("}")+1);
		}
		navReq = navReq.replaceFirst("["+navReq+"]", navReq.substring(0, 1).toUpperCase());
		
		return NavigationRequestType.valueOf(navReq);
	}
	
	public static boolean isRequest(ActivityTree tree, NavigationRequestType navigationRequestType) {
		
		return getNavigationRequestType(tree) == navigationRequestType;
	}
	
	public static boolean isTerminationRequest(ActivityTree tree) {
		
		NavigationRequestType navReq = getNavigationRequestType(tree);
		
		return (navReq != null && navReq.isTerminationRequest());
	}
	
	public String getValue(String key, ErrorManager errorManager) {
		return errorManager.getTree().getDataModel(key);
	}
	
	private static String validos[] = {"abandon", "abandonAll", "choice", "continue", "exit", "exitAll", "previous", 
									   "suspendAll", "_none_"};

	public boolean setValue(String key, String newValue, ErrorManager errorManager) throws Exception {
		
		if (newValue.trim().length() == 0)
			newValue = validos[7]; // default
		
		String requestCommand = newValue;
		if (newValue.indexOf("{") == 0) { // choice
			requestCommand = newValue.substring(newValue.indexOf("}")+1);
		}
		
		if (Arrays.binarySearch(validos, requestCommand) == -1) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
		
		/*
		 * Segundo SN 5.6.6. Request, a requisicao somente é processada após invocar o método Terminate()
		 */
		
		ActivityTree tree = errorManager.getTree();
		tree.putDataModel(Request.name, newValue);
		
		return true;
	}

	public void clear(ErrorManager errorManager) throws Exception {
		errorManager.getTree().removeDataModel(Request.name);
	}

	public void initialize(ErrorManager errorManager) {
	}

}
