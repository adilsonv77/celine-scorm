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

public class Objective {

	private boolean satisfiedByMeasure = false;
	private String objectiveID;
	private double minNormalizedMeasure = 1;
	private List<MapInfo> mapsInfo = new LinkedList<MapInfo>();
	private boolean primary;

	public Objective() {}
	public Objective(String objectiveID) {
		this.objectiveID = objectiveID;
	}
	
	public void addMapInfo(MapInfo mapInfo) {
		mapsInfo.add(mapInfo);
	}
	public  List<MapInfo> getMapsInfo() {
		return this.mapsInfo;
	}
	public String getObjectiveID() {
		return objectiveID;
	}
	public void setObjectiveID(String objectiveID) {
		this.objectiveID = objectiveID;
	}
	public boolean isSatisfiedByMeasure() {
		return satisfiedByMeasure;
	}
	public void setSatisfiedByMeasure(boolean satisfiedByMeasure) {
		this.satisfiedByMeasure = satisfiedByMeasure;
	}
	public double getMinNormalizedMeasure() {
		return minNormalizedMeasure;
	}
	// nao entendi porque tive que fazer isso, mas foi a unica forma de resolver o problema de mapeamento
	public void setMinNormalizedMeasure(String minNormalizedMeasure) {
		this.minNormalizedMeasure = Double.parseDouble(minNormalizedMeasure);
	}
	public String toString() {
		
		String id = getObjectiveID();
		if (id == null)
			id = "";
		else
			id = "objectiveID=\""+id+"\"";
		
		String label = "";
		if (isPrimary())
			label = "primaryObjective";
		else
			label = "objective";
		
		String ret = "<imsss:" + label + " satisfiedByMeasure=\""+isSatisfiedByMeasure()+"\" " + id + ">\n";
		ret += "<imsss:minNormalizedMeasure>"+minNormalizedMeasure+"</imsss:minNormalizedMeasure>\n";
		for (MapInfo map:mapsInfo) {
			ret += map;
		}
		return ret + "</imsss:"+label+">\n";
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	public boolean isPrimary() {
		return primary;
	}
	
}
