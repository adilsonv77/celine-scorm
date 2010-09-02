package br.univali.celine.scorm.sn.sb;

public class ChoiceActivityTraversalSubprocessResult {

	private int exception;

	public ChoiceActivityTraversalSubprocessResult() {
		
	}
	
	public ChoiceActivityTraversalSubprocessResult(int exception) {
		this.exception = exception;
	}

	public int getException() {
		return exception;
	}

	public boolean isReachable() { return exception == 0; }
	
}
