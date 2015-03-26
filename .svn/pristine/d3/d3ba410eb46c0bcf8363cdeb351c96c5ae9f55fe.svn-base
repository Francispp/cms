package com.cyberway.common.permission.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.common.domain.VcorePermission;
import com.cyberway.common.permission.service.PermissionService;
import com.cyberway.common.resource.service.ResourceManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.ectable.ExtremeTablePage;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class PermissionController extends BaseBizController<VcorePermission> {
	PermissionService service=(PermissionService)this.getServiceById("permissionService");
	private Long objectid;
	private Long type;
	private String typeName;
	private Long resourceid;
	
	@Override
	protected EntityDao getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		//显示树型结构
/*		if(pageStyle==0){
			return "frame";
		}*/
		if(objectid!=null&&type!=null){
/*		 list=service.find("from VcorePermission where objectid=? and targettype=? order by resourceid", new Object[]{objectid,type});
		 logger.info("list size:"+list.size());*/
			Map filterMap=new HashMap();	
			filterMap.put("objectid", objectid);
			filterMap.put("targettype", type);
			Page page=service.findECPage(ExtremeTablePage.getLimit(getHttpServletRequest()),null);
			getHttpServletRequest().setAttribute("items", page.getResult());
			setTotalRows(page.getTotalCount());//设置数据条数			
		 //getHttpServletRequest().setAttribute("items", list);
		}else
			new Exception("对象ID或类型不存在！");
		return LIST_RESULT;
	}
	
	/**
	 * 显示权限信息
	 * @return
	 */
	public String listPerm()throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(resourceid!=null){
			criteria.addFilter("resourceid", resourceid);	
		}	
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		
		return LIST_RESULT;
	}
	/**
	 * 树型结构
	 * 
	 * @return
	 */
	/*	public String tree() throws Exception{
		
/*		if(objectid!=null&&type!=null){
		items =service.getPermissionResourcesTree(objectid,type);
		List vps=service.getAllResourcesAccredited(objectid,type);
		if(vps!=null&&vps.size()>0){
			domain=(VcorePermission)vps.get(0);
		}
		getHttpServletRequest().setAttribute("items", items);
		}else
			new Exception("对象ID或类型不存在！");
		return TREE_RESULT;
	}  */
	/**
	 * 树型结构
	 * 
	 * @return
	 */
	public String tree() throws Exception{
	
		List vps=service.getAllResourcesAccredited(objectid,type);
		getHttpServletRequest().setAttribute("AllResourcesAccredited", vps);		
		ResourceManagerService resourceService =(ResourceManagerService)this.getServiceById("resourceManagerService");
		getHttpServletRequest().setAttribute("resourse", resourceService.getAll());	
		return "tree";
	}
	
	/*
	 * 保存权限
	 * @see com.cyberway.core.web.BaseBizController#saveOrUpdate()
	 */
	public String saveOrUpdate() throws Exception{
		List<Long> resourceids=StringUtil.splitToList(keys, ",");
		boolean succ=service.savePermissions(objectid, type, resourceids);
		if(succ){
			this.addActionMessage("保存成功！");
		}else{
			this.addActionMessage("保存失败！");
		}
		//返回树型中
		return tree();
	}
	
	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		//若类型名称设置为空，则取指定的类型名称
		if(typeName==null&&type!=null)
			return service.getTypeName(type);
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getResourceid() {
		return resourceid;
	}

	public void setResourceid(Long resourceid) {
		this.resourceid = resourceid;
	}

}
