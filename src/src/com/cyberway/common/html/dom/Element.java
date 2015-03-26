package com.cyberway.common.html.dom;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.Validate;

public final class Element extends Node
{
    private final String _name;
    private Map<String, String> _attributes;
    private Element _parent;
    private final Document _document;

    Element(Document container, String name)
    {
        super(container);

        _document = container;
        _name = name;
    }

    Element(Element parent, String name)
    {
        super(parent);

        _parent = parent;
        _name = name;

        _document = parent.getDocument();
    }

    public Document getDocument()
    {
        return _document;
    }

    public Element getParent()
    {
        return _parent;
    }

    public void attribute(String name, String value)
    {
    	Validate.notNull(name);

        if (value == null) 
        	return;

        if (_attributes == null) 
        {
        	_attributes = new LinkedHashMap<String, String> ();
        }

        if (!_attributes.containsKey(name))
        {
        	_attributes.put(name, value);
        }
    }

    public void attributes(String... namesAndValues)
    {
        int i = 0;
        while (i < namesAndValues.length)
        {
            String name = namesAndValues[i++];
            String value = namesAndValues[i++];

            attribute(name, value);
        }
    }

    public void forceAttributes(String... namesAndValues)
    {
        if (_attributes == null)
        {
        	_attributes = new LinkedHashMap<String, String> ();
        }

        int i = 0;

        while (i < namesAndValues.length)
        {
            String name = namesAndValues[i++];
            String value = namesAndValues[i++];

            if (value == null)
            {
                _attributes.remove(name);
                continue;
            }

            _attributes.put(name, value);
        }
    }

    public Element element(String name, String... namesAndValues)
    {
    	Validate.notNull(name);

        Element child = newChild(new Element(this, name));

        child.attributes(namesAndValues);

        return child;
    }

    public Element elementAt(int index, String name, String... namesAndValues)
    {
    	Validate.notNull(name);

        Element child = new Element(this, name);
        child.attributes(namesAndValues);

        insertChildAt(index, child);

        return child;
    }

    public Element comment(String text)
    {
        newChild(new Comment(this, text));

        return this;
    }

    public Element raw(String text)
    {
        newChild(new Raw(this, text));

        return this;
    }

    public Text text(String text)
    {
        return newChild(new Text(this, _document, text));
    }

    private <T extends Node> T newChild(T child)
    {
        addChild(child);

        return child;
    }

    @Override
    public void toMarkup(PrintWriter writer)
    {
        StringBuilder buffer = new StringBuilder();
        Formatter formatter = new Formatter(buffer);

        formatter.format("<%s", _name);

        if (_attributes != null)
        {
            List<String> keys = new ArrayList<String>(_attributes.keySet());
            Collections.sort(keys);

            for (String key : keys)
            {
                String value = _attributes.get(key);

                formatter.format(" %s=\"", key);

                buffer.append(StringEscapeUtils.unescapeHtml(value));

                buffer.append('"');
            }
        }
        
        formatter.format(">");
        
        writer.print(buffer.toString());

        if (hasChildren()) 
        {
        	writeChildMarkup(writer);
        }

        writer.printf("</%s>", _name);
    }

    public Element getElementById(String id)
    {
        Validate.notNull(id);

        LinkedList<Element> queue = new LinkedList<Element> ();

        queue.add(this);

        while (!queue.isEmpty())
        {
            Element e = queue.removeFirst();

            String elementId = e.getAttribute("id");

            if (id.equals(elementId)) return e;

            for (Node n : e.getChildren())
            {
                Element child = n.asElement();

                if (child != null) queue.addLast(child);
            }
        }

        return null;
    }

    public Element find(String path)
    {
        Validate.notNull(path);

        Element search = this;

        for (String name : path.split("/"))
        {
            search = search.findChildWithElementName(name);

            if (search == null) break;
        }

        return search;
    }

    private Element findChildWithElementName(String name)
    {
        for (Node node : getChildren())
        {
            Element child = node.asElement();

            if (child != null && child.getName().equals(name)) return child;
        }

        return null;
    }

    public String getAttribute(String attributeName)
    {
        return MapUtils.getString(_attributes, attributeName);
    }

    public String getName()
    {
        return _name;
    }

    @Override
    Element asElement()
    {
        return this;
    }
}
