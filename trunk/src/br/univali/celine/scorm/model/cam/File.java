package br.univali.celine.scorm.model.cam;

public class File {

	private String href;

	public File() {
	}
	
	public File(String href) {
		this.href = href;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	public String toString() {
		return "<file href = \""+href+"\"/>";
	}
}
