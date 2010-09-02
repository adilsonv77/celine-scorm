package br.univali.celine.scorm.model.imsss;

public enum RuleAction {
	// precondition
	skip, disabled, hiddenFromChoice, stopForwardTraversal,
	
	// postcondition
	exitParent, exitAll, retry, retryAll, actionContinue, previous,
	
	// exitcondition
	exit;
	
	public static RuleAction stringToRuleAction(String ruleAction) {
		if (ruleAction.equals("continue"))
			return actionContinue;
		else
			return RuleAction.valueOf(ruleAction);
	}
	
	public static String ruleActionToString(RuleAction ruleAction) {
		
		if (ruleAction == actionContinue)
			return "continue";
		
		return ruleAction.name();
	}
}
