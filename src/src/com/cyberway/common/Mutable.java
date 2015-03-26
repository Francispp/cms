package com.cyberway.common;

public class Mutable<T>
{
	private T _value;

	public Mutable()
	{
	}
	
	public Mutable (T value)
	{
		setValue(value);
	}
	
	public T getValue()
	{
		return _value;
	}

	public void setValue(T value)
	{
		_value = value;
	}
}
