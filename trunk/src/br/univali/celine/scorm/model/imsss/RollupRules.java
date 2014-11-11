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

import java.util.ArrayList;
import java.util.List;

public class RollupRules {

	private boolean rollupObjectiveSatisfied = true;
	private boolean rollupProgressCompletion = true;
	private double objectiveMeasureWeight = 1;

	private List<RollupRule> rollupRules = new ArrayList<RollupRule>();

	public void addRollupRule(RollupRule rollupRule) {
		rollupRules.add(rollupRule);
	}

	public List<RollupRule> getRollupRules() {
		return rollupRules;
	}

	public boolean isRollupObjectiveSatisfied() {
		return rollupObjectiveSatisfied;
	}

	public void setRollupObjectiveSatisfied(boolean rollupObjectiveSatisfied) {
		this.rollupObjectiveSatisfied = rollupObjectiveSatisfied;
	}

	public boolean isRollupProgressCompletion() {
		return rollupProgressCompletion;
	}

	public void setRollupProgressCompletion(boolean rollupProgressCompletion) {
		this.rollupProgressCompletion = rollupProgressCompletion;
	}

	public double getObjectiveMeasureWeight() {
		return objectiveMeasureWeight;
	}

	public void setObjectiveMeasureWeight(double objectiveMeasureWeight) {
		this.objectiveMeasureWeight = objectiveMeasureWeight;
	}

	@Override
	public String toString() {
		if (rollupRules.isEmpty()) 
			return "";
		
		String ret = "<imsss:rollupRules rollupObjectiveSatisfied=\"+rollupObjectiveSatisfied+\" " +
								  "rollupProgressCompletion=\"+rollupProgressCompletion+\" " +
								  "objectiveMeasureWeight=\"+objectiveMeasureWeight+\">\n";
		
		for (RollupRule rr:rollupRules) {
			ret += rr + "\n";
		}
		
		return ret + "</imsss:rollupRules>\n";
	}

	public void assign(RollupRules rollupRules2) {
		// TODO Auto-generated method stub
		
	}

}
