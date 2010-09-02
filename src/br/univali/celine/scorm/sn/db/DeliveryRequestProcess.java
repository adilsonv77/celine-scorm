package br.univali.celine.scorm.sn.db;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.up.CheckActivityProcess;

// [DB.1.1]
public class DeliveryRequestProcess {

	public DeliveryRequestResult run(ActivityTree activityTree, LearningActivity activity) {
		
		// can only deliver leaf activities
		if (activity.isLeaf() == false)
			return new DeliveryRequestResult(52);
		
		LearningActivity[] path = activityTree.makeActivityPath(activityTree.getRootActivity(), activity);
		if (path.length == 0) // nothing to deliver
			return new DeliveryRequestResult(53);
		
		ProcessProvider pp = ProcessProvider.getInstance();
		CheckActivityProcess cap = pp.getCheckActivityProcess();
		
		for (LearningActivity la:path) {
			if (cap.run(la)) {
				return new DeliveryRequestResult(54);
			}
			
		}
		
		return new DeliveryRequestResult();
	}
	
}
