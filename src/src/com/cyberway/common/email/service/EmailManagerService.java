package com.cyberway.common.email.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

import com.cyberway.cms.Constants;
import com.cyberway.cms.internal.template.DefaultTemplateParser;
import com.cyberway.cms.internal.template.EmailPageWriter;
import com.cyberway.common.email.domain.CoreEmail;
import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;

public class EmailManagerService extends HibernateEntityDao<CoreEmail> {
	
	private EmailPageWriter emailPageWriter;

	private DefaultTemplateParser defaultTemplateParser;

	private String hrHost;
	private String hrUsername;
	private String hrPassword;
	
    /**
     * @author lan
     * 发送邮件模板
     * @param To  接收邮件人
     * @param Cc  抄送
     * @param setFrom 发送人
     * @param attach 附件
     * @param templateid 邮件模板ID
     * @param object 业务对象
     * @param user 为用户对象
     * @return
     * @throws Exception
     */
	public String sentEmail(HashMap To,HashMap Cc,HashMap setFrom,HashMap attach,Long templateid,Object user,Object object) throws Exception{
		CoreEmail emailTemplate = get(templateid);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
		getEmailPageWriter().write(getDefaultTemplateParser().parseTemplate(emailTemplate.getTemplatecontent()), outputStream, user,object);
		HtmlEmail email = new HtmlEmail ();
		CoreEmailLog emailLog = new CoreEmailLog();
		emailLog.setCc(Cc.toString());
		emailLog.setTo(To.toString());
		emailLog.setSetFrom(setFrom.toString());
		emailLog.setAttach(attach.toString());
		emailLog.setEmailSubject(emailTemplate.getTemplatename());
		emailLog.setEmailContent(emailTemplate.getTemplatecontent());
		emailLog.setCoreEmail(emailTemplate);
		emailLog.setCreatetime(new java.util.Date(System.currentTimeMillis()));
		emailLog.setStatus(1);
		try
		{
		email.setHostName(Constants.SMTP_HOST);
		Set entries = setFrom.entrySet();
		Iterator iter = entries.iterator(); 
		if(iter.hasNext())
		{
			Map.Entry  entry = (Map.Entry)iter.next();
			  Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  email.setFrom(val.toString(),key.toString());
			}
			else
		    	  return "发送失败 ！";
		}
		entries = To.entrySet(); 
		iter = entries.iterator(); 
		while(iter.hasNext()) 
		{ 
			Map.Entry  entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  email.addTo(val.toString(),key.toString());
			}
		} 
		entries = Cc.entrySet(); 
		iter = entries.iterator(); 
		while(iter.hasNext()) 
		{ 
			Map.Entry  entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  email.addCc(val.toString(),key.toString());
			}
		} 
		entries = attach.entrySet(); 
		iter = entries.iterator(); 
		while(iter.hasNext()) 
		{ 
			Map.Entry  entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  EmailAttachment attachment = new EmailAttachment();
		    	  attachment.setPath(val.toString());
		    	  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		    	  attachment.setName(key.toString());
		    	  email.attach(attachment);
			}
		} 
		
			if(emailTemplate != null)
			{
				email.setSubject(emailTemplate.getTemplatename());
				email.setHtmlMsg(outputStream.toString());
			}
		email.send();
		}
		catch(Exception e)
		{
			emailLog.setStatus(0);
			save(emailLog);
			e.printStackTrace();
		}
		
		save(emailLog);
		return "发送成功 ！";
		
	}
	
	/**
	 * 用于定时工作类调用重新发送邮件
	 * @param coreEmailLog 邮件LOG对象
	 * @return
	 * @throws Exception
	 */
	public String reSentEmail(CoreEmailLog coreEmailLog) throws Exception{
		EmailLogManagerService emailLogManagerService = (EmailLogManagerService)ServiceLocator.getBean("emailLogManagerService");
		HashMap To = new HashMap();
		HashMap Cc = new HashMap();
		HashMap setFrom = new HashMap();
		HashMap attach = new HashMap();
		StringTokenizer stringTo = new StringTokenizer(coreEmailLog.getTo().replace("{", "").replace("}", ""),";");
		StringTokenizer stringCc = new StringTokenizer(coreEmailLog.getCc().replace("{", "").replace("}", ""),";");
		StringTokenizer stringSetFrom = new StringTokenizer(coreEmailLog.getSetFrom().replace("{", "").replace("}", ""),";");
		StringTokenizer stringAttach = new StringTokenizer(coreEmailLog.getAttach().replace("{", "").replace("}", ""),";");
		while(stringTo.hasMoreTokens())
		{
			String token = stringTo.nextToken();
			String[] s = token.split("=");
			if(s.length ==2)
			{
				To.put(s[0], s[1]);
			}
		}
		while(stringCc.hasMoreTokens())
		{
			String token = stringCc.nextToken();
			String[] s = token.split("=");
			if(s.length ==2)
			{
				Cc.put(s[0], s[1]);
			}
		}
		while(stringSetFrom.hasMoreTokens())
		{
			String token = stringSetFrom.nextToken();
			String[] s = token.split("=");
			if(s.length ==2)
			{
				setFrom.put(s[0], s[1]);
			}
		}
		while(stringAttach.hasMoreTokens())
		{
			String token = stringAttach.nextToken();
			String[] s = token.split("=");
			if(s.length ==2)
			{
				attach.put(s[0], s[1]);
			}
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
		HtmlEmail email = new HtmlEmail ();
		coreEmailLog.setStatus(1);
		try
		{
		email.setHostName(Constants.SMTP_HOST);
		Set entries = setFrom.entrySet();
		Iterator iter = entries.iterator(); 
		if(iter.hasNext())
		{
			Map.Entry  entry = (Map.Entry)iter.next();
			  Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  email.setFrom(val.toString(),key.toString());
			}
			else
		    	  return "发送失败 ！";
		}
		entries = To.entrySet(); 
		iter = entries.iterator(); 
		while(iter.hasNext()) 
		{ 
			Map.Entry  entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  email.addTo(val.toString(),key.toString());
			}
		} 
		entries = Cc.entrySet(); 
		iter = entries.iterator(); 
		while(iter.hasNext()) 
		{ 
			Map.Entry  entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  email.addCc(val.toString(),key.toString());
			}
		} 
		entries = attach.entrySet(); 
		iter = entries.iterator(); 
		while(iter.hasNext()) 
		{ 
			Map.Entry  entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey(); 
			Object  val = entry.getValue();
			if(val != null && StringUtils.isNotEmpty(val.toString()))
			{
		    	  EmailAttachment attachment = new EmailAttachment();
		    	  attachment.setPath(val.toString());
		    	  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		    	  attachment.setName(key.toString());
		    	  email.attach(attachment);
			}
		} 

				email.setSubject(coreEmailLog.getEmailSubject());
				email.setHtmlMsg(coreEmailLog.getEmailContent());

		email.send();
		}
		catch(Exception e)
		{
			coreEmailLog.setReSendtime(new Date(System.currentTimeMillis()));
			coreEmailLog.setStatus(0);
			emailLogManagerService.saveOrUpdate(coreEmailLog);
			e.printStackTrace();
		}
		coreEmailLog.setReSendtime(new Date(System.currentTimeMillis()));
		emailLogManagerService.saveOrUpdate(coreEmailLog);
		return "发送成功 ！";
		
	}
	
	/**
	 * 发送HR邮件
	 * @param to 收件人
	 * @param cc 抄送
	 * @param attach 附件
	 * @param subject 主题
	 * @param content 内容
	 * @return
	 */
	public boolean sentHrEmail(HashMap<String, String> to,HashMap<String, String> cc,HashMap<String, String> attach,String subject,String content){
		boolean isSuccess = false;
		HashMap<String, String> from = new HashMap<String, String>();
		from.put(hrUsername, hrUsername);
		HtmlEmail email = new HtmlEmail ();
		CoreEmailLog emailLog = new CoreEmailLog();
		emailLog.setCc(cc.toString());
		emailLog.setTo(to.toString());
		emailLog.setSetFrom(from.toString());
		emailLog.setAttach(attach.toString());
		emailLog.setEmailSubject(subject);
		emailLog.setEmailContent(content);
		emailLog.setCreatetime(new java.util.Date(System.currentTimeMillis()));
		emailLog.setStatus(1);
		try{
			email.setHostName(hrHost);
			email.setAuthentication(hrUsername, hrPassword);
			email.setCharset("utf-8");
			email.setFrom(hrUsername);
			Set<Entry<String, String>> entries = to.entrySet();
			Iterator<Entry<String, String>> iter = entries.iterator();
			while(iter.hasNext()){
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String val = entry.getValue();
				if(StringUtils.isNotEmpty(val)){
					email.addTo(val,key);
				}
			}
			entries = cc.entrySet(); 
			iter = entries.iterator(); 
			while(iter.hasNext()) 
			{ 
				Entry<String, String> entry = iter.next(); 
				String key = entry.getKey(); 
				String val = entry.getValue();
				if(StringUtils.isNotEmpty(val)){
			    	  email.addCc(val,key);
				}
			}
			entries = attach.entrySet(); 
			iter = entries.iterator(); 
			while(iter.hasNext()) 
			{ 
				Entry<String, String> entry = iter.next(); 
				String key = entry.getKey(); 
				String val = entry.getValue();
				if(StringUtils.isNotEmpty(val))
				{
			    	EmailAttachment attachment = new EmailAttachment();
			    	attachment.setPath(val);
			    	attachment.setDisposition(EmailAttachment.ATTACHMENT);
			    	attachment.setName(key);
			    	email.attach(attachment);
				}
			} 
			email.setSubject(subject);
			email.setHtmlMsg(content);
			email.send();
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			emailLog.setStatus(0);
		}
		save(emailLog);
		return isSuccess;
	}
	
	public DefaultTemplateParser getDefaultTemplateParser() {
		return defaultTemplateParser;
	}


	public void setDefaultTemplateParser(DefaultTemplateParser defaultTemplateParser) {
		this.defaultTemplateParser = defaultTemplateParser;
	}


	public EmailPageWriter getEmailPageWriter() {
		return emailPageWriter;
	}


	public void setEmailPageWriter(EmailPageWriter emailPageWriter) {
		this.emailPageWriter = emailPageWriter;
	}

	public String getHrHost() {
		return hrHost;
	}

	public void setHrHost(String hrHost) {
		this.hrHost = hrHost;
	}

	public String getHrUsername() {
		return hrUsername;
	}

	public void setHrUsername(String hrUsername) {
		this.hrUsername = hrUsername;
	}

	public String getHrPassword() {
		return hrPassword;
	}

	public void setHrPassword(String hrPassword) {
		this.hrPassword = hrPassword;
	}
}
