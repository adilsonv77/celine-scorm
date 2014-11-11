/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

