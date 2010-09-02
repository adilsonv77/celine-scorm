package br.univali.celine.scorm.sn.up;

import java.util.List;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.model.imsss.SequencingRule;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// [UP.2]
public class SequencingRulesCheckProcess {

	public RuleAction run(LearningActivity activity, List<SequencingRule> rulesActions) {

		// make sure the activity has rules to evaluate
		if (rulesActions.isEmpty() == false) {
			
			SequencingRuleCheckSubprocess seqRuleCheck = ProcessProvider.getInstance().getSequencingRuleCheckSubprocess();
			
			for (SequencingRule rule:rulesActions) {
				
				// evaluate each rule, one at a time
				if (seqRuleCheck.run(activity, rule) == true) {
					
					// stop at the first rule that evaluates to true - perform the associated action
					return rule.getEnumRuleAction();
				}
			}
		}
		
		return null; // not rules evaluated to true - do not perform any action
	}

}
