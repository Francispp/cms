package com.cyberway.common.ongl;

import java.util.Map;

import ognl.NoSuchPropertyException;
import ognl.ObjectPropertyAccessor;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import org.apache.commons.lang.ObjectUtils;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.InitializingBean;

public class DocumentPropertyAccessor extends ObjectPropertyAccessor implements InitializingBean
{
	protected boolean hasProperty (Map context, Object target, Object name) throws OgnlException
	{
		Document document = (Document)target;
		String propertyName = (String)name;
        
		return document.getField(propertyName) != null;
	}
	
	@Override
	public boolean hasGetProperty(Map context, Object target, Object name) throws OgnlException
	{
		return hasProperty(context, target, name);
	}
	
	@Override
	public boolean hasSetProperty(Map context, Object target, Object name) throws OgnlException
	{
		return hasProperty(context, target, name);
	}
	
	@Override
	public Object getProperty(Map context, Object target, Object name) throws OgnlException
	{
		Document document = (Document)target;
        
		if (!hasGetProperty(context, target, name))
			throw new NoSuchPropertyException (target, name);
		
		return document.get(ObjectUtils.toString(name));
	}
	
	@Override
	public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException
	{
		if (!hasSetProperty(context, target, name))
			throw new NoSuchPropertyException (target, name);
		
		throw new UnsupportedOperationException ();
	}

	public void afterPropertiesSet() throws Exception
	{
		OgnlRuntime.setPropertyAccessor(Document.class, this);
	}
}
