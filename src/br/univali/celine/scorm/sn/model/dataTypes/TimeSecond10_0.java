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
package br.univali.celine.scorm.sn.model.dataTypes;

public class TimeSecond10_0 {

	private static boolean tratarTime(String time) {

		String sHour = time;
		if (time.contains(":")) {
			sHour = time.substring(0, time.indexOf(":"));
			time = time.substring(time.indexOf(":"));
		} else {
			time = "";
		}

		if (sHour.length() != 2)
			return false;
		
		int hour = Integer.parseInt(sHour);
		if (hour < 0 || hour > 23)
			return false;
		
		if (time.length() > 0) {
			time = time.substring(1);
			String sMin = time;
			if (time.contains(":")) {
				sMin = time.substring(0, time.indexOf(":"));
				time = time.substring(time.indexOf(":"));
			} else {
				time = "";
			}
			if (sMin.length() != 2)
				return false;
			
			int min = Integer.parseInt(sMin);
			if (min < 0 || min > 59)
				return false;
			
			if (time.length() > 0) {
				time = time.substring(1);
				int posT = time.indexOf("Z");
				if (posT == -1) {
					posT = time.indexOf("+");
					if (posT == -1) {
						posT = time.indexOf("-");
					}
				}

				String timeZone = "";
				if (posT != -1) {
					timeZone = time.substring(posT);
					time = time.substring(0, posT);
				}

				int posPto = time.indexOf(".");
				String sec = time;
				if (posPto != -1) {
					sec = sec.substring(0, posPto);
				}
				if (sec.length() != 2)
					return false;
				int isec = Integer.parseInt(sec);
				if (isec < 0 || isec > 59)
					return false;
				
				if (posPto != -1) {
					String frac = time.substring(posPto+1);
					
					if (frac.length() > 2) {
						return false;
					}
					Integer.parseInt(frac);
				} else {
					if (timeZone.length() > 0)
						return false;
				}
				
				if (timeZone.length() > 0) {
					if (timeZone.charAt(0) == 'Z') {
						return timeZone.length() == 1;
					}
					timeZone = timeZone.substring(1);
					String shour = timeZone; 
					if (shour.contains(":")) {
						shour = shour.substring(0, shour.indexOf(":"));
						timeZone = timeZone.substring(timeZone.indexOf(":"));
					} else {
						timeZone = "";
					}
					
					if (shour.length() != 2)
						return false;
					hour = Integer.parseInt(shour);
					if (hour < 0 || hour > 23)
						return false;
					
					if (timeZone.length() > 0) {
						timeZone = timeZone.substring(1);
						if (timeZone.length() != 2)
							return false;
						min = Integer.parseInt(timeZone);
						if (min < 0 || min > 59)
							return false;
					}
				}
			}
		}
		
		return true;
	}


