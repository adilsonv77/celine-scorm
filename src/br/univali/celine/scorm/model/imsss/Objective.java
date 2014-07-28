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
