package br.univali.celine.scorm.sn.model;

import java.util.Iterator;

import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Organization;

public class ActivityTreeFactory {

	public static ActivityTree build(ContentPackage cp) {
		
		Organization org = cp.getOrganizations().getDefaultOrganization();

		LearningActivity root = new LearningActivity(org);
		
		Iterator<Item> children = org.getChildren();
		while (children.hasNext()) {
			Item item = children.next();
			
			iterateItem(item, root);
		}
		
		return new ActivityTree(cp, root);
	}

	private static void iterateItem(Item item, LearningActivity lastAct) {
		
		LearningActivity newAct = new LearningActivity(item);
		lastAct.addChild(newAct);
		
		Iterator<Item> children = item.getChildren();
		while (children.hasNext()) {
			Item itemChild = children.next();
			iterateItem(itemChild, newAct);
		}
		
	}
	
}
