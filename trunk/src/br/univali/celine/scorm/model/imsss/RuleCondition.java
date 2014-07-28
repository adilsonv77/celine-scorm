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
