package br.univali.celine.scorm.sn.model.dataTypes;

public class LongIdentifierType {

	public static boolean validate(String novoValor) {
		// “urn:”<NID>“:”<NSS> 
		
		novoValor = novoValor.trim();
		if (novoValor.length() == 0)
			return false;
		
		if (novoValor.contains(" ")) {
			return false;
		}
		
		if (novoValor.startsWith("urn:")) {
			String peaces[] = novoValor.split(":");
			if (peaces.length != 3)
				return false;
			if (peaces[1].length()==0||peaces[2].length()==0)
				return false;
		}
		
		return true;
	}
	
}
