package com.cyberway.core.mail;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * 提供发送SimpleMessage,发送使用Freemarker模版的HTML格式邮件,及简单版明文三种接口
 * @author caiw
 */
public interface MailService {
	/**
	 * 发送SimpleMailMessage的接口
 	 */
	void send(SimpleMailMessage msg);

	/**
	 * 使用模版发送HTML格式的SimpleMailMessage的接口
	 * @param msg 装有to,from,subject信息的SimpleMailMessage
	 * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
	 * @param model  渲染模版所需的数据
	 */
	void send(SimpleMailMessage msg, String templateName,Map model);

	/**
	 * 简易的发送接口
	 */
	void send(String emailAddress, String subject,String bodyText) throws MessagingException;

	/**
	 * 简易的群发接口
	 */
	void send(String[] emailAddresses, String subject,String bodyText) throws MessagingException;
}
