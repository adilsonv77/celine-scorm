package br.univali.celine.scorm2004_4th.sn.nb;

import br.univali.celine.scorm.sn.model.NavigationRequestType;
import br.univali.celine.scorm.sn.nb.NavigationRequestProcess;

public class NavigationRequestProcess20044th extends NavigationRequestProcess {

	public NavigationRequestProcess20044th() {
		super();

		commands.put(NavigationRequestType.Jump, new JumpNavigationRequestCommand());

	}
	
}
