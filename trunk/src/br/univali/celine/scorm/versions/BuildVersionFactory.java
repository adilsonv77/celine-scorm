package br.univali.celine.scorm.versions;


public class BuildVersionFactory {

	public static BuildVersion create(String version) {
		if (version.equals("1.2"))
			return Build1_2.create();
		else
			if (version.equals("CAM 1.3") || version.equals("2004 3rd Edition")) {
				return Build20043rdEdition.create();
			} else { // SCORM 2004 4th Edition
				return Build20044thEdition.create();
		}
	}
	
}
