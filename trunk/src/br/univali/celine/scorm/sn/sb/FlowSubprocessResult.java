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
package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.model.LearningActivity;

public class FlowSubprocessResult {

	private LearningActivity identifiedActivity;
	private boolean deliverable = false;
	private boolean endSequencingSession = false;
	private int exception;
	
	public FlowSubprocessResult(int exception) {
		this.exception = exception;
		this.deliverable = exception == 0;
	}
	
	public FlowSubprocessResult(LearningActivity identifiedActivity) {
		this.identifiedActivity = identifiedActivity;
		this.deliverable = true;
	}
	
	public FlowSubprocessResult() {}
	
	public boolean isDeliverable() {
		return deliverable;
	}
	public void setDeliverable(boolean deliverable) {
		this.deliverable = deliverable;
	}
	public boolean isEndSequencingSession() {
		return endSequencingSession;
	}
	public void setEndSequencingSession(boolean endSequencingSession) {
		this.endSequencingSession = endSequencingSession;
	}
	public int getException() {
		return exception;
	}
	public void setException(int exception) {
		this.exception = exception;
	}
	public LearningActivity getIdentifiedActivity() {
		return identifiedActivity;
	}
	public void setIdentifiedActivity(LearningActivity identifiedActivity) {
		this.identifiedActivity = identifiedActivity;
	}
	
	
}
