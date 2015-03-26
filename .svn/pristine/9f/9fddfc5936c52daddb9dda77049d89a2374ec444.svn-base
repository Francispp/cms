package com.cyberway.core.utils.property;

import java.io.InputStream;
import java.util.Properties;

public class DefaultProperty {
	private static final long PERIOD = 20000;

	private static Properties prop = null;
	
	private static void load() {
	    InputStream is = null;
	    try
	    {
		    if (prop == null){
		    	prop = new Properties();	
		    }
	    	is = DefaultProperty.class.getClassLoader().getResourceAsStream("cyberway.properties");
	        prop.load(is);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
        finally {
          try {
              if (is != null) { is.close(); }
          } catch (Exception e) {}
        }
	}

	public static String getProperty(String key, String defaultValue){
		if (prop == null) {
			load();
		}
		return prop.getProperty(key, defaultValue);
	}
	
	public static String getProperty(String key) {
		return getProperty(key, "");
	}
    
} 