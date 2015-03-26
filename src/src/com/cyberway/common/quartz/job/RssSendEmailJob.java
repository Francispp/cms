package com.cyberway.common.quartz.job;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.web.util.HtmlUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.wsr.domain.Emailcfg;
import com.cyberway.cms.component.wsr.domain.Subcreibe;
import com.cyberway.cms.component.wsr.service.EmailcfgService;
import com.cyberway.cms.component.wsr.service.SubcreibeService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.common.email.service.EmailLogManagerService;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class RssSendEmailJob implements StatefulJob{
	 public RssSendEmailJob() { }
	 public void execute(JobExecutionContext context) throws JobExecutionException {
		
		 
		 //声明服务器对象
	 EmailLogManagerService emailLogManagerService = (EmailLogManagerService)ServiceLocator.getBean("EmailLogManagerService");	 
	 DocumentCommonService documentCommonService = (DocumentCommonService)ServiceLocator.getBean("documentCommonService");	 
	 SubcreibeService subcreibeService = (SubcreibeService)ServiceLocator.getBean("SubcreibeService"); 
	 EmailcfgService emailcfgService = (EmailcfgService)ServiceLocator.getBean("EmailcfgService"); 
	 
	 //查找所有订阅用户信息
	 List<Subcreibe> subcreibes = subcreibeService.find("FROM Subcreibe WHERE locked = 1 AND approved = 1",new Object []{});  
	
	 List<BaseDocument> docs = null;
	 
	 //保存 Email 对象
	 Map<String, Subcreibe> subcmap = new HashMap<String,Subcreibe>();
	 //保存 每个Ｅmail 订阅的所有频道集合
	 Map<String,List<Long>> channelmap = new HashMap<String,List<Long>>();
	 
	 //获取出每个Ｅmail 订阅的所有频道
	 List<Long> list = null;
	 for(Subcreibe subcreibe : subcreibes)
	 {
		 //判断 Email是否已经在 submap中存在
		 if(!subcmap.containsKey(subcreibe.getUserEmail())){
			 //不存在 则将Email添加到 submap中  并新建一个空频道集合 
			 list  =  new ArrayList<Long>(); 
			 subcmap.put(subcreibe.getUserEmail(),subcreibe);
		 }else{
			 list = channelmap.get(subcreibe.getUserEmail()); 
		 }
		 //将频道信息保存到 当前Email 所订阅的 频道集合中
		 list.add(subcreibe.getChannelId());
		 //更新Map中频道信息
		 channelmap.put(subcreibe.getUserEmail(),list);
	 }
	  
	  //获取当前时间 和 昨天此时间
	 Calendar c = Calendar.getInstance();
	 c.setTime(new Date());
	 c.add(Calendar.DATE, -1); // 目前时间减一天
	 DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 Date yesterday = null;
	 Date today = null;
	 try {
		yesterday = df.parse(df.format(c.getTime()));
		 today = df.parse(df.format(new Date()));
	 } catch (ParseException e1) {
		e1.printStackTrace();
	 } 
	 
	 for(String emailkey : subcmap.keySet())
	 {
		 try{
			 	Subcreibe subcreibe = subcmap.get(emailkey); 
			 	
			 	//获取邮件服务器信息
			 	 Emailcfg  emailcfg = emailcfgService.findUniqueBy(Emailcfg.class, "siteId", subcreibe.getSiteId()); 
			 	 String formName = "";
			 	 
			 	 //获取站点的主要表单模板对象
			 	 if(emailcfg == null ||(emailcfg != null && StringUtil.isEmpty(emailcfg.getFormName()))){
			 		formName = "BasicDocument";
			 	 }else{
			 		formName = emailcfg.getFormName().substring(emailcfg.getFormName().lastIndexOf(".")+1,emailcfg.getFormName().length());
			 	 }
			 	 
			 	 //查询出 获取当前时间 和 昨天此时间所有发布信息
				 docs=  documentCommonService.find("FROM "+formName+" WHERE issued = 5 AND timeIssued between ? and ?",new Object []{yesterday,today});
				 String content = makeupContent(emailkey,channelmap,docs);
				 	
				 	if(StringUtil.isEmpty(content))
				 		continue;
				 
				 //创建发送日志 并添加属性值
				CoreEmailLog emailLog = new CoreEmailLog();
				emailLog.setCc(null);
				emailLog.setTo(subcreibe.getUserEmail());
				emailLog.setSetFrom(emailcfg.getFromMail());
				emailLog.setAttach(null);
				emailLog.setEmailSubject(emailcfg.getSubject());
				emailLog.setEmailContent(HtmlUtils.htmlEscape(content));
				emailLog.setCoreEmail(null);
				emailLog.setCreatetime(new java.util.Date(System.currentTimeMillis()));
				emailLog.setStatus(1); 
				
				
				
				try {
					//发送邮件
					HtmlEmail email = new HtmlEmail ();
					email.setHostName(emailcfg.getMailServer());
					email.setCharset("gb2312"); 
					email.setSubject(emailcfg.getSubject());
					email.setHtmlMsg(content);
					email.addTo(subcreibe.getUserEmail());
					email.setFrom(emailcfg.getFromMail());
					//判断是否需要发送邮件者的用户密码。不同的MailHost对发送者否需要验证也是不同有的需要有的不需要
					if(emailcfg.isNeedvalidate()){ 
						email.setAuthentication(emailcfg.getFromMail(),emailcfg.getFrompwd());
					}
					email.send();
					//如果成功 保存发送日志
					emailLogManagerService.save(emailLog);
					
				} catch (RuntimeException e) {
					//如果失败。 将状态设置为 失败并保存
					emailLog.setStatus(0);
					emailLogManagerService.save(emailLog);
				}
		 	}catch(Exception e){
				 e.printStackTrace();
			}
		 } 
	 }
	 
	/**
	 * 获取频道连接
	 * @param mapkey
	 * @param channelmap
	 * @param docs
	 * @return
	 */
	public String makeupContent(String mapkey,Map<String,List<Long>> channelmap,List<BaseDocument> docs){
		StringBuffer sb = new StringBuffer();
		List<Long> list = channelmap.get(mapkey);
		
		for (Long chnId : list) {
			for (BaseDocument doc : docs) {
				if(doc.getChannel().getId().longValue() == chnId.longValue()){
					//拼凑 连接信息
					String href="/cms/docInfo!view.action?templateName=childDeital2&id="+doc.getId();
					sb.append("<a href=\"").append(href).append("\">").append(doc.getTitle()).append("</a><br/>"); 
				}
			}
		}
		
		return sb.toString(); 
	}
	
	public static void main(String[] args) {
		try {
			HtmlEmail email = new HtmlEmail ();
			email.setHostName(Constants.SMTP_HOST);
			email.setCharset("gb2312"); 
			email.setSubject("邮件信息订阅");
			email.setHtmlMsg("body");
			email.addTo("lsheng@cyberway.net.cn");
			email.setFrom(Constants.DEFAULT_FROMEMAIL);
			//判断是否需要发送邮件者的用户密码。不同的MailHost对发送者否需要验证也是不同有的需要有的不需要
			if(Boolean.parseBoolean(Constants.EMAIL_NEED_AUTHENTICATION)){ 
				email.setAuthentication(Constants.DEFAULT_FROMEMAIL,Constants.DEFAULT_FROMEMAILPASS);
			}
			//email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		} 
		
		
		 Calendar c = Calendar.getInstance();
		 c.setTime(new Date());
		 c.add(Calendar.DATE, -1);
		 System.out.println(c.getTime().toLocaleString()); 
	}

}
