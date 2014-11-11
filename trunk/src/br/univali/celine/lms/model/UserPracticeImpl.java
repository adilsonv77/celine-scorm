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

import java.util.Date;

import br.univali.celine.lmsscorm.User;
import br.univali.celine.lmsscorm.UserPractice;

public class UserPracticeImpl implements UserPractice {

	private User user;
	private int quantity;
	private double time;
	private Date lastTimeAccessed;

	public int getQuantity() {
		return quantity;
	}

	public double getTime() {
		return time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	
	public void setLastTimeAccessed(Date lastTimeAccessed) {
		this.lastTimeAccessed = lastTimeAccessed;
	}

	public Date getLastTimeAccessed() {
		return lastTimeAccessed;
	}

}
