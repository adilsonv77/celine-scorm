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
