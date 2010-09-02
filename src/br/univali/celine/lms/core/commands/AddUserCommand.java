package br.univali.celine.lms.core.commands;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lms.utils.LMSLogger;

public class AddUserCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			UserImpl user = new UserImpl();
			user.setName(request.getParameter("name"));
			user.setPassw(request.getParameter("passw"));
			user.setAdmin(Boolean.parseBoolean(request.getParameter("admin")));			

			LMSControl control = LMSControl.getInstance(); 
			control.insertUser(user);
			user = (UserImpl) control.findUser(user.getName());
			
						
		} catch (Exception e) {

			LMSLogger.throwing(e);
			request.setAttribute("error", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher(request.getParameter("thisURL"));
			rd.forward(request, response);
			
		}
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
		
	}
	
	
}
