package br.univali.celine.scorm.model.imsss;

public class DeliveryControls {

	private boolean tracked = true;
	private boolean completionSetByContent = false;
	private boolean objectiveSetByContent = false;
	
	public boolean isTracked() {
		return tracked;
	}
	public void setTracked(boolean tracked) {
		this.tracked = tracked;
	}
	public boolean isCompletionSetByContent() {
		return completionSetByContent;
	}
	public void setCompletionSetByContent(boolean completionSetByContent) {
		this.completionSetByContent = completionSetByContent;
	}
	public boolean isObjectiveSetByContent() {
		return objectiveSetByContent;
	}
	public void setObjectiveSetByContent(boolean objectiveSetByContent) {
		this.objectiveSetByContent = objectiveSetByContent;
	}
	@Override
	public String toString() {
		
		return  String.format("<imsss:deliveryControls tracked=\"%s\" completionSetByContent=\"%s\" objectiveSetByContent=\"%s\"/>\n", 
							  new Object[]{tracked, completionSetByContent, objectiveSetByContent});
		
	}
	public void assign(DeliveryControls deliveryControls) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
