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
package br.univali.celine.lms.config;

import java.util.List;
import br.univali.celine.lms.integration.LMSIntegration;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequestType;

public class DefaultLMSIntegration implements LMSIntegration {

	
	public void selectCourses(User user, List<Course> courses) throws Exception {

		
	}

	
	public ContentPackage openContentPackage(User user, String courseId)  {
	
		return null;
		
	}

	
	public boolean processInteraction(User user, Course course, int index,
			String type, String value) throws Exception {

		return true;
	}

	
	public String retrieveInteraction(User user, Course course, int index,
			String type) throws Exception {
		
		return null;
	}

	
	public boolean changeCourse(User user, ActivityTree course,
			NavigationRequestType type, String nextActivity) throws Exception {

		return false;
		
	}

}
