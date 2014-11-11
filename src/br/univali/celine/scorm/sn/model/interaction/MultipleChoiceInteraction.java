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

public class MultipleChoiceInteraction extends Interaction {

	public MultipleChoiceInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));
		
		/*
		 * The following requirements shall be adhered to when building the characterstring:
			o The set may contain zero or more short_identifier_types.
			o If the set contains more than one short_identifier_type, then they shall be separated by the special reserved token “[,]”.
			o Each short_identifier_type shall occur in the set only once.
		 */
		
		/* no correct_responses pode nao ter respostas !!! */
		
		if (!correct_responses && respostas.size() == 0) // a especificacao diz uma coisa, os testes fazem outra :(
			throw new Exception("There arent choices");
		
		for (int x = 0; x<respostas.size(); x++) {
			String s = respostas.get(x); 
			if (s.length() == 0 || s.contains("[") || s.contains("]")) {
				throw new Exception("Incorrect identifier");
			}

			for (int y=x+1;y<respostas.size(); y++)
				if (s.equals(respostas.get(y)))
					throw new Exception("The same id is more one time");
		}
		
		// page 125 from RTE 4th version 
		for (int x=0;x<correctResponses.size();x++)
			if (x != index)
				if (pattern.equals(correctResponses.get(x))) {
					throw new Exception("These correct response already exists");
				}
		
	}

}
