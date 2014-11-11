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
package br.univali.celine.lms.core;

import java.io.File;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.utils.LMSLogger;

public class LMSContextListener implements ServletContextListener {

	private ServletContext context;

	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		context = servletContextEvent.getServletContext();
		try {
			LMSConfig.buildConfig(context);
			
			String coursesFolder = LMSConfig.getInstance().getCompleteCoursesFolder();
			if (coursesFolder.startsWith("file:")) {
				coursesFolder = (new URL(coursesFolder)).getFile();
			}
			File f = new File(coursesFolder);
			if (f.exists() == false && f.mkdir() == false) {
					throw new Exception("Dont succedd to create the courses folder.");
			}
			
									
			// TODO: implementar a exclusao
			/*
			 * - Precisa verificar se tem algum usuário conectado
			 * - Marque como nao podendo mais ninguem entrar ou se registrar
			 * - quando nao houverem mais usuarios conectados, entao exclua 
			 * 
			 */
			
			
		} catch (Exception e) {
			LMSLogger.throwing(e);
		}
	}	
	

	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

}
