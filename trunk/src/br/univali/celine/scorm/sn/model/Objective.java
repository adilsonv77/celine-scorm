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
package br.univali.celine.scorm.sn.model;

public abstract class Objective {

	public abstract String getObjectiveID();
	
	private boolean progressStatus;
	private boolean measureStatus;
	
	public boolean isProgressStatus() {
		return progressStatus;
	}
	
	public void setProgressStatus(boolean progressStatus) {
		this.progressStatus = progressStatus;
	}

	public boolean isMeasureStatus() {
		return measureStatus;
	}
	
	public void setMeasureStatus(boolean measureStatus) {
		this.measureStatus = measureStatus;
	}

	
	public SuccessStatus getSuccessStatus() {
		if (isProgressStatus() == false)
			return SuccessStatus.unknown;
		
		if (isSatisfiedStatus())
			return SuccessStatus.passed;
		
		return SuccessStatus.failed;

	}
	
	public void setSuccessStatus(String successStatus) {
		// see sequencing impacts at cmi.objectives.n.success_status in RTE Book
		
		// se for diferente de unknown entao progress status = true
		setProgressStatus(successStatus.equals(SuccessStatus.unknown.toString()) == false); 
		
		// se for igual a passed entao recebe true... 
		setSatisfiedStatus(successStatus.equals(SuccessStatus.passed.toString()));

	}

	private boolean satisfiedStatus;
	
	private double normalizedMeasure;
	private boolean normalizedMeasureInitialized = false;
	
	public boolean isSatisfiedStatus() {
		return this.satisfiedStatus;
	}

	public void setSatisfiedStatus(boolean objectiveSatisfiedStatus) {
		this.satisfiedStatus = objectiveSatisfiedStatus;
	}

	public double getNormalizedMeasure() {
		return this.normalizedMeasure;
	}
	

	public boolean isNormalizedMeasureInitialized() {
		return normalizedMeasureInitialized;
	}

	public void setNormalizedMeasure(double totalWeighted) {
		this.normalizedMeasure = totalWeighted;
		normalizedMeasureInitialized = true;
		setMeasureStatus(true);
	}

	private CompletionStatusValue completionStatus = CompletionStatusValue.unknown;

	public CompletionStatusValue getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(CompletionStatusValue completionStatus) {
		this.completionStatus = completionStatus;
	}
	
	private Double scoreRaw, scoreMin, scoreMax;
	private String description;
	private Double progressMeasure;

	public Double getScoreRaw() {
		return scoreRaw;
	}

	public void setScoreRaw(Double scoreRaw) {
		this.scoreRaw = scoreRaw;
	}

	public Double getScoreMin() {
		return scoreMin;
	}

	public void setScoreMin(Double scoreMin) {
		this.scoreMin = scoreMin;
	}

	public Double getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(Double scoreMax) {
		this.scoreMax = scoreMax;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getProgressMeasure() {
		return this.progressMeasure;
	}

	public void setProgressMeasure(Double progressMeasure) {
		this.progressMeasure = progressMeasure;
	}
	
	
}
