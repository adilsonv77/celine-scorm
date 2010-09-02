package br.univali.celine.scorm.sn.model.dataTypes;

public class LocalizedStringType {
	
	public static boolean validate(String novoValor) {
	
		if (!novoValor.startsWith("{lang=")) {
			return true;
		}
		String peaces[] = novoValor.split("}");
		peaces[0] = peaces[0].substring(6);
		return peaces[0].length()>=2;
	}
	
	public static void main(String[] args) {
		System.out.println(validate("{lang=}"));
		System.out.println(validate("{lang=e}"));
		System.out.println(validate("{lang=en}"));
		System.out.println(validate("en"));
}
}
