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

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.TraversalDirection;
import br.univali.celine.scorm.sn.sb.FlowSubprocessResult;

/**
 * Continue Sequencing Request Process [SB.2.7]
 * 
 * Passo (5.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */
public class ContinueSequencingRequestProcess implements
		SequencingRequestCommand {

	
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		
		if (activityTree.getCurrentActivity() == null) {
			// make sure the sequencing session has already begun
			return new ResultSequencingRequestCommand(33);
		}
		
		if (activityTree.getCurrentActivity() != activityTree.getRootActivity()) {
			// confirm a 'flow' traversal is allowed from the activity
			
			if (activityTree.getCurrentActivity().getParent().isSequencingControlFlow() == false)
				return new ResultSequencingRequestCommand(34);
		}
			
		// flow in a forward direction to the next allowed activity
		FlowSubprocessResult flowres = ProcessProvider.getInstance().getFlowSubprocess().run(activityTree, activityTree.getCurrentActivity(), TraversalDirection.forward, false);
		if (flowres.isDeliverable() == false) {
			ResultSequencingRequestCommand res = new ResultSequencingRequestCommand(flowres.getException());
			res.setEndSequencingSession(flowres.isEndSequencingSession());
			return res; 
		}
		
		ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
		res.setDeliveryRequest(flowres.getIdentifiedActivity());
		
		return res;
	}

}
