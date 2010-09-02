package br.univali.celine.lms.core.treecommand;

import java.util.ArrayList;

public class Branch implements NodeTree {

	private String branchText;
	private String id;
	
	private ArrayList<Leaf> leaves = new ArrayList<Leaf>();
	private ArrayList<NodeTree> branches = new ArrayList<NodeTree>();

	public Branch() {
	}

	public Branch(String id, String branchText) {
		this.branchText = branchText;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBranchText() {
		return branchText;
	}

	public void setBranchText(String branchText) {
		this.branchText = branchText;
	}

	public ArrayList<Leaf> getLeaves() {
		return leaves;
	}

	public void setLeaves(ArrayList<Leaf> leaves) {
		this.leaves = leaves;
	}

	
	public void addChild(NodeTree nodeTree) {
		this.branches.add(nodeTree);
	}

	public ArrayList<NodeTree> getBranches() {
		return branches;
	}
	
	
	
}
