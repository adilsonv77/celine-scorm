package br.univali.celine.scorm.sn.up;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.LocalObjective;


// [UP.4]
public class EndAttemptProcess {

	public void run(ActivityTree activityTree, LearningActivity currentActivity) {
		
		if (currentActivity.isLeaf()) {
			if (currentActivity.isTracked()) {
				if (currentActivity.isSuspended() == false) {
					// the sequencer will not affect the state of suspended activities
					
					if (currentActivity.isCompletionSetByContent() == false) {
						// should the sequencer set the completion status of the activity?
						
						if (currentActivity.isAttemptProgressStatus() == false) {
							// did the content inform the sequencer of the activity's completion status?
							
							currentActivity.setAttemptProgressStatus(true);
							currentActivity.setAttemptCompletionStatus(true);
						}
					}
					
					if (currentActivity.isObjectiveSetByContent() == false) {
						// should the sequencer set the objective status of the activity?
						
						for (LocalObjective objective:currentActivity.getObjectives()) {
							
							if (objective.isContributesToRollup() == true) {
						
								// did the content inform the sequencer of the activity's rolled-up objective status?
								if (objective.isProgressStatus() == false) {
									objective.setProgressStatus(true);
									objective.setSatisfiedStatus(true);
								}
								
							}
							
						}
						
					}
				}
			}
		} else {
			// the activity has children
			if (currentActivity.existsChildSuspended()) {
				// the suspended status of the parent is dependent on the suspended status of its children
				currentActivity.setSuspended(true);
			} else {
				currentActivity.setSuspended(false);
			}
		}

		currentActivity.endAttempt();// the current attempt on this activity has ended
		
		// ensure that any status change to this activity is propagated through the entire activity tree
		ProcessProvider.getInstance().getOverallRollupProcess().run(activityTree, currentActivity); 
	}

}
