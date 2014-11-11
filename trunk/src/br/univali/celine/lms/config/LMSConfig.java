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
package br.univali.celine.lms.config;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

import org.apache.commons.digester3.Digester;

import br.univali.celine.lms.dao.RDBDAO;
import br.univali.celine.lms.dao.XMLDAO;
import br.univali.celine.lms.integration.LMSIntegration;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackageReader;
import br.univali.celine.scorm.model.cam.ContentPackageReaderFactory;


/**
 * 
 * @author Adilson Vahldick
 **/

public class LMSConfig {
	
	// ------------------------------------------------------------ //
	//                 Parte estática 							    //
	// ------------------------------------------------------------ //
	
	private static LMSConfig config = null;	

	
	
	public static void buildConfig(String basePath, String celineConfigSubfolder) throws Exception {
		
		if (config != null) return;
		
        config = new LMSConfig(basePath);
        doBuildConfig(basePath + "/" + celineConfigSubfolder);
	}
	
	
	public static void buildConfig(ServletContext context) throws Exception {
		
		if (config != null) return;
		
        config = new LMSConfig(context);
		doBuildConfig(context.getRealPath("/") + "/WEB-INF");
		
	}
	
	
	private static void doBuildConfig(String path) throws Exception {
		
		Digester d = new Digester();
		d.setUseContextClassLoader(true);
        d.push(config);

        d.addCallMethod("*/courses-folder", "setCoursesFolder", 0);
//        d.addCallMethod("*/login-page", "setLoginPage", 0);
        d.addCallMethod("*/registerCourseOnImport", "setRegisterCourseOnImport", 0);
        d.addCallMethod("*/registerUserOnInsertCourse", "setRegisterUserOnInsert", 0);
        d.addCallMethod("*/error-page", "setErrorPage", 0);        
        d.addCallMethod("*/lmsIntegration", "setLMSIntegration", 0);
        
      
        d.addObjectCreate("*/database-source/rdb", RDBDAO.class);
        d.addSetNestedProperties("*/database-source/rdb");
        d.addSetNext("*/database-source/rdb", "setDAO");
        
        d.addObjectCreate("*/database-source/xml", XMLDAO.class);
        d.addCallMethod("*/database-source/xml", "setFileName", 0);
        d.addSetNext("*/database-source/xml", "setDAO");
        
        d.addObjectCreate("*/database-source/bean", "", "class"); // devido a um bug da versao 3.3 tive que fazer esse workaround !!!
        //d.addObjectCreate("*/database-source/bean", "class", DAO.class); 
		d.addSetNext("*/database-source/bean", "setDAO");

		d.addRule("*/database-source/bean/bean-attribute", new BeanSetterAttribute());
		
        String fileName = path + "/celine-config.xml";
        java.io.File srcfile = new java.io.File(fileName);
		d.parse(srcfile);
		
		if (config.dao == null)
			throw new Exception("DAO is not defined at celine-config.xml");
		
		config.dao.initialize();
		
	}
	
	
	public static LMSConfig getInstance() {
	
		return config;
		
	}
	
	// ------------------------------------------------------------ //
	//                 Parte dinâmica 							    //
	// ------------------------------------------------------------ //
	
	
	private ServletContext context; // esse é usado dentro de app web
	private String path; // esse é usado fora de app web
	private String errorPage;
	private LMSIntegration lmsIntegration;	
	private DAO dao;
	private String loginPage;
	private String coursesFolder = "Cursos";
	private boolean registerCourseOnImport;
	private boolean registerUserOnInsert;

	
	private LMSConfig(ServletContext context) {
	
		this.context = context;
		
	}
	
	private LMSConfig(String path) {
		
		this.path = path;
		
	}

	public String getCoursesFolder() {

		return coursesFolder;
		
	}

	public String getCourseFolder(String courseName) {
		
		String folder = getCoursesFolder(); 
		if (folder.startsWith("file:")) { // se é um caminho fora do contexto, entao coloca uma marca para o sistema se achar
			folder = "$$$$";
			try {
				courseName = URLEncoder.encode(courseName, "UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace(); // rezar para nao dar jibu !!!
			}
		}
		
		return folder + "/" + courseName+"/";
		
	}

	
	public void setCoursesFolder(String courseFolder) {
	
		this.coursesFolder = courseFolder;
		
	}

	public void setRegisterCourseOnImport(String register) {
	
		this.registerCourseOnImport = Boolean.parseBoolean(register);
		
	}
	
	public boolean isRegisterCourseOnImport() {
	
		return registerCourseOnImport;
		
	}

	public void setRegisterUserOnInsert(String register) {
		this.registerUserOnInsert = Boolean.parseBoolean(register);
	}
	
	
	public boolean isRegisterUserOnInsert() {
		return registerUserOnInsert;
	}
	
	public String getCompleteCoursesFolder() {
	
		String c = getCoursesFolder();
		if (c.startsWith("file:")) { // se a pasta começa com barra, entao indica que é uma pasta fora do contexto
			return c;
		}
		
		return getWebDeployedPath()+getCoursesFolder()+"/";
		
	}
	
	
	public String getWebDeployedPath() {
	
		if (context == null)
			return path;
		
		return context.getRealPath("/"); // essa atrapalha +"/";
	}
	
	public String getContentPackageFile(String courseName) {
		
		return  getCompleteCoursesFolder()+courseName+"/imsmanifest.xml";
		
	}

	public ContentPackage openContentPackageByFolderName(String courseFolder) throws Exception {

		String fileName = LMSConfig.getInstance().getContentPackageFile(courseFolder);
		ContentPackageReader cpr = ContentPackageReaderFactory.getContentPackageReader(fileName);
		
		return cpr.read(fileName);
		
	}
	
	public ContentPackage openContentPackageById(User user, String courseId) throws Exception {
		
		ContentPackage cp = getLMSIntegration().openContentPackage(user, courseId);
		
		if (cp == null) {
			cp = openContentPackageByFolderName(dao.findCourse(courseId).getFolderName());
		}
		return cp;
	}

	
	public ContentPackage openContentPackageStr(String packageStr) throws Exception {
	
		ByteArrayInputStream bytes = new ByteArrayInputStream(packageStr.getBytes());
		ContentPackageReader cpr = ContentPackageReaderFactory.getContentPackageReader(bytes);
		return cpr.read(bytes);		
	}
	
	
	public DAO getDAO() {
		return dao;
	}
	
	public void setDAO(DAO dao) throws Exception {
		this.dao = dao;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}


	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public void setLMSIntegration(String classLMSIntegration) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		lmsIntegration = (LMSIntegration) Class.forName(classLMSIntegration).newInstance();
	}
	
	public LMSIntegration getLMSIntegration() {
		if (lmsIntegration == null) {
			lmsIntegration = new DefaultLMSIntegration();
		}
		return lmsIntegration;
	}
	
}
