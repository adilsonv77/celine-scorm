package br.univali.celine.scorm.sn.model.interaction;

public enum InteractionType {

	choice, true_false, fill_in, long_fill_in, likert, matching, performance, sequencing, numeric, other; 
	
	
	public static InteractionType valueOfEx(String value) {
		if (value.indexOf("-") >= 0) {
			value = value.replace('-', '_');
		}
		
		return valueOf(value);
	}
}
