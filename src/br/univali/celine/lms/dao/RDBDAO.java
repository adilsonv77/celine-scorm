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
package br.univali.celine.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.univali.celine.lms.dao.util.ConnectionPool;
import br.univali.celine.lms.model.CourseImpl;
import br.univali.celine.lms.model.CoursePracticeImpl;
import br.univali.celine.lms.model.UserImpl;
import br.univali.celine.lms.model.UserPracticeImpl;
import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.CoursePractice;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.TrackModel;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.lmsscorm.UserPractice;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.sn.model.LearningActivity;

public class RDBDAO implements DAO {

	private Logger logger = Logger.getLogger("global"); 
	
	private String driver;
	private String url;	
	private String user;
	private String password;
	private String poolName;
	protected ConnectionPool pool; // os descendentes precisam poder ver esse atributo
	
	
	private static final String selectLoginSQL = "SELECT ID_USER, DS_NICK, DS_PASSW, TP_USER FROM USERS WHERE DS_NICK = ? AND DS_PASSW = ?";

	private static final String lastIDSQL = "SELECT LAST_INSERT_ID()";

	private static final String selectUsersSQL = "SELECT ID_USER, DS_NICK, DS_PASSW, TP_USER FROM USERS";
	private static final String selectUserByNickSQL = "SELECT ID_USER, DS_NICK, DS_PASSW, TP_USER FROM USERS WHERE DS_NICK = ?";	
	private static final String insertUserSQL = "INSERT INTO USERS (DS_NICK, DS_PASSW, TP_USER) VALUES (?, ?, ?)";
	private static final String deleteUserSQL = "DELETE FROM USERS WHERE DS_NICK = ?";
	private static final String editUserSQL = "UPDATE USERS SET DS_NICK = ?, DS_PASSW = ?, TP_USER = ? WHERE DS_NICK = ?";

	
	private static final String selectCoursesSQL = "SELECT ID_COURSE, DS_COURSE, DS_ID, DS_TITLE, DS_DERIVED, DS_REMOVED FROM COURSES ORDER BY DS_TITLE";
	private static final String selectCourseByCourseIdSQL = "SELECT ID_COURSE, DS_COURSE, DS_ID, DS_TITLE, DS_DERIVED, DS_REMOVED FROM COURSES WHERE DS_ID = ?";
	private static final String selectCourseByNameSQL = "SELECT ID_COURSE FROM COURSES WHERE DS_ID=?";
	private static final String selectCourseByFolderNameSQL = "SELECT ID_COURSE, DS_COURSE, DS_ID, DS_TITLE, DS_DERIVED, DS_REMOVED FROM COURSES WHERE DS_COURSE=?";	
	private static final String insertCourseSQL = "INSERT INTO COURSES (DS_COURSE, DS_ID, DS_TITLE, DS_REMOVED, DS_DERIVED) VALUES (?, ?, ?, ?, ?)";
	private static final String removeCourseSQL = "DELETE FROM COURSES WHERE DS_ID = ?";
	private static final String markCourseAsRemovedSQL = "UPDATE COURSES SET DS_REMOVED = ? WHERE DS_ID = ?";	
	
		
	private static final String insertContentPackageStrSQL = "INSERT INTO CONTENT_PACKAGES (DS_ID, DS_CONTENT_PACKAGE) VALUES (?, ?)";
	private static final String getDerivedCoursePackageStrSQL = "SELECT DS_CONTENT_PACKAGE FROM CONTENT_PACKAGES WHERE DS_ID = ?";

	private static final String selectUserRegisteredSQL = "SELECT ID_SUSPENDEDACTIVITY FROM COURSESUSERS CU, USERS U, COURSES C " +
								"  WHERE CU.ID_USER = U.ID_USER AND CU.ID_COURSE = C.ID_COURSE AND U.DS_NICK=? AND C.DS_ID = ?";
	

	
	 private static final String insertCoursesUsersSQL = "INSERT INTO COURSESUSERS (ID_USER, ID_COURSE) VALUES (?, ?)";
	 private static final String deleteCoursesUsersSQL = "DELETE FROM COURSESUSERS WHERE ID_USER=? AND ID_COURSE=?";
	

