package br.univali.celine.scorm.sn.sb;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;
import br.univali.celine.scorm.sn.up.SequencingRulesCheckProcess;

/**
 * Choice Activity Traversal Subprocess [SB.2.4]
 * 
 * 
 * @author Adilson Vahldick
 *
 */

public class ChoiceActivityTraversalSubprocess {

	public ChoiceActivityTraversalSubprocessResult run(LearningActivity activity, TraversalDirection traversalDirection) {
		
		if (traversalDirection == TraversalDirection.forward) {
			SequencingRulesCheckProcess seqRulesCheckProcess = ProcessProvider.getInstance().getSequencingRulesCheckProcess();
			if (seqRulesCheckProcess.run(activity, activity.getStopSequencingRules()) != null) {
				return new ChoiceActivityTraversalSubprocessResult(27);
			} else {
				return new ChoiceActivityTraversalSubprocessResult();
			}
		}

		// TraversalDirection.backward
		LearningActivity parent = activity.getParent();
		if (parent != null) {
			if (parent.isSequencingControlForwardOnly())
				return new ChoiceActivityTraversalSubprocessResult(28);
		} else {
			// cannot walk backward from the root of the activity tree
			return new ChoiceActivityTraversalSubprocessResult(29);
		}
		
		
		return new ChoiceActivityTraversalSubprocessResult();
		
	}
	
}
