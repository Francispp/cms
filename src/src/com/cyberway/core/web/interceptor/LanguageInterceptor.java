package com.cyberway.core.web.interceptor;

import java.util.Locale;
import java.util.Map;

import com.cyberway.core.objects.Constants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LanguageInterceptor extends AbstractInterceptor {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1742463673553099145L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session=invocation.getInvocationContext().getSession();		
		String locale_str=com.cyberway.core.Constants.DEFAULT_LOCALE;
		if(session!=null && session.containsKey(Constants.LOCALE_LANGUAGE)){
			locale_str=(String)session.get(Constants.LOCALE_LANGUAGE);
			//System.out.println(" LanguageInterceptor:"+locale_str);
		}else{
			if(session!=null)//把默认的环境put session
			 session.put(Constants.LOCALE_LANGUAGE, locale_str);
		}
		if(!locale_str.equals(invocation.getInvocationContext().getLocale().toString())){
			System.out.println("重新设置语言！");
			//System.out.println(locale_str+":"+invocation.getInvocationContext().getLocale().toString());
			if(locale_str.endsWith(Constants.LANGUAGE_ZH_CN))
			 invocation.getInvocationContext().setLocale(Locale.CHINESE);
		    else if(locale_str.endsWith(Constants.LANGUAGE_EN))
			 invocation.getInvocationContext().setLocale(Locale.US);
		    else if(locale_str.endsWith(Constants.LANGUAGE_ZH_TW))
			 invocation.getInvocationContext().setLocale(Locale.TAIWAN);
		
		}
		return invocation.invoke();
	}
}
