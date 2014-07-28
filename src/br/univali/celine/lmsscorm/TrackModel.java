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
