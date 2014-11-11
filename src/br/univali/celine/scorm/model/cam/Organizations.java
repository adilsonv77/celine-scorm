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
package br.univali.celine.scorm.model.cam;

import java.util.LinkedList;
import java.util.List;

public class Organizations {

	private String defaultOrg;

	public String getDefaultOrg() {
		return defaultOrg;
	}

	public void setDefaultOrg(String defaultOrg) {
		this.defaultOrg = defaultOrg;
	}
	
	private List<Organization> organizacoes = new LinkedList<Organization>();
	
	public void addOrganization(Organization organization) {
		organizacoes.add(organization);
	}
	
	public String toString() {
		String ret = "<organizations default=\"" + defaultOrg + "\">\n";
		for (Organization org:organizacoes)
			ret += org + "\n";
		
		return ret+"</organizations>";
	}

	public Organization getDefaultOrganization() {
		for (Organization org:organizacoes) {
			if (org.getIdentifier().equals(this.defaultOrg))
				return org;
		}
		
		return null;
	}
	
	private int idOrg = -1;
	
	public void initIteration() {
		this.idOrg = -1;
	}
	
	public Organization nextContentOrganization() {
		
		if (idOrg == -1) {
			idOrg = 0; // default organization tem que ser a primeira
		} else {
			idOrg++;
		}
		
		return organizacoes.get(idOrg);
	}

	public void finalization() {

		// coloque o default organization por primeiro
		Organization defOrg = getDefaultOrganization();
		organizacoes.remove(defOrg);
		organizacoes.add(0, defOrg);
	}
		
}
