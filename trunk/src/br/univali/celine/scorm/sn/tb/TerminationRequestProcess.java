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
package br.univali.celine.scorm.sn.tb;

import java.util.HashMap;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.TerminationRequest;

// TB.2.3
public class TerminationRequestProcess {

	private HashMap<TerminationRequest, TerminationRequestCommand> comandos = new HashMap<TerminationRequest, TerminationRequestCommand>(); 
	
	public TerminationRequestProcess() {
		
		ExitAllTerminationRequestCommand exitAllComm = new ExitAllTerminationRequestCommand();
		comandos.put(TerminationRequest.EXIT, new ExitTerminationRequestCommand(exitAllComm));
		comandos.put(TerminationRequest.EXITALL, exitAllComm);
		comandos.put(TerminationRequest.SUSPENDALL, new SuspendAllTerminationRequestCommand());
		comandos.put(TerminationRequest.ABANDON, new AbandonTerminationRequestCommand());
		comandos.put(TerminationRequest.ABANDONALL, new AbandonAllTerminationRequestCommand());
		
	}
	
	public TerminationRequestResult run(ActivityTree activityTree, TerminationRequest terminationRequest) {

		// if the sequencing session has not begun, there is nothing to terminate
		if (activityTree.getCurrentActivity() == null) {
			return TerminationRequestResult.buildNotValid(14); // TB.2.3-1
		}
		
		// if the current activity has already been terminated, there is nothing to terminate
		if ((terminationRequest == TerminationRequest.EXIT || terminationRequest == TerminationRequest.ABANDON) &&
				(activityTree.getCurrentActivity().isActive() == false)) {
			return TerminationRequestResult.buildNotValid(15); // TB.2.3-2
			
		}
		
		TerminationRequestCommand comm = comandos.get(terminationRequest);
		
		if (comm == null) {
			return TerminationRequestResult.buildNotValid(20); // TB.2.3-7
		} else {
			return comm.run(activityTree);
		}
	}

}
