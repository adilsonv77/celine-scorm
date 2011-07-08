package br.univali.celine.scorm.sn.model.interaction;


public class LikertInteraction extends Interaction {
	
	public LikertInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern) throws Exception {
		if (pattern.contains("[]") || pattern.contains("[,]"))
			throw new Exception();
	}


}
