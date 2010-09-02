package br.univali.celine.scorm.sn.model.interaction;

import java.util.Arrays;
import java.util.List;

public class MultipleChoiceInteraction extends Interaction {

	public MultipleChoiceInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(String pattern) throws Exception {
		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));
		
		/*
		 * The following requirements shall be adhered to when building the characterstring:
			o The set may contain zero or more short_identifier_types.
			o If the set contains more than one short_identifier_type, then they shall be separated by the special reserved token “[,]”.
			o Each short_identifier_type shall occur in the set only once.
		 */
		
		for (int x = 0; x<respostas.size(); x++) {
			for (int y=x+1;y<respostas.size(); y++)
				if (respostas.get(x).equals(respostas.get(y)))
					throw new Exception();
		}
		
		
	}

}
