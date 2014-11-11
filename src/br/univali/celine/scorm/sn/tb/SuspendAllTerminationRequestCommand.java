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

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.SequencingRequest;

public class SuspendAllTerminationRequestCommand implements
		TerminationRequestCommand {

	public TerminationRequestResult run(ActivityTree activityTree) {

		LearningActivity currentActivity = activityTree.getCurrentActivity();
		// if the current activity is active or already suspended, suspend it and all of its descendents
		if (currentActivity.isActive() == true || currentActivity.isSuspended() == true) {
			
			ProcessProvider pp = ProcessProvider.getInstance();
			// ensure that any status change to this activity is propagated through the entire activity tree
			pp.getOverallRollupProcess().run(activityTree, currentActivity);
			activityTree.setSuspendActivity(currentActivity);
			
			
		} else {
			
			// make sure the current activity is not the root of the activity tree
			if (currentActivity != activityTree.getRootActivity()) {
				activityTree.setSuspendActivity(currentActivity.getParent());
				
			} else {
				// nothing to suspend
				return TerminationRequestResult.buildNotValid(16); // TB.2.3-3
			}
			
		}
		
		LearningActivity[] activityPath = activityTree.makeActivityPath(activityTree.getRootActivity(), activityTree.getSuspendActivity());
		if (activityPath.length == 0) {
			// nothing to suspend
			return TerminationRequestResult.buildNotValid(18); // TB.2.3-5
		}
		
		for (LearningActivity activity:activityPath) {
			activity.setActive(false);
			//activity.setSuspended(true);
			
			activity.suspend(); // my modification
		}
		
		// move the current activity to the root of activity tree
		activityTree.setCurrentActivity(activityTree.getRootActivity());
		
		// inform the sequencer that the sequencing sessin has ended
		return TerminationRequestResult.buildValid(SequencingRequest.EXIT);
	}

}
