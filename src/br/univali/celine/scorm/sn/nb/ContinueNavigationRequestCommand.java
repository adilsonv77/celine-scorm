package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;

public class ContinueNavigationRequestCommand implements
		NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		NavigationRequestResult res = null;
		
		if (activityTree.getCurrentActivity() == null) {
			res = NavigationRequestResult.buildNotValid(2); // [NB.2.1-2]
		} else {
			if (activityTree.getRootActivity() != activityTree.getCurrentActivity() &&
					activityTree.getCurrentActivity().getParent().isSequencingControlFlow()) {
				
				res = NavigationRequestResult.buildValid(SequencingRequest.CONTINUE);
				if (activityTree.getCurrentActivity().isActive()) {
					res.setTerminationRequest(TerminationRequest.EXIT);
				}  
				
			} else {
				res = NavigationRequestResult.buildNotValid(4); // [NB.2.1-4]
			}
		}
		
		return res;
	}

}
