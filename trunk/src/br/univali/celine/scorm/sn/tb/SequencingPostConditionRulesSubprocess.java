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

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// TB.2.2
public class SequencingPostConditionRulesSubprocess {

	public SequencingPostConditionRulesSubprocessResult run(LearningActivity currentActivity) {
		
		if (currentActivity.isSuspended() == true) {
			// do not apply post condition rules to a suspendend activity
			return new SequencingPostConditionRulesSubprocessResult(null, null);
		}
		
		ProcessProvider pp = ProcessProvider.getInstance();
		// apply the post condition rule to the current activity
		RuleAction action = pp.getSequencingRulesCheckProcess().run(currentActivity, currentActivity.getPostConditionSequencingRules());
		if (action != null) {
			
			switch (action) {
				case retry:
				case actionContinue:
				case previous:
					return new SequencingPostConditionRulesSubprocessResult(null, action);

				case exitParent:
				case exitAll:
					return new SequencingPostConditionRulesSubprocessResult(action, null);
					
				case retryAll:
					return new SequencingPostConditionRulesSubprocessResult(RuleAction.exitAll, RuleAction.retry);
			default:
				break;
			}
		}
		
		return new SequencingPostConditionRulesSubprocessResult(null, null);
	}

}
