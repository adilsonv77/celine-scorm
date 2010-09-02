package br.univali.celine.scorm.sn.model.interaction;


public class TrueFalseInteraction extends Interaction {

	public TrueFalseInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(String pattern) throws Exception {

		if (!(pattern.equals("true") || pattern.equals("false"))){
			throw new Exception();
		}
	}

	
	
}
