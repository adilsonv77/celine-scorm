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
package br.univali.celine.lmsscorm;

import java.util.List;

import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.sn.model.LearningActivity;

public interface DAO {

	void initialize() throws Exception;

	/* user management functions */ 
	void insertUser(User user) throws Exception;
	
	void updateUser(String userName, User user) throws Exception;
	
	void removeUser(User user) throws Exception;

	User login(String user, String passw) throws Exception;
	
	List<User> listUsers() throws Exception;
	
	User findUser(String name) throws Exception;

	/* course management functions */
	
	void insertCourse(Course course) throws Exception; 
	
	void insertCourse(ContentPackage contentPackage) throws Exception;
	
	void markCourseAsRemoved(String courseId) throws Exception;
	
	void removeCourse(String courseId) throws Exception;
	
	Course findCourse(String courseId) throws Exception;
	
	Course findCourseByFolderName(String courseFolderName) throws Exception;
	
	List<Course> listCourses() throws Exception;
	
	String getDerivedCoursePackageStr(String courseId) throws Exception;

	/* user and course relation management functions */
	
	boolean userIsRegisteredAtCourse(User user, String courseId)
			throws Exception;

	boolean userHasRightsAtCourse(User user, String courseId) throws Exception;

	void registerUserCourse(User user, String courseId) throws Exception;

	void unregisterUserCourse(User user, String courseId) throws Exception;

	
	/* Track model management functions */
	
	void removeTrackModel(String courseId, String learnerId) throws Exception;
	
	TrackModel loadTrackModel(String courseId, String learnerId)
			throws Exception;

	void beginSaveTrackModel(String courseId, String learnerId,
			String suspendedActivityId, String suspendedData) throws Exception;

	// SN Book 4.2.1.2
	void saveObjectiveProgressInformation(String courseId, String learnerId,
			String activityId, String objectiveID, boolean progressStatus,
			boolean satisfiedStatus, boolean measureStatus,
			double normalizedMeasure) throws Exception;

	/**
	 *  SN Book 4.2.1.3
	 *  
	 *  parenctActId: serve para manter o availableChildren
	 *  idxChildren: o indice dessa atividade dentro de availableChildren
	 * @param scoreRaw 
	 * @param successStatus 
	 * @param c 
	 * @param attempt 
	 *  
	 */
	void saveActivityProgressInformation(String courseId, String learnerId,
			String activityId, String parentActId, int idxChildren, boolean progressStatus, double absoluteDuration,
			double experiencedDuration, int attemptCount, boolean suspended, 
			boolean attemptCompletionStatus, boolean attemptProgressStatus,
			String completionStatus, String successStatus, 
			Double scoreRaw, Double scoreMin, Double scoreMax, Double scoreScaled) throws Exception;

	void beginSaveInteractionInformation(String courseId, String learnerId,
			String activityId, int index, String id, String type,
			String timeStamp, Double weighting, String learnerResponse,
			String result, String latency, String description) throws Exception;

	void saveInteractionCorrectResponse(String courseId, String learnerId,
			String activityId, String id, int idxCorrect,
			String correctResponsesPattern) throws Exception;

	void finishSaveInteractionInformation(String courseId, String learnerId,
			String activityId, int index, String id) throws Exception;

	void finishSaveTrackModel() throws Exception;

	List<UserPractice> listUsersPractice() throws Exception;
	List<CoursePractice> listCoursesPractice(String learnerId) throws Exception;

	void saveRequestActivity(User user, Course course, LearningActivity learningActivity) throws Exception;



}
