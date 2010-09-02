package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.model.SequencingRequest;

public class TerminationRequestResult {

	public static TerminationRequestResult buildValid(SequencingRequest sequencingRequest) {
		
		TerminationRequestResult trr = new TerminationRequestResult();
		trr.setValid(true);
		trr.setSequencingRequest(sequencingRequest);
		return trr;
		
	}
	
	public static TerminationRequestResult buildNotValid(int exception) {
		
		TerminationRequestResult trr = new TerminationRequestResult();
		trr.setException(exception);
		return trr;
		
	}
	
	private boolean valid = false; 
	private SequencingRequest sequencingRequest;
	private int exception;
	
	public int getException() {
		return exception;
	}
	public void setException(int exception) {
		this.exception = exception;
	}
	public SequencingRequest getSequencingRequest() {
		return sequencingRequest;
	}
	public void setSequencingRequest(SequencingRequest sequencingRequest) {
		this.sequencingRequest = sequencingRequest;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
	
}
