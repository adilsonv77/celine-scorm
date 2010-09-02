package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.rteApi.ErrorManager;

public interface DotNotationCommand {
	
	String getValue(ErrorManager errorManager, int n, int size);
	boolean setValue(ErrorManager errorManager, int n, int size, String novoValor);

}
