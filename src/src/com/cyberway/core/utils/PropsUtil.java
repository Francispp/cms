package com.cyberway.core.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.cyberway.core.utils.jdom.XMLProperties;

/**
 * 属性文件读取类
 * 
 * @author caiw
 * 
 */
public class PropsUtil {

	public final static String module = PropsUtil.class.getName();

	public final static String XML_CONFIG_FILE = "sysconfig.xml";

	public static String ENCODING = "UTF-8";

	private Map propMap = new HashMap();

	private FileLocator fileLocator = new FileLocator();

	private final static PropsUtil propsUtil = new PropsUtil();

	public static PropsUtil getInstance() {
		return propsUtil;
	}

	/**
	 * load default config file
	 */
	private PropsUtil() {
		loadProperties(XML_CONFIG_FILE);
	}

	public void loadProperties(String configName) {
		System.out.println("load basic configure file " + configName);
		InputStream pathCongfgName = getConfStream(configName);
		if (pathCongfgName != null) {
			XMLProperties properties = new XMLProperties(pathCongfgName);
			properties.setFile(new File(getConfFile(configName)));
			propMap.put(configName, properties);
			return;
		}
		System.err.println(" cann't load config file:-->" + configName);
	}

	public String getConfFile(String fileName) {
		return fileLocator.getConfFile(fileName);
	}

	public InputStream getConfStream(String fileName) {
		return fileLocator.getConfStream(fileName);
	}

	public String getProperty(String name) {
		return getProperty(XML_CONFIG_FILE, name);
	}

	public String getProperty(String configName, String name) {
		XMLProperties properties = (XMLProperties) propMap.get(configName);
		String res = properties.getProperty(name);
		if (res == null)
			res = "";
		return res;
	}

	public void setProperty(String name, String value) {
		setProperty(XML_CONFIG_FILE, name, value);
	}

	public void setCDataProperty(String name, String value) {
		setCDdataProperty(XML_CONFIG_FILE, name, value);
	}

	public void setProperty(String configName, String name, String value) {
		XMLProperties properties = (XMLProperties) propMap.get(configName);
		properties.setProperty(name, value);
	}

	public void setCDdataProperty(String configName, String name, String value) {
		XMLProperties properties = (XMLProperties) propMap.get(configName);
		properties.setCDataProperty(name, value);
	}
}
