package br.univali.celine.scorm.sn.rb;

import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// RB.1.3
public class ActivityProgressRollupProcess {

	public void run(LearningActivity activity) {
		
		ProcessProvider pp = ProcessProvider.getInstance();
		
		// process all incomplete rules first
		if (pp.getRollupRuleCheckSubprocess().run(activity, RollupAction.incomplete)) {
			activity.setAttemptProgressStatus(true);
			activity.setAttemptCompletionStatus(false);
		}
		
		// process all complete rules first
		if (pp.getRollupRuleCheckSubprocess().run(activity, RollupAction.completed)) {
			activity.setAttemptProgressStatus(true);
			activity.setAttemptCompletionStatus(true);
		}
		
	}

}
