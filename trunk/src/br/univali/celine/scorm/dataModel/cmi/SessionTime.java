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
package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.model.dataTypes.Duration;
import br.univali.celine.scorm.rteApi.ErrorManager;

public class SessionTime implements DataModelCommand {

	public static final String name = "cmi.session_time";

	
	public void clear(ErrorManager errorManager) throws Exception {
		errorManager.getTree().removeDataModel(name);
	}

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsWriteOnly);
		
		return "";
	}

	
	public void initialize(ErrorManager errorManager) {}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		try {
			Duration d = new Duration(newValue);
			d.toSeconds();
			
			errorManager.getTree().putDataModel(name, newValue);
			return true;
			
		} catch (Exception e) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
	}

}
