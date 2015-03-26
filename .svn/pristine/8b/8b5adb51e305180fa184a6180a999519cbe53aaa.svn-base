package com.cyberway.weixin.business.suite.service;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.weixin.api.SuiteAPI;
import com.cyberway.weixin.business.suite.domain.Suite;
import com.cyberway.weixin.entity.AccessToken;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.SuiteUtil;

/**
 * 
 * @com.cyberway.weixin.business.suite.service.SuiteService
 * TODO : 套件处理类
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月9日 上午10:49:48
 */
public class SuiteService extends HibernateEntityDao<Suite>{

	public  String getJsonObjectData(String suiteId,String suiteSecret,String suiteTicket){
		String jsonData = "{\"suite_id\": \"%s\",\"suite_secret\": \"%s\",\"suite_ticket\": \"%s\"}";
		return String.format(jsonData,suiteId,suiteSecret,suiteTicket);
	}
	public  String getJsonAppData(String suiteId,String appId){
		String jsonData = "{\"suite_id\": \"%s\",\"appid\": [%s]}";
		return String.format(jsonData,suiteId,appId);
	}
	
	public void parseXml(String xmltext) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xmltext);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);
		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("SuiteId");
		NodeList nodelist2 = root.getElementsByTagName("SuiteTicket");
		NodeList nodelist3 = root.getElementsByTagName("InfoType");
		NodeList nodelist4 = root.getElementsByTagName("TimeStamp");
		String suiteId= nodelist1.item(0).getTextContent();
		String suiteTicket= nodelist2.item(0).getTextContent();
		String infoType= nodelist3.item(0).getTextContent();
		String timeStamp= nodelist4.item(0).getTextContent();
		Suite suite = new Suite();
		suite.setSuiteId(suiteId);
		suite.setSuiteTicket(suiteTicket);
		suite.setInfoType(infoType);
		suite.setTimeStamp(timeStamp);
		suite.setCreateDate(new Date());
		this.saveOrUpdate(suite);
	}
	public Suite getSuiteTicketValue(){
		String sql = "select * from WX_SUITE order by CREATE_DATE desc limit 1";
		List<Suite> suite = getSession().createSQLQuery(sql).addEntity(Suite.class).list();
		return suite.size()>0?suite.get(0):null;
	}
	
	public AccessToken getSuiteAccessToken(String jsonData) {
		JSONObject jsonobject = ConnectionUtil.getHttpRequest(SuiteAPI.GET_SUITE_ACCESS_TOKEN_URL, "POST", jsonData);
		AccessToken accessToken = null;
		if (null != jsonobject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonobject.getString("suite_access_token"));
				accessToken.setExpiresIn(jsonobject.getInt("expires_in"));
			}catch(Exception e){
				accessToken = null;
				// 获取token失败
				String error = String.format("获取token失败 errcode:{} errmsg:{}",jsonobject.getInt("errcode"), jsonobject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return accessToken;
	}
}
