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
package br.univali.celine.scorm.model.imsss;

import java.util.LinkedList;
import java.util.List;

public class Objectives {

	private List<Objective> objectives = new LinkedList<Objective>(); 
	
	public void addObjective(Objective obj) {
		objectives.add(obj);
	}
	
	public List<Objective> getObjectives() { return objectives; }
	
	private Objective primaryObjective;
	
	public Objective getPrimaryObjective() {
		if (primaryObjective == null) {
			if (objectives.size() == 0)
				primaryObjective = new Objective();
			else
				primaryObjective = objectives.get(0);
			primaryObjective.setPrimary(true);
		}
			
		return primaryObjective;
	}

	public void setPrimaryObjective(Objective primaryObjective) {
		this.primaryObjective = primaryObjective;
		primaryObjective.setPrimary(true);
	}

	public String toString() {
		String ret = "<imsss:objectives>\n";
		if (primaryObjective != null) {
			ret += primaryObjective; 
		}
		for (Objective obj:objectives) {
			ret += obj;
		}
		return ret + "</imsss:objectives>\n";
		
	}

	public void assign(Objectives objectives2) {
		// TODO Auto-generated method stub
		
	}
}
