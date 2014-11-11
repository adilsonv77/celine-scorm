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
package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

/**
 * Flow Activity Traversal Subprocess [SB.2.2]
 * 
 * @author adilsonv
 *
 */
public class FlowActivityTraversalSubprocess {

	public FlowSubprocessResult run(ActivityTree activityTree, LearningActivity activity, TraversalDirection traversalDirection, TraversalDirection previousDirection) {
		
		// Confirm that flow is enabled
		if (activity.getParent().isSequencingControlFlow() == false) {
			
			return new FlowSubprocessResult(25);
			
		};
		
		RuleAction action = ProcessProvider.getInstance().getSequencingRulesCheckProcess().run(activity, activity.getSkipSequencingRules());
		if (action != null) {
			// activity is skipped, try to go to next activity
			FlowTreeTraversalSubprocessResult activityRes = ProcessProvider.getInstance().getFlowTreeTraversalSubprocess().run(activityTree, activity, traversalDirection, previousDirection, false);
			if (activityRes.getNextActivity() == null) {
				 FlowSubprocessResult res = new FlowSubprocessResult(activityRes.getException());
				 res.setIdentifiedActivity(activity);
				 res.setEndSequencingSession(activityRes.isEndSequencingSession());
				 return res;
			}
			
			FlowSubprocessResult recursiveRes;
			if (previousDirection == TraversalDirection.backward && activityRes.getTraversalDirection() == TraversalDirection.backward) {
				// make sure the recursive call considers the correct direction
				// recursive call - make sure the next activity is OK
				
				recursiveRes = this.run(activityTree, activityRes.getNextActivity(), traversalDirection, TraversalDirection.none);
			} else {
				recursiveRes = this.run(activityTree, activityRes.getNextActivity(), traversalDirection, previousDirection);
			}
			
			// possible exit from recursion 
			FlowSubprocessResult res = new FlowSubprocessResult(recursiveRes.getException());
			res.setIdentifiedActivity(recursiveRes.getIdentifiedActivity());
			res.setEndSequencingSession(recursiveRes.isEndSequencingSession());
			return res;
		}
		
		// make sure the activity is allowed
		boolean checkRes = ProcessProvider.getInstance().getCheckActivityProcess().run(activity);
		if (checkRes == true) {
			return new FlowSubprocessResult(26);
		}
		
		// cannot deliver a non-leaf activity; enter the cluster looking for a leaf
		if (activity.isLeaf() == false) {
			FlowTreeTraversalSubprocessResult flowActiv = ProcessProvider.getInstance().getFlowTreeTraversalSubprocess().run(activityTree, activity, traversalDirection, TraversalDirection.none, true);
			if (flowActiv.getNextActivity() == null) {
				FlowSubprocessResult res = new FlowSubprocessResult(flowActiv.getException());
				res.setIdentifiedActivity(activity);
				res.setEndSequencingSession(flowActiv.isEndSequencingSession());
				return res;
			}
			
			// check if we are flowing backward through a forward only cluster - must move forward instead
			FlowSubprocessResult recursiveRes;
			if (traversalDirection == TraversalDirection.backward && flowActiv.getTraversalDirection() == TraversalDirection.forward) {
				recursiveRes = this.run(activityTree, flowActiv.getNextActivity(), TraversalDirection.forward, TraversalDirection.backward);
			} else {
				recursiveRes = this.run(activityTree, flowActiv.getNextActivity(), traversalDirection, TraversalDirection.none);
			}
			
			// possible exit from recursion
			FlowSubprocessResult res = new FlowSubprocessResult(recursiveRes.getException());
			res.setIdentifiedActivity(recursiveRes.getIdentifiedActivity());
			res.setEndSequencingSession(recursiveRes.isEndSequencingSession());
			return res;
		}
		
		// found a leaf
		
		return new FlowSubprocessResult(activity);
	}

}
