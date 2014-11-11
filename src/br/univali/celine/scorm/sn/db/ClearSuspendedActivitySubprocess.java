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

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;

//[DB.2.1]
public class ClearSuspendedActivitySubprocess {

	public void run(ActivityTree activityTree, LearningActivity identifiedActivity) {
		if (activityTree.getSuspendActivity() != null) {
			LearningActivity commonAncestor = activityTree.findCommonAncestor(identifiedActivity, activityTree.getSuspendActivity());
			LearningActivity[] path = activityTree.makeActivityPath(activityTree.getSuspendActivity(), commonAncestor);
			if (path.length > 0) {
				
				// walk down the tree setting each of the identified activities to 'not suspended'
				for (LearningActivity activity:path) {
					if (activity.isLeaf()) {
						activity.setSuspended(false);
					} else {
						if (activity.existsChildSuspended() == false) {
							activity.setSuspended(false);
						}
					}
						
				}
				
			}
			// clear the suspended activity attribute
			activityTree.setSuspendActivity(null);
		}
		
	}

}
