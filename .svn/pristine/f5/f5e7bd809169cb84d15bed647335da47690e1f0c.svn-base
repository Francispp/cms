package com.cyberway.weixin.business.field.controller;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.business.field.domain.Field;
import com.cyberway.weixin.business.field.service.FieldService;
import com.cyberway.weixin.oauth.Oauth2Util;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;
import com.cyberway.weixin.util.JSSDKUtil;
import com.cyberway.weixin.util.WXLoginer;
/**
 * 
 * @com.cyberway.weixin.business.field.controller.FieldController
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年3月19日 上午9:48:39
 */
public class FieldController extends BaseBizController<Field>{
	FieldService service = (FieldService) this.getServiceById("fieldService");
	@Override
	protected EntityDao<Field> getService() {
		return service;
	}
	//页面传来的外勤ID
	private Long fieldId;
	//wx.config参数
	private String config;
	//重定向获取user
	private String redirectUrl ;
	//用户名称
	private WXLoginer wxloginer;
	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	/**
	 * 用户查询自己已经创建好的外勤计划
	 * @return
	 * @throws Exception
	 */
	public String doList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("userId", domain.getUserId());
		criteria.setInOrder(Order.desc("createTime"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	/**
	 * 查询外勤计划
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		if(domain.getStartDate() !=null) {
			criteria.addFilter("startDate", domain.getStartDate());
		}
		if(domain.getEndDate() !=null) {
			criteria.addFilter("endDate", domain.getEndDate());
		}
		criteria.setInOrder(Order.desc("createTime"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	/**
	 * 实时查岗
	 * @return
	 * @throws Exception
	 */
	public String nowList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		if(domain.getStartDate() !=null) {
			criteria.addFilter("startDate", domain.getStartDate());
		}
		if(domain.getEndDate() !=null) {
			criteria.addFilter("endDate", domain.getEndDate());
		}
		criteria.setInOrder(Order.desc("createTime"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
//	public String edit() throws Exception {
//		return super.edit();
//	}
//	
	/**
	 * 
	 *  <p>TODO 外勤计划跳转页面</p>
	 * @return
	 * @throws Exception 
	 */
	public String legworkPlan() throws Exception{
		String userId = getWxUserId();
		String url = getRequest().getRequestURL().toString();
		if(StringUtils.isBlank(userId)){
			String code = getRequest().getParameter("code");
			if(StringUtils.isBlank(code)){
				redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2cc405f9858fc8&redirect_uri="+url+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
				return "redirect_getuser";//重定向获取user
			}else{
				userId = Oauth2Util.getUserID(code, Constants.AGENTID);
				Cookie coo = new Cookie("user",userId);
				getResponse().addCookie(coo);
				String state = getRequest().getParameter("state");
				url = url+"?code="+code+"&state="+state;
			}
		}else{
			wxloginer = CommonUtil.findByUserId(userId);
		}
		config = JSSDKUtil.getJsConfig(url,false);
		return EDIT_RESULT;
	}
	public String openBaiduMap(){
		
		return "map";
	}
	/**
	 * 提交：保存新建外勤计划
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception {
		if(domain.getStartDate() !=null) {
			if(null != domain.getFieldPlace() 
				 && !"".equals(domain.getFieldPlace().trim())) {
				domain = getService().saveOrUpdate(domain);
				addActionMessage("保存成功!");
				return doList();
			}else{
				addActionMessage("外出地点不能为空!");
				return super.edit();	
			}
		}else {
			addActionMessage("开始时间不能为空!");
			return super.edit();
		}
	}
	
	/**
	 * 提交并签到：保存新建外勤计划并跳转到外勤签到页面
	 * @return
	 * @throws Exception
	 */
	public String saveToSignIn() throws Exception {
		domain = getService().saveOrUpdate(domain);
		return "signIn";
	}
	public String doSave(){
		Integer type = Integer.parseInt(getRequest().getParameter("type"));
		Field field = getDomain();
		if(field.getId()!=null){
			
		}else{
			String userId = getWxUserId();
			if(StringUtils.isNotBlank(userId)){
				String userName = CommonUtil.findByUserId(userId).getName();
				field.setUserId(userId);
				field.setUserName(userName);
			}
			field.setSignInTime(new Date());
			setDomain(getService().saveOrUpdate(field));
		}
		return "signin_share";//签到成功跳转页面
	}
	/**
	 * 签到
	 * @return
	 * @throws Exception
	 * 
	 */
	public String signIn() throws Exception {
//		domain = service.get(fieldId);
//		String place = domain.getSignInPlace();
//		Date time = domain.getSignInTime();
//		String text = domain.getSignInTxt();
//		String picture = domain.getSignInPic();
//		service.signIn(id, place, time, text, picture);
		String userId = getWxUserId();
		String url = getRequest().getRequestURL().toString();
		if(StringUtils.isBlank(userId)){
			String code = getRequest().getParameter("code");
			if(StringUtils.isBlank(code)){
				redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2cc405f9858fc8&redirect_uri="+url+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
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
		return "signin";
	}
	
	/**
	 * 签退
	 * @return
	 * @throws Exception
	 */
	public String signOut() throws Exception {
		domain = service.get(fieldId);
		String place = domain.getSignOutPlace();
		Date time = domain.getSignOutTime();
		String text = domain.getSignOutTxt();
		String picture = domain.getSignOutPic();
		service.signOut(id, place, time, text, picture);
		return "success";
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public WXLoginer getWxloginer() {
		return wxloginer;
	}

	public void setWxloginer(WXLoginer wxloginer) {
		this.wxloginer = wxloginer;
	}


}
