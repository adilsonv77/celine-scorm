package br.univali.celine.scorm2004_4th.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.sb.seqreqprocess.ResultSequencingRequestCommand;
import br.univali.celine.scorm.sn.sb.seqreqprocess.SequencingRequestCommand;

public class JumpSequencingRequestProcess implements SequencingRequestCommand {

	@Override
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		
		
		if (activityTree.getCurrentActivity() == null)
			// Make sure the sequencing session has not already begun.
			return new ResultSequencingRequestCommand(56); // SB.2.13-1
		else {
			ResultSequencingRequestCommand rsr;
			rsr = new ResultSequencingRequestCommand();
			rsr.setDeliveryRequest(activityTree.getTargetActivity());
			
			return rsr;
		}

	}

}
