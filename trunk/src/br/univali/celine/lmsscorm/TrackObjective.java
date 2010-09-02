package br.univali.celine.lmsscorm;

public class TrackObjective {

	private String objectiveID;
	private boolean progressStatus;
	private boolean satisfiedStatus;
	private boolean measureStatus;
	private double normalizedMeasure;
	private String activityID;
	
	public TrackObjective(String activityID, String objectiveID, boolean progressStatus,
			boolean satisfiedStatus, boolean measureStatus,
			double normalizedMeasure) {
		this.activityID = activityID;
		this.objectiveID = objectiveID;
		this.progressStatus = progressStatus;
		this.satisfiedStatus = satisfiedStatus;
		this.measureStatus = measureStatus;
		this.normalizedMeasure = normalizedMeasure;
	}

	public String getObjectiveID() {
		return objectiveID;
	}

	public boolean isProgressStatus() {
		return progressStatus;
	}

	public boolean isSatisfiedStatus() {
		return satisfiedStatus;
	}

	public boolean isMeasureStatus() {
		return measureStatus;
	}

	public double getNormalizedMeasure() {
		return normalizedMeasure;
	}

	public String getActivityID() {
		return activityID;
	}
	
	@Override
	public String toString() {
		return objectiveID + " " + activityID;
	}
	
	
}
