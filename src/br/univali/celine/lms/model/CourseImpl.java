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
package br.univali.celine.lms.model;

import br.univali.celine.lmsscorm.Course;

public class CourseImpl implements Course {

	private String folderName;
	private String title;
	private String id; // esse id identifica um id que fica dentro do ContentPackage
	private boolean removed = false;
	private boolean derived = false;
	private int idCode = 0;

	public CourseImpl(String id, String folderName, String title, boolean derived, boolean removed) {
		this.folderName = folderName;
		this.title = title;
		this.id = id;
		this.derived = derived;
		this.removed = removed;
	}
	
	public CourseImpl(int idCode, String id, String folderName, String title, boolean derived, boolean removed) {
		this(id, folderName, title, derived, removed);
		this.idCode = idCode;
	}
	

	public String getFolderName() {
		return folderName;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	
	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	
	public boolean isDerived() {
		return derived;
	}

	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	
	public String toString() {

		return folderName + " " + id + " " + title;
	}

	public int getIdCode() {
		return idCode;
	}
}
