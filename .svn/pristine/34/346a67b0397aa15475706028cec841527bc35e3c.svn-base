package com.cyberway.cms.web.filter;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.staticResource.service.StaticResourceService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.common.commoninfo.service.CommonInfoService;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class WebFilter extends HttpServlet implements Filter {
	protected Log logger = LogFactory.getLog(getClass());
	private static final String PERMISSION_DENY_PAGE = "/errors/accessDenied.jsp";
	private static final String SITE_INDEX_URL="/cms/index.action";//首页模板访问地址 "/cms/index.action" "/test/sample.jsp"
	private static final String SITE_LIST_URL="/cms/docInfo!list.action";//概榄模板访问地址
	private static final String SITE_DETAIL_URL="/cms/docInfo!view.action";//细榄模板访问地址
	private FilterConfig filterConfig;

	// Handle the passed-in FilterConfig
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	// Process the request/response pair
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) throws IOException,ServletException {
		try{	
			boolean b=filterMapping(request, response);
			filterChain.doFilter(request, response);
		}catch(Exception e){
			//logger.error(e);
		}
	}

	// 地址跳转
	private void setForward(String url, ServletRequest request,
			ServletResponse response)throws IOException,ServletException{
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse servletResponse= (HttpServletResponse)response;
		servletResponse.sendRedirect(hreq.getContextPath()+getUrlAndParameters(hreq,url));
		return ;
	}
    /**
     * 获得跳转地址，同时增加原request的参数
     * @param hreq
     * @param url
     * @return
     */
    private String getUrlAndParameters(HttpServletRequest hreq,String url){
    	
    	Map parms =hreq.getParameterMap();
    	Iterator it=parms.keySet().iterator();
    	StringBuffer sb=new StringBuffer();
    	sb.append(url);
    	while(it.hasNext()){//增加其他参数
    		String parmname=(String)it.next();
    		if(url.indexOf("&"+parmname+"=")<0 && url.indexOf("?"+parmname+"=")<0){
    			sb.append("&").append(parmname).append("=").append(hreq.getParameter(parmname));
    		}
    	}
    	return sb.toString();
    }
	/**
	 * 跳转
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private boolean filterMapping(ServletRequest request, ServletResponse response)throws Exception {
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;
		String currentURL =hreq.getRequestURI().trim();
		currentURL = currentURL.replaceAll("//", "/");
		String contextPath = hreq.getContextPath();//返回 "/webroot"
		if (currentURL.startsWith(contextPath)) {
			currentURL = currentURL.substring(contextPath.length());
		}
		Loginer loginer = (Loginer)hreq.getSession().getValue(Loginer.LOGININFO_SESSION);
		  if(loginer == null)
	       {
			  String _localRole=request.getParameter("__localRole");
			  if(!StringUtil.isEmpty(_localRole) && isLocalHost(getIpAddr(hreq))){
				  Loginer _loginer=new Loginer();
				  _loginer.setRoleCode(_localRole);
				  _loginer.setUserid(Constants.PUBLIC_USERID);
				  _loginer.setLoginid(Constants.PUBLISH_USER);
				  RoleManagerService roleManagerService=(RoleManagerService)ServiceLocator.getBean("roleManagerService");
				 _loginer.setRoles(roleManagerService.getRolesByRoleCode(_localRole));
				 javax.servlet.http.HttpSession session=hreq.getSession();
				  session.putValue(Loginer.LOGININFO_SESSION,_loginer);
				 session.setMaxInactiveInterval(5);//存活时间5秒
				  //logger.info(" 静态生成登录  --->\n currentURL-->"+currentURL + "\n " + _loginer.toString());
			  }else if(StringUtil.ifEqual(currentURL, "/j_acegi_logout")){
				  hres.sendRedirect(contextPath + "/login!logout.action");
			  }
	      }
		
		
       //同步/uploads/下的资源
		if(currentURL.indexOf("/uploads/") !=-1)
		{
			String fileURL = currentURL.substring(currentURL.lastIndexOf("/uploads/"), currentURL.length());
			if(Constants.IS_REALPATH){
				String sourcePath = Constants.ABSOLUTE_PATH + fileURL;
				FileUtil.update(sourcePath, request.getRealPath(fileURL));
				return false;
			}
		}
		SiteManagerService siteService=(SiteManagerService)ServiceLocator.getBean("siteManagerService");
		//FIXME  2012-9-14 12:08:13 Dicky
//		CmsSite site=siteService.getSite("10.140.208.172",hreq.getServerPort());
		CmsSite site=siteService.getSite(hreq.getServerName(),hreq.getServerPort());
		if(site==null)//可直接通过ip地址访问后台
			return false;
		
		//若为图片，且不为静态资料直接返回
		if(currentURL.endsWith(".gif") && currentURL.indexOf("images")<0)
			return false;
		
			String lastUrl = currentURL;
			String channelPath=null;
			String optName=null;
			if (lastUrl.startsWith("/")) {
				lastUrl = lastUrl.substring(1);
			}
			if (lastUrl.endsWith("/")) {
				lastUrl = lastUrl.substring(0, lastUrl.length() - 1);
			}		
			if (lastUrl.equals("")||lastUrl.startsWith("index.htm")||lastUrl.startsWith("index.html")) { // 表示站点浏览
				if (site !=null && site.getIspublished() != null && site.getIspublished().intValue() == 1) {
					return filterSiteMapping(site, request, response);
				} else {
					hres.sendRedirect(contextPath + PERMISSION_DENY_PAGE);
				}
			} else {//访问站点下其他页面
				ChannelManagerService channelService=(ChannelManagerService)ServiceLocator.getBean("channelManagerService");
				Channel channel=null;
				currentURL = lastUrl;
				if (lastUrl.indexOf(".") > 0) {
					int po = lastUrl.lastIndexOf("/");
					if (po > 0) {
						channelPath = lastUrl.substring(0, po);
						lastUrl = lastUrl.substring(po + 1);
					}
					int commaPO = lastUrl.lastIndexOf(".");			
					currentURL = lastUrl;
					String extName = lastUrl.substring(commaPO + 1);
					lastUrl = lastUrl.substring(0, commaPO);
					if(extName.trim().equals(com.cyberway.cms.Constants.SITE_URL_LIST_POSTFIX)||
							extName.trim().equalsIgnoreCase(com.cyberway.cms.Constants.SITE_URL_DETAIL_POSTFIX)){
					
					channel=channelService.getChannelByPath(site.getOid(), channelPath);
					Long templateid=null;
					if(channel == null)
						throw new Exception("路径对应的频道不存在！");
					if (extName.trim().equalsIgnoreCase(com.cyberway.cms.Constants.SITE_URL_LIST_POSTFIX)) { //浏览频道，指定的概览模板
						String formName = lastUrl;
						
						if(StringUtil.isEmpty(formName))
							templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_SUMMARY);//channel.getSummaryTemplate().getId();
						else {
							TemplateManagerService templateService=(TemplateManagerService)ServiceLocator.getBean("templateManagerService");
							Template template=templateService.getTemplateByName(site.getOid(),formName);
							if(template==null)
								throw new Exception("指定的模板名不存在！");
							templateid=template.getId();
						}
						if ((templateid==null))
							throw new Exception("概览模板不存在！");
						if (channel.getIspublished()==1) {//检测是否发布
							return filterChannelMapping(SITE_LIST_URL+"?templateId="
									+ templateid + "&channelId="
									+ channel.getId(),channel, request,
							response);
						} else {
							hres.sendRedirect(contextPath
									+ PERMISSION_DENY_PAGE);
						}	
					}else if (extName.trim().equalsIgnoreCase(com.cyberway.cms.Constants.SITE_URL_DETAIL_POSTFIX)) {//细览模板
						String docID = lastUrl.trim();
						if(!StringUtil.isEmpty(request.getParameter("templateName"))){//指定了模板名称
						 TemplateManagerService templateService=(TemplateManagerService)ServiceLocator.getBean("templateManagerService");
						 String formName=request.getParameter("templateName");
						 Template template=templateService.getTemplateByName(site.getOid(),formName);
						 if(template==null)
							throw new Exception("指定的模板名不存在！");
						templateid=template.getId();						
						}else
							templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_DETAILS);//channel.getDetailsTemplate().getId();
						if ((templateid==null))
							throw new Exception("未设置默认细览模板或模板不存在！");	
						/*if(site.getIshtml()==1){//站点发布静态html，则直接跌转到html地址
							SiteHtmlHelp htmlHelp=(SiteHtmlHelp)ServiceLocator.getBean("siteHtmlHelp");
							request.getRequestDispatcher(htmlHelp.getHtmlPath(channel,docID)).forward(request, response);
							//return filterChannelMapping(htmlHelp.getHtmlPath(channel,docID), channel,request, response);
							
						}*/
						//request.getRequestDispatcher("/login.action").forward(request, response);
						// return true;
						 return filterChannelMapping(this.SITE_DETAIL_URL+"?id=" + docID + "&templateId="+templateid+"&channelId="
								+ channel.getId(), channel,request, response);
					}else{//其他资源						
							/*filterResourceMapping(,lastUrl, site.getOid(), 
									request, response);*/ //浏览静态资源						
					}
					}else{//其他资源
						
							filterResourceMapping(channelPath,currentURL, site.getOid(), request, response); //浏览静态资源
							
					}	
				} else {//浏览频道，缺省的概览模板
					if(lastUrl.endsWith("j_acegi_security_check")||lastUrl.endsWith("j_acegi_logout"))
						return false;
					if(lastUrl.endsWith("ajax"))
						return false;
					if(lastUrl.endsWith("/list"))
						lastUrl=lastUrl.replaceAll("/list", "");
					channel=channelService.getChannelByPath(site.getOid(), lastUrl);
					if ((channel != null)){
						Long templateid;
						if(isPc((HttpServletRequest)request)){
							templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_SUMMARY);
						}else{
							templateid=channelService.getDefualutTemplateId(channel.getId(), Template.TYPE_SUMMARY_WAP);
						}
						if(!StringUtil.isEmpty(request.getParameter("templateName"))){//指定了模板名称
							TemplateManagerService templateService=(TemplateManagerService)ServiceLocator.getBean("templateManagerService");
							Template template=templateService.getTemplateByName(site.getOid(),request.getParameter("templateName"));
							if(template==null) {
								throw new Exception("指定的模板名不存在！");
							}
							templateid=template.getId();						
						}
						if (templateid != null)
						{
							//throw new Exception("此页面不存在！");
							if (channel.getIspublished()==1) {//检测频道是否发布，参数是否合法
								return filterChannelMapping(this.SITE_LIST_URL+"?templateId=" + templateid + "&channelId=" + channel.getId(),channel, request,
										response);
							} else {
								hres.sendRedirect(contextPath
										+ PERMISSION_DENY_PAGE);
							}
						}
					
					}else{//若地址为其他类型
						
					}
				}
			}
		return false;

	}

	//站点首页跳转
	private boolean filterSiteMapping(CmsSite site,ServletRequest request, ServletResponse response) throws Exception {
		StringBuffer url=new StringBuffer();
		 url.append(SITE_INDEX_URL).append("?siteId=").append(site.getOid());		
		 String tempId=request.getParameter("templateId");
		 String tempName=request.getParameter("templateName");
		 //System.out.println("__"+tempId+"__"+tempName);
		 //若模板名称和模板id为空，取默认模板
		 if(isPc((HttpServletRequest)request)){
			 if(StringUtil.isEmpty(tempName) && StringUtil.isEmpty(tempId)){
				 if(site.getTemplate().getId()==null)
					new Exception("未设置首页模板！");
				  tempId=site.getTemplate().getId().toString();
				}
		 }else{
			 if(StringUtil.isEmpty(tempName) && StringUtil.isEmpty(tempId)){
				 if(site.getTemplateWap().getId()==null)
					new Exception("未设置首页模板！");
				  tempId=site.getTemplateWap().getId().toString();
				}
		 }
		
		if(!StringUtil.isEmpty(tempId))
		 url.append("&templateId=").append(tempId);
		setForward(url.toString(), request, response);
      return true;
	}
	//频道跳转
	private boolean filterChannelMapping(String url,Channel channel,ServletRequest request, ServletResponse response) throws Exception {
	
		setForward(url, request, response);
		return true;
	}
	//访问资源
	private boolean filterResourceMapping(String path,String currentURL, Long siteID,ServletRequest request, ServletResponse response)
			throws Exception {
		if(StringUtil.isEmpty(path)||path.equalsIgnoreCase("images")||path.endsWith("/images")//图片
				||path.equalsIgnoreCase("scripts")||path.endsWith("/scripts")//js
				||path.equalsIgnoreCase("css")||path.endsWith("/css")//css
				)
		{//若路径为空，或为images时，可能为静态资源
		 StaticResourceService resService=(StaticResourceService)ServiceLocator.getBean("staticResourceService");
		 String currentURL1=resService.getStaticResourcePath(siteID, currentURL);
		 HttpServletRequest hreq = (HttpServletRequest) request;
		 if(!StringUtil.isEmpty(currentURL1))
			 request.getRequestDispatcher(hreq.getContextPath()+currentURL1).forward(request, response);
           return true;
		}
		return false;
	}

	//Clean up resources
	public void destroy() {
		filterConfig = null;
	}
	private boolean isPc(HttpServletRequest request){
		String user=request.getHeader("User-Agent");
		System.out.println(user);
		if(user!=null&&user.toLowerCase().indexOf("mozilla")==0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取真实IP地址
	 * @author Dicky
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {  
       String ip = request.getHeader("x-forwarded-for");  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("Proxy-Client-IP");  
       }  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("WL-Proxy-Client-IP");  
       }  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getRemoteAddr();  
       }  
       return ip;  
	}
	
	/**
	 * 是否本机地址
	 * @author Dicky
	 * @time 2012-9-14 20:45:49
	 * @version 1.0
	 * @param ipAddr
	 * @return
	 */
	private boolean isLocalHost(String ipAddr) {
		Enumeration<NetworkInterface> allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if(ip.getHostAddress().equals(ipAddr)){
							return true;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return false;
	}
}
