package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

/**
 * Choice Flow Subprocess [SB.2.9.1]
 * 
 * @author Adilson Vahldick
 *
 */
public class ChoiceFlowSubprocess {

	public LearningActivity run(ActivityTree activityTree, LearningActivity activity, TraversalDirection traverse) {

		// Attempt to move away from the activity, 'one' activity in the specified direction
		LearningActivity activityIdentified = ProcessProvider.getInstance().getChoiceFlowTreeTraversalSubprocess().run(activityTree, activity, traverse);
		if (activityIdentified == null)
			return activity;
		else
			return activityIdentified;
		
	}

}
