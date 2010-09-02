package br.univali.celine.scorm.sn.db;

public class DeliveryRequestResult {

	private boolean valid;
	private int exception;

	public DeliveryRequestResult() {
		this.valid = true;
	}
	
	public DeliveryRequestResult(int exception) {
		this.valid = false;
		this.exception = exception;
	}
	
	public int getException() {
		return exception;
	}

	public boolean isValid() {
		return valid;
	}
	
	
}
