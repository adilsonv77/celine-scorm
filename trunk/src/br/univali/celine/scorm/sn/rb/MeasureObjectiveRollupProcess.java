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
package br.univali.celine.scorm.sn.rb;

import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.LocalObjective;

// RB.1.2a
public class MeasureObjectiveRollupProcess implements ObjectiveRollupProcess {

	public void run(LearningActivity activity) {
		
		LocalObjective targetObjective = null;
		for (LocalObjective objective:activity.getObjectives()) {
			if (objective.isContributesToRollup() == true) {
				// identify the objective that may be altered based on the activity's children's rolled-up measure
				targetObjective = objective;
				break;
			}
		}
		
		if (targetObjective != null) {
			
			if (targetObjective.isSatisfiedByMeasure() == true) {
				// if the objective is satisfied by measure, test the rolled-up measure against the defined threshold
				if (targetObjective.isMeasureStatus() == false) {
					// no measure known, so objective status is unreliable
					targetObjective.setProgressStatus(true);
				} else {
					
					if (activity.isActive() == false || 
						(activity.isActive() == true && activity.isMeasureSatisfactionIfActive() == true)) {
						
						if (targetObjective.getNormalizedMeasure() >= targetObjective.getMinNormalizedMeasure()) {
							
							targetObjective.setProgressStatus(true);
							targetObjective.setSatisfiedStatus(true);
							
						} else {
							
							targetObjective.setProgressStatus(true);
							targetObjective.setSatisfiedStatus(false);
							
						}
					} else {
						// incomplete information, do not evaluate objective status
						targetObjective.setProgressStatus(false);
					}
				}
			}
			
		}

	}

}
