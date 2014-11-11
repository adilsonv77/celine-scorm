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
package br.univali.celine.ws.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import br.univali.celine.ws.core.CelineService;

public class CelineServiceServlet extends CXFNonSpringServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void loadBus(ServletConfig servletConfig) throws ServletException {
		super.loadBus(servletConfig);

		Bus bus = getBus();
		BusFactory.setDefaultBus(bus);
		Endpoint ep = Endpoint.publish("/celineservice", new CelineService());
		SOAPBinding binding = (SOAPBinding) ep.getBinding();
		binding.setMTOMEnabled(true);

	}
}
