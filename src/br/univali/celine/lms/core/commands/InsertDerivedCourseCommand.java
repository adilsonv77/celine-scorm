package br.univali.celine.lms.core.commands;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lms.integration.EasyContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackage;


public class InsertDerivedCourseCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String title = request.getParameter("title");
		String courseId = request.getParameter("courseId");
		
		LMSConfig lms = LMSConfig.getInstance();	
		EasyContentPackage easy = new EasyContentPackage();		
		easy.addContentPackage(lms.openContentPackageByFolderName("tudo"), "tudo");
		
		ContentPackage newCP = easy.build(title, courseId);
		LMSControl.getInstance().insertCourse(newCP);
								
		return HTMLBuilder.buildRedirect("selectaction.jsp");
	}
	
		
	
}
