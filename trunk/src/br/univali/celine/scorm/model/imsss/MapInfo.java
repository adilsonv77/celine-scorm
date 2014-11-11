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
package br.univali.celine.scorm.model.imsss;

public class MapInfo {
	
	private String targetObjectiveID;
	private boolean readSatisfiedStatus = true;
	private boolean readNormalizedMeasure = true;
	private boolean writeSatisfiedStatus = false;
	private boolean writeNormalizedMeasure = false;
	
	public boolean isReadNormalizedMeasure() {
		return readNormalizedMeasure;
	}
	public void setReadNormalizedMeasure(boolean readNormalizedMeasure) {
		this.readNormalizedMeasure = readNormalizedMeasure;
	}
	public boolean isReadSatisfiedStatus() {
		return readSatisfiedStatus;
	}
	public void setReadSatisfiedStatus(boolean readSatisfiedStatus) {
		this.readSatisfiedStatus = readSatisfiedStatus;
	}
	public String getTargetObjectiveID() {
		return targetObjectiveID;
	}
	public void setTargetObjectiveID(String targetObjectiveID) {
		this.targetObjectiveID = targetObjectiveID;
	}
	public boolean isWriteNormalizedMeasure() {
		return writeNormalizedMeasure;
	}
	public void setWriteNormalizedMeasure(boolean writeNormalizedMeasure) {
		this.writeNormalizedMeasure = writeNormalizedMeasure;
	}
	public boolean isWriteSatisfiedStatus() {
		return writeSatisfiedStatus;
	}
	public void setWriteSatisfiedStatus(boolean writeSatisfiedStatus) {
		this.writeSatisfiedStatus = writeSatisfiedStatus;
	}
	public String toString() {
		return String.format("<imsss:mapInfo targetObjectiveID=\"%s\" readSatisfiedStatus=\"%s\" " +
									  "readNormalizedMeasure=\"%s\" writeSatisfiedStatus=\"%s\" " +
									  "writeNormalizedMeasure=\"%s\"/>\n",
									  
				new Object[]{targetObjectiveID, readSatisfiedStatus, readNormalizedMeasure, writeSatisfiedStatus, writeNormalizedMeasure});
	}
	
}
