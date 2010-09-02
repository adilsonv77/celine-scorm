package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;


public class DeleteCourseCommand implements Command {

	
	public String executar(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		String courseId = request.getParameter("courseId");

		LMSControl control = LMSControl.getInstance();
		control.markCourseAsRemoved(courseId);


		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}

}
