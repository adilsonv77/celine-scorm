package br.univali.celine.scorm.model.cam;

import java.util.HashMap;

public class Resources {

	private HashMap<String, Resource> resources = new HashMap<String, Resource>();
	
	public void addResource(Resource resource) {
		resources.put(resource.getIdentifier(), resource);
	}
	
	public Resource getResource(String identifier) {
		return resources.get(identifier);
	}
	
	public String toString() {
		if (resources.size() > 0) {
			String ret = "<resources>\n";
			for (Resource r:resources.values()) {
				ret += r + "\n";
			}
			ret += "</resources>\n";
			return ret;
		}
		return "";
	}
}
