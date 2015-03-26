package com.cyberway.core.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.cyberway.core.objects.Portal;
import com.cyberway.core.utils.jdom.XMLProperties;

/**
 * portal配置
 * @author caiw
 *
 */
public class PortalUtil {

	public final static String module = PortalUtil.class.getName();

	public final static String XML_CONFIG_FILE = "portals-config.xml";

	public final static String PORTAL_CANCHE_KEY="com.cyberway.core.utils.PortalUtil.PORTAL_CANCHE_KEY";
	
	public static String ENCODING = "UTF-8";

	private Map portalMap = new HashMap();

	private FileLocator fileLocator = new FileLocator();

	private final static PortalUtil portalUtil = new PortalUtil();

	public static PortalUtil getInstance() {
		return portalUtil;
	}

	/**
	 * load default config file
	 */
	private PortalUtil() {
		loadProperties(XML_CONFIG_FILE);
	}

	public void loadProperties(String configName) {
		InputStream pathCongfgName = getConfStream(configName);
		if (pathCongfgName != null) {
			XMLProperties properties = new XMLProperties(pathCongfgName);
			portalMap.put(configName, properties);
			return;
		}
		System.out.println(" cann't load config file:-->" + configName);
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

	/**
	 * Root
	 * @return
	 */
	public Element getRootElement(){
		XMLProperties properties = (XMLProperties) portalMap.get(XML_CONFIG_FILE);
		return properties.getRootElement();
	}
	
	public Element getElement(String name){
		XMLProperties properties = (XMLProperties) portalMap.get(XML_CONFIG_FILE);
		return properties.getChildren(name);		
	}
	
	public String getProperty(String configName, String name) {
		XMLProperties properties = (XMLProperties) portalMap.get(configName);
		String res = properties.getProperty(name);
		if (res == null)
			res = "";
		return res;
	}

	public String getProperty(Element element, String name) {
		String res=element.getChildText("cname");
		if (res == null)
			res = "";
		return res;
	}
	
	public void setProperty(String name, String value) {
		setProperty(XML_CONFIG_FILE, name, value);
	}

	public void setProperty(String configName, String name, String value) {
		XMLProperties properties = (XMLProperties) portalMap.get(configName);
		properties.setProperty(name, value);
	}

	public String[] getChildrenProperties( String name) {
		XMLProperties properties = (XMLProperties) portalMap.get(XML_CONFIG_FILE);
		String[] res = properties.getChildrenProperties(name);
		if (res == null)
			res = new String[] {};
		return res;
	}

	/**
	 * 得到所有门户的配置信息
	 * @return
	 */
	public Map getAllPortal(){
		Map map=(Map) portalMap.get(PORTAL_CANCHE_KEY);
		if(map==null){
			map=loadAllPortal();
			portalMap.put(PORTAL_CANCHE_KEY,map);
		}
		return map;			
	}
	
	private Map loadAllPortal(){
		
		PortalUtil portalUtil= PortalUtil.getInstance();

		/**
		 * 门户信息
		 */
		Map map=new HashMap();
		Element element=portalUtil.getRootElement();
		List lsPortal=element.getChildren();
		
		for(int i=0;i<lsPortal.size();i++){

			Portal portal =new Portal();
			Element ele=(Element) lsPortal.get(i);	
			String portalcode=ele.getChildText("portalcode");			
			portal.setPortalcode(portalcode);
			portal.setCname(ele.getChildText("cname"));
			portal.setDeptids(ele.getChildText("deptids"));
			//Log.debug("orgcode:"+orgcode);
			//Log.debug("cname:"+portal.getCname());	
			
			/**
			 * 风格定义
			 */
			List lsStyle=new ArrayList();
			List lsStyleXml= ele.getChildren();
			for(int k=0;k<lsStyleXml.size();k++){
				Portal.Style style=portal.getStyleInstance();				
				ele=(Element) lsStyleXml.get(k);
				boolean isEmpty=true;
				String css=ele.getChildText("css");
				if(!StringUtil.isEmpty(css)){
					style.setCss(css);
					isEmpty=false;
				}
				
				String imgpath=ele.getChildText("imgpath");
				if(!StringUtil.isEmpty(imgpath)){
					style.setImgpath(imgpath);
					isEmpty=false;
				}

				String template=ele.getChildText("template");
				if(!StringUtil.isEmpty(template)){
					style.setTemplate(template);
					isEmpty=false;
				}	
				
				if(!isEmpty)lsStyle.add(style);
			}
			portal.setStyle(lsStyle);
			
			//以portalcode为key保存
			map.put(portalcode,portal);			
		}
		return map;
	}
}
