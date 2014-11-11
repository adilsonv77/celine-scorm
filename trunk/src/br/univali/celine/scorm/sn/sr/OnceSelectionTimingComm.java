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
package br.univali.celine.scorm.sn.sr;

import java.util.LinkedList;
import java.util.List;

import br.univali.celine.scorm.sn.model.LearningActivity;

// Parte do SR.1
public class OnceSelectionTimingComm implements SelectionTimingComm {

	public void run(LearningActivity activity) {
		if (activity.isProgressStatus() == false) {
			// if the activity has not been attempted yet
			
			if (activity.isSelectionCountStatus() == true) {
				
				List<LearningActivity> newChildren = new LinkedList<LearningActivity>();
				java.util.Random rand = new java.util.Random();
				
				int count = activity.getSelectionCount();
				List<LearningActivity> children = activity.getChildren();
				int lim = children.size();
				while (newChildren.size() < count) {
					
					int n = Math.abs(rand.nextInt()) % lim;
					LearningActivity sortedActivity = children.get(n);
					if (newChildren.contains(sortedActivity) == false)
						newChildren.add(sortedActivity);
					
				}
				
				activity.setAvailableChildren(newChildren);
			}
			
		}
		
	}

}
