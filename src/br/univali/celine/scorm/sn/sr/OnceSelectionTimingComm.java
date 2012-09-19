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
