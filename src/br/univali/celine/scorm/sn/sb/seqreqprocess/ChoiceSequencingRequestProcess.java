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
package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.TraversalDirection;
import br.univali.celine.scorm.sn.sb.ChoiceActivityTraversalSubprocess;
import br.univali.celine.scorm.sn.sb.ChoiceActivityTraversalSubprocessResult;
import br.univali.celine.scorm.sn.sb.FlowSubprocessResult;

/**
 * Choice Sequencing Request Process [SB.2.9]
 * 
 * Passo (7.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */
public class ChoiceSequencingRequestProcess extends InitialSequencingRequest implements SequencingRequestCommand {

	
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {

		// there must be a target activity for choice
		LearningActivity target = activityTree.getTargetActivity();
		if (target == null) {
			return new ResultSequencingRequestCommand(37);
		}
		
		LearningActivity root = activityTree.getRootActivity();
		
		if (root.getAvailableChildren().size() == 0) {
			percorrerArvore(root); // isso daqui é útil quando Choice é o request de inicio
		}
		
		LearningActivity[] path = activityTree.makeActivityPath(root, target);
		for (LearningActivity activity:path) {
			if (activity != root) {
				if (activity.getParent().getAvailableChildren().contains(activity) == false) {
					// the activity is currently not available
					return new ResultSequencingRequestCommand(38);
				}
			}
			
			// cannot choose something that is hidden
			RuleAction action = ProcessProvider.getInstance().getSequencingRulesCheckProcess().run(activity, activity.getHideFromChoiceSequencingRules());
			if (action != null) {
				return new ResultSequencingRequestCommand(39);
			}
		}
		
		if (target != root) {
			// confirm that control mode allow choice of the target
			if (target.getParent().isSequencingControlChoice() == false) {
				return new ResultSequencingRequestCommand(40);
			}
		}
		
		LearningActivity commonAncestor;
		// has the sequencing session already begun?
		if (activityTree.getCurrentActivity() != null) {
			commonAncestor = activityTree.findCommonAncestor(activityTree.getCurrentActivity(), target);
		} else {
			// no, choosing the target will start the sequencing session
			commonAncestor = root;
		}
		LearningActivity current = activityTree.getCurrentActivity(); // antes dessa linha nao posso jogar isso
		if (current == target) {
			// nothing to do
		} else {
			ResultSequencingRequestCommand exitCases = null;
			if (current != null && current.isSibling(target)) {
				exitCases = doCase2(activityTree);
			} else {
				if (current == commonAncestor || current == null) {
					exitCases = doCase3(activityTree, commonAncestor);
				} else {
					if (target == commonAncestor) {
						exitCases = doCase4(activityTree);
					} else {
						 // if (target.isForwardFrom(commonAncestor)) 
							exitCases = doCase5(activityTree, commonAncestor);
						
					}
				}
			}
			
			if (exitCases != null)
				return exitCases;
		}
		
		if (target.isLeaf()) {
			ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
			res.setDeliveryRequest(target);
			return res;
		}
			
		ProcessProvider pp = ProcessProvider.getInstance();
		
		// the identified activity is a cluster. Enter the cluster and attempt to find a descendent leaf to deliver
		FlowSubprocessResult flowres = pp.getFlowSubprocess().run(activityTree, target, TraversalDirection.forward, true);
		if (flowres.isDeliverable() == false) {
			
			// nothing to deliver, but we succeeded in reaching the target activity - move the current activity
			pp.getTerminateDescendentAttemptsProcess().run(activityTree, commonAncestor);
			pp.getEndAttemptProcess().run(activityTree, commonAncestor);
			activityTree.setCurrentActivity(target);
			return new ResultSequencingRequestCommand(45);
			
		}
		
		ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
		res.setDeliveryRequest(flowres.getIdentifiedActivity());
		return res;
	}

	private ResultSequencingRequestCommand doCase2(ActivityTree activityTree) {
	
		// we are attempting to walk toward the target activity. Once we reach the target activity, we don't need to test it.
		LearningActivity[] path = activityTree.makeActivityPathExclusiveLastSameDepth(activityTree.getCurrentActivity(), activityTree.getTargetActivity());
		if (path.length == 0) {
			return new ResultSequencingRequestCommand(41);
		}
		
		TraversalDirection traverse;
		if (activityTree.getTargetActivity().isForwardFrom(activityTree.getCurrentActivity()))
			traverse = TraversalDirection.forward;
		else
			traverse = TraversalDirection.backward;
			
		ChoiceActivityTraversalSubprocess choiceSubproc = ProcessProvider.getInstance().getChoiceActivityTraversalSubprocess();
		for (LearningActivity activity:path) {
			ChoiceActivityTraversalSubprocessResult choiceRes = choiceSubproc.run(activity, traverse);
			if (choiceRes.isReachable() == false)
				return new ResultSequencingRequestCommand(choiceRes.getException());
		}
		
		return null;
	}

