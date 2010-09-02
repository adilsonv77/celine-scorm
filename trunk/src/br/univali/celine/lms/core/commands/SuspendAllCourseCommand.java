package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.rteApi.API;

public class SuspendAllCourseCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {


		API api = (API) request.getSession().getAttribute(API.APIKEY);
		
		if (api.getActivityTree().getCurrentActivity().isActive()) {
			api.setValue(Request.name, "suspendAll");
		} else {
			api.setValue(Request.name, "exitAll"); // não destrói o track model !!
		}
		
		// api.terminate("");// é responsabilidade do SCO terminar a comunicacao !!!!
		
		//request.getSession().removeAttribute(API.APIKEY); nao pode tirar da sessao pois ainda sao feitas chamadas depois de suspend
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}

}
