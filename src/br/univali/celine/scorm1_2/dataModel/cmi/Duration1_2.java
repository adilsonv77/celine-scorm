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
package br.univali.celine.scorm1_2.dataModel.cmi;


public class Duration1_2 {

	public static void test(String value) throws Exception {
		String[] peaces = value.split(":");
		if (peaces.length != 3) {
			throw new Exception("More than 3 fields");
		}
		
		if (peaces[0].length() < 2 || peaces[0].length() > 4) {
			throw new Exception("Hours incorrect");
		}
		
		Integer.parseInt(peaces[0]); 
		// hora ok
		
		if (peaces[1].length() != 2) {
			throw new Exception("Minutes incorrect");
		}
		
		Integer.parseInt(peaces[1]); 
		// minuto ok

		String[] seconds = peaces[2].split("\\."); 
		if (seconds[0].length() != 2) {
			throw new Exception("Seconds incorrect");
		}
		
		Integer.parseInt(seconds[0]);
		
		if (seconds.length > 1) {
			if (seconds[1].length() > 2) {
				throw new Exception();
			}
			
			Integer.parseInt(seconds[1]);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Duration1_2.test("01:00:10");
		Duration1_2.test("01:00:10.2");
		Duration1_2.test("0001:00:10.26");
		Duration1_2.test("0001:00:10");
		Duration1_2.test("0001:00:10.2");
		Duration1_2.test("0001:00:10.26");
	}
}
