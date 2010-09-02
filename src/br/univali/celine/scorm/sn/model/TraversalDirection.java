package br.univali.celine.scorm.sn.model;

public enum TraversalDirection {
	
	none, backward, forward;

	public boolean isDefined() {
		return this != none;
	}
	
}
