package br.univali.celine.scorm.model.imsss;

import java.util.LinkedList;
import java.util.List;

public class SequencingRule {

	private List<RuleCondition> ruleConditions = new LinkedList<RuleCondition>();
	
	public void addRuleCondition(RuleCondition ruleCondition) {
		this.ruleConditions.add(ruleCondition);
	}
	
	public List<RuleCondition> getRuleConditions() { return this.ruleConditions; }
	
	private RuleAction ruleAction = null;

	public String getRuleAction() {
		return RuleAction.ruleActionToString(ruleAction);
	}

	public RuleAction getEnumRuleAction() { return this.ruleAction; }
	
	public void setRuleAction(String ruleAction) {
		this.ruleAction = RuleAction.stringToRuleAction(ruleAction);
	}

	private ConditionCombinations conditionCombination = ConditionCombinations.all;
		
	public String getConditionCombination() {
		return conditionCombination.name();
	}
	
	public ConditionCombinations getEnumConditionCombination() { return this.conditionCombination; }

	public void setConditionCombination(String conditionCombination) {
		this.conditionCombination = ConditionCombinations.valueOf(conditionCombination);
	}

	@Override
	public String toString() {
		String ret = "";
		if (!getRuleConditions().isEmpty()) {
			ret = "<imsss:ruleConditions conditionCombination=\""+getConditionCombination()+"\">\n";
			for (RuleCondition rc:getRuleConditions()) {
				ret += rc + "\n";
			}
			ret += "</imsss:ruleConditions>\n";
		}

		if (getEnumRuleAction() != null)  {
			ret += "<imsss:ruleAction action=\""+getRuleAction()+"\"/>\n";
		}
		
		return ret;
	}
}
