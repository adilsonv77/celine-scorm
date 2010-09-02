package br.univali.celine.lms.core.commands;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.core.Command;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.rteApi.API;
import br.univali.celine.scorm.rteApi.APIImplementation;

public class NavRequestCommand implements Command {

	private Logger logger = Logger.getLogger("global");
	
	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APIImplementation api = (APIImplementation) request.getSession().getAttribute(API.APIKEY);
		api.removeDataElement(Request.name);
		
		String resource = LMSConfig.getInstance().getCourseFolder(api.getCourseFolder())+api.getResource();
		logger.info("NavRequestCommand : " + resource);
		
		return 	"<script>document.location.href=\"" + resource + "\";</script>";
	}

}
