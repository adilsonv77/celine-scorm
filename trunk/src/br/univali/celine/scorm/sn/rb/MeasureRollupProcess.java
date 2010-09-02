package br.univali.celine.scorm.sn.rb;

import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.LocalObjective;

// [RB.1.1]
public class MeasureRollupProcess {

	public void run(LearningActivity activity) {
		
		double totalWeighted = 0;
		boolean validData = false;
		double countedMeasures = 0;
		LocalObjective targetObjective = null;
		
		for (LocalObjective objective:activity.getObjectives()) {
			// find the target objective for the rollep-up measure
			if (objective.isContributesToRollup() == true) {
				targetObjective = objective;
				break;
			}
		}
		
		if (targetObjective != null) {
			for (LearningActivity child:activity.getChildren()) {
				
				if (child.isTracked()) {
					// only include tracked children
					LocalObjective rolledUp = null;
					for (LocalObjective objective:child.getObjectives()) {
						if (objective.isContributesToRollup() == true) {
							rolledUp = objective;
							break;
						}
					} 
					
					if (rolledUp != null) {
						countedMeasures += child.getRolledUpObjectiveMeasureWeight();
						if (rolledUp.isMeasureStatus() == true) {
							totalWeighted += (rolledUp.getNormalizedMeasure()*child.getRolledUpObjectiveMeasureWeight());
							validData = true;
						}
					} else
						return; // one of the children does not include a rolled-up objective
				}
				
			}
			
			if (validData == false) {
				// no tracking state rolled-up, cannot determine the rolled-up measure
				targetObjective.setMeasureStatus(false);
			} else {
				if (countedMeasures > 0) {
					// set the rolled-up measure for the target objective
					// setNormalizedMeasure já atribui como measure status = true
					targetObjective.setNormalizedMeasure(totalWeighted/countedMeasures);
				} else {
					// no children contributed weight
					targetObjective.setMeasureStatus(false);
				}
			}
		}
		// no objective contributes to rollup, so we cannot set anything
	}

}
