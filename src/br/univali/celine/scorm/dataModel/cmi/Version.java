package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

/**
 * RTE Book. Section 4.2.1. Data Model Version
 * 
 * DM Completed !!!
 * 
 * @author Adilson Vahldick
 *
 */

public class Version implements DataModelCommand {

	public static final String name = "cmi._version";

	public String getValue(String key, ErrorManager errorManager) {
		return "1.0";
	}

	public boolean setValue(String key, String novoValor, ErrorManager errorManager) {
		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		return false;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {
	}

	
	public void initialize(ErrorManager errorManager) {
	}

}
