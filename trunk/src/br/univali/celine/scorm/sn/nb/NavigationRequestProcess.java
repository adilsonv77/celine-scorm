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
package br.univali.celine.scorm.sn.nb;

import java.util.HashMap;

import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.NavigationRequest;
import br.univali.celine.scorm.sn.model.NavigationRequestType;

public class NavigationRequestProcess {

	protected HashMap<NavigationRequestType, NavigationRequestCommand> commands = new HashMap<NavigationRequestType, NavigationRequestCommand>();
	
	public NavigationRequestProcess() {
		commands.put(NavigationRequestType.Start, new StartNavigationRequestCommand());
		commands.put(NavigationRequestType.ResumeAll, new ResumeAllNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Continue, new ContinueNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Previous, new PreviousNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Forward, new UnsupportedNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Backward, new UnsupportedNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Choice, new ChoiceNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Exit, new ExitNavigationRequestCommand()); 
		commands.put(NavigationRequestType.ExitAll, new ExitAllNavigationRequestCommand()); 
		commands.put(NavigationRequestType.Abandon, new AbandonNavigationRequestCommand()); 
		commands.put(NavigationRequestType.AbandonAll, new AbandonAllNavigationRequestCommand()); 
		commands.put(NavigationRequestType.SuspendAll, new SuspendAllNavigationRequestCommand());
	}
	
	public NavigationRequestResult run(ActivityTree activityTree, NavigationRequest navigationRequest) {
		NavigationRequestCommand comm = commands.get(navigationRequest.getNavigationRequestCommand());
		
		if (comm == null) {
			return NavigationRequestResult.buildNotValid(13); // NB.2.1-13
		} else {
			return comm.run(activityTree, navigationRequest);
		}
	}

}
