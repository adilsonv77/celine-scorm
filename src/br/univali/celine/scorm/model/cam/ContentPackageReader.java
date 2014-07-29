package br.univali.celine.scorm.model.cam;

import br.univali.celine.scorm.versions.BuildVersion;


public interface ContentPackageReader {

	ContentPackage read(String fileName) throws Exception;

	ContentPackage read(java.io.InputStream fileByte) throws Exception;

	BuildVersion buildVersion() throws Exception;
}
