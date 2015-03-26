package com.cyberway.staticer.ftp;

import java.io.File;

/**
 * com.cyberway.staticer.ftp.SiteFTPClient
 * 
 * @author Janice Yang
 * 
 * @createTime 2011-12-27 下午3:13:45
 * 
 * @Description:FTP客户端
 * 
 */
public interface SiteFTPClient {
	/**
	 * 上传文件
	 * 
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param fileName
	 *            文件路径
	 * @param file
	 *            文件
	 */
	void upload(Long siteId, String siteResourceType, String fileName, File file) throws Exception;

	/**
	 * 上传文件
	 * 
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param fileName
	 *            文件路径
	 * @param file
	 *            文件
	 * @param overwrite
	 *            是否覆盖
	 */
	void upload(Long siteId, String siteResourceType, String fileName, File file, boolean overwrite) throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param ftpFilePath
	 *            ftp上的文件路径
	 * @param localFilePath
	 *            发布平台上的文件路径
	 * @throws Exception
	 */
	void delete(Long siteId, String siteResourceType, String ftpFilePath, String localFilePath) throws Exception;

	/**
	 * 上传文件
	 * 
	 * @param ftpLogId
	 *            ftp分发记录id
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param fileName
	 *            文件路径
	 * @param file
	 *            文件
	 */
	public void upload(Long ftpLogId, final Long siteId, final String siteResourceType, String fileName, File file)
	        throws Exception;

	/**
	 * 上传文件
	 * 
	 * @param ftpLogId
	 *            ftp分发记录id
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param ftpFilePath
	 *            文件路径
	 * @param file
	 *            文件
	 * @param overwrite
	 *            是否覆盖
	 */
	public void upload(final Long ftpLogId, final Long siteId, final String siteResourceType, String ftpFilePath,
	        final File file, final boolean overwrite) throws Exception;

	/**
	 * 删除文件
	 * 
	 * @param ftpLogId
	 *            ftp分发记录id
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param ftpFilePath
	 *            ftp上的文件路径
	 * @param localFilePath
	 *            发布平台上的文件路径
	 * @throws Exception
	 */
	public void delete(final Long ftpLogId, final Long siteId, final String siteResourceType, String ftpFilePath, final String localFilePath)
	        throws Exception;
	
	/**
	 * 删除路径path下的所有文件
	 * 
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            站点资源类型
	 * @param filePath
	 *            ftp上的文件路径
	 * @param localFilePath
	 *            发布平台上的文件路径
	 */
	public void deleteByPath(final Long siteId, final String siteResourceType, String filePath, final String localFilePath);
	
	/**
	 * 检测该ftp用户是否能连接上服务器
	 * 
	 * @return true表示能连接上服务器,false表示不能连接上服务器
	 */
	public boolean checkFtpConnect();
}
