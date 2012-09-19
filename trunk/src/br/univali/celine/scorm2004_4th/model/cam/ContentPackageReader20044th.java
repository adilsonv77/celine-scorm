package br.univali.celine.scorm2004_4th.model.cam;

import org.apache.commons.digester3.Digester;

import br.univali.celine.scorm.model.cam.CompletionThreshold;
import br.univali.celine.scorm.versions.BuildVersion;
import br.univali.celine.scorm2004_4th.versions.Build20044thEdition;

public class ContentPackageReader20044th extends br.univali.celine.scorm.model.cam.ContentPackageReader20043rd{

	public ContentPackageReader20044th() {
		this.itemClass = Item20044th.class;
		this.organizationClass = Organization20044th.class;
	}
	
   	protected void addCompletionThreshold(Digester d) {
        
        d.addObjectCreate("*/item/completionThreshold", CompletionThreshold.class); 
        d.addSetNext("*/item/completionThreshold", "setCompletionThreshold");
        d.addSetProperties("*/item/completionThreshold");
        
	}


   	@Override
   	protected void addRootOrganization(Digester d) {
   		super.addRootOrganization(d);

   		d.addSetProperties("manifest/organizations/organization", "sharedDataGlobalToSystem", "sharedDataGlobalToSystem");
   		
   	}
   	
   	@Override
   	protected void beforeOrganizationItemSetNext(Digester d) {
  		super.beforeOrganizationItemSetNext(d);

  		d.addObjectCreate("*/item/data/map", AdlcpMap.class);
        d.addSetNext("*/item/data/map", "addAdlcpMap");
		d.addSetProperties("*/item/data/map");
  		
   	}
   	
	@Override
	public BuildVersion buildVersion() throws Exception {
		if (this.version == null)
			this.version = new Build20044thEdition(); 
		return this.version;
	}
   	
}
