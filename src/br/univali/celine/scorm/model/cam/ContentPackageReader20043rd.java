package br.univali.celine.scorm.model.cam;

import java.io.FileInputStream;
import java.net.URL;

import org.apache.commons.digester3.Digester;

import br.univali.celine.scorm.model.adlnav.NavigationInterface;
import br.univali.celine.scorm.model.adlnav.Presentation;
import br.univali.celine.scorm.model.imsss.ConstrainedChoiceConsiderations;
import br.univali.celine.scorm.model.imsss.ControlMode;
import br.univali.celine.scorm.model.imsss.DeliveryControls;
import br.univali.celine.scorm.model.imsss.LimitConditions;
import br.univali.celine.scorm.model.imsss.MapInfo;
import br.univali.celine.scorm.model.imsss.Objective;
import br.univali.celine.scorm.model.imsss.Objectives;
import br.univali.celine.scorm.model.imsss.RandomizationControls;
import br.univali.celine.scorm.model.imsss.RollupCondition;
import br.univali.celine.scorm.model.imsss.RollupConsiderations;
import br.univali.celine.scorm.model.imsss.RollupRule;
import br.univali.celine.scorm.model.imsss.RollupRules;
import br.univali.celine.scorm.model.imsss.RuleCondition;
import br.univali.celine.scorm.model.imsss.Sequencing;
import br.univali.celine.scorm.model.imsss.SequencingRule;
import br.univali.celine.scorm.versions.Build20043rdEdition;
import br.univali.celine.scorm.versions.BuildVersion;

public class ContentPackageReader20043rd implements ContentPackageReader {

	protected Class<?> itemClass = Item20043rd.class;
	protected Class<?> organizationClass = Organization20043rd.class;
	protected BuildVersion version;
	
	public ContentPackage read(String nomeArquivo) throws Exception {
		if (nomeArquivo.startsWith("file:")) {
			nomeArquivo = (new URL(nomeArquivo)).getFile();
		}
		return ler(new java.io.File(nomeArquivo));
	}
	
	public ContentPackage ler(java.io.File arquivo) throws Exception {
		return read(new FileInputStream(arquivo));
	}
	
	public ContentPackage read(java.io.InputStream stream) throws Exception {
        // Create a Digester instance
        Digester d = new Digester();
        d.setNamespaceAware(true); // desconsidera todos os namespaces !!!
        
        // Prime the digester stack with an object for rules to
        // operate on. Note that it is quite common for "this"
        // to be the object pushed.
        ContentPackage manifest = new ContentPackage(this);
        d.push(manifest);
        // Add rules to the digester that will be triggered while
        // parsing occurs.
        addRules(d);
        
        // Process the input file.
        d.parse(stream);
        
        manifest.finalization();
        
        return manifest;
	}
	
	
	protected void addRules(Digester d) {
		
		// ver capítulos 3.4, 5.1 e 5.2 do CAM Book
		
		d.addSetProperties("manifest");
		
        addMetadata(d);
        addOrganizations(d);
        addResources(d);
        
	}

	protected void addMetadata(Digester d) {
		d.addObjectCreate("*/metadata", Metadata.class);
		
		d.addCallMethod("*/metadata/adlcp:location", "setLocation", 0);
		d.addCallMethod("*/metadata/schema", "setSchema", 0);
		d.addCallMethod("*/metadata/schemaversion", "setSchemaversion", 0);

		d.addSetNext("*/metadata", "setMetadata");
//        d.addSetNestedProperties("*/metadata");
	}
	
	protected void addOrganizations(Digester d) {
        d.addObjectCreate("manifest/organizations", Organizations.class);
        d.addSetProperties("manifest/organizations", "default", "defaultOrg");
        d.addSetNext("manifest/organizations", "setOrganizations");
        
        addOrganization(d);
        

	}

	protected void addOrganization(Digester d) {

		addRootOrganization(d);
		
		addOrganizationTitle(d);
		addOrganizationItem(d);
		addImssSequencing(d, "manifest/organizations/organization");
	}

	protected void addRootOrganization(Digester d) {
		d.addObjectCreate("manifest/organizations/organization", organizationClass);
        d.addSetNext("manifest/organizations/organization", "addOrganization");
		d.addSetProperties("manifest/organizations/organization");
		d.addSetProperties("manifest/organizations/organization", "objectivesGlobalToSystem", "objectivesGlobalToSystem");
	}

