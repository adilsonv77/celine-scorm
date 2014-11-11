/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
