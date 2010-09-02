package br.univali.celine.scorm.sn.model;

public class GlobalObjective extends Objective{

	private String id;

	public GlobalObjective(String id) {
		this.id = id;
	}

	public String getObjectiveID() {
		return id;
	}
	
	@Override
	public String toString() {
		return id;
	}

}
