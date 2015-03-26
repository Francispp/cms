package com.cyberway.staticer.ftp;

import java.io.File;

/**
 * FTP 事件监听器
 * 
 * @author helfen
 * 
 */
public interface EventListener {
	/**
	 * 上传成功
	 */
	void onUploadSuccessed(String fileName, byte[] content);

	/**
	 * 上传失败
	 */
	void onUploadFailed(String fileName, byte[] content);

	/**
	 * 删除成功
	 */
	void onDeleteSuccessed(String fileName);

	/**
	 * 删除失败
	 */
	void onDeleteFailed(String fileName);

	/**
	 * 上传成功
	 */
	void onUploadSuccessed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, File file);

	/**
	 * 上传失败
	 */
	void onUploadFailed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, File file);

	/**
	 * 删除成功
	 */
	void onDeleteSuccessed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, String localFilePath);

	/**
	 * 删除失败
	 */
	void onDeleteFailed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, String localFilePath);
}
