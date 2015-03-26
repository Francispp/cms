package com.cyberway.common.comtemplate.token;

import com.cyberway.common.comtemplate.TokenType;

public abstract class ComTemplateToken {

	private final TokenType _tokenType;
	
	protected ComTemplateToken(TokenType tokenType){
		
		_tokenType=tokenType;
	}
	
	public TokenType getTokenType(){
		
		return _tokenType;
	}
}
