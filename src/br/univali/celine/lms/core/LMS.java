package br.univali.celine.lms.core;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.commands.AbandonAllCourseCommand;
import br.univali.celine.lms.core.commands.AddUserCommand;
import br.univali.celine.lms.core.commands.DeleteCourseCommand;
import br.univali.celine.lms.core.commands.DeleteUserCommand;
import br.univali.celine.lms.core.commands.ExitAllCourseCommand;
import br.univali.celine.lms.core.commands.FileCommand;
import br.univali.celine.lms.core.commands.GoEditCourseCommand;
import br.univali.celine.lms.core.commands.GoEditUserCommand;
import br.univali.celine.lms.core.commands.ImportCourseCommand;
import br.univali.celine.lms.core.commands.InsertDerivedCourseCommand;
import br.univali.celine.lms.core.commands.LoginCommand;
import br.univali.celine.lms.core.commands.NavRequestCommand;
import br.univali.celine.lms.core.commands.OpenCourseCommand;
import br.univali.celine.lms.core.commands.RegisterCourseCommand;
import br.univali.celine.lms.core.commands.SeeCourseCommand;
import br.univali.celine.lms.core.commands.SeeTreeCommand;
import br.univali.celine.lms.core.commands.SuspendAllCourseCommand;
import br.univali.celine.lms.core.commands.UnregisterCourseCommand;
import br.univali.celine.lms.core.commands.UpdateUserCommand;
import br.univali.celine.lms.utils.LMSLogger;

public class LMS extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Command> comandos = new HashMap<String, Command>();

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		comandos.put("login", new LoginCommand());

		comandos.put("opencourse", new OpenCourseCommand());
		comandos.put("exitallcourse", new ExitAllCourseCommand());
		comandos.put("suspendallcourse", new SuspendAllCourseCommand());
		comandos.put("abandonallcourse", new AbandonAllCourseCommand());

		comandos.put("deletecourse", new DeleteCourseCommand());
		comandos.put("registercourse", new RegisterCourseCommand());
		comandos.put("unregistercourse", new UnregisterCourseCommand());
		comandos.put("importcourse", new ImportCourseCommand());
		comandos.put("goeditcourse", new GoEditCourseCommand());

		comandos.put("navrequest", new NavRequestCommand());
		comandos.put("seetree", new SeeTreeCommand());

		comandos.put("adduser", new AddUserCommand());
		comandos.put("deleteuser", new DeleteUserCommand());
		comandos.put("updateuser", new UpdateUserCommand());
		comandos.put("goedituser", new GoEditUserCommand());
		comandos.put("seecourse", new SeeCourseCommand());
		;
		comandos.put("insertderivedcourse", new InsertDerivedCourseCommand());

		comandos.put("file", new FileCommand());

		try {
			loadUserCommand(servletConfig);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		// Build20043rdEdition.build();
		// Build20044thEdition.build();
	}

	@SuppressWarnings("rawtypes")
	private void loadUserCommand(ServletConfig servletConfig) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Enumeration pm = servletConfig.getServletContext().getInitParameterNames();
		while (pm.hasMoreElements()) {
			String actionName = (String)pm.nextElement();
			if (actionName.startsWith("lms_")) {
				String className = servletConfig.getServletContext().getInitParameter(actionName);
				
				Class clazz = Class.forName(className);
				comandos.put(actionName.substring(4), (Command)(clazz.newInstance()));
			}
		}
		
	}

	private static Logger logger = Logger.getLogger("global");

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String pagRetorno = "";

		if (!action.equals("file"))
			logger.info("LMS : " + action);

		Command comm = comandos.get(action);

		try {
			pagRetorno = comm.executar(request, response);
			
			if (pagRetorno == null) { // já foi tudo tratdo no command
				return;
			}
			
			response.setContentType("text/html");
		} catch (Exception e) {

			LMSLogger.throwing(e);
			pagRetorno = "<html><body><h1>Error</h1>" + e + "</body></html>";

		}
		response.getWriter().println(pagRetorno);
	}
}
