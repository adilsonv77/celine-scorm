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
