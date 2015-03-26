package com.cyberway.weixin.business.attendance.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.business.attendance.domain.Attendance;
import com.cyberway.weixin.business.attendance.domain.AttendanceManage;
import com.cyberway.weixin.business.attendance.service.AttendanceManageService;
import com.cyberway.weixin.business.attendance.service.AttendanceService;
import com.cyberway.weixin.encrypt.AesException;
import com.cyberway.weixin.encrypt.WXBizMsgCrypt;
import com.cyberway.weixin.oauth.Oauth2Util;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;
import com.cyberway.weixin.util.JSSDKUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
/**
 * 
 * @com.cyberway.weixin.business.attendance.controller.AttendanceController
 * TODO :微信考勤控制器
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月16日 下午1:58:26
 */
public class AttendanceController extends BaseBizController<Attendance> implements Preparable{
	
	/**  
	* serialVersionUID:TODO（用一句话描述这个变量表示什么）
	*/  
	private static final long serialVersionUID = 1L;
	
	AttendanceService service = (AttendanceService) this.getServiceById("attendanceService");
	AttendanceManageService attendanceManageservice = (AttendanceManageService) this.getServiceById("attendanceManageService");
	
	private String config;
	private AttendanceManage am;
	private Attendance attendance;
	
	private String redirectUrl ;
	private Map<String, String> resultMap;
	
	
	@Override
	protected EntityDao<Attendance> getService() {
		// TODO Auto-generated method stub 未完成 by yanruqian 2015年2月16日
		return service;
	}
	public void prepare() throws Exception {
		String actionName = ActionContext.getContext().getActionInvocation().getProxy().getMethod();
		
		if (ArrayUtils.contains(new String[] { "doSave"}, actionName)) {
			// 设置单条记录的模型
			if (StringUtils.isBlank(getRequest().getParameter("domain.id"))) {
				try {
					setDomain(getDomainClass().newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				setDomain(getService().get(Long.parseLong(getRequest().getParameter("domain.id"))));
			}
		}
	}
	/**
	 * 
	*  <p>TODO 回调地址</p>
	 * @return http://120.197.42.141:8084/weixin/attendance!index.action
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws DocumentException 
	 */
	public String doIndex() throws IOException, ParserConfigurationException, SAXException{
		// 微信加密签名  
        String msg_signature = getRequest().getParameter("msg_signature");  
        // 时间戳  
        String timestamp = getRequest().getParameter("timestamp");  
        // 随机数  
        String nonce = getRequest().getParameter("nonce");  
        //加密的随机字符串
        String echostr = getRequest().getParameter("echostr");  
        // 打印请求地址
//        System.out.println("request=" + getRequest().getRequestURL());  
        String result = null;
        try {  
        	WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Constants.TOKEN,Constants.encodingAESKey,Constants.CORPID);  
	        if(null!=echostr){
	        	// 验证URL函数
	        	 result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);  
	        }else{
	        	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) getRequest().getInputStream()));
	    		String line = null;
	    		StringBuilder sb = new StringBuilder();
	    		while ((line = br.readLine()) != null) {
	    			sb.append(line);
	    		}
	    		result = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, sb.toString()); 
	        }
