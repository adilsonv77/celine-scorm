package br.univali.celine.scorm.dataModel.cmi;

// SCORM 1.2
public class Comments extends CommentsFromLearner {

	public static final String name = "cmi.comments";
	private static final String simpleName = "comments";
	
	@Override
	protected String getSimpleName() {
		return simpleName;
	}

}
