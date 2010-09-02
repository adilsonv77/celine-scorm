package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;

public class PreviousNavigationRequestCommand implements
		NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		NavigationRequestResult res = null;
		
		if (activityTree.getCurrentActivity() == null) {
			res = NavigationRequestResult.buildNotValid(2); // [NB.2.1-2]
		} else {
			if (activityTree.getCurrentActivity() != activityTree.getRootActivity()) {
				
				if (activityTree.getCurrentActivity().getParent().isSequencingControlFlow() == true &&
					activityTree.getCurrentActivity().getParent().isSequencingControlForwardOnly() == false) {
					
					res = NavigationRequestResult.buildValid(SequencingRequest.PREVIOUS);
					if (activityTree.getCurrentActivity().isActive() == true) {
						res.setTerminationRequest(TerminationRequest.EXIT);
					} 
					
				} else {
					res = NavigationRequestResult.buildNotValid(5); // [NB.2.1-5]
				}
				
			} else {
				res = NavigationRequestResult.buildNotValid(6); // [NB.2.1-6]
				
			}
		}
			
		
		return res;
	}

}
