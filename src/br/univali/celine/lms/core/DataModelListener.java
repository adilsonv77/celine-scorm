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
package br.univali.celine.lms.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.integration.LMSIntegration;
import br.univali.celine.lms.utils.LMSLogger;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.dataModel.cmi.Interactions;
import br.univali.celine.scorm.rteApi.ListenerDataModel;
import br.univali.celine.scorm.rteApi.ListenerRequest;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequestType;

public class DataModelListener implements ListenerDataModel, ListenerRequest {

	private static DataModelListener dml = null;
	
	private Logger logger = Logger.getLogger("global");

	private Map<String,Boolean> mustRecreateTree = new HashMap<String,Boolean>();
	
	public static ListenerDataModel getInstanceDataModel() {
		return getInstance();
	}

	public static ListenerRequest getInstanceRequest() {
		return getInstance();
	}

	public static DataModelListener getInstance() {
		if (dml == null)
			dml = new DataModelListener();
		return dml;
	}
	
	
	public boolean setValue(String key, String value, User user, String courseFolder)
			throws Exception {
		logger.info("DataModelListener.setValue : " + key);
		if (key.startsWith(Interactions.name) && !key.contains("_children")) {
			String[] peaces = key.split("[.]");
			LMSIntegration lmsIntegration = LMSConfig.getInstance().getLMSIntegration();
			
			DAO dao = LMSConfig.getInstance().getDAO();
			Course course = null;
			try {
				course = dao.findCourseByFolderName(courseFolder);
			} catch (Exception e) {
				LMSLogger.throwing(e);
				throw e;
			}
			
			logger.info("processInteraction");
			return lmsIntegration.processInteraction(user, course, Integer.parseInt(peaces[2]), peaces[peaces.length-1], value);
			
		}
		return true;
	}

	
	public String getValue(String key, User user, String courseFolder) throws Exception {
		logger.info("DataModelListener.getValue : " + key);
		if (key.startsWith(Interactions.name) && !key.contains("_children")) {
			String[] peaces = key.split("[.]");
			LMSIntegration lmsIntegration = LMSConfig.getInstance().getLMSIntegration();
			
			DAO dao = LMSConfig.getInstance().getDAO();
			Course course = null;
			try {
				course = dao.findCourseByFolderName(courseFolder);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
			int index = -1;
			try {
				index = Integer.parseInt(peaces[2]);
			} catch (Exception e) {
				LMSLogger.throwing(e);
			}

			logger.info("retrieveInteraction");
			return lmsIntegration.retrieveInteraction(user, course, index, peaces[peaces.length-1]);
			
		}
		
		return null;
	}

	
	public void request(NavigationRequestType type, String nextActivity, ActivityTree tree, User user, String courseName) throws Exception {
		
		logger.info("DataModelListener.request : (" + type + ", " + nextActivity + ") " + courseName);
		
		if (tree.getCurrentActivity() != null) {
			DAO dao = LMSConfig.getInstance().getDAO();
			Course course = dao.findCourseByFolderName(courseName);
			logger.info("saveRequestActivity");
			dao.saveRequestActivity(user, course, tree.getCurrentActivity());
		}
		
		if (nextActivity != null) {
			LMSIntegration lmsIntegration = LMSConfig.getInstance().getLMSIntegration();
			boolean recreateTree = lmsIntegration.changeCourse(user, tree, type, nextActivity);
			logger.info("DataModelListener.request : put("+user.getName()+","+recreateTree+")");
			
			mustRecreateTree.put(user.getName(), recreateTree);
		}
		
	}
	
	public boolean isMustRecreateTree(User user) {
		Boolean ret = mustRecreateTree.get(user.getName());
		logger.info("DataModelListener.isMustRecreateTree : "+user.getName()+"=>"+ret);
		mustRecreateTree.remove(user.getName());
		return (ret != null) && (ret);
		 
	}

}
