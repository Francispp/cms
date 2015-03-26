package com.cyberway.cms.labels.service;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.labels.domain.Label;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;

public class LabelService extends HibernateEntityDao<Label> {
	

	/**
	 * dwr 验证 : 
	 * 判断新建标签时，标签的标题是否重复
	 * 根据类型(labelId)，获得标签列表
	 * @remark add by xiacaho 2015-01-07
	 * @param labelId
	 * @param title
	 * @return String
	 */
	public String dwrLabelType(String labelId, String title){
		String flag = "0";
		if (title != null && title.length() > 0) {
			String hql = " from Label where title='"+title+"' ";
			if(labelId!=null && labelId.length()>0){
				hql += " and id<>"+labelId+" ";
			}  
			List<Label> list =this.find( hql );
			if(list==null || list.size()<1){
				flag = "1";
			}
		} 
		return flag;
	}
	
	/**
	 * 根据类型(labelId)，获得标签列表
	 * @param labelId
	 * @return List<CoreCommonInfo>
	 */
	public List<Label> getLabelsBylabelId(Long labelId){
		List<Label> cci=this.find(" from Label where label.id=?  order by sortOrder asc", new Object[]{labelId});
		return cci;
	}
	
}
