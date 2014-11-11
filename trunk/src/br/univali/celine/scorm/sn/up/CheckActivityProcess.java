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

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// UP.5
public class CheckActivityProcess {

	public boolean run(LearningActivity activity) {
		
		// make sure the activity is not disabled
		RuleAction ret = ProcessProvider.getInstance().getSequencingRulesCheckProcess().run(activity, activity.getDisabledSequencingRules());
		
		if (ret != null)
			return true;
		
		// make sure the activity does not violate any limit condition
		return ProcessProvider.getInstance().getLimitConditionsCheckProcess().run(activity); // false = activity is allowed
	}
	
}
