package br.univali.celine.lms.dao;

import java.util.ArrayList;
import java.util.List;

import br.univali.celine.lms.model.RegisteredCourse;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.User;

public class CelineXML {

	private List<User> users = new ArrayList<User>();
	private List<RegisteredCourse> registeredCourses = new ArrayList<RegisteredCourse>();
	private List<Course> courses = new ArrayList<Course>();
	

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<RegisteredCourse> getRegisteredCourses() {
		return registeredCourses;
	}

	public void setRegisteredCourses(List<RegisteredCourse> registeredCourses) {
		this.registeredCourses = registeredCourses;
	}

		
	public List<Course> getCourses() {
		return courses;
	}
	

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public User findUser(String userName) {
		
		for (User user:users) 
			if (user.getName().equals(userName)) {
				return user;
			}
		
		return null;
	}

	@Override
	public String toString() {
		
		String ret = "users: ";
		
		for (User user:users) {
			ret += "[" + user + "] ";
		}
		
		ret += "\ncourses:";
		for (Course course:courses) {
			ret += "[" + course + "] ";
		}
		
		ret += "\nregistered courses: ";
		for (RegisteredCourse rc:registeredCourses) {
			ret += "[" + rc + "] ";
		}
		
		return ret;
	}	
	
}
