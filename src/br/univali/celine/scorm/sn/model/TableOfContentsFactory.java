package br.univali.celine.scorm.sn.model;

import java.util.List;

import br.univali.celine.scorm.model.imsss.RuleAction;
import br.univali.celine.scorm.model.imsss.SequencingRule;
import br.univali.celine.scorm.sn.ProcessProvider;
import br.univali.celine.scorm.sn.up.SequencingRulesCheckProcess;

public class TableOfContentsFactory {

	private TableOfContentsFactory() {}
	
	public static TableOfContents build(ActivityTree tree) {
		return new TableOfContentsFactory().doBuild(tree);
	}
	
	private TableOfContents toc;
	private ActivityTree tree;
	private LearningActivity backAct;
	private LearningActivity forwAct;
	private LearningActivity constrainedChoice;

	private TableOfContents doBuild(ActivityTree tree) {
		
		this.toc = new TableOfContents();
		this.tree = tree;
		this.backAct = null;
		this.forwAct = null;
		this.constrainedChoice = null;
		
		prepareForConstrainedChoice(); // see session 3.3.1
		
		traverseTree(); // criar todo o TOC baseado na Activity Tree
		
		return this.toc;
		
	}

	/*
	 * Se a atividade corrente estah num cluster com ConstrainedChoice = true, entao somente podem estar disponiveis
	 * para selecao as atividades anterior e posterior.
	 * 
	 */
	private void prepareForConstrainedChoice() {
		LearningActivity[] path = tree.makeActivityPathInverseOrderExclusiveLast(tree.getRootActivity(), tree.getCurrentActivity());
		
		for (LearningActivity act:path) {
			if (act.isConstrainedChoice()) {
				ProcessProvider pp = ProcessProvider.getInstance();
				
				LearningActivity actFlow;
				
				actFlow = pp.getChoiceFlowSubprocess().run(tree, act, TraversalDirection.backward);
				if (actFlow != act)
					backAct = actFlow;
				else
					backAct = null;
				
				actFlow = pp.getChoiceFlowSubprocess().run(tree, act, TraversalDirection.forward);
				if (actFlow != act)
					forwAct = actFlow;
				else
					forwAct = null;
					
				this.constrainedChoice = act;
				return;
			}
		}
	}
	
	private void traverseTree() {
		
		traverseNode(tree.getRootActivity(), null);
		
	}

	private enum NodeStatus {
		notVisible, enabled, notEnabled 
	}
	
	private void traverseNode(LearningActivity activity, NodeTableOfContent lastNode) {
		NodeStatus status = evaluate(activity, lastNode);
			
		NodeTableOfContent newNode = new NodeTableOfContent(
				activity.getItem().getIdentifier(),
				activity.getItem().getTitle(),
				status == NodeStatus.enabled, 
				status != NodeStatus.notVisible); 
		
		if (tree.getCurrentActivity() == activity)
			newNode.setActive(true);
		
		if (lastNode == null)
			toc.setRoot(newNode);
		else
			lastNode.addChild(newNode);
	
		for (LearningActivity child:activity.getAvailableChildren()) {
			traverseNode(child, newNode);
		}
	}

	// sessão 3.2.1... do SN Book:
	/*
	 * Valid targets of Choice navigation
	 * requests can be conditionally constrained by applying the Sequencing Control Choice
	 * Exit element (refer to Section 3.2.2: Sequencing Control Choice Exit), elements of the
	 * Constrained Choice Controls (refer to Section 3.3: Constrain Choice Controls) or a
	 * Hidden From Choice Pre Condition Sequencing Rule (refer to Section 3.4: Sequencing
	 * Rule Description).
	 */

	// procure pelas sessões comentadas nessas linhas que darão uma explicacao legal sobre como 
	// .. implementar o método abaixo
	
	private NodeStatus evaluate(LearningActivity activity, NodeTableOfContent parentNode) {
		// raiz sempre é válida: a partir de 12/09/12 verificou-se que nao eh por ai
		/*
		if (activity.getParent() == null) // || activity == tree.getCurrentActivity())
			return NodeStatus.enabled;
		*/
		// Sequencing Control Choice
		/*
		if (sequencingControlChoice(activity) == false)
			return NodeStatus.notVisible;
		*/
		// Sequencing Control Choice Exit
		if (sequencingControlChoiceExit(activity) == false) {
			return NodeStatus.notVisible;
		}
		
		// Sequencing Rule Description
		if (sequencingRule(activity) == false) {
			return NodeStatus.notVisible;
		}
		
		if (activity.getParent() != null) {
			// Sequencing Control Choice
			// se o pai diz que nao pode selecionar os filhos
			if (activity.getParent().isSequencingControlChoice() == false) 
				return NodeStatus.notEnabled;
		
			// Constrain Choice Controls
			if (constrainChoiceControls(activity) == false) 
				return NodeStatus.notEnabled;
		}
		
		return NodeStatus.enabled;
	}

	@SuppressWarnings("unused")
	private boolean sequencingControlChoice(LearningActivity activity) {
		
		// The Sequencing Control Choice control mode has no affect when defined on a leaf activity.
		if (!activity.isLeaf()) {
			return false;
		}

		return true;
	}

	private boolean sequencingControlChoiceExit(LearningActivity activity) {
		if (tree.getCurrentActivity().isSequencingControlChoiceExit() == false) {
			
			if (tree.getCurrentActivity() != activity) {
				
				if (activity.isDescendantFrom(tree.getCurrentActivity()) == false)
					return false;
			} else {
				if (!activity.hasChildren())
					return false;
			}
		
		}
		return true;
	}

	private boolean constrainChoiceControls(LearningActivity activity) {
		// Constrain Choice
		if (this.constrainedChoice != null) {
			if (activity != this.constrainedChoice && activity != backAct && activity != forwAct && activity.isDescendantFrom(this.constrainedChoice) == false)
				return false;
		}
		
		// Prevent Activation
		if (activity.getParent().isPreventActivation() && (activity.getParent().isActive() == false))
			return false;
		
		return true;
	}

	private boolean sequencingRule(LearningActivity activity) {

		SequencingRulesCheckProcess srcp = ProcessProvider.getInstance().getSequencingRulesCheckProcess();
		List<SequencingRule> hideFromChoice = activity.getHideFromChoiceSequencingRules();
		RuleAction ra = srcp.run(activity, hideFromChoice);
		if (ra == null) // nenhuma condicao ativada
			return true;
		
		return false;
	}

}
