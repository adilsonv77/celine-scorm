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

public class CompletionThreshold {

	private boolean completedByMeasure = false;
	private double minProgressMeasure = 1.0;
	private double progressWeight = 1.0;
	
	public boolean isCompletedByMeasure() {
		return completedByMeasure;
	}
	public void setCompletedByMeasure(boolean completedByMeasure) {
		this.completedByMeasure = completedByMeasure;
	}
	public double getMinProgressMeasure() {
		return minProgressMeasure;
	}
	public void setMinProgressMeasure(double minProgressMeasure) {
		this.minProgressMeasure = minProgressMeasure;
	}
	public double getProgressWeight() {
		return progressWeight;
	}
	public void setProgressWeight(double progressWeight) {
		this.progressWeight = progressWeight;
	}
	
	



	
}
