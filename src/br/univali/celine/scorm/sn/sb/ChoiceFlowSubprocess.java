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
package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;

/**
 * Choice Flow Subprocess [SB.2.9.1]
 * 
 * @author Adilson Vahldick
 *
 */
public class ChoiceFlowSubprocess {

	public LearningActivity run(ActivityTree activityTree, LearningActivity activity, TraversalDirection traverse) {

		// Attempt to move away from the activity, 'one' activity in the specified direction
		LearningActivity activityIdentified = ProcessProvider.getInstance().getChoiceFlowTreeTraversalSubprocess().run(activityTree, activity, traverse);
		if (activityIdentified == null)
			return activity;
		else
			return activityIdentified;
		
	}

}
