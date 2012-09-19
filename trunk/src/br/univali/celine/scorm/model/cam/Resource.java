package br.univali.celine.scorm.model.cam;

import java.util.LinkedList;
import java.util.List;

public class Resource {

	private Metadata metadata;
	private String identifier;
	private String type = "webcontent";
	private String href;
	private String scormType;
	private String xmlBase = "";
	
	public String getXmlBase() {
		return xmlBase;
	}
	public void setXmlBase(String xmlBase) {
		this.xmlBase = xmlBase;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getScormType() {
		return scormType;
	}
	public void setScormType(String scormType) {
		this.scormType = scormType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	private List<File> files = new LinkedList<File>();
	
	public void addFile(File file) {
		files.add(file);
	}
	
	private List<Dependency> dependencies = new LinkedList<Dependency>();
	
	public void addDependency(Dependency d) {
		dependencies.add(d);
	}
	
	public String toString() {
		
		String ret = String.format("<resource identifier=\"%s\" type=\"%s\" href=\"%s\" xml:base=\"%s\" adlcp:scormType=\"%s\">\n",
							new Object[]{identifier, type, href, xmlBase, scormType}); 
			
		if (metadata != null) {
			ret += metadata;
		}
		
		for (File arq:files) {
			ret += arq + "\n";
		}
		
		for (Dependency d:dependencies) {
			ret += d + "\n";
		}
		
		return ret + "</resource>\n";
		
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public Resource clonar() {
		
		Resource newResource = new Resource();
		
		newResource.setHref(href);
		newResource.setIdentifier(identifier);
		newResource.setMetadata(clonarMetadata());
		newResource.setScormType(scormType);
		newResource.setType(type);
		newResource.setXmlBase(xmlBase);
		
		for (File f:files) {
			newResource.addFile(new File(f.getHref()));
		}
		
		for (Dependency d:dependencies) {
			newResource.addDependency(new Dependency(d.getIdentifierref()));
		}
		
		return newResource;
	}
	private Metadata clonarMetadata() {
		
		if (metadata == null)
			return null;
			
		Metadata newMetadata = new Metadata();
		
		newMetadata.setLocation(metadata.getLocation());
		newMetadata.setSchema(metadata.getSchema());
		newMetadata.setSchemaversion(metadata.getSchemaversion());
		
		return newMetadata;
	}
	
}
