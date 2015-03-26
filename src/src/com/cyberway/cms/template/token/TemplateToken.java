package com.cyberway.cms.template.token;


public abstract class TemplateToken
{
    private final TokenType _tokenType;

    protected TemplateToken(TokenType tokenType)
    {
        _tokenType = tokenType;
    }

    public TokenType getTokenType()
    {
        return _tokenType;
    }
}
