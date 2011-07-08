package br.univali.celine.scorm.sn.model.interaction;

import java.util.Arrays;
import java.util.List;

public class MatchingInteraction extends Interaction {

	public MatchingInteraction(String id) {
		super(id);
	}


	@Override
	protected void testPattern(int index, String pattern) throws Exception {
		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));

		// SetValue(“cmi.interactions.0.learner_response”, “2[.]c[,]1[.]a[,]3[.]b”)
		for (String resposta:respostas) {
			if (resposta.split("\\[.\\]").length != 2)
				throw new Exception();
		}
	}

	
	
}
