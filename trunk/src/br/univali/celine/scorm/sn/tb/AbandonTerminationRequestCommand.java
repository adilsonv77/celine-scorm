package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.model.ActivityTree;

public class AbandonTerminationRequestCommand implements
		TerminationRequestCommand {

	public TerminationRequestResult run(ActivityTree activityTree) {
		activityTree.getCurrentActivity().setActive(false);
		return TerminationRequestResult.buildValid(null);
	}

}
