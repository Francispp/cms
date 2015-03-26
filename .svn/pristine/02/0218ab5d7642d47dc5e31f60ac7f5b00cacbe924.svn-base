package com.cyberway.weixin.business.suite.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.api.SuiteAPI;
import com.cyberway.weixin.business.suite.domain.Suite;
import com.cyberway.weixin.business.suite.service.SuiteService;
import com.cyberway.weixin.encrypt.AesException;
import com.cyberway.weixin.encrypt.WXBizMsgCrypt;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.SuiteUtil;

/**
 * 
 * @com.cyberway.weixin.business.suite.controller.SuiteController
 *  TODO :套件控制器
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月6日 下午3:26:15
 */
public class SuiteController extends BaseBizController<Suite> {
	private static final long serialVersionUID = 1L;
	
	SuiteService	   service	          = (SuiteService) this.getServiceById("suiteService");
	/**
	 * TODO 获得微信推送过来的suite_ticket
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 * @throws AesException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String getSuiteTicket() throws IOException, AesException, DocumentException, ParserConfigurationException, SAXException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) getRequest().getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(SuiteUtil.SUITE_TOKEN, SuiteUtil.SUITE_EncodingAESKey, SuiteUtil.SUITE_ID);
		String sReqMsgSig = getRequest().getParameter("msg_signature");   //微信加密签名  
		String sReqTimeStamp = getRequest().getParameter("timestamp"); // 时间戳  
		String sReqNonce = getRequest().getParameter("nonce");   // 随机数  
		String decryptMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sb.toString());
		System.out.println("after decrypt msg: " + decryptMsg);
		service.parseXml(decryptMsg);
//		String encryptMsg = wxcpt.EncryptMsg(decryptMsg, sReqTimeStamp, sReqNonce);
//		System.out.println("after encrypt msg: " + encryptMsg);
		return "success";
	}
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub 未完成 by yanruqian 2015年2月10日
		return service;
	}
}
