package br.univali.celine.scorm.model.imsss;

import java.util.LinkedList;
import java.util.List;

public class Objectives {

	private List<Objective> objectives = new LinkedList<Objective>(); 
	
	public void addObjective(Objective obj) {
		objectives.add(obj);
	}
	
	public List<Objective> getObjectives() { return objectives; }
	
	private Objective primaryObjective;
	
	public Objective getPrimaryObjective() {
		if (primaryObjective == null) {
			if (objectives.size() == 0)
				primaryObjective = new Objective();
			else
				primaryObjective = objectives.get(0);
			primaryObjective.setPrimary(true);
		}
			
		return primaryObjective;
	}

	public void setPrimaryObjective(Objective primaryObjective) {
		this.primaryObjective = primaryObjective;
		primaryObjective.setPrimary(true);
	}

	public String toString() {
		String ret = "<imsss:objectives>\n";
		if (primaryObjective != null) {
			ret += primaryObjective; 
		}
		for (Objective obj:objectives) {
			ret += obj;
		}
		return ret + "</imsss:objectives>\n";
		
	}

	public void assign(Objectives objectives2) {
		// TODO Auto-generated method stub
		
	}
}
