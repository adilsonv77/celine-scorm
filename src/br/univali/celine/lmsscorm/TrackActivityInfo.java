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


public class TrackActivityInfo {

	private int attemptCount;
	private boolean progressStatus;
	private double absoluteDuration;
	private double experiencedDuration;
	private boolean suspended;
	private String id;
	private String parentId;
	private int indexChildren;
	private boolean attemptProgressStatus;
	private boolean attemptCompletionStatus;
	private Double scoreRaw;
	private Double scoreScaled;
	private Double scoreMax;
	private Double scoreMin;

	public TrackActivityInfo(String id, String parentId, int indexChildren, 
							 boolean progressStatus, double absoluteDuration, 
							 double experiencedDuration, int attemptCount, 
							 boolean suspended, boolean attemptCompletionStatus, 
							 boolean attemptProgressStatus, 
							 Double scoreRaw, Double scoreMin, Double scoreMax, Double scoreScaled) {
		this.id = id;
		this.parentId = parentId;
		this.indexChildren = indexChildren;
		this.progressStatus = progressStatus ;
		this.absoluteDuration = absoluteDuration;
		this.experiencedDuration = experiencedDuration;
		this.attemptCount = attemptCount;
		this.suspended = suspended;
		this.attemptCompletionStatus = attemptCompletionStatus; 
		this.attemptProgressStatus = attemptProgressStatus;
		this.scoreRaw = scoreRaw;
		this.scoreMin = scoreMin;
		this.scoreMax = scoreMax;
		this.scoreScaled = scoreScaled;
	}

	public int getIndexChildren() {
		return indexChildren;
	}

	public String getId() {
		return id;
	}

	public int getAttemptCount() {
		return attemptCount;
	}

	public boolean isProgressStatus() {
		return progressStatus;
	}

	public double getAbsoluteDuration() {
		return absoluteDuration;
	}

	public double getExperiencedDuration() {
		return experiencedDuration;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public String getParentId() {
		return parentId;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s %s %s %s %s %s", id, indexChildren, attemptCount, progressStatus, absoluteDuration, experiencedDuration, suspended, parentId);
	}

	public boolean isAttemptProgressStatus() {
		return attemptProgressStatus;
	}

	public boolean isAttemptCompletionStatus() {
		return attemptCompletionStatus;
	}

	public Double getScoreRaw() {
		return scoreRaw;
	}

	public Double getScoreScaled() {
		return scoreScaled;
	}

	public Double getScoreMax() {
		return scoreMax;
	}

	public Double getScoreMin() {
		return scoreMin;
	}

}
