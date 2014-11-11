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


public class Organization20043rd extends AbstractItem implements SuperItem, Organization {

	@Override
	public String toString() {
		String ret = "<organization identifier=\""+getIdentifier()+"\"> \n";
		ret+="<title>"+getTitle()+"</title> \n";
		if (getMetadata() != null)
			ret += getMetadata() + "\n";
		 	
		for (Item item:itens) {
			ret+=item+"\n";
		}
		if (getImsssSequencing() != null) {
			ret += getImsssSequencing() + "\n";
		}
		return ret + "</organization>";
	}

	public Item toItem() {
		Item20043rd item = new Item20043rd();
		
		item.setIdentifier(getIdentifier());
		item.setIdentifierref(getIdentifierref());
		item.setImsssSequencing(getImsssSequencing());
		item.setMetadata(getMetadata());
		item.setTitle(getTitle());
		item.setParameters(getParameters());
		
		return item;
	}
	
	private String structure = "hierarchical";

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}
	
	private boolean objectivesGlobalToSystem = true;

	public boolean isObjectivesGlobalToSystem() {
		return objectivesGlobalToSystem;
	}

	public void setObjectivesGlobalToSystem(boolean objectivesGlobalToSystem) {
		this.objectivesGlobalToSystem = objectivesGlobalToSystem;
	}
	
	

}
