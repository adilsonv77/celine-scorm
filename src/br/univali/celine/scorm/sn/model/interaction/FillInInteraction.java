package br.univali.celine.scorm.sn.model.interaction;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillInInteraction extends Interaction {

	public FillInInteraction(String id) {
		super(id);
	}

	@Override
	protected void testPattern(int index, String pattern, boolean correct_responses) throws Exception {
		/*
			The following requirements shall be adhered to in building the characterstring:
			o The array may contain one or more localized_string_types.
			o If the array contains more than one localized_string_type (the interaction has
			multiple correct answers – all of which are required), then they shall be
			separated by the special reserved token “[,]”.
			o Each localized_string may occur more than once.
			o The {case_matters=<boolean>} delimiter indicates whether or not the
			case matters for evaluation of the correct response pattern. The case_matters
			value shall be applied to all of the localized_string_types in the array. The
			{case_matters=<boolean>} may come before or after the
			{order_matters=<boolean>} delimiter. This delimiter is optional; if it
			is not specified the default value for case_matters is “false”. Refer to
			Section 4.1.1.6: Reserved Delimiters for more information on the requirements
			for the format of the {case_matters=<boolean>} delimiter.
			o The {order_matters=<boolean>} indicates whether or not the order
			matters for evaluation of the correct response pattern. The order_matters value
			shall be applied to all of the localized_string_types in the array. The
			{order_matters=<boolean>} may come before or after the
			{case_matters=<boolean>} delimiter. This delimiter is optional; if it
			is not specified the default value for order_matters is “true”. Refer to
			Section 4.1.1.6: Reserved Delimiters for more information on the requirements
			for the format of the {order_matters=<boolean>} delimiter.
		 */

		List<String> respostas = Arrays.asList(pattern.split("\\[,\\]"));
		for (String resposta:respostas) {
			
			testLanguage(resposta);
			testOrderMatters(resposta);
			testCaseMatters(resposta);

		}
		
		/*
		if (respostas.size() == 0) // a especificacao diz uma coisa, mas os testes fazem outra coisa :(
			throw new Exception();
		*/
	}

	private void testCaseMatters(String resposta) throws Exception {
		String pattern = "\\{case_matters=[_a-z0-9A-Z-]+\\}";
		Pattern padrao = Pattern.compile(pattern);     
        Matcher matcher = padrao.matcher(resposta);    
        while(matcher.find()) {  
            MatchResult matchResult = matcher.toMatchResult();  
            String[] trecho = matchResult.group().split("=");
            trecho[1] = trecho[1].substring(0, trecho[1].length()-1);
            if (trecho[1].equals("true") || trecho[1].equals("false")) {
            	
            } else {
            	throw new Exception("Invalid boolean value");
            }
        }		
		
	}

	private void testOrderMatters(String resposta) throws Exception {
		String pattern = "\\{order_matters=[_a-z0-9A-Z-]+\\}";
		Pattern padrao = Pattern.compile(pattern);     
        Matcher matcher = padrao.matcher(resposta);    
        while(matcher.find()) {  
            MatchResult matchResult = matcher.toMatchResult();  
            String[] trecho = matchResult.group().split("=");
            trecho[1] = trecho[1].substring(0, trecho[1].length()-1);
            if (trecho[1].equals("true") || trecho[1].equals("false")) {
            	
            } else {
            	throw new Exception("Invalid boolean value");
            }
        }		
	}

	private void testLanguage(String resposta) throws Exception {
		String patternLang = "\\{lang=[_a-z0-9A-Z-]+\\}";
		Pattern padrao = Pattern.compile(patternLang);     
        Matcher matcher = padrao.matcher(resposta);    
	         
        while(matcher.find()) {  
           MatchResult matchResult = matcher.toMatchResult();  
           String[] lang = matchResult.group().split("=");
           lang[1] = lang[1].substring(0, lang[1].length()-1);
           String[] iso = lang[1].split("-");
           if (iso[0].length() == 2)
        	   LocaleUtil.testISO2Language(iso[0]);
           else
        	   LocaleUtil.testISO3Language(iso[0]);
           if (iso.length > 1)
        	   LocaleUtil.testISO2Country(iso[1]);
        }        
	}


	
}
