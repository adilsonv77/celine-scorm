package br.univali.celine.lms.core.commands;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lmsscorm.User;

public class LoginCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LMSControl control = LMSControl.getInstance();
		User user = control.webLogin(request, request.getParameter("name"), request.getParameter("passw"));
		
		if (user != null) {
			Enumeration<?> ps = request.getParameterNames();
			String params = "";
			while (ps.hasMoreElements()) {
				String name = (String)ps.nextElement();
				params += "&" + name + "=" + request.getParameter(name);
				
			}
			params = params.substring(1);
			
			return HTMLBuilder.buildRedirect(request.getParameter("nextURL") + "?" + params);
		}

		
		return HTMLBuilder.buildRedirect(request.getParameter("thisURL"));
	}

}
