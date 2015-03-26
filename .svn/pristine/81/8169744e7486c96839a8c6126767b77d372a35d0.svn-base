package com.cyberway.cms.internal.template.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;

import com.cyberway.cms.internal.template.TokenWriter;
import com.cyberway.cms.internal.template.TokenWriterFilter;
import com.cyberway.cms.internal.template.TokenWriterFilterChain;
import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.service.MarkupWriter;

public class TokenWriterImpl implements TokenWriter, TokenWriterFilterChain
{
	private Iterator<TokenWriterFilter> _write;
	private Iterator<TokenWriterFilter> _prepare;
	private Iterator<TokenWriterFilter> _end;
	private List<TokenWriterFilter> _filters = new ArrayList<TokenWriterFilter> ();
	public List<TokenWriterFilter> getFilters()
	{
		return _filters;
	}

	public void setFilters(List<TokenWriterFilter> filters)
	{
		_filters = filters;
	}
	
	public void prepare(Template template, MarkupWriter writer)
	{
		_prepare = CollectionUtils.isEmpty(getFilters()) ? IteratorUtils.EMPTY_ITERATOR : getFilters().iterator();
		
		doPrepare(template, writer);
	}

	public void write(TemplateToken token)
	{
		_write = CollectionUtils.isEmpty(getFilters()) ? IteratorUtils.EMPTY_ITERATOR : getFilters().iterator();
		
		doWrite(token);
	}
	public void write(TemplateToken token,Object object1,Object object2)
	{
		_write = CollectionUtils.isEmpty(getFilters()) ? IteratorUtils.EMPTY_ITERATOR : getFilters().iterator();
		
		doWrite(token,object1,object2);
	}
	
	public void end()
	{
		_end = CollectionUtils.isEmpty(getFilters()) ? IteratorUtils.EMPTY_ITERATOR : getFilters().iterator();
	
		doEnd();
	}

	public void doPrepare(Template template, MarkupWriter writer)
	{
		if (_prepare.hasNext())
		{
			_prepare.next().prepare(template, writer, this);
		}
	}
	
	public void doWrite(TemplateToken token)
	{
		if (_write.hasNext())
		{
			_write.next().write(token, this);
		}
	}
	public void doWrite(TemplateToken token,Object object1,Object object2)
	{
		if (_write.hasNext())
		{
			_write.next().write(token, this,object1,object2);
		}
	}
	
	public void doEnd()
	{
		if (_end.hasNext())
		{
			_end.next().end(this);
		}
	}
}
