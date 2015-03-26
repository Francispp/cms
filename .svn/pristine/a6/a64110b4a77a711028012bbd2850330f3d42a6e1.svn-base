package com.cyberway.common.collections;

import java.util.Iterator;

import org.apache.commons.collections.IteratorUtils;

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
    	return getPageCount()  > getFirstPage() ? getPageCount()  : getFirstPage();
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
    		return 1;
    	}
    	else
    	{
    		return getPageSize () >= getVirtualCount () ? 1 : (getVirtualCount () % getPageSize ()) > 0 ? (getVirtualCount() / getPageSize () + 1) : (getVirtualCount() / getPageSize());
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
            currentPage = getPageCount() > 0 ? getPageCount()  : 1;
        }

        _currentPage = currentPage;
    }

    public int pageIndexOf (Object value)
    {
    	for (int pageIndex = 0; pageIndex < getPageCount(); pageIndex++)
    	{
    		if (IteratorUtils.toList(getData(pageIndex, getPageSize()).iterator()).contains(value))
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

