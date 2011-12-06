package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class LearnerName implements DataModelCommand {

	public static final String name = "cmi.learner_name";
	
	@Override
	public void clear(ErrorManager errorManager) throws Exception {
	}

	@Override
	public void initialize(ErrorManager errorManager) {
	}

	@Override
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		return errorManager.getUser().getName();	
	}

	@Override
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		
		return false;
	}

}
