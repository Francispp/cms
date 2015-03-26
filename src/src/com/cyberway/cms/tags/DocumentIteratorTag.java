package com.cyberway.cms.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.tags.components.DocumentIterator;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.util.ValueStack;


public class DocumentIteratorTag extends ComponentTagSupport
{
	private String _channel;
	private String _orderBy;
	private String _ascending = Boolean.TRUE.toString();
	private String _where;
	private String _pageIndex;
	private String _pageSize;
	private String _status="item";
	
	public void setChannel(String channel)
	{
		_channel = channel;
	}
	
	public void setOrderBy(String orderBy)
	{
		_orderBy = orderBy;
	}
	
	public void setAscending(String ascending)
	{
		_ascending = ascending;
	}
	
	public void setPageIndex(String pageIndex)
	{
		_pageIndex = pageIndex;
	}
	
	public void setPageSize(String pageSize)
	{
		_pageSize = pageSize;
	}
	
	public void setStatus(String status)
	{
		_status = status;
	}
	
	public void setWhere(String where)
	{
		_where = where;
	}
	
	public ChannelManagerService getChannelManagerService ()
	{
		return (ChannelManagerService)ServiceLocator.getBean("channelManagerService");
	}
	
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) 
	{
        return new DocumentIterator (stack);
    }
	
	protected void populateParams() 
    {
    	super.populateParams();
    	
        DocumentIterator component = (DocumentIterator)getComponent();
        
        if (StringUtils.isNumeric(_pageIndex))
        {
        	component.setPageIndex(Integer.valueOf(_pageIndex));
        }
        if (StringUtils.isNumeric(_pageSize))
        {
        	component.setPageSize(Integer.valueOf(_pageSize));
        }
        if (!StringUtils.isBlank(_status))
        {
        	component.setStatus(_status);
        }
        if (!StringUtils.isBlank(_where))
        {
        	component.setWhere(_where);
        }
        if (!StringUtils.isBlank(_orderBy))
        {
        	component.setOrderBy(_orderBy);
        }
        if (StringUtils.isNumeric(_channel))
        {
        	component.setChannel(getChannelManagerService().get(Long.valueOf(_channel)));
        }
        
        component.setAscending(!StringUtils.equalsIgnoreCase(_ascending, Boolean.FALSE.toString()));
    }
	
	@Override
	public int doAfterBody() throws JspException 
    {
        boolean again = component.end(pageContext.getOut(), getBody());

        if (again) 
        {
            return EVAL_BODY_AGAIN;
        }
        else 
        {
            if (bodyContent != null) 
            {
                try 
                {
                    bodyContent.writeOut(bodyContent.getEnclosingWriter());
                }
                catch (Exception e) 
                {
                    throw new JspException(e.getMessage());
                }
            }
            
            return SKIP_BODY;
        }
    }
    
    @Override
	public int doEndTag() throws JspException 
	{
        component = null;
        
        return EVAL_PAGE;
    }

}
