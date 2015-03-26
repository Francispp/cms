package com.cyberway.cms.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.cyberway.cms.tags.components.List;
import com.opensymphony.xwork2.util.ValueStack;


public class ListTag extends ComponentTagSupport
{
	protected String status="item";
	protected String where;
	protected String order;
	protected String channelid;
	protected String pageIndex = "1";
	protected String pageSize = "10";
	
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) 
	{
        return new List (stack);
    }

    protected void populateParams() 
    {
    	super.populateParams();
    	
    	List tag = (List) getComponent();
        tag.setPageIndex(pageIndex);
        tag.setPageSize(pageSize);
        tag.setStatus(status);
        tag.setWhere(where);
        tag.setOrder(order);
        tag.setChannelid(channelid);
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

	public void setPageIndex(String pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	public void setPageSize(String pageSize)
	{
		this.pageSize = pageSize;
	}

	public void setWhere(String where)
	{
		this.where = where;
	}

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
    
    public int doEndTag() throws JspException 
	{
        component = null;
        return EVAL_PAGE;
    }

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
}
