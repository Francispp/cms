package com.cyberway.cms.internal.template;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cyberway.cms.template.TemplateLibrary;

public class TemplateLibraryImpl implements TemplateLibrary
{
	private VelocityEngine _engine;
	
	public TemplateLibraryImpl(File libraryFile) throws Exception
	{	
		Document document = new SAXReader ().read(libraryFile);
		
		_engine = new VelocityEngine ();
		_engine.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
		_engine.setProperty("string.resource.loader.repository.class", "org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl");
		_engine.setProperty("resource.loader", "string");
		_engine.init(); 
		
		for (Element element : (List<Element>)document.getRootElement().elements("Template"))
		{
			StringResourceLoader.getRepository().putStringResource(element.attributeValue("name"), element.getText());
		}
	}

	public String getTemplate(final String name, Map<String, String> parameters) throws Exception
	{
		StringWriter writer = new StringWriter ();
		Template template = _engine.getTemplate(name, "UTF-8");
		
		Context context = MapUtils.isEmpty(parameters) ? new VelocityContext () : new VelocityContext (parameters);
		
		if (ObjectUtils.equals(name, "universal"))
		{
			context.put("displayFields", StringUtils.split(parameters.get("displayFields"), ","));
		}
		
		template.merge(context , writer);
		
		return writer.toString();
	}
}
