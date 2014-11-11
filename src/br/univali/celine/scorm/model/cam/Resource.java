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
		
		String ret = String.format("<resource identifier=\"%s\" type=\"%s\" "+
											(href!=null?"href=\""+href+"\"":"")+ 
											" xml:base=\"%s\" adlcp:scormType=\"%s\">\n",
							new Object[]{identifier, type, xmlBase, scormType}); 
			
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
