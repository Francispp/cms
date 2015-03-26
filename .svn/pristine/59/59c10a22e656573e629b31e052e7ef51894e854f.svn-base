package com.cyberway.staticer.cache;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.siteDistribution.view.SiteDistributionController;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.staticer.Configuration;
import com.cyberway.staticer.ftp.CompositeFTPClient;
import com.cyberway.staticer.ftp.FTPClient;

/**
 * 监听页面缓存事件，进行静态页面文件分发
 * @author helfen
 *
 */
public class FileDispatcherListener implements EventListener
{
	private String _defaultRoot = "/";
	//private FTPClient _ftpClient;
	
	//private FTPFlv _ftpFlv;
	
	public void setDefaultRoot(String defaultRoot)
	{
		_defaultRoot = defaultRoot;
	}
	
	/*public void setFtpClient(FTPClient ftpClient)
	{
		_ftpClient = ftpClient;
	}
	public void setFtpFlv(FTPFlv ftpFlv) {
		this._ftpFlv = ftpFlv;
	}

	public void afterflvSave(final PageKey key, final byte[] pageData)
	{
		_ftpFlv.upload(resolveFileName(key), pageData);
	}

	public void afterflvRemove(PageKey key) 
	{
		_ftpFlv.delete(resolveFileName(key));
	}*/

	public void afterSave(final PageKey key, final byte[] pageData) {
		SiteManagerService siteService = (SiteManagerService) ServiceLocator.getBean("siteManagerService");
		CmsSite site = siteService.findByHttp(key.getHost(), 0);
		FTPClient ftpClient =  new CompositeFTPClient(site.getOid(), SiteDistributionController.HTML_RESOURCE);
		ftpClient.upload(resolveFileName(key), pageData);
	}

	public void afterRemove(PageKey key) {
		SiteManagerService siteService = (SiteManagerService) ServiceLocator.getBean("siteManagerService");
		CmsSite site = siteService.findByHttp(key.getHost(), 0);
		FTPClient ftpClient =  new CompositeFTPClient(site.getOid(), SiteDistributionController.HTML_RESOURCE);
		ftpClient.delete(resolveFileName(key));
	}
	
	protected String resolveFileName (PageKey key)
	{
		StringBuilder fileName = new StringBuilder(String.format("%s%s/", _defaultRoot, key.getHost())); 
						
		if (key.getParameters().containsKey(Configuration.paramChannelId()) && key.getParameters().containsKey(Configuration.documentId ()))
		{
			fileName.append(Configuration.channelFolderPrefix() + key.getParameters().get(Configuration.paramChannelId()) + "/");
			fileName.append(Configuration.documentFolderPrefix() + key.getParameters().get(Configuration.documentId()) + "/");
			fileName.append(StringUtils.substringAfterLast(key.getPath(), "/"));
		}
		else if (key.getParameters().containsKey(Configuration.paramTemplateId()))
		{
			if (key.getParameters().containsKey(Configuration.paramChannelId()))
			{
				fileName.append(Configuration.channelFolderPrefix() + key.getParameters().get(Configuration.paramChannelId()) + "/");
			}
			
			fileName.append(Configuration.templateFolderPrefix() + key.getParameters().get(Configuration.paramTemplateId()) + "/");
			fileName.append(Configuration.roleFolderPrefix() + key.getRole() + "/");
			fileName.append(StringUtils.substringAfterLast(key.getPath(), "/"));
		}
		else
		{
			fileName.append(StringUtils.replace(key.getPath(), "/", "/"));
		}
		
		Iterator<Entry<String, String>> iterator = key.getParameters().entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, String> pair = iterator.next();
			
			if (!pair.getKey().startsWith(Configuration.flagParamPrefix()))
			{
				fileName.append(String.format("%s%s%s%s", Configuration.paramSep(), pair.getKey(), Configuration.paramNameValSep(), pair.getValue()));
			}
		}
		
		fileName.append(String.format("%s%s", Configuration.roleSep(), key.getRole()));
		
		return fileName.toString();
	}

	
}
