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
