package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;

public class ResumeAllNavigationRequestCommand implements
		NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		NavigationRequestResult res = null;
		
		if (activityTree.getCurrentActivity() == null) {
			if (activityTree.getSuspendActivity() != null) {
				res = NavigationRequestResult.buildValid(SequencingRequest.RESUMEALL);
			} else {
				res = NavigationRequestResult.buildNotValid(3); // [NB.2.1-3]
			}
		} else {
			res = NavigationRequestResult.buildNotValid(1); // [NB.2.1-1]
		}
		return res;
	}

}
