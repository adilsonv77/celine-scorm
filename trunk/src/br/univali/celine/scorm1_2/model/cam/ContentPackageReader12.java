package br.univali.celine.scorm1_2.model.cam;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.digester3.Digester;

import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackageReader20043rd;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Organization;
import br.univali.celine.scorm.versions.Build1_2;
import br.univali.celine.scorm.versions.BuildVersion;

public class ContentPackageReader12 extends ContentPackageReader20043rd {

	private Build1_2 version;

	@Override
	public ContentPackage read(InputStream stream) throws Exception {
		ContentPackage cp = super.read(stream);
		
		cp.getOrganizations().initIteration();
		Organization co = cp.getOrganizations().nextContentOrganization();
		do {
			iterar(co.getChildren());
			try {
				co = cp.getOrganizations().nextContentOrganization();
			} catch (Exception e) {
				co = null;
			}
		} while (co != null);
		
		return cp;
	}

	@Override
	protected void addResource(Digester d) {
		super.addResource(d);
        d.addSetProperties("manifest/resources/resource", "scormtype", "scormType");
	}
	
	private void iterar(Iterator<Item> c) {
		while (c.hasNext()) {
			Item it = c.next();
			it.getImsssSequencing().getControlMode().setFlow(true);
			iterar(it.getChildren());
		}

		
	}
	
	protected void beforeOrganizationItemSetNext(Digester d) {
        d.addCallMethod("*/item/adlcp:datafromlms", "setDataFromLMS", 0);
        d.addCallMethod("*/item/datafromlms", "setDataFromLMS", 0);       
	}
	
	@Override
	public BuildVersion buildVersion() throws Exception {
		if (this.version == null)
			this.version = Build1_2.create(); 
		return this.version;
	}
}
