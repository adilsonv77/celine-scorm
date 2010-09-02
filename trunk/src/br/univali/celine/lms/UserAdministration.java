package br.univali.celine.lms;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.User;

public class UserAdministration {

	private static Logger log = Logger.getLogger("global");
	
	public static UserImpl getUser(HttpServletRequest request) {
		UserImpl user = (UserImpl) request.getSession().getAttribute(UserImpl.USER);
		if (user == null) {
			log.info("USER é null");
		}
		return user;
	}
	
	public static void setUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(UserImpl.USER, user);
	}
	
}
