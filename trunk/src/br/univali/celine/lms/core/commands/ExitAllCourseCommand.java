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
package br.univali.celine.lms.core.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.rteApi.API;

public class ExitAllCourseCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		API api = (API) request.getSession().getAttribute(API.APIKEY);
		api.setValue(Request.name, "exitAll");
		//api.terminate(""); // é responsabilidade do SCO terminar a comunicacao !!!!
		
		//request.getSession().removeAttribute(API.APIKEY);
		
		return HTMLBuilder.buildRedirect(request.getParameter("nextURL"));
	}

}
