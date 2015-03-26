package com.cyberway.cms.internal.template;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.TemplateToken;


public class EmailPageWriter extends PageWriterImpl
{
	@Override
	public void write(Template template, OutputStream outputStream)
	{
		getMarkupWriter().element("html");
		
		getTokenWriter().prepare(template, getMarkupWriter());
		
		for (TemplateToken token : template.getTokens())
		{
			getTokenWriter().write(token);
		}
		
		getMarkupWriter().end();
		
		getTokenWriter().end();
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter (outputStream);
			writer.append(ObjectUtils.toString(getPrefix()));
			getMarkupWriter().toMarkup(writer);
			writer.append(ObjectUtils.toString(getSuffix()));
			writer.flush();
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStream);
		}
	}

	public void write(Template template, OutputStream outputStream,Object object1,Object object2)
	{
		getMarkupWriter().element("html");
		
		getTokenWriter().prepare(template, getMarkupWriter());
		
		for (TemplateToken token : template.getTokens())
		{
			getTokenWriter().write(token,object1,object2);
		}
		
		getMarkupWriter().end();
		
		getTokenWriter().end();
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter (outputStream);
			writer.append(ObjectUtils.toString(getPrefix()));
			getMarkupWriter().toMarkup(writer);
			writer.append(ObjectUtils.toString(getSuffix()));
			writer.flush();
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new RuntimeException (ex);
		}
		finally
		{
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStream);
		}
	}
}
