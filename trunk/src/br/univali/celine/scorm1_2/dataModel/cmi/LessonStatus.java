package br.univali.celine.scorm1_2.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.LearningActivity;

public class LessonStatus implements DataModelCommand {

	@Override
	public void clear(ErrorManager errorManager) throws Exception {
	}

	@Override
	public void initialize(ErrorManager errorManager) {
	}

	@Override
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		LearningActivity curAct = errorManager.getTree().getCurrentActivity();
		return curAct.getLessonStatus().toString();
	}

	@Override
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		br.univali.celine.scorm.sn.model.LessonStatus value = null;
		try {
			value = br.univali.celine.scorm.sn.model.LessonStatus.valueOf(newValue);
		} catch(Exception e) {

			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
			
		}
		
		errorManager.getTree().getCurrentActivity().setLessonStatus(value);
		return true;
		
	}

}
