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

import java.util.List;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

/**
 * Flow Tree Traversal Subprocess [SB.2.1]
 * 
 * @author adilsonv
 *
 */
public class FlowTreeTraversalSubprocess {

	public FlowTreeTraversalSubprocessResult run(ActivityTree activityTree, LearningActivity activity, TraversalDirection traversalDirection, TraversalDirection previousDirection, boolean considerChildren) {
		
		boolean reverseDirection = false;
		
		FlowTreeTraversalSubprocessResult res = new FlowTreeTraversalSubprocessResult();
		
 	    // Test if we have skipped all of the children in a forward only cluster moving backward
		if (previousDirection == TraversalDirection.backward) {
			List<LearningActivity> avalChildren = activity.getParent().getAvailableChildren();
			if (avalChildren.indexOf(activity) == avalChildren.size() - 1) {
				traversalDirection = TraversalDirection.backward;
				activity = avalChildren.get(0);
				reverseDirection = true;
			}
		}
		
		if (traversalDirection == TraversalDirection.forward) {
			// Walking off the tree causes the sequencing session to end
			
			if (activityTree.isLastAvailableActivityTraverseForward(activity) || (activityTree.getRootActivity() == activity && considerChildren == false)) {
				
				ProcessProvider.getInstance().getTerminateDescendentAttemptsProcess().run(activityTree, activityTree.getRootActivity());
				res.setEndSequencingSession(true);
				return res;
				
			}
			
			
			if (activity.isLeaf() || considerChildren == false) {
				List<LearningActivity>avalChildren = activity.getParent().getAvailableChildren();
				if (avalChildren.indexOf(activity) == avalChildren.size() - 1) {
					// recursion - move to the activity's parent's next forward sibling
					return this.run(activityTree, activity.getParent(), TraversalDirection.forward, TraversalDirection.none, false);
				} else {
					LearningActivity resTrav = activityTree.traverseForward(activity);
					res.setNextActivity(resTrav);
					res.setTraversalDirection(traversalDirection);
					return res;
				}
			} else {
				List<LearningActivity>avalChildren = activity.getAvailableChildren();
				// entering a cluster = forward
				if (avalChildren.size() > 0) {
					// make sure this activity has a child activity
					res.setNextActivity(avalChildren.get(0));
					res.setTraversalDirection(traversalDirection);
				} else {
					res.setException(22);
				}
				return res;
					
			}
		}
		
		// TraversalDirection.backward
		
		// cannot walk off the root of the activity tree
		if (activity == activityTree.getRootActivity()) {
			res.setException(23);
			return res;
		}
		
		
		if (activity.isLeaf() || considerChildren == false) {
			List<LearningActivity>avalChildren = activity.getParent().getAvailableChildren();
			// only test 'forward only' if we are not going to leave this forward only cluster
			if (reverseDirection == false) {
				// test the control mode before traversing
				if (activity.getParent().isSequencingControlForwardOnly() == true) {
					res.setException(24);
					return res;
				}
			}
			
			if (activity == avalChildren.get(0)) {
				// recursion - move to the activity's parent's next backward sibling
				return this.run(activityTree, activity.getParent(), TraversalDirection.backward, TraversalDirection.none, false);
			} else {
				LearningActivity travActiv = activityTree.traverseBackward(activity);
				res.setNextActivity(travActiv);
				res.setTraversalDirection(traversalDirection);
				return res;
			}
		} else {
			List<LearningActivity>avalChildren = activity.getAvailableChildren();
			// entering a cluster - backward
			if (avalChildren.size() > 0) {

				// make sure this activity has a child activity
				if (activity.isSequencingControlForwardOnly() == true) {
					// start at the beginning of a forward only cluster
					res.setNextActivity(avalChildren.get(0));
					res.setTraversalDirection(TraversalDirection.forward);
				} else {
					// start at the end of the cluster if we are backing into it
					res.setNextActivity(avalChildren.get(avalChildren.size()-1));
					res.setTraversalDirection(TraversalDirection.backward);
				}
				return res;
			} else {
				res.setException(22);
				return res;
			}
		}
		
	}

}
