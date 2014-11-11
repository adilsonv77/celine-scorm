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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class FileHandler {

	private static final String folder = "/br/univali/celine/lms/";
	private String content; 

	public FileHandler(HttpServletRequest request, String fileName) throws IOException {

		InputStream inputStream = getClass().getResourceAsStream(folder + fileName);
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		
		StringBuilder sb = new StringBuilder();
        while (true)
        {
            String line = in.readLine();
            if (line == null)
            {
                break;
            }
            
            if (line.contains("CONTEXTPATH")) {
            	line = line.replaceFirst("CONTEXTPATH", request.getContextPath());
            }
            
            sb.append("\n" + line);
            
        }
        
        this.content = sb.toString();
		
	}

	public String getContent() { return this.content; }
}
