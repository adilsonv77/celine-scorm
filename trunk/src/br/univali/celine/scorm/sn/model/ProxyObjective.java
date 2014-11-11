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


// é um objetivo local que tem uma referencia para um objetivo global

public class ProxyObjective extends LocalObjective {

	private GlobalObjective readNormalizedMeasure, writeNormalizedMeasure,
			readSatisfiedStatus, writeSatisfiedStatus;

	public ProxyObjective(
			br.univali.celine.scorm.model.imsss.Objective referencedObjective,
			GlobalObjective readNormalizedMeasure,
			GlobalObjective writeNormalizedMeasure,
			GlobalObjective readSatisfiedStatus,
			GlobalObjective writeSatisfiedStatus) {

		super(referencedObjective);
		this.readNormalizedMeasure = readNormalizedMeasure;
		this.writeNormalizedMeasure = writeNormalizedMeasure;
		this.readSatisfiedStatus = readSatisfiedStatus;
		this.writeSatisfiedStatus = writeSatisfiedStatus;
	}

	public ProxyObjective(br.univali.celine.scorm.model.imsss.Objective referencedObjective) {
		this(referencedObjective, null, null, null, null);
	}

	/*
	 * Read Objective Satisfied Status.
	 * 
	 * Indicates that the Objective Progress Status and Objective Satisfied
	 * Status values for the identified local objective (Activity Objective ID),
	 * should be retrieved (True or False) from the identified shared global
	 * objective (Target Objective ID), when the progress for the local
	 * objective is undefined (Objective Progress Status for the identified
	 * local objective is False).
	 * 
	 * This operation does not change the Objective Information associated with
	 * the local objective.
	 */

	@Override
	public boolean isProgressStatus() {
		if (readSatisfiedStatus == null)
			return super.isProgressStatus();
		return readSatisfiedStatus.isProgressStatus();
	}

	@Override
	public boolean isSatisfiedStatus() {
		if (readSatisfiedStatus == null)
			return super.isSatisfiedStatus();

		return readSatisfiedStatus.isSatisfiedStatus();
	}

	/*
	 * Write Objective Satisfied Status.
	 * 
	 * Indicates that the Objective Progress Status and Objective Satisfied
	 * Status values, for the identified local objective (Activity Objective
	 * ID), should be transferred (True or False) to the identified shared
	 * global objective (Target Objective ID), upon termination of an attempt on
	 * the activity.
	 */

	@Override
	public void setProgressStatus(boolean progressStatus) {
		super.setProgressStatus(progressStatus);
	}

	@Override
	public void setSatisfiedStatus(boolean objectiveSatisfiedStatus) {

		super.setSatisfiedStatus(objectiveSatisfiedStatus);
	}

	/*
	 * Read Objective Normalized Measure.
	 * 
	 * Indicates that the Objective Measure Status and Objective Normalized
	 * Measure values for the identified local objective (Activity Objective
	 * ID), should be retrieved (True or False) from the identified shared
	 * global objective (Target Objective ID), when the measure for the local
	 * object is undefined (Objective Measure Status for the identified local
	 * objective is False).
	 * 
	 * This operation does not change the Objective Information associated with
	 * the local objective.
	 */

	@Override
	public double getNormalizedMeasure() {
		if (super.isMeasureStatus() == false && readNormalizedMeasure != null)
			return readNormalizedMeasure.getNormalizedMeasure();

		return super.getNormalizedMeasure();
	}

	@Override
	public boolean isMeasureStatus() {
		if (super.isMeasureStatus() == false && readNormalizedMeasure != null)
			return readNormalizedMeasure.isMeasureStatus();
		return super.isMeasureStatus();
	}

	/*
	 * Indicates that the Objective Measure Status and Objective Normalized
	 * Measure values, for the identified local objective (Activity Objective
	 * ID), should be transferred (True or False) to the identified shared
	 * global objective (Target Objective ID), upon termination of an attempt 
	 * on the activity.
	 */
	@Override
	public void setNormalizedMeasure(double totalWeighted) {
		super.setNormalizedMeasure(totalWeighted);
	}

	@Override
	public void setMeasureStatus(boolean measureStatus) {
		super.setMeasureStatus(measureStatus);
	}

	/* ********************************************************************** */
	
	public void updateGlobalObjective() {

		if (writeNormalizedMeasure != null) {
			writeNormalizedMeasure.setMeasureStatus(isMeasureStatus());
			writeNormalizedMeasure.setNormalizedMeasure(getNormalizedMeasure());
		}
		
		if (writeSatisfiedStatus != null) {
			writeSatisfiedStatus.setSatisfiedStatus(isSatisfiedStatus());
			writeSatisfiedStatus.setProgressStatus(isProgressStatus());
		}
		
	}

	public String getReadNormalizedMeasureId() {
		if (readNormalizedMeasure != null)
			return readNormalizedMeasure.getObjectiveID();
		return "";
	}

	private String getWriteNormalizedMeasureId() {
		if (writeNormalizedMeasure != null) {
			return writeNormalizedMeasure.getObjectiveID();
		}
		return "";
	}

	private String getReadSatisfiedStatusId() {
		if (readSatisfiedStatus != null)
			return readSatisfiedStatus.getObjectiveID();
		return "";
	}

	private String getWriteSatisfiedStatusId() {
		if (writeSatisfiedStatus != null)
			return writeSatisfiedStatus.getObjectiveID();
		return "";
	}

	@Override
	public String toString() {
		return getObjectiveID() + " [" + getReadNormalizedMeasureId() + "] ["
				+ getWriteNormalizedMeasureId() + "] ["
				+ getReadSatisfiedStatusId() + "] ["
				+ getWriteSatisfiedStatusId() + "]";
	}

}
