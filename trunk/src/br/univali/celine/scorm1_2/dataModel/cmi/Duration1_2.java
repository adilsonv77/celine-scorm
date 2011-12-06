package br.univali.celine.scorm1_2.dataModel.cmi;


public class Duration1_2 {

	public static void test(String value) throws Exception {
		String[] peaces = value.split(":");
		if (peaces.length != 3) {
			throw new Exception("More than 3 fields");
		}
		
		if (peaces[0].length() < 2 || peaces[0].length() > 4) {
			throw new Exception("Hours incorrect");
		}
		
		Integer.parseInt(peaces[0]); 
		// hora ok
		
		if (peaces[1].length() != 2) {
			throw new Exception("Minutes incorrect");
		}
		
		Integer.parseInt(peaces[1]); 
		// minuto ok

		String[] seconds = peaces[2].split("\\."); 
		if (seconds[0].length() != 2) {
			throw new Exception("Seconds incorrect");
		}
		
		Integer.parseInt(seconds[0]);
		
		if (seconds.length > 1) {
			if (seconds[1].length() > 2) {
				throw new Exception();
			}
			
			Integer.parseInt(seconds[1]);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Duration1_2.test("01:00:10");
		Duration1_2.test("01:00:10.2");
		Duration1_2.test("0001:00:10.26");
		Duration1_2.test("0001:00:10");
		Duration1_2.test("0001:00:10.2");
		Duration1_2.test("0001:00:10.26");
	}
}
