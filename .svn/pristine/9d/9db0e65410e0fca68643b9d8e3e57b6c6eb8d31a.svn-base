package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class FileWriter extends ComponentWriter{
	public FileWriter(){
		super("","file");
	}
	
	@Override
	protected boolean isComponent(StartElementToken startElement) {
		if(getElementName().equalsIgnoreCase(startElement.getName())){
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token){
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement){
		StringBuilder config = new StringBuilder("{");
		String channelId = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "channelId");
		if(StringUtils.isBlank(channelId)){
			channelId="${channelId}";
		}
		String docId = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "docId");
		if(StringUtils.isBlank(docId)){
			docId="${id}";
		}
		String fieldName = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "name");
		String fileType = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "fileType");
		if(StringUtils.isBlank(fileType)){
			fileType="file";
		}
		String cssClass = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "cssClass");
		String showImage = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "showImage");
		if(StringUtils.isNotBlank(showImage)){
			if(config.length()>1){
				config.append(",");
			}
			config.append("\"showImage\":");
			config.append(showImage);
		}
		String imageHeight = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "imageHeight");
		if(StringUtils.isNotBlank(imageHeight)){
			if(config.length()>1){
				config.append(",");
			}
			config.append("\"imageHeight\":\"");
			config.append(imageHeight);
			config.append("\"");
		}
		String imageWidth = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "imageWidth");
		if(StringUtils.isNotBlank(imageWidth)){
			if(config.length()>1){
				config.append(",");
			}
			config.append("\"imageWidth\":\"");
			config.append(imageWidth);
			config.append("\"");
		}
		config.append("}");
		String id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		getComponentIdStack().push(id);
		if(StringUtils.isNotBlank(cssClass)){
			getMarkupWriter().element("div", "id", id, "class", cssClass);
		} else {
			getMarkupWriter().element("div", "id", id);
		}
		getMarkupWriter().end();
		getMarkupWriter().writeRaw("<script src=\"${ctx}/common/file/file.js\" type=\"text/javascript\"></script>");
		getMarkupWriter().element("script", "type", "text/javascript");
		getMarkupWriter().writeRaw("var fm_"+id+" = new FileManager('"+id+"','"+channelId+"','"+docId+"','"+fieldName+"','"+fileType+"',"+config.toString()+");");
	}
	
	@Override
	protected void writeComponentEnd(){
		getMarkupWriter().end();
	}

}
