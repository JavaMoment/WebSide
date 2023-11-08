package com.resources.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.StringBuilder;

public class Utils {

	private static final String baseRoute = "ejb:/PDTServerSide/";
	
	public static <T> T getBean(Class<T> expectedBean) {
		final Properties env = new Properties();
		final String sep = "!";
		try {
			try(InputStream jndi = Utils.class.getClassLoader().getResourceAsStream("com/resources/configs/jndi.properties")) {
				env.load(jndi);
			}
			Context ctx = new InitialContext(env);
			
			String route = new StringBuilder(baseRoute)
					.append(expectedBean.getSimpleName().replace("Remote", ""))
					.append(sep)
					.append(expectedBean.getName())
					.toString();
			
			return expectedBean.cast(ctx.lookup(route));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
