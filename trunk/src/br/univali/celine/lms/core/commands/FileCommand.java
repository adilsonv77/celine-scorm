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

public class FileCommand implements Command {

	@Override
	public String executar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileName = request.getParameter("name");

		if (fileName.endsWith(".gif") || fileName.endsWith(".png") ) {
			
			if (fileName.endsWith(".gif")) {
				response.setContentType("image/gif");
			} else {
				response.setContentType("image/png");
			}
			
			
			BinaryFileHandler bin = new BinaryFileHandler(request, fileName);
			byte[] file = bin.getContent();
			
			response.setContentLength(file.length);
			response.setBufferSize(file.length);
			
			response.getOutputStream().write(file);
			response.getOutputStream().flush();
			
			response.flushBuffer();
			response.getOutputStream().close();
			
			return null;
			
		} else {
		
			FileHandler fh = new FileHandler(request, fileName);
			if (fileName.endsWith(".js")) {
				response.setContentType("text/javascript");
			} else {
				response.setContentType("text/html");
			}
			return fh.getContent();
		}
	}

}
