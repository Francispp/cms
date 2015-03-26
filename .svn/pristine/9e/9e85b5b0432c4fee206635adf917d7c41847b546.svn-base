package com.cyberway.staticer.gather;

import org.apache.log4j.Logger;

public class EmptyEventListener implements EventListener
{
	private static Logger log_ = Logger.getLogger(EmptyEventListener.class);
	
	public void onSuccessed(String url, String role)
	{
		System.out.println("采集成功success log--------------"+url);
	}

	
	public void onFailed(String url, String role)
	{
		log_.error("采集失败fail log--------------"+url);
	}
}
