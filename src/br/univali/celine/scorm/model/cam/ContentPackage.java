package br.univali.celine.scorm.model.cam;


public class ContentPackage {

	private ContentPackageReader contentPackageReader;

	public ContentPackage(ContentPackageReader cpr) {
		this.contentPackageReader = cpr;
	}

	public ContentPackageReader getContentPackageReader() {
		return contentPackageReader;
	}
	
	public static ContentPackage buildBasic(String organizationName, String orgIdentifier, ContentPackageReader cpr) {
		ContentPackage cp = new ContentPackage(cpr);
		cp.setIdentifier(orgIdentifier + "_ims");

		cp.setOrganizations(new Organizations());
		cp.setResources(new Resources());

		Organization20043rd org = new Organization20043rd();
		org.setTitle(organizationName);
		org.setIdentifier(orgIdentifier);
		cp.getOrganizations().addOrganization(org);

		org.getImsssSequencing().getControlMode().setFlow(true);

		cp.getOrganizations().setDefaultOrg(orgIdentifier);
		
		return cp;
		
	}
	
	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	private Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	private String version;
	
	/**
	 * @return The version of the file. For the version of SCORM, use getMetadata().getSchemaVersion()
	 */
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private Organizations organizations;
	
	public Organizations getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Organizations organizations) {
		this.organizations = organizations;
	}

	private Resources resources;
	
	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public String toString() {
		String identifier = "";
		if (this.identifier != null) 
			identifier = "identifier=\"" + this.identifier + "\" ";
		String version = "";
		if (this.version != null)
			version = "version=\""  + this.version +"\" ";
		String s = "<manifest " +identifier + version +
			"xsi:schemaLocation=\"http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd http://www.adlnet.org/xsd/adlcp_v1p3 adlcp_v1p3.xsd http://www.adlnet.org/xsd/adlseq_v1p3 adlseq_v1p3.xsd http://www.adlnet.org/xsd/adlnav_v1p3 adlnav_v1p3.xsd http://www.imsglobal.org/xsd/imsss imsss_v1p0.xsd\" \n"+
			"xmlns=\"http://www.imsglobal.org/xsd/imscp_v1p1\"\n"+
			"xmlns:adlcp=\"http://www.adlnet.org/xsd/adlcp_v1p3\"\n"+
			"xmlns:adlseq=\"http://www.adlnet.org/xsd/adlseq_v1p3\"\n"+
			"xmlns:adlnav=\"http://www.adlnet.org/xsd/adlnav_v1p3\"\n"+
			"xmlns:imsss=\"http://www.imsglobal.org/xsd/imsss\"\n"+
			"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"+
			"version=\""+version+"\">\n";
		if (metadata != null)
			s += metadata + "\n";
		s += organizations + "\n" + 
				resources + "\n"+
		"</manifest>";
		return s;
	}

	private Organization orgAtual = null;
	
	public Organization nextContentOrganization() {
		
		orgAtual = organizations.nextContentOrganization(); 
		return orgAtual;
	}

	public Resource getResource(String identifierref) {
		return resources.getResource(identifierref); 
	}

	public Organization getCurrentOrganization() {
		
		return orgAtual;
	}

	public void finalization() {
		
		// finalize the global objectives
		organizations.finalization();
		
	}
	
}
