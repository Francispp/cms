package com.cyberway.cms.internal.template.token;


import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class UploadWriter extends ComponentWriter{
	public UploadWriter()
	{
		super("Upload","div");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String optionFileTypeList = TokenUtils.getAttributeValue(startElement, getTemplate(), "optionFileTypeList");
		String fileSizeField = TokenUtils.getAttributeValue(startElement, getTemplate(), "fileSizeField");
		if(fileSizeField == null && StringUtils.isEmpty(fileSizeField))
		{
			fileSizeField = "";
		}
		if(name == null)
		{
			name = "";
		}
		if(optionFileTypeList == null)
		{
			optionFileTypeList = "";
		}
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		StringBuffer sb=new StringBuffer();
		String divid = getComponentIdStack().peek();
		
		
		getMarkupWriter().element("div","class",style);
		//sb.append("<script src='${ctx}/common/attachment/attachment.js' type='text/javascript'></script>");
		//sb.append("<script src='${ctx}/common/attachment/xml.js' type='text/javascript'></script>");
		sb.append(" <s:if test='isEdit'><div class = \"but_img\" onclick=\"newAttachment(window."+divid+",'','','${domain.id}','${domain.site.oid}','"+optionFileTypeList+"','"+name+"','"+fileSizeField+"');\"></div></s:if>\n");
		getMarkupWriter().writeRaw(sb.toString());
		getMarkupWriter().element(getElementName(), "id", divid);
		
		getMarkupWriter().end ();
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
		String divid = getComponentIdStack().peek();
		String name = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		StringBuffer sb=new StringBuffer();
		sb.append(" <s:if test='isEdit'>");
		sb.append(" viewAttachment(window."+divid+",'','${domain.id}','"+name+"');\n");
		sb.append("</s:if><s:else>");
		sb.append(" viewAttachmentForDetailsTemplate(window."+divid+",'','${domain.id}','"+name+"');\n");
		sb.append("</s:else>");
		
		getMarkupWriter().element("script", "type", "text/javascript");
		getComponentIdStack().peek();
		getMarkupWriter().writeRaw(sb.toString());
		getMarkupWriter().end ();
	}

}
