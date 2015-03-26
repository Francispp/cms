package com.cyberway.weixin.business.attendance.controller;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;

import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.weixin.business.attendance.domain.LegworkPlan;
import com.cyberway.weixin.business.attendance.service.LegworkPlanService;
import com.cyberway.weixin.oauth.Oauth2Util;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;
import com.cyberway.weixin.util.JSSDKUtil;
import com.cyberway.weixin.util.WXLoginer;
import com.opensymphony.xwork2.Preparable;
/**
 * 
 * @com.cyberway.weixin.business.attendance.controller.LegworkPlanController
 * TODO :外勤计划
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年3月23日 上午10:52:34
 */
public class LegworkPlanController extends BaseBizController<LegworkPlan> implements Preparable{

	/**  
	* serialVersionUID:TODO（用一句话描述这个变量表示什么）
	*/  
	private static final long serialVersionUID = 1L;
	LegworkPlanService  service = (LegworkPlanService) this.getServiceById("legworkPlanService");
	//wx.config参数
	private String config;
	//重定向获取user
	private String redirectUrl ;
	//用户名称
	private WXLoginer wxloginer;
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub 未完成 by yanruqian 2015年3月23日
		
	}

	@Override
	protected EntityDao<LegworkPlan> getService() {
		// TODO Auto-generated method stub 未完成 by yanruqian 2015年3月23日
		return service;
	}
	/**
	 * http://wx.cyberway.com.cn:8084/weixin/legworkPlan!doIndex.action
	*  <p>TODO (这里用一句话描述这个方法的作用)</p>
	 * @return
	 */
	public String doIndex(){
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
		wxloginer = CommonUtil.findByUserId(userId);
		config = JSSDKUtil.getJsConfig(url,false);
		return "index";
	}
	/**
	 * 用户查询自己已经创建好的外勤计划
	 * @return
	 * @throws Exception
	 */
	public String doList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("userId", domain.getUserId());
		criteria.setInOrder(Order.desc("createDate"));
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	/**
	 * 保存外出计划
	*  <p>TODO (这里用一句话描述这个方法的作用)</p>
	 * @return
	 * @throws Exception 
	 */
	public String doSave() throws Exception{
		if(domain.getStartDate() !=null) {
			if(StringUtils.isNotBlank(domain.getFieldPlace())) {
				domain = getService().saveOrUpdate(domain);
				addActionMessage("保存成功!");
				return doList();
			}else{
				addActionMessage("外出地点不能为空!");
				return "index";	
			}
		}else {
			addActionMessage("开始时间不能为空!");
			return "index";
		}
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
