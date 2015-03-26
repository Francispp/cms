package com.cyberway.cms.exposed;

import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.core.objects.Loginer;

/**
 * @author caiw
 * ＣＭＳ权限对外接口
 *
 */
public class PermissionServiceJsUtil {
	
	private CmsPermissionService cmsPermissionService;

	public CmsPermissionService getCmsPermissionService() {
		return cmsPermissionService;
	}

	public void setCmsPermissionService(CmsPermissionService cmsPermissionService) {
		this.cmsPermissionService = cmsPermissionService;
	}
	/**
	 * 检测当前登录者，是否拥有权限
	 * @param loginer
	 * @param resCode 访问资源编码
	 * @param objectid 访问对象id
	 * @return
	 */
	public boolean haveThePermission(Loginer loginer,String resCode,Long objectid){
		return cmsPermissionService.haveThePermission(loginer, resCode, objectid);
	}
	/**
	 * 检测当前登录者，是否拥有权限
	 * @param loginer
	 * @param resCode 访问资源编码
	 * @param objectType 对象类型(站点、频道、文档)
	 * @param objectid 访问对象id
	 * @return
	 */
	public synchronized boolean haveThePermission(Loginer loginer,String resCode,int objectType,Long objectid) {
		return cmsPermissionService.haveThePermission(loginer, resCode, objectType, objectid);
	}
}
