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


public class RollupRule {

	private ChildActivitySet childActivitySet = ChildActivitySet.all;
	private int minimumCount = 0;
	private double minimumPercent = 0;
	
	private List<RollupCondition> rollupConditions = new LinkedList<RollupCondition>();
	private ConditionCombination conditionCombination = ConditionCombination.any; 
	
	private RollupAction rollupAction;
	
	public RollupAction getRollupAction() {
		return rollupAction;
	}

	public void setRollupAction(String rollupAction) {
		this.rollupAction = RollupAction.valueOf(rollupAction);
	}

	public String getConditionCombination() {
		return conditionCombination.name();
	}

	public void setConditionCombination(String conditionCombination) {
		this.conditionCombination = ConditionCombination.valueOf(conditionCombination);
	}

	public void addRollupCondition(RollupCondition rollupCondition) {
		this.rollupConditions.add(rollupCondition);
	}
	
	public List<RollupCondition> getRollupConditions() {
		return this.rollupConditions;
	}

	public String getChildActivitySet() {
		return childActivitySet.name();
	}
	
	public ChildActivitySet getEnumChildActivitySet() {
		return this.childActivitySet;
	}
	
	public void setChildActivitySet(String childActivitySet) {
		this.childActivitySet = ChildActivitySet.valueOf(childActivitySet);
		
	}

	public int getMinimumCount() {
		return minimumCount;
	}

	public void setMinimumCount(int minimumCount) {
		this.minimumCount = minimumCount;
	}

	public double getMinimumPercent() {
		return minimumPercent;
	}

	public void setMinimumPercent(double minimumPercent) {
		this.minimumPercent = minimumPercent;
	}

	@Override
	public String toString() {
		String ret = "<imsss:rollupRule childActivitySet=\""+childActivitySet+"\""+
						" minimumCount="+minimumCount+"\""+
						" minimumPercent="+minimumPercent+"\""+">";
		
		ret += "<imsss:rollupConditions conditionCombination=\""+conditionCombination+"\">";
		for (RollupCondition rc:rollupConditions) {
			ret += rc+"\n";
		}
		ret += "</imsss:rollupConditions>\n";
		
		ret += "<imsss:rollupAction action=\""+rollupAction+"\"/>\n";
		
		return ret + "</imsss:rollupRule>";
	}

	public ConditionCombination getEnumConditionCombination() {
		return this.conditionCombination;
	}

	
}
