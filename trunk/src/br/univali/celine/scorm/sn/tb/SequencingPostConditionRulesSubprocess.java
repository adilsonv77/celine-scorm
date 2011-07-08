package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// TB.2.2
public class SequencingPostConditionRulesSubprocess {

	public SequencingPostConditionRulesSubprocessResult run(LearningActivity currentActivity) {
		
		if (currentActivity.isSuspended() == true) {
			// do not apply post condition rules to a suspendend activity
			return new SequencingPostConditionRulesSubprocessResult(null, null);
		}
		
		ProcessProvider pp = ProcessProvider.getInstance();
		// apply the post condition rule to the current activity
		RuleAction action = pp.getSequencingRulesCheckProcess().run(currentActivity, currentActivity.getPostConditionSequencingRules());
		if (action != null) {
			
			switch (action) {
				case retry:
				case actionContinue:
				case previous:
					return new SequencingPostConditionRulesSubprocessResult(null, action);

				case exitParent:
				case exitAll:
					return new SequencingPostConditionRulesSubprocessResult(action, null);
					
				case retryAll:
					return new SequencingPostConditionRulesSubprocessResult(RuleAction.exitAll, RuleAction.retry);
			}
		}
		
		return new SequencingPostConditionRulesSubprocessResult(null, null);
	}

}
