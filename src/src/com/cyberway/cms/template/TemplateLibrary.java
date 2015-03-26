package com.cyberway.cms.template;

import java.util.Map;

public interface TemplateLibrary
{
	String getTemplate (String name, Map<String, String> parameters) throws Exception;
}
