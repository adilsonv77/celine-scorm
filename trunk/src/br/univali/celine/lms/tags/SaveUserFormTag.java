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

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import br.univali.celine.lmsscorm.User;

public class SaveUserFormTag extends NextURLBodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String nameFieldName = "Nick : ";
	private String nameFieldPassw = "Senha : ";
	private String label = "Enviar";
	private String nameFieldAdmin = "Administrador";

	@Override
	public int doStartTag() throws JspException {

		PageContext pageContext = this.pageContext; // (PageContext)
													// getJspContext();
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();
		User user = (User) session.getAttribute("editUser");

		try {
			out.println("<form action='lms' method='post'>");

			if (user == null) {

				out.println("<input type='hidden' name='action' value='adduser'/>");
				mountForm("", "", false);

			} else {

				out.println("<input type='hidden' name='action' value='updateuser'/>");
				out.println("<input type='hidden' name='oldName' value='"
						+ user.getName() + "'>");
				mountForm(user.getName(), user.getPassw(), user.isAdmin());

			}

			out.println("<input type='hidden' name='nextURL' value='"
					+ getNextURL() + "'/>");
			out.println("<input type='hidden' name='thisURL' value='"
					+ getThisURL() + "'/>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;

	}

	@Override
	public int doEndTag() throws JspException {
		PageContext pageContext = this.pageContext; 
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();
		session.removeAttribute("editUser");
		try {
			out.println("<input type='submit' value='" + label + "'/>");
			out.println("</form>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	private void mountForm(String name, String passw, boolean admin)
			throws IOException {

		JspWriter out = pageContext.getOut(); // getJspContext()
		out.println("<div class='saveuser_name'>");
		out.println(nameFieldName);
		out.println("<input type='text' name='name' value='" + name + "'/>");
		out.println("</div>");

		out.println("<div class='saveuser_passw'>");
		out.println(nameFieldPassw);
		out.println("<input type='password' name='passw' value='" + passw
				+ "'/>");
		out.println("</div>");

		out.println("<div class='saveuser_admin'>");

		out.println("<input id='admin' type='checkbox' value='true'"
				+ (admin ? "checked='checked'" : "") + " name='admin'>&nbsp;");
		out.println("<label for=\"admin\">" + nameFieldAdmin + "</label>");

		out.println("</div>");
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNameFieldAdmin() {
		return nameFieldAdmin;
	}

	public void setNameFieldAdmin(String nameFieldAdmin) {
		this.nameFieldAdmin = nameFieldAdmin;
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

}
