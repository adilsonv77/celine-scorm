package br.univali.celine.lms.model;

import br.univali.celine.lmsscorm.Course;

public class CourseImpl implements Course {

	private String folderName;
	private String title;
	private String id; // esse id identifica um id que fica dentro do ContentPackage
	private boolean removed = false;
	private boolean derived = false;
	private int idCode = 0;

	public CourseImpl(String id, String folderName, String title, boolean derived, boolean removed) {
		this.folderName = folderName;
		this.title = title;
		this.id = id;
		this.derived = derived;
		this.removed = removed;
	}
	
	public CourseImpl(int idCode, String id, String folderName, String title, boolean derived, boolean removed) {
		this(id, folderName, title, derived, removed);
		this.idCode = idCode;
	}
	

	public String getFolderName() {
		return folderName;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	
	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	
	public boolean isDerived() {
		return derived;
	}

	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	
	public String toString() {

		return folderName + " " + id + " " + title;
	}

	public int getIdCode() {
		return idCode;
	}
}
