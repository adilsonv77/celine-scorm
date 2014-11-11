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
import javax.servlet.jsp.JspWriter;



public class LoginFormTag extends NextURLBodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private String nameFieldName = "Nick : ";
	private String nameFieldPassw = "Senha : ";
	private String defaultName = "";
	private String defaultPassword = "";
	
	@Override
	public int doStartTag() throws JspException {
		
		
		try {
			
			JspWriter out = pageContext.getOut();
			
			
			
			
			out.println("<form action='lms' method='post'>");
			out.println("<input type='hidden' name='action' value='login'/>");
			out.println("<input type='hidden' name='thisURL' value='"+getThisURL()+"'/>");
			
			out.println("<input type='hidden' name='nextURL' value='"+getNextURL()+"'/>");
			
			out.println("<div class='login_name'>");
			out.println(nameFieldName);
			out.println("<input type='text' name='name' value='" + defaultName + "'/>");
			out.println("</div>");
			
			out.println("<div class='login_passw'>");
			out.println(nameFieldPassw);
			out.println("<input type='password' name='passw' value='" + defaultPassword + "'/>");
			out.println("</div>");

			
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e.getCause());
		}
		
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.println("<input type='submit' value='Enviar'/>");
			out.println("</form>");
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e.getCause());
		}

		return super.doEndTag();
	}
	
	public String getNameFieldName() {
		return nameFieldName;
	}

	public void setNameFieldName(String nameFieldName) {
		this.nameFieldName = nameFieldName;
	}

	public String getNameFieldPassw() {
		return nameFieldPassw;
	}

	public void setNameFieldPassw(String nameFieldPassw) {
		this.nameFieldPassw = nameFieldPassw;
	}
	
	public String getDefaultName() {
		return defaultName;
	}
	
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
	
	public String getDefaultPassword() {
		return defaultPassword;
	}
	
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	
}
