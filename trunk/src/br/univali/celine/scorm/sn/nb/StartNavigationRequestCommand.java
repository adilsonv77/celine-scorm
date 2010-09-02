package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;

// finalizado
public class StartNavigationRequestCommand implements NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		
		NavigationRequestResult res = null; 
		if (activityTree.getCurrentActivity() == null) {
			res = NavigationRequestResult.buildValid(SequencingRequest.START);
		} else {
			res = NavigationRequestResult.buildNotValid(1); // [NB.2.1-1]
		}
		return res;
		
	}

}
