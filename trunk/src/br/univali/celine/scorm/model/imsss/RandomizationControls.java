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

public class RandomizationControls {

	private RandomizationTiming randomizationTiming = RandomizationTiming.never;
	private int selectCount;
	private boolean reorderChildren;
	private SelectionTiming selectionTiming = SelectionTiming.never;
	
	public String getRandomizationTiming() {
		return randomizationTiming.name();
	}
	public void setRandomizationTiming(String randomizationTiming) {
		this.randomizationTiming = RandomizationTiming.valueOf(randomizationTiming);
	}
	public RandomizationTiming getEnumRandomizationTiming() {
		return this.randomizationTiming;
	}
	public int getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}
	public boolean isReorderChildren() {
		return reorderChildren;
	}
	public void setReorderChildren(boolean reorderChildren) {
		this.reorderChildren = reorderChildren;
	}
	public String getSelectionTiming() {
		return selectionTiming.name();
	}
	public void setSelectionTiming(String selectionTiming) {
		this.selectionTiming = SelectionTiming.valueOf(selectionTiming);
	}
	public SelectionTiming getEnumSelectionTiming() {
		return this.selectionTiming;
	}
	
	@Override
	public String toString() {
		
		return String.format("<imsss:randomizationControls randomizationTiming=\"%s\" " +
													"selectCount=\"%s\" " +
													"reorderChildren=\"%s\" " +
													"selectionTiming=\"%s\"/>\n", 
				new Object[]{getRandomizationTiming(), getSelectCount(), isReorderChildren(), getSelectionTiming()});
		
	}
	public void assign(RandomizationControls randomizationControls) {
		
		setRandomizationTiming(randomizationControls.getRandomizationTiming());
		setSelectionTiming(randomizationControls.getSelectionTiming());
		setReorderChildren(randomizationControls.isReorderChildren());
		setSelectCount(randomizationControls.getSelectCount());
		
	}
	
	
	
}
