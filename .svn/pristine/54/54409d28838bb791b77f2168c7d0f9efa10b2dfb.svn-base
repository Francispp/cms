package com.cyberway.common.message.utils;

import org.ecside.table.limit.FilterSet;
import org.ecside.table.limit.Sort;

public class Limit implements org.ecside.table.limit.Limit {

	private Integer _pageIndex;
	private Integer _pageSize;
	private Integer _recordCount;
	private String _orderBy;
	private boolean _ascending = true;
	
	public Limit(Integer pageIndex, Integer pageSize)
	{
		_pageIndex = pageIndex;
		_pageSize = pageSize;
	}
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
		return new Sort (_orderBy, _orderBy, _ascending ? "desc" : "asc");
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
