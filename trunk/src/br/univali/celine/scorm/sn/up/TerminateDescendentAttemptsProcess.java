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

// UP.3
public class TerminateDescendentAttemptsProcess {

	public void run(ActivityTree activityTree, LearningActivity activity) {
		
		if (activityTree.getCurrentActivity() == null)
			return;
		
		LearningActivity ancestor = activityTree.findCommonAncestor(activityTree.getCurrentActivity(), activity);
		
		// the current activity must have already been exited
		LearningActivity[] activityPath = activityTree.makeActivityPath(ancestor, activityTree.getCurrentActivity());
		
		if (activityPath.length > 2) { // > 2 because the ancestor and current activity will exclude from the activitypath
			
			EndAttemptProcess endAttempt = ProcessProvider.getInstance().getEndAttemptProcess();
			// there are some activities that need to be terminated
			for (int i = 1; i < activityPath.length-1; i++) { // exclude ancestor (i=1) and current activity (< activityPath.length-1)
				
				// end the current attempt on each activity
				endAttempt.run(activityTree, activityPath[i]);
				
				
			}
			
		}
		
	}
	
}
