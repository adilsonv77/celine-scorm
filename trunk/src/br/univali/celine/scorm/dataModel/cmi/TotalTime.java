package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.model.dataTypes.Duration;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class TotalTime implements DataModelCommand {

	public static final String name = "cmi.total_time";
	
	
	public void clear(ErrorManager errorManager) throws Exception {}

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		String totalTime = errorManager.getTree().getDataModel(TotalTime.name);
		if (totalTime == null)
			totalTime = "0";
		
		return Duration.parseDuration(Long.valueOf(totalTime)).toString();
	}

	
	public void initialize(ErrorManager errorManager) {	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
		return false;
		
	}

}
