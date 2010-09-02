package br.univali.celine.lms.core.treecommand;

import java.util.ArrayList;

public class Tree implements NodeTree {

	private ArrayList<NodeTree> branches = new ArrayList<NodeTree>();

	
	public void addChild(NodeTree nodeTree) {
		this.branches.add(nodeTree);
	}

	public ArrayList<NodeTree> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<NodeTree> branches) {
		this.branches = branches;
	}
	
	
	
}
