package br.univali.celine.scorm2004_4th.sn.sb.seqreqprocess;

import br.univali.celine.scorm.sn.model.SequencingRequest;
import br.univali.celine.scorm.sn.sb.seqreqprocess.SequencingRequestProcess;

/**
 * 
 * Sequencing Request Process [SB.2.12]
 * 
 * @author Adilson Vahldick
 *
 */
public class SequencingRequestProcess20044th extends SequencingRequestProcess{

	public SequencingRequestProcess20044th() {
		commands.put(SequencingRequest.JUMP, new JumpSequencingRequestProcess());
	}
	
}
