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
package br.univali.celine.scorm.sn.db;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;

//[DB.2]
public class ContentDeliveryEnvironmentProcess {

	public int run(ActivityTree activityTree, LearningActivity deliveryRequest) {
		
		// if the attempt on the current activity has not been terminated, we cannot deliver new content
		if (activityTree.getCurrentActivity()!=null && activityTree.getCurrentActivity().isActive() == true) {
			
			// delivery request is invalid - the current activity has not been terminated
			return 55;
		}
		
		// content is about to be delivered, clear any existing suspend all state
		if (deliveryRequest != activityTree.getSuspendActivity()) {
			ProcessProvider.getInstance().getClearSuspendedActivitySubprocess().run(activityTree, deliveryRequest);
		}
		
		// make sure that all attempts that should end are terminate
		ProcessProvider.getInstance().getTerminateDescendentAttemptsProcess().run(activityTree, deliveryRequest);
		
		// begin all attempts required to deliver the identified activity
		LearningActivity[] path = activityTree.makeActivityPath(activityTree.getRootActivity(), deliveryRequest);
		for (LearningActivity activity:path) {
			if (activity.isActive() == false) {
				if (activity.isTracked() == true) {
					// if the previous attempt on the activity ended due to a suspension, clear the suspended state;
					//  do not start a new attempt
					if (activity.isSuspended()) {
						activity.resume();
					} else {
						activity.incAttemptCount(); // begin a new attempt on the activity
						 // is the first attempt on the activity ?
						if (activity.getAttemptCount() == 1) {
							activity.initializeActivityProgressInformation(); // adicao minha !!!
							activity.setProgressStatus(true);
						}
						// initialize tracking information for the new attempt
						activity.initializeObjectiveProgressInformation();
						activity.initializeAttemptProgressInformation();
						
						// isso estava por ultimo nesse método... o problema que nao funciona com os pais da atividade
						activity.startAttemptAbsoluteDuration();
						activity.startAttemptExperiencedDuration();
					}
				}
				activity.setActive(true);
			}
		}
		
		// the activity identified for delivery becomes the current activity
		activityTree.setCurrentActivity(deliveryRequest);
		activityTree.setSuspendActivity(null);
		
		// cfe o pseudocode, as linhas abaixo sao executadas após o sistema entregar o conteúdo ao usuario
		/* if (deliveryRequest.isTracked() == false) {
			deliveryRequest.startAttemptAbsoluteDuration();
			deliveryRequest.startAttemptExperiencedDuration();
		 }*/
		return 0;
		
	}
	
}
