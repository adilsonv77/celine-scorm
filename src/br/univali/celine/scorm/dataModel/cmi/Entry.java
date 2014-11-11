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
