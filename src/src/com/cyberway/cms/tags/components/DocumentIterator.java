package com.cyberway.cms.tags.components;

import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.IteratorStatus;
import org.ecside.table.limit.FilterSet;
import org.ecside.table.limit.Sort;

import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.util.ValueStack;

public class DocumentIterator extends Component
{
	public static class Limit implements org.ecside.table.limit.Limit
	{
		private Integer _pageIndex;
		private Integer _pageSize;
		private Integer _recordCount;
		private String _orderBy;
		private boolean _ascending = true;
		
		public Limit(Integer pageIndex, Integer pageSize, Integer recordCount, String orderBy, boolean ascending)
		{
			_pageIndex = pageIndex;
			_pageSize = pageSize;
			_recordCount = recordCount;
			_orderBy = orderBy;
			_ascending = ascending;
		}

		public int getCurrentRowsDisplayed()
		{
			return _pageSize;
		}

		public FilterSet getFilterSet()
		{
			return null;
		}

		public int getPage()
		{
			return _pageIndex;
		}

		public int getRowEnd()
		{
			return 0;
		}

		public int getRowStart()
		{
			return 0;
		}

		public Sort getSort()
		{
			return new Sort (_orderBy, _orderBy, _ascending ? "asc" : "desc");
		}

		public int getTotalRows()
		{
			return _recordCount;
		}

		public boolean isCleared()
		{
			return false;
		}

		public boolean isExported()
		{
			return false;
		}

		public boolean isFiltered()
		{
			return false;
		}

		public boolean isSorted()
		{
			return false;
		}

		public void setRowAttributes(int i, int j)
		{
		}
	}

	
	private Channel _channel;
	private String _orderBy;
	private boolean _ascending = true;
	private String _where;
	private int _pageIndex = 1;
	private int _pageSize = 15;
	private Iterator _iterator;
	private IteratorStatus _status;
	private Object _oldStatus;
	private IteratorStatus.StatusState _statusState;
	private String _statusAttr;
	
	public DocumentIterator(ValueStack stack)
	{
		super(stack);
	}
	
	public void setChannel(Channel channel)
	{
		_channel = channel;
	}

	public void setOrderBy(String orderBy)
	{
		_orderBy = orderBy;
	}

	public void setAscending(boolean ascending)
	{
		_ascending = ascending;
	}

	public void setWhere(String where)
	{
		_where = where;
	}

	public void setPageIndex(int pageIndex)
	{
		_pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize)
	{
		_pageSize = pageSize;
	}
	
	public void setStatus(String status)
	{
		_statusAttr = status;
	}

	public DocumentCommonService getDocumentCommonService ()
	{
		return (DocumentCommonService)ServiceLocator.getBean("documentCommonService");
	}
	
	@Override
	public boolean start(Writer writer)
	{
		if (_statusAttr != null)
		{
			_statusState = new IteratorStatus.StatusState();
			_status = new IteratorStatus(_statusState);
		}

		ValueStack stack = getStack();
		
		if (_iterator == null)
		{
			Page page = null;
			Limit limit = new Limit (_pageIndex, _pageSize, null, _orderBy, _ascending);
			if (StringUtils.isEmpty(_where))
			{
				try
				{
					page = getDocumentCommonService().findECPage(limit, new CriteriaSetup (), _channel, null);
				}
				catch (RuntimeException ex)
				{
					throw ex;
				}
				catch (Exception ex)
				{
					throw new RuntimeException (ex);
				}
			}
			else
			{
				try
				{
					page = getDocumentCommonService().findECPage(limit, new CriteriaSetup (), _channel, null);
				}
				catch (RuntimeException ex)
				{
					throw ex;
				}
				catch (Exception ex)
				{
					throw new RuntimeException (ex);
				}
			}
			
			_iterator = IteratorUtils.getIterator(page.getResult());
		}

		if (_iterator.hasNext())
		{
			Object currentValue = _iterator.next();
			stack.push(currentValue);

			String id = (String)getParameters().get("id");

			if ((id != null) && (currentValue != null))
			{
				stack.getContext().put(id, currentValue);
			}

			if (_statusAttr != null)
			{
				_statusState.setLast(!_iterator.hasNext());
				_oldStatus = stack.getContext().get(_statusAttr);
				stack.getContext().put(_statusAttr, _status);
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
		if (_iterator != null)
		{
			stack.pop();
		}

		if (_iterator != null && _iterator.hasNext())
		{
			Object currentValue = _iterator.next();
			stack.push(currentValue);

			String id = (String)getParameters().get("id");

			if ((id != null) && (currentValue != null))
			{
				stack.getContext().put(id, currentValue);
			}

			if (_status != null)
			{
				_statusState.next(); // Increase counter
				_statusState.setLast(!_iterator.hasNext());
			}

			return true;
		}
		else
		{
			if (_status != null)
			{
				if (_oldStatus == null)
				{
					stack.getContext().put(_statusAttr, null);
				}
				else
				{
					stack.getContext().put(_statusAttr, _oldStatus);
				}
			}
			
			super.end(writer, "");
			return false;
		}
	}
}
