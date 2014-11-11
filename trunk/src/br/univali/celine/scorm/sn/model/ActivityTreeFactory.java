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
