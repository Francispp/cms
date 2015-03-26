package com.cyberway.common.comtemplate;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.comtemplate.token.ComTemplateToken;

public class ComTemplate {
	private String _body;
    private List<ComTemplateToken> _tokens = new ArrayList<ComTemplateToken>();
    private Integer _type;
    private Boolean _isWap;
    
    
    public ComTemplate(String body,List<ComTemplateToken> tokens, Integer type){
    	_body = body;
    	_tokens = tokens;
    	_type = type;
    }
    
    public ComTemplate(String body,List<ComTemplateToken> tokens, Integer type,Boolean isWap){
    	_body = body;
		_tokens = tokens;
		_type = type;
		_isWap = isWap;
    }
    
    public String getBody() {
		return _body;
	}
    
    public List<ComTemplateToken> getTokens(){
    	return _tokens;
    }
    
    public Integer getType() {
		return _type;
	}
    
    public Boolean getIsWap() {
		return _isWap;
	}
    
    
}
