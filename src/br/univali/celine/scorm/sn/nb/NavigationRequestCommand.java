package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;

public interface NavigationRequestCommand {

	NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest);

}
