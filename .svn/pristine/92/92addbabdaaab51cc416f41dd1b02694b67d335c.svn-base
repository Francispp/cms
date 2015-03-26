package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class IndexListItemsWriter extends ComponentWriter{
	public IndexListItemsWriter(){
		super("","indexlistitems");
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
		if("status".equals(token.getName())){
			return true;
		}
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement){
		String id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		getComponentIdStack().push(id);
		String status = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(status)){
			getMarkupWriter().element("ww:iterator", "value", "result");
		} else {
			getMarkupWriter().element("ww:iterator", "value", "result", "status", status);
		}
	}
	
	@Override
	protected void writeComponentEnd(){
		getMarkupWriter().end();
	}

}
