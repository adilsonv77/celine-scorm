package br.univali.celine.scorm.sn.model;

/*
 * 4.2.4. Completion Status
 * 
 */
public enum CompletionStatusValue {

	completed, 
	incomplete, 
	notAttempted {
		@Override
		public String toString() {
			return "not attempted";
		}
	} , 
	unknown;

	public static String[] CompletionStatusValueString = {"completed", "incomplete", "not attempted", "unknown"};
	
	public static CompletionStatusValue valor(String valor) {
		
		if (valor.equals(CompletionStatusValueString[2])) {
			valor = "notAttempted";
		}
		
		return valueOf(valor);
	}
	
}
