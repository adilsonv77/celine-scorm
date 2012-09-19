package br.univali.celine.lms.config;

import java.lang.reflect.Method;

import org.apache.commons.digester3.Rule;
import org.xml.sax.Attributes;

import br.univali.celine.lms.utils.LMSLogger;

public class BeanSetterAttribute extends Rule {

	private String attributeName;
	private String body;
	
	@Override
	public void begin(String namespace, String name, Attributes attributes) throws Exception {
		
		this.attributeName = attributes.getValue("name");
		attributeName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
	}

	@Override
	public void body(String namespace, String name, String text) throws Exception {
		this.body = text;
	}

	@Override
	public void end(String namespace, String name) throws Exception {
		
		String methodName = "set" + attributeName;
		Method[] methods = getDigester().peek().getClass().getMethods();
		for (Method method:methods) {
			if (method.getName().equals(methodName)) {
				
				// se achou o método que precisava
				try {
					
					// vai procurar um método valueOf do tipo do parametro setter que recebe uma String
					Object obj = body;
					if (method.getParameterTypes()[0] != String.class) {

						Method methodConversao = method.getParameterTypes()[0].getMethod("valueOf", String.class);
						obj = methodConversao.invoke(null, body);
						
					} 
					
					method.invoke(getDigester().peek(), obj);
					
				} catch (Exception e) {
					LMSLogger.throwing(e);					
				}
				
				return;
			}
		}
		
		
		
	}

	
}
