package br.univali.celine.lmsscorm;

import java.util.Date;

public interface CoursePractice {

	Course getCourse();
	
	/**
	 *  how much time spent in all the courses
	 */
	double getTime();

	/**
	 *  
	 */
	Date getLastTimeAccessed();
}
