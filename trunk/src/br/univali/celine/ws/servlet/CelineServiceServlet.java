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
