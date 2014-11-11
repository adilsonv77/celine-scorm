/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
