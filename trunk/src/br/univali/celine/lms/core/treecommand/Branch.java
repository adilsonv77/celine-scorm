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
