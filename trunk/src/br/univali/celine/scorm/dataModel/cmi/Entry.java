package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

// DM Completed !!!
public class Entry implements DataModelCommand {

	public static final String name = "cmi.entry";
	private String[] values = new String[]{"ab-initio", "resume", ""};

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		/*
		 * Essa explicacao está na tabela 4.2.8a na secao Additional Behaviour Requirements
		 * attempCount > 1 foi uma adicao minha, pois no resume da arvore ele acaba colocando as atividades suspensas como false
		 */
		if (errorManager.getTree().getCurrentActivity().isSuspended() || errorManager.getTree().getCurrentActivity().getAttemptCount()>1) {
			return values[1];
		}
		 
		return values[0];
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		return false;
	}

	
	public void initialize(ErrorManager errorManager) {
	}

	
	public void clear(ErrorManager errorManager) throws Exception {
	}


}
