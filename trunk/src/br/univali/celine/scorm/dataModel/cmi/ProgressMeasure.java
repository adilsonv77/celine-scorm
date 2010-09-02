package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

// DM Completed !!!
public class ProgressMeasure implements DataModelCommand {

	public static final String name = "cmi.progress_measure";

	
	public void clear(ErrorManager errorManager) throws Exception {	}

	
	public void initialize(ErrorManager errorManager) {}

	
	public String getValue(String key, ErrorManager errorManager) throws Exception {
		
		double valor = errorManager.getTree().getCurrentActivity().getProgressMeasure();
		if (valor == -1) {
			errorManager.attribError(ErrorManager.DataModelElementValueNotInitialized);
			return "";
		}
		
		return String.valueOf(valor);
	}

	
	public boolean setValue(String key, String newValue, ErrorManager errorManager) throws Exception {
		
		double valor = 0;
		try {
			valor = Double.valueOf(newValue);
			
			if (valor < 0 || valor > 1) {
				errorManager.attribError(ErrorManager.DataModelElementValueOutOfRange);
				return false;
			}
			
		} catch (Exception e) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
		
		errorManager.getTree().getCurrentActivity().setProgressMeasure(valor);
		return true;
	}

}
