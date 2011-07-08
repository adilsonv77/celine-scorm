package br.univali.celine.scorm.model.cam;

import java.util.Iterator;

import br.univali.celine.scorm.model.imsss.Sequencing;

public interface SuperItem {

	String getIdentifier();

	void setIdentifier(String string);
	
	String getTitle();

	Item getItem(String identifier);

	Iterator<Item> getChildren();

	void addItem(Item item);

	Sequencing getImsssSequencing();

	double getCompletionThreshold();
	
	Metadata getMetadata();
}
