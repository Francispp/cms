package com.cyberway.cms.internal.template;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;

import com.cyberway.cms.template.PageWriter;
import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.internal.MarkupWriterImpl;
import com.cyberway.common.service.MarkupWriter;

public class PageWriterImpl implements PageWriter
{
	private TokenWriter _tokenWriter;
	private MarkupWriter _markupWriter;
	private String _prefix;
	private String _suffix;
	private String sitefix;
	private String sitefixWap;
	
	public String getSitefixWap() {
		return sitefixWap;
	}

	public void setSitefixWap(String sitefixWap) {
		this.sitefixWap = sitefixWap;
	}

	public String getSitefix() {
		return sitefix;
	}

	public void setSitefix(String sitefix) {
		this.sitefix = sitefix;
	}

	public PageWriterImpl()
	{
		_markupWriter = new MarkupWriterImpl ();
	}
	
	public MarkupWriter getMarkupWriter()
	{
		return _markupWriter;
	}

	public void setMarkupWriter(MarkupWriter markupWriter)
	{
		_markupWriter = markupWriter;
	}

	public TokenWriter getTokenWriter()
	{
		return _tokenWriter;
	}

	public void setTokenWriter(TokenWriter tokenWriter)
	{
		_tokenWriter = tokenWriter;
	}

	public String getPrefix()
	{
		return _prefix;
	}

	public void setPrefix(String prefix)
	{
		_prefix = prefix;
	}

	public String getSuffix()
	{
		return _suffix;
	}

	public void setSuffix(String suffix)
	{
		_suffix = suffix;
	}

	public void write(Template template, OutputStream outputStream)
	{
		getTokenWriter().prepare(template, getMarkupWriter());
	
		for (TemplateToken token : template.getTokens())
		{
			getTokenWriter().write(token);
		}
		
		getTokenWriter().end();
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter (outputStream);
			if(template.getType() == 2 || template.getType() == 3)
				writer.append(ObjectUtils.toString(getSitefix()));
			else if(template.getType() == 6 || template.getType() == 7||template.getType() == 8 || template.getType() == 9)
				writer.append(ObjectUtils.toString(getSitefixWap()));
			else
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
		getTokenWriter().prepare(template, getMarkupWriter());
	
		for (TemplateToken token : template.getTokens())
		{
			getTokenWriter().write(token,object1,object2);
		}
		
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
