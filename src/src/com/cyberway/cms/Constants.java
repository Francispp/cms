package com.cyberway.cms;

import com.cyberway.core.utils.ConfigurableConstants;

/**
 * 系统级静态常量. 可通过cms.properties初始化,同时保持常量 static & final的特征.
 * 
 * @author caiw
 * @see ConfigurableConstants
 * 
 */
public class Constants extends ConfigurableConstants 
{

	// 静态初始化读入springside.properties中的设置
	static {
		init("cms.properties");
	}

	/**
	 * 从cms.properties中读取constant.default.password的值，
	 * 如果配置文件不存在或配置文件中不存在该值时，默认取值"123456"
	 */
	
	public final static Integer MESSAGE_PAGESIZE = new Integer (getProperty("cms.pageSize", "10"));//分页默认的记录数
	
	public final static String SMTP_USERNAME = getProperty("cms.smtp.userName", null);
	public final static String SMTP_PASSWORD = getProperty("cms.smtp.password", null);
	public final static String EMAIL_ADDRESS = getProperty("cms.email.address", null);

	public final static String SMTP_HOST = getProperty("cms.smtp.host", null);//邮件域名
	public final static String EMAIL_NEED_AUTHENTICATION = getProperty("cms.email.needAuthentication", "false");//发送邮件是否需要验证
	public final static String DEFAULT_SENDER = getProperty("cms.default.toemail", "wpsadmin1@amway.com");//发送邮件默认收件人地址
	public final static String DEFAULT_FROMEMAIL = getProperty("cms.default.fromemail", "lan@cyberway.net.cn");//发送邮件默认发送人地址
	public final static String DEFAULT_FROMEMAILPASS = getProperty("cms.default.fromemailpass", "lan");//发送邮件默认密码
	
	public final static String SITES_IN_APPLICATION = getProperty("cms.default.sites_in_application", "cms_sites");
	public final static boolean IS_REFUSE = new Boolean(getProperty("cms.is_refuse", "false"));
	public final static boolean IS_REALPATH = new Boolean(getProperty("cms.is_realpath", "false"));//是否采用绝对路径
	public final static boolean IS_SEARCH_USERS = new Boolean(getProperty("cms.is_search_users", "false"));//是否采用查询的方式添加用户
	public final static String TEMPLATE_FILE = getProperty("cms.template_file", "/dynamicPage/template.xml");//导出模板临时文件
	public final static String CHANNEL_FILE = getProperty("cms.channel_file", "/dynamicPage/channel.xml");//导出频道临时文件
	public final static String SITE_FILE = getProperty("cms.site_file", "/dynamicPage/site.xml");//导出频道临时文件
	public final static String DOC_FILE = getProperty("cms.doc_file", "/dynamicPage/doc.xml");//导出模板临时文件
	public final static String ZIP_FILE = getProperty("cms.zip_file", "/dynamicPage/file.zip");//导出ZIP临时文件
	public final static String ABSOLUTE_PATH = getProperty("cms.absolute_path", "/");//绝对路径
	public final static boolean HIDDENMEDIAFILE_PATH = new Boolean(getProperty("cms.hiddenMediaFllePath", "false"));//隐藏流媒体文件前缀
	public final static String MEDIAFILE_PATH = getProperty("cms.mediaFllePath", "/");//流媒体文件的访问路径
	public final static String CACHE_SYNCHROISM = getProperty("cms.cache_synchroism", "/");//缓存同步到站点运行平台
	public final static String LUCENE_SYNCHROISM = getProperty("cms.lucene_synchroism", "/");//Lucene同步到站点运行平台
	public final static String HTML_SYNCHROISM = getProperty("cms.html_synchroism", "/");
	
	public final static boolean LUCENESYNCHROISMTYPE = new Boolean(getProperty("cms.lucene_synchroism_type", "true"));//lucene同步方式 true webservice 、false ftp
	
