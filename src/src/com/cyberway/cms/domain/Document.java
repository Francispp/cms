package com.cyberway.cms.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import com.cyberway.cms.form.object.BaseDocument;


public class Document extends BaseDocument implements DynaBean
{	
	static class DynaClass extends BasicDynaClass
	{
		public DynaClass()
		{
			super(null, null, null);
		}

		public DynaClass(String name, Class dynaBeanClass)
		{
			super (name, dynaBeanClass, null);
		}
		
		@Override
		public DynaProperty[] getDynaProperties()
		{
			throw new UnsupportedOperationException ();
		}
		
		@Override
		public DynaProperty getDynaProperty(String name)
		{
			return new DynaProperty (name);
		}
	}
	
	class FindByNamePredicate implements Predicate
	{
		private String _name;
		
		public FindByNamePredicate (String name)
		{
			Validate.notNull(name);
			
			_name = name;
		}
		
		public boolean evaluate(Object obj)
		{
			return ObjectUtils.equals(_name, ((DocumentItem)obj).getName());
		}
	}  
	
	public final static Map<String, String> FIELD_MAPPING = new HashMap<String, String> ();
			
	private String _body;


	private List<DocumentItem> _items = new ArrayList<DocumentItem> ();
	private DynaClass _dynaClass = new DynaClass ();
	
	public Document ()
	{
	}
	
	public Document (DynaClass dynaClass)
	{
	}

	@Field(index=Index.TOKENIZED)
	public String getBody()
	{
		return _body;
	}

	public void setBody(String body)
	{
		_body = body;
	}

	public List<DocumentItem> getItems()
	{
		return _items;
	}

	public void setItems(List<DocumentItem> items)
	{
		_items = items;
	}
	public boolean contains(String name, String key)
	{
		return false;
	}
	
	public void remove(String s, String s1)
	{
		throw new UnsupportedOperationException ();
	}
	
	public Object get(String name)
	{
		if (BeanUtils.getPropertyDescriptor(this.getClass(), name) != null)
		{
			return new BeanWrapperImpl (this).getPropertyValue(name);
		}
		else
		{
			DocumentItem item = (DocumentItem)CollectionUtils.find(getItems(), new FindByNamePredicate (name));
			return item == null ? null : item.getValue();
		}
	}
	
	public Object get(String name, int index)
	{
		throw new UnsupportedOperationException ();
	}
	
	public Object get(String name, String key)
	{
		throw new UnsupportedOperationException ();
	}
	
	public void set(String name, Object value)
	{
		if (BeanUtils.getPropertyDescriptor(this.getClass(), name) != null)
		{
			new BeanWrapperImpl (this).setPropertyValue(name, value);
		}
		else
		{
			DocumentItem item = (DocumentItem)CollectionUtils.find(getItems(), new FindByNamePredicate (name));
			if (value == null)
			{
				if (item != null)
				{
					getItems().remove(item);
				}
			}
			else
			{
				if (item == null)
				{
					item = new DocumentItem ();
					item.setName(name);
					item.setValue(value);
					item.setDocument(this);
					getItems().add(item);
				}
				else
				{
					item.setValue(value);
				}
			}
		}
	}
	
	public void set(String name, int index, Object value)
	{
		throw new UnsupportedOperationException ();
	}
	
	public void set(String name, String key, Object value)
	{
		throw new UnsupportedOperationException ();
	}

	public DynaClass getDynaClass()
	{
		return _dynaClass;
	}
}
