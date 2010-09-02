package br.univali.celine.scorm.sn.model;

public class DynamicObjective extends ProxyObjective {

	// esse objetivo é criado durante a interacao do curso 
	public DynamicObjective(String objectiveID) {

		super(new br.univali.celine.scorm.model.imsss.Objective(objectiveID));
		
	}
	
}
