package br.univali.celine.lms.tags;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class DeleteCourseTag extends NextURLBodyTagSupport {

	private String courseId = "courseId";
	private String learnerId = "learnerId";
	private String varLink = "varLink";

	@Override
	public int doStartTag() throws JspException {

		pageContext.setAttribute(
				varLink,
				"lms?action=deletecourse&courseId=" + courseId 
						+ "&learnerId=" + learnerId 
						+ "&nextURL=" + getNextURL());
		
		return EVAL_BODY_INCLUDE;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getLearnerId() {
		return learnerId;
	}

	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}

	public void setVarLink(String varLink) {
		this.varLink = varLink;
	}

	public String getVarLink() {
		return varLink;
	}

}
