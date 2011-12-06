package br.univali.celine.scorm2004_4th.model.cam;

import org.apache.commons.digester.Digester;

import br.univali.celine.scorm.model.cam.CompletionThreshold;
import br.univali.celine.scorm.versions.Build20044thEdition;
import br.univali.celine.scorm.versions.BuildVersion;

public class ContentPackageReader20044th extends br.univali.celine.scorm.model.cam.ContentPackageReader20043rd{

	private BuildVersion version;

	public ContentPackageReader20044th() {
		this.itemClass = Item20044th.class;
		this.organizationClass = Organization20044th.class;
	}
	
   	protected void addCompletionThreshold(Digester d) {
        
        d.addObjectCreate("*/item/adlcp:completionThreshold", CompletionThreshold.class); 
        d.addSetNext("*/item/adlcp:completionThreshold", "setCompletionThreshold");
        d.addSetProperties("*/item/adlcp:completionThreshold");
        
        d.addObjectCreate("*/item/completionThreshold", CompletionThreshold.class); 
        d.addSetNext("*/item/completionThreshold", "setCompletionThreshold");
        d.addSetProperties("*/item/completionThreshold");
	}


   	@Override
   	protected void addRootOrganization(Digester d) {
   		super.addRootOrganization(d);

   		d.addSetProperties("manifest/organizations/organization", "adlcp:sharedDataGlobalToSystem", "sharedDataGlobalToSystem");
   		
   	}
   	
   	@Override
   	protected void beforeOrganizationItemSetNext(Digester d) {
  		super.beforeOrganizationItemSetNext(d);

  		d.addObjectCreate("*/item/adlcp:data/adlcp:map", AdlcpMap.class);
        d.addSetNext("*/item/adlcp:data/adlcp:map", "addAdlcpMap");
		d.addSetProperties("*/item/adlcp:data/adlcp:map");
  		
   	}
   	
	@Override
	public BuildVersion buildVersion() throws Exception {
		if (this.version == null)
			this.version = new Build20044thEdition(); 
		return this.version;
	}
   	
}
