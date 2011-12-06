import br.univali.celine.scorm.model.cam.CompletionThreshold;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackageReader;
import br.univali.celine.scorm.model.cam.ContentPackageReaderFactory;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Item20043rd;
import br.univali.celine.scorm.model.cam.Organization;
import br.univali.celine.scorm2004_4th.model.cam.AdlcpMap;
import br.univali.celine.scorm2004_4th.model.cam.Item20044th;
import br.univali.celine.scorm2004_4th.model.cam.Organization20044th;

public class TestarContent2004_4 {

	public static void main(String[] args) throws Exception {

		ContentPackageReader cpr; // = ContentPackageReaderFactory.getContentPackageReader("testes/imsmanifest.xml");

		ContentPackage cp; // = cpr.ler("testes/imsmanifest.xml");

		Organization org;
		Item item;
/*		org = cp.getOrganizations().getDefaultOrganization();
		
		System.out.println(org.getStructure());
		System.out.println(org.isObjectivesGlobalToSystem());
		System.out.println(((Organization20044th)org).isSharedDataGlobalToSystem());
		
		item = org.getChildren().next();

		imprimir20044th(item);
*/
		String s = "C:/adilson/cursos/LMSTestCourse01/imsmanifest.xml";
		cpr = ContentPackageReaderFactory.getContentPackageReader(s);
		cp = cpr.ler(s);
		org = cp.getOrganizations().getDefaultOrganization();
		item = org.getChildren().next();
		imprimir20043rd(item);
		

	}

	private static void imprimir20043rd(Item item) {
		Item20043rd i3rd = (Item20043rd) item;
		System.out.println(i3rd.getCompletionThreshold());
		System.out.println(i3rd.getImsssSequencing().getControlMode().isFlow());	
		
	}

	private static void imprimir20044th(Item item) {
		Item20044th i4th = (Item20044th) item;
		CompletionThreshold cts = i4th.getObjectCompletionThreshold();
		System.out.println(item.getTitle());
		System.out.println(i4th.getTimeLimitAction());
		System.out.println(cts.getMinProgressMeasure());
		System.out.println(cts.getProgressWeight());
		System.out.println(cts.isCompletedByMeasure());
		
		System.out.println(i4th.getAdlcpData().size());
		for (AdlcpMap map:i4th.getAdlcpData()) {
			System.out.println(map.getTargetID() + " " + map.isReadSharedData() + " " + map.isWriteSharedData());
		}

	}

}
