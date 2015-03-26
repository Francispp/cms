package com.cyberway.common.html.dom;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Node
{
    private Node _container;
    private List<Node> _children;

    protected Node(Node container)
    {
        _container = container;
    }

    public Node getContainer()
    {
        return _container;
    }

    Element asElement()
    {
        return null;
    }

    void addChild(Node child)
    {
        if (_children == null)
            _children = new ArrayList<Node> ();

        _children.add(child);
    }

    void insertChildAt(int index, Node child)
    {
        if (_children == null)
            _children = new ArrayList<Node> ();

        _children.add(index, child);
    }

    boolean hasChildren()
    {
        return _children != null && !_children.isEmpty();
    }

    void writeChildMarkup(PrintWriter writer)
    {
        if (_children == null)
            return;

        for (Node child : _children)
        {
            child.toMarkup(writer);
        }
    }

    public List<Node> getChildren()
    {
        return _children == null ? Collections.EMPTY_LIST : _children;
    }

    public abstract void toMarkup(PrintWriter writer);
}
