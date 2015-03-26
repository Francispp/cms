package com.cyberway.cms.email.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.cyberway.cms.Constants;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.common.ExpressionEvaluator;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.common.email.service.EmailLogManagerService;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.BaseController;
import com.opensymphony.xwork2.ActionSupport;

public class EmailController extends BaseController
{
	private static final Pattern PATTERN = Pattern.compile("\\$\\{\\w+\\}");
	
	private Long _documentId;
	private String _to;
	private String _cc;
	private String _subject;
	private String _body;
	private String returnValue;
	private String address;
	private String jumUrl;
	
	private String from;
	private String fromPass;
	
	
	public String getFromPass() {
		return fromPass;
	}

	public void setFromPass(String fromPass) {
		this.fromPass = fromPass;
	}

	public String getJumUrl() {
		return jumUrl;
	}

	public void setJumUrl(String jumUrl) {
		this.jumUrl = jumUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public UserManagerService getUserManagerService ()
	{
		return (UserManagerService)ServiceLocator.getBean("userManagerService");
	}
	
	public DocumentCommonService getDocumentCommonService ()
	{
		return (DocumentCommonService)ServiceLocator.getBean("documentCommonService");
	}
	
	public ExpressionEvaluator getExpressionEvaluator ()
	{
		return (ExpressionEvaluator)ServiceLocator.getBean("expressionEvaluator");
	}
	
	public Long getDocumentId()
	{
		return _documentId;
	}
	
	public void setDocumentId(Long documentId)
	{
		_documentId = documentId;
	}
	
	public String getTo()
	{
		return _to;
	}
	
	public void setTo(String to)
	{
		_to = to;
	}
	
	public String getCc()
	{
		return _cc;
	}
	
	public void setCc(String cc)
	{
		_cc = cc;
	}
	
	public String getSubject()
	{
		return _subject;
	}
	
	public void setSubject(String subject)
	{
		_subject = subject;
	}
	
	public String getBody()
	{
		return _body;
	}
	
	public void setBody(String body)
	{
		_body = body;
	}
	
	public String edit ()
	{
		return ActionSupport.INPUT;
	}
	
	
	public String sendTo() throws Exception
	{
		try {
			HtmlEmail email = new HtmlEmail ();
			email.setHostName(Constants.SMTP_HOST);
			email.setCharset("gb2312"); 
			email.setSubject(getSubject());
			email.setHtmlMsg(getBody());
			email.addTo(getTo() == null ? Constants.DEFAULT_SENDER : getTo());
			email.setFrom(getFrom() == null ? Constants.DEFAULT_FROMEMAIL : getFrom());
			//判断是否需要发送邮件者的用户密码。不同的MailHost对发送者否需要验证也是不同有的需要有的不需要
			if(Boolean.parseBoolean(Constants.EMAIL_NEED_AUTHENTICATION)){
				if(!StringUtils.isEmpty(getFrom()))
					email.setAuthentication(getFrom(),getFromPass());
				else
					email.setAuthentication(Constants.DEFAULT_FROMEMAIL,Constants.DEFAULT_FROMEMAILPASS);
			}
			email.send();
			getHttpServletRequest().getSession().setAttribute("mailMsg", "发送成功！");
		} catch (RuntimeException e) {
			e.printStackTrace();
			getHttpServletRequest().getSession().setAttribute("mailMsg", "发送失败！");
		}
		return "jump";
	} 
	
	 
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((jumUrl == null) ? 0 : jumUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EmailController other = (EmailController) obj;
		if (jumUrl == null) {
			if (other.jumUrl != null)
				return false;
		} else if (!jumUrl.equals(other.jumUrl))
			return false;
		return true;
	}
	
	public String rssSend()throws Exception
	{
		 EmailLogManagerService emailLogManagerService = (EmailLogManagerService)ServiceLocator.getBean("EmailLogManagerService");	 
		 List<CoreEmailLog> logs = emailLogManagerService.findBy("status", new Integer(0));
		 for(CoreEmailLog emailLog : logs)
		 {
			 if(emailLog.getStatus() == 0)
			 {
			 try {
				 String body="";   
			      String line = "";  
			      HtmlEmail email = new HtmlEmail ();
			      if(emailLog.getContentURL() != null && (emailLog.getEmailContent()==null || ObjectUtils.equals(emailLog.getEmailContent(), "")))
			      {
				 URL url = new URL  (emailLog.getContentURL());   
				 HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();   
				
				 BufferedReader in  = new BufferedReader(   
	                     new InputStreamReader(   
	                             urlConnection.getInputStream()));   
	             while ((line = in.readLine()) != null) {    
	            	 body+=line;   
	             }   
	             
			      }
			      else
			    	  body = emailLog.getEmailContent();
				email.setHostName("mail.cyberway.net.cn");
				email.setCharset("gb2312"); 
				email.setSubject(emailLog.getEmailSubject());
				//email.setTextMsg("中文"); 
				email.setHtmlMsg(body);
				if(emailLog.getTo() != null && !ObjectUtils.equals(emailLog.getTo(), ""))
				email.addTo(emailLog.getTo());
				if(emailLog.getCc() != null && !ObjectUtils.equals(emailLog.getCc(), ""))
				email.addCc(emailLog.getCc());
				email.setFrom( Constants.DEFAULT_FROMEMAIL);   
				email.setAuthentication(Constants.DEFAULT_FROMEMAIL,Constants.DEFAULT_FROMEMAILPASS);   
				email.send();
				emailLog.setStatus(1);
				emailLogManagerService.saveOrUpdate(emailLog);
				System.out.println("OK");
				} catch (Exception e) { 
					e.printStackTrace();
				}  
			 }
		 }
		 return ActionSupport.SUCCESS;
	}

	public String send () throws Exception
	{
		try
		{
		BaseDocument document = getDocumentId() == null ? null : getDocumentCommonService().get(getDocumentId());
		String siteHttp =getHttpServletRequest().getServerName();
		int siteport = getHttpServletRequest().getServerPort();
		String docUrl ="";
		if(document != null)
		{
		docUrl = Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL + siteHttp + (siteport == 80 ? "" :  ":" + siteport)+"/cms/docInfo!view.action?id="+document.getId();
		}
		HtmlEmail email = new HtmlEmail ();
		email.setHostName(Constants.SMTP_HOST);
		//email.setAuthentication(Constants.SMTP_USERNAME, Constants.SMTP_PASSWORD);
		for (String to : StringUtils.split(getTo(), ";"))
		{
			to = StringUtils.trim(to);
			if (!StringUtils.isBlank(to))
			{
				
				//CoreUser user = getUserManagerService().get(Long.valueOf(to));
				CoreUser user = getUserManagerService().getADUserById(Long.valueOf(to));
				if (!StringUtils.isBlank(user.getHotmail()))
				{
					email.addTo(user.getHotmail());
				}
			}
		}
		for (String cc : StringUtils.split(getCc(), ";"))
		{
			cc = StringUtils.trim(cc);
			if (!StringUtils.isBlank(cc))
			{
				//CoreUser user = getUserManagerService().get(Long.valueOf(cc));
				CoreUser user = getUserManagerService().getADUserById(Long.valueOf(cc));
				if (!StringUtils.isBlank(user.getEmail()))
				{
					email.addCc(user.getEmail());
				}
			}
		}
		
		String subject = getSubject();
		Matcher matcher = PATTERN.matcher(getSubject());
		while (matcher.find())
		{
			String var = matcher.group().substring(2, matcher.group().length() - 1);
			if (!StringUtils.isBlank(var) && document != null)
			{
				subject = subject.replace("${" + var + "}", ObjectUtils.toString(getExpressionEvaluator().getValue(document, var)));
			}
		}
		//email.setSubject(new String(subject.getBytes("ISO8859-1"),"GBK"));
		email.setSubject(subject);
		String body = "";
		if(StringUtils.isNotEmpty(address) && address.equals("true"))
		{
			body = getBody()+"<br>URL:<a href='" + docUrl + "' target='_blank'>"+ docUrl +"</a>";
		}
		else
		{
			body = getBody();
		}

		matcher = PATTERN.matcher(getBody());
		while (matcher.find())
		{
			String var = matcher.group().substring(2, matcher.group().length() - 1);
			if (!StringUtils.isBlank(var) && document != null)
			{
				body = body.replace("${" + var + "}", ObjectUtils.toString(getExpressionEvaluator().getValue(document, var)));
			}
		}
         //new String(body.getBytes("ISO8859-1"), "GBK")
		email.setHtmlMsg(body);
		String FromEmail = "";
		Loginer loginer = (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
		if(loginer != null)
		{
			CoreUser sendUser = getUserManagerService().get(loginer.getUserid());
			if(sendUser != null && StringUtils.isNotEmpty(sendUser.getHotmail()))
			{
				FromEmail = sendUser.getHotmail();
			}
		}
		email.setFrom((StringUtils.isNotEmpty(FromEmail)?FromEmail:Constants.DEFAULT_SENDER));
		email.send();
		}catch(Exception e)
		{
			e.printStackTrace();
			setReturnValue("发送失败！");
			return ActionSupport.SUCCESS;
		}
		setReturnValue("发送成功！");
		return ActionSupport.SUCCESS;
	}
	
	
	public static void main(String[] args) throws IOException {
		try {
			 String body="";   
		      String line = "";   
			 URL url = new URL  ("http://www.baidu.com/");   
			 HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();   
			HtmlEmail email = new HtmlEmail ();
			 BufferedReader in  = new BufferedReader(   
                     new InputStreamReader(   
                             urlConnection.getInputStream()));   
             while ((line = in.readLine()) != null) {    
            	 body+=line;   
             }   
			email.setHostName("mail.cyberway.net.cn");
			email.setCharset("gb2312"); 
			email.setSubject("中文");
			//email.setTextMsg("中文"); 
			email.setHtmlMsg(body);
			email.addTo(Constants.DEFAULT_SENDER);
			email.setFrom( Constants.DEFAULT_FROMEMAIL);   
			email.setAuthentication(Constants.DEFAULT_FROMEMAIL,Constants.DEFAULT_FROMEMAILPASS);   
			email.send();
			System.out.println("OK");
		} catch (EmailException e) { 
			e.printStackTrace();
		}  
	}

	public String get_body() {
		return _body;
	}

	public void set_body(String _body) {
		this._body = _body;
	}

	public String get_cc() {
		return _cc;
	}

	public void set_cc(String _cc) {
		this._cc = _cc;
	}

	public Long get_documentId() {
		return _documentId;
	}

	public void set_documentId(Long id) {
		_documentId = id;
	}

	public String get_subject() {
		return _subject;
	}

	public void set_subject(String _subject) {
		this._subject = _subject;
	}

	public String get_to() {
		return _to;
	}

	public void set_to(String _to) {
		this._to = _to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
}
