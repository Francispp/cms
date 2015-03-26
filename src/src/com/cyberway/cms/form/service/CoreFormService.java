package com.cyberway.cms.form.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.domain.CoreFormField;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class CoreFormService extends HibernateEntityDao<CoreForm> {
	public CoreForm getById (Long id)
	{
		return super.get(id);
	}
	/* (non-Javadoc)
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public CoreForm saveOrUpdate(CoreForm obj){
		boolean isAdd=obj.getOid()==null?true:false;
		super.saveOrUpdate(obj);
		if(!isAdd && obj.getIsCreateField()){//清除原有字段
			List<CoreFormField> ffs=this.find(" from CoreFormField where coreForm.oid=?", new Object[]{obj.getOid()});
			if(ffs!=null){
			 for(CoreFormField ff:ffs)	 
			 super.remove(ff);
			}
		}
		//新增时，会自动导入字段;
		//选择重新导入字段时，会自动导入字段
		if(isAdd || obj.getIsCreateField()){
			createFormFields(obj);			
		}
		
		
		return obj;
	}
	/**
	 * 根据表单生成字段属性
	 * @param obj
	 */
	private void createFormFields(CoreForm obj){
		if(!StringUtil.isEmpty(obj.getPojoName())){
			try{
			Class pojo=Class.forName(obj.getPojoName());
			Map fieldKeys=new HashMap();//已增加字段名
			List <CoreFormField> formFields=new ArrayList();
			//把公共基类(BaseDocument)字段增加进去
			Field[] fields=(BaseDocument.class).getDeclaredFields();//pojo.getDeclaredFields();						
			if(fields!=null){
				for(int i=0;i<fields.length;i++){
					if(!isBaseType(fields[i].getType().getName()))
					  continue;	
					if(fieldKeys.containsKey(fields[i].getName()))//检测是否有重复名称
					 continue;	
					fieldKeys.put(fields[i].getName(), fields[i].getName());					
					CoreFormField formField=getCoreFormField(fields[i]);
					formField.setCoreForm(obj);
					formFields.add(formField);
				} 
			 }
			 fields=pojo.getDeclaredFields();
			 //增加设置的对象			 
			if(fields!=null){
				
				for(int i=0;i<fields.length;i++){
					if(!isBaseType(fields[i].getType().getName()))
					  continue;	
					if(fieldKeys.containsKey(fields[i].getName()))//检测是否有重复名称
					 continue;	
					fieldKeys.put(fields[i].getName(), fields[i].getName());					
					CoreFormField formField=getCoreFormField(fields[i]);
					formField.setCoreForm(obj);
					formFields.add(formField);
				}				
			}
			obj.setFormFields(formFields);
			this.saveOrUpdateAll(formFields);
			}catch(Exception e){
				e.printStackTrace();
			}
			}
	}
    /**
     * 检测是否为基本类型
     * @param typeName
     * @return
     */
    private boolean isBaseType(String typeName){
    	boolean isBase=false;
    	if(typeName.equals("int")||typeName.equals("java.lang.Integer")
    		 ||typeName.equals("long")||typeName.equals("java.lang.Long")
    		 ||typeName.equals("double")||typeName.equals("java.lang.Double")
    		 ||typeName.equals("float")||typeName.equals("java.lang.Float")
    		 ||typeName.equals("char")||typeName.equals("java.lang.String")
    		 ||typeName.equals("date")||typeName.equals("java.util.Date"))
    		isBase=true;
    	return isBase;
    }
	/**
	 * 
	 * @param field
	 * @return
	 */
	private CoreFormField getCoreFormField(Field field){
		CoreFormField cff=new CoreFormField();
		cff.setFieldCode(field.getName());
		cff.setFieldName(field.getName());
		cff.setFieldType(field.getType().getName());
		return cff;
	}
	/**
	 * 获得当前系统下，所有pojo
	 * @return
	 */
	public List getAllClassMetadata(){
		List allClass=new ArrayList();
		//默认加载动态表单
		allClass.add("com.cyberway.cms.domain.Document");
		
		Map alls=this.getSessionFactory().getAllClassMetadata();
		Iterator iterator = alls.keySet().iterator();
		while(iterator.hasNext()){
			String clazz=(String)iterator.next();
			if(clazz.startsWith("com.cyberway.cms.form") && !clazz.startsWith("com.cyberway.cms.form.CoreForm"))//只取com.cyberway.cms.form下的
				allClass.add(clazz);
		}
		return allClass;
	}
	/**
	 * 获得所有form的id和名字
	 * @return
	 */
	public Map<Long,String> getAllForm(){
		Map<Long,String> forms=new TreeMap();
		List<CoreForm> allf=getAll();
		for(CoreForm cf:allf){
			forms.put(cf.getOid(), cf.getFormName());
		}
		return forms;
	}
	/**
	 * 获得所有form
	 * @param where
	 * @return
	 */
	public Map<Long,String> getAllForm(String where){
		return	getAllForm();
	}	
	/**
	 * 获得指定表单的字段
	 * @param formid
	 * @return
	 */
	public Map<String,String> getFieldsByForm(Long formid){
		Map<String,String> fields=new TreeMap();
		CoreForm cf=this.get(formid);
		if(cf.getFormType()==1)//若为动态表单，增加一行空的
			fields.put("", "");
		List<CoreFormField> allff=cf.getFormFields();
		//this.logger.info("fields length:"+allff.size());
		if(allff!=null&&!allff.isEmpty()){
			for(CoreFormField cff:allff){
				fields.put(cff.getFieldCode(), cff.getFieldName());
			}
		}
		return fields;
	}
	public Map<String,String> getFieldsAndTypeByForm(Long formid){
		Map<String,String> fields=new TreeMap();
		CoreForm cf=this.get(formid);
		if(cf.getFormType()==1)//若为动态表单，增加一行空的
			fields.put("", "");
		List<CoreFormField> allff=cf.getFormFields();
		//this.logger.info("fields length:"+allff.size());
		if(allff!=null&&!allff.isEmpty()){
			for(CoreFormField cff:allff){
				fields.put(cff.getFieldCode(), cff.getFieldType());
			}
		}
		return fields;
	}
	/**
	 * 获得指定表单的字段
	 * @param formid
	 * @return
	 */
	public Map<String,String> getFieldsByForm(String formid){
		if(StringUtil.isNumber(formid))
			return getFieldsByForm(new Long(formid));
		else
			return new HashMap();
	}
  /**
   * 根据指定表单模板名称，获得对应表单的字段
 * @param tempName
 * @return
 */
public List<CoreFormField> getFieldsByTemplateName(String tempName){
	CoreForm cf=getCoreFormByTemplateName(tempName);
	
	if(cf!=null)
		return cf.getFormFields();
	else
		return null;
  }
/**
 * 根据指定频道的ID，获得默认表单模板的字段
 * @param channelid
 * @return
 */
public List<CoreFormField> getFieldsByChannelId(Long channelid){
	ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean("channelManagerService");
	Channel channel = channelManagerService.getChannelFromCache(channelid);
	Template template;
	if(channel != null)
	{
	//是否引用公共模板
	template = channel.getPublicchannelid() != null && channel.getPublicchannelid() >0 && channel.getIscopy() == 2 ? channelManagerService.getChannelFromCache(channel.getPublicchannelid()).getFormTemplate() : channel.getFormTemplate();
	CoreForm cf;
	if(template != null)
	{
		if(template.getName() == null)
		{
			TemplateManagerService templateService = (TemplateManagerService)ServiceLocator.getBean("templateManagerService");
			template = templateService.get(template.getId());
		}
	cf=getCoreFormByTemplateName(template.getName(),channel.getSite().getOid());
	if(cf!=null)
		return cf.getFormFields();
	}
	}
		return new ArrayList();
  }
/**
 * 根据指定表单模板名称，获得对应表单
* @param tempName
* @return
*/
public CoreForm getCoreFormByTemplateName(String tempName){ 
	  Template temp =  findUniqueBy(Template.class, "name", tempName); 
	  if(temp!=null && temp.getForm()!=null){
		  Long formId=temp.getForm().getOid();
		  //temp=(Template)this.get(Template.class, temp.getId());
		  return get(formId);
	  }
	  return null;
  }

/**
 * 根据指定表单模板名称，获得对应表单
* @param tempName
* @param siteId
* @return
*/
public CoreForm getCoreFormByTemplateName(String tempName, long siteId){
	  List<Template> temps = findBy(Template.class,"name", tempName);
	  Template temp = null;
	  for (Template template : temps) {
		if(template.getSite_id() == siteId)
			temp = template;
	  }
	  
	  if(temp!=null && temp.getForm()!=null){
		  Long formId=temp.getForm().getOid();
		  //temp=(Template)this.get(Template.class, temp.getId());
		  return get(formId);
	  }
	  return null;
}
	/**
	 * 获得所有表单模板
	 * @return
	 */
	public Map<String,String> getFormTemplates(){
		Map<String,String> temps=new TreeMap();
		List <Template> list=this.find("from Template where type=?", new Object[]{Template.TYPE_FORM}); 
		for(Template tp:list)
			temps.put(tp.getName(), tp.getName());
		return temps;
	}
}
