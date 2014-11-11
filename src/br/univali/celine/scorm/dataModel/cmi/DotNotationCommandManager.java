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

import java.util.HashMap;

import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.ActivityTree;

public class DotNotationCommandManager {

	private HashMap<String, DotNotationCommand> commands = new HashMap<String, DotNotationCommand>();
	private String dataModelName; 
	private DotNotationCommand countCommand;

	public DotNotationCommandManager(String dataModelName, DotNotationCommand countCommand) {
		this(dataModelName);
		this.countCommand = countCommand;
	}
	
	public DotNotationCommandManager(String dataModelName) {
		this.dataModelName = dataModelName;
	}

	public void add(String chave, DotNotationCommand comm) {
		commands.put(chave, comm);
	}
	
	public String getValue(String key, ErrorManager errorManager) {
		
		if (key.equals("_children")) {
			return getChildren();
		}
	
		if (key.equals("_count")) {
			
			return countCommand.getValue(errorManager, 0, 0);
			
		}
		else {
			String ret = tratarRestoExpressao(true, key, null, errorManager);
			if (ret == null)
				ret = "";
			return ret;
		}
		
	}
	
	public boolean setValue(String key, String novoValor, ErrorManager errorManager) {
		
		if (key.equals("_children")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}

		if (key.equals("_count")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		} else {
			String ret = tratarRestoExpressao(false, key, novoValor, errorManager);
			return ret != null && Boolean.valueOf(ret);
		}
	}
	
	public void clear(ErrorManager errorManager) throws Exception {
		
		String idDM = getIdentifyDataModel(errorManager);
		
		ActivityTree tree = errorManager.getTree();
		String count = countCommand.getValue(errorManager, 0, 0);  // tree.getDataModel(idDM + ".count");
		if (count != null) {
			int size = Integer.parseInt(count);
			for (int i = 0; i < size; i++)
				tree.removeDataModel(idDM + "."+i);
		}
		//tree.removeDataModel(idDM + ".count");
		
	}
	
	protected String getIdentifyDataModel(ErrorManager errorManager) {
		return dataModelName;
	}
	
	private String tratarRestoExpressao(boolean getValue, String key, String novoValor, ErrorManager errorManager) {
		String campos[] = key.split("[.]");
		
		int n = 0;
		
		try {
			n = Integer.parseInt(campos[0]);
		} catch (Exception e) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return null;
		}
		
		if (String.valueOf(n).length() != campos[0].length()) {
			errorManager.attribError(ErrorManager.GeneralSetFailure);
			return null;
		}
		
		String count = countCommand.getValue(errorManager, 0, 0); 
		int size = 0;
		if (count != null)
			size = Integer.parseInt(count);
		
		DotNotationCommand comm = commands.get(campos[1]); //campos.length-1]);
		if (comm == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return null;
		}

		String retorno = null;
		if (getValue) {
			retorno = comm.getValue(errorManager, n, size);
		} else {
			retorno = ""+comm.setValue(errorManager, n, size, novoValor);
		}
		
		return retorno;
	}

	public String getChildren() {
		String children = "";
		for (String chave:commands.keySet()) {
			children += chave + ", ";
		}
		return children.substring(0, children.length()-2);
	}
}
