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
package br.univali.celine.lms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class MultipartRequestProcessor {

	private Map<String, Object> parameters;
	private ArrayList<FileItem> files;
	private static MultipartRequestProcessor instance = null;
	private ProgressListener progressListener;
	
	private MultipartRequestProcessor() {
		

		
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public boolean hasFiles() {
		
		if ((files != null) && (files.size() > 0))
			return true;
		
		return false;		
	}
	
	public boolean hasParameters() {
		
		if ((parameters != null) && (parameters.size() > 0))
			return true;
		
		return false;
		
	}
	
	
	public static MultipartRequestProcessor getInstance() {
	
		if (instance == null)
			instance = new MultipartRequestProcessor();
		
		return instance;
		
	}
	

	public void processRequest(HttpServletRequest request) {
		
		try {
			
			parameters = new HashMap<String, Object>();
			files = new ArrayList<FileItem>();
		
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(dfif);
			
			upload.setProgressListener(progressListener);
			List<?> items = upload.parseRequest(request);
			Iterator<?> iter = items.iterator();	
			
			while (iter.hasNext()) {			
			    	
				FileItem item = (FileItem) iter.next();
	
				if (item.isFormField())				
					parameters.put(item.getFieldName(), item.getString());
	
				else files.add(item);			
			}
			
		} catch (Exception e) {

			LMSLogger.throwing(e);
			
		}

	}
	
	
	public FileItem getNextFile() {
		
		if (files.size() > 0) {
			
			FileItem file = files.get(0);
			files.remove(0);
			
			return file;		
		}
		
		return null;
		
	}
	
	public String getParameter(String paramName, boolean delete) {
		
		if (parameters.containsKey(paramName)) {
		
			String paramValue = (String) parameters.get(paramName);
		/* Porque excluir ?	
			if (delete)
				parameters.remove(paramName);
		*/	
			return paramValue;
		}
		
		return null;
		
	}
	
	public int getParamCount() {
		
		return parameters.size();
		
	}
	
	public void clearParameters() {
		
		parameters.clear();
		
	}
	
	public void clearFiles() {
		
		files.clear();
		
	}
	
	public void clearAll() {
		
		files.clear();
		parameters.clear();		
		
	}
	
	public void setProgressListener(ProgressListener progressListener) {
		this.progressListener = progressListener;
	}
	
}
