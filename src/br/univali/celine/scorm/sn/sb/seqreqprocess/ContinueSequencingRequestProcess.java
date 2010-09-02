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
