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

import br.univali.celine.scorm.model.imsss.Sequencing;

public class Item20043rd extends AbstractItem implements Item {

	public static Item20043rd buildBasic(String identifier, String title) {
		Item20043rd item = new Item20043rd();
		item.setIdentifier(identifier);
		item.setTitle(title);
		item.setImsssSequencing(new Sequencing());
		item.getImsssSequencing().getControlMode().setFlow(true);
		return item;
	}
	
	@Override
	public String toString() {
		String ret = "";
		if (!getParameters().equals("")) {
			ret="parameters=\""+getParameters()+"\"";
		}
		
		String idRef = getIdentifierref();
		if (idRef == null || idRef.length() == 0) {
			idRef = "";
		} else {
			idRef = "identifierref=\""+getIdentifierref()+"\"";
		}
		ret = "<item identifier=\""+getIdentifier()+"\" "+idRef+" isvisible=\""+isIsvisible()+"\""+ret+">\n";
		ret += "<title>"+getTitle()+"</title>\n";
		if (getMetadata() != null)
			ret += getMetadata()+"\n";
		for (Item item:itens) {
			ret += item + "\n";
		}
		if (getTimeLimitAction() != null) {
			ret += "<adlcp:timeLimitAction>"+getTimeLimitAction()+"</adlcp:timeLimitAction>\n";
		}
		if (getDataFromLMS() != null) {
			ret += "<adlcp:dataFromLMS>"+getDataFromLMS()+"</adlcp:dataFromLMS>\n";
		}
		if (getCompletionThreshold() > -1) {
			ret += "<adlcp:completionThreshold>"+getCompletionThreshold()+"</adlcp:completionThreshold>\n";
		}
		if (getImsssSequencing() != null) {
			ret += getImsssSequencing() + "\n";
		}
		if (getAdlNavPresentation() != null) {
			ret += getAdlNavPresentation() + "\n";
		}
		return  ret + "</item>";
		
	}
	
}
