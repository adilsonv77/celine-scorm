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

import br.univali.celine.scorm.model.imsss.ConditionCombinations;
import br.univali.celine.scorm.model.imsss.RuleCondition;
import br.univali.celine.scorm.model.imsss.SequencingRule;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.Resultado;

//[UP.2.1]
// Referências no capítulo 3.4. Sequencing Rule Description do livro de Sequencing and Navigation

public class SequencingRuleCheckSubprocess {

	public boolean run(LearningActivity activity, SequencingRule rule) {
		
/*
 * 3.4.1. Condition Combination
 * 
 * All (default value): The condition set evaluates to True if and only if all of its
individual conditions evaluate to True. Acts as a logical And.
• Any: The condition set evaluates to True if any of the individual conditions
evaluates to True. Acts as a logical Or.
 */
		
		ConditionCombinations condition = rule.getEnumConditionCombination();
		
		boolean retorno;
		
		if (condition == ConditionCombinations.all) {
			retorno = true;
		} else {
			retorno = false;
		}
		
		// this is used to keep track of the evaluation of the rule's condition
		
		for (RuleCondition ruleCondition: rule.getRuleConditions()) {
			
			// Evaluate each condition against the activity's tracking information
			Resultado resultado = activity.evaluate(ruleCondition);
			
			if (condition == ConditionCombinations.all) {
				/*
				 * Segundo a secao 3.4. Sequencing Rule, a condicao deve retornar true para ativar 
				 * a ação. Entao eu implementei que o valor UNKNOWN é a mesma coisa que false
				 */
				if (resultado != Resultado.TRUE) 
					return false;
				
			} else {
				
				if (resultado == Resultado.TRUE)
					return true;
				
			}
		}
		
		return retorno;
	}

}
