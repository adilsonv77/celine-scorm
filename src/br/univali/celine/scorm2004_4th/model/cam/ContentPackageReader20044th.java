/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.celine.scorm2004_4th.model.cam;

import org.apache.commons.digester3.Digester;

import br.univali.celine.scorm.model.cam.CompletionThreshold;
import br.univali.celine.scorm.versions.Build20044thEdition;
import br.univali.celine.scorm.versions.BuildVersion;

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
			this.version = Build20044thEdition.create(); 
		return this.version;
	}
   	
}
