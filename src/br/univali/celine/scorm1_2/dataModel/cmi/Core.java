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
package br.univali.celine.scorm1_2.dataModel.cmi;

import java.util.HashMap;
import java.util.Map;

import br.univali.celine.scorm.dataModel.DataModelCommand;
import br.univali.celine.scorm.dataModel.cmi.Credit;
import br.univali.celine.scorm.dataModel.cmi.DataModelBasedDotNotationCommand;
import br.univali.celine.scorm.dataModel.cmi.Entry;
import br.univali.celine.scorm.dataModel.cmi.Exit;
import br.univali.celine.scorm.dataModel.cmi.LearnerId;
import br.univali.celine.scorm.dataModel.cmi.LearnerName;
import br.univali.celine.scorm.dataModel.cmi.Location;
import br.univali.celine.scorm.dataModel.cmi.Mode;
import br.univali.celine.scorm.dataModel.cmi.Score;
import br.univali.celine.scorm.dataModel.cmi.TotalTime;
import br.univali.celine.scorm.rteApi.ErrorManager;

/**
 * SCORM 1.2 Data Model
 * 
 * Fields Implemented:
 * <ul>
 * <li>_children</li>
 * <li>student_id</li>
 * <li>student_name</li>
 * <li>lesson_location</li>
 * <li>credit</li>
 * <li>lesson_status</li>
 * <li>entry</li>
 * <li>score
 * 		<ul>
 * 			<li>_children</li>
 * 			<li>scaled</li>
 * 			<li>raw</li>
 * 			<li>min</li>
 * 			<li>max</li>
 * 		</ul>
 * </li>
 * <li>total_time</li>
 * <li>lesson_mode</li>
 * <li>exit</li>
 * <li>session_time</li>
 * </ul>
 * 
 * 
 * @author adilsonv
 *
 */
public class Core implements DataModelCommand {
	
	public static final String name = "cmi.core";
	public static final String simpleName = "core";
	
	// private DotNotationCommandManager commMan = new DotNotationCommandManager(simpleName, null);
	private Score score = new Score();
	
	private Map<String, DataModelBasedDotNotationCommand> commMan = new HashMap<String, DataModelBasedDotNotationCommand>();
	
	public Core() {
		commMan.put("student_id", new DataModelBasedDotNotationCommand(new LearnerId()));
		commMan.put("student_name", new DataModelBasedDotNotationCommand(new LearnerName()));
		commMan.put("lesson_location", new DataModelBasedDotNotationCommand(new Location()));
		commMan.put("credit", new DataModelBasedDotNotationCommand(new Credit()));
		commMan.put("lesson_status", new DataModelBasedDotNotationCommand(new LessonStatus()));
		commMan.put("entry", new DataModelBasedDotNotationCommand(new Entry()));
		commMan.put("score", null);
		commMan.put("total_time", new DataModelBasedDotNotationCommand(new TotalTime()));
		commMan.put("lesson_mode", new DataModelBasedDotNotationCommand(new Mode()));
		commMan.put("exit", new DataModelBasedDotNotationCommand(new Exit()));
		commMan.put("session_time", new DataModelBasedDotNotationCommand(new SessionTime1_2()));
	}
	
	protected String getChildren() {
		String children = "";
		for (String chave:commMan.keySet()) {
			children += chave + ", ";
		}
		return children.substring(0, children.length()-2);
	}
	
	@Override
	public String getValue(String key, ErrorManager errorManager)
			throws Exception {
		
		if (key.startsWith("score")) {
			String[] p = key.split("[.]");
			return score.getValue(p[1], errorManager);
		}
		
		if (key.equals("_children"))
			return getChildren();
		
		DataModelBasedDotNotationCommand dmbdnc = commMan.get(key);
		if (dmbdnc == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return "";
		}
		return dmbdnc.getValue(errorManager, 0, 0);
	}

	@Override
	public boolean setValue(String key, String newValue,
			ErrorManager errorManager) throws Exception {
		
		if (key.equals("_children")) {
			errorManager.attribError(ErrorManager.DataModelElementIsReadOnly);
			return false;
		}
			
		if (key.startsWith("score")) {
			String[] p = key.split("[.]");
			return score.setValue(p[1], newValue, errorManager);
		}
		
		DataModelBasedDotNotationCommand dmbdnc = commMan.get(key);
		if (dmbdnc == null) {
			errorManager.attribError(ErrorManager.UndefinedDataModelElement);
			return false;
		}
		
		return dmbdnc.setValue(errorManager, 0, 0, newValue);
	}

	@Override
	public void clear(ErrorManager errorManager) throws Exception {
		
	}

	@Override
	public void initialize(ErrorManager errorManager) {
		
	}

}