	public final static String UPLOADS_ATTACHMENT_PATH = getProperty("cms.upload_attachment_path", "/cms_file/uploads/attachment/");//静态资源文件路径
	public final static String UPLOADS_IMAGE_PATH = getProperty("cms.upload_image_path", "/cms_file/uploads/image/");//静态资源文件路径
	public final static String UPLOADS_FLASH_PATH = getProperty("cms.upload_flash_path", "/cms_file/uploads/flash/");//静态资源文件路径
	public final static String UPLOADS_PATH = getProperty("cms.upload_path", "/cms_file/uploads/");
	public final static String UPLOADS_TEMP_PATH = getProperty("cms.upload_temp_path", "/cms_file/uploads/temp/");
	public final static String UPLOADS_FILE_PATH = getProperty("cms.upload_file_path", "/cms_file/uploads/file/");
	public final static String UPLOADS_MEDIA_PATH = getProperty("cms.upload_media_path", "/");
	public final static String MEDIA_BROADCAST = getProperty("cms.media_broadcast", "/");
	public final static String MEDIA_BROADCAST_REMOTE = getProperty("cms.media_broadcast_remote", "/");
	public final static String XML_PATH = getProperty("cms.xml_path", "/cms_file/uploads/xml/");//静态资源文件路径
	public final static String STATICRESOURCE_PATH = getProperty("cms.staticResource_path", "/cms_file/staticResource/");//静态资源文件路径
	public final static String DYNAMICPAGE_PATH = getProperty("cms.dynamicPage_path", "/dynamicPage/");//临时文件路径
	public final static String STATICHTML_PATH = getProperty("cms.staticHtml_path", "/staticHtml/");//静态html文件路径
	public final static String STATICHTML_ABSOLUTE_PATH = getProperty("cms.staticHTML.absolute.path", "/apps/amway_net_cms/WEB-INF/statics/");//静态html文件绝对路径
	public final static String TEMPLATE_STATICHTML_PATH = getProperty("template.path", "/cms_file/templates/");//静态html文件路径
	public final static String SITE_URL_POSTFIX = getProperty("cms.site.url_postfix", "htm");//站点的后缀
	public final static String SITE_URL_LIST_POSTFIX = getProperty("cms.site.url_list_postfix", "html2");//站点的列表页后缀
	public final static String SITE_URL_DETAIL_POSTFIX = getProperty("cms.site.url_detail_postfix", "html3");//站点的详细页后缀
	public final static int SITE_TYPE=1;
	public final static int CHANNEL_TYPE=2;
	public final static int DOCUMENT_TYPE=3;
	public final static int USER_TYPE=1;
	public final static int DEPT_TYPE=3;
	public final static int ROLE_TYPE=2;
	public final static Long ALL_USER_ROLE_ID = new Long(getProperty("cms.role.AllUserRoleId", "0"));//系统中代表所有用户的角色
	public final static Long NUMBER_MAX_CLICK_COUNT = new Long(getProperty("cms.number.MaxClickCount", "10"));//点击数累计达到此值时，同步到数据库中	
	public final static Long CMS_DEFAULT_TEMPLATEFORMID = new Long(getProperty("cms.default.TemplateFormId", "1"));//cms模板默认的表单ID
	public final static String INFO_OFFICE_PATH = getProperty("cms.infoOfficeFile_path", "/cms_file/templates/dsoframe/");//dsoframe控件文件路径
	public final static boolean INFO_OFFICE_FILE_CONTENT_ISSEARCH=new Boolean(getProperty("cms.infoOfficeFielContentIsSearch", "false"));
	public final static String LOG_TARGET_TYPE_DOCUMENT = "document";
	public final static Long PUBLIC_USERID = new Long(getProperty("cms.public.userid", "0"));//公共用户
	public final static String PUBLISH_USER = "publish_user";
	public final static String LOGON_USER = (getProperty("cms.logon.user", ""));//AD或者本地验证方式
	public final static String LOGON_DEFAULT_URL = (getProperty("cms.logon.default.url", "/logon.action"));//前台默认登录地址
	public final static String DOWNLOAD_BLOB_FILE_OBJECT = "BLOB_FILE_OBJECT";
	public final static String DOCUMENT_PERMISSION = getProperty("cms.document.permission", "CMS_DOCUMENT_VIEW");
	public final static Long KMS_DEPTID = new Long(getProperty("kms.default.deptid", "1"));//KMS默认DEPTID
	public final static boolean DOCUMENT_BUILDINDEX = new Boolean(getProperty("cms.document.buildIndex", "false"));
	public final static int DOCUMENT_MAXOBJECT_COUNT = new Integer(getProperty("cms.document.MaxObjectCount", "1000")).intValue();//获得最大对象数
	public final static String FFMPEG_PATH = getProperty("ffmpeg_path", "/dynamicPage/ffmpeg.exe");//ffmpeg.exe文件路径
	public final static String LUCENE_PATH = getProperty("cms.document.lucenePath", "/WEB-INF/lucene/");//lucene文件路径
	//限制文件大小
	public final static String INFO_OFFICE_SIZE = getProperty("cms.infoOfficeFile_size", "10");//限制文件大小
	public final static boolean MEDIA_ISSUE = new Boolean(getProperty("cms.media_issue", "false"));
	