//	        System.out.println("result:"+result);
        } catch (AesException e) {  
            e.printStackTrace();  
        }  
        outWrite(result);
		return null;
	}
	/**
	 * 
	*  <p>TODO 签到跳转页面</p>
	*  http://wx.cyberway.com.cn:8084/weixin/attendance!signIn.action
	*  https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2cc405f9858fc8&redirect_uri=http://wx.cyberway.com.cn:8084/weixin/attendance!signIn.action&response_type=code&scope=snsapi_base&state=1#wechat_redirect
	 * @return
	 */
	public String signIn(){
		String url = getRequest().getRequestURL().toString();
		String userId = getWxUserId();
		if(StringUtils.isBlank(userId)){
			String code = getRequest().getParameter("code");
			if(StringUtils.isBlank(code)){
				redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2cc405f9858fc8&redirect_uri=http://wx.cyberway.com.cn:8084/weixin/attendance!signIn.action&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
				return "redirect_getuser";//重定向获取user
			}else{
				userId = Oauth2Util.getUserID(code, Constants.AGENTID);
				Cookie coo = new Cookie("user",userId);
				getResponse().addCookie(coo);
				String state = getRequest().getParameter("state");
				url = url+"?code="+code+"&state="+state;
			}
		}
		config = JSSDKUtil.getJsConfig(url,false);
		am = attendanceManageservice.getAttendance();
		return "signin_medi";
	}
	
	public String doSave(){
		Integer type = Integer.parseInt(getRequest().getParameter("type"));
		Attendance att = getDomain();
		if(att.getId() != null){
			setDomain(getService().saveOrUpdate(att));
			if(type==1){//签到
				return "signin_share";//分享签到心情成功页面
			}else{
				return "signout_share";//分享签退心情成功页面
			}
		}else{
			String url = getRequest().getRequestURL().toString()+"?type="+type;
			config = JSSDKUtil.getJsConfig(url,false);
			String userId = getWxUserId();
			if(StringUtils.isNotBlank(userId)){
				String userName = CommonUtil.findByUserId(userId).getName();
				att.setUserId(userId);
				att.setUserName(userName);
			}
			if(type==1){
				att.setSigninTime(new Date());
				att.setIsSignIn(true);
				setDomain(getService().saveOrUpdate(att));
				service.sendMpnews(userId,true);//发送图文消息
				return "signin";//发表心情页面
			}else{
				att.setSignOutTime(new Date());
				att.setIsSignIn(false);
//				att.setSigninId(signinId);
				setDomain(getService().saveOrUpdate(att));
				service.sendMpnews(userId,false);//发送图文消息
				return "signout";//发表心情页面
			}
		}
	}
	/**
	 * 
	*  <p>TODO 签退跳转页面</p>
	 * @return
	 */
	public String signOut(){
		String userId = getWxUserId();
		String url = getRequest().getRequestURL().toString();
		if(StringUtils.isBlank(userId)){
			String code = getRequest().getParameter("code");
			if(StringUtils.isBlank(code)){
				redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2cc405f9858fc8&redirect_uri=http://wx.cyberway.com.cn:8084/weixin/attendance!signOut.action&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
				return "redirect_getuser";//重定向获取user
			}else{
				userId = Oauth2Util.getUserID(code, Constants.AGENTID);
				Cookie coo = new Cookie("user",userId);
				getResponse().addCookie(coo);
				String state = getRequest().getParameter("state");
				url = url+"?code="+code+"&state="+state;
			}
		}
		config = JSSDKUtil.getJsConfig(url,false);
		am = attendanceManageservice.getAttendance();
		return "signout_medi";
	}
	
	/**
	 * 
	 *  <p>TODO 外勤签/退跳转页面</p>
	 * @return
	 */
	public String legwork(){
		String url = getRequest().getRequestURL().toString();
		config = JSSDKUtil.getJsConfig(url,false);
		return "legwork";
	}
	
	/**
	 * 
	 *  <p>TODO 外勤计划跳转页面</p>
	 * @return
	 */
	public String legworkPlan(){
		
		return "legwork_plan";
	}
	
	/**
	 * 
	 *  <p>TODO 外勤记录跳转页面</p>
	 * @return
	 */
	public String wRecord(){
		
		return "wRecord";
	}
	/**
	 * 
	 *  <p>TODO 考勤记录跳转页面</p>
	 * @return
	 */
	public String kRecord(){
		attendance = service.getCurrentRecord();
		return "kRecord";
	}
	
	public String loadRecord(){
		
		return "";
	}
	public String ajaxloadConfig(){
		String url = getRequest().getRequestURL().toString();
		String type = getRequest().getParameter("type");
		System.out.println("url :"+url +"\n type :"+type);
		resultMap = JSSDKUtil.getJspTicket(url, type);
		outWrite(resultMap);
		return null;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public AttendanceManage getAm() {
		return am;
	}
	public void setAm(AttendanceManage am) {
		this.am = am;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public Attendance getAttendance() {
		return attendance;
	}
	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}
	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

}
