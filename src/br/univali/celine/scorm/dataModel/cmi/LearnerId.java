package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

// DM Completed !!!
public class LearnerId implements DataModelCommand {

	public static final String name = "cmi.learner_id";
	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		return String.valueOf(((UserImpl)errorManager.getUser()).getId());
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		
		return false;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {
	}

	
	public void initialize(ErrorManager errorManager) {
	}

}
