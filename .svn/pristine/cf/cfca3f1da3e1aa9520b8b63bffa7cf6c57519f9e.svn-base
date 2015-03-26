package com.cyberway.cms.component.docShare.view;

import java.util.HashMap;
import java.util.Map;

import com.cyberway.cms.component.docShare.domain.DocShareRelation;
import com.cyberway.cms.component.docShare.service.DocShareRelationService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.web.BaseBizController;

/**
 * @author caiw
 * 文档共享设置控制器
 *
 */
public class DocShareRelationController extends BaseBizController<DocShareRelation> {

	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer, String> trueOfFalseMap1 = null;
	
	private DocShareRelationService docShareRelationService = (DocShareRelationService) this.getServiceById("docShareRelationService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return docShareRelationService;
	}

	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseBizController#saveOrUpdate()
	 */
	public String saveOrUpdate() throws Exception{
		//设置当前操作人
		if(domain!=null && domain.getOid()==null){
			Loginer loginer=getLoginer();
			domain.setUserId(loginer.getUserid());
			domain.setUserName(loginer.getUsername());
		}
		//增加更新时间
		domain.setUpdateTime(new java.util.Date());
		//保存前检测源频道与目标频道表单是否一致
		boolean isFormConsistent=docShareRelationService.checkdocShareRelationService(domain);
		if(!isFormConsistent){
			this.addActionError("设置的源频道与目标频道表单不一致，无法实现信息共享，保存失败！");
			throw new Exception("设置的源频道与目标频道表单不一致，无法实现信息共享，保存失败！");
			//logger.info("设置的源频道与目标频道表单不一致，无法实现信息共享，保存失败！");
			//return EDIT_RESULT;
		}
		domain=docShareRelationService.saveOrUpdate(domain);
		return EDIT_RESULT;
	}
	
	public Map<Integer, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "是");
			trueOfFalseMap1.put(new Integer(0), "否");
			return trueOfFalseMap1;
		}
    }
}
