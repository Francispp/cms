package com.cyberway.common.utils;

import java.util.Formatter;

public final class BodyBuilder
{
    private static final int DEFAULT_LENGTH = 200;
    
    private final StringBuilder _buffer = new StringBuilder(DEFAULT_LENGTH);
    private final Formatter _formatter = new Formatter(_buffer);
    private static final String INDENT = "  ";
    private int _nestingDepth = 0;
    private boolean _atNewLine = true;

    public void clear()
    {
        _nestingDepth = 0;
        _atNewLine = true;
        _buffer.setLength(0);
    }

    public void add(String format, Object... args)
    {
        add(format, args, false);
    }

    public void addln(String format, Object... args)
    {
        add(format, args, true);
    }

    private void add(String format, Object[] args, boolean newLine)
    {
        indent();

        _formatter.format(format, args);

        if (newLine) newline();
    }

    private void newline()
    {
        _buffer.append("\n");
        _atNewLine = true;
    }

    public void begin()
    {
        if (!_atNewLine) newline();

        indent();
        _buffer.append("{");
        newline();

        _nestingDepth++;
    }

    public void end()
    {
        if (!_atNewLine) newline();

        _nestingDepth--;

        indent();
        _buffer.append("}");

        newline();
    }

    private void indent()
    {
        if (_atNewLine)
        {
            for (int i = 0; i < _nestingDepth; i++)
                _buffer.append(INDENT);

            _atNewLine = false;
        }
    }

    @Override
    public String toString()
    {
        return _buffer.toString();
    }
}