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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.univali.celine.scorm.model.adlnav.Presentation;
import br.univali.celine.scorm.model.imsss.Sequencing;

public abstract class AbstractItem {
	
	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	private Sequencing imsssSequencing;
	
	public Sequencing getImsssSequencing() {
		if (imsssSequencing == null)
			imsssSequencing = new Sequencing();
		return imsssSequencing;
	}

	public void setImsssSequencing(Sequencing imsssSequencing) {
		this.imsssSequencing = imsssSequencing;
	}

    protected List<Item> itens = new LinkedList<Item>();
	
	public void addItem(Item item) {
		itens.add(item);
	}
	
	public Item getItem(String identifier) {
		for (Item it:itens) {
			if (it.getIdentifier().equals(identifier)) {
				return it;
			}
			
			Item i = it.getItem(identifier);
			if (i != null)
				return i;
		}
		return null;
	}
	
	public Iterator<Item> getChildren() {
		return itens.iterator();
	}

	private Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	private double completionThreshold = -1; // nao inicializado

	public double getCompletionThreshold() {
		return completionThreshold;
	}

	public void setCompletionThreshold(String completionThreshold) {
		setCompletionThreshold(Double.valueOf(completionThreshold));
	}
	
	public void setCompletionThreshold(double completionThreshold) {
		this.completionThreshold = completionThreshold;
	}

	private Presentation adlNavPresentation;  
	
	
	public Presentation getAdlNavPresentation() {
		return adlNavPresentation;
	}

	public void setAdlNavPresentation(Presentation adlNavPresentation) {
		this.adlNavPresentation = adlNavPresentation;
	}

	private String identifierref, parameters = "";
	private boolean isvisible = true;
	public boolean isIsvisible() {
		return isvisible;
	}

	public void setIsvisible(boolean isvisible) {
		this.isvisible = isvisible;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getIdentifierref() {
		return identifierref;
	}

	public void setIdentifierref(String identifierref) {
		this.identifierref = identifierref;
	}
	
	private String dataFromLMS;

	public String getDataFromLMS() {
		return dataFromLMS;
	}

	public void setDataFromLMS(String dataFromLMS) {
		this.dataFromLMS = dataFromLMS;
	}
	
	private String timeLimitAction;

	public String getTimeLimitAction() {
		return timeLimitAction;
	}

	public void setTimeLimitAction(String timeLimitAction) {
		this.timeLimitAction = timeLimitAction;
	}
	
	


}
