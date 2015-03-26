package com.cyberway.staticer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统配置
 * @author helfen
 *
 */
public class Configuration
{
	public static ServletContext SERVLET_CONTEXT;
	
	public static ServletContext servletContext ()
	{
		return SERVLET_CONTEXT;
	}
	
	/**
	 * 默认的角色名
	 * @return
	 */
	public static String defaultRole ()
	{
		return "public";
	}
	
	/**
	 * 用户自定义标识参数前缀
	 * @return
	 */
	public static String flagParamPrefix ()
	{
		return "__";
	}
	
	/**
	 * 页面文件中各参数的分割符
	 * @return
	 */
	public static String paramSep ()
	{
		return "$";
	}
	
	/**
	 * 文档文件夹前缀
	 * @return
	 */
	public static String documentFolderPrefix ()
	{
		return "DOC_";
	}
	
	/**
	 * 角色文件夹前缀
	 * @return
	 */
	public static String roleFolderPrefix ()
	{
		return "R_";
	}
	
	/**
	 * 频道文件夹前缀
	 * @return
	 */
	public static String channelFolderPrefix ()
	{
		return "C_";
	}
	
	/**
	 * 模板文件夹前缀
	 * @return
	 */
	public static String templateFolderPrefix ()
	{
		return "T_";
	}
	
	/**
	 * 模板编号参数名
	 * @return
	 */
	public static String paramTemplateId ()
	{
		return "templateId";
	}
	
	/**
	 * 频道编号参数名
	 * @return
	 */
	public static String paramChannelId ()
	{
		return "channelId";
	}
	
	/**
	 * 文档编号
	 * @return
	 */
	public static String documentId ()
	{
		return "id";
	}
	
	/**
	 * 页面文件中参数的键与值之间的分割符
	 * @return
	 */
	public static String paramNameValSep ()
	{
		return "=";
	}
	
	/**
	 * 页面文件中的角色分割符
	 * @return
	 */
	public static String roleSep ()
	{
		return "^";
	}
	
	public static String headerVarPrefix ()
	{
		return "$VAR_BEGIN(";
	}
	
	public static String headerVarSuffix ()
	{
		return ")VAR_END";
	}
	
	public static String headerGahterPhase ()
	{
		return "gatherPhase";
	}
	
	public static class Initializer implements ServletContextListener
	{
		public void contextInitialized(ServletContextEvent sce)
		{
			SERVLET_CONTEXT = sce.getServletContext();
		}
		
		public void contextDestroyed(ServletContextEvent sce)
		{	
		}
	}
}
