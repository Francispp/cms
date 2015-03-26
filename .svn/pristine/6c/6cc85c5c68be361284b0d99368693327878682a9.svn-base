package com.cyberway.cms.internal.template;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class IdAllocator
{
	public String allocate()
	{
		return RandomStringUtils.randomNumeric(10);
	}
	
	public String allocate(String baseId)
	{		
		return StringUtils.isBlank(baseId) ? allocate() : String.format("%s_%s", baseId, RandomStringUtils.randomNumeric(10));
	}
}
