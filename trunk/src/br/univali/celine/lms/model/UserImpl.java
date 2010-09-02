package br.univali.celine.lms.model;

import br.univali.celine.lmsscorm.User;

public class UserImpl implements User {
	
	public static final String USER = "userlogged";
	
	private String name;
	private String passw;
	private boolean admin;

	private int id;
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassw() {
		return passw;
	}
	public void setPassw(String passw) {
		this.passw = passw;
	}
	@Override
	public String toString() {
		return name + " " + passw;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	

}
