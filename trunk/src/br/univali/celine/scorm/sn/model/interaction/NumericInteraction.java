package br.univali.celine.scorm.sn.model.interaction;

public class NumericInteraction extends Interaction {

	public NumericInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
		String values[] = pattern.split("\\[:\\]");
		if (values.length > 0) {
			Double.valueOf(values[0]);
			if (values.length>1)
				Double.valueOf(values[1]);
		}
	}

	@Override
	protected void testLearnerResponse(String learnerResponse) throws Exception {
		Double.valueOf(learnerResponse);
	}

	
	
}
