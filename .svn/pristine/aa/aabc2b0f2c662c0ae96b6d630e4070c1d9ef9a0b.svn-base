package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * 流程功能标签的过滤器
 *
 */
public class FlowWriter extends ComponentWriter
{
	public FlowWriter()
	{
		super ("Flow", "");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token) 
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement) 
	{		
		
		String functionType = TokenUtils.getAttributeValue(startElement, getTemplate(), "functionType");
		String custom = TokenUtils.getAttributeValue(startElement, getTemplate(), "custom");
		if(custom!=null)
			 custom=StringEscapeUtils.unescapeHtml(custom);
		//writeRaw 原样输出
		if(!StringUtil.isEmpty(functionType)){
			if(functionType.equals("activityName")){
				getMarkupWriter().writeRaw(custom);
			}else if(functionType.equals("compleButton")){
				getMarkupWriter().writeRaw(custom);
			}else if(functionType.equals("flowButton")){
				getMarkupWriter().writeRaw(custom);
			}else if(functionType.equals("flowLog")){
				getMarkupWriter().writeRaw(custom);
			}else if(functionType.equals("other")){
				getMarkupWriter().writeRaw(custom);
			}
		}
		
		
	}
	
	@Override
	protected void writeComponentEnd() 
	{		
		//super.writeComponentEnd();			
	}
}
