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
package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;

// TB.2.1
public class SequencingExitActionRulesSubprocess {

	public void run(ActivityTree activityTree, LearningActivity currentActivity) {
		
		ProcessProvider pp = ProcessProvider.getInstance();
		
		LearningActivity[] activityPath = activityTree.makeActivityPath(activityTree.getRootActivity(), currentActivity);
		
		LearningActivity exitTarget = null;
		
		for (LearningActivity activity:activityPath) {
		
			// evaluate all exit rules along the active path, starting at the root of the activity tree
			RuleAction action = pp.getSequencingRulesCheckProcess().run(activity, activity.getExitSequencingRules());
			if (action != null) {
				// stop at the first activity that has an exit rule evaluating to true
				exitTarget = activity;
				break;
			}
			
		}
		
		if (exitTarget != null) {
			
			pp.getTerminateDescendentAttemptsProcess().run(activityTree, exitTarget); // end the current attempt on all active descendents
			pp.getEndAttemptProcess().run(activityTree, exitTarget); // end the current attempt on the 'exiting' activity
			activityTree.setCurrentActivity(exitTarget); // move the current activity to the activity that identified for termination
			
		}
		
		
	}

}
