package com.cyberway.common.email.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.common.email.domain.CoreEmail;
import com.cyberway.common.email.service.EmailManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

public class EmailController extends BaseBizController<CoreEmail>{
	EmailManagerService service = (EmailManagerService)this.getServiceById("emailManagerService");
	
	private static List<CoreEmail> templateList;
	private String toEmail;
	private String ccEmail;
	
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	public String saveOrUpdate() throws Exception{
		if(domain.getCreatetime() == null)
		domain.setCreatetime(new Date());
		domain=service.saveOrUpdate(domain);
		addActionMessage("保存完成");
		return EDIT_RESULT;
	}
	
	@Override
	public String execute() throws Exception {
		return list();
	}
	
	public String edit()throws Exception{
		if(id!=null){
			get();
		}else{
			domain=getDomainClass().newInstance();
		}			
		return EDIT_RESULT;
	}
	
	public void sentEmail()throws Exception{
		HashMap To = new HashMap();
		To.put("To", "lanter@foxmail.com");
		To.put("T1", "lan@cyberway.net.cn");
		HashMap Cc = new HashMap();
		Cc.put("Cc", "lan@cyberway.net.cn");
		HashMap setFrom = new HashMap();
		setFrom.put("setFrom", "lanter@foxmail.com");
		try{
			service.sentEmail(To, Cc, setFrom, new HashMap(),1l,new Object[]{},service.get(1l));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String selectTemplateHr() throws Exception {
		return "selectHr";
	}
	
	public String writeHr() throws Exception {
		if(id != null){
			get();
			return "writeHr";
		} else {
			return "selectHr";
		}
	}

	public String sentHr() throws Exception {
		HashMap<String, String> to = new HashMap<String, String>();
		for(String toEmailStr:toEmail.split(";")){
			if(StringUtils.isNotBlank(toEmailStr)){
				toEmailStr = toEmailStr.trim();
				to.put(toEmailStr, toEmailStr);
			}
		}
		HashMap<String, String> cc = new HashMap<String, String>();
		if(StringUtils.isNotBlank(ccEmail)){
			for(String ccEmailStr:ccEmail.split(";")){
				if(StringUtils.isNotBlank(ccEmailStr)){
					ccEmailStr = ccEmailStr.trim();
					cc.put(ccEmailStr, ccEmailStr);
				}
			}
		}
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("<html><head><meta http-equiv=Content-Type content=text/html; charset=utf-8><title>");
		contentBuilder.append(domain.getTemplatename());
		contentBuilder.append("</title></head><body>");
		contentBuilder.append(domain.getTemplatecontent());
		contentBuilder.append("</body></html>");
		boolean result = service.sentHrEmail(to, cc, new HashMap<String, String>(), domain.getTemplatename(), contentBuilder.toString());
		if(!result){
			addActionError("发送失败");
		}else{
			addActionMessage("发送成功");
		}
		return "writeHr";
	}
	
	@SuppressWarnings("unchecked")
	public List<CoreEmail> getTemplateList(){
		if(templateList == null){
			templateList = new ArrayList<CoreEmail>();
			List<Object[]> list = service.find("select oid,templatename from CoreEmail");
			for(Object[] objs:list){
				CoreEmail coreEmail = new CoreEmail();
				coreEmail.setOid((Long)objs[0]);
				coreEmail.setTemplatename(objs[1].toString());
				templateList.add(coreEmail);
			}
		}
		return templateList;
	}
	
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getCcEmail() {
		return ccEmail;
	}
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}
	
}
