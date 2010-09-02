package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;

public class ExitNavigationRequestCommand implements NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {

		NavigationRequestResult res = null;
		
		if (activityTree.getCurrentActivity() != null) {
			
			if (activityTree.getCurrentActivity().isActive() == true) {
				res = NavigationRequestResult.buildValid(SequencingRequest.EXIT);
				res.setTerminationRequest(TerminationRequest.EXIT);
			} else {
				res = NavigationRequestResult.buildNotValid(12); // NB.2.1-12
			}
			
		} else {
			res = NavigationRequestResult.buildNotValid(12); // NB.2.1-12
		}
		
		return res;
	}

}
