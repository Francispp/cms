package com.cyberway.core.acegi.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyberway.core.Constants;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.Portal;
import com.cyberway.core.utils.RequestUtil;
import com.cyberway.core.utils.StringUtil;

/**
 * 把User变量放入http session变量中,key为Constants.USER_IN_SESSION
 * 
 * @author cac
 */
public class UserAuthenticationProcessingFilter extends
		AuthenticationProcessingFilter {

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 用户管理对象
	 */
	private MyUserManager myUserManager;

	public void setMyUserManager(MyUserManager myUserManager) {
		this.myUserManager = myUserManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acegisecurity.ui.AbstractProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest)
	 */
	public Authentication attemptAuthentication(HttpServletRequest request)
			throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		// Place the last username attempted into HttpSession for views
		request.getSession().setAttribute(ACEGI_SECURITY_LAST_USERNAME_KEY,
				username);
    	//增加获得登录站点
		String portalid=request.getParameter(Constants.PORTAL_CODE);
        if(portalid==null||portalid.length()==0)
        	portalid=Constants.DEFAULT_PORTAL_CODE;
        request.getSession().setAttribute(Constants.PORTAL_CODE,portalid);
        
        String siteId=request.getParameter(Constants.SITEID);
        String siteName=StringUtil.toUTF8(request.getParameter(Constants.SITENAME));
        request.getSession().setAttribute(Constants.SITEID,siteId);
        request.getSession().setAttribute(Constants.SITENAME,siteName);
        //getAuthenticationManager().authenticate(authRequest)
		return getAuthenticationManager().authenticate(authRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.acegisecurity.ui.AbstractProcessingFilter#requiresAuthentication(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		boolean requiresAuth = super.requiresAuthentication(request, response);

		HttpSession httpSession = null;
		try {
			httpSession = request.getSession(false);
		} catch (IllegalStateException ignored) {
		}

		if (httpSession != null) {
			//if (httpSession.getAttribute(Constants.USER_IN_SESSION) == null) {
				if (!requiresAuth) {
					SecurityContext sc = SecurityContextHolder.getContext();
					Authentication auth = sc.getAuthentication();
					if (auth != null
							&& auth.getPrincipal() instanceof UserDetails) {
						UserDetails ud = (UserDetails) auth.getPrincipal();
						auth.getAuthorities();
						Loginer loginer = (Loginer)httpSession.getAttribute(Constants.USER_IN_SESSION);
						/*
						 * 已通过acegi登录
						 * loginer 为空
						 * acegi登录用户名与loginer中用户名不一致时再重新获得用户信息
						*/
						if(ud !=null && (loginer==null || !ud.getUsername().equalsIgnoreCase(loginer.getLoginid()))){
						// 登录者信息
						 loginer = myUserManager.getLoginerByLoginidAndPasswd( ud.getUsername(), ud
										.getPassword());
						//获得管理登录站点信息
						String portalcode=(String)httpSession.getAttribute(Constants.PORTAL_CODE);
						if(portalcode==null || portalcode.length()==0){
							portalcode=Constants.DEFAULT_PORTAL_CODE;						
						}
						Portal portal =myUserManager.getPortalByPortalcode(portalcode);
						//
						loginer.setPortal(portal);
						String siteId=(String)httpSession.getAttribute(Constants.SITEID);
						String siteName=(String)httpSession.getAttribute(Constants.SITENAME);
						
						loginer.setPortals(myUserManager.getPortalsByLoginer(loginer));
						//loginer.setPermissions(myUserManager.getPermissionsStringByUserid(loginer.getUserid()));
						loginer.setSiteId(Long.parseLong(siteId));
						loginer.setSiteName(siteName);
						//放入角色信息
						loginer.setRoles(myUserManager.getRolesByUserid(loginer.getUserid()));
						RequestUtil.setCookie(response, "loginid", loginer.getLoginid(),-1);
						RequestUtil.setCookie(response, "loginName",loginer.getUsername(), -1);
						httpSession.setAttribute(Constants.USER_IN_SESSION,
								loginer);

					}
				 }
				}/*else{//验证未通过 
					if (httpSession.getAttribute(Constants.USER_IN_SESSION) != null) {
						httpSession.setAttribute(Constants.USER_IN_SESSION,	null);
					}
				}*/
			//}
		}
		return requiresAuth;
	}
}
