package com.cyberway.cms.internal.template.token;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class IndexListPagerWriter extends ComponentWriter{
	public IndexListPagerWriter(){
		super("","indexlistpager");
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
		String id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		getComponentIdStack().push(id);
		getMarkupWriter().element("cms:tablePager", "pageIndex", "currentPageNo",
				"pageSize", "pageSize", "recordCount", "totalCount");
	}
	
	@Override
	protected void writeComponentEnd(){
		getMarkupWriter().end();
	}

}
