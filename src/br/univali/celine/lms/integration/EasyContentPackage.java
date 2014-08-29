package br.univali.celine.lms.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Organization;
import br.univali.celine.scorm.model.cam.Resource;
import br.univali.celine.scorm.model.cam.Resources;
import br.univali.celine.scorm.model.imsss.MapInfo;
import br.univali.celine.scorm.model.imsss.Objective;

public class EasyContentPackage {

	private List<Item> itens = new ArrayList<Item>();
	private Resources resources = new Resources();
	private boolean modifyId = true;
	
	public EasyContentPackage() {
	}

	public EasyContentPackage(boolean modifyId) {
		this.modifyId = modifyId;
	}
	
	public void addItem(String contentPackageId, Item item) {
		updateIdentifier(contentPackageId, item);
		itens.add(item);
	}

	private void updateIdentifier(String contentPackageId, Item item) {
		if (!modifyId)
			return;
		
		// garantir que os itens sejam unicos dentro do novo ContentPackage pois, nada impede que itens de CP diferentes tenham o mesmo id !!!
		item.setIdentifier(contentPackageId+"_"+item.getIdentifier());
		
		
		// alterar os nomes dos objetivos globais, para garantir que nao haja conflitos.
		// Por exemplo, dois cursos podem ter o objetivo global "obj1", mas nao pode interferir no do outro.
		
		updateMapInfo(contentPackageId, item.getImsssSequencing().getObjectives().getPrimaryObjective());
		
		for (Objective obj:item.getImsssSequencing().getObjectives().getObjectives()) {
			updateMapInfo(contentPackageId, obj);
		}
		
		
		Iterator<Item> children = item.getChildren();
		while (children.hasNext()) {
			updateIdentifier(contentPackageId, children.next());
		}
	}
	
	
	private void updateMapInfo(String contentPackageId, Objective obj) {
		for (MapInfo mapInfo:obj.getMapsInfo()) {
			if (mapInfo.getTargetObjectiveID() != null) {
				mapInfo.setTargetObjectiveID(contentPackageId+"_"+mapInfo.getTargetObjectiveID());
			}
		}
	}

	private Item doLoadItem(ContentPackage contentPackage, String itemId, String courseFolder) {
		
		Item item = contentPackage.getOrganizations().getDefaultOrganization().getItem(itemId);

		if (item != null) {
				loadResources(contentPackage.getResources(), item, courseFolder);
		}
		
		return item;
	}
	
	public Item addItem(ContentPackage contentPackage, String itemId,
			String courseFolder) {

		Item item = doLoadItem(contentPackage, itemId, courseFolder);

		if (item != null) {
			addItem(contentPackage.getIdentifier(), item);
		}
		return item;
	}

	public void addSubItem(ContentPackage contentPackage, String baseItem,
			String newItemId, String courseFolder) {
		
		Item item = doLoadItem(contentPackage, newItemId, courseFolder);
		if (item != null) {
			
			Item base = findItem(baseItem);
			if (base != null) {
				base.addItem(item);
				
				updateIdentifier(contentPackage.getIdentifier(), item);
			}
			
		}
		
	}

	public Item findItem(String itemIdentifier) {

		for (Item item:itens) {
			if (item.getIdentifier().equals(itemIdentifier)) {
				return item;
			}
				
			Item found = item.getItem(itemIdentifier);
			if (found != null)
				return found;
		}
		
		return null;
	}

	private void loadResources(Resources resources, Item item,
			String courseFolder) {
		Resource res = resources.getResource(item.getIdentifierref());
		if (res != null) {
			res = res.clonar(); // isso daqui para nao afetar o ContentPackage original
			// i dont understand why this line below 
			// res.setXmlBase("../" + courseFolder + "/" + res.getXmlBase());
			res.setIdentifier(item.getIdentifier() + "_" + res.getIdentifier());
			item.setIdentifierref(res.getIdentifier());
			addResource(res);
		}

		Iterator<Item> children = item.getChildren();
		while (children.hasNext()) {
			loadResources(resources, children.next(), courseFolder);
		}
	}

	public Item addContentPackage(ContentPackage contentPackage, String courseFolder) {
		
		Organization defaultOrg = contentPackage.getOrganizations().getDefaultOrganization();
		Item root = defaultOrg.toItem();
		root.getImsssSequencing().getControlMode().setFlow(true);
		itens.add(root);
		Iterator<Item> list = defaultOrg.getChildren();
		while (list.hasNext()) {
			root.addItem(list.next());
		}
		
		updateIdentifier(contentPackage.getIdentifier(), root);
		
		loadResources(contentPackage.getResources(), root, courseFolder);
		
		return root;
		
	}

	public void addItem(Item item) {
		itens.add(item);
	}

	public void addResource(Resource res) {
		resources.addResource(res);
	}

	public Resources getResources() {
		return resources;
	}
	
	public ContentPackage build(String organizationName, String orgIdentifier) {

		ContentPackage cp = ContentPackage.buildBasic(organizationName, orgIdentifier, null);

		Organization org = cp.getOrganizations().getDefaultOrganization();
		for (Item item : itens) {
			org.addItem(item);
		}

		cp.setResources(resources);

		return cp;
	}

}
