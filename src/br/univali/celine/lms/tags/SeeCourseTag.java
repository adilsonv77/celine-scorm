package br.univali.celine.lms.tags;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class SeeCourseTag extends NextURLBodyTagSupport {

	private String courseId;
	private String varLink = "varLink";
	
	@Override
	public int doStartTag() throws JspException {

		try {
			
			setAttributes();
		
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	
	private void setAttributes() {
		
		pageContext.setAttribute(varLink, 
				
				"lms?action=seecourse"
				+ "&courseId=" + courseId
				+ "&nextURL=" + getNextURL());
		
	}

	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	
	public String getVarLink() {
		return varLink;
	}
	
	
	public void setVarLink(String varLink) {
		this.varLink = varLink;
	}

}
