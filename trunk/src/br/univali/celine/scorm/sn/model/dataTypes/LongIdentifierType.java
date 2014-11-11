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
