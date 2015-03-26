package com.cyberway.common.html.dom;

import java.io.PrintWriter;

public final class Comment extends Node
{
    private String _comment;

    Comment(Node container, String comment)
    {
        super(container);

        _comment = comment;
    }

    @Override
    public void toMarkup(PrintWriter writer)
    {
        writer.print("<!-- ");
        writer.print(_comment);
        writer.print(" -->");
    }
}
