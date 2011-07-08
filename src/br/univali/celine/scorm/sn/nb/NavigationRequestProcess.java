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
