package br.univali.celine.lms.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.univali.celine.lms.config.LMSConfig;
import br.univali.celine.lms.model.CourseImpl;
import br.univali.celine.lms.model.RegisteredCourse;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.CoursePractice;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.TrackActivityInfo;
import br.univali.celine.lmsscorm.TrackModel;
import br.univali.celine.lmsscorm.TrackObjective;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.lmsscorm.UserPractice;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.Item20043rd;
import br.univali.celine.scorm.sn.model.LearningActivity;

import com.thoughtworks.xstream.XStream;

public class XMLDAO implements DAO {

	private Logger logger = Logger.getLogger("global"); 
	
	private String fileName;
	private String folder;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.folder = null;
		this.xml = null;
		
		if (fileName.startsWith("/")) {
			String[] path = fileName.split("/");
			String ret = "";
			for (int y=0;y<path.length-1;y++)
				ret += path[y] + "/";
			this.folder = ret;
			this.fileName = path[path.length-1];
			return; 
		}
		
		this.fileName = fileName;
	}

	private CelineXML xml = null;

	protected String getFolderName() {
		
		if (folder != null) {
			return folder;
		}
		
		LMSConfig cfg = LMSConfig.getInstance(); 
		if (cfg == null) {
			return "";
		}
		
		return cfg.getWebDeployedPath();
	}
	
	private void load() throws Exception {
		FileInputStream file = new FileInputStream(new File(getFolderName() + fileName));
		this.xml = (CelineXML) getXStream().fromXML(file);
		file.close();

	}
	
	private void save() throws Exception {
		FileOutputStream file = new FileOutputStream(new File(getFolderName() + fileName));
		getXStream().toXML(this.xml, file);
		file.close();
	}

	private XStream getXStream() {
		XStream xstream = new XStream();

		xstream.alias("celine", CelineXML.class);
		xstream.alias("user", UserImpl.class);
		xstream.alias("registeredCourse", RegisteredCourse.class);
		xstream.alias("course", CourseImpl.class);
		xstream.alias("item", Item20043rd.class);

		
		return xstream;
	}

	protected CelineXML getXml() throws Exception {
		if (this.xml == null) {
			load();
		}
		return this.xml;
	}
	
	
	public User login(String user, String passw) throws Exception {
		
		User u = getXml().findUser(user);
		
		if (u != null && u.getPassw().equals(passw)) {
			return u;
		}
		
		return null;
	}

	
	public String toString() {
		return "XML " + fileName;
	}

	
	public boolean userIsRegisteredAtCourse(User user, String courseName) throws Exception {
		
		if (user == null)
			return false;
		
		for (RegisteredCourse rc: getXml().getRegisteredCourses()) {
			if (rc.getCourse().equals(courseName)) {
				if (rc.getUser().equals(user.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	
	public boolean userHasRightsAtCourse(User user, String nomeCurso) {
		return true;
	}

	
	public void registerUserCourse(User user, String courseName) throws Exception {
		if (user == null)
			return;
		
		if (userIsRegisteredAtCourse(user, courseName) == false) {
			getXml().getRegisteredCourses().add(new RegisteredCourse(user.getName(), courseName));
			save();
		}
	}

	
	public void unregisterUserCourse(User user, String courseName) throws Exception {
		if (user == null)
			return;
		
		CelineXML xml = getXml();
		
		for (int i = 0; i < xml.getRegisteredCourses().size(); i++) {
			RegisteredCourse rc = xml.getRegisteredCourses().get(i);
			
			if (rc.getCourse().equals(courseName) && rc.getUser().equals(user.getName())) {
				xml.getRegisteredCourses().remove(i);
				save();
				return;
			}
		}
		
	}

	public static void main(String[] args) throws Exception {
		XMLDAO xmlDAO = new XMLDAO();
		xmlDAO.setFileName("WebContent/WEB-INF/celine.xml");
		xmlDAO.setFileName("/temp/celine.xml");
		xmlDAO.load();
		System.out.println(xmlDAO.xml);
		/*
		
		User u1 = new User();
		u1.setName("teste");
		xmlDAO.registerUserCourse(u1, "qualquer");
		
		xmlDAO.beginSaveTrackModel("teste", "001", "act01");
		xmlDAO.saveObjectiveProgressInformation("act01", "obj001", true, true, true, 10);
		//xmlDAO.finishSaveTrackModel();
		*/
		//TrackModel tm = (TrackModel)xmlDAO.createXStreamTrackModel().fromXML(new FileInputStream("adilson_MosOrgteste_com_attempts_eceline.xml"));
		/*
		 * (TrackModel) createXStreamTrackModel().fromXML(new FileInputStream(arq));
		 */
		//TrackModel tm = xmlDAO.loadTrackModel("teste", "001");
		//System.out.println(tm);
		
	}

	//TODO: precisa arrumar isso daqui... funciona com um unico usuario acessando o sistema, mas é problema quando tem mais de um
	//      talvez usar um HashMap
	private TrackModel trackModelXML;
	

	
	public void beginSaveTrackModel(String courseId, String learnerId, String suspendedActivityId)
			throws Exception {

		this.trackModelXML = new TrackModel(courseId, learnerId);
		this.trackModelXML.setSuspendedActivity(suspendedActivityId);
	}

	private String getFolderFileName() {
		
		String[] names = fileName.split("/");
		
		String folder = "";
		for (int x = 0; x < names.length-1; x++)
			folder += names[x] + "/";
		
		return folder;
	}
	
	private String getNameFileName() {
		String[] names = fileName.split("/");
		return names[names.length-1];
	}
	private XStream createXStreamTrackModel() {
		

		XStream xstream = new XStream();
		
		xstream.alias("trackModel", TrackModel.class);
		xstream.alias("trackObjectiveInfo", TrackObjective.class);
		xstream.alias("trackActivityInfo", TrackActivityInfo.class);
		return xstream;
		
	}
	
	
	public void finishSaveTrackModel() throws Exception {
		
		File file = new File(getFolderName() +
				getFolderFileName() + 
				trackModelXML.getLearnerId() + "_" + trackModelXML.getCourseId() + 
				getNameFileName());
		FileOutputStream fileOut = new FileOutputStream(file);
		createXStreamTrackModel().toXML(trackModelXML, fileOut);
		fileOut.close();
		
	}

	
	public void saveObjectiveProgressInformation(String courseId, String learnerId, String activityID, String objectiveID, boolean progressStatus,
			boolean satisfiedStatus, boolean measureStatus,
			double normalizedMeasure) throws Exception {
		
		trackModelXML.addObjective(activityID, objectiveID,  progressStatus, satisfiedStatus, measureStatus, normalizedMeasure);
		
	}

	
	public void saveActivityProgressInformation(String courseId, String learnerId, String activityID, String parentActId, int idxChildren,
			boolean progressStatus, double absoluteDuration,
			double experiencedDuration, int attemptCount, boolean suspended,
			boolean attemptCompletionStatus, boolean attemptProgressStatus, String completionStatus, String successStatus, 
			Double scoreRaw, Double scoreMin, Double scoreMax, Double scoreScaled) {
		
		trackModelXML.addActivityProgressInformation(activityID, parentActId, idxChildren,
				progressStatus, absoluteDuration,
				experiencedDuration, attemptCount, suspended, attemptCompletionStatus, 
				attemptProgressStatus, scoreRaw, scoreMin, scoreMax, scoreScaled);
		
	}

	
	public TrackModel loadTrackModel(String courseId, String learnerId)
			throws Exception {
		
		File arq = new File(getFolderName() + 
				getFolderFileName() + 
				learnerId + "_" + courseId+ 
				getNameFileName());
		
		logger.info(arq.toString());
		
		if (arq.exists()) {
			FileInputStream file = new FileInputStream(arq);
			TrackModel trackModel = (TrackModel) createXStreamTrackModel().fromXML(file);
			file.close();
			return trackModel;
		} else {
			return new TrackModel(courseId, learnerId);
		}
	}

	
	public void insertUser(User user) throws Exception {

		getXml().getUsers().add(user);
		save();
		
		save();
	}
	
	
	public void insertCourse(Course course) throws Exception {
		
		CourseImpl courseImpl = new CourseImpl(0, course.getId(), course.getFolderName(), course.getTitle(), course.isDerived(), course.isRemoved());
		getXml().getCourses().add(courseImpl);
		save();				
		
	}
	
	
	public void removeTrackModel(String courseId, String learnerId) throws Exception {
		
	}
	
	
	public void removeCourse(String courseId) throws Exception {
		
		
		for (int  i = 0; i < getXml().getCourses().size(); i++)
			if ( getXml().getCourses().get(i).getId().equals(courseId)) {
				
				
				File file = new File(LMSConfig.getInstance().getCoursesFolder() + "\\" + getXml().getCourses().get(i).getFolderName());
				file.delete();
				
				getXml().getCourses().remove(i);
				save();
				return;				
			}
		
	}

	
	public void initialize() throws Exception {
	}

	
	public List<Course> listCourses() throws Exception {
		List<Course> ret = new ArrayList<Course>(getXml().getCourses().size());
		for (Course course:getXml().getCourses())
			ret.add(course);
		return ret;
	}

	
	public List<User> listUsers() throws Exception {
		List<User> ret = new ArrayList<User>(getXml().getUsers().size());
		for (User user:getXml().getUsers())
			ret.add(user);
		return ret;
	}

	
	public Course findCourse(String courseId) throws Exception {
		
		for (Course course:getXml().getCourses())
			if (course.getId().equals(courseId))
				return course;
				
		return null;
	}

	
	public Course findCourseByFolderName(String courseFolderName) throws Exception {
		for (Course course:getXml().getCourses()) {
			if (course.getFolderName() != null)
				if (course.getFolderName().equals(courseFolderName))
					return course;
		}
		
		return null;
	}

	
	public void beginSaveInteractionInformation(String courseId,
			String learnerId, String activityId, int index, String id,
			String type, String timeStamp, Double weighting,
			String learnerResponse, String result, String latency,
			String description) throws Exception {

		//TODO: nao remova essa marca... é para lembrar que preciso fazer
	}

	
	public void finishSaveInteractionInformation(String courseId,
			String learnerId, String activityId, int index, String id)
			throws Exception {

		//TODO: nao remova essa marca... é para lembrar que preciso fazer
	}

	
	public void saveInteractionCorrectResponse(String courseId,
			String learnerId, String activityId, String id, int idxCorrect,
			String correctResponsesPattern) throws Exception {

		//TODO: nao remova essa marca... é para lembrar que preciso fazer
	}
	
	
	
	public void markCourseAsRemoved(String courseId) throws Exception {
		
		CourseImpl course = null;
		
		for (int i = 0; i < getXml().getCourses().size(); i++)
			if ((course = (CourseImpl) getXml().getCourses().get(i)).getId().equals(courseId))
				course.setRemoved(true);
		
		save();
		
	}

	
	public void removeUser(User user) throws Exception {
		
		for (int i = 0; i < getXml().getUsers().size(); i++)
			if (getXml().getUsers().get(i).getName().equals(user.getName())) {
			
				getXml().getUsers().remove(i);
				save();
				return;
				
			}
	}
	
	
	public void updateUser(String userName, User user) throws Exception {
		
		User anUser = null;
		
		for (int i = 0; i < getXml().getUsers().size(); i++)
			if ((anUser = getXml().getUsers().get(i)).getName().equals(userName)) {
			
				anUser.setName(user.getName());
				anUser.setPassw(user.getPassw());
				anUser.setAdmin(user.isAdmin());				
				save();				
				return;				
			}		
	}

	
	
	public User findUser(String name) throws Exception {
		
		User user = null;
		
		for (int i = 0; i < getXml().getUsers().size(); i++)
			if ((user = getXml().getUsers().get(i)).getName().equals(name))
				return user;
		
		return null;
		
	}

	
	public void insertCourse(ContentPackage coursePackage) throws Exception {

		int idCode = 0;
		String courseId = coursePackage.getOrganizations().getDefaultOrganization().getIdentifier();
		String title = coursePackage.getOrganizations().getDefaultOrganization().getTitle();
		
		CourseImpl course = new CourseImpl(idCode, courseId, courseId, title, true, false);
		getXml().getCourses().add(course);
		save();		
		
		PrintWriter pw = new PrintWriter(LMSConfig.getInstance().getCompleteCoursesFolder() + "\\" + course.getId() + ".xml");
		pw.println(coursePackage.toString());
		pw.close();		
		
	}
	
	
	public String getDerivedCoursePackageStr(String courseId) throws Exception {
		
		int charsRead = 0;
		char[] buffer = new char[1024];		
		FileReader reader = new FileReader(LMSConfig.getInstance().getCompleteCoursesFolder() + courseId + ".xml");
		StringWriter writer = new StringWriter();

		while ( (charsRead = reader.read(buffer, 0, 1024)) > 0) {
			writer.write(buffer, 0, charsRead);
		}
		return new String(writer.getBuffer());
	}

	public List<UserPractice> listUsersPractice() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveRequestActivity(User user, Course course, LearningActivity activity)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public List<CoursePractice> listCoursesPractice(String learnerId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
