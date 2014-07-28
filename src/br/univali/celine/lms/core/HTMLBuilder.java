package br.univali.celine.lms.core;

public class HTMLBuilder {

	public static String buildFrame(String pag2ndFrame, String pag3rdFrame) {
		// carrega a segunda frame só depois de carregar a primeira
		// assim garante criar e carregar o objeto de API antes de carregar a pagina
		return "<html>" +
		        "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">"+
				"<frameset id=\"courseFrameset\" cols=\"0, 250, *\" border=\"0\" frameborder=\"no\">" +
			   	"<frame src=\"lms?action=file&name=apiinstance.html\" " +
			   		"onload=\"window.frames[1].location='"+pag2ndFrame+"';" +
			   				 "window.frames[2].location='"+pag3rdFrame+"'; \"/>" +
				"<frame src=\"\" />" +
				"<frame name=\"basefrm\" src=\"\"/>" +
			   "</frameset></html>";
		
	}
	
	public static String buildRedirect(String url) {
		return "<html><body><script>document.location.href='"+url+"';</script></body></html>";
	}
	
}
