package com.cyberway.common.service;

import java.io.PrintWriter;

import com.cyberway.common.html.dom.Document;
import com.cyberway.common.html.dom.Element;

public interface MarkupWriter
{
	Document getDocument();
    Element getElement();
	Element element(String name, Object... attributes);
    Element end();
    void write(String text);
    void writeRaw(String text);
    void comment(String text);
    void attributes(Object... namesAndValues);
    void toMarkup (PrintWriter writer);
}
