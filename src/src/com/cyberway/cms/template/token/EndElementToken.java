package com.cyberway.cms.template.token;


public class EndElementToken extends TemplateToken
{
	private String _name;
	
	public EndElementToken()
    {
        super(TokenType.EndElement);
    }
	
    public EndElementToken(String name)
    {
        super(TokenType.EndElement);
        
        _name = name;
    }
    
    public String getName()
	{
		return _name;
	}
}
