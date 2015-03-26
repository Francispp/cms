package com.cyberway.common.comtemplate.view;

import java.util.List;

import ognl.Ognl;
import ognl.OgnlException;

import com.cyberway.common.comtemplate.domain.ComTemplateGather;
import com.cyberway.common.comtemplate.service.ComTemplateGatherService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class ComTemplateGatherController extends BaseBizController<ComTemplateGather>{
	
	
	ComTemplateGatherService service = (ComTemplateGatherService) this.getServiceById("comtemplateGatherService");
	
	private Long comtemplateId;
	
	private Long temLibraryId;
	
	private Integer comtemplateType;
	
	private Long includeTemLibraryId;
	
	private String includeTemLibraryNmae;
	
	
	
	public String getIncludeTemLibraryName(){
		return includeTemLibraryNmae;
	}
	
	public void setIncludeTemLibraryName(String includeTemLibraryNmae){
		this.includeTemLibraryNmae = includeTemLibraryNmae;
	}
	
	public Long getComTemplateId() {
		return comtemplateId;
	}

	public void setComTemplateId(Long comtemplateId) {
		this.comtemplateId = comtemplateId;
	}
	
	public Long getTemLibraryId(){
		return temLibraryId;
	}
	
	public void setTemLibraryId(Long temLibraryId){
		this.temLibraryId = temLibraryId;
	}
	
	public Integer getComTemplateType() {
		return comtemplateType;
	}

	public void setComTemplateType(Integer comtemplateType) {
		this.comtemplateType = comtemplateType;
	}
	
	public Long getIncludeTemLibraryId() {
		return includeTemLibraryId;
	}

	public void setIncludeTemLibraryId(Long includeTemLibraryId) {
		this.includeTemLibraryId = includeTemLibraryId;
	}

	@Override
	protected ComTemplateGatherService getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	
	public String list() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		if(comtemplateType != null && comtemplateType != 5){
			criteria.addFilter("temLibraryId", temLibraryId);
		}
		criteria.addFilter("comtemplateId", comtemplateId);
		criteria.addFilter("comtemplateType", comtemplateType);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	
	
	/*
	 * 得到所有temLibrary,尚未完成
	 */
	public void gathertree() throws Exception{
		
	}
	
	/*
	 * 保存中间表信息
	 */
	public String save() throws Exception{
		  ComTemplateGather gather=new ComTemplateGather();
		  try {
			Ognl.setValue("comtemplateId", gather, comtemplateId);
			Ognl.setValue("temLibraryId", gather, temLibraryId);
			Ognl.setValue("comtemplateType", gather, comtemplateType);
			Ognl.setValue("includeTemLibraryId", gather, includeTemLibraryId);
			
//			TemLibrary _temLibrary=service.getTemLibrary(includeTemLibraryId);       等待TemLibrary
//			if(_temLibrary!=null){
//				Ognl.setValue("includeTemLibraryName", gather, _temLibrary.getName());
//			}
			service.save(gather);
		} catch (OgnlException e) {
			e.printStackTrace();
		}
		return list();
	  }
	
	
	/*
	 * 删除
	 */
	
	public String delete()throws Exception{
		if(!StringUtil.isEmpty(keys)){
			List<Long> list=StringUtil.splitToList(keys,",");
			service.delete(list);
		}else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return list();
	}
	
	
	
	

}
