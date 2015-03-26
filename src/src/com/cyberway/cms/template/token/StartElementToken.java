package com.cyberway.cms.template.token;


public class StartElementToken extends TemplateToken
{
    private final String _name;

    public StartElementToken(String name)
    {
        super(TokenType.StartElement);

        _name = name;
    }

    public String getName()
    {
        return _name;
    }
}
