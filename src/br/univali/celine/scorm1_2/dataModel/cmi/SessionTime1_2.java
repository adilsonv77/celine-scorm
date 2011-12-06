package br.univali.celine.scorm1_2.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class SessionTime1_2 implements DataModelCommand {

	@Override
	public void clear(ErrorManager errorManager) throws Exception {	}

	@Override
	public void initialize(ErrorManager errorManager) {	}

	@Override
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		errorManager.attribError(ErrorManager.DataModelElementIsWriteOnly);
		return "";
	}

	@Override
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		try {
			Duration1_2.test(newValue);
			errorManager.getTree().putDataModel("cmi.core.session_time", newValue);
		} catch (Exception e) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
		
		return true;
	}

}
