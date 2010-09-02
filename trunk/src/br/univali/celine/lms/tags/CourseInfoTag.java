package br.univali.celine.lms.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.Course;


@SuppressWarnings("serial")
public class CourseInfoTag extends BodyTagSupport {

	private String courseId;
	private String varFolderName = "varFolderName";
	private String varTitle = "varTitle";
	
	@Override
	public int doStartTag() throws JspException {

		try {
			
			setAttributes();
			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	private void setAttributes() throws Exception {
		
		Course course = LMSConfig.getInstance().getDAO().findCourse(courseId);
		
		pageContext.setAttribute(varTitle, course.getTitle());
		pageContext.setAttribute(varFolderName, course.getFolderName());
		
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getVarFolderName() {
		return varFolderName;
	}
	
	public void setVarFolderName(String varFolderName) {
		this.varFolderName = varFolderName;
	}
	
	public String getVarTitle() {
		return varTitle;
	}
	
	
	public void setVarTitle(String varTitle) {
		this.varTitle = varTitle;
	}
	
}
