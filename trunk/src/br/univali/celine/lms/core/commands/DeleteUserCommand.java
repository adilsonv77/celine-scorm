package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lms.utils.LMSLogger;

public class DeleteUserCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		try {

			UserImpl user = new UserImpl();
			user.setName(request.getParameter("name"));
			
			LMSControl control = LMSControl.getInstance();
			control.removeUser(user);
			
		} catch (Exception e) {
			LMSLogger.throwing(e);
		}
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}
	
}
