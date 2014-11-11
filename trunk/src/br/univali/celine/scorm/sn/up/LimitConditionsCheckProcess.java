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
package br.univali.celine.scorm.sn.up;

import br.univali.celine.scorm.sn.model.LearningActivity;

// [UP.1]
public class LimitConditionsCheckProcess {

	public boolean run(LearningActivity activity) {

		// if the activity is not tracked, its limit conditions cannot be violated
		if (activity.isTracked() == false) {
			return false; // activity is not tracked, no limit conditions can be violated
		}
		
		// only need to check activities that will begin a new attempt
		if (activity.isActive() == true || activity.isSuspended() == true) {
			return false;
		}
		
		if (activity.isLimitConditionAttemptControl() == true) {
			
			if (activity.isProgressStatus() == true &&
					activity.getAttemptCount() >= activity.getLimitConditionAttemptLimit()) {
				return true; // limit conditions have been violated
			}
			
		}
		
		// below is optional code, but implemented ;)

		if (activity.isLimitConditionAbsoluteDurationControl() == true) {

			if (activity.isProgressStatus() == true &&
					activity.getActivityAbsoluteDuration() >= activity.getLimitConditionAbsoluteDurationLimit()) {
				return true; // limit conditions have been violated
			}
			
		}
		
		
		// abaixo daqui faltam informacoes de onde posso pegar. Provavelmente no CAM, mas lá também nao está definido ainda
		// por essa razao, vou deixar em comentario para futuramente liberar qdo a ADL tambem liberar
		/*
		if (activity.isLimitConditionExperiencedDurationControl() == true) {
			
			if (activity.isProgressStatus() == true &&
					activity.getExperiencedDuration() >= activity.getLimitConditionExperiencedDurationLimit()) {
				return true; // limit conditions have been violated
			}
			
		}
		
		if (activity.isLimitConditionAttemptAbsoluteDurationControl() == true) {
			
			if (activity.isProgressStatus() == true && 
					activity.getAttemptAbsoluteDuration() >= activity.getLimitConditionAttemptAbsoluteDurationLimit()) {
				return true; // limit conditions have been violated
			}
			
		}
		
		if (activity.isLimitConditionAttemptExperiencedDurationControl() == true) {
			
			if (activity.isProgressStatus() == true &&
					activity.getAttemptExperiencedDuration() >= activity.getLimitConditionAttemptExperiencedDurationLimit()) {
				return true; // limit conditions have been violated
			}
			
		}
		
		if (activity.isLimitConditionBeginTimeLimitControl() == true) {
			
				if (activity.getCurrentTimePoint() < activity.getLimitConditionBeginTimeLimit())
			
						return true; // limit conditions have been violated
			
		
		}
		
		if (activity.isLimitConditionEndTimeLimitControl() == true) {
			
			if (activity.getCurrentTimePoint() > activity.getLimitConditionEndTimeLimit())
				return true; // limit conditions have been violated
		}
	
		 */
		return false;
	}

}
