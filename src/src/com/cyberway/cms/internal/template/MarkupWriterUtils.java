package com.cyberway.cms.internal.template;

import com.cyberway.common.service.MarkupWriter;

public class MarkupWriterUtils
{
	public static void writeJavascript (String script, MarkupWriter writer)
	{
		writer.element("script", "type", "text/javascript");
		writer.writeRaw(script);
		writer.end();
	}
}
