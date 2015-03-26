package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.Template;
import com.cyberway.cms.template.token.StartElementToken;

public class HeadWriter extends AbstractComponentWriter
{
	@Override
	protected boolean isComponent(StartElementToken startElement)
	{
		return StringUtils.equalsIgnoreCase(startElement.getName(), "head");
	}
	
	@Override
	protected void writeComponentEnd()
	{
		/*if (ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || ObjectUtils.equals(getTemplate().getType(), Template.TYPE_ADMIN_SUMMARY))
		{
			getMarkupWriter().writeRaw("<%@ include file=\"/common/template/head.inc\"%>");
		}else
			getMarkupWriter().writeRaw("<%@ include file=\"/common/template/util.inc\"%>");*/
		if(!getTemplate().getIsWap()){
			getMarkupWriter().writeRaw("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=8\" />");
		}
		super.writeComponentEnd();
	}
	
	/**
	 * 加载头部文件
	 */
	protected void writePopedomStart (StartElementToken startElement)
	{
		if (ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || ObjectUtils.equals(getTemplate().getType(), Template.TYPE_ADMIN_SUMMARY))
		{
			getMarkupWriter().writeRaw("<%@ include file=\"/common/template/head.inc\"%>");
		}else{
			if(!getTemplate().getIsWap()){
				getMarkupWriter().writeRaw("<%@ include file=\"/common/template/util.inc\"%>");
			}else{
				//类型错误
				getMarkupWriter().writeRaw("<%@ page contentType=\"text/html; charset=UTF-8\"%>");
				//getMarkupWriter().writeRaw("<%@ page contentType=\"application/xhtml+xml; charset=UTF-8\"%>");
			}
			
		}
			
		super.writePopedomStart(startElement);
	}
}
