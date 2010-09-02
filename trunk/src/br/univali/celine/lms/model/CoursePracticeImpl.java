package br.univali.celine.lms.model;

import java.util.Date;

import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.CoursePractice;

public class CoursePracticeImpl implements CoursePractice {

	private Course course;
	private Date lastTimeAccessed;
	private double time;
	
	public Course getCourse() {
		return course;
	}

	public Date getLastTimeAccessed() {
		return lastTimeAccessed;
	}

	public double getTime() {
		return time;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setLastTimeAccessed(Date lastTimeAccessed) {
		this.lastTimeAccessed = lastTimeAccessed;
	}

	public void setTime(double time) {
		this.time = time;
	}

	
}
