package com.cyberway.cms.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.cyberway.cms.tags.components.Property;
import com.opensymphony.xwork2.util.ValueStack;


public class PropertyTag extends ComponentTagSupport
{
    private String defaultValue;
    private String value;
    private boolean escape;
    private boolean isOffecOcx;//是否为Office控件，若为true,输现iframe
    private boolean isDate;
    private String formatType;
    
	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public boolean getIsDate() {
		return isDate;
	}

	public void setIsDate(boolean isDate) {
		this.isDate = isDate;
	}

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) 
	{		
        return new Property (stack);
    }

    protected void populateParams() 
    {    	
    	super.populateParams();
        Property tag = (Property)component;
        tag.setDefault(defaultValue);
        tag.setValue(value);
        tag.setEscape(escape);
        tag.setPageContext(pageContext);
        tag.setIsOffecOcx(isOffecOcx);
        tag.setIsDate(isDate);
        tag.setFormatType(formatType);
    }

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isEscape() {
		return escape;
	}

	public void setEscape(boolean escape) {
		this.escape = escape;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean getIsOffecOcx() {
		return isOffecOcx;
	}

	public void setIsOffecOcx(boolean isOffecOcx) {
		this.isOffecOcx = isOffecOcx;
	}

}
