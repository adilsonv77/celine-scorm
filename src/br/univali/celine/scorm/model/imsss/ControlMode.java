package br.univali.celine.scorm.model.imsss;

public class ControlMode {

	private boolean choice = true; 
	private boolean choiceExit = true;
	private boolean flow = false;
	private boolean forwardOnly = false;
	private boolean useCurrentAttemptObjectiveInfo = true;
	private boolean useCurrentAttemptProgressInfo = true;
	
	public boolean isChoice() {
		return choice;
	}
	public void setChoice(boolean choice) {
		this.choice = choice;
	}
	public boolean isChoiceExit() {
		return choiceExit;
	}
	public void setChoiceExit(boolean choiceExit) {
		this.choiceExit = choiceExit;
	}
	public boolean isFlow() {
		return flow;
	}
	public void setFlow(boolean flow) {
		this.flow = flow;
	}
	public boolean isForwardOnly() {
		return forwardOnly;
	}
	public void setForwardOnly(boolean forwardOnly) {
		this.forwardOnly = forwardOnly;
	}
	public boolean isUseCurrentAttemptObjectiveInfo() {
		return useCurrentAttemptObjectiveInfo;
	}
	public void setUseCurrentAttemptObjectiveInfo(
			boolean useCurrentAttemptObjectiveInfo) {
		this.useCurrentAttemptObjectiveInfo = useCurrentAttemptObjectiveInfo;
	}
	public boolean isUseCurrentAttemptProgressInfo() {
		return useCurrentAttemptProgressInfo;
	}
	public void setUseCurrentAttemptProgressInfo(
			boolean useCurrentAttemptProgressInfo) {
		this.useCurrentAttemptProgressInfo = useCurrentAttemptProgressInfo;
	}
	@Override
	public String toString() {
		return "<imsss:controlMode choice=\""+this.choice+"\" choiceExit=\""+this.choiceExit+"\" flow=\""+this.flow+"\"" +
				" forwardOnly=\""+this.forwardOnly+"\" useCurrentAttemptObjectiveInfo=\""+isUseCurrentAttemptObjectiveInfo()+"\"" +
				" useCurrentAttemptProgressInfo=\""+isUseCurrentAttemptProgressInfo()+"\" /> \n";
	}
	public void assign(ControlMode controlMode) {
		// TODO Auto-generated method stub
		
	}
	
}
