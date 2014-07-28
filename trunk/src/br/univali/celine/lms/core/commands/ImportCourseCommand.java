package br.univali.celine.lms.core.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.io.FilenameUtils;

import br.univali.celine.lms.ajax.AjaxInterface;
import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.core.Command;
import br.univali.celine.lms.core.HTMLBuilder;
import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lms.model.CourseImpl;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lms.utils.MultipartRequestProcessor;
import br.univali.celine.lms.utils.zip.Zip;
import br.univali.celine.lms.utils.zip.ZipListener;
import br.univali.celine.lmsscorm.User;


public class ImportCourseCommand implements Command, ZipListener, ProgressListener {

	private String userName;
	private Logger logger = Logger.getLogger("global");
	
	public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(UserImpl.USER);
		userName = user.getName();
		
		AjaxInterface ajaxInterface = AjaxInterface.getInstance();
		ajaxInterface.updateProgress(userName, 0.0);
		ajaxInterface.updateStatus(userName, 1);
		
		MultipartRequestProcessor mrp = MultipartRequestProcessor.getInstance();
		mrp.setProgressListener(this);
		mrp.processRequest(request);			
		
		String coursesFolder = LMSConfig.getInstance().getCompleteCoursesFolder();
		coursesFolder = coursesFolder.replaceAll("file:", "");
		String title = mrp.getParameter("title", true); // TODO: esse title nao deveria vir do formulario, mas ser extraido do contentpackage !!!
		String id = mrp.getParameter("id", true); // TODO: esse id nao deveria vir do formulario, mas ser extraido do contentpackage !!!
		
		while (mrp.hasFiles()) {			
	    					
			FileItem item = mrp.getNextFile();
			String fileFolder = FilenameUtils.getBaseName(item.getName()).replaceAll(".zip", "");
			fileFolder = fileFolder.replace('.', '_');
			
			File dir = new File(coursesFolder + fileFolder);
			
			while (dir.exists()) {
				
				fileFolder = "_" + fileFolder;
				dir = new File(coursesFolder + fileFolder);
				
			}			

			logger.info("mkdirs " + dir.getAbsolutePath());
			dir.mkdirs();
			logger.info("done mkdirs");
			
			ajaxInterface.updateProgress(userName, 0.0);
			ajaxInterface.updateStatus(userName, 2);
			
			byte[] buffer = new byte[1024];
			long totalBytes = 0;
			int bytesRead = 0;
			
			File zipFile = new File(dir + "\\" + FilenameUtils.getName(item.getName()));
			FileOutputStream fos = new FileOutputStream(zipFile);
			InputStream is = item.getInputStream();
						
			
			while ((bytesRead = is.read(buffer, 0, buffer.length)) > 0) {
				
				fos.write(buffer, 0, bytesRead);
				totalBytes = totalBytes + bytesRead;
				ajaxInterface.updateProgress(userName, (100 * totalBytes) / item.getSize());
				
			}
			
			fos.close();
			is.close();
			
			ajaxInterface.updateProgress(userName, 0.0);
			ajaxInterface.updateStatus(userName, 3);
			
			Zip zip = new Zip();	
			zip.setListener(this);			
			zip.unzip(zipFile, dir);			
			
			zipFile.delete();
			
			ajaxInterface.removeProgress(userName);	
			ajaxInterface.removeStatus(userName);
			
			LMSControl control = LMSControl.getInstance();
			CourseImpl course = new CourseImpl(id, fileFolder, title, false, false);
			logger.info("Inserting course");
			control.insertCourse(course);
			
		}
		
		Map<String, Object> mparams = mrp.getParameters();
		String params = "";
		for(String name:mparams.keySet()) {
			params += "&" + name + "=" + mparams.get(name);
			
		}
		params = params.substring(1);
		
		return HTMLBuilder.buildRedirect(mrp.getParameter("nextURL", true) + "?" + params);
	}
	


	
	public void unzip(double progress) {

		AjaxInterface.getInstance().updateProgress(userName, progress);
	}
	
	
	public void zip(double progress) {

	}

	
	public void update(long bytesRead, long totalBytes, int count) {
		
		AjaxInterface.getInstance().updateProgress(userName, (100 * bytesRead) / totalBytes);		
	}
}
