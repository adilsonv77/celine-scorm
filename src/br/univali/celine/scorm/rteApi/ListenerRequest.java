package br.univali.celine.scorm.rteApi;

import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequestType;

public interface ListenerRequest {

	void request(NavigationRequestType type, String nextActivity, ActivityTree tree, User user, String courseName) throws Exception;
	
}
