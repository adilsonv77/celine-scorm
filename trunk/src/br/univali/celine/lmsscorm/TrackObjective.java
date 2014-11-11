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