	public final static String MEDIA_VIDEO = getProperty("cms.media_video", "/video/");
	public final static String MEDIA_AUDIO = getProperty("cms.media_audio", "/audio/");
	
	/**
	 * 保存登录外部用户信息到session中的标志
	 */
	public final static String WEBUSER_IN_SESSION = getProperty("cms.webuser_in_session", "cmswebuser");
	
	public final static String CMS2_UPLOADS_PATH = getProperty("cms2.upload_path", "/cms2resource/");//CMS2.0静态资源文件路径
	public final static String CMS2_WORDS_PATH = getProperty("cms2.word_path", "/cms2resource/document/");//CMS2.0静态资源文件路径
	public final static boolean CMS2_IS_REALPATH = new Boolean(getProperty("cms2.is_realpath", "false"));
	public final static String CMS_REPORT_PATH = getProperty("cms.report_allhbmxml", "/WEB-INF/classes/com/cyberway/");
	
	/**
	 * cms外部用户的角色id--代表系统中所有用户
	 */
	public final static String WEBUSER_DEFAULT_ROLE_ID = getProperty("webUser.default.roleId","15");
	
	/**
	 * 分发主页模板为静态文件的路径
	 */
	public final static String PUB_STA_INDEX_PATH = getProperty("cms.pub_sta_index_path", "/cms/index.action");
	/**
	 * 分发概览模板为静态文件的路径
	 */
	public final static String PUB_STA_SUMMARY_PATH = getProperty("cms.pub_sta_summary_path", "/cms/docInfo!list.action");
	/**
	 * 分发细览模板为静态文件的路径
	 */
	public final static String PUB_STA_DETAILST_PATH = getProperty("cms.pub_sta_detailst_path", "/cms/docInfo!view.action");
	
	/**
	 * 分发细览模板为静态文件的路径WAP
	 */
	public final static String PUB_STA_DETAILST_WAP_PATH = getProperty("cms.pub_sta_detailst_wap_path", "/cms/docInfo!viewWap.action");
	
	/**
	 * 登录安利教育网url
	 */
	public final static String CMS_LOGIN_AMWAY_EDU_URL = getProperty("cms.login.amway.edu.url", "http://learning-china.qa.intranet.local/login/singlelogin.aspx");
	/**
	 * 登录安利教育网密钥
	 */
	public final static String CMS_LOGIN_AMWAY_EDU_ACTISECREKEY = getProperty("cms.login.amway.edu.actisecretkey", "ACTIITCA");
	
