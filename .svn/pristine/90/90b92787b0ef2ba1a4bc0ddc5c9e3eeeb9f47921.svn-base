package com.cyberway.cms.internal.template;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.service.MarkupWriter;

public interface TokenWriterFilterChain
{	
	void doPrepare (Template template, MarkupWriter writer);
	void doWrite (TemplateToken token);
	void doWrite (TemplateToken token,Object object1,Object object2);
	void doEnd ();
}