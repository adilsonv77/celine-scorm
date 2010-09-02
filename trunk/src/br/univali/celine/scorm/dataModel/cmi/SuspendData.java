package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class SuspendData implements DataModelCommand {

	public static final String name = "cmi.suspend_data";

	
	public void clear(ErrorManager errorManager) throws Exception {
		// TODO: IMPORTANTE: ver a pagina no segundo Aditional Behaviour Requirement RTE-4-136
		errorManager.getTree().removeDataModel(name);
	}

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		String s = errorManager.getTree().getDataModel(name);
		if (s == null) {
			errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			return "";
		}
		
		return s;
	}

	
	public void initialize(ErrorManager errorManager) {	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		errorManager.getTree().putDataModel(name, newValue);
		return true;
	}

}
