package br.univali.celine.scorm.dataModel.cmi;

import java.util.List;

import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.Comment;

/**
 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>_count</li>
 * <li>n.comment</li>
 * <li>n.location</li>
 * <li>n.timestamp</li>
 * 
 */
public class CommentsFromLMS extends CommentsManager {

	public class TrataCommentFromLMS extends TrataComment {

		public TrataCommentFromLMS(Fields field) {
			super(field);
		}

		@Override
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			
			return false;
		}
		
	}

	public static final String name = "cmi.comments_from_lms";
	private static final String simpleName = "comments_from_lms";

	public CommentsFromLMS() {
		super(TrataCommentFromLMS.class);
	}

	@Override
	protected String getSimpleName() {
		return simpleName;
	}

	@Override
	protected List<Comment> getSource(ErrorManager errorManager) {
		return errorManager.getTree().getCurrentActivity().getCommentsFromLMS();
	}
	
}
