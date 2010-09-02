package br.univali.celine.lmsscorm;

import java.util.Date;

public interface UserPractice {

	User getUser();

	/**
	 * how many courses accessed
	 */ 
	int getQuantity();

	/**
	 *  how much time spent in all the courses
	 */
	double getTime();

	Date getLastTimeAccessed();

}
