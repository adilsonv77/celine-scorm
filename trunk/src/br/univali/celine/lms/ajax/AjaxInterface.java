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
package br.univali.celine.lms.ajax;

import java.util.HashMap;


public class AjaxInterface {

	private static AjaxInterface instance = null;
	private static HashMap<String, Object> attributes;	
	
	private static final String UPLOAD_PROGRESS = "_uploadProgress";
	private static final String UPLOAD_STATUS = "_uploadStatus";
	

	private AjaxInterface() {
		
		attributes = new HashMap<String, Object>();		
	}
	
	public static AjaxInterface getInstance() {
	
		if (instance == null)
			instance = new AjaxInterface();
		
		return instance;		
	}
	
	
	private synchronized static Object getAttribute(String name) {
		
		if (attributes.containsKey(name))
			return attributes.get(name);
		
		return null;		
	}
	
	
	private synchronized static void setAttribute(String name, Object attribute) {
		
		removeAttribute(name);		
		attributes.put(name, attribute);		
	}
	
	
	private synchronized static void removeAttribute(String name) {
		
		if (attributes.containsKey(name))
			attributes.remove(name);		
	}
	
	
	
	public synchronized static double getUploadProgress(String userName) {
		
		Double doub;
		
		if ( (doub = (Double) getAttribute(userName + UPLOAD_PROGRESS)) == null)
			doub = 100.0;
		
		return doub;   		
	}
	
	public synchronized static int getUploadStatus(String userName) {
		
		Integer sint;
		
		if ( (sint = (Integer) getAttribute(userName + UPLOAD_STATUS)) == null)
			sint = 0;
		
		return sint;
	}
	
	
	public synchronized void updateStatus(String userName, int status) {
		
		setAttribute(userName + UPLOAD_STATUS, status);
		
	}
	
	public synchronized void removeStatus(String userName) {
		
		removeAttribute(userName + UPLOAD_STATUS);
	
	}
	
	public synchronized void updateProgress(String userName, double progress) {		
		
		setAttribute(userName + UPLOAD_PROGRESS, progress);		
	}
	
	
	public synchronized void removeProgress(String userName) {
		
		removeAttribute(userName + UPLOAD_PROGRESS);		
	}
	
	
}
