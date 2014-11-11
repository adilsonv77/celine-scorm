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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.User;

@SuppressWarnings("serial")
public class UserInfoTag extends BodyTagSupport {

	private String name;
	private String varPassw = "varPassw";
	private String varAdmin = "varAdmin";
	private User user;
	
	@Override
	public int doStartTag() throws JspException {
	
		try {
		
			DAO dao = LMSConfig.getInstance().getDAO();
			user = dao.findUser(name);
			setAttributes();
			
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}

	private void setAttributes(){
		
		pageContext.setAttribute(varAdmin, user.isAdmin());
		pageContext.setAttribute(varPassw, user.getPassw());
		
	}
	
	public String getName() {
	
		return name;
		
	}

	
	public void setName(String name) {
	
		this.name = name;
		
	}

	
	public String getVarPassw() {
	
		return varPassw;
		
	}

	
	public void setVarPassw(String varPassw) {
	
		this.varPassw = varPassw;
		
	}

	
	public String getVarAdmin() {
	
		return varAdmin;
		
	}

	
	public void setVarAdmin(String varAdmin) {

		this.varAdmin = varAdmin;
		
	}
	
}