	 private static final String updateSuspendedActivitySQL = "UPDATE COURSESUSERS SET ID_SUSPENDEDACTIVITY=?, DS_SUSPENDEDDATA=? WHERE ID_USER=? AND ID_COURSE=?";
	 private static final String selectSuspendedActivitySQL = "SELECT ID_SUSPENDEDACTIVITY, DS_SUSPENDEDDATA FROM COURSESUSERS WHERE ID_USER=? AND ID_COURSE=?";

	 private static final String insertTrackModelObjectiveSQL = "INSERT INTO TMOBJECTIVES " +
			"(BOOL_PROGRESSSTATUS, BOOL_SATISFIEDSTATUS, " +
				 "BOOL_MEASURESTATUS, VL_NORMALIZEDMEASURE," +
				 "ID_USER, ID_COURSE, ID_ACTIVITY, ID_OBJECTIVE) VALUES " +
				 "(?,?,?,?,?,?,?,?)";
	
	
	
	 private static final String selectTrackModelOneObjectiveSQL = 
			"SELECT ID_USER, ID_COURSE, ID_ACTIVITY, ID_OBJECTIVE, BOOL_PROGRESSSTATUS, BOOL_SATISFIEDSTATUS, " +
				 "BOOL_MEASURESTATUS, VL_NORMALIZEDMEASURE FROM TMOBJECTIVES " +
				 "WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=? AND ID_OBJECTIVE=?";
	
	
	 private static final String updateTrackModelObjectiveSQL = 
			"UPDATE TMOBJECTIVES " +
			"SET BOOL_PROGRESSSTATUS=?, BOOL_SATISFIEDSTATUS=?, " +
				 "BOOL_MEASURESTATUS=?, VL_NORMALIZEDMEASURE=? " +
			"WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=? AND ID_OBJECTIVE=?";
	
	
	 private static final String selectTrackModelObjectivesSQL = 
			"SELECT ID_USER, ID_COURSE, ID_ACTIVITY, ID_OBJECTIVE, BOOL_PROGRESSSTATUS, BOOL_SATISFIEDSTATUS, " +
				 "BOOL_MEASURESTATUS, VL_NORMALIZEDMEASURE FROM TMOBJECTIVES " +
				 "WHERE ID_USER=? AND ID_COURSE=?";
	
	
	 private static final String deleteTrackModelObjectivesSQL = "DELETE FROM TMOBJECTIVES WHERE ID_USER=? AND ID_COURSE=?";

	 private static final String insertTrackModelActivitySQL = "INSERT INTO TMACTIVITIES " +
			"(BOOL_PROGRESSSTATUS, VL_ABSOLUTEDURATION, " +
				 "VL_EXPERIENCEDDURATION, VL_ATTEMPTCOUNT, BOOL_SUSPENDED, ID_PARENTACTIVITY, IDX_CHILDREN, " +
				 "BOOL_ATTEMPTCOMPLETIONSTATUS, BOOL_ATTEMPTPROGRESSSTATUS, VL_SCORERAW, VL_SCOREMIN, VL_SCOREMAX, VL_SCORESCALED, " +
				 "ID_USER, ID_COURSE, ID_ACTIVITY) VALUES " +
				 "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	 private static final String selectTrackModelOneActivitySQL = 
			"SELECT ID_USER, ID_COURSE, ID_ACTIVITY, BOOL_PROGRESSSTATUS, VL_ABSOLUTEDURATION, " +
				 "VL_EXPERIENCEDDURATION, VL_ATTEMPTCOUNT, BOOL_SUSPENDED, ID_PARENTACTIVITY, IDX_CHILDREN FROM TMACTIVITIES " +
				 "WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=?";
	
	
	 private static final String updateTrackModelActivitySQL = 
			"UPDATE TMACTIVITIES " +
			"SET BOOL_PROGRESSSTATUS=?, VL_ABSOLUTEDURATION=?, " +
				 "VL_EXPERIENCEDDURATION=?, VL_ATTEMPTCOUNT=?, BOOL_SUSPENDED=?, ID_PARENTACTIVITY=?, IDX_CHILDREN=?, " +
				"BOOL_ATTEMPTCOMPLETIONSTATUS=?, BOOL_ATTEMPTPROGRESSSTATUS=?, VL_SCORERAW=?, VL_SCOREMIN=?, VL_SCOREMAX=?, VL_SCORESCALED=? " +
			"WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=?";
	
	
	 private static final String selectTrackModelActivitiesSQL = 
			"SELECT ID_USER, ID_COURSE, ID_ACTIVITY, BOOL_PROGRESSSTATUS, VL_ABSOLUTEDURATION, " +
				 "VL_EXPERIENCEDDURATION, VL_ATTEMPTCOUNT, BOOL_SUSPENDED, ID_PARENTACTIVITY, IDX_CHILDREN, " +
				 "BOOL_ATTEMPTCOMPLETIONSTATUS, BOOL_ATTEMPTPROGRESSSTATUS, VL_SCORERAW, " +
				 "VL_SCOREMIN, VL_SCOREMAX, VL_SCORESCALED FROM TMACTIVITIES " +
				 "WHERE ID_USER=? AND ID_COURSE=? ORDER BY ID_PARENTACTIVITY, IDX_CHILDREN";
	
	
	 private static final String deleteTrackModelActivitiesSQL = "DELETE FROM TMACTIVITIES WHERE ID_USER=? AND ID_COURSE=?";
	/*
	 @SuppressWarnings("unused")
	private String selectTrackModelInteractionsSQL = 
			"SELECT ID_USER, ID_COURSE, ID_ACTIVITY, NR_INDEX, ID_INTERACTION, DS_TYPE, DS_TIMESTAMP, NR_WEIGHTING, " +
			" DS_LEARNERRESPONSE, DS_RESULT, DS_LATENCY, DS_DESCRIPTION FROM TMINTERACTIONS " +
			"WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=? ORDER BY NR_INDEX";
	*/
	
