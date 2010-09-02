package br.univali.celine.lms.integration;

import java.util.List;

import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequestType;

public interface LMSIntegration {

	/**
	 * Receives a course's list and remove or add courses from this list.
	 * 
	 * @param courses
	 * @throws Exception
	 */
	void selectCourses(User user, List<Course> courses) throws Exception;
	
	/**
	 * Receives an activy tree's course and change it for the system.
	 * 
	 * @param course
	 * @param nextActivity 
	 * @param type
	 * @return returns if needs to recreate the visual tree 
	 * @throws Exception
	 */
	
	boolean changeCourse(User user, ActivityTree course, NavigationRequestType type, String nextActivity) throws Exception;

	/**
	 * Mount a content package based at the course id parameter.
	 * If there isnt a special mountage, then return null. 
	 * 
	 * @param courseId
	 * @return
	 * @throws Exception 
	 */
	ContentPackage openContentPackage(User user, String courseId) throws Exception;
	
	/**
	 * Process a setValue function with the cmi.interactions DM. 
	 * 
	 * @param index
	 * @param type
	 * @param value 
	 * @return true for the system process this element too 
	 * @throws Exception
	 */
	boolean processInteraction(User user, Course course, int index, String type, String value) throws Exception;

	/**
	 * Process a getValue function with the cmi.interactions DM. 

	 * @param user
	 * @param course
	 * @param index
	 * @param type
	 * @return
	 * @throws Exception
	 */
	String retrieveInteraction(User user, Course course, int index, String type) throws Exception;
	
}
