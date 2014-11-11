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
import br.univali.celine.scorm.sn.model.TraversalDirection;

public class FlowTreeTraversalSubprocessResult {

	private LearningActivity nextActivity;
	private TraversalDirection traversalDirection = TraversalDirection.none;
	private int exception;
	private boolean endSequencingSession = false;

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

	public TraversalDirection getTraversalDirection() {
		return traversalDirection;
	}

	public void setTraversalDirection(TraversalDirection traversalDirection) {
		this.traversalDirection = traversalDirection;
	}

	public LearningActivity getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(LearningActivity nextActivity) {
		this.nextActivity = nextActivity;
	}
	
}
