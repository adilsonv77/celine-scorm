package br.univali.celine.scorm.model.adlnav;

public class Presentation {

	private NavigationInterface navigationInterface;

	public NavigationInterface getNavigationInterface() {
		return navigationInterface;
	}

	public void setNavigationInterface(NavigationInterface navigationInterface) {
		this.navigationInterface = navigationInterface;
	}
	
	@Override
	public String toString() {
		if (navigationInterface != null) {
			return "<adlnav:presentation>\n"+navigationInterface+"</adlnav:presentation>\n";
		}
		return "";
	}
}