	public static boolean validate(String novoValor) {
		// YYYY[-MM[-DD[Thh[:mm[:ss[.s[TZD]]]]]]]
		
		if (novoValor.trim().length() == 0)
			return false;
		
		String sYear = novoValor;
		if (novoValor.contains("-")) {
			sYear = novoValor.substring(0, novoValor.indexOf("-"));
			novoValor = novoValor.substring(novoValor.indexOf("-"));
		}
		else
			novoValor = "";
		
		try {
			if (sYear.length() != 4)
				return false;
			int year = Integer.parseInt(sYear);
			if (year < 1970 || year > 2038)
				return false;

			if (novoValor.length() > 0) {
				novoValor = novoValor.substring(1);
				String sMonth = novoValor;
				if (novoValor.contains("-")) {
					sMonth = novoValor.substring(0, novoValor.indexOf("-"));
					novoValor = novoValor.substring(novoValor.indexOf("-"));
				} else
					novoValor = "";
				if (sMonth.length() != 2) {
					return false;
				}
				int month = Integer.parseInt(sMonth);
				if (month < 1 || month > 12)
					return false;
				
				if (novoValor.length() > 0) {
					novoValor = novoValor.substring(1);
					String sDay = novoValor;
					if (novoValor.contains("T")) {
						sDay = novoValor.substring(0, novoValor.indexOf("T"));
						novoValor = novoValor.substring(novoValor.indexOf("T"));
					} else {
						novoValor = "";
					}
					if (sDay.length() != 2)
						return false;
					
					int iday = Integer.parseInt(sDay);
					if (iday > 31 || iday <= 0)
						return false;
					else
						if (iday > 28) {
							if (iday == 29 && month == 2) {
								if (year % 4 != 0) {
									return false;
								}
							} else {
								if (iday == 31) {
									if (month == 4 || month == 6 || month == 9 || month == 11) {
										return false;
									}
								} 
							}
							
							
						}
					
					if (novoValor.length() > 0) {
						return tratarTime(novoValor.substring(1));
					}
				}
			} 
			
			
		} catch (Exception e) {
			return false;
		}
		 
		return true;
	}

	
	public static void main(String[] args) {
		
		System.out.println(validate("2005"));
		System.out.println(validate("2005-07"));
		System.out.println(validate("2005-07-10"));
		System.out.println(validate("2005-07-25T03:00:00"));
		System.out.println(validate("2005-07-25T03:00:00.2-03:10"));
		System.out.println(validate("2005-07-25T03:30:35.2+05"));
		System.out.println(validate("2005-07-25T03:00:00.2Z"));
		System.out.println(validate("2005-04-01T15:45:58.25Z"));		
		System.out.println(validate("2005-04-01T15:45:58.25+02"));		
		System.out.println(validate("2005-04-01T15:45:58.25+02:30"));		
		System.out.println(validate("2005-04-01T15:45:58.25-02:30"));		
		System.out.println(validate("2005-04-01T15:45:58.25-02"));		
		
		System.out.println("TUDO FALSE");
		
		System.out.println(validate(""));
		System.out.println(validate("4"));
		System.out.println(validate("04"));
		System.out.println(validate("005"));
		System.out.println(validate("22005"));
		
		System.out.println(validate("2005-"));
		System.out.println(validate("2005-4"));
		System.out.println(validate("2005-003"));
		System.out.println(validate("2005-04-"));
		System.out.println(validate("2005-04-00"));
		System.out.println(validate("2005-04-32"));
		System.out.println(validate("2005-04-1"));
		System.out.println(validate("2005-04-001"));
		System.out.println(validate("2005-04-01T"));		
		System.out.println(validate("2005-04-01T9"));		
		System.out.println(validate("2005-04-01T015"));		
		System.out.println(validate("2005-04-01T15+03"));		
		System.out.println(validate("2005-04-01T15+03:30"));		
		System.out.println(validate("2005-04-01T15-03"));		
		System.out.println(validate("2005-04-01T15-03:30"));		
		System.out.println(validate("2005-04-01T015:"));		
		System.out.println(validate("2005-04-01T15:4"));		
		System.out.println(validate("2005-04-01T15:045"));		
		System.out.println(validate("2005-04-01T15:45+03"));		
		System.out.println(validate("2005-04-01T15:45Z"));		
		System.out.println(validate("2005-04-01T15:45:8"));		
		System.out.println(validate("2005-04-01T15:45:580"));		
		System.out.println(validate("2005-04-01T15:45:58."));		
		System.out.println(validate("2005-04-01T15:45:58.025"));		
		System.out.println(validate("2005-04-01T15:45:58.25+02:"));		
		System.out.println(validate("2005-04-01T15:45:58.25+02:3"));		
		System.out.println(validate("2005-04-01T15:45:58.25+02:03.20"));		
		System.out.println(validate("2005-04-01T15:45:58.25Z+02:30"));		
		System.out.println(validate("2005-04-01T15:45:58.25-02:"));		
		System.out.println(validate("2005-04-01T15:45:58.25-02:3"));		
		System.out.println(validate("2005-04-01T15:45:58.25-02:03.20"));		
		System.out.println(validate("2005-04-01T15:45:58.25Z-02:30"));		
		System.out.println(validate("2004-04-01T09:45:58Z"));		
		System.out.println(validate("2004-06-30T14:00:00.10+24"));		
		System.out.println(validate("2004-06-30T14:00:00.10+47"));		
		System.out.println(validate("2004-06-30T14:00:00.10+05:60"));		
		System.out.println(validate("2004-06-30T14:00:00.10+05:99"));		
		System.out.println(validate("2004-06-30T14:00:00.10-24"));		
		System.out.println(validate("2004-06-30T14:00:00.10-47"));		
		System.out.println(validate("2004-06-30T14:00:00.10-05:60"));		
		System.out.println(validate("2004-06-30T14:00:00.10-05:99"));		
	}
}
