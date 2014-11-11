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
package br.univali.celine.lms.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import br.univali.celine.scorm.rteApi.API;
import br.univali.celine.scorm.rteApi.APIImplementation;
import br.univali.celine.scorm.sn.model.NodeTableOfContent;
import br.univali.celine.scorm.sn.model.TableOfContents;
import br.univali.celine.scorm.sn.model.TableOfContentsFactory;

public class CourseTreeTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
	
		JspWriter out = getJspContext().getOut();
		PageContext pageContext = (PageContext) getJspContext();
		
		APIImplementation api = (APIImplementation) pageContext.getSession().getAttribute(API.APIKEY);
		TableOfContents toc = TableOfContentsFactory.build(api.getActivityTree());
		StringBuilder retorno = new StringBuilder();		
				
		retorno.append("<script src='lms?action=file&name=ftiens4.js'></script>\n");
		retorno.append("<script src='lms?action=file&name=myapiwrapper.js'></script>\n");
		retorno.append("<script type='text/javascript' src='lms?action=file&name=toc.js'></script>\n");
		retorno.append("<script src='lms?action=file&name=ua.js'></script>\n");		
		retorno.append("<script type='text/javascript' src='dwr/interface/API_1484_11.js'></script>");
		retorno.append("<script type='text/javascript' src='dwr/engine.js'></script>");
		
		
		retorno.append("<script>\n");                                
		retorno.append("window.parent.API_1484_11 = API_1484_11;\n");
		retorno.append("API_1484_11.loadTOC = true;\n");                                                              
		retorno.append("API_1484_11.Initialize = function(p0) {\n");                                     
		retorno.append("var ret;\n");                                                            
		retorno.append("API_1484_11.doInitialize(p0,  {\n");                                         
		retorno.append("callback:function(_ret){ret = _ret;},\n");                       
		retorno.append("async:false\n");                                                 
		retorno.append("});\n");                                                                 
		retorno.append("return ret;\n");                                                         
		retorno.append("}\n");                                                      
		retorno.append("API_1484_11.Terminate = function(p0) {\n");                                      
		retorno.append("var ret;\n");                                                            
		retorno.append("API_1484_11.doTerminate(p0,  {\n");                                          
		retorno.append("callback:function(_ret){ret = _ret;},\n");                       
		retorno.append("async:false\n");                                                 
		retorno.append("});\n");                                                    
		retorno.append("if (API_1484_11.HasNavRequest()) {\n");
		retorno.append("var link = 'CONTEXTPATH/lms?action=navrequest';\n");
		retorno.append("window.parent.frames[2].location = link;\n");
		retorno.append("if (API_1484_11.loadTOC == true)\n"); 
		retorno.append("atualizarTOC(window.parent.frames[1].document,API_1484_11.GetTOC());\n");
		retorno.append("}\n");
		retorno.append("return ret;\n");
		retorno.append("}\n");                                                     
		retorno.append("API_1484_11.Commit = function(p0) {\n");                                        
		retorno.append("var ret;\n");
		retorno.append("API_1484_11.doCommit(p0,  {\n");
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");                                                                
		retorno.append("return ret;\n");                                                        
		retorno.append("}\n");
		retorno.append("API_1484_11.GetValue = function(p0) {\n");                                      
		retorno.append("var ret;\n");                                                           
		retorno.append("API_1484_11.doGetValue(p0,  {\n");                                          
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");                                                                
		retorno.append("return ret;\n");                                                        
		retorno.append("}\n");                                                     
		retorno.append("API_1484_11.SetValue = function(p0, p1) {\n");                                  
		retorno.append("var ret;\n");                                                           
		retorno.append("API_1484_11.doSetValue(p0, p1, {\n");                                       
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");
		retorno.append("if (API_1484_11.loadTOC == true) {\n"); 
		retorno.append("if (API_1484_11.NeedsReloadTOC()) {\n"); 
		retorno.append("atualizarTOC(window.parent.frames[1].document,API_1484_11.GetTOC());\n");
		retorno.append("}\n");
		retorno.append("}\n");                     
		retorno.append("return ret;\n");                                                        
		retorno.append("}\n");                                                     
		retorno.append("API_1484_11.GetLastError = function() {\n");
		retorno.append("var ret;\n");
		retorno.append("API_1484_11.doGetLastError( {\n");                                          
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");                                                                
		retorno.append("return ret;\n");                                                        
		retorno.append("}\n");
		retorno.append("API_1484_11.GetErrorString = function(p0) {\n");                                
		retorno.append("var ret;\n");                                                           
		retorno.append("API_1484_11.doGetErrorString(p0, {\n");                                     
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");
		retorno.append("return ret;\n");                                                        
		retorno.append("}\n");                                                     
		retorno.append("API_1484_11.GetDiagnostic = function(p0) {\n");                                 
		retorno.append("var ret;\n");                                                           
		retorno.append("API_1484_11.doGetDiagnostic(p0, {\n");                                      
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");                                                                
		retorno.append("return ret;\n");                                                        
		retorno.append("}\n");                                                     
		retorno.append("API_1484_11.HasNavRequest = function() {\n");                                   
		retorno.append("var ret;\n");                                                           
		retorno.append("API_1484_11.doHasNavRequest( {\n");                                         
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");                                                                
		retorno.append("return ret;\n");
		retorno.append("}\n");
		retorno.append("API_1484_11.NeedsReloadTOC = function() {\n");                                   
		retorno.append("var ret;\n");                                                           
		retorno.append("API_1484_11.doNeedsReloadTOC( {\n");                                         
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");                               
		retorno.append("return ret;\n");
		retorno.append("}\n");
		retorno.append("API_1484_11.GetTOC = function() {\n");                                    
		retorno.append("var ret;\n");
		retorno.append("API_1484_11.doGetTOC( {\n");
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");
		retorno.append("return ret;\n");                                           
		retorno.append("}\n");
		retorno.append("API_1484_11.NeedsRecreateTree = function() {\n");                                    
		retorno.append("var ret;\n");
		retorno.append("API_1484_11.isMustRecreateTree( {\n");
		retorno.append("callback:function(_ret){ret = _ret;},\n");                      
		retorno.append("async:false\n");                                                
		retorno.append("});\n");
		retorno.append("return ret;\n");                                           
		retorno.append("}\n");
		retorno.append("</script>\n");		
		
		
		
		retorno.append("<script>function loadPage(page){\n" +
				"storeDataValue( 'adl.nav.request', '{target='+page+'}choice' );\n" +
				"getAPI().loadTOC = false; \n"+
				"terminateCommunication();\n"+
				"var toc = getAPI().GetTOC();\n"+
			//	"if (getAPI().NeedsRecreateTree()) { createTOC(document, toc); }\n else "+
				"atualizarTOC(document, toc);\n"+
				"getAPI().loadTOC = true; \n"+
				"}\n\n");

		retorno.append("getWindowMasterParent().loadPage = loadPage;\n\n");
		retorno.append("getWindowMasterParent().storeDataValue = storeDataValue;\n\n");
		retorno.append("getWindowMasterParent().terminateCommunication = terminateCommunication;\n\n");
		retorno.append(
				"USETEXTLINKS=1;STARTALLOPEN=1;HIGHLIGHT=0;PRESERVESTATE=1;ICONPATH=\"tree/\";USEFRAMES=0;"+
				"USEICONS = 0;BUILDALL = 1;PRESERVESTATE = 0;\n\n");
		retorno.append("var arvore = gFld('"+toc.getRoot().getName()+"', null, 'treeID');\narvore.treeID = \"curso\";\n\n");
		//retorno.append("window.parent.foldersTree = foldersTree;\n\n");
		
		retorno.append("var niveis = new Array();\nniveis.push(arvore);\nvar nivelBase=arvore;\n");

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
				"}\n");
		retorno.append("</script>\n");
		
		retorno.append("<script>\n");		
		retorno.append("function refresh() {\n");
		retorno.append("atualizarTOC(document, getAPI().GetTOC());\n");
		retorno.append("}\n");
		retorno.append("function scheduleRefresh() {\n");
		retorno.append("setInterval('refresh()', 500);\n");
		retorno.append("}\n");
		retorno.append("</script>\n");
		
		retorno.append("<body onload='javascript:scheduleRefresh()'>\n\n");
		
		retorno.append("<div id=\"domRoot\"></div>\n");
		retorno.append("<script>\n");
		retorno.append("initializeDocument(arvore);\n\n");
		retorno.append("var elemAtivo = atualizarTOC(document, getAPI().GetTOC());\n\n");
		retorno.append("BUILDALL = 0;\n\n");
		retorno.append("</script>\n");
		retorno.append("</body>\n");

		out.println(retorno);
	
		super.doTag();		
		
	}

	private void printNodeTOC(StringBuilder retorno, NodeTableOfContent node) {
	
		String id = node.getIdLearningActivity();
		
		if (node.getChildren().size() == 0) {
			retorno.append("insDoc(niveis[niveis.length-1], gLnk(\"T\", \""+node.getName()+"\", \"javascript:loadPage('"+id+"');\",\""+id+"\"));\n\n");
		} else {
			retorno.append("nivelBase = insFld(niveis[niveis.length-1], gFld(\""+node.getName()+"\", \"javascript:loadPage('"+id+"');\",\""+id+"\"));\n\n");
			retorno.append("niveis.push(nivelBase);\n\n");
		}
		
		for (NodeTableOfContent child:node.getChildren()) {
			printNodeTOC(retorno, child);
		}
		if (node.getChildren().size() > 0) {
			retorno.append("niveis.pop();\n\n");
		}
	}
}
