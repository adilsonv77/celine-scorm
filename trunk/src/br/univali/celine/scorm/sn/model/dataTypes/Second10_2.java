package br.univali.celine.scorm.sn.model.dataTypes;

public class Second10_2 {

	public static boolean validate(String value) {
		// P[yY][mM][dD][T[hH][nM][s[.s]S]]

		if (!value.startsWith("P")) {
			return false;
		}
		String another = "";
		try {
			another = value;
			if (another.contains("T")) {
				another = value.substring(0, value.indexOf("T"));
				value = value.substring(value.indexOf("T"));
			} else {
				value = "";
			}
					
			another = test(test(test(another.substring(1), "Y"), "M"), "D");

			if (value.startsWith("T") && value.length() > 1) {
				value = testFloat(test(test(value.substring(1), "H"), "M"), "S");
			}
		} catch (Exception e) {
			return false;
		}
		return value.length() == 0 && another.length() == 0;
	}

	private static String testFloat(String value, String key) {
		if (value.contains(key)) {
			int idx = value.indexOf(key);
			Float.parseFloat(value.substring(0, idx));
			value = value.substring(idx + 1);
		}
		return value;
	}

	private static String test(String value, String key) {

		if (value.contains(key)) {
			int idx = value.indexOf(key);
			Integer.parseInt(value.substring(0, idx));
			value = value.substring(idx + 1);
		}
		return value;
	}

	public static void main(String[] args) {

		
		System.out.println(Second10_2.validate("P1Y6M14DT4H15M6.34S"));
		System.out.println("false " + Second10_2.validate("1Y6M14DT4H15M6.34S"));
		System.out.println(Second10_2.validate("P6M14DT4H15M6.34S"));
		System.out.println(Second10_2.validate("P14DT4H15M6.34S"));
		System.out.println(Second10_2.validate("P6MT4H15M6.34S"));
		System.out.println(Second10_2.validate("P6M14DT15M6.34S"));
		System.out.println(Second10_2.validate("P6M14DT4H6.34S"));
		System.out.println(Second10_2.validate("P6M14DT4H15M"));
		System.out.println(Second10_2.validate("P1Y14DT4H15M6.34S"));
		System.out.println(Second10_2.validate("P1YT4H15M6.34S"));
		System.out.println(Second10_2.validate("P1Y14DT15M6.34S"));
		System.out.println(Second10_2.validate("P1Y14DT4H6.34S"));
		System.out.println("false " + Second10_2.validate("P1Y6M14D4H15M6.34S"));
		System.out.println(Second10_2.validate("P1YT15M6.34S"));
		System.out.println(Second10_2.validate("P1Y6MT6.34S"));
		System.out.println("false " + Second10_2.validate("P1Y6M14DT"));
		System.out.println(Second10_2.validate("P1Y"));
		System.out.println(Second10_2.validate("PT0S"));
		System.out.println(Second10_2.validate("P6M"));
		System.out.println(Second10_2.validate("P14D"));
		
		System.out.println(Second10_2.validate("P1Y6M14DT4H015M6.34S"));
		System.out.println(Second10_2.validate("P1Y6M14DT4H15M06.34S"));
		System.out.println(Second10_2.validate("P1Y6M14D4H15M6.34S"));	
	}

}
