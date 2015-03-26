package com.cyberway.cms.tags.components;

import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.IteratorStatus;

import com.cyberway.common.lucene.SearchService;
import com.cyberway.core.collections.PagedCollection;
import com.cyberway.core.collections.PagedCollectionImpl;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.util.ValueStack;

public class DocumentLoop extends Component
{
	protected Iterator iterator;
	protected IteratorStatus status;
	protected Object oldStatus;
	protected IteratorStatus.StatusState statusState;
	protected String statusAttr;
	protected String where;
	protected int pageIndex = 0;
	protected int pageSize = -1;
	protected String order;
	protected boolean ascending = true;
	protected String service="documentSearchService";

	public DocumentLoop(ValueStack stack)
	{
		super(stack);
	}

	public SearchService getSearchService ()
	{
		return (SearchService)ServiceLocator.getBean(service);
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
		this.pageIndex = Integer.valueOf(pageIndex) - 1;
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
		
		if (iterator == null)
		{
			PagedCollection<Document> result = null;
			if (StringUtils.isEmpty(where))
			{
				result = new PagedCollectionImpl<Document> (getSearchService().searchAll()); 
			}
			else
			{
				result = new PagedCollectionImpl<Document> (getSearchService().search(where));
			}
			
			if (pageSize < 0)
			{
				result.setPageSize(result.getVirtualCount());
			}
			else
			{
				result.setPageSize(pageSize);
			}
			
			result.setCurrentPage(pageIndex);
			
			iterator = result.iterator();
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
}
