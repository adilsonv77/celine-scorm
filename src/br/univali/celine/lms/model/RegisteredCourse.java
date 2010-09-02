package br.univali.celine.lms.model;

public class RegisteredCourse {
	
	private String user;
	private String course;
	
	public RegisteredCourse(String userName, String courseName) {
		this.user = userName;
		this.course = courseName;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	@Override
	public String toString() {
		
		return user + " " + course;
	}
	
	

}
