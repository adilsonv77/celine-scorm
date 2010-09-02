package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

public class FlowTreeTraversalSubprocessResult {

	private LearningActivity nextActivity;
	private TraversalDirection traversalDirection = TraversalDirection.none;
	private int exception;
	private boolean endSequencingSession = false;

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

	public TraversalDirection getTraversalDirection() {
		return traversalDirection;
	}

	public void setTraversalDirection(TraversalDirection traversalDirection) {
		this.traversalDirection = traversalDirection;
	}

	public LearningActivity getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(LearningActivity nextActivity) {
		this.nextActivity = nextActivity;
	}
	
}
