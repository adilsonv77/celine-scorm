/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
