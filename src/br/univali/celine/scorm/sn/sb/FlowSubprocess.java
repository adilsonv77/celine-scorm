package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

/**
 * Flow Subprocess [SB.2.3]
 * 
 * @author Adilson Vahldick
 *
 */
public class FlowSubprocess {

	public FlowSubprocessResult run(ActivityTree activityTree, LearningActivity candidateActivity, TraversalDirection traversalDirection, boolean considerChildren) {
		
		ProcessProvider pp = ProcessProvider.getInstance();
		FlowTreeTraversalSubprocess treeTraversal = pp.getFlowTreeTraversalSubprocess();
		
		// Attempt to move away from the starting activity, one activity in the specified direction
		FlowTreeTraversalSubprocessResult res = treeTraversal.run(activityTree, candidateActivity, traversalDirection, TraversalDirection.none, considerChildren);

		
		
		// No activity to move to.
		if (res.getNextActivity() == null) {
			FlowSubprocessResult resFlow = new FlowSubprocessResult();
			
			resFlow.setIdentifiedActivity(candidateActivity);
			resFlow.setDeliverable(false);
			resFlow.setEndSequencingSession(res.isEndSequencingSession());
			resFlow.setException(res.getException());
			
			return resFlow;
			
		} else {
			candidateActivity = res.getNextActivity();
			FlowActivityTraversalSubprocess activityTraversal = pp.getFlowActivityTraversalSubprocess();
			return activityTraversal.run(activityTree, candidateActivity, traversalDirection, TraversalDirection.none);
			
		}
	}

}
