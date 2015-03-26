package com.cyberway.cms.internal.template;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.service.MarkupWriter;

public interface TokenWriterFilter
{
	void prepare (Template template, MarkupWriter writer, TokenWriterFilterChain chain);
	void write(TemplateToken token, TokenWriterFilterChain chain);
	void write(TemplateToken token, TokenWriterFilterChain chain,Object object1,Object object2);
	void end (TokenWriterFilterChain chain);
}
