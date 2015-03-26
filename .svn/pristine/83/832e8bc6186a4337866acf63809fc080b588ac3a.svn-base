package com.cyberway.cms.internal.template.token;

import java.io.File;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.PageWriter;
import com.cyberway.cms.template.TemplateParser;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.template.service.TemplateService;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.service.DynamicPageService;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class TemplateIncludeWriter extends AbstractComponentWriter implements ApplicationContextAware
{
	//private static final Pattern REGEX_BODY = Pattern.compile("<body[^>/]*>(.*?)</body>", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	
	private ApplicationContext _applicationContext;
	private TemplateManagerService _templateManagerService;
	private Map<Integer, TemplateParser> _templateParserMap;
	private DynamicPageService _dynamicPageService;
	private String _pageWriterBeanName;
	private TemplateService templateService;
	
	public static final String suffix_name="_include";
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException
	{
		_applicationContext = applicationcontext;
	}
	
	public String getPageWriterBeanName()
	{
		return _pageWriterBeanName;
	}

	public void setPageWriterBeanName(String pageWriterBeanName)
	{
		_pageWriterBeanName = pageWriterBeanName;
	}

	public TemplateManagerService getTemplateManagerService()
	{
		return _templateManagerService;
	}

	public void setTemplateManagerService(TemplateManagerService templateManagerService)
	{
		_templateManagerService = templateManagerService;
	}
	public DynamicPageService getDynamicPageService()
	{
		return _dynamicPageService;
	}

	public void setDynamicPageService(DynamicPageService dynamicPageService)
	{
		_dynamicPageService = dynamicPageService;
	}
	public Map<Integer, TemplateParser> getTemplateParserMap()
	{
		return _templateParserMap;
	}

	public void setTemplateParserMap(Map<Integer, TemplateParser> templateParserMap)
	{
		_templateParserMap = templateParserMap;
	}

	protected PageWriter getPageWriter()
	{
		return (PageWriter)_applicationContext.getBean(getPageWriterBeanName());
	}

	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		return TokenUtils.hasAttribute(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute, "TemplateInclude");
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token)
	{
		return !TokenUtils.hasAttribute(getComponentStack().peek(), getTemplate(), "action", "reference");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		
		if (TokenUtils.hasAttribute(startElement, getTemplate(), "action", "reference"))
		{			
			String templateid = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "template");
			String templateName = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "templateName");
			
			try
			{
				//_templateManagerService = (TemplateManagerService)ServiceLocator.getBean("templateManagerService");
				Template template=null;
				 if(!StringUtil.isEmpty(templateid))
				  template =_templateManagerService.getTemplateObjectByTemplateId(new Long(templateid));//templateid
				//若模板id不存在，则按templateName获取
                  if(template==null && !StringUtil.isEmpty(templateName)){
                	  //_templateManagerService=(TemplateManagerService)_applicationContext.getBean("templateManagerService");
                	  template =_templateManagerService.getNewTemplateByName(templateName);
                	  
                  }
				if(template!=null){
				  
				File includeFile=getTemplateService().getTemplateFile(template.getSite_id(), template.getName()+suffix_name);
				getTemplateService().updateIncludeTempate(template, includeFile);
							//String path =includeFile.getPath();				
				String path = String.format(Constants.TEMPLATE_STATICHTML_PATH+"%d/%s"+getTemplateService().suffix_name+".jsp", template.getSite_id(), template.getName());
				getMarkupWriter().writeRaw(" <% com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(\""+path+"\",request.getRealPath(\""+path+"\"));%>\n");
				getMarkupWriter().writeRaw("<%@ include " + "file=\"" + path + "\" %>");
				}
				//java.io.File includeFile=getTemplateService().getTemplateFile(template.getSite_id(), template.getName()+suffix_name);
				
				//String templateBody = template.getBody();
				
				/*Matcher matcher = REGEX_BODY.matcher(templateBody);
				if (matcher.find())
				{
				templateBody = matcher.group(1);
				}*/
				/*if (!newFile.exists())
				{
					if (!newFile.getParentFile().exists())
					{
						newFile.getParentFile().mkdirs();
					}
				}					 
				outputStream = new ByteArrayOutputStream ();
				
				getPageWriter().write(getTemplateParserMap().get(template.getType()).parseTemplate(templateBody), outputStream);
				getDynamicPageService().getPage(outputStream.toString(), DynamicPageService.DEFAULT_PAGE_ENCODING, newFile);
				*/

			
//				getMarkupWriter().writeRaw(outputStream.toString());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				//throw new TemplateException ("引用模板已经被删除！");
			}
			
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
}
