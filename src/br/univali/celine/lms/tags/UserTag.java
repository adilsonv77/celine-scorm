package br.univali.celine.lms.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.User;

public class UserTag extends SimpleTagSupport {

	private String varUserName="username", varUserPassw="userpassw", varUserAdmin="useradmin", varUserObj="user";

	public String getVarUserObj() {
		return varUserObj;
	}

	public void setVarUserObj(String varUserObj) {
		this.varUserObj = varUserObj;
	}

	public String getVarUserName() {
		return varUserName;
	}

	public void setVarUserName(String varUserName) {
		this.varUserName = varUserName;
	}

	public String getVarUserPassw() {
		return varUserPassw;
	}

	public void setVarUserPassw(String varUserPassw) {
		this.varUserPassw = varUserPassw;
	}

	public String getVarUserAdmin() {
		return varUserAdmin;
	}

	public void setVarUserAdmin(String varUserAdmin) {
		this.varUserAdmin = varUserAdmin;
	}

	@Override
	public void doTag() throws JspException, IOException {
		
		User user = (User) getJspContext().getAttribute(UserImpl.USER, PageContext.SESSION_SCOPE);
		String userName = "";
		String userPassw = "";
		boolean userAdmin = false; 
		if (user != null) {
			userName = user.getName();
			userPassw = user.getPassw();
			userAdmin = user.isAdmin();
		}
		
		getJspContext().setAttribute(varUserName, userName);
		getJspContext().setAttribute(varUserPassw, userPassw);
		getJspContext().setAttribute(varUserAdmin, userAdmin);
		getJspContext().setAttribute(varUserObj, user);
		
	}
	
	
	
	
}
