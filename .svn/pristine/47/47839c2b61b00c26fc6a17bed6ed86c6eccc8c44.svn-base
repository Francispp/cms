package com.cyberway.core.acegi.ui;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.ui.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.bind.ServletRequestUtils;

public class TokenRememberMeServices extends TokenBasedRememberMeServices {

	protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
		if (isAlwaysRemember()) {
			return true;
		}
		return ServletRequestUtils.getBooleanParameter(request, parameter, false);
	}
}
