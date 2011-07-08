package br.univali.celine.scorm.model.cam;


public interface ContentPackageReader {

	ContentPackage ler(String fileName) throws Exception;

	ContentPackage ler(java.io.InputStream fileByte) throws Exception;

}
