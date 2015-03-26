package com.cyberway.core.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author caiw
 */
public class MailServiceImp implements MailService {
	protected static final Log log = LogFactory.getLog(MailServiceImp.class);

	private JavaMailSender mailSender;

	private FreeMarkerConfigurer freeMarkerEngine = null;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setFreeMarkerEngine(FreeMarkerConfigurer freeMarkerEngine) {
		this.freeMarkerEngine = freeMarkerEngine;
	}

	/**
	 * 发送SimpleMailMessage
	 */
	public void send(SimpleMailMessage msg) {
		try {
			mailSender.send(msg);
		} catch (MailException ex) {
			log.error(ex.getMessage());
		}
	}

	/**
	 * 使用模版发送HTML格式的SimpleMailMessage
	 */
	@SuppressWarnings("unchecked")
	public void send(SimpleMailMessage msg, String templateName, Map model) {
		// 生成html邮件内容
		String content = generateEmailContent(templateName, model);

		msg.setText(content);
		mailSender.send(msg);

	}

	/**
	 * 简易的发送接口
	 */
	public void send(String emailAddress, String subject, String bodyText)
			throws MessagingException {
		send(new String[] { emailAddress }, subject, bodyText);
	}

	/**
	 * 简易的群发接口
	 */
	public void send(String[] emailAddresses, String subject, String bodyText)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(emailAddresses);
		helper.setText(bodyText);
		helper.setSubject(subject);

		mailSender.send(message);
	}

	/**
	 * 使用Freemarker 根据模版生成邮件内容
	 */
	public String generateEmailContent(String templateName, Map map) {
		String result = null;
		try {
			Template t = freeMarkerEngine.getConfiguration().getTemplate(
					templateName);
			result = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
		} catch (TemplateException e) {
			log.error("Error while processing FreeMarker template ", e);
			return null;
		} catch (FileNotFoundException e) {
			log.error("Error while open template file ", e);
			return null;
		} catch (IOException e) {
			log.error("Error while generate Email Content ", e);
			return null;
		}
		return result;
	}
}
