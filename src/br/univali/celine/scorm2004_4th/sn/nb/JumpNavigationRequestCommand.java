package br.univali.celine.scorm2004_4th.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;
import br.univali.celine.scorm.sn.nb.NavigationRequestCommand;
import br.univali.celine.scorm.sn.nb.NavigationRequestResult;

public class JumpNavigationRequestCommand implements NavigationRequestCommand {

	@Override
	public NavigationRequestResult run(ActivityTree activityTree,
			NavigationRequest navigationRequest) {

		NavigationRequestResult res = null;
		
		// make sure the the target activity exists in the activity tree and is available
		if (activityTree.contains(navigationRequest.getLearningActivity())
				&&
			navigationRequest.getLearningActivity().getParent().getAvailableChildren().contains(navigationRequest.getLearningActivity())) 
		{
			res = NavigationRequestResult.buildValid(SequencingRequest.JUMP);
			res.setTargetActivity(navigationRequest.getLearningActivity());
			res.setTerminationRequest(TerminationRequest.EXIT);
			
		} else {
			res = NavigationRequestResult.buildNotValid(11); // NB.2.1-11
		}
		
		

		return res;
	}

}
