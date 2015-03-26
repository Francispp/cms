package com.cyberway.common.html.dom;

import java.io.PrintWriter;

import org.apache.commons.lang.Validate;

public final class Document extends Node
{
    private Element _rootElement;

    public Document()
	{
		super (null);
	}
    
    Document getDocument()
    {
        return this;
    }

    public Element find(String path)
    {
    	Validate.notNull(path);

        if (_rootElement == null) 
        	return null;

        int slashx = path.indexOf("/");

        String rootElementName = slashx < 0 ? path : path.substring(0, slashx);

        if (!_rootElement.getName().equals(rootElementName)) 
        	return null;

        return slashx < 0 ? _rootElement : _rootElement.find(path.substring(slashx + 1));
    }

    public Element newRootElement(String name)
    {
        _rootElement = new Element(this, name);

        return _rootElement;
    }

    @Override
    public void toMarkup(PrintWriter writer)
    {
        if (_rootElement == null)
            throw new IllegalStateException("No root element has been defined.");

        _rootElement.toMarkup(writer);
    }

    public Element getRootElement()
    {
        return _rootElement;
    }

    public Element getElementById(String id)
    {
        return _rootElement.getElementById(id);
    }
}
