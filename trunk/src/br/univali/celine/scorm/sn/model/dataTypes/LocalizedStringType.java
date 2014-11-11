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
