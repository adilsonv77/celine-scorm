package br.univali.celine.scorm.dataModel;

import br.univali.celine.scorm.rteApi.ErrorManager;

public interface BasicDataModelCommand {

	public String getValue(String key, ErrorManager errorManager) throws Exception;
	public boolean setValue(String key, String newValue, ErrorManager errorManager) throws Exception;
	
}
