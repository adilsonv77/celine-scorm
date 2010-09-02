package br.univali.celine.scorm.sn.sr;

import br.univali.celine.scorm.sn.model.LearningActivity;

// [SR.2]
public class RandomizeChildrenProcess {

	public void run(LearningActivity activity) {
		
		// cannot apply randomization to a leaf activity
		if (activity.getChildren().size() == 0) 
			return;
		
		// cannot apply randomization to a suspended or active activity
		if (activity.isSuspended() || activity.isActive())
			return;
		
		switch (activity.getRandomizationTiming()) {
			case never: 
				break;
			
			case once:
				if (activity.isProgressStatus() == false)
					// if the activity has not been attempted yet
					if (activity.isRandomizeChildren() == true)
						activity.reorderRandomly();
				break;
				
			case onEachNewAttempt:
				if (activity.isRandomizeChildren() == true)
					activity.reorderRandomly();
				
		}
	}
	
}
