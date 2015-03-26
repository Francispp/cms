package com.cyberway.cms.template.token;

public class CodeToken extends TemplateToken
{
    private final String _code;

    public CodeToken(String code)
    {
        super(TokenType.Code);

        _code = code;
    }

    public String getCode()
    {
        return _code;
    }
}
