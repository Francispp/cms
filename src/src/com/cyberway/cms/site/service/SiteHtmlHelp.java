package com.cyberway.cms.site.service;

import java.io.File;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Document;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.core.utils.FileUtil;

public class SiteHtmlHelp {
    private File storeDir;//文件对象
	//private ServletContext servletContext;
	private SiteCache siteCache;

/**
 * 发布成静态页面
 * @param doc
 * @return
 */
public boolean getDocumentHtml(Document doc){
	//HttpURLConnection connection = null;
	String url="";
	Channel channel=this.siteCache.getChannelFromCach(doc.getChannel().getId());
	CmsSite site=this.siteCache.getSiteFromCache(channel.getSite().getOid().toString());
	if(site.getIshtml()!=1)
		return false;
	  String filepath=getStoreDir().getAbsolutePath()+File.separator+site.getOid()+File.separator+channel.getChannelPath();
	   File file=new File(filepath);
	    if(!file.exists())
	    	file.mkdirs();
	  filepath+=File.separator+doc.getId()+".html"; 
	  //servletContext.getServerInfo()
	//url="http://"+site.getSitehttp()+":86/"+channel.getChannelPath()+"/"+doc.getId()+".html3";
	url=Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL + "localhost:86/cms/docInfo!view.action?id="+doc.getId()+"&templateId=63&channelId="+channel.getId()+"";
	//logger.info("url:"+url);
	return FileUtil.saveUrlAs(url, filepath);
}
/**
 * 删除已发布的html
 * @param doc
 * @return
 */
public boolean deleteDocumentHtml(Document doc){
	return true;
}
/**
 * 获得信息的静态地址
 * @param channel
 * @param docid
 * @return
 */
public String getHtmlPath(Channel channel,String docid){
	String url="/"+Constants.STATICHTML_PATH+File.separator+channel.getSite().getOid()+File.separator+channel.getChannelPath()+File.separator+docid+".html";
	
	return url;
}
public File getStoreDir() {
	return storeDir;
}
public void setStoreDir(File storeDir) {
	this.storeDir = storeDir;
}
//public ServletContext getServletContext() {
//	return servletContext;
//}
//public void setServletContext(ServletContext servletContext) {
//	this.servletContext = servletContext;
//}

public SiteCache getSiteCache() {
	return siteCache;
}
public void setSiteCache(SiteCache siteCache) {
	this.siteCache = siteCache;
}

}
