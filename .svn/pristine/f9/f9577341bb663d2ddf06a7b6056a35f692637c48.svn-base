package com.cyberway.cms.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.cyberway.cms.tags.components.TablePager;
import com.opensymphony.xwork2.util.ValueStack;

public class TablePagerTag extends ComponentTagSupport
{
	private String _pageIndex;
	private String _pageSize;
	private String _recordCount;
	private String _style;
	private String _pageType;
	
	public void setPageIndex(String pageIndex)
	{
		_pageIndex = pageIndex;
	}
	
	public void setPageSize(String pageSize)
	{
		_pageSize = pageSize;
	}
	
	public void setRecordCount(String recordCount)
	{
		_recordCount = recordCount;
	}
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) 
	{
        return new TablePager (stack);
    }
	
	@Override
	protected void populateParams() 
    {
    	super.populateParams();
    	 
        TablePager component = (TablePager)getComponent();
        
        component.setRecordCount((Integer)findValue(_recordCount, Integer.class));
        
        if (!StringUtils.isBlank(_pageSize))
        { 
        	component.setPageSize((Integer)findValue(_pageSize, Integer.class));
        }
        if (!StringUtils.isBlank(_pageIndex)) 
        {
        	component.setPageIndex((Integer)findValue(_pageIndex, Integer.class));
        }
        component.setStyle(_style);
        component.setPageType(_pageType);
    }
	
	@Override
	public int doStartTag() throws JspException
	{
		return super.doStartTag();
	}

	public void setStyle(String style) {
		this._style = style;
	}

	public String getPageType() {
		return _pageType;
	}

	public void setPageType(String pageType) {
		this._pageType = pageType;
	}
	
}
