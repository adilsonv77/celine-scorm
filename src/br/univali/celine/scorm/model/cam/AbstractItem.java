package br.univali.celine.scorm.model.cam;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

	public String getIdentifierref() {
		return "";
	}

	public String getParameters() {
		return "";
	}

	private Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	

}
