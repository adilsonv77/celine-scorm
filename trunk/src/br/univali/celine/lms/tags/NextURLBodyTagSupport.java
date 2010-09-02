package br.univali.celine.lms.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public abstract class NextURLBodyTagSupport extends BodyTagSupport {

	private String nextURL;

	public String getNextURL() {
		return nextURL;
	}

	public void setNextURL(String nextURL) {
		this.nextURL = nextURL;
	}
	
	public String getThisURL() { 
		return (((HttpServletRequest)pageContext.getRequest()).getServletPath()).substring(1);
	}

}
