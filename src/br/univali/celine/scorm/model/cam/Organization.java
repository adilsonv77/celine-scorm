package br.univali.celine.scorm.model.cam;

public interface Organization extends SuperItem {

	String getStructure();

	boolean isObjectivesGlobalToSystem();

	Item toItem();

}
