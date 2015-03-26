package com.cyberway.cms.permission.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ecside.util.ServletUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.CmsBaseDocument;
import com.cyberway.cms.permission.domain.CmsPermission;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.permission.service.CmsResourceService;
import com.cyberway.cms.webservice.service.CacheSynchroismService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * cms权限管理控制器
 * @author caiw
 *
 */
public class CmsPermissionController extends BaseBizController<CmsPermission> {
	CmsPermissionService service=(CmsPermissionService)this.getServiceById("cmsPermissionService");
	CmsResourceService resService=(CmsResourceService)this.getServiceById("cmsResourceService");
	DocumentCommonService documentCommonService = (DocumentCommonService)getServiceById("documentCommonService");
	private int type;//当前是在什么位置上设置（比如：在站点上，可设置站点、频道、文档类型的）
	private Long objectId;
	private int objectType;//指当前对站点、频道、文档
	private String objectTypeName;
	private String pageUrl;
	private int isInheritPerm=1;//是否继承父系权限
	private List roleTypePerms;//角色默认的权限
	private CmsBaseDocument document;
	public Boolean getIsSearchUsers() {
		return Constants.IS_SEARCH_USERS;
	}
	@Override
	protected EntityDao getService() {
		return service;
	}
	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		
		if(type<3){//对站点、频道权限检测
			CmsPermissionService permService=(CmsPermissionService)this.getServiceById("cmsPermissionService");
			//检测是否有权限
			if(!permService.haveThePermission(getLoginer(), "CMS_DOCUMENT_PERMISSION_MANAGER",type, objectId)
					&&!permService.haveThePermission(getLoginer(), "CMS_SITE_DOCUMENT_PERMISSION_MANAGER",type, objectId)) {
				//throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));
				this.addActionMessage(this.getText("RESOURCE.HINTINFO.NOPERMISSION"));
				pageUrl=resService.getResourcePageUrlByType(objectType);
				return this.LIST_RESULT;
			} 
		}

		CriteriaSetup criteria = new CriteriaSetup();
		if(objectId!=null)//若objectId不为空，
			criteria.addFilter("objectId",objectId);
		if(objectType!=0)//若objectType不为空，
			criteria.addFilter("objectType",objectType);
		if(type!=0)//若objectType不为空，
			criteria.addFilter("setType",type);	
		//criteria.addFilter("permType", 0);//只显示用户增加的权限
		doListEntity(criteria, getTableId(), 10000);
		
		pageUrl=resService.getResourcePageUrlByType(objectType);
		//若对象类型与类型相同，则可设置是否继承父系权限（除站点之外,如：频道--频道权限;文档--文档权限）
		if(type>1 && (type==objectType|| (type==Constants.CHANNEL_TYPE && objectType == Constants.DOCUMENT_TYPE))){
			isInheritPerm=service.getIsInheritPerm(objectType,type,objectId);
		}
		//amway 增加角色默认权限
		this.roleTypePerms= getRolePerms(objectType);
		
		return this.LIST_RESULT;
	}
	/**
	 * 获得角色默认具有权限的字符串
	 * @param _type
	 * @return
	 */
	private List getRolePerms(int _type){
		List rps=new ArrayList();
		String configStr=null;
		if(_type==1)
			configStr="cms.permission.roleType.site";
		else  if(_type==2)
			configStr="cms.permission.roleType.channel";
		else  if(_type==3)
			configStr="cms.permission.roleType.document";
		if(configStr!=null){
			String cfstr=Constants.getProperty(configStr, "");
			if(!StringUtil.isEmpty(cfstr)){
				String[] roles=StringUtil.split(cfstr, ";");
				for(int i=0;i<roles.length;i++){
					if(roles!=null){
					 String[] temps=StringUtil.split(roles[i], ":");
					 if(temps!=null && temps.length>=2)
						 rps.add(temps[1]);
					 else 
						 rps.add("");
					}
				}
			}
		}
		return rps;
	}
	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseBizController#saveAjax()
	 */
	public String saveAjax(){
		HttpServletRequest request = this.getHttpServletRequest();
		Map map = ServletUtils.getParameterMap(request);
		boolean result = false;
		try{
			result = getService().saveByAjax(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.ajaxSaveInfo(result);
		if(result){
			String oid=(String)map.get("recordKey");
			/*同步缓存*/
			if(!StringUtil.isEmpty(oid)){
				CacheSynchroismService cacheSynchroismService = (CacheSynchroismService)ServiceLocator.getBean("cacheSynchroismService");
				try{
					cacheSynchroismService.updateCmsPermission(oid, "");	
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return AJAX;
	}
    /* (non-Javadoc)
     * @see com.cyberway.core.web.BaseBizController#edit()
     */
    public String edit()throws Exception{
    	return super.edit();
    }
    /* (non-Javadoc)
     * @see com.cyberway.core.web.BaseBizController#saveOrUpdate()
     */
    public String saveOrUpdate()throws Exception{
    	
    	return super.saveOrUpdate();
    }
    /**
     * 进入赋权界面
     * @return
     */
    public String perm(){
    	
    	return "permission";
    }
	/**
	 * 返回tabxml地址
	 * @return
	 * @throws Exception
	 */
	public String tabxml()throws Exception
	{
		return "xml";
	}
	
	/**
	 * 文档权限树
	 * @author Dicky
	 * @time 2012-9-12 18:08:34
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String docPerTree() throws Exception{
		this.document = documentCommonService.getCmsBaseDocument(objectId);
		if(this.document==null){
			this.document= new CmsBaseDocument();
			this.document.setId(objectId);
		}
		return "docPerTree";
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	/**
	 * 取得当前设置对象类型
	 * @return
	 */
	public String getObjectTypeName() {
		if(StringUtil.isEmpty(objectTypeName))
			objectTypeName=com.cyberway.cms.permission.service.CmsResourceService.ObjectResourceTypes[objectType];
		return objectTypeName;
	}
	public void setObjectTypeName(String typeName) {
		this.objectTypeName = typeName;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public int getIsInheritPerm() {
		return isInheritPerm;
	}
	public void setIsInheritPerm(int isInheritPerm) {
		this.isInheritPerm = isInheritPerm;
	}
	public List getRoleTypePerms() {
		return roleTypePerms;
	}
	public void setRoleTypePerms(List roleTypePerms) {
		this.roleTypePerms = roleTypePerms;
	}
	public CmsBaseDocument getDocument() {
		return document;
	}

	public void setDocument(CmsBaseDocument document) {
		this.document = document;
	}
}
