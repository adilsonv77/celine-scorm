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
package br.univali.celine.scorm.sn.model;

//esse guarda o objetivo da atividade a quantas ele anda. 
//O outro objetivo a que esse está relacionado é o objetivo contido no XML

public class LocalObjective extends Objective {

	private br.univali.celine.scorm.model.imsss.Objective referencedObjective;

	public LocalObjective(br.univali.celine.scorm.model.imsss.Objective referencedObjective) {
		this.referencedObjective = referencedObjective;
	}
	
	public br.univali.celine.scorm.model.imsss.Objective getReferencedObjective() {
		return referencedObjective;
	}

	public String getObjectiveID() { 
		return referencedObjective.getObjectiveID();
	}
	
	public boolean isContributesToRollup() {
		return this.referencedObjective.isPrimary();
	}
	
	public boolean isSatisfiedByMeasure() {
		return this.referencedObjective.isSatisfiedByMeasure();
	}

	public double getMinNormalizedMeasure() {
		return this.referencedObjective.getMinNormalizedMeasure();
	}

	@Override
	public boolean isProgressStatus() {
		if (isSatisfiedByMeasure()) {
			if (isMeasureStatus()) // se foi atribuido um valor
				return true;
		}
		
		return super.isProgressStatus();
	}

	@Override
	public boolean isSatisfiedStatus() {
		if (isSatisfiedByMeasure()) {
			if (isMeasureStatus()) // se foi atribuido um valor
				return getNormalizedMeasure() >= getMinNormalizedMeasure();
		}
		
		return super.isSatisfiedStatus();
	}

	

	
	

}
