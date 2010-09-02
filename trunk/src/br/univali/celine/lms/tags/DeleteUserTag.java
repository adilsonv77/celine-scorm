package br.univali.celine.lms.tags;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class DeleteUserTag extends NextURLBodyTagSupport {

	private String name;
	private String varLink = "varLink";

	
	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute(varLink,
				"lms?action=deleteuser&name=" + name 
					+ "&nextURL=" + getNextURL());
		
		return EVAL_BODY_INCLUDE;
	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getVarLink() {

		return varLink;

	}

	public void setVarLink(String varLink) {

		this.varLink = varLink;

	}

}
