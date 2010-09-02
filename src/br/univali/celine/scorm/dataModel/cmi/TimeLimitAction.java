package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

// DM Completed !!!
public class TimeLimitAction implements DataModelCommand {

	public static final String name = "cmi.time_limit_action";

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		String timeLimitAction = errorManager.getTree().getCurrentActivity().getTimeLimitAction();
		if (timeLimitAction == null)
			timeLimitAction = "continue,no message";
		
		return timeLimitAction;
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		
		return false;
	}
	
	
	public void initialize(ErrorManager errorManager) {	}

	
	public void clear(ErrorManager errorManager) throws Exception {	}


}
