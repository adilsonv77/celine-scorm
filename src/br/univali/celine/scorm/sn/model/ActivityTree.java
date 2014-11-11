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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.imsss.MapInfo;
import br.univali.celine.scorm.model.imsss.Objective;
import br.univali.celine.scorm.model.imsss.RollupAction;
import br.univali.celine.scorm.model.imsss.RollupCondition;
import br.univali.celine.scorm.model.imsss.RollupRule;
import br.univali.celine.scorm.model.imsss.RollupRules;
import br.univali.celine.scorm.sn.model.interaction.Interaction;

public class ActivityTree {

	private LearningActivity currentActivity;
	private LearningActivity rootActivity;
	private LearningActivity suspendedActivity;
	private LinkedHashSet<LearningActivity> setOfActivities;
	private LearningActivity targetActivity;
	private HashMap<String, GlobalObjective> globalObjectives = new HashMap<String, GlobalObjective>();
	
	// para guardar os elementos do data model
	private HashMap<String, String> dataModel = new HashMap<String, String> ();
	private ContentPackage contentPackage;
	
	public void putDataModel(String key, String value) {
		dataModel.put(key, value);
	}
	
	public String getDataModel(String key) {
		return dataModel.get(key);
	}
	
	public void removeDataModel(String key) {
		dataModel.remove(key);
	}
	
	public ActivityTree(ContentPackage cp, LearningActivity rootActivity) {
		this.contentPackage = cp;
		this.rootActivity = rootActivity;
		this.setOfActivities = new LinkedHashSet<LearningActivity>();
		
		percorrerArvore(rootActivity);
	}
	
	public ContentPackage getContentPackage() {
		return this.contentPackage;
	}
	
	private void percorrerArvore(LearningActivity activity) {
		this.setOfActivities.add(activity);
		
		initialize(activity);
		
		List<LearningActivity> children = activity.getChildren();
		for (LearningActivity child:children)
			percorrerArvore(child);
		
	}

	public void initialize(LearningActivity activity) {

		buildObjectives(activity);
		buildDefaultRollupRules(activity);
		
	}

	private void buildObjectives(LearningActivity activity) {
		addPrimaryObjective(activity, activity.getItem().getImsssSequencing().getObjectives().getPrimaryObjective());
		
		for (br.univali.celine.scorm.model.imsss.Objective obj:activity.getItem().getImsssSequencing().getObjectives().getObjectives()) {
			if (obj.getMapsInfo().size() == 0) {
				activity.getObjectives().add(new ProxyObjective(obj)); // nao tem mapeamento para o global
			} else {
				
				addObjective(activity, obj);
			}
		}
		
	}

	// SN 4.6.4 e 4.6.5
	private void buildDefaultRollupRules(LearningActivity activity) {
		RollupRules rollupRules = activity.getItem().getImsssSequencing().getRollupRules();
		if (!findAction(rollupRules, RollupAction.incomplete)) {
			rollupRules.addRollupRule(createIncompleteRule());
		}
		if (!findAction(rollupRules, RollupAction.completed)) {
			rollupRules.addRollupRule(createCompletedRule());
		}
		if (!findAction(rollupRules, RollupAction.notsatisfied)) {
			rollupRules.addRollupRule(createNotSatisfiedRule());
		}
		if (!findAction(rollupRules, RollupAction.satisfied)) {
			rollupRules.addRollupRule(createSatisfiedRule());
		}
	}

	private RollupRule createSatisfiedRule() {
		RollupRule newRule = new RollupRule();
		newRule.setChildActivitySet("all");
		
		RollupCondition ruleCond1 = new RollupCondition();
		ruleCond1.setCondition("satisfied");
		newRule.addRollupCondition(ruleCond1);
		
		newRule.setRollupAction("satisfied");
		return newRule;
	}

	private RollupRule createNotSatisfiedRule() {
		RollupRule newRule = new RollupRule();
		newRule.setChildActivitySet("all");
		newRule.setConditionCombination("any");
		
		RollupCondition ruleCond1 = new RollupCondition();
		ruleCond1.setCondition("attempted");
		newRule.addRollupCondition(ruleCond1);

		RollupCondition ruleCond2 = new RollupCondition();
		ruleCond2.setCondition("satisfied");
		ruleCond2.setOperator("not");
		newRule.addRollupCondition(ruleCond2);
		
		newRule.setRollupAction("notsatisfied");
		return newRule;
	}

	private RollupRule createCompletedRule() {
		RollupRule newRule = new RollupRule();
		newRule.setChildActivitySet("all");
		
		RollupCondition ruleCond1 = new RollupCondition();
		ruleCond1.setCondition("completed");
		newRule.addRollupCondition(ruleCond1);
		
		newRule.setRollupAction("completed");
		return newRule;
	}

	private RollupRule createIncompleteRule() {
		RollupRule newRule = new RollupRule();
		newRule.setChildActivitySet("all");
		newRule.setConditionCombination("any");
		
		RollupCondition ruleCond1 = new RollupCondition();
		ruleCond1.setCondition("attempted");
		newRule.addRollupCondition(ruleCond1);

		RollupCondition ruleCond2 = new RollupCondition();
		ruleCond2.setCondition("completed");
		ruleCond2.setOperator("not");
		newRule.addRollupCondition(ruleCond2);
		
		newRule.setRollupAction("incomplete");
		return newRule;
	}

