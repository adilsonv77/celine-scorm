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
package br.univali.celine.scorm.sn.model.interaction;

public class NumericInteraction extends Interaction {

	public NumericInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
		String values[] = pattern.split("\\[:\\]");
		if (values.length > 0) {
			Double.valueOf(values[0]);
			if (values.length>1)
				Double.valueOf(values[1]);
		}
	}

	@Override
	protected void testLearnerResponse(String learnerResponse) throws Exception {
		Double.valueOf(learnerResponse);
	}

	
	
}
