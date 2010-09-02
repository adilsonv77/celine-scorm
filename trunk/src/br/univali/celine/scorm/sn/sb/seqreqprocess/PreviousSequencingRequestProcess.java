package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.TraversalDirection;
import br.univali.celine.scorm.sn.sb.FlowSubprocessResult;

/**
 * Previous Sequencing Request Process [SB.2.8]
 * 
 * Passo (6.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */
public class PreviousSequencingRequestProcess implements
		SequencingRequestCommand {

	
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		
		if (activityTree.getCurrentActivity() == null) {
			// make sure the sequencing session has already begun
			return new ResultSequencingRequestCommand(35);
		}
		
		if (activityTree.getCurrentActivity() != activityTree.getRootActivity()) {
			// confirm a 'flow' traversal is allowed from the activity
			
			if (activityTree.getCurrentActivity().getParent().isSequencingControlFlow() == false)
				return new ResultSequencingRequestCommand(36);
		}
			
		// flow in a backward direction to the next allowed activity
		FlowSubprocessResult flowres = ProcessProvider.getInstance().getFlowSubprocess().run(activityTree, activityTree.getCurrentActivity(), TraversalDirection.backward, false);
		if (flowres.isDeliverable() == false) {
			return new ResultSequencingRequestCommand(flowres.getException());
		}
		
		ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
		res.setDeliveryRequest(flowres.getIdentifiedActivity());
		
		return res;
	}

}
