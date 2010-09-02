package br.univali.celine.scorm.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.ActivityTree;

/**
 * Exit Sequencing Request Process [SB.2.11]
 * 
 * Passo (3.) do Sequencing Request Process [SB.2.12] 
 * 
 * @author Adilson Vahldick
 *
 */
public class ExitSequencingRequestProcess implements SequencingRequestCommand {

	
	public ResultSequencingRequestCommand run(ActivityTree activityTree) {
		
		if (activityTree.getCurrentActivity() == null) {
			// make sure the sequencing session has already begun
			return new ResultSequencingRequestCommand(49);
		}
		
		if (activityTree.getCurrentActivity().isActive()) {
			// make sure the current activity has already been terminated
			return new ResultSequencingRequestCommand(50);
		}
		
		if (activityTree.getCurrentActivity() == activityTree.getRootActivity()) {
			// The sequencing session has ended, return control to the LTS
			ResultSequencingRequestCommand res = new ResultSequencingRequestCommand();
			res.setEndSequencingSession(true);
			return res;
		}
		
		
		return new ResultSequencingRequestCommand();
	}

}
