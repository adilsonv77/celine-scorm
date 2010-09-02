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
