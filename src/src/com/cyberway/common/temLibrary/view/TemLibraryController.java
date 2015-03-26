package com.cyberway.common.temLibrary.view;


import java.util.List;

import com.cyberway.common.temLibrary.domain.TemLibrary;
import com.cyberway.common.temLibrary.service.TemLibraryService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * 模版库管理控制器
 * 
 * 
 */

public class TemLibraryController extends BaseBizController<TemLibrary> {

	TemLibraryService service = (TemLibraryService) this.getServiceById("temLibraryService");
	
	private String treeXml;

	public String getTreeXml() {
		return treeXml;
	}

	public void setTreeXml(String treeXml) {
		this.treeXml = treeXml;
	}


	private Long parentid; // 父节点 ：当前暂未应用

	public String tree() {
		return TREE_RESULT;
	}

	public String xml() throws Exception {
		
		setTreeXml(service.getCommonTypeXmls());
		
		
		return HTMLXTREE_RESULT;
	}

	public String listBySite() throws Exception {
			CriteriaSetup criteria = new CriteriaSetup();
			if (parentid != null)
				criteria.addFilter("parentid", parentid);
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}

	public String execute() throws Exception {
		return list();
	}
 
	public String listCommonType() throws Exception {
			CriteriaSetup criteria = new CriteriaSetup();
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}

	public String editCommonType() throws Exception {
		super.edit();
		return INPUT;
	}

	public String saveOrUpdateCommonType() throws Exception {
		if (parentid != null){
			domain.setParentid(parentid);
		}
		service.saveOrUpdate(domain);
		addActionMessage("保存成功!");
		return EDIT_RESULT;
	}

	/**
	 * 第一步：删除关联数据CoreCommonInfo 第二步：执行删除操作
	 */
	public String deleteCommonType() throws Exception {
//		if (!StringUtil.isEmpty(keys)) {
//			String[] tempArray = keys.split(",");
//			StringBuffer strCoreCommonInfoId = new StringBuffer();
//			for (int i = 0; i < tempArray.length; i++) {
//				Long tempIdLong = Long.valueOf(tempArray[i]);
//				List<CoreCommonInfo> tempList = commonInfoService.getCoreCommonInfosByCommonTypeId(tempIdLong);// service.findBy("parentid", tempArray[i]);
//				if (tempList != null && tempList.size() > 0) {
//					for (CoreCommonInfo coreCommonInfo : tempList) {
//						strCoreCommonInfoId.append(coreCommonInfo.getId()).append(",");
//						//commonInfoService.remove(coreCommonInfo);
//					} 
//				}
//			}
//			if (strCoreCommonInfoId.length()>0) {
//				strCoreCommonInfoId.deleteCharAt(strCoreCommonInfoId.length()-1);
//				List<Long> list = StringUtil.splitToList(strCoreCommonInfoId.toString(), ",");
//				commonInfoService.removeByIds(list);
//				
//				for (Long long1 : list) {
//					try {
//						cacheSynchroismService.updateCommonType(long1, "del");
//					} catch (Exception ex) {
//						logger.error("", ex);
//						this.addActionMessage("删除失败!");
//					}
//				}
//				
//			}
//			List<Long> list  = StringUtil.splitToList(keys, ",");
//			getService().removeByIds(list);
//			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));
//		} else
//			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
//		return listCommonType();
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list  = StringUtil.splitToList(keys, ",");
			getService().removeByIds(list);
			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));
		} else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return list();
	}


	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Long getSiteid() {
		// loginer.setSiteId(cmsSite.getOid());
		return super.getLoginerSiteId();
	}
	public String publicItree() throws Exception {
			return "public";
	}
	
	public String publicItree2() throws Exception {
		return "public2";
	}
	

	@Override
	protected EntityDao getService() {
		return service;
	}
}