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
package br.univali.celine.lmsscorm;

import br.univali.celine.scorm.sn.model.interaction.Interaction;

public class TrackInteraction extends Interaction {

	private String activityId;
	private int index;

	public TrackInteraction(String activityId, String id) {
		super(id);
		this.activityId = activityId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public Interaction makeInteraction() throws Exception {
		
		Interaction interact = createInteraction(getType());
		interact.setLearnerResponse(getLearnerResponse());
		
		for (int i=0;i<getCorrectResponsesPatternCount();i++)
			interact.setCorrectResponsesPattern(i, getCorrectResponsesPattern(i));
		
		return interact;
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
	}
	
	
}
