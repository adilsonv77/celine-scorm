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
