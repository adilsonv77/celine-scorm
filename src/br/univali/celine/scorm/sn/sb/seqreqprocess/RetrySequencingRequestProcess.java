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
