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
package br.univali.celine.scorm.dataModel.adl.nav;

import java.util.Arrays;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.NavigationRequestType;
/**
 * SN Book 5.6.7. Request Valid
 * 
 * @author Adilson Vahldick
 *
 */
public class RequestValid implements DataModelCommand {

	public static final String name = "adl.nav.request_valid";
	
	private static String validos[] = {"choice", "continue", "previous"};

	
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		String ativSelec = "";
		if (key.contains(".")) { // choice
			ativSelec = key.split("[.]")[1];
			key = key.split("[.]")[0];
			
			ativSelec = ativSelec.substring(1, ativSelec.indexOf("}"));
			ativSelec = ativSelec.split("=")[1];
			
		}

		if (Arrays.binarySearch(validos, key) == -1) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement); 
			// é que request_valid ainda tem um filho, entao nao podemos considerá-lo como sendo um nó final
			return "";
		}
		
		// newValue terá o primeiro caracter em maiusculo
		key = key.replaceFirst("["+key+"]", key.substring(0, 1).toUpperCase());

		NavigationRequestType nrt = NavigationRequestType.valueOf(key);

		ActivityTree tree = errorManager.getTree();
		NavigationRequest nr = null;
		if (nrt == NavigationRequestType.Choice) {
			nr = new NavigationRequest(tree.getLearningActivity(ativSelec));  
		} else {
			nr = new NavigationRequest(nrt);
		}
		
		if (ProcessProvider.getInstance().getOverallSequencingProcess().isValidRequest(nr, tree)) {
			return "true";
		} else {
			return "false";
		}
		
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