	private ResultSequencingRequestCommand doCase3(ActivityTree activityTree, LearningActivity commonAncestor) {
		LearningActivity[] path = activityTree.makeActivityPathExclusiveLast(commonAncestor, activityTree.getTargetActivity());
		if (path.length == 0) {
			return new ResultSequencingRequestCommand(41);
		}
		
		ChoiceActivityTraversalSubprocess choiceSubproc = ProcessProvider.getInstance().getChoiceActivityTraversalSubprocess();
		for (LearningActivity activity:path) {
			ChoiceActivityTraversalSubprocessResult choiceRes = choiceSubproc.run(activity, TraversalDirection.forward);
			if (choiceRes.isReachable() == false)
				return new ResultSequencingRequestCommand(choiceRes.getException());
			
			if (activity.isActive() == false && (activity != commonAncestor && activity.isPreventActivation() == true)) {
				// if the activity being considered is not already active, make sure we are allowed to activate it
				return new ResultSequencingRequestCommand(42);
			}
		}
		
		return null;
	}


	private ResultSequencingRequestCommand doCase4(ActivityTree activityTree) {
		
		LearningActivity[] path = activityTree.makeActivityPath(activityTree.getCurrentActivity(), activityTree.getTargetActivity());
		if (path.length == 0) {
			return new ResultSequencingRequestCommand(41);
		}		
		
		LearningActivity last = path[path.length-1];
		for (LearningActivity activity:path) {
			if (activity != last) {
				
				// make sure an activity that should not exit will exit if the target is delivered
				if (activity.isSequencingControlChoiceExit() == false) {
					return new ResultSequencingRequestCommand(43);
				}
			}
		}
		
		return null;
		
	}

	private ResultSequencingRequestCommand doCase5(ActivityTree activityTree, LearningActivity commonAncestor) {
		
		LearningActivity[] path = activityTree.makeActivityPathExclusiveFirst(commonAncestor, activityTree.getCurrentActivity());
		if (path.length == 0) {
			return new ResultSequencingRequestCommand(41);
		}		
		
		LearningActivity constrainedActivity = null;
		for (LearningActivity activity:path) {
			if (activity.isSequencingControlChoiceExit() == false) {
				// make sure an activity that should not exit will exit if the target is delivered 
				return new ResultSequencingRequestCommand(43);
			}
			
			if (constrainedActivity == null) {
				if (activity.isConstrainedChoice() == true)
					constrainedActivity = activity;
			}
		}
		
		if (constrainedActivity != null) {
			TraversalDirection traverse;
			if (activityTree.getTargetActivity().isForwardFrom(constrainedActivity))
				traverse = TraversalDirection.forward;
			else
				traverse = TraversalDirection.backward;
			
			LearningActivity activityChoice = ProcessProvider.getInstance().getChoiceFlowSubprocess().run(activityTree, constrainedActivity, traverse);
			if (activityTree.getTargetActivity().isDescendantFrom(activityChoice) == false && 
					activityTree.getTargetActivity() != activityChoice &&
					activityTree.getTargetActivity() != constrainedActivity) {
				// make sure the target activity is within the set of 'flow' constrained choices 
				return new ResultSequencingRequestCommand(44);
			}
		}
		
		path = activityTree.makeActivityPathExclusiveLast(commonAncestor, activityTree.getTargetActivity());
		if (path.length == 0) {
			return new ResultSequencingRequestCommand(41);
		}		
		
		if (activityTree.getTargetActivity().isForwardFrom(activityTree.getCurrentActivity())) {
			// walk toward the target activity
			
			ChoiceActivityTraversalSubprocess subprocess = ProcessProvider.getInstance().getChoiceActivityTraversalSubprocess();
			for (LearningActivity learningActivity:path) {
				ChoiceActivityTraversalSubprocessResult res = subprocess.run(learningActivity, TraversalDirection.forward);
				if (res.isReachable() == false) {
					return new ResultSequencingRequestCommand(res.getException());
				}
				
				
				if (learningActivity.isActive() == false && 
						(learningActivity != commonAncestor && learningActivity.isPreventActivation() == true)) {
					// if the activity being considered is not already active, make sure we are allowed to activate it
					return new ResultSequencingRequestCommand(42);
				}
			}
		} else {
			
			for (LearningActivity learningActivity:path) {
				if (learningActivity.isActive() == false && 
						(learningActivity != commonAncestor && learningActivity.isPreventActivation() == true))
					// if the activity being considered is not already active, make sure we are allowed to activate it
					return new ResultSequencingRequestCommand(42);
			}
		}
		
		return null;
		
	}
}
