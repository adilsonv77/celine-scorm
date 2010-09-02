package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.univali.celine.lms.UserAdministration;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;


public class RegisterCourseCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LMSControl.getInstance().registerUserCourse(UserAdministration.getUser(request), request.getParameter("courseId"));
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}

}
