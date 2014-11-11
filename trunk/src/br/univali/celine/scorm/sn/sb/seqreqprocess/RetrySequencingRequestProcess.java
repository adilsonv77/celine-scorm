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
 * Retry Sequencing Request Process [SB.2.10]
 * 
 * Passo (4.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */
public class RetrySequencingRequestProcess implements SequencingRequestCommand {

	
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		if (activityTree.getCurrentActivity() == null) {
			// make sure tge sequencing session has already begun
			return new ResultSequencingRequestCommand(46);
		}
		
		if (activityTree.getCurrentActivity().isActive() == true || activityTree.getCurrentActivity().isSuspended() == true) {
			// Cannot retry an activity that is still active or suspended
			return new ResultSequencingRequestCommand(47);
		}
		
		if (activityTree.getCurrentActivity().isLeaf() == false) {
			
			FlowSubprocessResult flowres = ProcessProvider.getInstance().getFlowSubprocess().run(activityTree, activityTree.getCurrentActivity(), TraversalDirection.forward, true);
			if (flowres.isDeliverable() == false) {
				return new ResultSequencingRequestCommand(48);
			} else {
				ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
				res.setDeliveryRequest(flowres.getIdentifiedActivity());
				return res;
			}
			
		}
		ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
		res.setDeliveryRequest(activityTree.getCurrentActivity());
		return res;
	}

}
