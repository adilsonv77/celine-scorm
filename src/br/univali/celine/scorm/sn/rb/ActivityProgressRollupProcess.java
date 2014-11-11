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
package br.univali.celine.scorm.sn.rb;

import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// RB.1.3
public class ActivityProgressRollupProcess {

	public void run(LearningActivity activity) {
		
		ProcessProvider pp = ProcessProvider.getInstance();
		
		// process all incomplete rules first
		if (pp.getRollupRuleCheckSubprocess().run(activity, RollupAction.incomplete)) {
			activity.setAttemptProgressStatus(true);
			activity.setAttemptCompletionStatus(false);
		}
		
		// process all complete rules first
		if (pp.getRollupRuleCheckSubprocess().run(activity, RollupAction.completed)) {
			activity.setAttemptProgressStatus(true);
			activity.setAttemptCompletionStatus(true);
		}
		
	}

}
