package com.cyberway.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;

public class PagedCollectionImpl<T> extends AbstractPagedCollection<T>
{
	private Collection _source;
	
	public PagedCollectionImpl (Collection<T> source) 
	{ 
		Validate.notNull(source);
 
		_source = source;
	}

	@Override
	public int getVirtualCount()
	{
		return _source.size();
	}
	
	@Override
	protected Iterable<T> getData(int pageIndex, int pageSize)
	{
		int fromIndex = pageIndex * pageSize;
        int toIndex = fromIndex + pageSize;
        List<T> list = new ArrayList<T> ();

        if ((getVirtualCount () - (pageIndex * pageSize)) < pageSize)
        {
            toIndex = getVirtualCount (); 
        }

        CollectionUtils.addAll(list, _source.iterator());
        
        return list.subList(fromIndex, toIndex);
	}
}