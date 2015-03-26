package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class MediaUploadWriter extends ComponentWriter{
	public MediaUploadWriter()
	{
		super("MediaUpload","div");
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
		String issueRadioField = TokenUtils.getAttributeValue(startElement, getTemplate(), "_issueRadioField");
		String mediaType = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaType");
		
		String uploadType = TokenUtils.getAttributeValue(startElement, getTemplate(), "_uploadType");
		
		String mediaWidth = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaWidth");
		String mediaHeight = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaHeight");
		
		String mediaSize = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaSize");
		
        if(StringUtils.isEmpty(mediaWidth))
        {
        	mediaWidth = "";
        }
        if(StringUtils.isEmpty(mediaHeight))
        {
        	mediaHeight = "";
        }
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		getComponentIdStack().push(id);
		StringBuffer sb=new StringBuffer();
		StringBuffer script=new StringBuffer();
		String divid = getComponentIdStack().peek();
		
		getMarkupWriter().element("div","class",style);//路径、文档ID、类型、DIV显示名称、后台存储名称、大小、分发、宽、高、站点ID
		if(mediaType!=null&&StringUtils.equals("video", mediaType)){
			sb.append("<button class = \"but_img\" onclick=\"t_uploadVideo('','${domain.id}','"+uploadType+"','"+divid+"','"+name+"','"+mediaSize+"','"+issueRadioField+"','"+mediaWidth+"','"+mediaHeight+"','"+mediaType+"','${siteId}');\">上传视频</button>\n");
		}else if(mediaType!=null&&StringUtils.equals("audio", mediaType)){
			sb.append("<button class = \"but_img\" onclick=\"t_uploadAudio('','${domain.id}','"+uploadType+"','"+divid+"','"+name+"','"+mediaSize+"','"+issueRadioField+"','"+mediaWidth+"','"+mediaHeight+"','"+mediaType+"','${siteId}');\">上传音频</button>\n");
		}
		getMarkupWriter().writeRaw(sb.toString());
		getMarkupWriter().element(getElementName(), "id", "div"+divid);
		getMarkupWriter().end ();
		script.append("<script language='javascript'>t_showList('${domain.id}','"+divid+"','"+name+"','"+mediaWidth+"','"+mediaHeight+"','"+mediaType+"');</script>");		
		getMarkupWriter().writeRaw(script.toString());

		
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
	}


}
