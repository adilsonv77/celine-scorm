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
import java.util.LinkedList;
import java.util.List;

public class Sequencing {

	private ControlMode controlMode;

	public ControlMode getControlMode() {
		if (controlMode == null)
			controlMode = new ControlMode();
		return controlMode;
	}

	public void setControlMode(ControlMode controlMode) {
		this.controlMode = controlMode;
	}
	
	private DeliveryControls deliveryControls;
	
	public DeliveryControls getDeliveryControls() {
		if (deliveryControls == null)
			deliveryControls = new DeliveryControls(); // isso é para garantir que use valores padroes
		return deliveryControls;
	}

	public void setDeliveryControls(DeliveryControls deliveryControls) {
		this.deliveryControls = deliveryControls;
	}
	
	private LimitConditions limitConditions;
	
	public LimitConditions getLimitConditions() {
		if (limitConditions == null)
			limitConditions = new LimitConditions();
		return limitConditions;
	}

	public void setLimitConditions(LimitConditions limitConditions) {
		this.limitConditions = limitConditions;
	}

	private RandomizationControls randomizationControls;
	
	public RandomizationControls getRandomizationControls() {
		if (randomizationControls == null)
			randomizationControls = new RandomizationControls();
		return randomizationControls;
	}

	public void setRandomizationControls(RandomizationControls randomizationControls) {
		this.randomizationControls = randomizationControls;
	}

	private RollupRules rollupRules;

	public RollupRules getRollupRules() {
		if (this.rollupRules == null)
			this.rollupRules = new RollupRules();
		return rollupRules;
	}

	public void setRollupRules(RollupRules rollupRules) {
		if (rollupRules == null)
			rollupRules = new RollupRules();
		this.rollupRules = rollupRules;
	}
	
	private RollupConsiderations rollupConsiderations;

	public RollupConsiderations getRollupConsiderations() {
		if (rollupConsiderations == null)
			rollupConsiderations = new RollupConsiderations();
		return rollupConsiderations;
	}

	public void setRollupConsiderations(RollupConsiderations rollupConsiderations) {
		this.rollupConsiderations = rollupConsiderations;
	}
	private ConstrainedChoiceConsiderations constrainedChoiceConsiderations;
	
	public ConstrainedChoiceConsiderations getConstrainedChoiceConsiderations() {
		if (constrainedChoiceConsiderations == null)
			constrainedChoiceConsiderations = new ConstrainedChoiceConsiderations();
		return constrainedChoiceConsiderations;
	}

	public void setConstrainedChoiceConsiderations(
			ConstrainedChoiceConsiderations constrainedChoiceConsiderations) {
		this.constrainedChoiceConsiderations = constrainedChoiceConsiderations;
	}

	private List<SequencingRule> preConditionRules = new LinkedList<SequencingRule>();
	
	public void addPreConditionRule(SequencingRule sequencingRule) {
		this.preConditionRules.add(sequencingRule);
	}
	
	private List<SequencingRule> postConditionRules = new LinkedList<SequencingRule>();
	
	public void addPostConditionRule(SequencingRule sequencingRule) {
		this.postConditionRules.add(sequencingRule);
	}
	
	private List<SequencingRule> exitConditionRules = new LinkedList<SequencingRule>();
	
	public void addExitConditionRule(SequencingRule sequencingRule) {
		this.exitConditionRules.add(sequencingRule);
	}
	
	private Objectives objectives;
	
	public Objectives getObjectives() {
		if (objectives == null)
			objectives = new Objectives();
		return objectives;
	}

	public void setObjectives(Objectives objectives) {
		this.objectives = objectives;
	}

