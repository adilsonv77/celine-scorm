package br.univali.celine.scorm.model.cam;

public class Dependency {

	public Dependency() {
	}

	public Dependency(String identifierref) {
		this.identifierref = identifierref;
	}
	
	private String identifierref;

	public String getIdentifierref() {
		return identifierref;
	}

	public void setIdentifierref(String identifierref) {
		this.identifierref = identifierref;
	}
	
	public String toString() {
		return "<dependency identifierref=\""+identifierref+"\"/>\n";
	}
}