	/**
	 * 运行平台ftp根目录
	 */
	public final static String DEFAULT_ROOT = getProperty("site.defaultRoot", "/www/kangjia/ROOT/");
	
	/**
	 * 运行平台静态资源路径
	 */
	public final static String STATIC_PATH = getProperty("site.staticResource_path", "/cms_file/staticResource");
	/**
	 * 运行平台doc文档资源路径
	 */
	public final static String WORD_CENTER_PATH = getProperty("site.infoOfficeFile_path", "/cms_file/templates/dsoframe");
	/**
	 * 运行平台动态jsp资源路径
	 */
	public final static String MOVEMEN_PATH = getProperty("site.template.path", "/cms_file/templates");
	/**
	 * 运行平台其他资源路径  (attachment,file,image)
	 */
	public final static String OTHER_CENTER_PATH  = getProperty("site.upload_path", "/cms_file/uploads");
	/**
	 * 
	 */
	public final static String ATTACHMENT_PATH = getProperty("site.upload_attachment_path", "/cms_file/uploads/attachment");
	/**
	 * 
	 */
	public final static String OTHER_IMAGE_PATH = getProperty("site.upload_image_path", "/cms_file/uploads/image");
	/**
	 * 
	 */
	public final static String OTHER_FLASH_PATH = getProperty("site.upload_flash_path", "/cms_file/uploads/flash");
	/**
	 * 运行平台静态页面
	 */
	public final static String HTML_CENTER_PATH = getProperty("site.staticHtml_path", "/WEB-INF/statics");
	/**
	 * 运行平台Lucene资源
	 */
	public final static String LUCENE_PATH_FTP = getProperty("site.document.lucenePath", "/WEB-INF/lucene");
	/**
	 * 运行平台流媒体资源
	 */
	public final static String MEDIA_PATH = getProperty("site.fpt.media.path", "/media");
	
	/**
	 * 使用何种数据库
	 */
	public final static String DATABASE_SELECT = getProperty("cms.database_select", "oracle");
	
	/**
	 * ftp服务器的密钥
	 */
	public final static String CMS_FTP_ACTISECRETKEY = getProperty("cms.ftp.actisecretkey", "goodnight");
	
	/**
	 * ftp操作开关:为true时,表示可以进行ftp操作,为false时表示不可以进行ftp操作
	 */
	public final static String	CMS_FTP_ENABLED	= getProperty("cms.ftp.enabled", "true");
	
	/**
	 * 超文本传输协议
	 */
	public final static String CMS_HYPERTEXT_TRANSFER_PROTOCOL = getProperty("cms.hypertext.transfer.protocol", "http://");
	

	/**
	 * FreeMarker相关
	 */
	public final static String FREEMARKER_TEMPLATE_FOLDER = getProperty("freemarker.templateFolder", "/WEB-INF/ftl/");
	public final static String FREEMARKER_RESUME_FOLDER = getProperty("freemarker.resumeFolder", "/cms_file/resume/");

	/**
	 * 导入模板
	 */
	public final static String IMPORT_TEMPLATE_FOLDER = getProperty("import.templateFolder", "/WEB-INF/template/");


	/**
	 * CMS FTP相关
	 */
	public final static String CMS_FTP_NEED_UPLOAD = getProperty("cms.ftp.needUpload", "true");
	public final static String CMS_FTP_IS_SFTP = getProperty("cms.ftp.isSftp", "false");
	public final static String CMS_FTP_HOST = getProperty("cms.ftp.host", "127.0.0.1");
	public final static String CMS_FTP_USERNAME = getProperty("cms.ftp.username", "root");
	public final static String CMS_FTP_PASSWORD = getProperty("cms.ftp.password", "root");
	public final static String CMS_FTP_PORT=getProperty("cms.ftp.port", "21");
	public final static String CMS_FTP_BASE_PATH=getProperty("cms.ftp.basePath", "/home/cms");
	
	protected static final String CMS_STATIC_HTML_PATH = null;

}
