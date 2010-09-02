package br.univali.celine.scorm.model.cam;

import br.univali.celine.scorm.model.adlnav.Presentation;
import br.univali.celine.scorm.model.imsss.Sequencing;

public class Item extends AbstractItem {

	public static Item buildBasic(String identifier, String title) {
		Item item = new Item();
		item.setIdentifier(identifier);
		item.setTitle(title);
		item.setImsssSequencing(new Sequencing());
		item.getImsssSequencing().getControlMode().setFlow(true);
		return item;
	}
	
	private String identifierref, parameters = "";
	private boolean isvisible = true;
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
	
	private Presentation adlNavPresentation;  
	
	
	public Presentation getAdlNavPresentation() {
		return adlNavPresentation;
	}

	public void setAdlNavPresentation(Presentation adlNavPresentation) {
		this.adlNavPresentation = adlNavPresentation;
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
		ret = "<item identifier=\""+getIdentifier()+"\" "+idRef+" isVisible=\""+isIsvisible()+"\""+ret+">\n";
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