	 private static final String insertTrackModelInteractionsSQL = "INSERT INTO TMINTERACTIONS " +
			"(ID_USER, ID_COURSE, ID_ACTIVITY, NR_INDEX, ID_INTERACTION, DS_TYPE, DS_TIMESTAMP, NR_WEIGHTING, " +
			" DS_LEARNERRESPONSE, DS_RESULT, DS_LATENCY, DS_DESCRIPTION) VALUES " +
			"(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	 private static final String deleteTrackModelInteractionsSQL = 
			"DELETE FROM TMINTERACTIONS WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=? AND ID_INTERACTION=?";
	
	 private static final String deleteTrackModelInteractionsByCourseSQL = 
			"DELETE FROM TMINTERACTIONS WHERE ID_USER=? AND ID_COURSE=?";
	
	// request
	 private static final String insertRequestActivitySQL = 
			"INSERT INTO REQUESTACTIVITIES (VL_ATTEMPT, VL_EXPERIENCEDDURATION, DT_LASTTIMEACCESSED, ID_USER, ID_COURSE, ID_ACTIVITY) " +
			"VALUES (?,?,?,?,?,?)";
	
	
	 private static final String updateRequestActivitySQL = 
			"UPDATE REQUESTACTIVITIES SET VL_ATTEMPT=?, VL_EXPERIENCEDDURATION=?, DT_LASTTIMEACCESSED=? WHERE " +
			"ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=?";
	
	
	 private static final String selectRequestActivitySQL = 
			"SELECT VL_ATTEMPT, VL_EXPERIENCEDDURATION, DT_LASTTIMEACCESSED FROM REQUESTACTIVITIES " +
			"WHERE ID_USER=? AND ID_COURSE=? AND ID_ACTIVITY=?";
	

	 private static final String selectCoursesAccessedSQL = 
			"SELECT ID_USER, COUNT(DISTINCT ID_COURSE), SUM(VL_EXPERIENCEDDURATION), MAX(DT_LASTTIMEACCESSED) FROM " +
				"REQUESTACTIVITIES GROUP BY ID_USER";
	
	 private static final String selectCoursesAccessedOneUserSQL = 
			"SELECT ID_COURSE, SUM(VL_EXPERIENCEDDURATION), MAX(DT_LASTTIMEACCESSED) FROM REQUESTACTIVITIES " +
				"WHERE ID_USER = ? GROUP BY ID_COURSE";
	
	

	

