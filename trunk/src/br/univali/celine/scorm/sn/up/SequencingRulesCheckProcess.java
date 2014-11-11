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
package br.univali.celine.scorm.sn.up;

import java.util.List;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.model.imsss.SequencingRule;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// [UP.2]
public class SequencingRulesCheckProcess {

	public RuleAction run(LearningActivity activity, List<SequencingRule> rulesActions) {

		// make sure the activity has rules to evaluate
		if (rulesActions.isEmpty() == false) {
			
			SequencingRuleCheckSubprocess seqRuleCheck = ProcessProvider.getInstance().getSequencingRuleCheckSubprocess();
			
			for (SequencingRule rule:rulesActions) {
				
				// evaluate each rule, one at a time
				if (seqRuleCheck.run(activity, rule) == true) {
					
					// stop at the first rule that evaluates to true - perform the associated action
					return rule.getEnumRuleAction();
				}
			}
		}
		
		return null; // not rules evaluated to true - do not perform any action
	}

}
