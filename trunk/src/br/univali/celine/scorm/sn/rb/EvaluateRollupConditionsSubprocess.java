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

import java.util.List;

import br.univali.celine.scorm.model.imsss.ConditionCombination;
import br.univali.celine.scorm.model.imsss.RollupCondition;
import br.univali.celine.scorm.sn.model.LearningActivity;

// [RB.1.4.1]
public class EvaluateRollupConditionsSubprocess {

	public Boolean run(LearningActivity activity, ConditionCombination conditionCombination, List<RollupCondition> rollupConditions) {
		
		if (rollupConditions.size() == 0)
			return null;
		
		for (RollupCondition rollupCondition:rollupConditions) {
			
			// evaluate each condition against the activity's tracking information.
			// This evaluation may result in "unknown"
			
			// See the element 3.7.2. Rollup Condition from SN Book
			
			boolean res = activity.evaluate(rollupCondition);
			if (conditionCombination == ConditionCombination.all) {
				if (res == false)
					return Boolean.FALSE;
			} else {
				// any
				if (res == true)
					return Boolean.TRUE;
			}
		}
		
		if (conditionCombination == ConditionCombination.all)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

}
