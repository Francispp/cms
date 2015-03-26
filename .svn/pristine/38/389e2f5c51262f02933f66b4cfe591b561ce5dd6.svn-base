package com.cyberway.common.lucene;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;


public class FieldDescriptor
{
	private String _name;
	private String _expression;
	private Store _store = Store.YES;
	private Index _index = Index.TOKENIZED;
	private TermVector _termVector = TermVector.YES;
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public String getExpression()
	{
		return _expression == null ? getName() : _expression;
	}
	
	public void setExpression(String expression)
	{
		_expression = expression;
	}
	
	public Store getStore()
	{
		return _store;
	}
	
	public void setStore(Store store)
	{
		_store = store;
	}
	
	public Index getIndex()
	{
		return _index;
	}
	
	public void setIndex(Index index)
	{
		_index = index;
	}
	
	public TermVector getTermVector()
	{
		return _termVector;
	}
	
	public void setTermVector(TermVector termVector)
	{
		_termVector = termVector;
	}
}
