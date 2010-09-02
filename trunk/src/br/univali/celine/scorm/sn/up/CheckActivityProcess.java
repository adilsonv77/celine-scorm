package br.univali.celine.scorm.sn.up;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;

// UP.5
public class CheckActivityProcess {

	public boolean run(LearningActivity activity) {
		
		// make sure the activity is not disabled
		RuleAction ret = ProcessProvider.getInstance().getSequencingRulesCheckProcess().run(activity, activity.getDisabledSequencingRules());
		
		if (ret != null)
			return true;
		
		// make sure the activity does not violate any limit condition
		return ProcessProvider.getInstance().getLimitConditionsCheckProcess().run(activity); // false = activity is allowed
	}
	
}
