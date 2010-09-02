package br.univali.celine.scorm.sn.db;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;

//[DB.2.1]
public class ClearSuspendedActivitySubprocess {

	public void run(ActivityTree activityTree, LearningActivity identifiedActivity) {
		if (activityTree.getSuspendActivity() != null) {
			LearningActivity commonAncestor = activityTree.findCommonAncestor(identifiedActivity, activityTree.getSuspendActivity());
			LearningActivity[] path = activityTree.makeActivityPath(activityTree.getSuspendActivity(), commonAncestor);
			if (path.length > 0) {
				
				// walk down the tree setting each of the identified activities to 'not suspended'
				for (LearningActivity activity:path) {
					if (activity.isLeaf()) {
						activity.setSuspended(false);
					} else {
						if (activity.existsChildSuspended() == false) {
							activity.setSuspended(false);
						}
					}
						
				}
				
			}
			// clear the suspended activity attribute
			activityTree.setSuspendActivity(null);
		}
		
	}

}
