package com.cyberway.core.collections;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;

public abstract class AbstractPagedCollection<T> implements PagedCollection<T>
{
    private int _currentPage;
    private int _pageSize = -1;

    public abstract int getVirtualCount ();

    protected abstract Iterable<T> getData (int pageIndex, int pageSize);

    public int getFirstPage ()
    {
    	return 0;
    }

    public int getLastPage ()
    {
    	return getPageCount() - 1 > getFirstPage() ? getPageCount() - 1 : getFirstPage();
    }

    public int getNextPage ()
    {
    	return getCurrentPage() + 1 > getLastPage() ? getLastPage() : getCurrentPage() + 1;
    }

    public int getPrevPage()
    {
    	return getCurrentPage() - 1 > getFirstPage() ? getCurrentPage() - 1 : getFirstPage();
    }

    public int getCount ()
    {
    	if ((getCurrentPage() * getPageSize ()) + getPageSize () > getVirtualCount ())
    	{
    		return getPageSize () - (((getCurrentPage() * getPageSize()) + getPageSize ()) - getVirtualCount());
    	}
    	else
    	{
    		return getPageSize ();
    	}
    }

    public int getPageCount()
    {
    	if (getVirtualCount () <= 0)
    	{
    		return 0;
    	}
    	else
    	{
    		return getPageSize () >= getVirtualCount () ? 1 : (getVirtualCount () / getPageSize ()) + 1;
    	}
    }

    public int getPageSize()
    {
    	if (_pageSize == -1)
    	{
    		return getVirtualCount ();
    	}
    	else
    	{
    		return _pageSize;
    	}
    }

    public void setPageSize(int pageSize)
    {
        if (pageSize < 1)
        {
            pageSize = 1;
        }

        _pageSize = pageSize;
    }

    public int getCurrentPage()
    {
        return _currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        if (currentPage < 0)
        {
            currentPage = 0;
        }
        else if (currentPage >= getPageCount())
        {
            currentPage = getPageCount() > 0 ? getPageCount() - 1 : 0;
        }

        _currentPage = currentPage;
    }

    public int pageIndexOf (Object value)
    {
    	for (int pageIndex = 0; pageIndex < getPageCount(); pageIndex++)
    	{
    		ArrayList<T> data = new ArrayList<T> ();
    		CollectionUtils.addAll(data, getData(pageIndex, getPageSize()).iterator());

    		if (data.contains(value))
    		{
    			return pageIndex;
    		}
    	}

    	return -1;
    }

    public Iterator<T> iterator()
    {
    	return getData (getCurrentPage(), getPageSize ()).iterator ();
    }
}

