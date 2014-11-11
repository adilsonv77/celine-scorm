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
