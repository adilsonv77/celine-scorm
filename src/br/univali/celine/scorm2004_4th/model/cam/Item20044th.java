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

import java.util.LinkedList;
import java.util.List;

import br.univali.celine.scorm.model.cam.AbstractItem;
import br.univali.celine.scorm.model.cam.CompletionThreshold;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.imsss.Sequencing;

public class Item20044th extends AbstractItem implements Item {

	public static Item20044th buildBasic(String identifier, String title) {
		Item20044th item = new Item20044th();
		item.setCompletionThreshold(new CompletionThreshold());
		item.setIdentifier(identifier);
		item.setTitle(title);
		item.setImsssSequencing(new Sequencing());
		item.getImsssSequencing().getControlMode().setFlow(true);
		return item;
	}
	
	private CompletionThreshold completionThreshold;

	public double getCompletionThreshold() {
		return getObjectCompletionThreshold().getMinProgressMeasure();
	}
	
	public CompletionThreshold getObjectCompletionThreshold() {
		if (completionThreshold == null)
			completionThreshold = new CompletionThreshold();
		return completionThreshold;
	}

	public void setCompletionThreshold(CompletionThreshold completionThreshold) {
		this.completionThreshold = completionThreshold;
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
		if (getObjectCompletionThreshold() != null) {
			ret += "<adlcp:completionThreshold completedByMeasure=\""+completionThreshold.isCompletedByMeasure()+"\""+
							" minProgressMeasure=\""+completionThreshold.getMinProgressMeasure()+"\""+
							" progressWeight=\""+completionThreshold.getProgressWeight()+"\"/>\n";
		}
		if (getImsssSequencing() != null) {
			ret += getImsssSequencing() + "\n";
		}
		if (getAdlNavPresentation() != null) {
			ret += getAdlNavPresentation() + "\n";
		}
		if (getAdlcpData().size() > 0) {
			ret += "<adlcp:data> \n";
			for (AdlcpMap m:getAdlcpData())
				ret += "<adlcp:map targetID=\""+m.getTargetID()+"\" " +
								  "readSharedData=\""+m.isReadSharedData()+"\" " +
								  "writeSharedData=\""+m.isWriteSharedData()+"\"/>\n";
			ret += "</adlcp:data> \n";
		}
		
		return  ret + "</item>";
		
	}
	
	private List<AdlcpMap> adlcpData = new LinkedList<AdlcpMap>();

	public List<AdlcpMap> getAdlcpData() {
		return adlcpData;
	}

	public void setAdlcpData(List<AdlcpMap> adlcpData) {
		this.adlcpData = adlcpData;
	}
	
	public void addAdlcpMap(AdlcpMap map) {
		this.adlcpData.add(map);
	}
	
}
