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

import java.util.Arrays;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.ProcessProvider;

// DM Completed !!!
public class Exit implements DataModelCommand {

	public static final String name = "cmi.exit";
	
	public static final String suspend = "suspend";
	
	private static final String[] valoresValidos = new String[]{"logout", "normal", suspend, "time-out"};

	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		errorManager.attribError(ErrorManager.DataModelElementIsWriteOnly);
		
		return "";
	}

	
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		if ("".equals(newValue) == false && Arrays.binarySearch(valoresValidos, newValue) == -1) {
			errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
			return false;
		}
		
		
		if (newValue.equals(valoresValidos[2])) {
			/*
			If the SCO sets cmi.exit to “suspend”, the LMS shall set the “Activity is Suspended” value of 
			the learning activity associated with the SCO to true.
			*/
			errorManager.getTree().getCurrentActivity().setSuspended(true);
		} else {
			if ("".equals(newValue) || newValue.equals(valoresValidos[1])){
				ProcessProvider.getInstance().getEndAttemptProcess().run(errorManager.getTree(), errorManager.getTree().getCurrentActivity());
			} else {
				
				errorManager.getTree().putDataModel(Exit.name, newValue);
				
			}
			
			
			/*
			 If the SCO sets cmi.exit to “time-out”, the LMS shall process an “Exit All” navigation request
			 when the SCO is taken away, instead of any pending (from the learner or LMS) navigation request.
			 
			 If the SCO sets cmi.exit to “logout”, the LMS shall process a “Exit All” navigation request 
			 when the SCO is taken away, instead of any pending (from the learner or LMS) navigation request.
			 
			 Porém, mais abaixo na documentação, diz que esses valores somente sao processados quando o SCO
			 invoca o método Terminate("").
			 
			 */
			
		}
		
		/*
		if (!newValue.equals(valoresValidos[2])) {
			// nao é suspended
			/*
			if (!newValue.equals(""))
				errorManager.getTree().getCurrentActivity().endAttempt();
		} else {
		
			errorManager.getTree().getCurrentActivity().setSuspended(newValue.equals(valoresValidos[2]));
			
		}
			*/
		
		return true;
	}

	
	public void clear(ErrorManager errorManager) throws Exception {
		errorManager.getTree().removeDataModel(name);
	}

	
	public void initialize(ErrorManager errorManager) {	}
}
