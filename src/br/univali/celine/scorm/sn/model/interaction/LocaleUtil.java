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
