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


public class RuleCondition {

	private String referencedObjective;
	private Conditions condition;
	private float measureThreshold;
	private ConditionOperator operator = ConditionOperator.noOp;
	
	public String getCondition() {
		return condition.name();
	}
	
	public Conditions getEnumCondition() { return this.condition; }
	
	public void setCondition(String condition) {
		this.condition = Conditions.valueOf(condition);
	}
	public float getMeasureThreshold() {
		return measureThreshold;
	}
	public void setMeasureThreshold(float measureThreshold) {
		this.measureThreshold = measureThreshold;
	}
	public String getOperator() {
		return operator.name();
	}
	public ConditionOperator getEnumConditionOperator() {
		return this.operator;
	}
	public void setOperator(String operator) {
		this.operator = ConditionOperator.valueOf(operator);
	}
	public String getReferencedObjective() {
		return referencedObjective;
	}
	public void setReferencedObjective(String referencedObjective) {
		this.referencedObjective = referencedObjective;
	}
	
	@Override
	public String toString() {
		
		String ret = "";
		if (referencedObjective != null) {
			ret = "referencedObjective=\""+referencedObjective+"\"";
		}
		
		return String.format("<imsss:ruleCondition %s condition=\"%s\" measureThreshold=\"%f\" operator=\"%s\"/>",
				new Object[]{ret, condition, measureThreshold, operator});
	}

	
}
