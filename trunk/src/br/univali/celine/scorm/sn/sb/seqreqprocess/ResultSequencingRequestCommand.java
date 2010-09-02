package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.LearningActivity;

public class ResultSequencingRequestCommand {

	private LearningActivity deliveryRequest;
	private boolean endSequencingSession;
	private int exception;
	private boolean valid;
	
	public ResultSequencingRequestCommand() {
		this.valid = true;
	}
	
	public ResultSequencingRequestCommand(int exception) {
		this.valid = false;
		this.exception = exception;
	}
	
	public LearningActivity getDeliveryRequest() {
		return deliveryRequest;
	}
	public void setDeliveryRequest(LearningActivity deliveryRequest) {
		this.deliveryRequest = deliveryRequest;
	}
	public int getException() {
		return exception;
	}
	public boolean isEndSequencingSession() {
		return endSequencingSession;
	}
	public void setEndSequencingSession(boolean endSequencingSession) {
		this.endSequencingSession = endSequencingSession;
	}

	public boolean isValid() {
		return valid;
	}
	
}
