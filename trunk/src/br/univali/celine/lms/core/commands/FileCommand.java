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
