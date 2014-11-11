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
package br.univali.celine.scorm.sn.up;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.LocalObjective;


// [UP.4]
public class EndAttemptProcess {

	public void run(ActivityTree activityTree, LearningActivity currentActivity) {
		
		if (currentActivity.isLeaf()) {
			if (currentActivity.isTracked()) {
				if (currentActivity.isSuspended() == false) {
					// the sequencer will not affect the state of suspended activities
					
					if (currentActivity.isCompletionSetByContent() == false) {
						// should the sequencer set the completion status of the activity?
						
						if (currentActivity.isAttemptProgressStatus() == false) {
							// did the content inform the sequencer of the activity's completion status?
							
							currentActivity.setAttemptProgressStatus(true);
							currentActivity.setAttemptCompletionStatus(true);
						}
					}
					
					if (currentActivity.isObjectiveSetByContent() == false) {
						// should the sequencer set the objective status of the activity?
						
						for (LocalObjective objective:currentActivity.getObjectives()) {
							
							if (objective.isContributesToRollup() == true) {
						
								// did the content inform the sequencer of the activity's rolled-up objective status?
								if (objective.isProgressStatus() == false) {
									objective.setProgressStatus(true);
									objective.setSatisfiedStatus(true);
								}
								
							}
							
						}
						
					}
				}
			}
		} else {
			// the activity has children
			if (currentActivity.existsChildSuspended()) {
				// the suspended status of the parent is dependent on the suspended status of its children
				currentActivity.setSuspended(true);
			} else {
				currentActivity.setSuspended(false);
			}
		}

		currentActivity.endAttempt();// the current attempt on this activity has ended
		
		// ensure that any status change to this activity is propagated through the entire activity tree
		ProcessProvider.getInstance().getOverallRollupProcess().run(activityTree, currentActivity); 
	}

}
