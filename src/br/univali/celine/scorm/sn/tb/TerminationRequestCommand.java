package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.model.ActivityTree;

public interface TerminationRequestCommand {

	TerminationRequestResult run(ActivityTree activityTree);

}
