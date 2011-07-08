package br.univali.celine.scorm2004_4th.model.cam;

import java.util.LinkedList;
import java.util.List;

import br.univali.celine.scorm.model.cam.AbstractItem;
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
			ret += "<adlcp:completionThreshold completedByMeasure="+completionThreshold.isCompletedByMeasure()+
							" minProgressMeasure="+completionThreshold.getMinProgressMeasure()+
							" progressWeight="+completionThreshold.getProgressWeight()+"/>\n";
		}
		if (getImsssSequencing() != null) {
			ret += getImsssSequencing() + "\n";
		}
		if (getAdlNavPresentation() != null) {
			ret += getAdlNavPresentation() + "\n";
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
