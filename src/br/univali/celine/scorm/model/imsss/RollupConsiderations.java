package br.univali.celine.scorm.model.imsss;

public class RollupConsiderations {

	private RequiredForRollupConsiderations requiredForSatisfied = RequiredForRollupConsiderations.always;
	private RequiredForRollupConsiderations requiredForNotSatisfied = RequiredForRollupConsiderations.always;
	private RequiredForRollupConsiderations requiredForCompleted = RequiredForRollupConsiderations.always;
	private RequiredForRollupConsiderations requiredForIncomplete = RequiredForRollupConsiderations.always;
	private boolean measureSatisfactionIfActive = true;
	
	public String getRequiredForSatisfied() {
		return requiredForSatisfied.name();
	}
	public void setRequiredForSatisfied(String requiredForSatisfied) {
		this.requiredForSatisfied = RequiredForRollupConsiderations.valueOf(requiredForSatisfied);
	}
	public String getRequiredForNotSatisfied() {
		return requiredForNotSatisfied.name();
	}
	public void setRequiredForNotSatisfied(String requiredForNotSatisfied) {
		this.requiredForNotSatisfied = RequiredForRollupConsiderations.valueOf(requiredForNotSatisfied);
	}
	public String getRequiredForCompleted() {
		return requiredForCompleted.name();
	}
	public void setRequiredForCompleted(String requiredForCompleted) {
		this.requiredForCompleted = RequiredForRollupConsiderations.valueOf(requiredForCompleted);
	}
	public String getRequiredForIncomplete() {
		return requiredForIncomplete.name();
	}
	public void setRequiredForIncomplete(String requiredForIncomplete) {
		this.requiredForIncomplete = RequiredForRollupConsiderations.valueOf(requiredForIncomplete);
	}
	public boolean isMeasureSatisfactionIfActive() {
		return measureSatisfactionIfActive;
	}
	public void setMeasureSatisfactionIfActive(boolean measureSatisfactionIfActive) {
		this.measureSatisfactionIfActive = measureSatisfactionIfActive;
	}
	public RequiredForRollupConsiderations getEnumRequiredForSatisfied() {
		return this.requiredForSatisfied;
	}
	public RequiredForRollupConsiderations getEnumRequiredForNotSatisfied() {
		return this.requiredForSatisfied;
	}
	public RequiredForRollupConsiderations getEnumRequiredForCompleted() {
		return this.requiredForSatisfied;
	}
	public RequiredForRollupConsiderations getEnumRequiredForIncomplete() {
		return this.requiredForSatisfied;
	}

	@Override
	public String toString() {
		
		return String.format("<adlseq:rollupConsiderations requiredForSatisfied=\"%s\" " +
								                   "requiredForNotSatisfied=\"%s\" " +
								                   "requiredForCompleted=\"%s\" " +
								                   "requiredForIncomplete=\"%s\" " +
								                   "measureSatisfactionIfActive=\"%s\"/>\n",
				  new Object[]{requiredForSatisfied, requiredForNotSatisfied, 
							   requiredForCompleted, requiredForIncomplete, 
							   measureSatisfactionIfActive});
	}
	public void assign(RollupConsiderations rollupConsiderations) {
		// TODO Auto-generated method stub
		
	}
	
}
