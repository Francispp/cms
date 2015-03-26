package com.cyberway.common.permission.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.memory.UserAttribute;
import org.apache.commons.lang.StringUtils;

import com.cyberway.common.domain.CorePermission;
import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.domain.CoreResource;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.core.Constants;
import com.cyberway.core.acegi.AcegiConstants;
import com.cyberway.core.acegi.cache.AcegiCacheManager;
import com.cyberway.core.acegi.resource.Resource;
import com.cyberway.core.acegi.resource.ResourceDetails;

/**
 * 对权限缓存进行统一管理
 * 
 * @author Administrator
 * 
 */
public class AuthorityManager {

	/**
	 * 对缓存进行统一管理
	 */
	private AcegiCacheManager acegiCacheManager;

	/**
	 * @param acegiCacheManager
	 *            the acegiCacheManager to set
	 */
	public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
		this.acegiCacheManager = acegiCacheManager;
	}
    public static String rolePrefix = "ROLE_";
	/**
	 * 重置用户的权限缓存
	 * 
	 * @param userLogid
	 * @param portalCode
	 *            当前角色处于那个门户下
	 * @param password
	 * @param isEnabled 当前用户是否有效
	 * @param roleCode
	 * @param isAuth
	 *            是授权还是撤销授权
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void renovateUser(String userLogid, String portalCode,
			String password, boolean isEnabled, String roleCode, boolean isAuth) {

		//跨网站的权限ＩＤ处理
		String cacheKey = getCacheKey(userLogid,portalCode);
		String rolekey=getRoleKey(roleCode);
		UserDetails userDetails = this.acegiCacheManager.getUser(cacheKey);
		
		//权限集合
		List authorities = new Vector();
		if(userDetails!=null){
			GrantedAuthority[] authoritys = userDetails.getAuthorities();
			for (GrantedAuthority auth : authoritys) {
	
				if (isAuth) {
					// 授权操作，保留原有权限
					authorities.add(auth);
				} else {
					// 撤销授权，保留不是当前角色的权限
					String authority = auth.getAuthority();
					if (authority.equals(rolekey) == false) {
						authorities.add(auth);
					}
				}
			}
		}

		// /授权操作，增加新权限
		if (isAuth) {
			GrantedAuthority authority = new GrantedAuthorityImpl(rolekey);
			authorities.add(authority);
		}

		//转为数组
		GrantedAuthority[] newAuthoritys = convertAuthorities(authorities);

		//新的权限用户对象
		userDetails = new User(cacheKey, password, isEnabled, true, true,
				true, newAuthoritys);
		
		// ------renovate cache methods
		this.acegiCacheManager.renovateUser(cacheKey, userDetails);

	}
	
	/**
	 * 重置用户缓存
	 * @param orgLogid 原有的用户登录ID
	 * @param user 当前的用户对象
	 */
	@SuppressWarnings("unchecked")
	public void renovateUser(String orgLogid,CoreUser user){
		
		if(user == null) return;
		
		Set roleSet = user.getCoreUserRoles();
		if(roleSet == null || roleSet.isEmpty()) return;

		//当前用户所有的角色按网站分类
		Map<String,List> authMap = new HashMap();

		Iterator<CoreRole> roles = roleSet.iterator();
		while(roles.hasNext()){
			CoreRole role = roles.next();
			CoreOrg portal = role.getCoreOrg();
			String portalCode = portal.getOrgCode();
			
			List authoritys = authMap.get(portalCode);
			if(authoritys == null)
				authoritys = new Vector();
			authoritys.add(role.getRolecode());
			authMap.put(portalCode, authoritys);
		}
		
		//重建用户在所有网站下的权限对象
		Iterator<String> codes = authMap.keySet().iterator();
		while(codes.hasNext()){
			String code = codes.next();
			List authoritys = authMap.get(code);
			UserAttribute userAttr = new UserAttribute();
			userAttr.setAuthoritiesAsString(authoritys);
			
			//先清除原用户缓存
			String cacheKey = getCacheKey(orgLogid,code);
			this.acegiCacheManager.removeUser(cacheKey);
			
			///建立新用户缓存
			cacheKey = getCacheKey(user.getLoginid(),code);
			
			//用户状态
			String status = user.getState().toString();
			boolean isEnabled = true;
			if(Constants.STATUS_INVALID.equals(status))
				isEnabled = false;
			
			//新的权限用户对象
			UserDetails userDetails = new User(cacheKey, user.getPassword(), isEnabled, true, true,
					true, userAttr.getAuthorities());	
			
			this.acegiCacheManager.addUser(userDetails);
		}
	}
	
	/**
	 * 刷新资源权限缓存
	 * @param resString
	 * @param resType
	 * @param roleCode
	 * @param isAuth
	 */
	@SuppressWarnings("unchecked")
	public  void renovateResource(String resString,String resType,String roleCode,boolean isAuth){
		//this.acegiCacheManager.renovateResource(orgResString, resString, resType, authorities);
		ResourceDetails resDetails = this.acegiCacheManager.getResourceFromCache(resString);
		//权限集合
		List authorities = new Vector();
		String rolekey=getRoleKey(roleCode);
		if(resDetails!=null){
			GrantedAuthority[] authoritys = resDetails.getAuthorities();
			for (GrantedAuthority auth : authoritys) {
	
				if (isAuth) {
					// 授权操作，保留原有权限
					authorities.add(auth);
				} else {
					// 撤销授权，保留不是当前角色的权限
					String authority = auth.getAuthority();
					if (authority.equals(rolekey) == false) {
						authorities.add(auth);
					}
				}
			}
		}
		
		// /授权操作，增加新权限
		if (isAuth) {
			GrantedAuthority authority = new GrantedAuthorityImpl(rolekey);
			authorities.add(authority);
		}

		//转为数组
		GrantedAuthority[] newAuthoritys = convertAuthorities(authorities);
		
		//新的权限资源对象
		ResourceDetails newResDetails = new Resource(resString,resType,newAuthoritys);
		
		this.acegiCacheManager.renovateResource(resString, newResDetails);
		
	}
	
	/**
	 * 刷新资源权限缓存
	 * @param orgResString
	 * @param resource
	 */
	public  void renovateResource(String orgResString,CoreResource resource){
		
		Set roleSet = resource.getCorePermissions();
		
		if(roleSet == null || roleSet.isEmpty()) return;

		//当前用户所有的角色按网站分类
		Map<String,List> authMap = new HashMap();

		Iterator<CorePermission> roles = roleSet.iterator();
		List authoritys = new Vector();
		while(roles.hasNext()){
			CorePermission role = roles.next();
			String roleCode = role.getComp_id().getObjectid().toString();
			GrantedAuthority authority = new GrantedAuthorityImpl(getRoleKey(roleCode));
			authoritys.add(authority);
		}
		
		//新的权限资源对象
		String resString = resource.getResourcestring();
		String resType = resource.getResourcetype();
		GrantedAuthority[] newAuthoritys = convertAuthorities(authoritys);		
		ResourceDetails newResDetails = new Resource(resString,resType,newAuthoritys);
		if(StringUtils.isNotBlank(orgResString)){
			this.acegiCacheManager.removeResource(orgResString);
		}
		this.acegiCacheManager.addResource(newResDetails);
	}
	
	/**
	 * 转换为权限数据
	 * @param authorities
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private GrantedAuthority[] convertAuthorities(List authorities){
//		如果角色权限集合为空，增加匿名用户角色
		if(authorities.isEmpty())
			authorities.add(new GrantedAuthorityImpl(AcegiConstants.ANONYMOUSUSER_CODE));
		
		//转为数组
		GrantedAuthority[] toReturn = {new GrantedAuthorityImpl("demo")};		
		GrantedAuthority[] newAuthoritys = (GrantedAuthority[]) authorities
				.toArray(toReturn);
		return newAuthoritys; 
	}
	
	//跨网站的权限ＩＤ处理
	private String getCacheKey(String logId,String portalCode){
		String cacheKey = logId;
		//cacheKey = cacheKey.concat(AcegiConstants.KEY_USERNAME_AND_SITEID_FLAG);
		//cacheKey = cacheKey.concat(portalCode);
		return cacheKey;
	}	
	/**
	 * 获得role key
	 * @param roleCode
	 * @return
	 */
	private String getRoleKey(String roleCode){
		return rolePrefix+roleCode;
	}
}