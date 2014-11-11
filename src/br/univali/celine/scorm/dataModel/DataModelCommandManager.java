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
package br.univali.celine.scorm.dataModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.rteApi.ListenerDataModel;


/**
 * 
 * Manage the DataModelCommand's. Because the structure of the command, separated by dots,
 * it's necessary a tree to identify the command.
 * 
 * @author Adilson Vahldick
 *
 */

public class DataModelCommandManager {

	private static DataModelCommandManager instance;
	private Logger logger = Logger.getLogger("global");
	
	public static DataModelCommandManager getGlobalInstance() {
		if (instance == null)
			instance = new DataModelCommandManager();
		return instance;
	}
	
	public static void clearGlobalInstance() {
		instance = null;
	}

	private class DataValue {

		private String restoChave;
		private DataModelCommand dmCommand;
		
		public DataValue(String restoChave, DataModelCommand dmCommand) {
			this.restoChave = restoChave;
			this.dmCommand = dmCommand;
		}

		public DataModelCommand getDmCommand() {
			return dmCommand;
		}
		
		public String getRestoChave() {
			return restoChave;
		}

		@Override
		public String toString() {
			return dmCommand + " " + restoChave;
		}
		
	}
	
	
	private class NodeTree {
		
		private HashMap<String, Object> values = new HashMap<String, Object>(); 
		
		public void put(String key, DataModelCommand comm) {
			
			String keys[] = key.split("[.]");
			if (keys.length == 1) {
				values.put(key, comm);
			} else {
				NodeTree subTree = (NodeTree) values.get(keys[0]);
				if (subTree == null) {
					subTree = new NodeTree();
					values.put(keys[0], subTree);
				}
				subTree.put(key.substring(keys[0].length()+1), comm);
			}
			
		}
		
		public DataValue get(String key) {

			String keys[] = key.split("[.]");
			if (keys.length == 1) {
				
				try {
					return new DataValue("", (DataModelCommand) values.get(key));
				} catch (Exception e) {
					return null;
				}
				
			} else {
				Object o = values.get(keys[0]);
				if (o != null) {
					String saldoKey = key.substring(keys[0].length()+1);
					if (o instanceof NodeTree) {
						return ((NodeTree)o).get(saldoKey);
					} else {
						return new DataValue(saldoKey, (DataModelCommand) o);
					}
				}

				return null; 
				
			}
			
		}

		public void clear(ErrorManager errorManager) throws Exception {
			for (Object o:values.values()) {
				if (o instanceof NodeTree)
					((NodeTree)o).clear(errorManager);
				else
					((DataModelCommand)o).clear(errorManager);
			}
			
		}

		public void initialize(ErrorManager errorManager) {
			for (Object o:values.values()) {
				if (o instanceof NodeTree)
					((NodeTree)o).initialize(errorManager);
				else
					((DataModelCommand)o).initialize(errorManager);
			}
		}
	}

	private NodeTree tree = new NodeTree();
	private Set<ListenerDataModel> listeners = new HashSet<ListenerDataModel>();
	
	public void put(String key, DataModelCommand comm) {
		
		tree.put(key, comm);
	
	}
	
	public String getValue(String key, ErrorManager errorManager) throws Exception {
		
		
		errorManager.attribError(0);
		DataValue dv = tree.get(key);

		logger.info(key + " " + dv);
		
		if (dv == null || dv.getDmCommand() == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return "";
		}
		
		String value = requestListeners(key, errorManager);
		if (value == null) {
			return dv.getDmCommand().getValue(dv.getRestoChave(), errorManager);
		}
		
		return value;
	}

	public boolean setValue(String key, String value,
			ErrorManager errorManager) throws Exception {
		
		errorManager.attribError(0);
		DataValue dv = tree.get(key);
		if (dv == null || dv.getDmCommand() == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return false;
		}
		try {
			if (notifyListeners(key, value, errorManager)) {
				return dv.getDmCommand().setValue(dv.getRestoChave(), value, errorManager);
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private boolean notifyListeners(String key, String value, ErrorManager errorManager) throws Exception {
		
		boolean retorno = true; // when true then the RTE will process the DM 
		for (ListenerDataModel listener:listeners) {
			retorno = retorno && listener.setValue(key, value, errorManager.getUser(), errorManager.getCourseFolder());
		}
		return retorno;
	}

	private String requestListeners(String key, ErrorManager errorManager) throws Exception {
		for (ListenerDataModel listener:listeners) {
			String value = listener.getValue(key, errorManager.getUser(), errorManager.getCourseFolder());
			if (value != null)
				return value;
		}
		return null;
	}

	public void clear(ErrorManager errorManager) throws Exception {
		
		tree.clear(errorManager);
		
	}

	public void initialize(ErrorManager errorManager) {
		tree.initialize(errorManager);
	}

	public void registerListener(ListenerDataModel listener) {
		if (listener != null)
			listeners.add(listener);
	}

}
