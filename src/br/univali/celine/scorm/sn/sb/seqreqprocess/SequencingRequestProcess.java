package br.univali.celine.scorm.sn.sb.seqreqprocess;

import java.util.HashMap;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.SequencingRequest;

/**
 * 
 * Sequencing Request Process [SB.2.12]
 * 
 * @author Adilson Vahldick
 *
 */
public class SequencingRequestProcess {

	protected HashMap<SequencingRequest, SequencingRequestCommand> commands = new HashMap<SequencingRequest, SequencingRequestCommand>(); 
	
	public SequencingRequestProcess() {
		commands.put(SequencingRequest.START, new StartSequencingRequestProcess()); // passo 1.
		commands.put(SequencingRequest.RESUMEALL, new ResumeAllSequencingRequestProcess()); // passo 2.
		commands.put(SequencingRequest.EXIT, new ExitSequencingRequestProcess()); // passo 3.
		commands.put(SequencingRequest.RETRY, new RetrySequencingRequestProcess()); // passo 4.
		commands.put(SequencingRequest.CONTINUE, new ContinueSequencingRequestProcess()); // passo 5.
		commands.put(SequencingRequest.PREVIOUS, new PreviousSequencingRequestProcess()); // passo 6.
		commands.put(SequencingRequest.CHOICE, new ChoiceSequencingRequestProcess()); // passo 7.
	}
	
	public ResultSequencingRequestCommand run(ActivityTree activityTree, SequencingRequest sequencingRequest) {
		
		SequencingRequestCommand comm = commands.get(sequencingRequest);
		return comm.run(activityTree);
		
	}

}
