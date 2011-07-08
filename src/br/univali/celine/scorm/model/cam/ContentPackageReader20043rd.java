package br.univali.celine.scorm.model.cam;

import java.io.FileInputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;

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

public class ContentPackageReader20043rd implements ContentPackageReader {

	protected Class<?> itemClass = Item20043rd.class;
	protected Class<?> organizationClass = Organization20043rd.class;
	
	public ContentPackage ler(String nomeArquivo) throws Exception {
		if (nomeArquivo.startsWith("file:")) {
			nomeArquivo = (new URL(nomeArquivo)).getFile();
		}
		return ler(new java.io.File(nomeArquivo));
	}
	
	public ContentPackage ler(java.io.File arquivo) throws Exception {
		return ler(new FileInputStream(arquivo));
	}
	
	public ContentPackage ler(java.io.InputStream stream) throws Exception {
        // Create a Digester instance
        Digester d = new Digester();
        
        // Prime the digester stack with an object for rules to
        // operate on. Note that it is quite common for "this"
        // to be the object pushed.
        ContentPackage manifest = new ContentPackage();
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
		d.addSetProperties("manifest/organizations/organization", "adlseq:objectivesGlobalToSystem", "objectivesGlobalToSystem");
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
        d.addObjectCreate("*/item/adlnav:presentation/adlnav:navigationInterface", NavigationInterface.class);
        d.addSetNext("*/item/adlnav:presentation/adlnav:navigationInterface", "setNavigationInterface");
        d.addCallMethod("*/item/adlnav:presentation/adlnav:navigationInterface/adlnav:hideLMSUI", "addHideLMSUI", 0);
	}

	protected void addPresentation(Digester d) {
		d.addObjectCreate("*/item/adlnav:presentation", Presentation.class);
        d.addSetNext("*/item/adlnav:presentation", "setAdlNavPresentation");

	}

	protected void addCompletionThreshold(Digester d) {
      d.addCallMethod("*/item/adlcp:completionThreshold", "setCompletionThreshold", 0);
	}
	
	protected void addOrganizationTitle(Digester d) {
		d.addCallMethod("manifest/organizations/organization/title", "setTitle", 0);
	}

	protected void addImssSequencing(Digester d, String tagParent) {
		d.addObjectCreate(tagParent+"/imsss:sequencing", Sequencing.class);
        d.addSetNext(tagParent+"/imsss:sequencing", "setImsssSequencing");

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
		
        d.addObjectCreate(tagParent+"/imsss:sequencing/adlseq:constrainedChoiceConsiderations", ConstrainedChoiceConsiderations.class);
        d.addSetNext(tagParent+"/imsss:sequencing/adlseq:constrainedChoiceConsiderations", "setConstrainedChoiceConsiderations");
        d.addSetProperties(tagParent+"/imsss:sequencing/adlseq:constrainedChoiceConsiderations");
		
	}

	protected void addRollupConsiderations(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/imsss:sequencing/adlseq:rollupConsiderations", RollupConsiderations.class);
        d.addSetNext(tagParent+"/imsss:sequencing/adlseq:rollupConsiderations", "setRollupConsiderations");
        d.addSetProperties(tagParent+"/imsss:sequencing/adlseq:rollupConsiderations");
	}

	protected void addRollupRules(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:rollupRules", RollupRules.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:rollupRules", "setRollupRules");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:rollupRules");

