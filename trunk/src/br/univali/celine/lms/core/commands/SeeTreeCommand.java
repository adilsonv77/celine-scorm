package br.univali.celine.lms.core.commands;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.univali.celine.lms.core.Command;

public class SeeTreeCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		StringBuilder retorno = new StringBuilder();

		retorno.append("<html>");
			retorno.append("<head>");
				retorno.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">");
				//retorno.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/scorm.css\">");
				retorno.append("<script type='text/javascript' src='lms?action=file&name=ftiens4.js'></script>\n");
				retorno.append("<script type='text/javascript' src='lms?action=file&name=myapiwrapper.js'></script>\n");
				retorno.append("<script type='text/javascript' src='lms?action=file&name=toc.js'></script>\n");
				retorno.append("<script type='text/javascript' src='lms?action=file&name=ua.js'></script>\n");
				retorno.append("<script type='text/javascript' src='lms?action=file&name=celinetoc.js'></script>\n");
			
			retorno.append("</head>");

			retorno.append("<body>");
				retorno.append("<center>");
					retorno.append("<a href='javascript:hideFrame();' id='linkTree'>");
						retorno.append("<img id='show_hide_tree_icon' src='lms?action=file&name=hide_tree.png' border='0' title='Hide Activity Tree'/>");
					retorno.append("</a>");
				retorno.append("</center>");
				retorno.append("<div id='domRoot'>&nbsp;");
				retorno.append("</div>");
			
				retorno.append("<script>");
				retorno.append("initializeCelineTOC();\n");
				retorno.append("</script>");
			retorno.append("</body>");
		retorno.append("</html>");
		
		return retorno.toString();
	}

}
