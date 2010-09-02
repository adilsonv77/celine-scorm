package br.univali.celine.scorm.dataModel.cmi;

import java.util.Arrays;
import java.util.logging.Logger;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.CompletionStatusValue;
/**
 * 
 * DM Element completed !!!
 * 
 * @author adilsonv
 *
 */
public class CompletionStatus implements DataModelCommand {

	public static final String name = "cmi.completion_status";
	private static Logger log = Logger.getLogger("global");

	
	public String getValue(String key, ErrorManager errorManager) {
		return errorManager.getTree().getCurrentActivity().getCompletionStatus().toString();
	}

	
	public boolean setValue(String key, String newValue, ErrorManager errorManager) {
		
		log.info("setValue (cmi.completion_status) " + newValue);
		
		int index = Arrays.binarySearch(CompletionStatusValue.CompletionStatusValueString, newValue);
		if (index == -1) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
		
		log.info("setValue (cmi.completion_status) [aprovou] " + newValue);
		errorManager.getTree().getCurrentActivity().setCompletionStatus(CompletionStatusValue.valor(newValue));
		log.info("setValue (cmi.completion_status) [pós-setagem] " + newValue);
		
		return true;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {}

	
	public void initialize(ErrorManager errorManager) {}
	
}
