package com.cyberway.common.html.dom;

import java.io.PrintWriter;

public final class Raw extends Node
{
    private final String _text;

    Raw(Node container, String text)
    {
        super(container);

        _text = text;
    }

    @Override
    public void toMarkup(PrintWriter writer)
    {
        writer.print(_text);
    }
}
