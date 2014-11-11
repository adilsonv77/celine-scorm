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

import java.util.Arrays;
import java.util.List;

public class PerformanceInteraction extends Interaction {

	public PerformanceInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));

		if (respostas.size() == 0)
			throw new Exception();
		
		// apesar desse daqui usar a sintaxe step_name[.]step_answer[,], ele 
		// permite que ocorra ou um outro !!!
	}

	
}
