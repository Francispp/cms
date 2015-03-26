package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.Template;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

/**
 * @author caiw
 * HTML控件的过滤器
 *
 */
public class HtmlEditWriter extends ComponentWriter
{
	public HtmlEditWriter()
	{
		super ("Html", "ww:textarea");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token) 
	{
		return false;
	}
	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		if (StringUtils.equalsIgnoreCase(attribute.getName(), "_name"))
		{
			if ((ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || 
				ObjectUtils.equals(getTemplate().getType(), Template.TYPE_DETAILS)) &&
				!ObjectUtils.toString(attribute.getValue()).startsWith("domain."))
			{
				getMarkupWriter().attributes("name", "domain." + attribute.getValue());
			}
			else
			{
				getMarkupWriter().attributes("name", attribute.getValue());
			}
		}
		else
		{
			super.writeAttribute(attribute);
		}
	}	
	@Override
	protected void writeComponentStart(StartElementToken startElement) 
	{
		String name=TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String checkField="max-length-"+TokenUtils.getAttributeValue(startElement, getTemplate(), "checkField");
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		getComponentIdStack().push(id);
		getMarkupWriter().element(getElementName(), "id", getComponentIdStack().peek());
		if ((ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || 
				ObjectUtils.equals(getTemplate().getType(), Template.TYPE_DETAILS)) &&
				!ObjectUtils.toString(name).startsWith("domain."))
			{
				getMarkupWriter().attributes("name", "domain." + name,"cssClass",checkField);
			}
			else
			{
				getMarkupWriter().attributes("name", name,"cssClass",checkField);
			}

		//super.writeComponentStart(startElement);
	}
	
	@Override
	protected void writeComponentEnd() 
	{
		//获得名称
		String name=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		String templateUrl=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "templateUrl");
		String channelIdAttrName = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channelIdAttrName");
		if (StringUtils.isBlank(channelIdAttrName)){
			channelIdAttrName = "channelId";
		}
		String docIdAttrName = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "docIdAttrName");
		if (StringUtils.isBlank(docIdAttrName)){
			docIdAttrName = "id";
		}
		String uploadAction = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "uploadAction");
		if (StringUtils.isBlank(uploadAction)){
			uploadAction = "uploadFile!uploadCK.action";
		}

		String htmlEdit=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "htmlEdit");
		if(htmlEdit==null||StringUtils.isEmpty(htmlEdit)){
			htmlEdit="CmsEdit";
		}
		if ((ObjectUtils.equals(getTemplate().getType(), Template.TYPE_FORM) || 
				ObjectUtils.equals(getTemplate().getType(), Template.TYPE_DETAILS)) &&
				!ObjectUtils.toString(name).startsWith("domain."))
			{
				name= "domain." + name;
			}
			
		super.writeComponentEnd();

		//String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "type");
		StringBuffer sb=new StringBuffer();
		if (!StringUtils.isEmpty(templateUrl))//若无模板，则创建新的
		{		
			 sb.append("  <c:if test=\"${isEdit and (empty "+name+")}\">\n");//若不为新增
			// sb.append("<SCRIPT LANGUAGE=javascript>\n");
			 sb.append("   $(\""+name+"\").value=getHtmlContent(\"<%=BASEURL%>/"+templateUrl+"\");\n");
			 //sb.append(" </SCRIPT>\n");
			 sb.append(" </c:if>\n");
		}
		/*
		sb.append(" var fckEditor = new FCKeditor(\""+name+"\", \"100%\", \"400px\", \""+htmlEdit+"\");\n");
		sb.append(" fckEditor.BasePath = \"/common/fckeditor/\";\n");
		sb.append(" fckEditor.Config[\"FullPage\"]=false;\n");
		sb.append(" fckEditor.ReplaceTextarea();\n");
		*/
		
		getMarkupWriter().element("script", "type", "text/javascript");
		String id = getComponentIdStack().peek();
		getMarkupWriter().writeRaw(sb.toString());
		getMarkupWriter().end();
		StringBuilder ckConfig = new StringBuilder();
		ckConfig.append("com.ckeditor.CKEditorConfig ckConfig_"+id+" = new com.ckeditor.CKEditorConfig();");
		ckConfig.append("ckConfig_"+id+".addConfigValue(\"filebrowserImageUploadUrl\", pageContext.getAttribute(\"ctx\")+\"");
		ckConfig.append(uploadAction);
		if(uploadAction.indexOf("?")!=-1){
			ckConfig.append("&");
		} else {
			ckConfig.append("?");
		}
		ckConfig.append("file.fileType=image&file.docId=\"+request.getAttribute(\"");
		ckConfig.append(docIdAttrName);
		ckConfig.append("\")+\"&file.channelId=\"+request.getAttribute(\"");
		ckConfig.append(channelIdAttrName);
		ckConfig.append("\")+\"&file.fieldName=");
		ckConfig.append(name);
		ckConfig.append("\");");
		ckConfig.append("ckConfig_"+id+".addConfigValue(\"filebrowserFlashUploadUrl\", pageContext.getAttribute(\"ctx\")+\"");
		ckConfig.append(uploadAction);
		if(uploadAction.indexOf("?")!=-1){
			ckConfig.append("&");
		} else {
			ckConfig.append("?");
		}
		ckConfig.append("file.fileType=flash&file.docId=\"+request.getAttribute(\"");
		ckConfig.append(docIdAttrName);
		ckConfig.append("\")+\"&file.channelId=\"+request.getAttribute(\"");
		ckConfig.append(channelIdAttrName);
		ckConfig.append("\")+\"&file.fieldName=");
		ckConfig.append(name);
		ckConfig.append("\");");
		getMarkupWriter().writeRaw("<%" + ckConfig.toString() + "%>");
		getMarkupWriter().element("ckeditor:replace", "replace", name, "basePath", "/common/ckeditor/", "config", "<%=ckConfig_"+id+"%>");
		getMarkupWriter().end ();
	}
}