	public void assign(Sequencing source) {
		if (getControlMode() == null)
			setControlMode(new ControlMode());
		getControlMode().assign(source.getControlMode());
		
		assign(getPreConditionRules(), source.getPreConditionRules());
		assign(getPostConditionRules(),source.getPostConditionRules());
		assign(getExitConditionRules(),source.getExitConditionRules());
		
		if (getLimitConditions() == null)
			setLimitConditions(new LimitConditions());
		getLimitConditions().assign(source.getLimitConditions());
		
		if (getRollupRules() == null)
			setRollupRules(new RollupRules());
		getRollupRules().assign(source.getRollupRules());
		
		getObjectives().assign(source.getObjectives());
		
		if (getRandomizationControls() == null)
			setRandomizationControls(new RandomizationControls());
		getRandomizationControls().assign(source.getRandomizationControls());
		
		if (getDeliveryControls() == null) 
			setDeliveryControls(new DeliveryControls());
		getDeliveryControls().assign(source.getDeliveryControls());
		
		if (getConstrainedChoiceConsiderations() == null) 
			setConstrainedChoiceConsiderations(new ConstrainedChoiceConsiderations());
		getConstrainedChoiceConsiderations().assign(source.getConstrainedChoiceConsiderations());
		
		if (getRollupConsiderations() == null)
			setRollupConsiderations(new RollupConsiderations());
		getRollupConsiderations().assign(source.getRollupConsiderations());
		
		
	}

	private void assign(List<SequencingRule> preConditionRules2,
			List<SequencingRule> preConditionRules3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
	
		String ret = "<imsss:sequencing>\n";
		if (getControlMode() != null)
			ret += getControlMode();
		if (!getPreConditionRules().isEmpty() || !getPostConditionRules().isEmpty() || !getExitConditionRules().isEmpty()) {
			ret += "<imsss:sequencingRules>\n";
			
			ret += print("preConditionRule", getPreConditionRules());
			ret += print("exitConditionRule", getExitConditionRules());
			ret += print("postConditionRule", getPostConditionRules());
			
			ret += "</imsss:sequencingRules>\n";
		}
		if (getLimitConditions() != null) {
			ret += getLimitConditions();
		}
		if (getRollupRules() != null) {
			ret += getRollupRules();
		}
		if (getObjectives().getPrimaryObjective() != null || getObjectives().getObjectives().size() > 0) {
			ret += getObjectives();
		}
		if (getRandomizationControls() != null) {
			ret += getRandomizationControls();
		}
		if (getDeliveryControls() != null) {
			ret += getDeliveryControls();
		}
		if (getConstrainedChoiceConsiderations() != null) {
			ret += getConstrainedChoiceConsiderations();
		}
		if (getRollupConsiderations() != null) {
			ret += getRollupConsiderations();
		}
		return ret + "</imsss:sequencing>";
		
	}

	private String print(String name, List<SequencingRule> listRules) {
		String ret = "";
		for (SequencingRule sr:listRules) {
			ret += "<imsss:"+name+">\n" + sr + "</imsss:"+name+">\n";
		}
		return ret;
	}

	public List<SequencingRule> getPreConditionRules() {
		return preConditionRules;
	}
	
	private List<SequencingRule> getSubsetPreConditionRules(RuleAction ruleAction) {
		List<SequencingRule> res = new ArrayList<SequencingRule>();
		for (SequencingRule sr:preConditionRules) {
			if (sr.getEnumRuleAction().equals(ruleAction))
				res.add(sr);
		}
		return res;
	}
	
	public List<SequencingRule> getSkippedConditionRules() {
		return getSubsetPreConditionRules(RuleAction.skip);
	}

	public List<SequencingRule> getPostConditionRules() {
		return postConditionRules;
	}

	public List<SequencingRule> getExitConditionRules() {
		return exitConditionRules;
	}

	public List<SequencingRule> getStoppedConditionRules() {
		return getSubsetPreConditionRules(RuleAction.stopForwardTraversal);
	}

	public List<SequencingRule> getHideFromChoiceConditionRules() {
		return getSubsetPreConditionRules(RuleAction.hiddenFromChoice);
	}

	public List<SequencingRule> getDisabledConditionRules() {
		return getSubsetPreConditionRules(RuleAction.disabled);
	}

}
