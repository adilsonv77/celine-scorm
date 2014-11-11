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
