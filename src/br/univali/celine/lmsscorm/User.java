package br.univali.celine.lmsscorm;

public interface User {

	boolean isAdmin();
	void setAdmin(boolean admin);
	
	String getName();
	void setName(String name);
	
	String getPassw();
	void setPassw(String passw);

}
