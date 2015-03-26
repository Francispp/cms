package com.cyberway.common.comtemplate.service;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.common.comtemplate.domain.ComTemplate;
import com.cyberway.core.dao.HibernateEntityDao;

public class ComTemplateManagerService extends HibernateEntityDao<ComTemplate>{
	
//	private ServletContext  	_servletContext;
//	private CoreFormService	   coreFormservice;
//	
//	
//	
//	public CoreFormService getCoreFormservice() {
//		return coreFormservice;
//	}
//	
//	public void setCoreFormservice(CoreFormService coreFormservice) {
//		this.coreFormservice = coreFormservice;
//	}
//	
//	
//	@Override
//	public void setServletContext(ServletContext servletContext) {
//		// TODO Auto-generated method stub
//		_servletContext = servletContext;
//	}
	/**
	 * 根据模板名称，获得指定模板
	 * 
	 * @param site
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ComTemplate getComTemplateByName(String comtemplateName){
		
		Validate.notNull(comtemplateName);
		DetachedCriteria criteria = DetachedCriteria.forClass(ComTemplate.class);
		criteria.add(Restrictions.eq("name",comtemplateName));
		List temps = (List<ComTemplate>) getHibernateTemplate().findByCriteria(criteria);
		if(temps != null&& temps.size() > 0)
			return (ComTemplate) temps.get(0);
		else
			return null;
	}
	
	
	/*
	 * 更新模板
	 */
	
	public ComTemplate updateComTemplateForm(ComTemplate comtemplate,Long temLibraryId){
		
		if(comtemplate.getParent().getId()== null)
			comtemplate.setParent(null);
			comtemplate.setAdminSummaryComTemplate(null);
			comtemplate.setDetailsComTemplate(null);
			comtemplate.setFormComTemplate(null);
			comtemplate.setSummaryComTemplate(null);
			comtemplate = saveOrUpdate(comtemplate);
			if(comtemplate != null){
				
				comtemplate = saveOrUpdate(comtemplate);
			}
		return comtemplate;
	}
	
	public ComTemplate saveOrUpdate(ComTemplate comtemplate){
		comtemplate = super.saveOrUpdate(comtemplate);
		return comtemplate;
	}
	

	
	/*
	 * 判断模板名称是否重复
	 */
	public String dwrComTemplateType(String comtemplateId,String name){
		String flag = "0";
		if(name != null && name.length() > 0){
			String hql = "from ComTemplate where name='"+name+"' ";
			if(comtemplateId != null && comtemplateId.length() > 0){
				hql += "and id <> " + comtemplateId+" ";
			}
			System.out.println("hql="+hql);
			@SuppressWarnings("unchecked")
			List<ComTemplate> list = this.find(hql);
			if(list == null || list.size()<1 ){
				flag = "1";
			}
		}
		return flag;
	}
	
	
	

	
	
	
	
	
	
	

}
