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
package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.SequencingRequest;

public class AbandonAllTerminationRequestCommand implements
		TerminationRequestCommand {

	public TerminationRequestResult run(ActivityTree activityTree) {
		
		LearningActivity[] activityPath = activityTree.makeActivityPath(activityTree.getRootActivity(), activityTree.getCurrentActivity());
		if (activityPath.length == 0) {
			// nothing to abandon
			return TerminationRequestResult.buildNotValid(19); // TB.2.3-6
		}
		
		for (LearningActivity activity:activityPath) {
			activity.setActive(false);
		}
		
		// move the current activity to the root of the activity tree
		activityTree.setCurrentActivity(activityTree.getRootActivity());
		
		return TerminationRequestResult.buildValid(SequencingRequest.EXIT);
	}

}