        // <rollupRule>
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule", RollupRule.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule", "addRollupRule");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule");

		// <rollupCondition>
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule/imsss:rollupConditions/imsss:rollupCondition", RollupCondition.class);
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule/imsss:rollupConditions/imsss:rollupCondition");
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule/imsss:rollupConditions/imsss:rollupCondition", "addRollupCondition");
		
		// <rollupAction>
		d.addCallMethod(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule/imsss:rollupAction", "setRollupAction", 1);
		d.addCallParam(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule/imsss:rollupAction", 0, "action");
        
        /*
        d.addCallMethod(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule/imsss:rollupAction", "set");
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule", "addRollupRule");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:rollupRules/imsss:rollupRule");
        */
        
	}

	protected void addRandomizationControls(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:randomizationControls", RandomizationControls.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:randomizationControls", "setRandomizationControls");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:randomizationControls");
	}

	protected void addLimitConditions(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:limitConditions", LimitConditions.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:limitConditions", "setLimitConditions");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:limitConditions");
        
	}

	protected void addDeliveryControls(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:deliveryControls", DeliveryControls.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:deliveryControls", "setDeliveryControls");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:deliveryControls");
	}

	protected void addObjectives(Digester d, String tagParent) {

        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:objectives", Objectives.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:objectives", "setObjectives");

        addObjective(d, tagParent+"/imsss:sequencing/imsss:objectives/imsss:primaryObjective", "setPrimaryObjective");
        addObjective(d, tagParent+"/imsss:sequencing/imsss:objectives/imsss:objective", "addObjective");
	}

	protected void addObjective(Digester d, String tagParent, String metodoAdd) {
		d.addObjectCreate(tagParent, Objective.class);
		
		d.addSetProperties(tagParent);                     
		d.addSetNext(tagParent, metodoAdd);
		d.addCallMethod(tagParent+"/imsss:minNormalizedMeasure", "setMinNormalizedMeasure", 0);
		
		d.addObjectCreate(tagParent+"/imsss:mapInfo", MapInfo.class);
		d.addSetNext(tagParent+"/imsss:mapInfo", "addMapInfo");
		d.addSetProperties(tagParent+"/imsss:mapInfo");
	}

	protected void addSequencingRules(Digester d, String tagParent) {
        addConditionRule(d, tagParent+"/imsss:sequencing/imsss:sequencingRules/imsss:preConditionRule", "addPreConditionRule");
        addConditionRule(d, tagParent+"/imsss:sequencing/imsss:sequencingRules/imsss:postConditionRule", "addPostConditionRule");
        addConditionRule(d, tagParent+"/imsss:sequencing/imsss:sequencingRules/imsss:exitConditionRule", "addExitConditionRule");
	}

	protected void addControlMode(Digester d, String tagParent) {
        d.addObjectCreate(tagParent+"/imsss:sequencing/imsss:controlMode", ControlMode.class);
        d.addSetNext(tagParent+"/imsss:sequencing/imsss:controlMode", "setControlMode");
        d.addSetProperties(tagParent+"/imsss:sequencing/imsss:controlMode");
	}

	protected void addConditionRule(Digester d, String tagCondition, String metodoAdd) {
		d.addObjectCreate(tagCondition, SequencingRule.class);
		d.addSetNext(tagCondition, metodoAdd);

		// <ruleConditions>
		d.addSetProperties(tagCondition+"/imsss:ruleConditions");
				
		d.addObjectCreate(tagCondition+"/imsss:ruleConditions/imsss:ruleCondition", RuleCondition.class);
		d.addSetNext(tagCondition+"/imsss:ruleConditions/imsss:ruleCondition", "addRuleCondition");
		d.addSetProperties(tagCondition+"/imsss:ruleConditions/imsss:ruleCondition");

		// <ruleAction>
		d.addCallMethod(tagCondition+"/imsss:ruleAction", "setRuleAction", 1);
		d.addCallParam(tagCondition+"/imsss:ruleAction", 0, "action");
	}

	protected void addResources(Digester d) {
        d.addObjectCreate("manifest/resources", Resources.class);
        d.addSetNext("manifest/resources", "setResources");
        
        d.addObjectCreate("manifest/resources/resource", Resource.class);
        d.addSetNext("manifest/resources/resource", "addResource");
        d.addSetProperties("manifest/resources/resource", "adlcp:scormType", "scormType");
        d.addSetProperties("manifest/resources/resource", "xml:base", "xmlBase");
		d.addSetProperties("manifest/resources/resource");
		
        d.addObjectCreate("manifest/resources/resource/file", File.class);
        d.addSetNext("manifest/resources/resource/file", "addFile");
		d.addSetProperties("manifest/resources/resource/file");
		
        d.addObjectCreate("manifest/resources/resource/dependency", Dependency.class);
        d.addSetNext("manifest/resources/resource/dependency", "addDependency");
		d.addSetProperties("manifest/resources/resource/dependency");
	}

}