	protected void addOrganizationItem(Digester d) {
		
		d.addObjectCreate("*/item", itemClass);

		addPresentation(d);
		
		addNavigationInterface(d);
        
        addImssSequencing(d, "*/item");

        d.addCallMethod("*/item/title", "setTitle", 0);
        
        d.addCallMethod("*/item/adlcp:dataFromLMS", "setDataFromLMS", 0);
        d.addCallMethod("*/item/dataFromLMS", "setDataFromLMS", 0);       
        
        d.addCallMethod("*/item/adlcp:timeLimitAction", "setTimeLimitAction", 0);
        d.addCallMethod("*/item/timeLimitAction", "setTimeLimitAction", 0);
        
        d.addSetProperties("*/item");
        beforeOrganizationItemSetNext(d);
        d.addSetNext("*/item", "addItem");
  
        addCompletionThreshold(d);
	}

	protected void beforeOrganizationItemSetNext(Digester d) {
	}

	private void addNavigationInterface(Digester d) {
        d.addObjectCreate("*/item/presentation/navigationInterface", NavigationInterface.class);
        d.addSetNext("*/item/presentation/adlnav:navigationInterface", "setNavigationInterface");
        d.addCallMethod("*/item/presentation/navigationInterface/hideLMSUI", "addHideLMSUI", 0);
	}

	protected void addPresentation(Digester d) {
		d.addObjectCreate("*/item/presentation", Presentation.class);
        d.addSetNext("*/item/presentation", "setAdlNavPresentation");

	}

	protected void addCompletionThreshold(Digester d) {
      d.addCallMethod("*/item/completionThreshold", "setCompletionThreshold", 0);
	}
	
	protected void addOrganizationTitle(Digester d) {
		d.addCallMethod("manifest/organizations/organization/title", "setTitle", 0);
	}

	protected void addImssSequencing(Digester d, String tagParent) {
		d.addObjectCreate(tagParent+"/sequencing", Sequencing.class);
        d.addSetNext(tagParent+"/sequencing", "setImsssSequencing");

        addControlMode(d, tagParent);
        addSequencingRules(d, tagParent);
        addObjectives(d, tagParent);
        addDeliveryControls(d, tagParent);
        addLimitConditions(d, tagParent);
        addRandomizationControls(d, tagParent);
        addRollupRules(d, tagParent);
        addRollupConsiderations(d, tagParent);
        addConstrainedChoiceConsiderations(d, tagParent);
	}

	protected void addConstrainedChoiceConsiderations(Digester d,
			String tagParent) {
		
        d.addObjectCreate(tagParent+"/sequencing/adlseq:constrainedChoiceConsiderations", ConstrainedChoiceConsiderations.class);
        d.addSetNext(tagParent+"/sequencing/adlseq:constrainedChoiceConsiderations", "setConstrainedChoiceConsiderations");
        d.addSetProperties(tagParent+"/sequencing/adlseq:constrainedChoiceConsiderations");
		
	}

	protected void addRollupConsiderations(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/sequencing/rollupConsiderations", RollupConsiderations.class);
        d.addSetNext(tagParent+"/sequencing/rollupConsiderations", "setRollupConsiderations");
        d.addSetProperties(tagParent+"/sequencing/rollupConsiderations");
	}

	protected void addRollupRules(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/sequencing/rollupRules", RollupRules.class);
        d.addSetNext(tagParent+"/sequencing/rollupRules", "setRollupRules");
        d.addSetProperties(tagParent+"/sequencing/rollupRules");

        // <rollupRule>
        d.addObjectCreate(tagParent+"/sequencing/rollupRules/rollupRule", RollupRule.class);
        d.addSetNext(tagParent+"/sequencing/rollupRules/rollupRule", "addRollupRule");
        d.addSetProperties(tagParent+"/sequencing/rollupRules/rollupRule");

		// <rollupCondition>
        d.addObjectCreate(tagParent+"/sequencing/rollupRules/rollupRule/rollupConditions/rollupCondition", RollupCondition.class);
        d.addSetProperties(tagParent+"/sequencing/rollupRules/rollupRule/rollupConditions/rollupCondition");
        d.addSetNext(tagParent+"/sequencing/rollupRules/rollupRule/rollupConditions/rollupCondition", "addRollupCondition");
		
		// <rollupAction>
		d.addCallMethod(tagParent+"/sequencing/rollupRules/rollupRule/rollupAction", "setRollupAction", 1);
		d.addCallParam(tagParent+"/sequencing/rollupRules/rollupRule/rollupAction", 0, "action");
        
        /*
        d.addCallMethod(tagParent+"/sequencing/rollupRules/rollupRule/rollupAction", "set");
        d.addSetNext(tagParent+"/sequencing/rollupRules/rollupRule", "addRollupRule");
        d.addSetProperties(tagParent+"/sequencing/rollupRules/rollupRule");
        */
        
	}

