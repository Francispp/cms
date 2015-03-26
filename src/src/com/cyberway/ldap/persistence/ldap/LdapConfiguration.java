/*
 * Created on Aug 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence.ldap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Category;



/**
 * Singleton class responsible for reading in the details of 
 * the current LDAP configuration.  Currently reads the details
 * from a properties file in the user.home directory.
 *  
 *  
 */
public class LdapConfiguration {
	private static Category log =
			org.apache.log4j.Category.getInstance(LdapConfiguration.class.getName());
	private static LdapConfiguration instance;
	private Properties configuration;
	
	/**
	 * 	Private constructor
	 *
	 */
	private LdapConfiguration() throws IOException{
		configuration = readFile();
	}
	
	
	/**
	 * Get an instance of this class which will contain
	 * a properties object with details of the current 
	 * LDAP configuration.
	 * 
	 * @return An instance of this class
	 */
	public static LdapConfiguration getInstance() throws IOException{
		if (instance==null)
				try {
					instance = new LdapConfiguration();
				} catch (IOException e) {
					throw e;
				}
		return instance;
	}
	/**
	 * Get the details of this configuration
	 * 
	 * @return A Properties object representing the 
	 * 			current LDAP configuration  
	 */
	public Properties getProperties(){
		return configuration;
	}
	/**
	 * Attempts to load LDAP properties from a file named
	 * ldap.properties.  At present, this file should be in the
	 * "user.dir" directory.  "user.dir" is set to "jboss.home\bin"
	 * when running on JBoss
	 * 
	 * @return A Properties object created from
	 * 						the specified file
	 */
	private Properties readFile() throws IOException{
		log.info("Current user home directory: " + System.getProperty("user.dir"));

		Properties props = new Properties();
		String propertiesFileName = "ldap.properties";
		//FileInputStream fileIn = null;
		InputStream fileIn = null;

		try {
			fileIn = LdapConfiguration.class.getClassLoader().getResourceAsStream(propertiesFileName);
			//fileIn = new FileInputStream(new File(propertiesFileName));
			props.load(fileIn);
		} 
		catch (FileNotFoundException e) {
			fileIn = null;
			log.error("Unable to find ldap properties file");
			throw e;
		}
		catch (IOException e) {
			log.error("Unable to read ldap properties file");
			throw e;
		}
		finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} 
				catch (IOException e) {
					throw e;
				}
				fileIn = null;
			}
		}
		return props;
	}
}
