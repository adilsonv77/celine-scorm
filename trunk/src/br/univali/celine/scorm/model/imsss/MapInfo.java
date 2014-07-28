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
