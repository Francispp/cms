package com.cyberway.issue;

import java.io.File;

import com.cyberway.core.web.BaseController;
import com.cyberway.issue.crawler.framework.CrawlController;
import com.cyberway.issue.crawler.settings.XMLSettingsHandler;

public class ConsoleController extends BaseController{
	
public String execute()throws Exception{
	try{
		 File file = new File("D:/Project/CMS3/jobs/test/order.xml");
		 XMLSettingsHandler xmlSetting = new XMLSettingsHandler(file);
		 xmlSetting.initialize();
		 CrawlController crawl = new CrawlController();
		 crawl.initialize(xmlSetting);
		 crawl.requestCrawlStart();
		 
		}catch(Exception e){e.printStackTrace();}
		return "index";
	}
}