	protected void addRandomizationControls(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/sequencing/randomizationControls", RandomizationControls.class);
        d.addSetNext(tagParent+"/sequencing/randomizationControls", "setRandomizationControls");
        d.addSetProperties(tagParent+"/sequencing/randomizationControls");
	}

	protected void addLimitConditions(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/sequencing/limitConditions", LimitConditions.class);
        d.addSetNext(tagParent+"/sequencing/limitConditions", "setLimitConditions");
        d.addSetProperties(tagParent+"/sequencing/limitConditions");
        
	}

	protected void addDeliveryControls(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/sequencing/deliveryControls", DeliveryControls.class);
        d.addSetNext(tagParent+"/sequencing/deliveryControls", "setDeliveryControls");
        d.addSetProperties(tagParent+"/sequencing/deliveryControls");
	}

	protected void addObjectives(Digester d, String tagParent) {

        d.addObjectCreate(tagParent+"/sequencing/objectives", Objectives.class);
        d.addSetNext(tagParent+"/sequencing/objectives", "setObjectives");

        addObjective(d, tagParent+"/sequencing/objectives/primaryObjective", "setPrimaryObjective");
        addObjective(d, tagParent+"/sequencing/objectives/objective", "addObjective");
	}

	protected void addObjective(Digester d, String tagParent, String metodoAdd) {
		d.addObjectCreate(tagParent, Objective.class);
		
		d.addSetProperties(tagParent);                     
		d.addSetNext(tagParent, metodoAdd);
		d.addCallMethod(tagParent+"/minNormalizedMeasure", "setMinNormalizedMeasure", 0);
		
		d.addObjectCreate(tagParent+"/mapInfo", MapInfo.class);
		d.addSetNext(tagParent+"/mapInfo", "addMapInfo");
		d.addSetProperties(tagParent+"/mapInfo");
	}

	protected void addSequencingRules(Digester d, String tagParent) {
        addConditionRule(d, tagParent+"/sequencing/sequencingRules/preConditionRule", "addPreConditionRule");
        addConditionRule(d, tagParent+"/sequencing/sequencingRules/postConditionRule", "addPostConditionRule");
        addConditionRule(d, tagParent+"/sequencing/sequencingRules/exitConditionRule", "addExitConditionRule");
	}

	protected void addControlMode(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/sequencing/controlMode", ControlMode.class);
        d.addSetNext(tagParent+"/sequencing/controlMode", "setControlMode");
        d.addSetProperties(tagParent+"/sequencing/controlMode");
	}

	protected void addConditionRule(Digester d, String tagCondition, String metodoAdd) {
		d.addObjectCreate(tagCondition, SequencingRule.class);
		d.addSetNext(tagCondition, metodoAdd);

		// <ruleConditions>
		d.addSetProperties(tagCondition+"/ruleConditions");
				
		d.addObjectCreate(tagCondition+"/ruleConditions/ruleCondition", RuleCondition.class);
		d.addSetNext(tagCondition+"/ruleConditions/ruleCondition", "addRuleCondition");
		d.addSetProperties(tagCondition+"/ruleConditions/ruleCondition");

		// <ruleAction>
		d.addCallMethod(tagCondition+"/ruleAction", "setRuleAction", 1);
		d.addCallParam(tagCondition+"/ruleAction", 0, "action");
	}

	protected void addResources(Digester d) {
        d.addObjectCreate("manifest/resources", Resources.class);
        d.addSetNext("manifest/resources", "setResources");
        
        addResource(d);

        d.addObjectCreate("manifest/resources/resource/file", File.class);
        d.addSetNext("manifest/resources/resource/file", "addFile");
		d.addSetProperties("manifest/resources/resource/file");
		
        d.addObjectCreate("manifest/resources/resource/dependency", Dependency.class);
        d.addSetNext("manifest/resources/resource/dependency", "addDependency");
		d.addSetProperties("manifest/resources/resource/dependency");
	}

	protected void addResource(Digester d) {
        d.addObjectCreate("manifest/resources/resource", Resource.class);
        d.addSetNext("manifest/resources/resource", "addResource");
        d.addSetProperties("manifest/resources/resource", "scormType", "scormType");
        d.addSetProperties("manifest/resources/resource", "base", "xmlBase");
		d.addSetProperties("manifest/resources/resource");
		
	}

	@Override
	public BuildVersion buildVersion() throws Exception {
		if (this.version == null)
			this.version = Build20043rdEdition.create(); 
		return this.version;
	}

}
