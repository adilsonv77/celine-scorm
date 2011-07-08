package br.univali.celine.scorm.dataModel.cmi;

import java.util.Arrays;

import br.univali.celine.scorm.rteApi.ErrorManager;

public abstract class DotNotationCommandImpl implements DotNotationCommand {

	private String[] validValues;

	public DotNotationCommandImpl(String[] validValues) {
		this.validValues = validValues;
	}
	
	public DotNotationCommandImpl() {
		this.validValues = null;
	}
	
	public final String getValue(ErrorManager errorManager, int n, int size) {
		
		if (n >= size) {
			errorManager.attribError(ErrorManager.GeneralGetFailure);
			return "";
		}
	
		return doGetValue(errorManager, n, size);
	}

	
	public final boolean setValue(ErrorManager errorManager, int n, int size,
			String novoValor) {
		
		if (n > size) {
			errorManager.attribError(ErrorManager.GeneralSetFailure);
			return false;
		}
		
		if (n == size) {
			errorManager.attribError(ErrorManager.DataModelDependencyNotEstablished);
			return false;
		}
	
		if (validValues != null)
			if (Arrays.binarySearch(validValues, novoValor) < 0) {
				errorManager.attribError(ErrorManager.DataModelElementTypeMismatch);
				return false;
			}
		
		return doSetValue(errorManager, n, size, novoValor);
	}

	protected abstract boolean doSetValue(ErrorManager errorManager, int n, int size,
			String novoValor) ;

	protected abstract String doGetValue(ErrorManager errorManager, int n, int size);
}