	private boolean findAction(RollupRules rollupRules, RollupAction action) {

		for (RollupRule rr: rollupRules.getRollupRules()) {
			if (rr.getRollupAction() == action)
				return true;
		}
		return false;

	}
	
	private void addPrimaryObjective(LearningActivity activity,
			Objective obj) {
		
		activity.setPrimaryObjective(createObjective(activity, obj));
		
	}

	private void addObjective(LearningActivity activity, Objective obj) {
		activity.getObjectives().add(createObjective(activity, obj));		
	}
	
	private ProxyObjective createObjective(LearningActivity activity, Objective obj) {
		GlobalObjective readNormalizedMeasure = null;
		GlobalObjective writeNormalizedMeasure = null;
		GlobalObjective readSatisfiedStatus = null;
		GlobalObjective writeSatisfiedStatus = null;
		
		for (MapInfo map:obj.getMapsInfo()) {
			
			String idGlobal = map.getTargetObjectiveID();
			GlobalObjective global = globalObjectives.get(idGlobal); 
			// se nao existe o objetivo global, entao cria agora
			if (global == null) {
				global = new GlobalObjective(idGlobal);
				globalObjectives.put(idGlobal, global);
			}
			
			if (map.isReadNormalizedMeasure())
				readNormalizedMeasure = global;
			
			if (map.isReadSatisfiedStatus())
				readSatisfiedStatus = global;
			
			if (map.isWriteNormalizedMeasure())
				writeNormalizedMeasure = global;
			
			if (map.isWriteSatisfiedStatus())
				writeSatisfiedStatus = global;
				
		}
		
		return new ProxyObjective(obj, readNormalizedMeasure, writeNormalizedMeasure, readSatisfiedStatus, writeSatisfiedStatus);
	}

	public Collection<GlobalObjective> getGlobalObjectives() {
		return this.globalObjectives.values();
	}

	public LearningActivity getCurrentActivity() {
		return this.currentActivity;
	}
	
	public void setCurrentActivity(LearningActivity activity) {
		this.currentActivity = activity;
		if (activity != null) {
			activity.restartDataModel(); // nao tirar essa linha: toda vez que uma atividade se torna ativa, precisa reiniciar alguns dados
		}
	}

	public LearningActivity getRootActivity() {
		return this.rootActivity;
	}

	public LearningActivity getSuspendActivity() {
		return this.suspendedActivity;
	}

	public void setSuspendActivity(LearningActivity activity) {
		this.suspendedActivity = activity;
	}

	public boolean contains(LearningActivity learningActivity) {
		return this.setOfActivities.contains(learningActivity);
	}

	/**
	 * 
	 * Find the common ancestor from the both activities. 
	 * 
	 * @param currentActivity
	 * @param learningActivity
	 * @return
	 */
	public LearningActivity findCommonAncestor(LearningActivity activity1, LearningActivity activity2) {
		
		// find the ancestors of the first activity
		ArrayList<LearningActivity> ancestors = new ArrayList<LearningActivity>();
		LearningActivity parent = activity1.getParent();
		while (parent != null) {
			ancestors.add(parent);
			parent = parent.getParent();
		}
		
		LearningActivity parent2 = activity2.getParent();
		while (parent2 != null) {
			if (ancestors.contains(parent2))
				return parent2;
			parent2 = parent2.getParent();
		}
			
		return null;
	}

	/**
	 * Make the activity path between the both activities, where the first is an ancestor of the second. 
	 * 
	 * @param ancestor
	 * @param currentActivity
	 * @return
	 */
	public LearningActivity[] makeActivityPath(LearningActivity ancestor, LearningActivity currentActivity) {

		ArrayList<LearningActivity> ancestors = new ArrayList<LearningActivity>();
		ancestors.add(currentActivity);
		
		LearningActivity parent = currentActivity.getParent();
		while (parent != null && parent != ancestor) {
			ancestors.add(parent);
			parent = parent.getParent();
		}
		
		// ancestor parameter isnt the ancestor of the currentActivity 
		if (parent == null)
			ancestors.clear();
		else
			ancestors.add(parent);
		
		LearningActivity[] path = new LearningActivity[ancestors.size()];
		int index = ancestors.size() - 1;
		for (LearningActivity activity:ancestors) {
			path[index] = activity;
			index--;
		}
		
		return path;
	}

	/**
	 * Make the activity path between the both activities, where the first is an ancestor of the second.
	 * The second activity is excluded from the path 
	 * 
	 * @param ancestor
	 * @param currentActivity
	 * @return
	 */
	public LearningActivity[] makeActivityPathExclusiveLast(LearningActivity activity1, LearningActivity activity2) {
		
		LearningActivity[] path = makeActivityPath(activity1, activity2);
		if (path.length > 0)
			return Arrays.copyOf(path, path.length-1);
		else
			return path;
	}
	
