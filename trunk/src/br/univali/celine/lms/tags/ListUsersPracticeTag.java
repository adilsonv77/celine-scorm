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

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lmsscorm.UserPractice;

@SuppressWarnings("serial")
public class ListUsersPracticeTag extends BodyTagSupport {

	private String varUser="varUser";
	private String varQuantity="varQuant";
	private String varTime="varTime";
	private String varLastTime="varLastTime";

	private Iterator<UserPractice> users;
	
		
	@Override
	public int doStartTag() throws JspException {


		List<UserPractice> lista = null;
		
		try {			
			lista = LMSConfig.getInstance().getDAO().listUsersPractice();
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		users = lista.iterator();
		
		if (users.hasNext()) {
			setAttributes();
			return EVAL_BODY_INCLUDE;
			
		}
		
		return SKIP_BODY;
	}
	
	private void setAttributes() {
		
		UserPractice user = users.next();
		
		pageContext.setAttribute(varUser, user.getUser());
		pageContext.setAttribute(varQuantity, user.getQuantity());
		pageContext.setAttribute(varTime, user.getTime());
		pageContext.setAttribute(varLastTime, user.getLastTimeAccessed());
		
	}
	
	@Override
	public int doAfterBody() throws JspException {
		
		if (users.hasNext()) {
			
			setAttributes();
			return EVAL_BODY_AGAIN;
		}		
		
		return SKIP_BODY;
	}

	public String getVarUser() {
		return varUser;
	}

	public void setVarUser(String varUser) {
		this.varUser = varUser;
	}

	public String getVarQuantity() {
		return varQuantity;
	}

	public void setVarQuantity(String varQuantity) {
		this.varQuantity = varQuantity;
	}

	public String getVarTime() {
		return varTime;
	}

	public void setVarTime(String varTime) {
		this.varTime = varTime;
	}

	public String getVarLastTime() {
		return varLastTime;
	}

	public void setVarLastTime(String varLastTime) {
		this.varLastTime = varLastTime;
	}
	
	
}
