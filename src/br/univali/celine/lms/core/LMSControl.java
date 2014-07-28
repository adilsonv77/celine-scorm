package br.univali.celine.lms.core;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.univali.celine.lms.UserAdministration;
import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lms.utils.zip.Zip;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.CoursePractice;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.TrackModel;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.lmsscorm.UserPractice;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.sn.model.LearningActivity;

// LMSControl nao é um DAO !!!
public class LMSControl {

	private static LMSControl instance = null;	
	
	private LMSControl() {
		
		
	}
	
	public static LMSControl getInstance() {
	
		if (instance == null)
			instance = new LMSControl();
		
		return instance;
	}
	
	
	public void insertCourse(Course course, File zipFile) throws Exception {
		
		String sdir = LMSConfig.getInstance().getCompleteCoursesFolder();
		sdir = sdir.replaceAll("file:/", "");
		File dir = new File(sdir+"/"+course.getFolderName());
		dir.mkdirs();
		
		Zip zip = new Zip();	
		zip.unzip(zipFile, dir);
		
		insertCourse(course);

		
	}
		
	public void insertCourse(Course course) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.insertCourse(course);
		
		if (config.isRegisterCourseOnImport()) {

			List<User> users = dao.listUsers();
			
			for (int i = 0; i < users.size(); i++) {
			
				UserImpl anUser = (UserImpl) users.get(i);
				dao.registerUserCourse(anUser, course.getId());
				
			}
		}
	}
	
	

		
	public void insertUser(User user) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.insertUser(user);
		
		if (config.isRegisterUserOnInsert()) {
			
			List<Course> courses = listCourses();
			user = dao.findUser(user.getName());
			
			for (int i = 0; i < courses.size(); i++)
				dao.registerUserCourse(user, courses.get(i).getId());
			
		}
		
	}
	

	
	public List<Course> listCourses() throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.listCourses();
		
	}

	
	
	public List<User> listUsers() throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.listUsers();
	}

	
	public User webLogin(HttpServletRequest request, String login, String password) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		User user = dao.login(login, password);
		if (user != null) UserAdministration.setUser(request, user);
		
		return user;
	}
	
	
	
	public User login(String login, String password) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		User user = dao.login(login, password);
			
		return user;
	}

	
	
	public void removeCourse(String courseId) throws Exception {
	
		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.removeCourse(courseId);
		
	}
	
	
	
	public void removeUser(User user) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.removeUser(user);
		
	}

	
	
	public void updateUser(String name, User user) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.updateUser(name, user);
		
	}

	
	
	public void beginSaveInteractionInformation(String arg0, String arg1,
			String arg2, int arg3, String arg4, String arg5, String arg6,
			Double arg7, String arg8, String arg9, String arg10, String arg11)
			throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.beginSaveInteractionInformation(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
		
	}

	
	
	public void beginSaveTrackModel(String arg0, String arg1, String arg2, String arg3)
			throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.beginSaveTrackModel(arg0, arg1, arg2, arg3);
		
	}

	
	
	public Course findCourse(String courseId) throws Exception {
		
		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.findCourse(courseId);
	}
	

	
	public Course findCourseByFolderName(String folderName) throws Exception {
		
		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.findCourseByFolderName(folderName);
	}
	

	
	public User findUser(String name) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.findUser(name);
	}

	
	
	public void finishSaveInteractionInformation(String arg0, String arg1,
			String arg2, int arg3, String arg4) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.finishSaveInteractionInformation(arg0, arg1, arg2, arg3, arg4);
		
	}
	

	
	public void finishSaveTrackModel() throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.finishSaveTrackModel();
		
	}
	

	
	public String getDerivedCoursePackageStr(String arg0) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.getDerivedCoursePackageStr(arg0);
	}
	

	
	public void initialize() throws Exception {
	
		
	}
	
	
	
	public void insertCourse(ContentPackage arg0) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.insertCourse(arg0);
		
	}
	

	
	public List<CoursePractice> listCoursesPractice(String arg0) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();


		return dao.listCoursesPractice(arg0);
		
	}

	
	
	public List<UserPractice> listUsersPractice() throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();

		return dao.listUsersPractice();
		
	}
	

	
	public TrackModel loadTrackModel(String arg0, String arg1) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();

		return dao.loadTrackModel(arg0, arg1);
	}


	
	public void markCourseAsRemoved(String arg0) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();

		dao.markCourseAsRemoved(arg0);
		
	}

	
	
	public void registerUserCourse(User arg0, String arg1) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();

		dao.registerUserCourse(arg0, arg1);
		
	}
	

	
	public void removeTrackModel(String arg0, String arg1) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.removeTrackModel(arg0, arg1);
		
	}
	

	
	public void saveActivityProgressInformation(String arg0, String arg1,
			String arg2, String arg3, int arg4, boolean arg5, double arg6,
			double arg7, int arg8, boolean arg9, boolean arg10, boolean arg11,
			String arg12, String arg13, Double arg14, Double arg15,
			Double arg16, Double arg17) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.saveActivityProgressInformation(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17);
		
	}

	
	
	public void saveInteractionCorrectResponse(String arg0, String arg1,
			String arg2, String arg3, int arg4, String arg5) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.saveInteractionCorrectResponse(arg0, arg1, arg2, arg3, arg4, arg5);
		
	}

	
	
	public void saveObjectiveProgressInformation(String arg0, String arg1,
			String arg2, String arg3, boolean arg4, boolean arg5, boolean arg6,
			double arg7) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.saveObjectiveProgressInformation(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		
	}

	
	public void saveRequestActivity(User arg0, Course arg1,
			LearningActivity arg2) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.saveRequestActivity(arg0, arg1, arg2);
		
	}

	
	public void unregisterUserCourse(User arg0, String arg1) throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		dao.unregisterUserCourse(arg0, arg1);
		
	}

	
	public boolean userHasRightsAtCourse(User arg0, String arg1)
			throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.userHasRightsAtCourse(arg0, arg1);
		
	}

	
	public boolean userIsRegisteredAtCourse(User arg0, String arg1)
			throws Exception {

		LMSConfig config = LMSConfig.getInstance();
		DAO dao = config.getDAO();
		
		return dao.userHasRightsAtCourse(arg0, arg1);
	}
	
}
