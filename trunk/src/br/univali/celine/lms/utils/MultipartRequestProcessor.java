package br.univali.celine.lms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class MultipartRequestProcessor {

	private HashMap<String, Object> parameters;
	private ArrayList<FileItem> files;
	private static MultipartRequestProcessor instance = null;
	private ProgressListener progressListener;
	
	private MultipartRequestProcessor() {
		

		
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
	

	@SuppressWarnings("unchecked")
	public void processRequest(HttpServletRequest request) {
		
		try {
			
			parameters = new HashMap<String, Object>();
			files = new ArrayList<FileItem>();
		
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(dfif);
			
			upload.setProgressListener(progressListener);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();	
			
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
			
			if (delete)
				parameters.remove(paramName);
			
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
