package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.LearningActivity;

// DM Completed !!!
public class ScaledPassingScore implements DataModelCommand {

	public static final String name = "cmi.scaled_passing_score";

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {

		LearningActivity curAct = errorManager.getTree().getCurrentActivity();
		Double v = curAct.getScaledPassingScore();

		if (v != null) {
			return String.valueOf(v);
		}
			
		errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
		return "";
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		return false;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {	}

	
	public void initialize(ErrorManager errorManager) {	}

}
