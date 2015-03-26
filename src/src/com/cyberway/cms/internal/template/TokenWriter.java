package com.cyberway.cms.internal.template;

import com.cyberway.cms.template.Template;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.service.MarkupWriter;

public interface TokenWriter
{
	void prepare (Template template, MarkupWriter writer);
	void write (TemplateToken token);
	void write (TemplateToken token,Object object1,Object object2);
	void end ();
}
