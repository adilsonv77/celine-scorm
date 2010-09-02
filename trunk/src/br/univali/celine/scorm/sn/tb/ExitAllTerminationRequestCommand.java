package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.up.EndAttemptProcess;

public class ExitAllTerminationRequestCommand  implements TerminationRequestCommand{

	public TerminationRequestResult run(ActivityTree activityTree) {
		
		ProcessProvider pp = ProcessProvider.getInstance();
		EndAttemptProcess eap = pp.getEndAttemptProcess();
		if (activityTree.getCurrentActivity().isActive()) {
			// has the completion subprocess and rollup been applied to the current
			//   activity yet?
			eap.run(activityTree, activityTree.getCurrentActivity());
		}
		
		pp.getTerminateDescendentAttemptsProcess().run(activityTree, activityTree.getRootActivity());
		eap.run(activityTree, activityTree.getRootActivity());
		
		// move the current activity to the root of the activity tree
		activityTree.setCurrentActivity(activityTree.getRootActivity());
		
		// inform the sequencer that the sequencing session has ended
		return TerminationRequestResult.buildValid(SequencingRequest.EXIT);
	}

}
