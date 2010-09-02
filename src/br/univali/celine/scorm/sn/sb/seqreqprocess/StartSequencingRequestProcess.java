package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.TraversalDirection;
import br.univali.celine.scorm.sn.sb.FlowSubprocess;
import br.univali.celine.scorm.sn.sb.FlowSubprocessResult;

/**
 * Start Sequencing Request Process [SB.2.5]
 * 
 * Passo (1.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */

public class StartSequencingRequestProcess extends InitialSequencingRequest implements SequencingRequestCommand {

	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		
		ResultSequencingRequestCommand res;
		// make sure the sequencing session has not already begun
		if (activityTree.getCurrentActivity() != null) {
			res = new ResultSequencingRequestCommand(30);
		} else {
			// before starting, make sure the activity tree contains more than one activity
			if (activityTree.getRootActivity().isLeaf()) {
				// only one activity, it must be a leaf
				res = new ResultSequencingRequestCommand();
				res.setDeliveryRequest(activityTree.getRootActivity());
			} else {
				
				// minha decisao: o SCORM nao define quando executar esse processo
				percorrerArvore(activityTree.getRootActivity());
				
				// attempt to flow into the activity tree
				FlowSubprocess flow = new FlowSubprocess();
				FlowSubprocessResult resultado =  flow.run(activityTree, activityTree.getRootActivity(), TraversalDirection.forward, true); 
				
				if (resultado.isDeliverable() == false) {
					res = new ResultSequencingRequestCommand(resultado.getException());
					res.setEndSequencingSession(resultado.isEndSequencingSession());
				} else {
					res = new ResultSequencingRequestCommand();
					res.setDeliveryRequest(resultado.getIdentifiedActivity());
				}
			}
		}
		return res;
	}

}
