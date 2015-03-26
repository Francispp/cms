package com.cyberway.weixin.util;
/**
 * 
 * @com.cyberway.weixin.util.Constants
 * TODO :此处定义的常量需要持久化，可以保存在数据库中，在需要的地方读取。在多企业号中，最好以每个应用来定义。
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015-1-27 上午09:57:25
 */
public class Constants {
	/**
	 * 自定义
	 */
	public static final String TOKEN = "cyberway";
	/**
	 * 公司唯一标识ID
	 */
	public static final String CORPID = "wx5c2cc405f9858fc8";
	/**
	 * 权限密钥
	 */
	public static final String SECRET = "GM40kgp0jp1VRkLLsnixnt5lJtTBrwK_2vJ9IF36oguPG5F2kBsj3K42o7BccCjp";
	/**
	 * 43位字符
	 */
	public static final String encodingAESKey = "MeMX2DIRQ9RDJjRHvgMVDSiZu6W2gPWqWKCVoJ3KO3e";
	/**
	 *  OAuth2 回调地址
	 */
	public static String REDIRECT_URI = "http://wx.cyberway.com.cn:8084/weixin/attendance!signIn.action";
	/**
	 * 微信考勤ID
	 */
	public static final String AGENTID = "5";

}
