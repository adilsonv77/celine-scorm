/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
