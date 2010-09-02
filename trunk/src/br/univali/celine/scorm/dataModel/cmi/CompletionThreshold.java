package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

/**
 * DM Element completed !!
 * 
 * @author adilsonv
 *
 */
public class CompletionThreshold implements DataModelCommand {

	public static final String name = "cmi.completion_threshold";

	
	public void initialize(ErrorManager errorManager) {}

	
	public void clear(ErrorManager errorManager) throws Exception {}

	
	public String getValue(String key, ErrorManager errorManager) throws Exception {
		
		double completionThreshold = errorManager.getTree().getCurrentActivity().getCompletionThreshold();
		if (completionThreshold == -1) {
			errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			return "";
		}
		
		return String.valueOf(completionThreshold);
	}

	
	public boolean setValue(String key, String newValue, ErrorManager errorManager) throws Exception {

		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		
		return false;
	}

}
