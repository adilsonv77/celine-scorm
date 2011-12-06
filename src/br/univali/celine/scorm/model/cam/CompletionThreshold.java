package br.univali.celine.scorm.model.cam;

public class CompletionThreshold {

	private boolean completedByMeasure = false;
	private double minProgressMeasure = 1.0;
	private double progressWeight = 1.0;
	
	public boolean isCompletedByMeasure() {
		return completedByMeasure;
	}
	public void setCompletedByMeasure(boolean completedByMeasure) {
		this.completedByMeasure = completedByMeasure;
	}
	public double getMinProgressMeasure() {
		return minProgressMeasure;
	}
	public void setMinProgressMeasure(double minProgressMeasure) {
		this.minProgressMeasure = minProgressMeasure;
	}
	public double getProgressWeight() {
		return progressWeight;
	}
	public void setProgressWeight(double progressWeight) {
		this.progressWeight = progressWeight;
	}
	
	



	
}
