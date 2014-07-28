package br.univali.celine.scorm.model.imsss;

public class ConstrainedChoiceConsiderations {
	private boolean preventActivation;
	private boolean constrainChoice;
	
	public boolean isPreventActivation() {
		return preventActivation;
	}
	public void setPreventActivation(boolean preventActivation) {
		this.preventActivation = preventActivation;
	}
	public boolean isConstrainChoice() {
		return constrainChoice;
	}
	public void setConstrainChoice(boolean constrainChoice) {
		this.constrainChoice = constrainChoice;
	}
	
	@Override
	public String toString() {
		
		return String.format("<adlseq:constrainedChoiceConsiderations preventActivation=\"%s\" constrainChoice=\"%s\"/>\n",
								new Object[]{preventActivation, constrainChoice});
	}
	public void assign(
			ConstrainedChoiceConsiderations constrainedChoiceConsiderations) {
		// TODO Auto-generated method stub
		
	}
}
