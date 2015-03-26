package com.cyberway.cms.domain;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;

public class DocumentItem
{
	public final static String VALUETYPE_NUMBER = "number";
	public final static String VALUETYPE_VARCHAR = "varchar";
	public final static String VALUETYPE_DATE = "date";
	public final static String VALUETYPE_BOOLEAN = "boolean";
	public final static String VALUETYPE_TEXT = "text";
	public final static String VALUETYPE_OBJECT = "object";
	public final static String[] DEFINED_VALUETYPE = 
		{ VALUETYPE_NUMBER, VALUETYPE_VARCHAR, VALUETYPE_DATE, VALUETYPE_BOOLEAN, VALUETYPE_TEXT, VALUETYPE_OBJECT };
	public final static int VARCHAR_LENGTH = 255;
	
	private Long _id;
	private Document _document;
	private String _name;
	public String _valueType = VALUETYPE_VARCHAR;
	private Number _number;
	private String _string;
	private Date _date;
	private Boolean _boolean;
	private String _text;
	private Object _object;

	public Document getDocument()
	{
		return _document;
	}

	public void setDocument(Document document)
	{
		_document = document;
	}

	public Object getObject()
	{
		return _object;
	}
	
	public void setObject(Object object)
	{
		_object = object;
	}
	
	public Boolean getBoolean()
	{
		return _boolean;
	}
	
	public void setBoolean(Boolean b)
	{
		_boolean = b;
	}
	
	public Date getDate()
	{
		return _date;
	}
	
	public void setDate(Date date)
	{
		_date = date;
	}
	
	public Long getId()
	{
		return _id;
	}
	
	public void setId(Long id)
	{
		_id = id;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public Number getNumber()
	{
		return _number;
	}
	
	public void setNumber(Number number)
	{
		_number = number;
	}
	
	public String getString()
	{
		return _string;
	}
	
	public void setString(String string)
	{
		_string = string;
	}
	
	public String getText()
	{
		return _text;
	}
	
	public void setText(String text)
	{
		_text = text;
	}

	public String getValueType()
	{
		return _valueType;
	}
	
	public void setValueType(String valueType)
	{
		Validate.isTrue(ArrayUtils.contains(DEFINED_VALUETYPE, valueType));
		
		_valueType = valueType;
	}
	
	public Object getValue()
	{
		if (ObjectUtils.equals(VALUETYPE_BOOLEAN, getValueType()))
		{
			return getBoolean();
		}
		else if (ObjectUtils.equals(VALUETYPE_NUMBER, getValueType()))
		{
			return getNumber();
		}
		else if (ObjectUtils.equals(VALUETYPE_DATE, getValueType()))
		{
			return getDate();
		}
		else if (ObjectUtils.equals(VALUETYPE_VARCHAR, getValueType()))
		{
			return getString();
		}
		else if (ObjectUtils.equals(VALUETYPE_TEXT, getValueType()))
		{
			return getText();
		}
		else
		{
			return getObject();
		}
	}
	
	public void setValue(Object value)
	{
		if (ClassUtils.isAssignable(value.getClass(), Boolean.class))
		{
			setValueType(VALUETYPE_BOOLEAN);
			setBoolean((Boolean)value);
		}
		else if (ClassUtils.isAssignable(value.getClass(), Number.class))
		{
			setValueType(VALUETYPE_NUMBER);
			setNumber((Number)value);
		}
		else if (ClassUtils.isAssignable(value.getClass(), Date.class))
		{
			setValueType(VALUETYPE_DATE);
			setDate ((Date)value);
		}
		else if (ClassUtils.isAssignable(value.getClass(), String.class) ||
				(ClassUtils.isAssignable(value.getClass(), String[].class) &&
				ArrayUtils.getLength(value) == 1))
		{
			if (ClassUtils.isAssignable(value.getClass(), String[].class))
			{
				value = ((String[])value)[0];
			}
			
			if (((String)value).length() <= VARCHAR_LENGTH)
			{
				setValueType(VALUETYPE_VARCHAR);
				setString ((String)value);
			}
			else
			{
				setValueType(VALUETYPE_TEXT);
				setText((String)value);
			}
		}
		else
		{
			setValueType(VALUETYPE_OBJECT);
			setObject(value);
		}
	}	
}
