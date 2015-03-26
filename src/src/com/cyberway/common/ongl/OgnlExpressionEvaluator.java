package com.cyberway.common.ongl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;

import ognl.ClassResolver;
import ognl.Node;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;
import ognl.TypeConverter;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.Validate;

import com.cyberway.common.ExpressionEvaluator;

public class OgnlExpressionEvaluator implements ExpressionEvaluator
{
	private ClassResolver _classResolver;
	private TypeConverter _typeConverter;
	private Map<?, ?> _defaultContext;
	private Map<Class<?>, PropertyAccessor> _propertyAccessors = new LinkedHashMap<Class<?>, PropertyAccessor> ();
	private Map<String, Object> _cache = new WeakHashMap<String, Object> ();
	private Map<Class<?>, Map<String, Node>> _objectCache = new WeakHashMap<Class<?>, Map<String, Node>> ();
	
	public OgnlExpressionEvaluator(ClassResolver classResolver, Map<Class<?>, PropertyAccessor> accessors, TypeConverter typeConverter)
	{
		Validate.notNull(classResolver);
		
		if (!MapUtils.isEmpty(accessors))
		{
			for (Entry<Class<?>, PropertyAccessor> pair : accessors.entrySet())
			{
				OgnlRuntime.setPropertyAccessor(pair.getKey(), pair.getValue());
			}
		}
		
		_defaultContext = Ognl.createDefaultContext(null, classResolver, typeConverter);
		_classResolver = classResolver;
		_typeConverter = typeConverter;
	}
	
	public Object getValue(Object target, String expression)
	{	
		try
		{
			return Ognl.getValue(expression, createContext(target), target);
		}
		catch (Exception ex)
		{
			throw new RuntimeException (ex);
		}
	}

	public void setValue(Object target, String expression, Object value)
	{
		try
		{
			Ognl.setValue(expression, createContext(target), target, value);
		}
		catch (Exception ex)
		{
			throw new RuntimeException (ex);
		}
	}
    
    private OgnlContext createContext (Object target)
    {
    	OgnlContext context = (OgnlContext)Ognl.createDefaultContext(target, _classResolver);
    	
    	if (_typeConverter != null)
    	{
    		Ognl.setTypeConverter(context, _typeConverter);
    	}
    	
    	return context;
    }
}
