package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.SequencingRequest;

public class AbandonAllTerminationRequestCommand implements
		TerminationRequestCommand {

	public TerminationRequestResult run(ActivityTree activityTree) {
		
		LearningActivity[] activityPath = activityTree.makeActivityPath(activityTree.getRootActivity(), activityTree.getCurrentActivity());
		if (activityPath.length == 0) {
			// nothing to abandon
			return TerminationRequestResult.buildNotValid(19); // TB.2.3-6
		}
		
		for (LearningActivity activity:activityPath) {
			activity.setActive(false);
		}
		
		// move the current activity to the root of the activity tree
		activityTree.setCurrentActivity(activityTree.getRootActivity());
		
		return TerminationRequestResult.buildValid(SequencingRequest.EXIT);
	}

}
