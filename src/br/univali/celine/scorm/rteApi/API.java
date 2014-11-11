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
