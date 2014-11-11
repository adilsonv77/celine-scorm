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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.utils.LMSLogger;

public class LMSFilter implements Filter {

	private ServletContext svlCtxt;
	private Logger logger = Logger.getLogger("global"); 

	public void init(FilterConfig filterConfig) throws ServletException {
		svlCtxt = filterConfig.getServletContext();
		
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		boolean encadear = true;
		try {
			if (request.getRequestURI().contains("$$$$")) {
				
				String uri = request.getRequestURI();
				String resource = uri.substring(uri.indexOf("$$$$") + 4);
				File f = new File(myURIEncoder(LMSConfig.getInstance().getCoursesFolder()+ resource));

				// logger.info(f.toString());
				
				FileInputStream fio = new FileInputStream(f);
				byte b[] = new byte[(int) f.length()];
				fio.read(b);
				fio.close();

				servletResponse.getOutputStream().write(b);
				servletResponse.setContentType(svlCtxt.getMimeType(f.toString()));
				encadear = false;
			}
		} catch (Exception e) {
			LMSLogger.throwing(e);
			servletResponse.getWriter().print("<html><body><h1>Error</h1>" + e + "</body></html>");
			return;
		}
		
		logger.info(request.getRequestURL().toString());
		if (encadear)
			chain.doFilter(servletRequest, servletResponse);
		
		String servletPath = request.getServletPath();
		boolean naoEhLMS = servletPath == null || (!servletPath.equals("/lms"));
		String type = servletResponse.getContentType();
		logger.info(type);
		if (naoEhLMS && type != null && type.equals("text/html")) {
			/*
			try {
				servletResponse.getWriter().print("<script>changeOnLoad(window);</script>");
			} catch (IllegalStateException e) {
				servletResponse.getOutputStream().print("<script>changeOnLoad(window);</script>");
			}
			*/
		}
	}

	private URI myURIEncoder(String s) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
		
		URL url = new URL(s);
		File f;
		try {
			f = new File(url.toURI());
		} catch (Exception e) {
			f = new File(url.getFile());
		}

		return f.toURI();
/*		
		URL url = null;
		try {
			url = new URL(s);
			return url.toURI();
		} catch (Exception e) {
			
			s = URLEncoder.encode( s , "UTF8" );
			int i = 0;
			while ((i = s.indexOf("%2F")) >= 0) {
				s = s.substring(0, i) + "/" + s.substring(i+3); 
			}
			
			while ((i = s.indexOf("%3A")) >= 0) {
				s = s.substring(0, i) + ":" + s.substring(i+3); 
			}

			while ((i = s.indexOf("%")) >= 0) {
				s = s.substring(0, i) + " " + s.substring(i+1); 
			}
			
			url = new URL(s);
		}
		return url.toURI();
		*/
	}
	
	public void destroy() {
	}

}
