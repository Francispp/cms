package com.cyberway.common.html.dom;

import java.io.PrintWriter;

import org.apache.commons.lang.StringEscapeUtils;

public final class Text extends Node
{
    private final StringBuilder _buffer;
    private final Document _document;

    Text(Node container, Document document, String text)
    {
        super(container);

        _document = document;

        _buffer = new StringBuilder();

        write(text);
    }

    public void write(String text)
    {
        _buffer.append(StringEscapeUtils.escapeHtml(text));
    }

    public void writef(String format, Object... args)
    {
        write(String.format(format, args));
    }

    @Override
    public void toMarkup(PrintWriter writer)
    {
        writer.print(_buffer.toString());
    }
}
