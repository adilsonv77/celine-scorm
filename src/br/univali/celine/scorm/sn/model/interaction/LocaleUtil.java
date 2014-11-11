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
package br.univali.celine.scorm.sn.model.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleUtil {

	private static List<String> iso2Lang = new ArrayList<String>();
	private static List<String> iso3Lang = new ArrayList<String>();
	private static List<String> iso2Country = new ArrayList<String>();
	
	static {
		String[] langs = Locale.getISOLanguages();
		
		for(String lang:langs) {
			iso2Lang.add(lang);
			
			Locale l = new Locale(lang);
			iso3Lang.add(l.getISO3Language());
			
		}
		
		langs = Locale.getISOCountries();
		for (String lang:langs)
			iso2Country.add(lang);
	}
	
	public static void testISO2Language(String language) throws Exception {
		if (!iso2Lang.contains(language))
			throw new Exception("ISO Language not found");
	}
	
	public static void testISO2Country(String country) throws Exception {
		if (!iso2Country.contains(country))
			throw new Exception("ISO Country not found");
	}
	
	public static void testISO3Language(String language) throws Exception {
		if (!iso3Lang.contains(language))
			throw new Exception("ISO Language not found");
	}
	
	
}
