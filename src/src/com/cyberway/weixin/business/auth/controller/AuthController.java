package com.cyberway.weixin.business.auth.controller;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.api.AuthAPI;
import com.cyberway.weixin.api.SuiteAPI;
import com.cyberway.weixin.business.auth.domain.Auth;
import com.cyberway.weixin.business.auth.domain.AuthDetails;
import com.cyberway.weixin.business.auth.service.AuthService;
import com.cyberway.weixin.business.department.domain.Department;
import com.cyberway.weixin.business.suite.domain.Suite;
import com.cyberway.weixin.business.suite.service.SuiteService;
import com.cyberway.weixin.entity.AccessToken;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.Constants;
import com.cyberway.weixin.util.SuiteUtil;
/**
 * 
 * @com.cyberway.weixin.business.auth.controller.AuthController
 * TODO :授权控制器
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月12日 下午4:21:37
 */
public class AuthController  extends BaseBizController<Auth>{

	/**  
	* serialVersionUID:TODO（用一句话描述这个变量表示什么）
	*/  
	private static final long serialVersionUID = 1L;
	
	 AuthService	   service	          = (AuthService) this.getServiceById("authService");
	 SuiteService	   suiteService	          = (SuiteService) this.getServiceById("suiteService");

	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub 未完成 by yanruqian 2015年2月12日
		return service;
	}
	/**
	 * 应用提供商可以在自己的网站中放置“微信企业号应用授权”的入口，
	 * 引导企业号管理员进入应用套件授权页。网址为:
	 * 
	 * https://qy.weixin.qq.com/cgi-bin/loginpage?
	 * suite_id=$suite_id$&pre_auth_code=$pre_auth_code$
	 * &redirect_uri=$redirect_uri$&state=$state$
	 * @return
	 */
	private String suite_id=SuiteUtil.SUITE_ID;
	private String pre_auth_code;
	private String redirect_uri="http://wx.cyberway.com.cn:8084/weixin/auth!doList.action";
	private String state="1";
	/**
	 * 
	*  <p>TODO 授权页面</p>
	 * @return
	 */
	public String doIndex(){
		Suite suite = suiteService.getSuiteTicketValue();
		String jsonData = suiteService.getJsonObjectData(SuiteUtil.SUITE_ID,SuiteUtil.SUITE_SECRET, suite.getSuiteTicket());
		String suiteAccessToken = suiteService.getSuiteAccessToken(jsonData).getToken();
		pre_auth_code = service.getCode(suiteAccessToken).getAuthCode();
		return "index";
	}
	/**
	 * 
	*  <p>TODO 授权成功返回页面</p>
	 * @return
	 */
	public String doList(){
		String auth_code = getRequest().getParameter("auth_code");
		String expires_in = getRequest().getParameter("expires_in");
		String state = getRequest().getParameter("state");
		//永久授权码
		String jsonpermanmentData = service.getPermanmentData(SuiteUtil.SUITE_ID, auth_code);
		Suite suite = suiteService.getSuiteTicketValue();
		String jsonData = suiteService.getJsonObjectData(SuiteUtil.SUITE_ID,SuiteUtil.SUITE_SECRET, suite.getSuiteTicket());
		String suiteAccessToken = suiteService.getSuiteAccessToken(jsonData).getToken();
		JSONObject jsonObject = ConnectionUtil.getHttpRequest(AuthAPI.GET_PREMANENT_CODE_URL+suiteAccessToken, "POST", jsonpermanmentData);
		Auth auth = service.saveCorpAuthInfo(jsonObject);
		System.out.println("永久授权码: "+jsonObject);
		//获取企业号的授权信息
		String jsonAuthData = service.getJsonAuthInfo(SuiteUtil.SUITE_ID, Constants.CORPID, auth.getPermanentCode());
		JSONObject jsonObject2 = ConnectionUtil.getHttpRequest(AuthAPI.GET_AUTH_INFO_URL+suiteAccessToken, "POST", jsonAuthData);
		System.out.println("获取企业号的授权信息: "+jsonObject2);
		//获取企业号应用
		String jsonAgentData = service.getJsonAgent(SuiteUtil.SUITE_ID, Constants.CORPID, auth.getPermanentCode());
		JSONObject jsonObject3 = ConnectionUtil.getHttpRequest(AuthAPI.GET_AUTH_INFO_URL+suiteAccessToken, "POST", jsonAgentData);
		System.out.println("获取企业号应用: "+jsonObject3);
		//获取企业号access_token
		JSONObject jsonObject4 = ConnectionUtil.getHttpRequest(AuthAPI.GET_CORP_TOKEN_URL+suiteAccessToken, "POST", jsonAuthData);
		System.out.println("获取企业号access_token: "+jsonObject4);
		return "list";
	}
	
	public String getSuite_id() {
		return suite_id;
	}

	public void setSuite_id(String suite_id) {
		this.suite_id = suite_id;
	}

	public String getPre_auth_code() {
		return pre_auth_code;
	}

	public void setPre_auth_code(String pre_auth_code) {
		this.pre_auth_code = pre_auth_code;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
