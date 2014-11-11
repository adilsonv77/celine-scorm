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
package br.univali.celine.scorm.model.imsss;

import java.util.LinkedList;
import java.util.List;

public class SequencingRule {

	private List<RuleCondition> ruleConditions = new LinkedList<RuleCondition>();
	
	public void addRuleCondition(RuleCondition ruleCondition) {
		this.ruleConditions.add(ruleCondition);
	}
	
	public List<RuleCondition> getRuleConditions() { return this.ruleConditions; }
	
	private RuleAction ruleAction = null;

	public String getRuleAction() {
		return RuleAction.ruleActionToString(ruleAction);
	}

	public RuleAction getEnumRuleAction() { return this.ruleAction; }
	
	public void setRuleAction(String ruleAction) {
		this.ruleAction = RuleAction.stringToRuleAction(ruleAction);
	}

	private ConditionCombinations conditionCombination = ConditionCombinations.all;
		
	public String getConditionCombination() {
		return conditionCombination.name();
	}
	
	public ConditionCombinations getEnumConditionCombination() { return this.conditionCombination; }

	public void setConditionCombination(String conditionCombination) {
		this.conditionCombination = ConditionCombinations.valueOf(conditionCombination);
	}

	@Override
	public String toString() {
		String ret = "";
		if (!getRuleConditions().isEmpty()) {
			ret = "<imsss:ruleConditions conditionCombination=\""+getConditionCombination()+"\">\n";
			for (RuleCondition rc:getRuleConditions()) {
				ret += rc + "\n";
			}
			ret += "</imsss:ruleConditions>\n";
		}

		if (getEnumRuleAction() != null)  {
			ret += "<imsss:ruleAction action=\""+getRuleAction()+"\"/>\n";
		}
		
		return ret;
	}
}
