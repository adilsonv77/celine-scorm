package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;
import br.univali.celine.scorm.sn.up.EndAttemptProcess;

public class ExitTerminationRequestCommand implements TerminationRequestCommand {

	private ExitAllTerminationRequestCommand exitAllComm;

	public ExitTerminationRequestCommand(ExitAllTerminationRequestCommand exitAllComm) {
		this.exitAllComm = exitAllComm;
	}

	public TerminationRequestResult run(ActivityTree activityTree) {
		
		ProcessProvider pp = ProcessProvider.getInstance();
		
		// ensure the state of the current activity is up to date
		EndAttemptProcess eap = pp.getEndAttemptProcess();
		eap.run(activityTree, activityTree.getCurrentActivity());
		
		// check if any of the current activity's ancestors need to terminate
		SequencingExitActionRulesSubprocess sears = pp.getSequencingExitActionRulesSubprocess();
		sears.run(activityTree, activityTree.getCurrentActivity());
		
		TerminationRequestResult termReqRes = null;
		
		SequencingPostConditionRulesSubprocessResult res = null;
		boolean processedExit;
		do {
			processedExit = false;
			
			res = pp.getSequencingPostConditionRulesSubprocess().run(activityTree.getCurrentActivity());
			if (res.getTerminationRequest() == TerminationRequest.EXITALL) {
				
				// process an exit all termination request
				return exitAllComm.run(activityTree);
			}
			
			// if we exit the parent of the current activity, move the current activity to the parent
			// of the current activity
			if (res.getTerminationRequest() == TerminationRequest.EXITPARENT) {
				
				// the root of the activity tree does not have a parent to exit
				if (activityTree.getCurrentActivity() != activityTree.getRootActivity()) {
					activityTree.setCurrentActivity(activityTree.getCurrentActivity().getParent());
					
					// need to evaluate post conditions on the new current activity
					eap.run(activityTree, activityTree.getCurrentActivity());
					processedExit = true;
					
				} else {
					termReqRes = TerminationRequestResult.buildNotValid(17); // TB.2.3-4
				}
			} else {
				
				// if the attempt on the root of the activity tree is ending without a retry, the
				//   sequencing session also ends
				if (activityTree.getCurrentActivity() == activityTree.getRootActivity() && 
					res.getSequencingRequest() != SequencingRequest.RETRY) {
				
					termReqRes = TerminationRequestResult.buildValid(SequencingRequest.EXIT);
				}
			}
			
			
		} while (processedExit == true);
		
		if (termReqRes == null) {
			
			termReqRes = TerminationRequestResult.buildValid(res.getSequencingRequest());
		}
		
		return termReqRes;
	}

}
