package com.cyberway.core.mail;

import com.cyberway.core.test.BaseServiceTest;

public class TestMailUtil extends BaseServiceTest {
	MailService mailUtil;
	
	public TestMailUtil(){
		setDefaultRollback(true);
		//测试是否提交到数据库
	}

	public void setMailUtil(MailService mailUtil) {
		this.mailUtil = mailUtil;
	}
	public void testSentEmail()throws Exception{
		
		mailUtil.send("caiwen88@163.com", "test", "testtttttt");
		
	}
}
