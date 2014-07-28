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
