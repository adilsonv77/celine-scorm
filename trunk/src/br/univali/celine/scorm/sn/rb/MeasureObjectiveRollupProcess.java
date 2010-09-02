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
