package br.univali.celine.scorm.sn.model;


public class NavigationRequest {

	private NavigationRequestType navigationRequestCommand;
	private LearningActivity learningActivity; // usado no choice
	
	public NavigationRequest(LearningActivity learningActivity) {
		this.navigationRequestCommand = NavigationRequestType.Choice;
		this.learningActivity = learningActivity;
	}

	public NavigationRequest(NavigationRequestType navigationRequestCommand) {
		this.navigationRequestCommand = navigationRequestCommand;
	}

	public LearningActivity getLearningActivity() {
		return learningActivity;
	}

	public NavigationRequestType getNavigationRequestCommand() {
		return navigationRequestCommand;
	}
	
}
