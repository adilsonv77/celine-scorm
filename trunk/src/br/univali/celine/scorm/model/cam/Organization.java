package br.univali.celine.scorm.model.cam;


public class Organization extends AbstractItem {

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
		Item item = new Item();
		
		item.setIdentifier(getIdentifier());
		item.setIdentifierref(getIdentifierref());
		item.setImsssSequencing(getImsssSequencing());
		item.setMetadata(getMetadata());
		item.setTitle(getTitle());
		item.setParameters(getParameters());
		
		return item;
	}
	
}
