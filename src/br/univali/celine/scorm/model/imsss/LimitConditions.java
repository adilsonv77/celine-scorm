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
package br.univali.celine.scorm.model.imsss;

import br.univali.celine.scorm.model.dataTypes.Duration;

public class LimitConditions {

	private int attemptLimit = 0; // not initialized
	private String attemptAbsoluteDurationLimit = null; 
	private Duration duracao = null; // not initialized
	
	public int getAttemptLimit() {
		return attemptLimit;
	}
	public void setAttemptLimit(int attemptLimit) {
		this.attemptLimit = attemptLimit;
	}
	
	public double getAttemptAbsoluteDurationLimitInSeconds() {
		if (duracao == null)
			return -1;
		
		return duracao.toSeconds();
	}
	
	public String getAttemptAbsoluteDurationLimit() {
		return attemptAbsoluteDurationLimit;
	}
	
	public void setAttemptAbsoluteDurationLimit(String attemptAbsoluteDurationLimit) {
		this.attemptAbsoluteDurationLimit = attemptAbsoluteDurationLimit;
		this.duracao = new Duration(attemptAbsoluteDurationLimit);
	}
	
	@Override
	public String toString() {
		if (attemptLimit == 0 && attemptAbsoluteDurationLimit == null)
			return "";
		
		String ret = "";
		if (attemptLimit > 0) {
			ret += "attemptLimit=\""+attemptLimit+"\"";
		}
		
		if (attemptAbsoluteDurationLimit != null) {
			ret += " attemptAbsoluteDurationLimit=\""+attemptAbsoluteDurationLimit+"\"";
		}
		
		return "<imsss:limitConditions "+ret+"/>\n";
	}
	public void assign(LimitConditions limitConditions) {
		// TODO Auto-generated method stub
		
	}
	
}
