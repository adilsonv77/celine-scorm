package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

// DM Completed !!!
public class LaunchData implements DataModelCommand {

	public static final String name = "cmi.launch_data";

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {

		if (key.length() > 0) {
			errorManager.attribError(ErrorManager.GeneralArgumentError);
			return "";
		}
		
		String dataFromLMS = errorManager.getTree().getCurrentActivity().getDataFromLMS();
		if (dataFromLMS == null) {
			errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			return "";
		}
			
		return dataFromLMS;
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		return false;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {	}

	
	public void initialize(ErrorManager errorManager) {	}

}
