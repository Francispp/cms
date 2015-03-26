package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class PhotoWriter extends ComponentWriter{
	public PhotoWriter()
	{
		super("Photo","div");
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
		String title = TokenUtils.getAttributeValue(startElement, getTemplate(), "title");
		String imgWidth = TokenUtils.getAttributeValue(startElement, getTemplate(), "imgWidth");
		String imgHeight = TokenUtils.getAttributeValue(startElement, getTemplate(), "imgHeight");
		String optionFileTypeList = TokenUtils.getAttributeValue(startElement, getTemplate(), "optionFileTypeList");
		String fileSizeField = TokenUtils.getAttributeValue(startElement, getTemplate(), "fileSizeField");
        if(StringUtils.isEmpty(imgWidth))
        {
        	imgWidth = "";
        }
        if(StringUtils.isEmpty(imgHeight))
        {
        	imgHeight = "";
        }
		
        if(fileSizeField == null && StringUtils.isEmpty(fileSizeField))
		{
			fileSizeField = "";
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
		StringBuffer script=new StringBuffer();
		String divid = getComponentIdStack().peek();
		
		if(!name.startsWith("domain.")){
			name = "domain."+name;
		}
		
		if("sr_up_pic".equals(style)){
			getMarkupWriter().element("p","type","text","class","sr_up_pic","id","div"+divid);
			getMarkupWriter().writeRaw("<img alt=\"\" src=\"/${("+name+"==null || "+name+"=='') ? 'images/none_pic.jpg' : "+name+" }\" complete=\"complete\"/>");
			getMarkupWriter().end ();
			sb.append("<input type=\"button\" class=\"sr_up_btn\" value=\"浏览...\" onclick=\"uploadPic('"+divid+"','','${domain.id}','${domain.site.oid}','"+imgWidth+"','"+imgHeight+"','"+title+"','"+optionFileTypeList+"','"+fileSizeField+"','${domain.channel.id}');\"/>\n");
			sb.append("<input type=\"button\" class=\"sr_up_btn\" value=\"上传\" onclick=\"uploadPic('"+divid+"','','${domain.id}','${domain.site.oid}','"+imgWidth+"','"+imgHeight+"','"+title+"','"+optionFileTypeList+"','"+fileSizeField+"','${domain.channel.id}');\"/>\n");
			getMarkupWriter().writeRaw(sb.toString());
			getMarkupWriter().element("div","style","diaplay:none;");
		}else if("mr_up_pic".equals(style)){
			getMarkupWriter().element("p","type","text","class","mr_up_pic","id","div"+divid);
			getMarkupWriter().writeRaw("<img alt=\"\" src=\"/${("+name+"==null || "+name+"=='') ? 'images/none_pic.jpg' : "+name+" }\" complete=\"complete\"/>");
			getMarkupWriter().end ();
			sb.append("<input type=\"button\" class=\"sr_up_btn\" value=\"上传照片\" onclick=\"uploadPic('"+divid+"','','${domain.id}','${domain.site.oid}','"+imgWidth+"','"+imgHeight+"','"+title+"','"+optionFileTypeList+"','"+fileSizeField+"','${domain.channel.id}');\"/>\n");
			getMarkupWriter().writeRaw(sb.toString());
			getMarkupWriter().element("div","style","diaplay:none;");
		}else{
			getMarkupWriter().element("div","class",style);
			sb.append("<button class = \"but_img\" onclick=\"uploadPic('"+divid+"','','${domain.id}','${domain.site.oid}','"+imgWidth+"','"+imgHeight+"','"+title+"','"+optionFileTypeList+"','"+fileSizeField+"','${domain.channel.id}');\">上传图片</button>\n");
			getMarkupWriter().writeRaw(sb.toString());
			getMarkupWriter().element("div", "id", "div"+divid);
			getMarkupWriter().end ();
		}
		getMarkupWriter().element("ww:hidden", "id", divid,"name",name);
		getMarkupWriter().end();
		script.append("<script language='javascript'>showPic('"+divid+"','"+imgWidth+"','"+imgHeight+"','"+title+"','"+optionFileTypeList+"','"+fileSizeField+"');</script>");
		getMarkupWriter().writeRaw(script.toString());
	}
	
}
