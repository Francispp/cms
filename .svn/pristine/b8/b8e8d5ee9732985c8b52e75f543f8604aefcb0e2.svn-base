package com.cyberway.common.ongl;

import java.util.Map;

import ognl.NoSuchPropertyException;
import ognl.ObjectPropertyAccessor;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.beans.factory.InitializingBean;

public class DynaBeanPropertyAccessor extends ObjectPropertyAccessor implements InitializingBean
{
	protected boolean hasProperty (Map context, Object target, Object name) throws OgnlException
	{
		DynaBean dynaBean = (DynaBean)target;
		String propertyName = (String)name;
        
		return dynaBean.getDynaClass().getDynaProperty(propertyName) != null;
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
		DynaBean dynaBean = (DynaBean)target;
        
		if (!hasGetProperty(context, target, name))
			throw new NoSuchPropertyException (target, name);
		
		return dynaBean.get((String)name);
	}
	
	@Override
	public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException
	{
		DynaBean dynaBean = (DynaBean)target;
 
		if (!hasSetProperty(context, target, name))
			throw new NoSuchPropertyException (target, name);
		
		dynaBean.set((String)name, value);
	}

	public void afterPropertiesSet() throws Exception
	{
		OgnlRuntime.setPropertyAccessor(DynaBean.class, this);
	}
}
