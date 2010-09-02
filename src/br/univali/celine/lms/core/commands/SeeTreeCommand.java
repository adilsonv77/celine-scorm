package br.univali.celine.lms.core.commands;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.univali.celine.lms.core.Command;
import br.univali.celine.scorm.rteApi.*;
import br.univali.celine.scorm.sn.model.NodeTableOfContent;
import br.univali.celine.scorm.sn.model.TableOfContents;
import br.univali.celine.scorm.sn.model.TableOfContentsFactory;

public class SeeTreeCommand implements Command {

	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIImplementation api = (APIImplementation) request.getSession().getAttribute(API.APIKEY);
		TableOfContents toc = TableOfContentsFactory.build(api.getActivityTree());
		StringBuilder retorno = new StringBuilder();

		retorno.append("<html><head>");
		retorno.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/scorm.css\">");
		retorno.append("<script src='lms?action=file&name=ftiens4.js'></script>\n");
		retorno.append("<script src='lms?action=file&name=myapiwrapper.js'></script>\n");
		retorno.append("<script type='text/javascript' src='lms?action=file&name=toc.js'></script>\n");
		retorno.append("<script src='lms?action=file&name=ua.js'></script>\n");
		retorno.append("<script>function loadPage(page){\n" +
				"getAPI().loadTOC = false; \n"+
				"if (getAPI().Terminated() == false) {\n"+
				"storeDataValue( 'adl.nav.request', '{target='+page+'}choice' );\n" +
				"window.parent.frames[2].location = \"lms?action=file&name=nothing.htm\";\n" +
				"getAPI().GetErrorString(); \n"+ // para comer tempo !!! a action da linha acima pode nao ter sido processada quando pega o TOC a seguir
				"} else {\n"+
				"getAPI().ProcessRequest(page); \n"+
				"window.parent.frames[2].location = \"lms?action=navrequest\";\n"+
				"}\n"+
				"var toc = getAPI().GetTOC();\n"+
				"if (getAPI().NeedsRecreateTree()) { createTOC(document, toc); }\n else "+
				"atualizarTOC(document, toc);\n"+
				"getAPI().loadTOC = true; \n"+
				"}\n");
		retorno.append("getWindowMasterParent().loadPage = loadPage;\n");
		retorno.append("getWindowMasterParent().storeDataValue = storeDataValue;\n");
		retorno.append("getWindowMasterParent().terminateCommunication = terminateCommunication;\n");
		retorno.append(
				"USETEXTLINKS=1;STARTALLOPEN=1;HIGHLIGHT=0;PRESERVESTATE=1;USEFRAMES=0;"+
				"USEICONS = 0;BUILDALL = 1;PRESERVESTATE = 0;\n");
		//retorno.append("ICONPATH=\"tree/\";");
		retorno.append("ICONPATH=\"lms?action=file&name=\";");
		retorno.append("var arvore = gFld('"+toc.getRoot().getName()+"', null, 'treeID');\narvore.treeID = \"curso\";\n");
		retorno.append("window.parent.foldersTree = foldersTree;\n");
		
		retorno.append("var niveis = new Array();\nniveis.push(arvore);\nvar nivelBase=arvore;");
		
		for (NodeTableOfContent child:toc.getRoot().getChildren()) {
			printNodeTOC(retorno, child);
		}

		retorno.append("if (elemAtivo != null) {\n" +
				"var totalScroll = 0;\n " +
				"while (elemAtivo != null) {\n"+
				" if (elemAtivo.tagName == 'BODY') break;\n "+
				" totalScroll = totalScroll + elemAtivo.scrollHeight;\n"+
				"elemAtivo = elemAtivo.parentNode;\n"+
				"} \n" +
				"window.scroll(0, totalScroll);" +
				"}");
	
		retorno.append("</script>\n\n");

		retorno.append("<script type='text/javascript'>\n\n");
		
		retorno.append("var frameHidden = false;\n");
		retorno.append("var mainFrame = parent.document.getElementById('courseFrameset');\n\n");
		retorno.append("var firstTime = true;");

		retorno.append("function showFrame() {\n\n");
		retorno.append("var link = document.getElementById('linkTree');\n");
		retorno.append("var linkImage = document.getElementById('show_hide_tree_icon');\n");
		retorno.append("if (frameHidden) {\n\n");		
		retorno.append("mainFrame.setAttribute('cols', '0, 250, *', 0);\n");
		retorno.append("frameHidden = false;\n\n");
		retorno.append("link.href = 'javascript:hideFrame();';\n");
		retorno.append("linkImage.src='lms?action=file&name=hide_tree.png';\n");
		retorno.append("linkImage.title='Hide Activity Tree';\n");		
		retorno.append("initializeDocument(arvore);\n");
		retorno.append("var elemAtivo = atualizarTOC(document, getAPI().GetTOC());\n");
		retorno.append("}\n");
		retorno.append("}\n");		
		
		retorno.append("function hideFrame() {\n\n");
		retorno.append("var link = document.getElementById('linkTree');\n");	
		retorno.append("var linkImage = document.getElementById('show_hide_tree_icon');\n");		
		retorno.append("if (!frameHidden) {\n");
		retorno.append("mainFrame.setAttribute('cols', '0, 50, *', 0);\n");
		retorno.append("frameHidden = true;\n\n");
		retorno.append("var domDiv = document.getElementById('domRoot');\n");
		retorno.append("domDiv.innerHTML = '';\n");
		retorno.append("link.href='javascript:showFrame();';\n");
		retorno.append("linkImage.src='lms?action=file&name=show_tree.png';\n");		
		retorno.append("linkImage.title='Show Activity Tree';\n");
		retorno.append("}\n");
		retorno.append("}\n\n");

		retorno.append("</script>\n\n");
		retorno.append("</head>");
		retorno.append("<body>\n");
		retorno.append("<center><a href='javascript:hideFrame();' id='linkTree'><img id='show_hide_tree_icon' " +
				"src='lms?action=file&name=hide_tree.png' border='0' title='Hide Activity Tree'></img></a></center><br/><br/>");
		retorno.append("<div id='domRoot'>&nbsp;");
		retorno.append("</div>");
		retorno.append("<script>");
		
		retorno.append("initializeDocument(arvore);\n");
		retorno.append("var elemAtivo = atualizarTOC(document, getAPI().GetTOC());\n");
		retorno.append("var domDiv = document.getElementById('domRoot');\n");
		retorno.append("BUILDALL = 0;\n");
		retorno.append("</script>");
		retorno.append("</body>");
		retorno.append("</html>");
		
		return retorno.toString();
	}

	private void printNodeTOC(StringBuilder retorno, NodeTableOfContent node) {
		
		String id = node.getIdLearningActivity();
		
		if (node.getChildren().size() == 0) {
			retorno.append("insDoc(niveis[niveis.length-1], gLnk(\"T\", \""+node.getName()+"\", \"javascript:loadPage('"+id+"');\",\""+id+"\"));\n");
		} else {
			retorno.append("nivelBase = insFld(niveis[niveis.length-1], gFld(\""+node.getName()+"\", \"javascript:loadPage('"+id+"');\",\""+id+"\"));\n");
			retorno.append("niveis.push(nivelBase);\n");
		}
		
		for (NodeTableOfContent child:node.getChildren()) {
			printNodeTOC(retorno, child);
		}
		if (node.getChildren().size() > 0) {
			retorno.append("niveis.pop();\n");
		}
		
	}

}
