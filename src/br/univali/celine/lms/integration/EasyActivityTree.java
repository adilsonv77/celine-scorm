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
package br.univali.celine.lms.integration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import br.univali.celine.scorm.model.cam.Item;
import br.univali.celine.scorm.model.cam.Resource;
import br.univali.celine.scorm.model.cam.Resources;
import br.univali.celine.scorm.sn.model.ActivityTree;
import br.univali.celine.scorm.sn.model.LearningActivity;
import br.univali.celine.scorm.sn.model.Objective;

public class EasyActivityTree {

	private ActivityTree tree;

	public EasyActivityTree(ActivityTree tree) {
		this.tree = tree;
	}

	public void addSubItem(String parent, Item item, List<Resource> resources) {

		// o item que vem aqui tem que ser mais simples
		
		LearningActivity parentLearnAct = tree.getLearningActivity(parent);

		// existe um processo controlador para criar os availableChildren... 
		// mas aqui o item tem que ser simples
		
		iterateLearningActivity(parentLearnAct, item);

		Resources res = tree.getContentPackage().getResources();
		for (Resource r:resources) {
			res.addResource(r);
		}
	}
	
	private void iterateLearningActivity(LearningActivity parentLearnAct, Item item) {

		LearningActivity newLearnAct = new LearningActivity(item);
		parentLearnAct.addChild(newLearnAct);
		parentLearnAct.getAvailableChildren().add(newLearnAct);
		tree.initialize(newLearnAct);
		
		Iterator<Item> children = item.getChildren();
		while (children.hasNext()) {
			Item child = children.next();
			iterateLearningActivity(newLearnAct, child);
			
		}
	}

	private void traverse(LearningActivity activity, Visitor visitor) {
		
		if (activity == null) {
			return;
		}
		
		for (LearningActivity child:activity.getChildren()) {
			visitor.visit(child);
			traverse(child, visitor);
		}

	}
	
	private abstract class Visitor {
		
		abstract void visit(LearningActivity learningActivity);
		
	}
	
	private abstract class WithListVisitor extends Visitor {
		
		List<LearningActivity> list = new ArrayList<LearningActivity>();
		
		List<LearningActivity> getList() {
			return list;
		}
	}
	
	/* ****************************************************************************** */
	/*    List the Learning Activities by attempt. The highest came first.
	/* ****************************************************************************** */
	
	public List<LearningActivity> getLAByAttempt() {
		
		AttemptVisitor visitor = new AttemptVisitor();
		traverse(tree.getRootActivity(), visitor);
		return visitor.getList();
		
	}

	private class AttemptVisitor extends WithListVisitor {

		
		void visit(LearningActivity learningActivity) {
			if (learningActivity.getAttemptCount() > 0) {
				list.add(learningActivity);
			}
			
		}

		
		List<LearningActivity> getList() {
			
			Collections.sort(list, new Comparator<LearningActivity>() {

				
				public int compare(LearningActivity act0, LearningActivity act1) {
					
					return act1.getAttemptCount() - act0.getAttemptCount(); // será negativo se o primeiro for maior
				}
			});
			
			return list;
		}
		
	}

	/* ****************************************************************************** */
	/*    List the Learning Activities where the title matches the key. Keys can use '%' like a wild card.
	/* ****************************************************************************** */
	
	public List<LearningActivity> getLAByTitle(String key) {
		
		ByTitleVisitor visitor = new ByTitleVisitor(key);
		traverse(tree.getRootActivity(), visitor);
		return visitor.getList();
		
	}

	private class ByTitleVisitor extends WithListVisitor {

		private String key;
		private int kindOfKey; // 0-equal; 1-ends with; 2-starts with; 3-has
		
		public ByTitleVisitor(String key) {
			kindOfKey = 0;
			if (key.indexOf("%") == -1) {
				// kindOfKey = 0; dont need this line
			} else {
				if (key.charAt(0) == '%') {
					kindOfKey = 1;
					key = key.substring(1);
				}
				
				if (key.charAt(key.length()-1) == '%') {
					kindOfKey += 2;
					key = key.substring(0, key.length()-2);
				}
			}
			this.key = key;
		}
		
		
		void visit(LearningActivity learningActivity) {
			
			String title = learningActivity.getItem().getTitle();
			switch (kindOfKey) {
				case 0 :
					
					if (title.equals(key)) {
						list.add(learningActivity);
  				    }
					return;
					
				case 1:
					if (title.endsWith(key)) {
						list.add(learningActivity);
  				    }
					return;
					
				case 2:
					if (title.startsWith(key)) {
						list.add(learningActivity);
  				    }
					return;

				case 3:
					if (title.indexOf(key) > -1) {
						list.add(learningActivity);
  				    }
					return;
}
			
			
		}
		
	}

	/* ****************************************************************************** */
	/*    List the Learning Activities by time duration. The highest came first.
	/* ****************************************************************************** */
	
	public List<LearningActivity> getLAByTimeDuration() {
		TimeDurationVisitor visitor = new TimeDurationVisitor();
		traverse(tree.getRootActivity(), visitor);
		return visitor.getList();
	}
	
	private class TimeDurationVisitor extends AttemptVisitor {

		
		List<LearningActivity> getList() {
			
			Collections.sort(list, new Comparator<LearningActivity>() {

				
				public int compare(LearningActivity act0, LearningActivity act1) {
					
					double r = act1.getActivityAbsoluteDuration() - act0.getActivityAbsoluteDuration(); 
					if (r < 0) return -1;
					if (r > 0) return 1;
					return 0;
					
				}});
			
			return list;
		}
		
	}

	public List<Objective> getObjectivesGreaterThan(double threshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Objective> getObjectivesLessThan(double threshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Objective> getObjectivesGreaterThanOrEqual(double threshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Objective> getObjectivesLessThanOrEqual(double threshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateObjectives(double newMinNormalizedMeasure, List<Objective> objectives) {
		// TODO Auto-generated method stub
		
	}

	public void updateAllObjectives(double newMinNormalizedMeasure) {
		// TODO Auto-generated method stub
		
	}

}
