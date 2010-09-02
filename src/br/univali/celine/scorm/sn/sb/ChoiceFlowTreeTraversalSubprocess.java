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
