package br.univali.celine.ws.core;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class CourseData {

	private String courseId;
	private String courseTitle;
	
	private DataHandler zipFile;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	@XmlMimeType("application/octet-stream")
	public DataHandler getZipFile() {
		return zipFile;
	}

	public void setZipFile(DataHandler zipFile) {
		this.zipFile = zipFile;
	}
	
	
	
}
