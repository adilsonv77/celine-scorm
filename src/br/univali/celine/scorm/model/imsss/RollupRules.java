package br.univali.celine.scorm.model.imsss;

import java.util.ArrayList;
import java.util.List;

public class RollupRules {

	private boolean rollupObjectiveSatisfied = true;
	private boolean rollupProgressCompletion = true;
	private double objectiveMeasureWeight = 1;

	private List<RollupRule> rollupRules = new ArrayList<RollupRule>();

	public void addRollupRule(RollupRule rollupRule) {
		rollupRules.add(rollupRule);
	}

	public List<RollupRule> getRollupRules() {
		return rollupRules;
	}

	public boolean isRollupObjectiveSatisfied() {
		return rollupObjectiveSatisfied;
	}

	public void setRollupObjectiveSatisfied(boolean rollupObjectiveSatisfied) {
		this.rollupObjectiveSatisfied = rollupObjectiveSatisfied;
	}

	public boolean isRollupProgressCompletion() {
		return rollupProgressCompletion;
	}

	public void setRollupProgressCompletion(boolean rollupProgressCompletion) {
		this.rollupProgressCompletion = rollupProgressCompletion;
	}

	public double getObjectiveMeasureWeight() {
		return objectiveMeasureWeight;
	}

	public void setObjectiveMeasureWeight(double objectiveMeasureWeight) {
		this.objectiveMeasureWeight = objectiveMeasureWeight;
	}

	@Override
	public String toString() {
		if (rollupRules.isEmpty()) 
			return "";
		
		String ret = "<imsss:rollupRules rollupObjectiveSatisfied=\"+rollupObjectiveSatisfied+\" " +
								  "rollupProgressCompletion=\"+rollupProgressCompletion+\" " +
								  "objectiveMeasureWeight=\"+objectiveMeasureWeight+\">\n";
		
		for (RollupRule rr:rollupRules) {
			ret += rr + "\n";
		}
		
		return ret + "</imsss:rollupRules>\n";
	}

	public void assign(RollupRules rollupRules2) {
		// TODO Auto-generated method stub
		
	}

}
