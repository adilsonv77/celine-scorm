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

public class DeliveryControls {

	private boolean tracked = true;
	private boolean completionSetByContent = false;
	private boolean objectiveSetByContent = false;
	
	public boolean isTracked() {
		return tracked;
	}
	public void setTracked(boolean tracked) {
		this.tracked = tracked;
	}
	public boolean isCompletionSetByContent() {
		return completionSetByContent;
	}
	public void setCompletionSetByContent(boolean completionSetByContent) {
		this.completionSetByContent = completionSetByContent;
	}
	public boolean isObjectiveSetByContent() {
		return objectiveSetByContent;
	}
	public void setObjectiveSetByContent(boolean objectiveSetByContent) {
		this.objectiveSetByContent = objectiveSetByContent;
	}
	@Override
	public String toString() {
		
		return  String.format("<imsss:deliveryControls tracked=\"%s\" completionSetByContent=\"%s\" objectiveSetByContent=\"%s\"/>\n", 
							  new Object[]{tracked, completionSetByContent, objectiveSetByContent});
		
	}
	public void assign(DeliveryControls deliveryControls) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
