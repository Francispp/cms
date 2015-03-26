package com.cyberway.cms.template;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.cms.template.token.TemplateToken;

public class Template {
	private String _body;
	private List<TemplateToken> _tokens = new ArrayList<TemplateToken>();
	private Integer _type;
	private Boolean _isWap;
	
	public Template(String body, List<TemplateToken> tokens, Integer type) {
		_body = body;
		_tokens = tokens;
		_type = type;
	}
	public Template(String body, List<TemplateToken> tokens, Integer type,Boolean isWap) {
		_body = body;
		_tokens = tokens;
		_type = type;
		_isWap = isWap;
	}
	public String getBody() {
		return _body;
	}

	public List<TemplateToken> getTokens() {
		return _tokens;
	}

	public Integer getType() {
		return _type;
	}
	public Boolean getIsWap() {
		return _isWap;
	}
}
