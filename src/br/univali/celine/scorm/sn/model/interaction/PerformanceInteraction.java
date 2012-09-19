package br.univali.celine.scorm.sn.model.interaction;

import java.util.Arrays;
import java.util.List;

public class PerformanceInteraction extends Interaction {

	public PerformanceInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));

		if (respostas.size() == 0)
			throw new Exception();
		
		// apesar desse daqui usar a sintaxe step_name[.]step_answer[,], ele 
		// permite que ocorra ou um outro !!!
	}

	
}
