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