	public void initialize() throws Exception {
	
			pool = new ConnectionPool(poolName, driver, url, user, password);

	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPoolName() {
		return poolName;
	}
	
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	
	
	public String toString() {
		return "RDB " + driver + " " + url + " " + user + " " + password;
	}

	
	public User login(String user, String passw) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserImpl objUser = null;
		
		try {
		
			ps = connection.prepareStatement(selectLoginSQL);		
			ps.setString(1, user);
			ps.setString(2, passw);
			rs = ps.executeQuery();	
		
			if (rs.next()) {
				
				objUser = new UserImpl();
				objUser.setId(rs.getInt("ID_USER"));
				objUser.setName(user);
				objUser.setPassw(passw);
				objUser.setAdmin(rs.getString("TP_USER").equals("A"));
			}
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}			
			try { connection.close(); } catch (Exception e) {}
			
		}			
		
		return objUser;
	}

	
	public boolean userIsRegisteredAtCourse(User user, String nome) throws Exception {

		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean registered = false;
		
		try {
		
			ps = connection.prepareStatement(selectUserRegisteredSQL);		
			ps.setString(1, user.getName());
			ps.setString(2, nome);
			rs = ps.executeQuery();		
			registered = rs.next();
			
		} finally {
			
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return registered;
	}

	
	public boolean userHasRightsAtCourse(User user, String nomeCurso) {
		
		return true; // por padrao sempre retorna true
	}

	private int getIDUser(String userName) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int codigo = 0;
		
		try {
			
			ps = connection.prepareStatement(selectUserByNickSQL);
			ps.setString(1, userName);
			rs = ps.executeQuery();		
			if (rs.next()) codigo = rs.getInt(1);
		
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return codigo;
	}
	
	private int getIDCourse(String courseName) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int codigo = 0;
		
		try {
		
			ps = connection.prepareStatement(selectCourseByNameSQL);
			ps.setString(1, courseName);
			rs = ps.executeQuery();		
			if (rs.next()) codigo = rs.getInt(1);

		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { try { ps.close(); } catch (Exception e) {} } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return codigo;
	}
	
	
	public void registerUserCourse(User user, String curso) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(insertCoursesUsersSQL);
			ps.setInt(1, getIDUser(user.getName()));
			ps.setInt(2, getIDCourse(curso));
			ps.executeUpdate();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { try { connection.close(); } catch (Exception e) {} } catch (Exception e) {}
			
		}		
	}

	
	public void unregisterUserCourse(User user, String courseName) throws Exception {

		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(deleteCoursesUsersSQL);
			ps.setInt(1, getIDUser(user.getName()));
			ps.setInt(2, getIDCourse(courseName));
			ps.executeUpdate();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void beginSaveTrackModel(String courseId, String learnerId, String suspendedActivityId, String suspendedData) throws Exception {
		
		updateSuspendedActivity(getIDCourse(courseId), getIDUser(learnerId), suspendedActivityId, suspendedData);
		
	}
	
	private void updateSuspendedActivity (int idCourse, int idUser, String suspendedActivityId, String suspendedData) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(updateSuspendedActivitySQL);
			ps.setString(1, suspendedActivityId);
			ps.setString(2, suspendedData);
			
			ps.setInt(3, idUser);
			ps.setInt(4, idCourse); 
		
			logger.info(ps.toString());		
			ps.executeUpdate();
			
		} finally {
			
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void finishSaveTrackModel() throws Exception { } // nothing to do	

	
	public void saveObjectiveProgressInformation(String courseId, String learnerId, String activityID, String objectiveID, boolean progressStatus,
			boolean satisfiedStatus, boolean measureStatus,
			double normalizedMeasure) throws Exception {
		
		
		int idCourse = getIDCourse(courseId);
		int idUser = getIDUser(learnerId);
		
		if (activityID == null)
			activityID = ""; // objetivo global nao pertence a atividades
		if (objectiveID == null)
			objectiveID = ""; // objetivo primário
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			ps = connection.prepareStatement(selectTrackModelOneObjectiveSQL);
		
			ps.setInt(1, idUser);
			ps.setInt(2, idCourse);
			ps.setString(3, activityID);
			ps.setString(4, objectiveID);
		
			logger.info(ps.toString());		
			rs = ps.executeQuery();
			
			boolean temActObj = rs.next();
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
	

			if (temActObj) 
				ps = connection.prepareStatement(updateTrackModelObjectiveSQL);
			else
				ps = connection.prepareStatement(insertTrackModelObjectiveSQL);
		
			ps.setBoolean(1, progressStatus);
			ps.setBoolean(2, satisfiedStatus); 
			ps.setBoolean(3, measureStatus);
			ps.setDouble(4, normalizedMeasure);
			ps.setInt(5, idUser);
			ps.setInt(6, idCourse);		
			ps.setString(7, activityID);		
			ps.setString(8, objectiveID);			
			ps.executeUpdate();

			
		} finally {

			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}			
		}		
	}


	
	public void saveActivityProgressInformation(String courseId, String learnerId, 
			String activityID, String parentActId, int idxChildren,
			boolean progressStatus, double absoluteDuration,
			double experiencedDuration, int attemptCount, boolean suspended,
			boolean attemptCompletionStatus, boolean attemptProgressStatus,
			String completionStatus, String successStatus,
			Double scoreRaw, Double scoreMin, Double scoreMax, Double scoreScaled) throws Exception {

		int idCourse = getIDCourse(courseId);
		int idUser = getIDUser(learnerId);
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			ps = connection.prepareStatement(selectTrackModelOneActivitySQL);		
			ps.setInt(1, idUser);
			ps.setInt(2, idCourse);
			ps.setString(3, activityID);
		
			rs = ps.executeQuery();
			boolean temActObj = rs.next();
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
	
			if (temActObj) 
				ps = connection.prepareStatement(updateTrackModelActivitySQL);
			else
				ps = connection.prepareStatement(insertTrackModelActivitySQL);
		
			ps.setBoolean(1, progressStatus);
			ps.setDouble(2, absoluteDuration); 
			ps.setDouble(3, experiencedDuration);
			ps.setInt(4, attemptCount);
			ps.setBoolean(5, suspended);
			ps.setString(6, parentActId);
			ps.setInt(7, idxChildren);
			ps.setBoolean(8, attemptCompletionStatus);
			ps.setBoolean(9, attemptProgressStatus);
		
			saveDoubleNull(ps, 10, scoreRaw);			
			saveDoubleNull(ps, 11, scoreMin);
			saveDoubleNull(ps, 12, scoreMax);		
			saveDoubleNull(ps, 13, scoreScaled);

			ps.setInt(14, idUser);
			ps.setInt(15, idCourse);
			ps.setString(16, activityID);
	
			logger.info(ps.toString());
		
			ps.executeUpdate();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}
	
	private void saveDoubleNull(PreparedStatement ps, int index, Double value) throws SQLException {
		
		if (value == null)
			ps.setNull(index, Types.DOUBLE);
		else
			ps.setDouble(index, value);
	}

	public TrackModel loadTrackModel(String courseId, String learnerId) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		TrackModel trackModel = null;
		ResultSet rs = null;
		
		try {
		
			trackModel = new TrackModel(courseId, learnerId);		
			int idUser = getIDUser(learnerId);
			int idCourse = getIDCourse(courseId);
		
			ps = connection.prepareStatement(selectSuspendedActivitySQL);		
			ps.setInt(1, idUser);
			ps.setInt(2, idCourse);
		
			logger.info("loadTrackModel " + learnerId + " => " + idUser + " || " + courseId + " => " + idCourse);
		
			rs = ps.executeQuery();
			boolean temTM = rs.next();
		
			if (temTM) {
				logger.info("temSuspendedAct " + rs.getString(1));
				trackModel.setSuspendedActivity(rs.getString(1));
				trackModel.setSuspendedData(rs.getString(2));
			}
			
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
		
			
			if (temTM) {
		
				ps = connection.prepareStatement(selectTrackModelObjectivesSQL);
				ps.setInt(1, idUser);
				ps.setInt(2, idCourse);
			
				rs = ps.executeQuery();
			
				while (rs.next()) {
				
					logger.info(String.format("objective %s %s %s %s %s %s",
										  rs.getString(3),rs.getString(4),
										  rs.getString(5),rs.getString(6),
										  rs.getString(7),rs.getString(8)));
				
					String activityID = rs.getString(3);
				
					if (activityID.length() == 0) 
						activityID = null;
				
					String objectiveID = rs.getString(4);
					
					if (objectiveID.length() == 0) 
						objectiveID = null;
					
					boolean progressStatus = rs.getBoolean(5);
					boolean satisfiedStatus = rs.getBoolean(6);
					boolean measureStatus = rs.getBoolean(7);
					double normalizedMeasure = rs.getDouble(8);
				
					trackModel.addObjective(activityID, objectiveID, progressStatus, satisfiedStatus, measureStatus, normalizedMeasure);
				}
				
				try { rs.close(); } catch (Exception e) {}
				try { ps.close(); } catch (Exception e) {}
			
				ps = connection.prepareStatement(selectTrackModelActivitiesSQL);			
				ps.setInt(1, idUser);
				ps.setInt(2, idCourse);
			
				logger.info(ps.toString());
			
				rs = ps.executeQuery();
				
				while (rs.next()) {

					logger.info(String.format("activity %s %s %s %s %s %s",
						  rs.getString(3),rs.getString(4),
						  rs.getString(5),rs.getString(6),
						  rs.getString(7),rs.getString(8),
						  rs.getString(9),rs.getString(10)));

					String activityID = rs.getString(3);
					boolean progressStatus = rs.getBoolean(4);
					double absoluteDuration = rs.getDouble(5);
					double experiencedDuration = rs.getDouble(6);
					int attemptCount = rs.getInt(7);
					boolean suspended = rs.getBoolean(8);
					String parentActId = rs.getString(9);
					int idxChildren = rs.getInt(10);
					boolean attemptCompletionStatus = rs.getBoolean(11);
					boolean attemptProgressStatus = rs.getBoolean(12);
					double scoreRaw = rs.getDouble(13);
					double scoreMin = rs.getDouble(14);
					double scoreMax = rs.getDouble(15);
					double scoreScaled = rs.getDouble(16);
					trackModel.addActivityProgressInformation(
							activityID, parentActId, idxChildren, progressStatus, absoluteDuration, 
							experiencedDuration, attemptCount, suspended,
							attemptCompletionStatus, attemptProgressStatus, scoreRaw, scoreMin, scoreMax, scoreScaled);
				}
			}
			
		} finally {

			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}		
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return trackModel;
	}

	
	public List<Course> listCourses() throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Course> l = null;
		
		try {
		
			ps = connection.prepareStatement(selectCoursesSQL);		
			l = new ArrayList<Course>();
			rs = ps.executeQuery();
		
			while (rs.next()) l.add(new CourseImpl(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6)));

		} finally {
			
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return l;
	}

	
	public List<User> listUsers() throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> l = null;
		
		try {
		
			ps = connection.prepareStatement(selectUsersSQL);	
		
			l = new ArrayList<User>();
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				UserImpl user = new UserImpl();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassw(rs.getString(3));
				user.setAdmin(rs.getBoolean(4));
				l.add(user);
				
			}
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return l;
	}

	
	public Course findCourse(String courseId) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Course c = null;
		
		try {
			
			ps = connection.prepareStatement(selectCourseByCourseIdSQL);
			ps.setString(1, courseId);
			rs = ps.executeQuery();
		
			if (rs.next()) c = new CourseImpl(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
		
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
		}
		
		return c;
	}

	
	public Course findCourseByFolderName(String courseFolder) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Course c = null;
				
		try {
		
			ps = connection.prepareStatement(selectCourseByFolderNameSQL);		
			ps.setString(1, courseFolder);
			rs = ps.executeQuery();

			if (rs.next()) c = new CourseImpl(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6));
		
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return c;
	}

	
	public void beginSaveInteractionInformation(String courseId,
			String learnerId, String activityId, int index, String id,
			String type, String timeStamp, Double weighting,
			String learnerResponse, String result, String latency,
			String description) throws Exception {
		
		
		
		int idUser = getIDUser(learnerId);
		int idCourse = getIDCourse(courseId);
		
		// nao vou mais eliminar as interactions, pois preciso manter um histórico delas. 
		// eliminateTrackModelInteractions(idCourse, idUser, activityId, id);
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(insertTrackModelInteractionsSQL);		
			ps.setInt(1, idUser);
			ps.setInt(2, idCourse);
			ps.setString(3, activityId);
			ps.setInt(4, index);
			ps.setString(5, id);
			ps.setString(6, type);
			ps.setString(7, timeStamp);
			
			if (weighting == null)	ps.setNull(8, java.sql.Types.DOUBLE);
			else ps.setDouble(8, weighting);
		
			ps.setString(9, learnerResponse);
			ps.setString(10, result);
			ps.setString(11, latency);
			ps.setString(12, description);
		
			logger.info(ps.toString());		
			ps.executeUpdate();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	private void eliminateTrackModelInteractions(int idCourse, int idUser, String activityId, String id) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null; 	
		
		try {
		
			if (activityId != null) {
			
				ps = connection.prepareStatement(deleteTrackModelInteractionsSQL);
				ps.setString(3, activityId);
				ps.setString(4, id);
			
			} else connection.prepareStatement(deleteTrackModelInteractionsByCourseSQL);
		
			ps.setInt(1, idUser);
			ps.setInt(2, idCourse);
			ps.executeUpdate();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void finishSaveInteractionInformation(String courseId,
			String learnerId, String activityId, int index, String id) {
		
		
	}

	
	public void saveInteractionCorrectResponse(String courseId,
			String learnerId, String activityId, String id, int idxCorrect,
			String correctResponsesPattern) throws Exception {
		
		
		
	}

	
	public void removeTrackModel(String courseId, String learnerId) throws Exception  {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			int idCourse = getIDCourse(courseId);
			int idLearner = getIDUser(learnerId);
			
			updateSuspendedActivity(idCourse, idLearner, null, null);		
			eliminateTrackModelInteractions(idCourse, idLearner, null, null);		
			
			ps = connection.prepareStatement(deleteTrackModelObjectivesSQL);		
			ps.setInt(1, idLearner);
			ps.setInt(2, idCourse);
			ps.executeUpdate();
		
			try { ps.close(); } catch (Exception e) {}
		
			ps = connection.prepareStatement(deleteTrackModelActivitiesSQL);
		
			ps.setInt(1, idLearner);
			ps.setInt(2, idCourse);
			ps.executeUpdate();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public User findUser(String name) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserImpl userImpl = null;
		
		try {
		
			ps = connection.prepareStatement(selectUserByNickSQL);		
			ps.setString(1, name);
			rs = ps.executeQuery();
		
			if (rs.next()) {
				
				userImpl = new UserImpl();
				userImpl.setId(rs.getInt(1));
				userImpl.setName(rs.getString(2));
				userImpl.setPassw(rs.getString(3));			
				userImpl.setAdmin(rs.getString(4).equals("A")?true:false);
			}
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return userImpl;
	}
	
	
	public void insertUser(User user) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			ps = connection.prepareStatement(insertUserSQL);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassw());
			ps.setString(3, user.isAdmin()?"A":"U");		
			ps.execute();
			
			st = connection.createStatement();
			rs = st.executeQuery(lastIDSQL);
			rs.next();
			((UserImpl)user).setId(rs.getInt(1));
			
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { st.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}		
	}
	
	
	
	public void removeUser(User user) throws Exception {
	
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(deleteUserSQL);
			ps.setString(1, user.getName());
			ps.execute();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}		
		
	}	
	
	
	public void updateUser(String userName, User user) throws Exception {

		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			ps = connection.prepareStatement(editUserSQL);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassw());
			ps.setString(3, user.isAdmin()?"A":"U");
			ps.setString(4, userName);
			ps.executeUpdate();
			ps.close();
			
			ps = connection.prepareStatement(selectUserByNickSQL);
			ps.setString(1, user.getName());
			rs = ps.executeQuery();
			rs.next();
			((UserImpl)user).setId(rs.getInt(1));
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void insertCourse(Course course) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(insertCourseSQL);
			ps.setString(1, course.getFolderName());
			ps.setString(2, course.getId());
			ps.setString(3, course.getTitle());
			ps.setBoolean(4, course.isRemoved());
			ps.setBoolean(5, course.isDerived());		
			ps.execute();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void markCourseAsRemoved(String courseId) throws Exception {

		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(markCourseAsRemovedSQL);		
		//markCourseAsRemoved.setString(1, "Y";
			ps.setBoolean(1, true); // alterado na rev 50
			ps.setString(2, courseId);		
			ps.execute();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void removeCourse(String courseId) throws Exception {

		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		
		try {
		
			ps = connection.prepareStatement(removeCourseSQL);		
			ps.setString(1, courseId);		
			ps.execute();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public void insertCourse(ContentPackage contentPackage) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;

		// TODO: qual a pasta dele ?
		CourseImpl course = new CourseImpl(contentPackage.getOrganizations().getDefaultOrganization().getIdentifier(), null, contentPackage.getOrganizations().getDefaultOrganization().getTitle(), true, false);

		try {
		
			ps = connection.prepareStatement(insertContentPackageStrSQL);		
			insertCourse(course);
			ps.setString(1, course.getId());
			ps.setString(2, contentPackage.toString());
			ps.execute();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
	}

	
	public String getDerivedCoursePackageStr(String courseId) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String retorno = null;
		
		try {
		
			ps = connection.prepareStatement(getDerivedCoursePackageStrSQL);
			ps.setString(1, courseId);
			rs = ps.executeQuery();
		
			if (rs.next()) retorno = rs.getString("DS_CONTENT_PACKAGE");
		
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return retorno;
	}


	public List<UserPractice> listUsersPractice() throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<UserPractice> l = null;
		
		try {
		
			List<User> users = listUsers();
			l = new ArrayList<UserPractice>();
			
			ps = connection.prepareStatement(selectCoursesAccessedSQL);	
			rs = ps.executeQuery();
			
			while (rs.next()) {
			
				int idUser = rs.getInt(1);
				UserPracticeImpl up = new UserPracticeImpl();
			
				for (User u:users) {
				
					if (((UserImpl)u).getId() == idUser) {
					
						up.setUser(u);
						break;
					}
				}
				
				up.setQuantity(rs.getInt(2));
				up.setTime(rs.getDouble(3));
				up.setLastTimeAccessed(new java.util.Date(rs.getTimestamp(4).getTime()));
				l.add(up);
			}
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return l;
	}

	public List<CoursePractice> listCoursesPractice(String learnerId) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CoursePractice> l = null;
		
		try {
		
			l = new ArrayList<CoursePractice>();
			List<Course> courses = listCourses();		
		
			ps = connection.prepareStatement(selectCoursesAccessedOneUserSQL);		
			ps.setInt(1, getIDUser(learnerId));		
			rs = ps.executeQuery();
			
			while (rs.next()) {
			
				int courseID = rs.getInt(1);
				CoursePracticeImpl cp = new CoursePracticeImpl();
			
				for (Course c:courses) {
					
					if (((CourseImpl)c).getIdCode() == courseID) {
						
						cp.setCourse(c);
						break;
					}
				}
			
				cp.setTime(rs.getDouble(2));
				cp.setLastTimeAccessed(new java.util.Date(rs.getTimestamp(3).getTime()));			
				l.add(cp);
			}
			
		} finally {
		
			try { rs.close(); } catch (Exception e) {}
			try { ps.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}
		
		return l;
	}

	public void saveRequestActivity(User user, Course course, LearningActivity learningActivity) throws Exception {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		try {
		
			int attempt = 0;
			double duration = 0;
			int idUser = getIDUser(user.getName());
			int idCourse = getIDCourse(course.getId());		
			String idAct = learningActivity.getLearningActivityId();		
			
			ps = connection.prepareStatement(selectRequestActivitySQL);		
			ps.setInt(1, idUser);
			ps.setInt(2, idCourse);
			ps.setString(3, idAct);		
			rs = ps.executeQuery();
			
			if (rs.next()) {
				
				ps2 = connection.prepareStatement(updateRequestActivitySQL);
				attempt = rs.getInt(1);
				duration = rs.getDouble(2);
				
			} else ps2 = connection.prepareStatement(insertRequestActivitySQL);
		
			Timestamp now = new Timestamp(new java.util.Date().getTime());
		
			ps2.setInt(1, ++attempt);
			ps2.setDouble(2, duration + learningActivity.getAttemptExperiencedDuration());
			ps2.setTimestamp(3, now);
			ps2.setInt(4, idUser);
			ps2.setInt(5, idCourse);
			ps2.setString(6, learningActivity.getLearningActivityId());			
			ps2.execute();
			
		} finally {
		
			try { ps.close(); } catch (Exception e) {}
			try { rs.close(); } catch (Exception e) {}			
			try { ps2.close(); } catch (Exception e) {}
			try { connection.close(); } catch (Exception e) {}
			
		}		
	}
}
