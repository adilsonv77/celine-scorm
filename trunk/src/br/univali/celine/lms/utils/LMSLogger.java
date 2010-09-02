package br.univali.celine.lms.utils;

import java.util.logging.Logger;

public class LMSLogger {

	private static Logger logger = Logger.getLogger("global");

	public static void throwing(Throwable throwable) {
	
		StackTraceElement[] st = throwable.getStackTrace();
		logger.info(throwable + ":" + st[0].getClassName()+"."+st[0].getMethodName() + "() in " + st[0].getLineNumber());

	}
	
	
}
