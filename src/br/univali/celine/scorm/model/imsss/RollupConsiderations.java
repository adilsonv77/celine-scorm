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

public class RollupConsiderations {

	private RequiredForRollupConsiderations requiredForSatisfied = RequiredForRollupConsiderations.always;
	private RequiredForRollupConsiderations requiredForNotSatisfied = RequiredForRollupConsiderations.always;
	private RequiredForRollupConsiderations requiredForCompleted = RequiredForRollupConsiderations.always;
	private RequiredForRollupConsiderations requiredForIncomplete = RequiredForRollupConsiderations.always;
	private boolean measureSatisfactionIfActive = true;
	
	public String getRequiredForSatisfied() {
		return requiredForSatisfied.name();
	}
	public void setRequiredForSatisfied(String requiredForSatisfied) {
		this.requiredForSatisfied = RequiredForRollupConsiderations.valueOf(requiredForSatisfied);
	}
	public String getRequiredForNotSatisfied() {
		return requiredForNotSatisfied.name();
	}
	public void setRequiredForNotSatisfied(String requiredForNotSatisfied) {
		this.requiredForNotSatisfied = RequiredForRollupConsiderations.valueOf(requiredForNotSatisfied);
	}
	public String getRequiredForCompleted() {
		return requiredForCompleted.name();
	}
	public void setRequiredForCompleted(String requiredForCompleted) {
		this.requiredForCompleted = RequiredForRollupConsiderations.valueOf(requiredForCompleted);
	}
	public String getRequiredForIncomplete() {
		return requiredForIncomplete.name();
	}
	public void setRequiredForIncomplete(String requiredForIncomplete) {
		this.requiredForIncomplete = RequiredForRollupConsiderations.valueOf(requiredForIncomplete);
	}
	public boolean isMeasureSatisfactionIfActive() {
		return measureSatisfactionIfActive;
	}
	public void setMeasureSatisfactionIfActive(boolean measureSatisfactionIfActive) {
		this.measureSatisfactionIfActive = measureSatisfactionIfActive;
	}
	public RequiredForRollupConsiderations getEnumRequiredForSatisfied() {
		return this.requiredForSatisfied;
	}
	public RequiredForRollupConsiderations getEnumRequiredForNotSatisfied() {
		return this.requiredForSatisfied;
	}
	public RequiredForRollupConsiderations getEnumRequiredForCompleted() {
		return this.requiredForSatisfied;
	}
	public RequiredForRollupConsiderations getEnumRequiredForIncomplete() {
		return this.requiredForSatisfied;
	}

	@Override
	public String toString() {
		
		return String.format("<adlseq:rollupConsiderations requiredForSatisfied=\"%s\" " +
								                   "requiredForNotSatisfied=\"%s\" " +
								                   "requiredForCompleted=\"%s\" " +
								                   "requiredForIncomplete=\"%s\" " +
								                   "measureSatisfactionIfActive=\"%s\"/>\n",
				  new Object[]{requiredForSatisfied, requiredForNotSatisfied, 
							   requiredForCompleted, requiredForIncomplete, 
							   measureSatisfactionIfActive});
	}
	public void assign(RollupConsiderations rollupConsiderations) {
		// TODO Auto-generated method stub
		
	}
	
}
