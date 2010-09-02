package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.LearningActivity;

/**
 * 
 * DM Completed !!!
 * 
 * @author adilsonv
 *
 */
public class MaxTimeAllowed implements DataModelCommand {

	public static final String name = "cmi.max_time_allowed";

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		LearningActivity curAct = errorManager.getTree().getCurrentActivity();

		String maxTimeAllowed = curAct.getLimitConditionAbsoluteDurationLimitTimeInterval();
		if (maxTimeAllowed == null) {
			errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
		} else {
			return maxTimeAllowed;
		}
		
		return "";
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		
		return false;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {}

	
	public void initialize(ErrorManager errorManager) {	}

}
