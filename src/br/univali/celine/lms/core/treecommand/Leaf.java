package br.univali.celine.lms.core.treecommand;

public class Leaf {

	private String leafText, link;

	public Leaf() {} 
	public Leaf(String leafText, String link) {
		this.leafText = leafText;
		this.link = link;
	}

	public String getLeafText() {
		return leafText;
	}

	public void setLeafText(String leaveText) {
		this.leafText = leaveText;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	
}
