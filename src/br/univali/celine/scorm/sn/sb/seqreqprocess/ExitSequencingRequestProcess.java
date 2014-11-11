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
