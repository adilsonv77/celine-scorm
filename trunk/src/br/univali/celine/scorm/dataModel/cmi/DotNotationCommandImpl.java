/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
