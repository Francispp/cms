package com.cyberway.staticer.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.cyberway.cms.Constants;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.common.ftpservice.service.ThreeDES;
import com.cyberway.core.utils.ServiceLocator;

/**
 * com.cyberway.staticer.ftp.SiteCompositeFTPClient
 * 
 * @author Janice Yang
 * 
 * @createTime 2011-12-27 下午3:24:52
 * 
 * @Description:复合的SiteFTPClient，用于透明的同时对多个FTP服务端进行文件的上传与删除
 * 
 */
public class SiteCompositeFTPClient implements SiteFTPClient {
	private static Logger log_ = Logger.getLogger(SiteCompositeFTPClient.class);
	
	List<SiteFTPClient>	       _ftpClients	        = new ArrayList<SiteFTPClient>();

	FtpServiceService	       ftpServiceService	= (FtpServiceService) ServiceLocator.getBean("ftpServiceService");

	/**
	 * 线程池执行者
	 */
	private ThreadPoolExecutor	_threadPoolExecutor	= (ThreadPoolExecutor) ServiceLocator.getBean("cms.FTPClient.threadPoolExecutor");

	public SiteCompositeFTPClient() {
		super();
	}

	public SiteCompositeFTPClient(Long siteId, String siteResourceType) {
		if (Constants.CMS_FTP_ENABLED.equals("true")) {
			List<CoreSiteDistribution> ftpConfigList = ftpServiceService.getDistributionBySiteAndResource(siteId, siteResourceType);
			Validate.notEmpty(ftpConfigList, "该资源类型还没有选择需要分发的ftp服务器!");
			for (CoreSiteDistribution ftpConfig : ftpConfigList) {
				// 解密
				String ftpPassWord = ftpConfig.getPassWord();
				if (StringUtils.isNotBlank(ftpPassWord)) {
					ThreeDES des = new ThreeDES();
					des.getKey(Constants.CMS_FTP_ACTISECRETKEY);
					ftpPassWord = des.getDesString(ftpPassWord);
				}

				SiteFTPClient ftpService = new SiteFTPClientImpl(ftpConfig.getId(), ftpConfig.getFtpIp(), ftpConfig.getPort(),
				        ftpConfig.getUserName(), ftpPassWord, ftpConfig.getIsFtp().equals("2") ? true : false);
				_ftpClients.add(ftpService);
			}
		} else {
			log_.error("=========================FTP操作的开关被关掉了,不可以执行FTP相关的操作!=========================");
		}
	}

	/**
	 * 多线程删除文件时会关错连接，连接断开后的读和写操作引起Connect reset异常
	 * 因此一个接着一个删除
	 * @author Dicky
	 * @time 2012-9-25 15:09:06
	 * @version 1.0
	 * @param siteId
	 * @param siteResourceType
	 * @param fptFileName
	 * @param file
	 * @param overWrite
	 */
	public void upload(final Long siteId, final String siteResourceType, final String fptFileName, final File file, final boolean overWrite) {
		for (final SiteFTPClient ftpClient : _ftpClients) {
			try {
				ftpClient.upload(siteId, siteResourceType, fptFileName, file, overWrite);
			} catch (Exception e) {
				log_.error("-上传失败->fptFileName->\n"+fptFileName,e);
			}
		}
	}

	/**
	 * 多线程删除文件时会关错连接，连接断开后的读和写操作引起Connect reset异常
	 * 因此一个接着一个删除
	 * @author Dicky
	 * @time 2012-9-25 15:12:37
	 * @version 1.0
	 * @param siteId
	 * @param siteResourceType
	 * @param fptFileName
	 * @param file
	 */
	public void upload(final Long siteId, final String siteResourceType, final String fptFileName, final File file) {
		for (SiteFTPClient ftpClient : _ftpClients) {
			try {
				ftpClient.upload(siteId, siteResourceType, fptFileName, file);
			} catch (Exception e) {
				log_.error("-上传失败->fptFileName->\n"+fptFileName,e);
			}
		}
	}

	public void delete(final Long siteId, final String siteResourceType, final String ftpFilePath, final String localFilePath) {
		for (final SiteFTPClient ftpClient : _ftpClients) {
			_threadPoolExecutor.execute(new Runnable() {
				public void run() {
					try {
						ftpClient.delete(siteId, siteResourceType, ftpFilePath, localFilePath);
					} catch (Exception e) {
						log_.error("-删除失败->ftpFilePath->\n\r"+ftpFilePath,e);
					}
				}
			});
		}
	}

	public void deleteByPath(final Long siteId, final String siteResourceType, final String filePath, final String localFilePath) {
		for (final SiteFTPClient ftpClient : _ftpClients) {
			_threadPoolExecutor.execute(new Runnable() {
				public void run() {
					try {
						ftpClient.deleteByPath(siteId, siteResourceType, filePath, localFilePath);
					} catch (Exception e) {
						//log_.error("-删除失败->ftpFilePath->\n\r"+filePath,e);
					}
				}
			});
		}
	}

	public void upload(Long ftpLogId, Long siteId, String siteResourceType, String fileName, File file) {
	}

	public void upload(Long ftpLogId, Long siteId, String siteResourceType, String ftpFilePath, File file, boolean overwrite) {
	}

	public void delete(Long ftpLogId, Long siteId, String siteResourceType, String ftpFilePath, String localFilePath) {
	}

	public boolean checkFtpConnect() {
		return false;
	}

}
