package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.model.dataTypes.Duration;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class SessionTime implements DataModelCommand {

	public static final String name = "cmi.session_time";

	
	public void clear(ErrorManager errorManager) throws Exception {
		errorManager.getTree().removeDataModel(name);
	}

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsWriteOnly);
		
		return "";
	}

	
	public void initialize(ErrorManager errorManager) {}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		try {
			Duration d = new Duration(newValue);
			d.toSeconds();
			
			errorManager.getTree().putDataModel(name, newValue);
			return true;
			
		} catch (Exception e) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
	}

}
