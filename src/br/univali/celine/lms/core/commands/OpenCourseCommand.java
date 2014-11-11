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
package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.UserAdministration;
import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.DataModelListener;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lms.model.CourseImpl;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.rteApi.API;
import br.univali.celine.scorm.rteApi.APIImplementation;
import br.univali.celine.scorm.rteApi.ListenerDataModel;
import br.univali.celine.scorm.rteApi.ListenerRequest;



public class OpenCourseCommand implements Command {

	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String courseId = request.getParameter("courseId");

		String userName = request.getParameter("name");
		String userPassw = request.getParameter("passw");
		
		LMSConfig config = LMSConfig.getInstance();
		LMSControl control = LMSControl.getInstance();
		User user = null;
		if (userName != null)
			user = control.webLogin(request, userName, userPassw);
		else
			user = UserAdministration.getUser(request);
		
		
		CourseImpl course = (CourseImpl) LMSConfig.getInstance().getDAO().findCourse(courseId);
		ContentPackage cp = null;
		String courseFolder = "";

		if (course != null) { // eu acho que sempre tem q ser diferente de null :p
		
			if (course.isDerived())
				cp = config.openContentPackageStr(control.getDerivedCoursePackageStr(courseId));
				
	
			else {				
			
				cp = config.openContentPackageById(user, courseId);
						
			}
			courseFolder = course.getFolderName();	
		}
		
		ListenerDataModel dml = DataModelListener.getInstanceDataModel();
		ListenerRequest rl = DataModelListener.getInstanceRequest();
		
		APIImplementation api = new APIImplementation(
				courseFolder, 
				cp,
				user,
				config.getDAO(), 
				dml, rl);
		
		request.getSession().setAttribute(API.APIKEY, api);

		return HTMLBuilder.buildFrame("lms?action=seetree", LMSConfig
				.getInstance().getCourseFolder(courseFolder) + 
				api.getResource());
		
	}

}
