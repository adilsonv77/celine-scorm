package br.univali.celine.scorm.dataModel.cmi;

import java.lang.reflect.Constructor;
import java.util.List;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;
import br.univali.celine.scorm.sn.model.Comment;
import br.univali.celine.scorm.sn.model.dataTypes.LocalizedStringType;
import br.univali.celine.scorm.sn.model.dataTypes.TimeSecond10_0;

public abstract class CommentsManager implements DataModelCommand {

	public enum Fields {
		comment, location, timestamp
	}

	private DotNotationCommandManager commMan = new DotNotationCommandManager(
			getSimpleName(), new CommentsCount());

	public CommentsManager(Class<? extends TrataComment> clazz) {
		Constructor<? extends TrataComment> constr = null;

		try {
			constr = clazz.getConstructor(this.getClass(), Fields.class);
		} catch (Exception e) {
			try {
				constr = clazz.getConstructor(this.getClass().getSuperclass(), Fields.class);
			} catch (Exception e1) {
				try {
					constr = clazz.getConstructor(this.getClass().getSuperclass().getSuperclass(), Fields.class); // i´m hating this solution :(
				} catch (Exception e2) {
					e2.printStackTrace();
					// ai fodeu !!!
					return;
				}
			}
		}

		try {
			commMan.add("comment", constr.newInstance(this, Fields.comment));
			commMan.add("location", constr.newInstance(this, Fields.location));
			commMan.add("timestamp", constr.newInstance(this, Fields.timestamp));
		} catch(Exception e) {
			
		}
	}

	public void clear(ErrorManager errorManager) throws Exception {
	}

	public void initialize(ErrorManager errorManager) {
	}

	protected abstract String getSimpleName();

	protected abstract List<Comment> getSource(ErrorManager errorManager);

	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		return commMan.getValue(key, errorManager);
	}

	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {

		return commMan.setValue(key, newValue, errorManager);
	}

	private class CommentsCount implements DotNotationCommand {

		public String getValue(ErrorManager errorManager, int n, int size) {
			return String.valueOf(getSource(errorManager).size());
		}

		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}

	}

	public class TrataComment implements DotNotationCommand {

		private Fields field;

		public TrataComment(Fields field) {
			this.field = field;
		}

		public String getValue(ErrorManager errorManager, int n, int size) {

			if (n >= size) {
				errorManager.attribError(ErrorManager.GeneralGetFailure);
				return "";
			}

			String result = null;
			switch (field) {
			case comment:
				result = getSource(errorManager).get(n).getComment();
				break;

			case location:
				result = getSource(errorManager).get(n).getLocation();
				break;

			case timestamp:
				result = getSource(errorManager).get(n).getTimeStamp();
				break;

			}

			if (result == null) {
				result = "";
				errorManager
						.attribError(ErrorManager.DataModelElementValueNotInitialized);

			}

			return result;

		}

		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {

			if (n > size) {
				errorManager.attribError(ErrorManager.GeneralSetFailure);
				return false;
			}

			Comment comment;
			if (n < size) {
				comment = getSource(errorManager).get(n);
			} else {
				comment = new Comment();

				getSource(errorManager).add(comment);
			}

			switch (field) {
			case comment:
				if (!LocalizedStringType.validate(novoValor)) {
					errorManager
							.attribError(ErrorManager.DataModelElementTypeMismatch);
					return false;
				}
				comment.setComment(novoValor);
				break;

			case location:
				comment.setLocation(novoValor);
				break;

			case timestamp:
				if (!TimeSecond10_0.validate(novoValor)) {
					errorManager
							.attribError(ErrorManager.DataModelElementTypeMismatch);
					return false;

				}
				comment.setTimeStamp(novoValor);
			}

			return true;
		}

	}
}
