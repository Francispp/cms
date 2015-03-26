package com.cyberway.cms.template;

import java.io.OutputStream;

public interface PageWriter
{
	void write (Template template, OutputStream outputStream);
	void write (Template template, OutputStream outputStream,Object object1,Object object2);
}
