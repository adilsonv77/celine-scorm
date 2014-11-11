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

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

/**
 * 
 * Choice Flow Tree Traversal Subprocess [SB.2.9.2]
 * 
 * @author Adilson Vahldick
 *
 */
public class ChoiceFlowTreeTraversalSubprocess {

	public LearningActivity run(ActivityTree activityTree, LearningActivity activity, TraversalDirection traverse) {

		if (traverse == TraversalDirection.forward) {
			if (activityTree.isLastAvailableActivityTraverseForward(activity) || activity == activityTree.getRootActivity()) {
				// cannot walk off the activity tree
				return null;
			}
			
			List<LearningActivity> avChildren = activity.getParent().getAvailableChildren();
			if (avChildren.indexOf(activity) == avChildren.size()-1) {
				// recursion - move to the activity's parent's next forward sibling
				return this.run(activityTree, activity.getParent(), TraversalDirection.forward);
			}
			
			return activityTree.traverseForward(activity);
			
		}
		
		// esse é backward
		if (activity == activityTree.getRootActivity()) {
			// cannot walk off the root of the activity tree
			return null;
		}
		
		List<LearningActivity> avChildren = activity.getParent().getAvailableChildren();
		if (avChildren.indexOf(activity) == 0) {
			// recursion - move to the activity's parent's next backward sibling
			return this.run(activityTree, activity.getParent(), TraversalDirection.backward);
		}
		return activityTree.traverseBackward(activity);
		
	}

}
