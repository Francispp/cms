package com.cyberway.common.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.commoninfo.service.CommonInfoService;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.login.edu.Des;
import com.cyberway.common.service.BasicService;
import com.cyberway.core.objects.Constants;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.EncryptionHelper;
import com.cyberway.core.utils.RequestUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.utils.property.DefaultProperty;
import com.cyberway.core.web.BaseController;

public class LoginController extends BaseController {

	
	
	private String loginid;
	private String password;
	private String fromUrl;//登录成功后跳转的地址
	private String language;
	private String indexUrl;
	private String portalcode;
	private String login_error;
	private String style;
	private long random;//随机数
	private String j_username;
	private String j_password;
	private String siteUrlHttp;
	private long  channelId;

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	
	
	
	/* 进入登录
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		
		if(com.cyberway.cms.Constants.IS_REFUSE)
		{
		CommonInfoService commonInfoService = (CommonInfoService)ServiceLocator.getBean("commonInfoService");
		List<CoreCommonInfo> infos = commonInfoService.getCoreCommonInfos("IP限制");
		if(infos.size() == 0)
		{
			return INPUT;
		}
		for(CoreCommonInfo info : infos )
		{
			if(getHttpServletRequest().getRemoteAddr().matches(info.getContent()))
				return INPUT;	
		}
		return "refuse";
		}
		else
		 return INPUT;
	}
	
	
	/**
	 * 登录验证
	 * @return
	 * @throws Exception
	 */
	public String login()throws Exception{
		if(StringUtil.isEmpty(j_username)||StringUtil.isEmpty(j_password)){
			addActionError("账号或密码为空！");			
			return INPUT;
		}
		HttpServletRequest request=(HttpServletRequest)getActionContext().get(StrutsStatics.HTTP_REQUEST);
		siteUrlHttp=request.getServerName();
		BasicService service=(BasicService)this.getServiceById("basicService");
		SiteManagerService siteManagerService=(SiteManagerService)this.getServiceById("siteManagerService");
		
		String rt = SUCCESS;
	    CmsSite cmsSite=siteManagerService.getCmsSite(siteUrlHttp);
		Loginer loginer = service.login(j_username, j_password,portalcode);
		loginer.setSiteId(cmsSite.getOid());
		loginer.setSiteName(cmsSite.getSitename());
		//验证需要调整
		if(loginer!=null&&loginer.getLoginid()!=null){
			indexUrl = "/j_acegi_security_check?j_username="+j_username+"&j_password="+j_password+"&portalcode="+portalcode+"&siteId="+cmsSite.getOid()+"&siteName="; 
			 indexUrl+=java.net.URLEncoder.encode(cmsSite.getSitename(),"UTF-8");
			 
			 System.out.println(indexUrl);
			return rt;
		}else if(loginer!=null&&loginer.getLoginid()==null){

			loginer=service.logon(j_username, j_password,portalcode);
			loginer.setSiteId(cmsSite.getOid());
			loginer.setSiteName(cmsSite.getSitename());
			if (loginer.getLoginerStatus() == Loginer.NO_SUCH_ID) {
				addActionError("用户不存在或用户名与密码不匹配！");
				rt = INPUT;
			} else if (loginer.getLoginerStatus() == Loginer.THE_ID_IS_OVERTIME) {
				addActionError("用户过期！");
				rt = INPUT;
			} else if (loginer.getLoginerStatus() == Loginer.WRONG_PASSWORD) {
				addActionError("密码错误！");			
				rt = INPUT;
			} else {
				rt = SUCCESS;
			getSession().put(Loginer.LOGININFO_SESSION,loginer);
			RequestUtil.setCookie((HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE), "loginid", loginer.getLoginid(),
			        -1);
			RequestUtil.setCookie((HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE), "loginName",
			        loginer.getUsername(), -1);
			String style=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_DEFAULT);			
	        if(!StringUtil.isEmpty(loginer.getIndexStyle()))
	        	style=loginer.getIndexStyle();
	        	indexUrl=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_STYLE+"."+style,"index.action");
	            if(rt==SUCCESS && (!StringUtil.isEmpty(this.getFromUrl())))
	              {
			        rt= "tofrom";
		           }		
			}
			return rt;
		}
		return rt;		
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String logon()throws Exception{
		BasicService service=(BasicService)this.getServiceById("basicService");
		SiteManagerService siteService=(SiteManagerService)this.getServiceById("siteManagerService");
		Loginer loginer = service.logon(j_username, j_password,portalcode);
		loginer.setRoleCode("public");
		String rt;
		if (loginer.getLoginerStatus() == Loginer.NO_SUCH_ID) {
			addActionError("用户不存在或用户名与密码不匹配！");
			rt = INPUT;
		} else if (loginer.getLoginerStatus() == Loginer.THE_ID_IS_OVERTIME) {
			addActionError("用户过期！");
			rt = INPUT;
		} else if (loginer.getLoginerStatus() == Loginer.WRONG_PASSWORD) {
			addActionError("密码错误！");			
			rt = INPUT;
		} else {
			rt = SUCCESS;
		getSession().put(Loginer.LOGININFO_SESSION,loginer);
		RequestUtil.setCookie((HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE), "loginid", loginer.getLoginid(),
		        -1);
		RequestUtil.setCookie((HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE), "loginName",
		        loginer.getUsername(), -1);
		
		String style=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_DEFAULT);			
        if(!StringUtil.isEmpty(loginer.getIndexStyle()))
        	style=loginer.getIndexStyle();
        	indexUrl=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_STYLE+"."+style,"index.action");
            if(rt==SUCCESS && (!StringUtil.isEmpty(this.getFromUrl())))
              {
		        rt= "tofrom";
	           }		
		}
		//跳到指定url
	    if(rt==SUCCESS && (!StringUtil.isEmpty(this.getFromUrl()))){
			rt= "tofrom";
			return rt;
		}
		String siteHttp =getHttpServletRequest().getServerName();
		int siteport = getHttpServletRequest().getServerPort();
		indexUrl = com.cyberway.cms.Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL + siteHttp + (siteport == 80 ? "" :  ":" + siteport);
	
		return rt;		
	}
	/**
	 * 易联登录
	 * @return
	 * @throws Exception
	 */
	public String ebizlogon()throws Exception{
		String standing="";
		String level="";
		String userName="";
		String loginFrom ="";
		Boolean isOnline = false;
		//检测参数
		if(StringUtil.isEmpty(loginid)||StringUtil.isEmpty(style)){
			throw new Exception("登录参数不正确！");
		}
		Date curr_date=new Date();
		//比较是否在充许范围内
		long tparm=EncryptionHelper.getTimeParm(curr_date);	
		try{
		 String rm=EncryptionHelper.decrypt(style,EncryptionHelper.PASS_PHRASE);
		 //System.out.println("random:"+EncryptionHelper.decrypt(style,EncryptionHelper.PASS_PHRASE));
		 if(StringUtil.isNumber(rm)){
		   random = new Long(rm);	
		   }
		 }catch(Exception e){
				throw new Exception("参数非法，请检查是否正确！");
			}
		 if(random<=0)
			 throw new Exception("参数非法，请检查是否正确！");
		 
		 /*if(EncryptionHelper.revertTimeParm(tparm)<EncryptionHelper.revertTimeParm(random))
				throw new Exception("非法的操作！");*/
			if(EncryptionHelper.revertTimeParm(tparm)>EncryptionHelper.revertTimeParm(random)+1000*60*30){
				throw new Exception("操作已超时，请重新登录！");
			}

		try{		   
		   loginid=EncryptionHelper.decrypt(loginid,EncryptionHelper.getPassPhrase(curr_date,random)); 
		   if(loginid.indexOf("@") != -1)
			{
			   String st[] = loginid.split("@");
				if (st.length > 4)
					loginFrom = st[4];
				if (st.length > 3)
					level = st[3];
				if (st.length > 2)
					standing = st[2];
				if (st.length > 1)
					userName = st[1];
				if (st.length > 0)
					loginid = st[0];
			}
		 }catch(Exception e){
			throw new Exception("参数非法，请检查是否正确！");
		}
		if(StringUtil.isEmpty(loginid))
			throw new Exception("参数非法，请检查是否正确！");
		BasicService service=(BasicService)this.getServiceById("basicService");
		SiteManagerService siteService=(SiteManagerService)ServiceLocator.getBean("siteManagerService");
		portalcode="cms";
		CmsSite site = siteService.getSite(getHttpServletRequest().getServerName(),getHttpServletRequest().getServerPort());
		Loginer loginer = service.newEbizlogon(loginid, standing, level, site.getIsLogined());
		if(StringUtils.isNotBlank(userName))
			loginer.setUsername(userName);
		loginer.setRoleCode(standing+"_"+level);
		String rt;
		if (loginer.getLoginerStatus() == Loginer.NO_SUCH_ID) {
			addActionError("用户不存在或用户名不匹配！");
			rt = INPUT;
		} else if (loginer.getLoginerStatus() == Loginer.THE_ID_IS_OVERTIME) {
			addActionError("用户过期！");
			rt = INPUT;
		} else if (loginer.getLoginerStatus() == Loginer.WRONG_PASSWORD) {
			addActionError("密码错误！");			
			rt = INPUT;
		} else {
			rt = SUCCESS;
		getSession().put(Loginer.LOGININFO_SESSION,loginer);
		RequestUtil.setCookie((HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE), "loginid", URLEncoder.encode(loginer.getLoginid(),"utf-8"),
		        -1);
		RequestUtil.setCookie((HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE), "loginName",URLEncoder.encode(loginer.getUsername(),"utf-8"), -1);
		String style=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_DEFAULT);			
        if(!StringUtil.isEmpty(loginer.getIndexStyle()))
        	style=loginer.getIndexStyle();
        	indexUrl=DefaultProperty.getProperty(com.cyberway.common.base.objects.Constants.INDEX_PAGE_STYLE+"."+style,"index.action");
            if(rt==SUCCESS && (!StringUtil.isEmpty(this.getFromUrl())))
              {
            	if(this.getChannelId() > 0)
    				this.setFromUrl(this.getFromUrl()+"&channelId="+this.getChannelId());
		        rt= "tofrom";
	           }		
		}		
		
		String siteHttp =getHttpServletRequest().getServerName();
		int siteport = getHttpServletRequest().getServerPort();
		indexUrl = com.cyberway.cms.Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL + siteHttp + (siteport == 80 ? "" :  ":" + siteport);
		return rt;		
	}
	
	/**
	 * 页面跳转
	 * @author Dicky
	 * @time 2012-11-2 17:22:51
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String skipto() throws Exception{
		Loginer loginer = (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
		if(loginer!=null){
			String host = fromUrl.replace("http://", "");
			host = host.substring(0,host.indexOf("/"));
			SiteManagerService siteService = (SiteManagerService)this.getServiceById("siteManagerService");
			String[] ss = host.split(":");
			Integer port = 80;
			if(ss.length==2){
				port = Integer.valueOf(ss[1]);
			}
			CmsSite site = siteService.findByHttp(ss[0], port);
			if(site!=null){
				String roleCode = loginer.getRoleCode()!=null ? loginer.getRoleCode().replace("_", "@") : "";
				String loginno= loginer.getLoginid() + "@" + loginer.getUsername() + "@" + roleCode + "@易联网";
				Date curr_date = new Date();
				long tparm=EncryptionHelper.getTimeParm(curr_date);
				String after_encrypt=EncryptionHelper.encrypt(loginno,EncryptionHelper.getPassPhrase(curr_date,tparm));
				after_encrypt=EncryptionHelper.encodeURL(after_encrypt);
				String after_tparm= EncryptionHelper.encrypt(String.valueOf(tparm),EncryptionHelper.PASS_PHRASE);     
				after_tparm=EncryptionHelper.encodeURL(after_tparm);
				String redirect = "/cms/ebizlogon.action?loginid="+after_encrypt+"&style="+after_tparm;
				String form = "http://"+host+redirect+"&fromUrl="+URLEncoder.encode(fromUrl, "UTF-8");
				setFromUrl(form);
			}
		}
		return "tofrom";
	}
	
	/**
	 * 退出登录
	 * @return
	 * @throws Exception
	 */
	public String logout()throws Exception{
		if(getSession().containsKey(Loginer.LOGININFO_SESSION))
		 getSession().remove(Loginer.LOGININFO_SESSION);
		
		logger.info("退出登录!");
        if((!StringUtil.isEmpty(getFromUrl())))
        {
	        return  "tofrom";
         }
		return execute();
	}

	/**
	 * 设置语言类型
	 * @return
	 * @throws Exception
	 */
	public String language()throws Exception{
		if(!StringUtil.isEmpty(language)){
			getSession().put(Constants.LOCALE_LANGUAGE, language);
			if(language.endsWith(Constants.LANGUAGE_ZH_CN))
				  getActionContext().setLocale(Locale.CHINESE);
			    else if(language.endsWith(Constants.LANGUAGE_EN))
			    	getActionContext().setLocale(Locale.US);
			    else if(language.endsWith(Constants.LANGUAGE_ZH_TW))
			    	getActionContext().setLocale(Locale.TAIWAN);
			    else
			    	getActionContext().setLocale(Locale.CHINESE);
		}
		if(!StringUtil.isEmpty(style)){
			getSession().put(com.cyberway.core.Constants.STYLE_IN_SESSION, style);
		}
		logger.info("language:"+language);
		
		return "language";
	}
	
	/**
	 * 登录安利教育网
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String loginEducationNet() throws UnsupportedEncodingException {
		String actiurl = com.cyberway.cms.Constants.CMS_LOGIN_AMWAY_EDU_URL;
		String actisecretkey = com.cyberway.cms.Constants.CMS_LOGIN_AMWAY_EDU_ACTISECREKEY;
		Loginer loginer = (Loginer) getSession().get(Loginer.LOGININFO_SESSION);
		if(loginer != null)
		{
		//Long ada = 344703L;
		String ada = loginer.getLoginid();
		SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
		TimeZone tz = TimeZone.getDefault();
		int offset = tz.getRawOffset();
		Date now = new Date();
		now.setTime(now.getTime() - offset);
		String timeStr = format.format(now);
		//String userid = Des.toHexString(Des.encrypt(URLEncoder.encode(ada.toString(), "utf-8").toLowerCase(), actisecretkey));	
		String token = Des.MD5EnCrypt(ada + timeStr + actisecretkey);
		
		setFromUrl(actiurl + "?userid=" + ada + "&timestamp=" + URLEncoder.encode(timeStr, "utf-8").toLowerCase() + "&token=" + token);
		}
		else
			setFromUrl(actiurl);
		return "tofrom";
	}
	
	public String getFromUrl() {
		return fromUrl;
	}
	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getIndexUrl() {
		return indexUrl;
	}
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}
	public String getPortalcode() {
		return portalcode;
	}
	public void setPortalcode(String portalcode) {
		this.portalcode = portalcode;
	}
	public String getLogin_error() {
		return login_error;
	}
	public void setLogin_error(String login_error) {
		this.login_error = login_error;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public long getRandom() {
		return random;
	}
	public void setRandom(long random) {
		this.random = random;
	}
	public String getJ_username() {
		return j_username;
	}
	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}
	public String getJ_password() {
		return j_password;
	}
	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}
	public String getSiteUrlHttp() {
		return siteUrlHttp;
	}


	public void setSiteUrlHttp(String siteUrlHttp) {
		this.siteUrlHttp = siteUrlHttp;
	}
	
}
