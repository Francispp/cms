package com.cyberway.weixin.api;

public class AuthAPI {


	/**
	 * 获取预授权码
	 */
	public static String GET_PRE_AUTH_CODE_URL=  "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code?suite_access_token=";
	
	/**
	 * 获取永久授权码
	 */
	public static String GET_PREMANENT_CODE_URL= "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=";
	/**
	 * 获取企业号的授权信息
	 */
	public static String GET_AUTH_INFO_URL= "https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info?suite_access_token=";
	/**
	 * 获取企业号应用
	 */
	public static String GET_AGENT_URL= "https://qyapi.weixin.qq.com/cgi-bin/service/get_agent?suite_access_token=";
	/**
	 * 获取企业号access_token
	 */
	public static String GET_CORP_TOKEN_URL= "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token=";
}
