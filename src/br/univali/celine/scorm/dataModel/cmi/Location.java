package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class Location implements DataModelCommand {

	public static final String name = "cmi.location";

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		String s = errorManager.getTree().getDataModel(name);
		if (s == null) {
			errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			return "";
		}
		
		return s;
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		errorManager.getTree().putDataModel(name, newValue);
		return true;
	}
	
	
	public void initialize(ErrorManager errorManager) {	}

	
	public void clear(ErrorManager errorManager) throws Exception {
		errorManager.getTree().removeDataModel(name);
	}


}
