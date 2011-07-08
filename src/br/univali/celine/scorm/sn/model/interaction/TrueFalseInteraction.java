package br.univali.celine.scorm.sn.model.interaction;

import br.univali.celine.scorm.rteApi.ErrorManager;


public class TrueFalseInteraction extends Interaction {

	public TrueFalseInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern) throws Exception {

		if (index > 0)
			throw new Exception(""+ErrorManager.GeneralSetFailure);
		if (!(pattern.equals("true") || pattern.equals("false"))){
			throw new Exception();
		}
	}

	@Override
	protected void testGetResponsesPattern(int index) throws Exception {
		if (index > 0)
			throw new Exception();
	}
	
	
}
