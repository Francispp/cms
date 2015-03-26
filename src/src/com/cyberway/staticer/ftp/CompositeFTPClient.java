package com.cyberway.staticer.ftp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.cyberway.cms.Constants;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.common.ftpservice.service.ThreeDES;
import com.cyberway.core.utils.ServiceLocator;

/**
 * 复合的FTPClient，用于透明的同时对多个FTP服务端进行文件的上传与删除
 * 
 * @author helfen
 * 
 */
public class CompositeFTPClient implements FTPClient {
	private List<FTPClient>	_ftpClients	= new ArrayList<FTPClient>();

	public CompositeFTPClient() {
		super();
	}

	public CompositeFTPClient(Long siteId, String siteResourceType) {
		FtpServiceService ftpServiceService = (FtpServiceService) ServiceLocator.getBean("ftpServiceService");
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

			FTPClientImpl ftpService = new FTPClientImpl(ftpConfig.getId(), ftpConfig.getFtpIp(), ftpConfig.getPort(),
			        ftpConfig.getUserName(), ftpPassWord, ftpConfig.getIsFtp().equals("2") ? true : false);
			_ftpClients.add(ftpService);
		}
	}

	public void setFtpClients(List<FTPClient> ftpClients) {
		_ftpClients = ftpClients;
	}

	public void upload(final String fileName, final byte[] content, final boolean overwrite) {
		for (final FTPClient ftpClient : _ftpClients) {
			new Thread(new Runnable() {

				public void run() {
					ftpClient.upload(fileName, content, overwrite);
				}
			}).run();
		}
	}

	public void upload(final String fileName, final byte[] content) {
		for (final FTPClient ftpClient : _ftpClients) {
			new Thread(new Runnable() {

				public void run() {
					ftpClient.upload(fileName, content);
				}
			}).run();
		}
	}

	public void delete(final String fileName) {
		for (final FTPClient ftpClient : _ftpClients) {
			new Thread(new Runnable() {

				public void run() {
					ftpClient.delete(fileName);
				}
			}).run();
		}
	}

	public List<String> getList(String filePath) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteByPath(String filePath) {
		// TODO Auto-generated method stub

	}
}
