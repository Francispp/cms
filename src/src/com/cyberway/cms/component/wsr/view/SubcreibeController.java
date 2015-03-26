package com.cyberway.cms.component.wsr.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;

import org.hibernate.criterion.Order;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.component.webuser.service.WebUserService;
import com.cyberway.cms.component.wsr.domain.Subcreibe;
import com.cyberway.cms.component.wsr.service.SubcreibeService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.web.BaseBizController;

public class SubcreibeController extends BaseBizController<Subcreibe> {
	private Long channelId;
	private String email;
	private String username;
	private Date startDate;
	private Date endDate;
	
	
	private Map<Long,String> yesno = new HashMap<Long,String>();
	

	SubcreibeService service = (SubcreibeService)this.getServiceById("SubcreibeService");
	ChannelManagerService channelService=(ChannelManagerService) getServiceById("channelManagerService");
	WebUserService webUserService = (WebUserService) this.getServiceById("WebUserService");
	SiteManagerService siteService=(SiteManagerService) getServiceById("siteManagerService");
	
	@Override
	protected EntityDao<Subcreibe> getService() {
		return  service;
	}
	
	/**
	 * 列表使用的action
	 */
	public String list() throws Exception{
		CriteriaSetup setup  = new CriteriaSetup();
		setup.setInOrder(Order.desc("leaveTime"));
		//设默认 以时间，单位 排序  
		doListEntity(setup, getTableId(), Page.DEFAULT_PAGE_SIZE);
		
		return LIST_RESULT;
	}
	
	
	/**
	 * 订阅保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception{
		//添加默认值 
		if(getEmail()!=null)
			this.domain.setUserEmail(getEmail()); 
		
		//判断当前邮件地址是否已经定阅过此频道
		boolean issubcreibe = service.checkScByEmailAndChannnelId(getEmail(),getChannelId());
		
		if(issubcreibe){
			getHttpServletRequest().setAttribute("msg","当前频道已经订阅！");
			return "subc";
		}
		
		saveOrUpdate();
		
		getHttpServletRequest().setAttribute("msg","保存成功！");
		 
		return "subc";
	}
	/**
	 * 外部用户在线订阅
	 * @return
	 * @throws Exception
	 */
	public String rss()throws Exception
	{
		Loginer loginer = (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
		WebUser webUser = null;
		if(loginer != null && loginer.getLoginType() == 2)
		{
			webUser = webUserService.get(loginer.getUserid());
		}
		if(getChannelId() != null && webUser != null)
		{
			Channel channel = channelService.getChannelFromCache(channelId);
			
			if(channel != null)
			{
				CmsSite site = siteService.getSiteFromCache(channel.getSite().getOid());
				domain = Subcreibe.class.newInstance();
				domain.setApproved(1l);
				domain.setChannelId(channelId);
				domain.setChannelName(channel.getName());
				domain.setLocked(1l);
				domain.setRemoteAddr("");
				domain.setSiteId(channel.getSite().getOid());
				domain.setSiteName(site.getSitename());
				domain.setSubcreibeDate((getStartDate() != null)? getStartDate() : new Date());
				domain.setLastSubcreibeDate((getEndDate() != null)? getEndDate() : new Date());
				domain.setUserEmail(webUser.getEmail());
				domain.setUserId(webUser.getOid());
				domain.setUserName(webUser.getName());
				boolean issubcreibe = service.checkScByEmailAndChannnelId(webUser.getEmail(),getChannelId());
				if(!issubcreibe)
				service.saveOrUpdate(domain);
				
			}
		}
		return "subc";
	}
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception{
		if(this.domain.getId()== null) 
			init(); 
		
		//添加默认值  
		super.saveOrUpdate();
		return "edit";
		 
	}
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String intoSubcreibe() throws Exception{
		Loginer loginer = (Loginer)getHttpServletRequest().getSession().getAttribute(Loginer.LOGININFO_SESSION);
		if(loginer != null && loginer.getLoginType() == 2){
			setEmail(loginer.getUserEmail()); 
			setUsername(loginer.getUsername());
		}else{
			setUsername("匿名");
		}
		
		return "subc";
	}
	
	private void init() throws Exception{
		 
		this.domain.setLocked(0l);
		this.domain.setApproved(0l); 
		
		if(domain.getSubcreibeDate() == null)
			this.domain.setSubcreibeDate(new Date());
		if(domain.getRemoteAddr() == null)
			this.domain.setRemoteAddr(getHttpServletRequest().getRemoteHost()); 
		
		if(this.domain.getSiteId() == null || this.domain.getSiteId().longValue() <=0){
			//获取站点信息
			CmsSite site  = ((SiteManagerService) this.getServiceById("siteManagerService")).getSite(getHttpServletRequest().getServerName(),0);
			if(site != null){
				this.domain.setSiteId(site.getOid());
				this.domain.setSiteName(site.getSitename());
			}
		}
		 
		if(channelId != null && this.domain.getChannelId()== null){
			//获取站点信息 
			Channel channel  = ((ChannelManagerService) this.getServiceById("channelManagerService")).get(channelId);
			if(channel != null){
				this.domain.setChannelId(channelId);
				this.domain.setChannelName(channel.getName());
			}
		}
		
		Object temp = getHttpServletRequest().getSession().getAttribute(Loginer.LOGININFO_SESSION);
		if(this.domain.getUserName() == null  && temp != null){
			//判断是否登录
			Loginer loginer = (Loginer)temp;
			//WEB登录
			if(loginer != null && loginer.getLoginType() == 2){
				this.domain.setUserName(getUsername());
				this.domain.setUserId(loginer.getUserid().longValue()); 
			}
		}
	}

	public Long getChannelId() {
		return channelId;
	}
	
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<Long, String> getYesno() {
		yesno.put(0l,"否");
		yesno.put(1l,"是");
		return yesno;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	} 
}
