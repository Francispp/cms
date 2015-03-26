package com.cyberway.cms.domain;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable
{
	private Long _id;
	private String _action;
	private Long _operatorId;
	private String _operator;
	private Long _operatorDepartmentId;
	private String _operatorDepartment;
	private Long _target;
	private String _targetType;
	private String _description;
	private Date _time = new Date ();
	
	public Long getId()
	{
		return _id;
	}
	
	public void setId(Long id)
	{
		_id = id;
	}
	
	public Date getTime()
	{
		return _time;
	}

	public void setTime(Date time)
	{
		_time = time;
	}

	public String getAction()
	{
		return _action;
	}
	
	public void setAction(String action)
	{
		_action = action;
	}
	
	public String getOperator()
	{
		return _operator;
	}

	public void setOperator(String operator)
	{
		_operator = operator;
	}

	public Long getOperatorId()
	{
		return _operatorId;
	}

	public void setOperatorId(Long operatorId)
	{
		_operatorId = operatorId;
	}

	public Long getOperatorDepartmentId()
	{
		return _operatorDepartmentId;
	}

	public void setOperatorDepartmentId(Long operatorDepartmentId)
	{
		_operatorDepartmentId = operatorDepartmentId;
	}

	public String getOperatorDepartment()
	{
		return _operatorDepartment;
	}

	public void setOperatorDepartment(String operatorDepartment)
	{
		_operatorDepartment = operatorDepartment;
	}

	public Long getTarget()
	{
		return _target;
	}
	
	public void setTarget(Long target)
	{
		_target = target;
	}
	
	public String getTargetType()
	{
		return _targetType;
	}
	
	public void setTargetType(String targetType)
	{
		_targetType = targetType;
	}
	
	public String getDescription()
	{
		return _description;
	}
	
	public void setDescription(String description)
	{
		_description = description;
	}
}
