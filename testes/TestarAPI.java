import br.univali.celine.lms.dao.RDBDAO;
import br.univali.celine.lmsscorm.User;
import br.univali.celine.scorm.model.cam.ContentPackage;
import br.univali.celine.scorm.model.cam.ContentPackageReader;
import br.univali.celine.scorm.model.cam.ContentPackageReaderFactory;
import br.univali.celine.scorm.rteApi.APIImplementation;


public class TestarAPI {

	public static void main(String[] args) throws Exception {

		
		//String folder = "C:/adilson/cursos/LMSTestPackage_DMI";
		//String folder2 = "LMSTestPackage_DMI";
		
		String folder = "C:/adilson/cursos/LMSTestPackage_DMI";
		String folder2 = "LMSTestPackage_DMI";
		
		//String folder = "C:/Documents and Settings/adilsonv/Desktop/redesbayes_probabilidade";
		//String folder2 = "redesbayes_probabilidade";
		ContentPackageReader cpr = ContentPackageReaderFactory.getContentPackageReader(folder + "/imsmanifest.xml");
		ContentPackage cp = cpr.ler(folder + "/imsmanifest.xml");
		
		RDBDAO rdbDAO = new RDBDAO();
		rdbDAO.setDriver("com.mysql.jdbc.Driver");
		rdbDAO.setUrl("jdbc:mysql://localhost:3306/CELINE");

		rdbDAO.setUser("root");
		rdbDAO.setPassword("root");
		
		rdbDAO.initialize();
		
		User user = rdbDAO.findUser("adilson");
		
		APIImplementation api = new APIImplementation(
				folder2, cp, user, rdbDAO, null, null);
		api.initialize("");
		
		
		/*
		System.out.println("adl.data._count : " + api.getValue("adl.data._count"));
		System.out.println("Last error : " + api.getLastError());

		/*
		System.out.println("Last error : " + api.getLastError());

		System.out.println("cmi.score.scaled : " + api.getValue("cmi.score.scaled"));
		System.out.println("Last error : " + api.getLastError());
		
		System.out.println("cmi.score.raw : " + api.getValue("cmi.score.raw"));
		System.out.println("Last error : " + api.getLastError());
		
		System.out.println("cmi.score.min : " + api.getValue("cmi.score.min"));
		System.out.println("Last error : " + api.getLastError());
		
		System.out.println("cmi.score.max : " + api.getValue("cmi.score.max"));
		System.out.println("Last error : " + api.getLastError());
/*
		System.out.println(api.getValue("cmi.core.student_name"));
		System.out.println(api.getLastError());

		System.out.println(api.getValue("cmi.core.student_id"));
		System.out.println(api.getLastError());

		/*	

		System.out.println(api.getValue("cmi.core.score._children"));
		System.out.println(api.getLastError());
		
		System.out.println(api.getValue("cmi.core.entry"));
		System.out.println(api.getLastError());
		System.out.println(api.setValue("cmi.core.session_time","00:01:00"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.suspend_data","none"));
		System.out.println(api.getLastError());
		*/
		
	//	api.processRequest("I_SCO1");
	//	api.processRequest("I_SCO2");
		/*
		System.out.println(api.getValue("cmi.launch_data"));
		System.out.println(api.getLastError());
		*/
		api.processRequest("activity_3");
		api.initialize("");
		/*
		System.out.println(api.getLastError());
		System.out.println(api.getValue("cmi.completion_threshold"));
		System.out.println(api.getLastError());
		System.out.println(api.setValue("cmi.interactions", "illegal"));
		System.out.println(api.getLastError());
		*/
		api.setValue("cmi.interactions.0.id", "INT0");
		api.setValue("cmi.interactions.1.id", "INT1");
		/*
		api.setValue("cmi.interactions.1.type", "choice");
		api.setValue("cmi.interactions.1.correct_responses.0.pattern", "");
		
	    String s = "a[,]" +
	    		   "asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50ew[,]" +
	    		   "asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]" +
	    		   "am[,]" +
	    		   "asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49";
		api.setValue("cmi.interactions.1.correct_responses.0.pattern", s);
		System.out.println(api.getLastError());
		*/
		api.setValue("cmi.interactions.2.id", "INT2");
		api.setValue("cmi.interactions.2.type", "choice");
		System.out.println(api.setValue("cmi.interactions.2.learner_response", 
				"a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49"));
		api.setValue("cmi.interactions.2.correct_responses.0.pattern", "a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49"); 
		//System.out.println(api.getLastError());
		System.out.println("cmi.interactions.2.correct_responses.3.pattern : " + api.setValue("cmi.interactions.2.correct_responses.3.pattern", "b")); 
		//System.out.println(api.getLastError());
		
		api.setValue("cmi.interactions.3.id", "INT3");
		api.setValue("cmi.interactions.3.type", "fill-in");
		System.out.println(api.setValue("cmi.interactions.3.correct_responses.0.pattern", "{lang=eng}{order_matters=true}{case_matters=invalid}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49[,]{case_matters=true}{case_matters=invalid}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49"));

		System.out.println(api.getLastError());
		/*
		System.out.println("{lang=en}7r61bLD4PC3Hdf6GY1WXF1DRaRD3a5Fmw3VbXXYR5PE4DZ4aY9sVZ6PIjTBMt7ELux0YLG7R1h6TP1AvHY1mCbFDcPRWQEae9u0uHF9USHX7pgbDyW06RrZY8DTALwm5AH2C6xM3v3KOG0J7GlAJ85V51Icj514IRRpEDJ3n3R3bTBHONIZPG9xA9zDkWi4Jw5ZVZ5aBmnOot5DqQ4FNhUADILTBh9GRqscwdvefblengthOfString250".length());
		api.setValue("cmi.interactions.2.type", "fill-in");
		s = "{lang=eng}{order_matters=true}{case_matters=invalid}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49[,]{case_matters=true}{case_matters=invalid}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49";
		api.setValue("cmi.interactions.2.correct_responses.0.pattern", s);
		System.out.println(api.getLastError());
		
		
/*		System.out.println(api.setValue("cmi.interactions.0.id", "INT1"));
		System.out.println(api.setValue("cmi.interactions.0.latency", "P1Y6M14D4H15M6.34S"));
		System.out.println(api.getLastError());
/*
		System.out.println(api.getValue("cmi.completion_threshold"));
		System.out.println(api.getLastError());
		

		System.out.println(api.setValue("cmi.interactions._count", "illegal"));
		System.out.println(api.getLastError());
		*/
/*		System.out.println(api.getValue("cmi.interactions.0.objectives.0.id"));
		System.out.println(api.getLastError());
		
		System.out.println(api.getValue("cmi.interactions.0.correct_responses.0.pattern"));
		System.out.println(api.getLastError());

		System.out.println(api.setValue("cmi.interactions.0.correct_responses.0.pattern", "true"));
		System.out.println(api.getLastError());
		/*
		api.setValue("cmi.interactions.0.id", "int1");
		api.setValue("cmi.interactions.0.type", "true-false");
		/*

		api.setValue("cmi.interactions.1.correct_responses.1.pattern", "true");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "true");

		api.setValue("cmi.interactions.0.correct_responses.1.pattern", "true");
		api.setValue("cmi.interactions.0.correct_responses.1.pattern", "true-false");
		//System.out.println(api.getLastError());
		
		api.getValue("cmi.interactions.0.correct_responses.1.pattern");
		//System.out.println(api.getLastError());
*//*
		api.setValue("cmi.interactions.1.id", "int2");
		api.setValue("cmi.interactions.1.type", "choice");
		/*
		api.setValue("cmi.interactions.1.learner_response", "[,]");
		System.out.println(api.getLastError());
		
		api.setValue("cmi.interactions.1.learner_response", "[,]aa[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]ab[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49");
		api.setValue("cmi.interactions.1.learner_response", "{}{[]}{}{[]}{}");
		System.out.println(api.getLastError());
*//*
		api.setValue("cmi.interactions.2.id", "int3");
		api.setValue("cmi.interactions.2.type", "fill-in");
		api.setValue("cmi.interactions.2.learner_response", "[,]");
		api.setValue("cmi.interactions.2.correct_responses.0.pattern", "{lang=fr}z");
		api.setValue("cmi.interactions.2.correct_responses.0.pattern", "a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]{lang=frl}a[,]{lang=frl}asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49[,]{lang=frl}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]{lang=frl}a[,]{lang=frl}asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{lang=frl}z");
		api.setValue("cmi.interactions.2.correct_responses.0.pattern", "a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]{lang=exg}a[,]{lang=exg}asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49[,]{lang=exg}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]{lang=exg}a[,]{lang=exg}asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{lang=exg}z");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{lang=sp}z");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{order_matters=false}z");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{order_matters=true}z");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{order_matters=false-true}z");
		api.setValue("cmi.interactions.2.correct_responses.1.pattern", "{order_matters=true-false}a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50[,]a[,]asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50asdftyuioplkjhgfdsazxcvbnm0987654321qwertyuioplk50fdsatyuioplkjhgfdsazxcvbnm0987654321qwertyuiopl49");
		api.setValue("cmi.interactions.2.correct_responses.2.pattern", "{case_matters=true}{lang=eng}{order_matters=true}z");
		System.out.println(api.getLastError());
*/
		/*
		
		System.out.println(api.setValue("cmi.interactions.1.description", "Teste"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.interactions.2.description", "Teste"));
		System.out.println(api.getLastError());
		
		System.out.println(Second10_2.validate("P1Y6M14DT4H15M6.34S"));
		
		System.out.println(api.setValue("cmi.interactions.0.latency", "P1Y6M14DT4H15M6.34S"));
		System.out.println(api.getLastError());
*/
		/*
		
		
		System.out.println(api.setValue("cmi.interactions.0.type", "true_false"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.interactions.0.correct_responses.0.pattern", "Dependency on cmi.interaction.n.id not established"));
		System.out.println(api.getLastError());
		
		/*
		api.processRequest("activity_3");

		System.out.println(api.getValue("cmi.completion_threshold"));
		System.out.println(api.getLastError());

		System.out.println(api.setValue("cmi.objectives", "illegal"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.objectives.0.id", "obj1"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.objectives.0.score.scaled", "1.0"));
		System.out.println(api.getLastError());
		
		System.out.println(api.getValue("cmi.objectives.0.score.scaled"));
		System.out.println(api.getLastError());
		
		System.out.println(api.getValue("cmi.score.scaled"));
		System.out.println(api.getLastError());
		
		System.out.println(api.getValue("adl.data.0.id"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("adl.data.0.store", "xxxx"));
		System.out.println(api.getLastError());

		System.out.println(api.getValue("adl.data.0.store"));
		System.out.println(api.getLastError());

		System.out.println(api.getValue("adl.data._children"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("adl.data._children", "xx"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.comments_from_lms", "xx"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.comments_from_lms._children", "xx"));
		System.out.println(api.getLastError());
		
		System.out.println(api.setValue("cmi.comments_from_lms.0.comment", "My Comment"));
		System.out.println(api.getLastError());
		
		*/
		
	}

}
