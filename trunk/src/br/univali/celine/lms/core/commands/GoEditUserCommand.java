package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lmsscorm.User;

public class GoEditUserCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = LMSControl.getInstance().findUser(request.getParameter("name"));
		request.getSession().setAttribute("editUser", user);
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}
	
}
