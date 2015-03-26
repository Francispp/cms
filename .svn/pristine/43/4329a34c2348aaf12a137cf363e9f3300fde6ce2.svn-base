package com.cyberway.cms.component.wsr.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cyberway.cms.component.wsr.domain.Emailcfg;
import com.cyberway.cms.component.wsr.service.EmailcfgService;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class EmailcfgController extends BaseBizController<Emailcfg> {  

	
	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Boolean, String> trueOfFalseMap1 = null;
	
	EmailcfgService service = (EmailcfgService)this.getServiceById("EmailcfgService");
	private Map<String,String> forms= null;
	
	
	public Map getForms() {
		if(forms==null){
			forms = new TreeMap();
			CoreFormService formService=(CoreFormService)this.getServiceById("coreFormService");
			 List<CoreForm> formlist = formService.getAll(); 
			 for(CoreForm cf:formlist){
					forms.put(cf.getPojoName(), cf.getFormName());
				}
				return forms;
		}
		return forms;
	}
	
	
	@Override
	protected EntityDao<Emailcfg> getService() {
		return  service;
	} 
	
	 
	
	/**
	 * 通用保存方法
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception{
		
		if(this.domain.getId() == null){
			List<Emailcfg> list = service.findBy("siteId", this.domain.getSiteId());
			if(list.size()>0){
				getHttpServletRequest().setAttribute("msg","站点配置信息已存在！");
				return EDIT_RESULT;
			}
		}
		//添加默认值
		super.saveOrUpdate();
		
		getHttpServletRequest().setAttribute("msg","保存成功!");
		return EDIT_RESULT; 
	}
	public Map<Boolean, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Boolean, String>();
			trueOfFalseMap1.put(new Boolean(true), "立即生效");
			trueOfFalseMap1.put(new Boolean(false), "暂不使用");
			return trueOfFalseMap1;
		}
	}
}
