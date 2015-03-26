package com.cyberway.cms.template.service;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.template.PageWriter;
import com.cyberway.cms.template.TemplateException;
import com.cyberway.cms.template.TemplateParser;
import com.cyberway.common.service.DynamicPageService;
import com.cyberway.common.service.EntityEventListener;
import com.cyberway.common.service.HibernateEventListener;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class TemplateService implements EntityEventListener, InitializingBean, ServletContextAware, ApplicationContextAware
{
	protected Log logger = LogFactory.getLog(getClass());//日志输出
	
	public static class TemplateCacheKey 
	{
		private Integer _type;
		private String _body;
		
		public TemplateCacheKey(Integer type, String body)
		{
			_type = type;
			_body = body;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof TemplateCacheKey == false) 
			{
			     return false;
		   }
		   if (this == obj) 
		   {
		     return true;
		   }

		   TemplateCacheKey key = (TemplateCacheKey)obj;
		   
		   return new EqualsBuilder().append(_body, key._body).append(_type, key._type).isEquals();
		}
		
		@Override
		public int hashCode()
		{
			return new HashCodeBuilder ().append(_body).append(_type).toHashCode();
		}
	}
	
	private File _storeDir;
	private ApplicationContext _applicationContext;
	private ServletContext _servletContext;
	private DynamicPageService _dynamicPageService;
	private SiteManagerService _siteManagerService;
	private ChannelManagerService _channelManagerService;
	private Map<Integer, TemplateParser> _templateParserMap;
	private TemplateManagerService _templateManager;
	private HibernateEventListener _hibernateEventListener;
	public static final String suffix_name="_include";

	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException
	{
		_applicationContext = applicationcontext;
	}
	
	public ServletContext getServletContext()
	{
		return _servletContext;
	}

	public void setServletContext(ServletContext servletContext)
	{
		_servletContext = servletContext;
	}

	public File getStoreDir()
	{
		return _storeDir;
	}

	public void setStoreDir(File storeDir)
	{
		_storeDir = storeDir;
	}

	public DynamicPageService getDynamicPageService()
	{
		return _dynamicPageService;
	}

	public void setDynamicPageService(DynamicPageService dynamicPageService)
	{
		_dynamicPageService = dynamicPageService;
	}

	public SiteManagerService getSiteManagerService()
	{
		return _siteManagerService;
	}

	public void setSiteManagerService(SiteManagerService siteManagerService)
	{
		_siteManagerService = siteManagerService;
	}

	public ChannelManagerService getChannelManagerService()
	{
		return _channelManagerService;
	}

	public void setChannelManagerService(ChannelManagerService channelManagerService)
	{
		_channelManagerService = channelManagerService;
	}

	public Map<Integer, TemplateParser> getTemplateParserMap()
	{
		return _templateParserMap;
	}

	public void setTemplateParserMap(Map<Integer, TemplateParser> templateParserMap)
	{
		_templateParserMap = templateParserMap;
	}

	public PageWriter getPageWriter()
	{
		return (PageWriter)ServiceLocator.getBean("template.pageWriter");
	}

	public TemplateManagerService getTemplateManager()
	{
		return _templateManager;
	}

	public void setTemplateManager(TemplateManagerService templateManager)
	{
		_templateManager = templateManager;
	}

	public HibernateEventListener getHibernateEventListener()
	{
		return _hibernateEventListener;
	}

	public void setHibernateEventListener(HibernateEventListener hibernateEventListener)
	{
		_hibernateEventListener = hibernateEventListener;
	}
	
	public String getTemplatePage (int templateType, String templateBody)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
		
		getPageWriter().write(getTemplateParserMap().get(templateType).parseTemplate(templateBody), outputStream);
		
		return getDynamicPageService().getPage(outputStream.toString());
	}
	
	/**
	 * 发布站点下所有模板
	 * @param siteId
	 */
	public void sitePublish (long siteId)
	{
		//站点下的模板
		for (Template template : getTemplateManager().findBy("site_id", siteId))
		if (template.getIssued())
		{
			createTemplateFile(template);
			}
		//频道下的模板
/*		for (Channel channel : getChannelManagerService().findBy("site", getSiteManagerService().getSiteFromCache(siteId)))
		{
			channelPublish(channel.getId());
		}*/
	}
	/**
	 * 重新发布站点下所有模板
	 * @param siteId
	 * @return
	 */
	public boolean sitePublish(String siteId){
		if(StringUtil.isNumber(siteId)){
			sitePublish(new Long(siteId));
			return true;
		}else
			return false;
	}
	public void channelPublish (long channelId)
	{
		for (Template template : getTemplateManager().findBy("channel_id", channelId))
		{
			publish(template.getId());
		}
		
		for (Channel child : getChannelManagerService().findBy("parent", getChannelManagerService().getChannelFromCache(channelId)))
		{
			channelPublish(child.getId());
		}
	}
	
	/**
	 * 发布指定模板
	 * @param templateId
	 */
	public void publish (long templateId)
	{
		Template template = getTemplateManager().get(templateId);
		//File file = getTemplateFile(template.getSite_id(), template.getName());
		
		if (template.getIssued())
		{
			createTemplateFile(template);
			/*if (!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
			try{
			  getPageWriter().write(getTemplateParserMap().get(template.getType()).parseTemplate(template.getBody()), outputStream);
			}catch(Exception e){
				logger.error(template.getName()+"解析出错!");
				//e.printStackTrace();
			}
			getDynamicPageService().getPage(outputStream.toString(), DynamicPageService.DEFAULT_PAGE_ENCODING, file);*/
		}
	}
	
	/**
	 * 获得指定模板对应的url
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public String getTemplatePage (long templateId)throws Exception
	{
		//从缓冲中获得地址
		String tempUrl=getTemplateManager().getTemplateUrlByTemplateId(templateId);
		if(!StringUtil.isEmpty(tempUrl) && !Constants.IS_REALPATH){
			return tempUrl;
		}
		//从缓冲中获得
		Template template = getTemplateManager().getTemplateObjectByTemplateId(templateId);
		if(template==null)
		  throw new Exception("指定模板不存在！");
		tempUrl= Constants.getProperty("template.path", "/templates/")+String.valueOf(template.getSite_id())+"/"+template.getName() + DynamicPageService.JSP_EXTENSIONS;
		//tempUrl= Constants.getProperty("template.path", "/templates/")+FilenameUtils.concat(String.valueOf(template.getSite_id()), template.getName() + DynamicPageService.JSP_EXTENSIONS);
		//若使用绝对路径，同步更新
		if(Constants.IS_REALPATH){
			String sourceTempPaht=FilenameUtils.concat(getStoreDir().getAbsolutePath(), 
					FilenameUtils.concat(String.valueOf(template.getSite_id()), template.getName()+ DynamicPageService.JSP_EXTENSIONS));
			FileUtil.update(sourceTempPaht, _servletContext.getRealPath(tempUrl));
		}
		//缓存地址
		getTemplateManager().putTemplateUrlInCache(templateId, tempUrl);
		return tempUrl;
		/*if(Constants.IS_REALPATH)
			return getTemplateFile(template.getSite_id(), template.getName()).getAbsolutePath();
		 else	*/
		   //return "/" + getTemplateFile(template.getSite_id(), template.getName()).getAbsolutePath().substring(getServletContext().getRealPath("/").length());
		//return "/" + getTemplateFile(template.getSite_id(), template.getName()).getAbsolutePath().substring(getServletContext().getRealPath("/").length());
	}
	
	public void entityDeleted(Object entity)
	{
		if (ClassUtils.isAssignable(entity.getClass(), Template.class))
		{
			Template template = (Template)entity;
			File file = getTemplateFile(template.getSite_id(), template.getName());
			
			if (file.exists())
			{
				file.delete();
			}
		}
	}

	public void entityInserted(Object entity)
	{
		if (ClassUtils.isAssignable(entity.getClass(), Template.class))
		{
			Template template = (Template)entity;
			File file = getTemplateFile(template.getSite_id(), template.getName());
			
			if (template.getIssued())
			{
				if (!file.getParentFile().exists())
				{
					file.getParentFile().mkdirs();
				}
				
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
				
				getPageWriter().write(getTemplateParserMap().get(template.getType()).parseTemplate(StringUtils.trim(template.getBody())), outputStream);
				
				String body = outputStream.toString();
				//FIXME 页面标准化
				if(Template.TYPE_DETAILS == template.getType() || Template.TYPE_SUMMARY == template.getType() || Template.TYPE_INDEX == template.getType()){
					body = body.replace("<html>", "<!DOCTYPE html>\n<html>");
				}
				getDynamicPageService().getPage(body, DynamicPageService.DEFAULT_PAGE_ENCODING, file);
				//若为绝对，更新当前环境下
				if(Constants.IS_REALPATH){					
					String tfname=getTemplateFileName(template.getSite_id(), template.getName());
					com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(tfname,this._servletContext.getRealPath(tfname));
					}
			}
		}
	}

	public void entityModified(Object entity, Object[] state, Object[] oldState)
	{
		if (ClassUtils.isAssignable(entity.getClass(), Template.class))
		{
			String newName = (String)state[0];
			String oldName = oldState == null ? null : (String)oldState[0];
			Long newSiteid = (Long)state[8];
			Long oldSiteid = oldState == null ? null : (Long)oldState[8];
			//CmsSite newSite = (CmsSite)state[8];
			//CmsSite oldSite = oldState == null ? null : (CmsSite)oldState[8];
			
			Template template = (Template)entity;
			
			File oldFile = getTemplateFile(newSiteid, newName);//oldSiteid != null && oldName != null ? getTemplateFile(oldSiteid, oldName) : null;
			
			if (oldFile != null && oldFile.exists())
			{
				oldFile.delete();
				
			}
			//生成模板文件
			createTemplateFile(template);
		}
	}
    /**
     * 根据模板对象，生成模板文件
     * @param template
     */
    public void createTemplateFile(Template template){
		File newFile = getTemplateFile(template.getSite_id(), template.getName());//getTemplateFile(newSiteid, newName);
		
		if (template.getIssued())
		{
			if (!newFile.exists())
			{
				if (!newFile.getParentFile().exists())
				{
					newFile.getParentFile().mkdirs();
				}
			}	
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream ();
			//try{
			getPageWriter().write(getTemplateParserMap().get(template.getType()).parseTemplate(template.getBody()), outputStream);
			/*}catch(Exception e){
				e.printStackTrace();
			}*/
			String body = outputStream.toString();
			//FIXME 页面标准化
			if(Template.TYPE_DETAILS == template.getType() || Template.TYPE_SUMMARY == template.getType() || Template.TYPE_INDEX == template.getType()){
				body = body.replace("<html>", "<!DOCTYPE html>\n<html>");
			}
			getDynamicPageService().getPage(body, DynamicPageService.DEFAULT_PAGE_ENCODING, newFile);
			
			//若为绝对，更新当前环境下
			if(Constants.IS_REALPATH){	
				String tfname=getTemplateFileName(template.getSite_id(), template.getName());
				com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(tfname,this._servletContext.getRealPath(tfname));
			}
			//若存在包含文件，同步更新包含内容
			File includeFile = getTemplateFile(template.getSite_id(), template.getName()+suffix_name);
			{
				if (includeFile.exists()){
					updateIncludeTempate(template,includeFile);
					String includeFileName=FilenameUtils.concat(Constants.getProperty("template.path", "/templates/"), 
							FilenameUtils.concat(String.valueOf(template.getSite_id()), template.getName()+suffix_name + DynamicPageService.JSP_EXTENSIONS));
					//更新文件
					com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(includeFileName,includeFile.getAbsolutePath());						
				}
			}
		}
		else if (newFile.exists())
		{
			newFile.delete();
		}
    }
	/**
	 * 更新包含模板文件
	 * 
	 * @param template
	 * @param includeFile
	 * @return
	 */
	public boolean updateIncludeTempate(Template template, File includeFile) {
		if (includeFile == null)
			includeFile = getTemplateFile(template.getSite_id(), template
					.getName()
					+ suffix_name);

		Pattern REGEX_BODY = Pattern.compile("<body[^>/]*>(.*?)</body>",
				Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

		String templateBody = null;
		ByteArrayOutputStream outputStream=null;
		try{
		 outputStream = new ByteArrayOutputStream();
			getPageWriter().write(
					getTemplateParserMap().get(template.getType()).parseTemplate(
							template.getBody()), outputStream);		
		Matcher matcher = REGEX_BODY.matcher(outputStream.toString());
		templateBody="<%@ page contentType=\"text/html; charset=UTF-8\"%>";
		if (matcher.find()) {
			templateBody += matcher.group(1);
		}
		//templateBody="<%@ page contentType=\"text/html; charset=UTF-8\"%>"+templateBody;
		//StringUtil.toUtf8String(templateBody)
		getDynamicPageService().getPage(templateBody,
				DynamicPageService.DEFAULT_PAGE_ENCODING, includeFile);
		}catch (Exception ex){
			ex.printStackTrace();
			//throw new TemplateException (ex);
		}
		finally
		{
			IOUtils.closeQuietly(outputStream);
		}
		return true;
	}
	/**
	 * 获得文件（使用相对或绝对从配置中读取）
	 * @param siteId
	 * @param templateName
	 * @return
	 */
	public File getTemplateFile (Long siteId, String templateName)
	{
		return new File (FilenameUtils.concat(getStoreDir().getAbsolutePath(), 
				FilenameUtils.concat(String.valueOf(siteId), templateName + DynamicPageService.JSP_EXTENSIONS)));
	}

	/**
	 * 获回当前模板所在的路径
	 * @return
	 */
	public String getTemplatePath(){
		if(Constants.IS_REALPATH){
		  return Constants.ABSOLUTE_PATH+Constants.getProperty("template.path", "/templates/");
		}else
			return Constants.getProperty("template.path", "/templates/");
		
	}
    /**
     * 获得当前的相对路径
	 * @param siteId
	 * @param templateName
	 * @return
	 */
	public String getTemplateFileName (Long siteId, String templateName){
	   return FilenameUtils.concat(Constants.getProperty("template.path", "/templates/"), 
				FilenameUtils.concat(String.valueOf(siteId), templateName + DynamicPageService.JSP_EXTENSIONS));
   }
	
	public void afterPropertiesSet() throws Exception
	{
		getHibernateEventListener().getListeners().add(this);
	}
}
