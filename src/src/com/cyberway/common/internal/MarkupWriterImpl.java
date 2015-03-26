package com.cyberway.common.internal;

import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;

import com.cyberway.common.html.dom.Document;
import com.cyberway.common.html.dom.Element;
import com.cyberway.common.html.dom.Text;
import com.cyberway.common.service.MarkupWriter;

public class MarkupWriterImpl implements MarkupWriter
{
    private final Document _document;
    private Element _current;
    private Text _currentText;

    public MarkupWriterImpl()
    {
    	_document = new Document ();
    }

    public void toMarkup(PrintWriter writer)
    {
        _document.toMarkup(writer);
    }

    @Override
    public String toString()
    {
        return _document.toString();
    }

    public Document getDocument()
    {
        return _document;
    }

    public Element getElement()
    {
        return _current;
    }

    public void write(String text)
    {
        if (_current == null && StringUtils.isBlank(text))
            return;

        ensureCurrentElement();

        if (text == null)
            return;

        if (_currentText == null)
        {
            _currentText = _current.text(text);
            return;
        }

        _currentText.write(text);
    }

    public void attributes(Object... namesAndValues)
    {
        ensureCurrentElement();

        int i = 0;

        while (i < namesAndValues.length)
        {
            // name should never be null.

            String name = namesAndValues[i++].toString();
            Object value = namesAndValues[i++];

            if (value == null)
                continue;

            _current.attribute(name, value.toString());
        }

    }

    private void ensureCurrentElement()
    {
        if (_current == null)
            throw new IllegalStateException("Not current element.");
    }

    public Element element(String name, Object... namesAndValues)
    {
        if (_current == null)
            _current = _document.newRootElement(name);
        else
            _current = _current.element(name);

        attributes(namesAndValues);

        _currentText = null;

        return _current;
    }

    public void writeRaw(String text)
    {
    	if (_current == null && StringUtils.isBlank(text))
             return;

        ensureCurrentElement();

    	_currentText = null;

        _current.raw(text);
    }

    public Element end()
    {
        ensureCurrentElement();

        _current = _current.getParent();

        _currentText = null;

        return _current;
    }

    public void comment(String text)
    {
        ensureCurrentElement();

        _current.comment(text);

        _currentText = null;
    }
}