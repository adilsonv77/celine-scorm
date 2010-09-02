package br.univali.celine.scorm.sn.rb;

import java.util.HashMap;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.ObjectiveRollupUsing;

// RB.1.5
/*
 * Rollup: The process of determining a cluster activity’s status information 
 * based on its children’s status information. [Section 4.6]
 * This is synonymous with "Apply the Overall Rollup Process" 
 */
public class OverallRollupProcess {

	private HashMap<ObjectiveRollupUsing, ObjectiveRollupProcess> comms = new HashMap<ObjectiveRollupUsing, ObjectiveRollupProcess>();
	
	public OverallRollupProcess() {
		comms.put(ObjectiveRollupUsing.MEASURE, new MeasureObjectiveRollupProcess());
		comms.put(ObjectiveRollupUsing.RULES, new RulesObjectiveRollupProcess());
	}
	
	public void run(ActivityTree activityTree, LearningActivity activity) {
		
		LearningActivity[] activityPath = activityTree.makeActivityPathInverseOrder(activityTree.getRootActivity(), activity);
		if (activityPath.length == 0) {
			// nothing to rollup
		} else {
			ProcessProvider pp = ProcessProvider.getInstance();
			for (LearningActivity eachActivity:activityPath) {

				if (eachActivity.hasChildren()) {
					// only apply Measure Rollup to non-leaf activities
					pp.getMeasureRollupProcess().run(eachActivity);
				}
				
				// Apply the appropriate Objective Rollup Process to the activity 
				// make a decision between RB.1.2a and RB.1.2b
				ObjectiveRollupProcess comm = comms.get(eachActivity.getObjectiveRollupUsing());
				comm.run(eachActivity);

				pp.getActivityProgressRollupProcess().run(eachActivity);
			}
			
		}
	}

}
