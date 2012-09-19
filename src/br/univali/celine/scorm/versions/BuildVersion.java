package br.univali.celine.scorm.versions;

import br.univali.celine.scorm.model.cam.AbstractItem;

public interface BuildVersion {

	int getVersion();
	AbstractItem buildItem();
	String[] getXSDFileNames();

}
