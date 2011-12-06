package br.univali.celine.scorm.dataModel.cmi;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.rteApi.ErrorManager;

/**
 * SCORM 1.2 Data Model
 * 
 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>mastery_score</li>
 * <li>max_time_allowed</li>
 * <li>time_limit_action</li>
 * </ul>
 * 
 * 
 * @author adilsonv
 *
 */
public class StudentData implements DataModelCommand {

	public static final String name = "cmi.student_data";
	public static final String simpleName = "student_data";
	
	private DotNotationCommandManager commMan = new DotNotationCommandManager(
			simpleName, null);
	
	public StudentData() {
		commMan.add("mastery_score", new MasteryScore());
		commMan.add("max_time_allowed", new DataModelBasedDotNotationCommand(new MaxTimeAllowed()));
		commMan.add("time_limit_action", new DataModelBasedDotNotationCommand(new TimeLimitAction()));
	}
	
	@Override
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		return commMan.getValue(key, errorManager);
	}

	@Override
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		return commMan.setValue(key, newValue, errorManager);
	}


	@Override
	public void clear(ErrorManager errorManager) throws Exception {}

	@Override
	public void initialize(ErrorManager errorManager) {}

	public class MasteryScore implements DotNotationCommand {

		@Override
		public String getValue(ErrorManager errorManager, int n, int size) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean setValue(ErrorManager errorManager, int n, int size,
				String novoValor) {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
