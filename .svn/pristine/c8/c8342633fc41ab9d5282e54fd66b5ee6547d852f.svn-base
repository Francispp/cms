package com.cyberway.cms.tags.components;

import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.IteratorStatus;

import com.cyberway.cms.document.service.DocumentManagerService;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.util.ValueStack;


public class List extends Component
{
	protected Iterator iterator;
	protected IteratorStatus status;
	protected Object oldStatus;
	protected IteratorStatus.StatusState statusState;
	protected String statusAttr;
	protected String where;
	protected Long channelid;
	protected int pageIndex = 1;
	protected int pageSize = 10;
	protected String order;
	protected boolean ascending = true;
	protected String service="documentManagerService";

	public List(ValueStack stack)
	{
		super(stack);
	}

	public DocumentManagerService getDocumentService ()
	{
		return (DocumentManagerService)ServiceLocator.getBean(service);
	}
	
	public void setService(String service)
	{
		this.service = service;
	}

	public void setStatus(String status)
	{
		this.statusAttr = status;
	}

	public void setWhere(String where)
	{
		this.where = where;
	}
	
	public void setPageIndex(String pageIndex)
	{
		this.pageIndex = Integer.valueOf(pageIndex);
	}
	
	public void setPageSize(String pageSize)
	{
		this.pageSize = Integer.valueOf(pageSize);
	}
	
	public void setOrder(String order)
	{
		this.order = order;
	}
	
	public void setAscending(boolean ascending)
	{
		this.ascending = ascending;
	}

	@Override
	public boolean start(Writer writer)
	{
		if (statusAttr != null)
		{
			statusState = new IteratorStatus.StatusState();
			status = new IteratorStatus(statusState);
		}

		ValueStack stack = getStack();
		StringBuffer  hql=new StringBuffer(" from Document where issued=5 and ");
		if (iterator == null)
		{
			Page result = null;
			Object[] objs=null;
			if(channelid!=null){
				objs=new Object[]{channelid};
				hql.append("channel.id=? ");
			}				
			if (!StringUtils.isEmpty(where)){	
				hql.append(where);
			}else if(channelid==null)
				hql.append(" 1=1");
			if(!StringUtils.isEmpty(order))
				hql.append(" order by "+order);
			result = this.getDocumentService().pagedQuery(hql.toString(), pageIndex, pageSize, objs); 
			
			iterator = ((java.util.List)result.getData()).iterator();
			stack.getContext().put("LPageIndex", pageIndex);
			stack.getContext().put("LPageSize", pageSize);
			stack.getContext().put("LTotalRows", result.getTotalCount());
		}

		if (iterator.hasNext())
		{
			Object currentValue = iterator.next();
			stack.push(currentValue);

			String id = (String)getParameters().get("id");

			if ((id != null) && (currentValue != null))
			{
				stack.getContext().put(id, currentValue);
			}

			if (statusAttr != null)
			{
				statusState.setLast(!iterator.hasNext());
				oldStatus = stack.getContext().get(statusAttr);
				stack.getContext().put(statusAttr, status);
			}

			return true;
		}
		else
		{
			super.end(writer, "");
			return false;
		}
	}

	@Override
	public boolean end(Writer writer, String body)
	{
		ValueStack stack = getStack();
		if (iterator != null)
		{
			stack.pop();
		}

		if (iterator != null && iterator.hasNext())
		{
			Object currentValue = iterator.next();
			stack.push(currentValue);

			String id = (String)getParameters().get("id");

			if ((id != null) && (currentValue != null))
			{
				stack.getContext().put(id, currentValue);
			}

			// Update status
			if (status != null)
			{
				statusState.next(); // Increase counter
				statusState.setLast(!iterator.hasNext());
			}

			return true;
		}
		else
		{
			if (status != null)
			{
				if (oldStatus == null)
				{
					stack.getContext().put(statusAttr, null);
				}
				else
				{
					stack.getContext().put(statusAttr, oldStatus);
				}
			}
			super.end(writer, "");
			return false;
		}
	}

	public Long getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		if(StringUtils.isNumeric(channelid))
		  this. channelid = new Long(channelid);
	}
}
