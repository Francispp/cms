package com.cyberway.common;

public interface ExpressionEvaluator
{
	Object getValue (Object target, String expression);
	void setValue (Object target, String expression, Object value);
}
