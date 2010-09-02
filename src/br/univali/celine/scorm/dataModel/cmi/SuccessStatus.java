package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.LearningActivity;

// DM Completed !!!
public class SuccessStatus implements DataModelCommand {

	public static final String name = "cmi.success_status";

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		LearningActivity curAct = errorManager.getTree().getCurrentActivity();
		
		return curAct.getSuccessStatus().toString();
		
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		br.univali.celine.scorm.sn.model.SuccessStatus value = null;
		try {
			value = br.univali.celine.scorm.sn.model.SuccessStatus.valueOf(newValue);
		} catch(Exception e) {

			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
			
		}
		
		errorManager.getTree().getCurrentActivity().setSuccessStatus(value);
		return true;
		
	}

	
	public void initialize(ErrorManager errorManager) {
	}

	
	public void clear(ErrorManager errorManager) throws Exception {
	}

}
