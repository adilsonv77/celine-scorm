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
package br.univali.celine.lms.rte;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import br.univali.celine.lms.UserAdministration;
import br.univali.celine.lms.core.DataModelListener;
import br.univali.celine.scorm.rteApi.API;
import br.univali.celine.scorm.sn.model.NodeTableOfContent;

/**
 * Início desse projeto: 23/7/7
 * Objetivo: SBIE/2008
 * 
 * Essa classe é utilizada com a técnica AJAX, onde das páginas do lado cliente é feita
 * a comunicação para esse objeto
 * 
 * @author Adilson Vahldick
 *
 */

public class APIInstance {

	private Logger log = Logger.getLogger("global");
	
	private API loadAPI(HttpServletRequest request) {
		
		API api = (API) request.getSession().getAttribute(API.APIKEY);
		return api;

	}
	
	public String doInitialize(HttpServletRequest request, String param) {

		return String.valueOf(loadAPI(request).initialize(param));
		
	}
	
	public String doTerminate(HttpServletRequest request, String param) {

		return String.valueOf(loadAPI(request).terminate(param));
		
	}
	
	public String doGetValue(HttpServletRequest request, String nomeDataModel) {
		
		return loadAPI(request).getValue(nomeDataModel);
		
	}

	public String doSetValue(HttpServletRequest request, String nomeDataModel, String valor) {
		
		return String.valueOf(loadAPI(request).setValue(nomeDataModel, valor));
	}
	
	public String doCommit(HttpServletRequest request, String param) {

		return String.valueOf(loadAPI(request).commit(param));
		
	}
	
	public int doGetLastError(HttpServletRequest request) {
		
		return loadAPI(request).getLastError();
		
	}
	
	public String doGetErrorString(HttpServletRequest request, String param) {

		return loadAPI(request).getErrorString(param);
		
	}
	
	public String doGetDiagnostic(HttpServletRequest request, String param) {

		return loadAPI(request).getDiagnostic(param);
		
	}
	
	public boolean doHasNavRequest(HttpServletRequest request) {
		
		return loadAPI(request).hasNavRequest();
		
	}
	
	public NodeTableOfContent doGetTOC(HttpServletRequest request) {
		return loadAPI(request).getTOC();
	}
	
	public boolean doNeedsReloadTOC(HttpServletRequest request) {
		return loadAPI(request).needsReloadTOC();
	}
	
	public boolean isMustRecreateTree(HttpServletRequest request) {
		
		boolean ret = DataModelListener.getInstance().isMustRecreateTree(UserAdministration.getUser(request));
		log.info("APIInstance.isMustRecreateTree : " + ret);
		return ret;
	}
	
	public boolean isTerminated(HttpServletRequest request) {
		return loadAPI(request).isTerminated();
	}
	
	public boolean processRequest(HttpServletRequest request, String param) {
		return loadAPI(request).processRequest(param);
	}
}
