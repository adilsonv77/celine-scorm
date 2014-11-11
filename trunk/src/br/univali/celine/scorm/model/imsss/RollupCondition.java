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


public class RollupCondition {

	private ConditionOperator operator  = ConditionOperator.noOp;
	private ConditionRollupCondition condition;
	
	public String getCondition() {
		return condition.name();
	}
	public void setCondition(String condition) {
		this.condition = ConditionRollupCondition.valueOf(condition);
	}
	public String getOperator() {
		return operator.name();
	}
	public void setOperator(String operator) {
		this.operator = ConditionOperator.valueOf(operator);
	}
	@Override
	public String toString() {
		return "<imsss:rollupCondition operator=\""+operator+"\" condition=\""+condition+"\"/>";
	}
	public ConditionRollupCondition getEnumCondition() {
		return this.condition;
	}
	public ConditionOperator getEnumOperator() {
		return this.operator;
	}
	
	
}
