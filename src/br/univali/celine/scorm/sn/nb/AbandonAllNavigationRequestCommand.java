package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;

public class AbandonAllNavigationRequestCommand implements
		NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		
		NavigationRequestResult res = null;
		if (activityTree.getCurrentActivity() != null) {
			res = NavigationRequestResult.buildValid(SequencingRequest.EXIT);
			res.setTerminationRequest(TerminationRequest.ABANDONALL);
		} else {
			res = NavigationRequestResult.buildNotValid(2); // NB.2.1-2
		}
		
		return res;
	}

}
