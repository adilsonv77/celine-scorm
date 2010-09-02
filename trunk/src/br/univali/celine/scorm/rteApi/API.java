package br.univali.celine.scorm.rteApi;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NodeTableOfContent;

public interface API {

	public static final String APIKEY = "CURRENTCOURSE";
	
	// these are SCORM functions
	boolean initialize(String parameter);
	boolean terminate(String parameter);
	String getValue(String dataModelElement);
	boolean setValue(String dataModelElement, String value);
	boolean commit(String parameter);
	int getLastError();
	String getErrorString(String errorCode);
	String getDiagnostic(String errorCode);
	
	// these are my utilitaries functions
	boolean hasNavRequest();
	boolean needsReloadTOC();
	NodeTableOfContent getTOC();
	String getCourseFolder();
	ActivityTree getActivityTree();
	boolean isTerminated();
	boolean processRequest(String page);
	
}
