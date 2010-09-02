package br.univali.celine.scorm.sn.rb;

import java.util.LinkedList;
import java.util.List;

import br.univali.celine.scorm.model.imsss.ChildActivitySet;
import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.model.imsss.RollupRule;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// [RB.1.4]
public class RollupRuleCheckSubprocess {

	public boolean run(LearningActivity activity, RollupAction rollupAction) {
	
		if (activity.containsRollupRules(rollupAction)) { // make sure the activity has rules to evaluate
			
			/*
			Initialize rules list by selecting the set of Rollup Rules for the activity
			that have the specified Rollup Actions, maintaining original rule ordering
			*/
			
			List<RollupRule> rollupRules = activity.selectRollupRules(rollupAction);
			for (RollupRule rollupRule:rollupRules) {
				
				List<Boolean> childrenContributed = new LinkedList<Boolean>();
				for (LearningActivity child:activity.getChildren()) {
					if (child.isTracked()) {
						
						// Make sure this child contributes to the status of its parent.
						ProcessProvider pp = ProcessProvider.getInstance();
						if (pp.getCheckChildRollupSubprocess().run(child, rollupAction) == true) {
							
							Boolean res = pp.getEvaluateRollupConditionsSubprocess().run(child, rollupRule.getEnumConditionCombination(), rollupRule.getRollupConditions());
							childrenContributed.add(res);
						}
						
					}
				}
				
				// determine if the appropriate children contributed to rollup;
				// if they did, the status of the activity should be changed.
				
				boolean statusChanged = false;
				
				switch (rollupRule.getEnumChildActivitySet()) {
					case all : 
						statusChanged = (childrenContributed.contains(Boolean.FALSE) == false && childrenContributed.contains(null) == false);
						break;
						
					case any : 
						statusChanged = childrenContributed.contains(Boolean.TRUE);
						break;

					case none : 
						statusChanged = (childrenContributed.contains(Boolean.TRUE) == false && childrenContributed.contains(null) == false);
						break;
					
					case atLeastCount : ;
					case atLeastPercent : ;
						int conta = 0;
						for (Boolean b:childrenContributed) {
							if (b != null && b.booleanValue() == true) 
								conta++;
						}
					
						if (rollupRule.getEnumChildActivitySet() == ChildActivitySet.atLeastCount) {

							statusChanged = conta >= rollupRule.getMinimumCount();
							
						} else {
							
							statusChanged = (conta / childrenContributed.size()) >= rollupRule.getMinimumPercent();
						}
						break;
						
				}
				
				// stop at first rule that evaluates to true - perform the associated action
				if (statusChanged == true)
					return true;
				
			}
			
		}
		
		return false; // no rules evaluated to true - do not perform any action
		
	}
}

