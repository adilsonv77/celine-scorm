package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.rteApi.API;

public class AbandonAllCourseCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {


		API api = (API) request.getSession().getAttribute(API.APIKEY);
		api.setValue(Request.name, "abandonAll");
		api.terminate("");
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}

}
