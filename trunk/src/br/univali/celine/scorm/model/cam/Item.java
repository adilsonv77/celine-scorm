package br.univali.celine.scorm.model.cam;

import br.univali.celine.scorm.model.adlnav.Presentation;


public interface Item extends SuperItem {

	void setIdentifierref(String identifier);

	String getTimeLimitAction();

	String getDataFromLMS();

	String getIdentifierref();

	String getParameters();
	
	Presentation getAdlNavPresentation();
	
}
