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
package br.univali.celine.scorm.sn.nb;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.model.TerminationRequest;

/**
 * Navigation Request Process [NB.2.1]
 * 
 * Passo 7
 * 
 * @author Adilson Vahldick
 *
 */

public class ChoiceNavigationRequestCommand implements NavigationRequestCommand {

	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		NavigationRequestResult res = null;
		
		// make sure the target activity exists in activity tree
		if (activityTree.contains(navigationRequest.getLearningActivity())) {
			
			// validate that a 'choice' sequencing request can be processed on the target activity
			if (activityTree.getRootActivity() == navigationRequest.getLearningActivity() ||
					navigationRequest.getLearningActivity().getParent().isSequencingControlChoice() == true) {
				
				// attempt to start the sequencing session through choice
				if (activityTree.getCurrentActivity() == null) {
					res = NavigationRequestResult.buildValid(SequencingRequest.CHOICE);
					res.setTargetActivity(navigationRequest.getLearningActivity());
				} else {
					
					if (activityTree.getCurrentActivity().isSibling(navigationRequest.getLearningActivity()) == false) {
						
						LearningActivity ancestor = activityTree.findCommonAncestor(activityTree.getCurrentActivity(), navigationRequest.getLearningActivity());
						
						/* 7.1.1.2.2.
						 * The common ancestor will not terminate as a result of processing the choice sequencing request, 
						 * unless the common ancestor is the Current Activity - the current activity should always be included in the 
						 * activity path
						 */
						LearningActivity activityPath[] = activityTree.makeActivityPath(ancestor, activityTree.getCurrentActivity());
						if (activityPath.length > 0) { // activity path is not empty
							
							for (LearningActivity activity:activityPath) {
								
								if (activity.isActive() == true && activity.isSequencingControlChoiceExit() == false) {
									res = NavigationRequestResult.buildNotValid(8); // NB.2.1-8
									break;
								}
								
							}
							
						} else {
							res = NavigationRequestResult.buildNotValid(9); // NB.2.1-9
						}
					}	
					
					if (res == null) { // 7.1.1.3.
						if (activityTree.getCurrentActivity().isActive() == true) {
							if (activityTree.getCurrentActivity().isSequencingControlChoiceExit() == false) {
								res = NavigationRequestResult.buildNotValid(8); // NB.2.1-8
							} else {
								res = NavigationRequestResult.buildValid(SequencingRequest.CHOICE);
								res.setTargetActivity(navigationRequest.getLearningActivity());
								res.setTerminationRequest(TerminationRequest.EXIT);
								
							}
						} else {
							
							res = NavigationRequestResult.buildValid(SequencingRequest.CHOICE);
							res.setTargetActivity(navigationRequest.getLearningActivity());
							
						}
					}
					
				}
				
			} else {
				res = NavigationRequestResult.buildNotValid(10); // NB.2.1-10
			}
			
		} else {
			res = NavigationRequestResult.buildNotValid(11); // NB.2.1-11
		}
		
		return res;
	}

}
