package com.cyberway.common.comtemplate.service;

import java.util.List;

import com.cyberway.common.comtemplate.domain.ComTemplateGather;
import com.cyberway.core.dao.HibernateEntityDao;

public class ComTemplateGatherService extends HibernateEntityDao<ComTemplateGather>{

	
	
	
	
	
	
	
	/*
	 * 删除方法
	 */
	public void delete(List<Long> ids)throws Exception {
		// TODO Auto-generated method stub
		if(ids != null){
			ComTemplateGather comtemplateGather;
			for(Long id:ids){
				comtemplateGather = get(id);
				if(comtemplateGather != null){
					remove(comtemplateGather);
				}
			}
		}
	}
	

}
