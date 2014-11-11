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

import java.util.ArrayList;
import java.util.List;

public class TrackModel {

	private String learnerId, courseId;
	private String suspendedActivity;
	private List<TrackObjective> objectives = new ArrayList<TrackObjective>();
	private List<TrackActivityInfo> activitiesInfo = new ArrayList<TrackActivityInfo>();
	private String suspendedData;

	public TrackModel(String courseId, String learnerId) {
		this.learnerId = learnerId;
		this.courseId = courseId;
	}
	
	public List<TrackObjective> getObjectives() {
		return objectives;
	}

	public List<TrackActivityInfo> getActivities() {
		return activitiesInfo;
	}
	
	public void addObjective(String activityID, String objectiveID, boolean progressStatus,
			boolean satisfiedStatus, boolean measureStatus,
			double normalizedMeasure) {
		
		TrackObjective obj = new TrackObjective(activityID, objectiveID, progressStatus,
				satisfiedStatus, measureStatus, normalizedMeasure);
		this.objectives .add(obj);
		
	}

	public void addActivityProgressInformation(String activityID, String parentId, int idxChildren,
			boolean progressStatus, double absoluteDuration,
			double experiencedDuration, int attemptCount, boolean suspended, 
			boolean attemptCompletionStatus, boolean attemptProgressStatus,
			Double scoreRaw, Double scoreMin, Double scoreMax, Double scoreScaled) {

		TrackActivityInfo attemptInfo = new TrackActivityInfo(
				activityID,
				parentId, idxChildren,
				progressStatus, 
				absoluteDuration, 
				experiencedDuration,
				attemptCount,suspended,attemptCompletionStatus,attemptProgressStatus,
				scoreRaw,scoreMin,scoreMax, scoreScaled);
		
		this.activitiesInfo.add(attemptInfo);
		
	}
	
	public String getLearnerId() {
		return learnerId;
	}

	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSuspendedActivity() {
		return suspendedActivity;
	}

	public void setSuspendedActivity(String currentActivity) {
		this.suspendedActivity = currentActivity;
	}

	public void setSuspendedData(String suspendedData) {
		this.suspendedData = suspendedData;
	}
	
	public String getSuspendedData() {
		return suspendedData;
	}

}
