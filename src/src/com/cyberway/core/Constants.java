package com.cyberway.core;

import com.cyberway.core.utils.ConfigurableConstants;

/**
 * 系统级静态常量. 可通过cyberway.properties初始化,同时保持常量 static & final的特征.
 * 
 * @author caiw
 * @see ConfigurableConstants
 * 
 */
public class Constants extends ConfigurableConstants {

	// 静态初始化读入springside.properties中的设置
	static {
		init("cyberway.properties");
	}
	/**
	 * 默认LOCALE
	 */
	public final static String DEFAULT_LOCALE = getProperty("constant.default.locale", "zh_CN");
	/**
	 * 默认用户密码
	 */
	public final static String DEFAULT_PASSWD = getProperty(
			"constant.default.passwd", "123456");
	
	/**
	 * 从cyberway.properties中读取constant.message_bundle_key的值，
	 * 如果配置文件不存在或配置文件中不存在该值时，默认取值"messages"
	 */
	public final static String MESSAGE_BUNDLE_KEY = getProperty(
			"constant.message_bundle_key", "i18n/messages");

	public final static String ERROR_BUNDLE_KEY = getProperty(
			"constant.error_bundle_key", "i18n/errors");

	/**
	 * 通过地址栏传递的网站名称接受参数
	 */
	public final static String VAR_WEB_SITE_URL = getProperty(
			"constant.web_site_name", "website");

	/**
	 * 保存登录用户信息到session中的标志
	 */
	public final static String USER_IN_SESSION = getProperty(
			"constant.user_in_session", "loginer");
	/**
	 * 保存登录用户信息到session中的标志
	 */
	public final static String STYLE_IN_SESSION = getProperty(
			"constant.style_in_session", "LOCALE_STYLE");
	public static final Long STATUS_INVALID = new Long(0);

	public static final Long STATUS_VALID = new Long(1);
	//登录门户参数
	public static final String PORTAL_CODE="portalcode";	
	//登录门户参数
	public static final String SITEID="siteId";	
	
	
	//登录门户参数
	public static final String SITENAME="siteName";	
	
	//默认门户编码
    public static final String DEFAULT_PORTAL_CODE = getProperty(
			"constant.default.portalcode", "cms").trim();
    //默认站点管理员组
    public static final String DEFAULT_ADMINISTRATORS_ID= getProperty(
			"constant.default.administrators.id", "0").trim();
	//默认系统提交日志数量
    public static final String DEFAULT_LOG_CONMIT_COUNT = getProperty(
			"constant.system.log.count.commit", "1000").trim();    
}
