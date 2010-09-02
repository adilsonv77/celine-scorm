package br.univali.celine.lms.tags;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class SuspendAllCourseTag extends NextURLBodyTagSupport {

	private String varLink = "varLink";

	@Override
	public int doStartTag() throws JspException {

		pageContext.setAttribute(varLink,
				"lms?action=suspendallcourse" 
					+ "&nextURL=" + getNextURL());

		return EVAL_BODY_INCLUDE;
	}

	public String getVarLink() {
		return varLink;
	}

	public void setVarLink(String varLink) {
		this.varLink = varLink;
	}
}
