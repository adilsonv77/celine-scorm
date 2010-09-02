package br.univali.celine.scorm.sn.model.interaction;

import java.util.Arrays;
import java.util.List;

public class FillInInteraction extends Interaction {

	public FillInInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(String pattern) throws Exception {
		/*
		 * The following requirements shall be adhered to when building the characterstring:
			o The array may contain one or more localized_string_types.
			o If the array contains more than one localized_string_type 
			  (the interaction has multiple correct answers – 
			  all of which are required), then they shall be separated by 
			  the special reserved token “[,]”.
			o Each localized_string may occur more than once.
			o The order of the localized_strings is significant.
		 */

		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));
		if (respostas.size() == 0)
			throw new Exception();
	}

	
}
