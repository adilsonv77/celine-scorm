package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

//DM Completed !!!
public class Credit implements DataModelCommand {

	public static final String name = "cmi.credit";

	
	public void clear(ErrorManager errorManager) throws Exception {	}

	
	public void initialize(ErrorManager errorManager) {	}

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		return "credit";
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		return false;
	}

}
