package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;

public class NavigationRequestResult {

	public static NavigationRequestResult buildValid(SequencingRequest sequencingRequest) {
		NavigationRequestResult res = new NavigationRequestResult();
		res.setSequencingRequest(sequencingRequest);
		return res;
	}
	
	public static NavigationRequestResult buildNotValid(int exception) {
		NavigationRequestResult res = new NavigationRequestResult();
		res.setException(exception);
		res.setNavigationRequestValid(false);
		return res;
	}
	
	
	
	private boolean navigationRequestValid = true;
	private SequencingRequest sequencingRequest = null;
	private TerminationRequest terminationRequest = null;
	private LearningActivity targetActivity;
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
	public LearningActivity getTargetActivity() {
		return targetActivity;
	}
	public void setTargetActivity(LearningActivity targetActivity) {
		this.targetActivity = targetActivity;
	}
	public TerminationRequest getTerminationRequest() {
		return terminationRequest;
	}
	public void setTerminationRequest(TerminationRequest terminationRequest) {
		this.terminationRequest = terminationRequest;
	}
	public boolean isNavigationRequestValid() {
		return navigationRequestValid;
	}
	public void setNavigationRequestValid(boolean navigationRequestValid) {
		this.navigationRequestValid = navigationRequestValid;
	}
	public boolean isTerminationRequest() {
		return terminationRequest != null;
	}
	public boolean isSequencingRequest() {
		return sequencingRequest != null;
	}
	
	
}
