package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.model.LearningActivity;

public class FlowSubprocessResult {

	private LearningActivity identifiedActivity;
	private boolean deliverable = false;
	private boolean endSequencingSession = false;
	private int exception;
	
	public FlowSubprocessResult(int exception) {
		this.exception = exception;
		this.deliverable = exception == 0;
	}
	
	public FlowSubprocessResult(LearningActivity identifiedActivity) {
		this.identifiedActivity = identifiedActivity;
		this.deliverable = true;
	}
	
	public FlowSubprocessResult() {}
	
	public boolean isDeliverable() {
		return deliverable;
	}
	public void setDeliverable(boolean deliverable) {
		this.deliverable = deliverable;
	}
	public boolean isEndSequencingSession() {
		return endSequencingSession;
	}
	public void setEndSequencingSession(boolean endSequencingSession) {
		this.endSequencingSession = endSequencingSession;
	}
	public int getException() {
		return exception;
	}
	public void setException(int exception) {
		this.exception = exception;
	}
	public LearningActivity getIdentifiedActivity() {
		return identifiedActivity;
	}
	public void setIdentifiedActivity(LearningActivity identifiedActivity) {
		this.identifiedActivity = identifiedActivity;
	}
	
	
}
