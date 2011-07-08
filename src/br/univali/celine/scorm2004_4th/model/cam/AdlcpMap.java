package br.univali.celine.scorm2004_4th.model.cam;

public class AdlcpMap {

	private String targetID;
	private boolean readSharedData = true;
	private boolean writeSharedData = true;
	
	public String getTargetID() {
		return targetID;
	}
	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}
	public boolean isReadSharedData() {
		return readSharedData;
	}
	public void setReadSharedData(boolean readSharedData) {
		this.readSharedData = readSharedData;
	}
	public boolean isWriteSharedData() {
		return writeSharedData;
	}
	public void setWriteSharedData(boolean writeSharedData) {
		this.writeSharedData = writeSharedData;
	}
	
	
	
}
