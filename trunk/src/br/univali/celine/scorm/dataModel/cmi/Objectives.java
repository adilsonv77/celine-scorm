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
package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.DynamicObjective;
import br.univali.celine.scorm.sn.model.LocalObjective;
import br.univali.celine.scorm.sn.model.Objective;
import br.univali.celine.scorm.sn.model.ProxyObjective;
import br.univali.celine.scorm.sn.model.SuccessStatus;
import br.univali.celine.scorm.sn.model.dataTypes.LongIdentifierType;

/**
 * cmi.objectives: RTE Book 4.2.17

 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>_count</li>
 * <li>n.id</li>
 * <li>n.score
 * 		<ul>
 * 			<li>_children</li>
 * 			<li>scaled</li>
 * 			<li>raw</li>
 * 			<li>min</li>
 * 			<li>max</li>
 * 		</ul>
 * </li>
 * <li>n.success_status</li>
 * <li>n.completion_status</li> 
 * <li>n.progress_measure</li>
 * <li>n.description</li> 
 * </ul>
 * 
 * DM Completed !!!
 * 
 * @author adilsonv
 * 
 */
public class Objectives implements DataModelCommand {

	public class ObjectivesCount implements DotNotationCommand {

		
		public String getValue(ErrorManager errorManager, int n, int size) {
			return String.valueOf(errorManager.getTree().getCurrentActivity().getObjectives().size());
		}

		
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			
			return false;
		}

	}

	public class ObjectivesId implements DotNotationCommand {

		
		public String getValue(ErrorManager errorManager, int n, int size) {
			if (n >= size) {
				errorManager.attribError(ErrorManager.GeneralGetFailure);
				return "";
			}
			
			ProxyObjective objective = errorManager.getTree().getCurrentActivity().getObjectives().get(n);
			
			return objective.getObjectiveID(); 
		}

		
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String objectiveId) {

			if (LongIdentifierType.validate(objectiveId) == false) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			if (n != size) { // nao pode ser nem maior e nem menor

				if (n < size) {
					LocalObjective objective = errorManager.getTree().getCurrentActivity().getObjectives().get(n);
					if (objective.getObjectiveID().equals(objectiveId)) {
						return true;
					}
				}
					
				
				errorManager.attribError(ErrorManager.GeneralSetFailure);
				return false;
			}
			
			LocalObjective objective = errorManager.getTree().getCurrentActivity().getObjective(objectiveId);
			if (objective != null) {
				errorManager.attribError(ErrorManager.GeneralSetFailure);
				return false;
			}

			errorManager.getTree().getCurrentActivity().getObjectives().add(new DynamicObjective(objectiveId));
			
			return true;
		}
		
		
	}
	
	public static final String name = "cmi.objectives";
	private static final String[] success_status_state;
	private static final String[] completion_status_state;

	static {
		
		success_status_state = new String[SuccessStatus.values().length];
		int conta = 0;
		for (SuccessStatus oss:SuccessStatus.values()) {
			success_status_state[conta] = oss.name();
			conta++;
		}
		
		completion_status_state = new String[br.univali.celine.scorm.sn.model.CompletionStatusValue.values().length];
		conta = 0;
		for (br.univali.celine.scorm.sn.model.CompletionStatusValue cs:br.univali.celine.scorm.sn.model.CompletionStatusValue.values()) {
			completion_status_state[conta] = cs.toString();
			conta++;
		}
	
	}
	
	private ObjectivesCount objCount = new ObjectivesCount();
	private DotNotationCommandManager commMan = new DotNotationCommandManager("objectives", objCount);
	private DotNotationCommandManager scoreComms= new DotNotationCommandManager("objectives", objCount);
	
	public Objectives() {
		
		commMan.add("id", new ObjectivesId());
		commMan.add("success_status", new DMObjectiveSuccessStatus());
		commMan.add("completion_status", new DMObjectiveCompletionStatus());
		commMan.add("progress_measure", new DMObjectiveProgressMeasure());
		commMan.add("description", new DMObjectiveDescription());
		
		commMan.add("score", null); // necessario para producao do _children
		
		scoreComms.add("scaled", new ScoreScaled());
		scoreComms.add("raw", new ScoreRaw());
		scoreComms.add("min", new ScoreMin());
		scoreComms.add("max", new ScoreMax());
	}

	public String getValue(String key, ErrorManager errorManager) {

		if (key.contains(".score.")) {
			if (key.endsWith("_children")) {
				return scoreComms.getChildren();
			}
			String[] p = key.split("[.]");
			return scoreComms.getValue(p[0]+"."+p[2], errorManager);
		}
		
		return commMan.getValue(key, errorManager);
	}
	
	public boolean setValue(String key, String novoValor,
			ErrorManager errorManager) {

		if (key.endsWith("_children")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}
		
		if (key.contains(".score.")) {
			String[] p = key.split("[.]");
			return scoreComms.setValue(p[0]+"."+p[2], novoValor, errorManager);
		}
		
		return commMan.setValue(key, novoValor, errorManager);
	}


	
	public void clear(ErrorManager errorManager) throws Exception {

		commMan.clear(errorManager);

	}
	
	public class DMObjectiveSuccessStatus extends DotNotationCommandImpl {

		public DMObjectiveSuccessStatus() {
			super(success_status_state);
		}
		
		
		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			return obj.getSuccessStatus().toString();
		}

		
		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {

			try {
				SuccessStatus.valueOf(novoValor);
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n); 

			
			obj.setSuccessStatus(novoValor);

			return true;
		}

	}

	public class DMObjectiveCompletionStatus extends DotNotationCommandImpl {

		public DMObjectiveCompletionStatus() {
			super(completion_status_state);  
		}
		
		
		protected String doGetValue(ErrorManager errorManager, int n, int size) {
			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			
			return obj.getCompletionStatus().toString();
		}

		
		protected boolean doSetValue(ErrorManager errorManager, int n,
				int size, String novoValor) {
			
			br.univali.celine.scorm.sn.model.CompletionStatusValue value = null;
			try {
				value = br.univali.celine.scorm.sn.model.CompletionStatusValue.valor(novoValor);
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n); 

			obj.setCompletionStatus(value);

			return true;
		}

	}

	private class DMObjectiveDescription extends DotNotationCommandImpl  {
		
		public String doGetValue(ErrorManager errorManager, int n, int size) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			String desc = obj.getDescription();
			if (desc == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			return desc;
		}

		
		public boolean doSetValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			obj.setDescription(novoValor);
			return true;
		}

	}

	private class DMObjectiveProgressMeasure extends DotNotationCommandImpl {

		
		public String doGetValue(ErrorManager errorManager, int n, int size) {
			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			Double measure = obj.getProgressMeasure();

			if (obj.getProgressMeasure() == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(measure);
		}

		
		public boolean doSetValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
		
			double valor = 0;
			try {
				valor = Double.valueOf(novoValor);
				if (valor < 0 || valor > 1) {
					errorManager.attribError(ErrorManager.DataModelElementValueOutOfRange);
					return false;
					
				}
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			obj.setProgressMeasure(valor);
			
			return true;
		}

	}

	// ********************************************************************************
	//                     cmi.objectives.n.score.xxxx
	// ********************************************************************************

	public class ScoreScaled extends DotNotationCommandImpl {
		
		public String doGetValue(ErrorManager errorManager, int n, int size) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			
			if (obj.isNormalizedMeasureInitialized() == false) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			return String.valueOf(obj.getNormalizedMeasure());
		}

		
		public boolean doSetValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			
			double valor = 0;
			try {
				valor = Double.valueOf(novoValor);
				if (valor < -1 || valor > 1) {
					
					errorManager.attribError(ErrorManager.DataModelElementValueOutOfRange);
					return false;
					
				}
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			obj.setNormalizedMeasure(valor);
			
			return true;
		}

		
	}

	public class ScoreRaw extends DotNotationCommandImpl {

		
		public String doGetValue(ErrorManager errorManager, int n, int size) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			
			if (obj.getScoreRaw() == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(obj.getScoreRaw());
		}

		
		public boolean doSetValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			
			try {
				obj.setScoreRaw(Double.valueOf(novoValor));
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			return true;
		}

		
	}
	
	public class ScoreMin extends DotNotationCommandImpl {

		
		public String doGetValue(ErrorManager errorManager, int n, int size) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			
			if (obj.getScoreMin() == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			return String.valueOf(obj.getScoreMin());
		}

		
		public boolean doSetValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			try {
				obj.setScoreMin(Double.valueOf(novoValor));
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			return true;
		}

		
	}

	public class ScoreMax extends DotNotationCommandImpl {

		
		public String doGetValue(ErrorManager errorManager, int n, int size) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);

			if (obj.getScoreMax() == null) {
				errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
				return "";
			}
			
			
			return String.valueOf(obj.getScoreMax());
		}

		
		public boolean doSetValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			ActivityTree tree = errorManager.getTree();
			Objective obj = tree.getCurrentActivity().getObjectives().get(n);
			try {
				obj.setScoreMax(Double.valueOf(novoValor));
			} catch (Exception e) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
			
			return true;
		}

		
	}

	
	public void initialize(ErrorManager errorManager) {
	}

}
