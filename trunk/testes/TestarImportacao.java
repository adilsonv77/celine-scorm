import java.io.File;

import br.univali.celine.lms.utils.zip.Zip;


public class TestarImportacao {

	public static void main(String[] args) throws Exception {

		String zipDir = "C:\\adilson\\cursos\\LMSTestCourse01\\LMSTestCourse01.zip";
		String dirS = "C:\\adilson\\cursos\\LMSTestCourse01";
		
		// String zipDir = "C:\\adilson\\My Dropbox\\Public\\celine\\SCORM.2004.4ED.TS.v1.1.1.LMS.Packages\\LMSTestPackage_API.zip";
		// String dirS = "C:\\adilson\\cursos\\LMSTestCourse01";
		
		File zipFile = new File(zipDir);
		File dir = new File(dirS);
		
		Zip zip = new Zip();	
		zip.unzip(zipFile, dir);
	}

}
