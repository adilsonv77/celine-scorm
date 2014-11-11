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
package br.univali.celine.ws.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import br.univali.celine.lms.core.LMSControl;
import br.univali.celine.lms.model.CourseImpl;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.User;

@WebService
@HandlerChain(file="celinews_handler.xml")
public class CelineService {

	@WebMethod
	public void registerNewUser(
			@WebParam(name="name") String name, 
			@WebParam(name="passw")String passw, 
			@WebParam(name="admin")boolean admin) throws Exception {
		LMSControl lms = LMSControl.getInstance();
		
		User user = new UserImpl();
		user.setName(name);
		user.setPassw(passw);
		user.setAdmin(admin);
		
		lms.insertUser(user);
	}
	
	@WebMethod
	public void registerNewCourse(
			@WebParam(name="courseId") String courseId,
			@WebParam(name="courseTitle") String courseTitle) throws Exception {
		
		MessageContext msg = webCtx.getMessageContext();
		HttpSession sessao = ((HttpServletRequest)msg.get(MessageContext.SERVLET_REQUEST)).getSession();
		String zipFileName = (String) sessao.getAttribute("CelineService.filename_scorm_zip");
		if (zipFileName == null)
			throw new Exception("Not attached de the SCORM ZipFile");
		sessao.removeAttribute("CelineService.filename_scorm_zip");
		
		LMSControl lms = LMSControl.getInstance();
		String courseFolderName = courseTitle; // ??? need to be different ?!??!
		Course c = new CourseImpl(courseId, courseFolderName , courseTitle, false, false);
		
		File zipFile = new File(zipFileName);
		lms.insertCourse(c, zipFile);
		zipFile.deleteOnExit();
	}
	
	@WebMethod()
	public void registerNewCourseWithFile(
			@WebParam(name="courseData")CourseData courseData) throws Exception {
		
		LMSControl lms = LMSControl.getInstance();
		
		Course c = new CourseImpl(courseData.getCourseId(), courseData.getCourseTitle(), courseData.getCourseTitle(), false, false);
		
		InputStream in = courseData.getZipFile().getInputStream();
		
		File zipFile = File.createTempFile(courseData.getCourseId() + "_", ".zip");
		FileOutputStream out = new FileOutputStream(zipFile);
		
		IOUtils.copy(in, out);
		
		out.close();

		lms.insertCourse(c, zipFile);
		zipFile.deleteOnExit();
	}
	
    @Resource
    private WebServiceContext webCtx;
    
	@WebMethod
	public void registerUserAtCourse(
			@WebParam(name="userName")String userName, 
			@WebParam(name="courseId")String courseId) throws Exception {
		
		LMSControl lms = LMSControl.getInstance();
		
		User user = lms.findUser(userName);
		
		lms.registerUserCourse(user, courseId);
		
	}
	
}
