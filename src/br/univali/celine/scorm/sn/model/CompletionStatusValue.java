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
