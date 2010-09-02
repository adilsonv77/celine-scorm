package br.univali.celine.lms.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;



public class LoginFormTag extends NextURLTagSupport {

	private String nameFieldName = "Nick : ";
	private String nameFieldPassw = "Senha : ";
	private String defaultName = "";
	private String defaultPassword = "";
	
	@Override
	public void doTag() throws JspException, IOException {
		
		
		try {
			PageContext jsp = (PageContext) getJspContext();
			JspWriter out = jsp.getOut();
			
			
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

			out.println("<input type='submit' value='Enviar'/>");
			out.println("</form>");
			
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e.getCause());
		}
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
