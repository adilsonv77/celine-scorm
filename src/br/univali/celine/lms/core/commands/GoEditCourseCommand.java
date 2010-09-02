package br.univali.celine.lms.core.commands;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.Command;


public class GoEditCourseCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("courseId", request.getParameter("courseId"));
		
		RequestDispatcher rd = request.getRequestDispatcher(request.getParameter("nextURL"));
		rd.forward(request, response);
		
		return null;
		
	}
	
}
