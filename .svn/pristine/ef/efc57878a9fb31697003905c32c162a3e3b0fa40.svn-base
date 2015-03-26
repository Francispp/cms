package com.cyberway.cms.form.service;

import java.util.Map;

import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.BeanUtils;


public class CoreFormFieldService extends HibernateEntityDao<CoreFormField> {

	/**
	 * 供页面ajax调用的保存方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public boolean saveByAjax(Map<String, Object> data){

		boolean result = true;

		try{
			
			//得到当前对象
			CoreFormField entity = buildEntity(data);			

			// 将页面提交的数据转换为实体对象中的属性值
			BeanUtils.forceSetProperty(entity, data);
			//若为新增，设置表单对象 
			if(entity.getOid()==null){
				CoreForm coreForm=new CoreForm();
				if(data.containsKey("coreForm.oid")){
					Long formid=new Long((String)data.get("coreForm.oid"));
					coreForm.setOid(formid);
					data.remove("coreForm.oid");
				}				
			 entity.setCoreForm(coreForm);
			}
			if (result && entity !=null) {
				this.saveOrUpdate(entity);
				//this.save(entity);
				
				this.flush();
				
				// 取主键值
				String pkidName = this.getIdName(this.entityClass);
				Object pkid = BeanUtils.getProperty(entity, pkidName);
				data.put(pkidName, pkid);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			result = false;		
			
		}
		
		return result;
	}	
}
