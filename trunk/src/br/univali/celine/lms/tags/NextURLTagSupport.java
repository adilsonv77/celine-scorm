package br.univali.celine.lms.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public abstract class NextURLTagSupport extends SimpleTagSupport {

	private String nextURL;

	public String getNextURL() {
		return nextURL;
	}

	public void setNextURL(String nextURL) {
		this.nextURL = nextURL;
	}
	
	public String getThisURL() { 
		PageContext jsp = (PageContext) getJspContext();
		return (((HttpServletRequest)jsp.getRequest()).getServletPath()).substring(1);
	}

	
}
