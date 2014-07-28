package br.univali.celine.scorm.model.imsss;

public class RandomizationControls {

	private RandomizationTiming randomizationTiming = RandomizationTiming.never;
	private int selectCount;
	private boolean reorderChildren;
	private SelectionTiming selectionTiming = SelectionTiming.never;
	
	public String getRandomizationTiming() {
		return randomizationTiming.name();
	}
	public void setRandomizationTiming(String randomizationTiming) {
		this.randomizationTiming = RandomizationTiming.valueOf(randomizationTiming);
	}
	public RandomizationTiming getEnumRandomizationTiming() {
		return this.randomizationTiming;
	}
	public int getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}
	public boolean isReorderChildren() {
		return reorderChildren;
	}
	public void setReorderChildren(boolean reorderChildren) {
		this.reorderChildren = reorderChildren;
	}
	public String getSelectionTiming() {
		return selectionTiming.name();
	}
	public void setSelectionTiming(String selectionTiming) {
		this.selectionTiming = SelectionTiming.valueOf(selectionTiming);
	}
	public SelectionTiming getEnumSelectionTiming() {
		return this.selectionTiming;
	}
	
	@Override
	public String toString() {
		
		return String.format("<imsss:randomizationControls randomizationTiming=\"%s\" " +
													"selectCount=\"%s\" " +
													"reorderChildren=\"%s\" " +
													"selectionTiming=\"%s\"/>\n", 
				new Object[]{getRandomizationTiming(), getSelectCount(), isReorderChildren(), getSelectionTiming()});
		
	}
	public void assign(RandomizationControls randomizationControls) {
		
		setRandomizationTiming(randomizationControls.getRandomizationTiming());
		setSelectionTiming(randomizationControls.getSelectionTiming());
		setReorderChildren(randomizationControls.isReorderChildren());
		setSelectCount(randomizationControls.getSelectCount());
		
	}
	
	
	
}
