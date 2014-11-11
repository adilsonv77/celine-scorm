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
package br.univali.celine.scorm.sn.tb;

import br.univali.celine.scorm.sn.model.SequencingRequest;

public class TerminationRequestResult {

	public static TerminationRequestResult buildValid(SequencingRequest sequencingRequest) {
		
		TerminationRequestResult trr = new TerminationRequestResult();
		trr.setValid(true);
		trr.setSequencingRequest(sequencingRequest);
		return trr;
		
	}
	
	public static TerminationRequestResult buildNotValid(int exception) {
		
		TerminationRequestResult trr = new TerminationRequestResult();
		trr.setException(exception);
		return trr;
		
	}
	
	private boolean valid = false; 
	private SequencingRequest sequencingRequest;
	private int exception;
	
	public int getException() {
		return exception;
	}
	public void setException(int exception) {
		this.exception = exception;
	}
	public SequencingRequest getSequencingRequest() {
		return sequencingRequest;
	}
	public void setSequencingRequest(SequencingRequest sequencingRequest) {
		this.sequencingRequest = sequencingRequest;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
	
}
