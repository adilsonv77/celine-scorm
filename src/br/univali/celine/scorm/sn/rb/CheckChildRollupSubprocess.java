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

import br.univali.celine.scorm.model.imsss.RequiredForRollupConsiderations;
import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// [RB.1.4.2]
public class CheckChildRollupSubprocess {

	public boolean run(LearningActivity activity, RollupAction rollupAction) {
		
		boolean included = false;
		
		if (rollupAction == RollupAction.satisfied || rollupAction == RollupAction.notsatisfied) {
			
			included = runSatisfied(activity, rollupAction == RollupAction.satisfied);
			
		} else {
			
			included = runCompleted(activity, rollupAction == RollupAction.completed);
		}
		
		return included;
	}

	private boolean runSatisfied(LearningActivity activity, boolean satisfied) {
		boolean included = false;
		
		// test the objective rollup control
		if (activity.isRollupObjectiveSatisfied()) {  // 2.1.
			
			// default behaviour - adlseq:requiredFor[xxx] == always
			included = true;
			
			if (   // 2.1.2.
					(satisfied && activity.getRequiredForSatisfied() == RequiredForRollupConsiderations.ifNotSuspended)
				|| 
					(satisfied == false && activity.getRequiredForNotSatisfied() == RequiredForRollupConsiderations.ifNotSuspended)
				) {
				
					if (activity.isProgressStatus() == false || 
							(activity.getAttemptCount() > 0 && activity.isSuspended())) {
						included = false;
					}
					
				} else {
					// 2.1.3.1.
					if ((satisfied && activity.getRequiredForSatisfied() == RequiredForRollupConsiderations.ifAttempted)
						||
						(satisfied == false && activity.getRequiredForNotSatisfied() == RequiredForRollupConsiderations.ifAttempted)
					) {
						
						if (activity.isProgressStatus() == false || activity.getAttemptCount() == 0) {
							included = false;
						}
					} else {
						// 2.1.3.2.1.
						if ((satisfied && activity.getRequiredForSatisfied() == RequiredForRollupConsiderations.ifNotSkipped)
							||
							(satisfied == false && activity.getRequiredForNotSatisfied() == RequiredForRollupConsiderations.ifNotSkipped)
						) {
						
							RuleAction res = ProcessProvider.getInstance().getSequencingRulesCheckProcess().run(activity, activity.getSkipSequencingRules());
							if (res != null)
								included = false;
						}
					}
				}
		}
		return included;
	}

	private boolean runCompleted(LearningActivity activity, boolean completed) {
		boolean included = false;
		
		// Test the progress rollup control
		if (activity.isRollupProgressCompletion() == true) {
			
			// default behaviour - adlseq:requiredFor[xxx] == always
			included = true;
			
			if ( // 3.1.2.
					(completed && activity.getRequiredForCompleted() == RequiredForRollupConsiderations.ifNotSuspended)
					||
					(completed == false && activity.getRequiredForIncomplete() == RequiredForRollupConsiderations.ifNotSuspended) 
				){
				
				if (activity.isProgressStatus() == false ||
						(activity.getAttemptCount() > 0 && activity.isSuspended() == true)) {
					included = false;
				}
			} else {
				
				// 3.1.3.1.
				if ( 
						(completed && activity.getRequiredForCompleted() == RequiredForRollupConsiderations.ifAttempted)
						||
						(completed == false && activity.getRequiredForIncomplete() == RequiredForRollupConsiderations.ifAttempted) 
					){
					
					if (activity.isProgressStatus() == false || activity.getAttemptCount() == 0) {
						included = false;
					}
					
				} else {
					
					// 3.1.3.2.1
					if ( 
							(completed && activity.getRequiredForCompleted() == RequiredForRollupConsiderations.ifNotSkipped)
							||
							(completed == false && activity.getRequiredForIncomplete() == RequiredForRollupConsiderations.ifNotSkipped) 
						){
						
						RuleAction res = ProcessProvider.getInstance().getSequencingRulesCheckProcess().run(activity, activity.getSkipSequencingRules());
						if (res != null)
							included = false;
					}
					
				}
				
				
			}
				
		}
		return included;
	}

}