	public LearningActivity[] makeActivityPathExclusiveFirst(LearningActivity activity1, LearningActivity activity2) {
		LearningActivity[] path = makeActivityPath(activity1, activity2);
		if (path.length > 0)
			return Arrays.copyOfRange(path, 1, path.length);
		else
			return path;
	}

	public LearningActivity[] makeActivityPathInverseOrder(LearningActivity ancestor, LearningActivity currentActivity) {

		LearningActivity[] path = makeActivityPath(ancestor, currentActivity);
		LearningActivity[] newpath = new LearningActivity[path.length];
		for (int x = 0; x < path.length; x++)
			newpath[x] = path[path.length-x-1];
		return newpath;
		
	}

	public LearningActivity[] makeActivityPathInverseOrderExclusiveLast(LearningActivity ancestor, LearningActivity currentActivity) {
		
		LearningActivity[] path = makeActivityPathInverseOrder(ancestor, currentActivity);
		if (path.length > 0)
			return Arrays.copyOfRange(path, 1, path.length-1);
		else
			return path;
	}
	
	public LearningActivity getTargetActivity() {
		return this.targetActivity;
	}
	
	public void setTargetActivity(LearningActivity targetActivity) {
		this.targetActivity = targetActivity;
	}

	// traverse the tree (SN-C-55)
	// navega pelos irmaos. 
	private LearningActivity traverse(boolean forward, LearningActivity activity) {
		
		List<LearningActivity> brothers = activity.getParent().getAvailableChildren();
		int index = brothers.indexOf(activity);
		if (forward)
			return brothers.get(index+1);
		else
			return brothers.get(index-1);
		
	}

	public LearningActivity traverseForward(LearningActivity activity) {
		return traverse(true, activity);
	}

	public LearningActivity traverseBackward(LearningActivity activity) {
		return traverse(false, activity);
	}

	// se a atividade é a última da árvore
	public boolean isLastAvailableActivityTraverseForward(LearningActivity activity) {
		if (activity.hasChildren())
			return false;
		
		return percorrerPais(activity);
	}

	// percorre pelos pais para saber se eles sao a ultima atividade da arvore. 
	// vai subindo enquando for a ultima. quando ultrapassar a raiz, é pq realmente é a ultima
	private boolean percorrerPais(LearningActivity activity) {
		if (activity.getParent() == null)
			return true;
		
		List<LearningActivity> brothers = activity.getParent().getAvailableChildren();
		if (brothers.get(brothers.size()-1) == activity) {
			return percorrerPais(activity.getParent());
		}
		
		return false;
		
	}

	public LearningActivity getLearningActivity(String id) {
		
		return procurarLearningActivity(getRootActivity(), id);
		
	}

	private LearningActivity procurarLearningActivity(LearningActivity activity, String id) {
		if (activity.getItem().getIdentifier().equals(id))
			return activity;
		
		for (LearningActivity child:activity.getChildren()) {
			LearningActivity ret = procurarLearningActivity(child, id); 
			if (ret != null)
				return ret;
		}
			
		return null;
	}

	public LearningActivity[] makeActivityPathExclusiveLastSameDepth(LearningActivity activity1, LearningActivity activity2) {
		
		LearningActivity first, last;
		if (activity1.isForwardFrom(activity2)) {
			first = activity2;
			last = activity1;
		} else {
			first = activity1;
			last = activity2;
		}
		
		List<LearningActivity> res = new ArrayList<LearningActivity>();
		List<LearningActivity> brothers = activity1.getParent().getChildren();
		for (LearningActivity act:brothers) {
			if (act == first) {
				res.add(act);
			} else {
				if (act == last)
					return res.toArray(new LearningActivity[res.size()]);
				
				if (res.size() > 0)
					res.add(act);
					
			}
		}
		
		return new LearningActivity[]{};
	}

	public Interaction getInteraction(Integer index) {
		return currentActivity.getInteractions().get(index);
	}

	public void clearInteractions() {
		
		LearningActivity act = getRootActivity();
		
		doClearInteractions(act);
	}

	private void doClearInteractions(LearningActivity act) {
		act.clearInteractions();
		
		for (LearningActivity child:act.getChildren())
			doClearInteractions(child);	

	}

	public int getCurrActInteractionsCount() {
		return currentActivity.getInteractionsCount();
	}

	public void addCurrActInteraction(Interaction interaction) {
		currentActivity.addInteraction(interaction);
	}

	public void changeCurrActInteractionType(int n, String novoValor) {
		currentActivity.changeInteractionType(n, novoValor);	
	}

	public GlobalObjective getGlobalObjective(String objectiveID) {
		
		for (GlobalObjective g:globalObjectives.values())
			if (g.getObjectiveID().equals(objectiveID)) {
				return g;
			}
		
		return null;
	}

	//TODO: o certo é criar uma nova versao para o LearningActivity, só para versao 2004 4th
	private Map<String, String> adlcpData = new HashMap<String, String>(); 
	
	public void storeData(String targetID, String novoValor) {
		this.adlcpData.put(targetID, novoValor);
	}

	public String retrieveData(String targetID) {
		return this.adlcpData.get(targetID);	
		
	}

}
