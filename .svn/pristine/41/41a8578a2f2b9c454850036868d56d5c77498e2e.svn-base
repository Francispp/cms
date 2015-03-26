package com.cyberway.common.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService
{
	private String _smtpHost;
	private String _smtpPassword;
	private String _smtpUser;
	private String _smtpAuthUserName;
	private String _smtpAuthPassword;
	
	public String getSmtpAuthPassword()
	{
		return _smtpAuthPassword;
	}

	public void setSmtpAuthPassword(String smtpAuthPassword)
	{
		_smtpAuthPassword = smtpAuthPassword;
	}

	public String getSmtpAuthUserName()
	{
		return _smtpAuthUserName;
	}

	public void setSmtpAuthUserName(String smtpAuthUserName)
	{
		_smtpAuthUserName = smtpAuthUserName;
	}

	public String getSmtpHost()
	{
		return _smtpHost;
	}

	public void setSmtpHost(String smtpHost)
	{
		_smtpHost = smtpHost;
	}

	public String getSmtpPassword()
	{
		return _smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword)
	{
		_smtpPassword = smtpPassword;
	}

	public String getSmtpUser()
	{
		return _smtpUser;
	}

	public void setSmtpUser(String smtpUser)
	{
		_smtpUser = smtpUser;
	}

	public void sendEMail (String subject, String content, String emailAddress)
	{
		try
        {
            Properties props = new Properties();
            Session sendMailSession;
            Store store;
            Transport transport;
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.host", getSmtpHost());
            props.put("mail.smtp.user", getSmtpUser());
            props.put("mail.smtp.password", getSmtpPassword());
            PopupAuthenticator popA= new PopupAuthenticator();
            PasswordAuthentication pop = popA.performCheck(getSmtpAuthUserName(), getSmtpAuthPassword());
            sendMailSession = Session.getInstance(props, popA);
            Message newMessage = new MimeMessage(sendMailSession);
            newMessage.setFrom(new InternetAddress(getSmtpUser()));
            newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            newMessage.setSubject(subject);
            newMessage.setSentDate(new Date(System.currentTimeMillis()));
            newMessage.setText(content);
            transport = sendMailSession.getTransport("smtp");
            transport.send(newMessage);
        }
        catch (MessagingException ex)
        {
            throw new RuntimeException (ex);
        }
	}
}
