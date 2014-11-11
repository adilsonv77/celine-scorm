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

public class Metadata {

	private String schema;
	private String schemaversion;
	private String location;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getSchemaversion() {
		return schemaversion;
	}
	public void setSchemaversion(String schemaVersion) {
		this.schemaversion = schemaVersion;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		String ret = "";
		if (schema != null) {
			ret = "<schema>" + schema + "</schema>\n";
		}
		if (schemaversion != null) {
			ret += "<schemaversion>"+schemaversion+"</schemaversion>\n";
		}
		if (location != null) {
			ret += "<adlcp:location>" + this.location +"</adlcp:location>\n";
		}
		return "<metadata>\n" + ret + "</metadata>";
	}
	
	public Metadata clonar() {
		Metadata md = new Metadata();
		
		md.setLocation(location);
		md.setSchema(schema);
		md.setSchemaversion(schemaversion);
		
		return md;
	}
	
	
}
