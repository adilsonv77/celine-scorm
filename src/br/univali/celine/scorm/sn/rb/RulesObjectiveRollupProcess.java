package br.univali.celine.scorm.sn.rb;

import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.LocalObjective;

// RB.1.2b
public class RulesObjectiveRollupProcess implements ObjectiveRollupProcess {

	public void run(LearningActivity activity) {
		LocalObjective targetObjective = null;
		
		for (LocalObjective objective:activity.getObjectives()) {
			if (objective.isContributesToRollup() == true) {
				// identify the objective that may be altered based on the activity's children's
				//   rolled-up status
				
				targetObjective = objective;
				break;
			}
		}
		
		if (targetObjective != null) {

			// process all not satisfied rules first
			ProcessProvider pp = ProcessProvider.getInstance();
			RollupRuleCheckSubprocess rollupRuleCheck = pp.getRollupRuleCheckSubprocess();
			
			if (rollupRuleCheck.run(activity, RollupAction.notsatisfied) == true) {
				
				targetObjective.setProgressStatus(true);
				targetObjective.setSatisfiedStatus(false);
				
			}
			
			// process all satisfied rules last
			if (rollupRuleCheck.run(activity, RollupAction.satisfied) == true) {
				
				targetObjective.setProgressStatus(true);
				targetObjective.setSatisfiedStatus(true);
				
			}
			
		}

	}

}
