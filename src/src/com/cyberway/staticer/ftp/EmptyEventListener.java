package com.cyberway.staticer.ftp;

import java.io.File;
import java.util.Date;

import com.cyberway.cms.distributionlog.service.DistributionLogService;
import com.cyberway.cms.domain.CmsDistributionLog;
import com.cyberway.core.utils.ServiceLocator;

public class EmptyEventListener implements EventListener {
	DistributionLogService	distributionLogService	= (DistributionLogService) ServiceLocator.getBean("distributionLogService");

	public void onUploadSuccessed(String fileName, byte[] content) {
	}

	public void onUploadFailed(String fileName, byte[] content) {

	}

	public void onDeleteSuccessed(String fileName) {

	}

	public void onDeleteFailed(String fileName) {

	}

	public void onUploadSuccessed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, File file) {
		CmsDistributionLog distributionLog = null;
		if (ftpLogId == null) {
			distributionLog = new CmsDistributionLog();
			distributionLog.setFtpId(ftpId);
			distributionLog.setStatus("分发成功");
			distributionLog.setLastTime(new Date());
			distributionLog.setName(file.getName());
			distributionLog.setPath(file.getPath());
			distributionLog.setFtpPath(ftpFilePath);
			distributionLog.setSiteId(siteId);
			distributionLog.setType(siteResourceType);
		} else {
			distributionLog = distributionLogService.get(ftpLogId);
			distributionLog.setLastTime(new Date());
			distributionLog.setStatus("分发成功");
		}
		distributionLogService.save(distributionLog);
	}

	public void onUploadFailed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, File file) {
		CmsDistributionLog distributionLog = null;
		if (ftpLogId == null) {
			distributionLog = new CmsDistributionLog();
			distributionLog.setFtpId(ftpId);
			distributionLog.setStatus("分发失败");
			distributionLog.setLastTime(new Date());
			distributionLog.setName(file.getName());
			distributionLog.setPath(file.getPath());
			distributionLog.setFtpPath(ftpFilePath);
			distributionLog.setSiteId(siteId);
			distributionLog.setType(siteResourceType);
		} else {
			distributionLog = distributionLogService.get(ftpLogId);
			distributionLog.setLastTime(new Date());
			distributionLog.setStatus("分发失败");
		}
		distributionLogService.save(distributionLog);
	}

	public void onDeleteSuccessed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, String localFilePath) {
		CmsDistributionLog distributionLog = null;
		if (ftpLogId == null) {
			distributionLog = new CmsDistributionLog();
			distributionLog.setFtpId(ftpId);
			distributionLog.setStatus("撤销分发成功");
			distributionLog.setLastTime(new Date());
			String name = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1, ftpFilePath.length());
			distributionLog.setName(name);
			distributionLog.setPath(localFilePath);
			distributionLog.setFtpPath(ftpFilePath);
			distributionLog.setSiteId(siteId);
			distributionLog.setType(siteResourceType);
		} else {
			distributionLog = distributionLogService.get(ftpLogId);
			distributionLog.setLastTime(new Date());
			distributionLog.setStatus("撤销分发成功");
		}
		distributionLogService.save(distributionLog);
	}

	public void onDeleteFailed(Long ftpLogId, Long ftpId, Long siteId, String siteResourceType, String ftpFilePath, String localFilePath) {
		CmsDistributionLog distributionLog = null;
		if (ftpLogId == null) {
			distributionLog = new CmsDistributionLog();
			distributionLog.setFtpId(ftpId);
			distributionLog.setStatus("撤销分发失败");
			distributionLog.setLastTime(new Date());
			String name = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1, ftpFilePath.length());
			distributionLog.setName(name);
			distributionLog.setPath(localFilePath);
			distributionLog.setFtpPath(ftpFilePath);
			distributionLog.setSiteId(siteId);
			distributionLog.setType(siteResourceType);
		} else {
			distributionLog = distributionLogService.get(ftpLogId);
			distributionLog.setLastTime(new Date());
			distributionLog.setStatus("撤销分发失败");
		}
		distributionLogService.save(distributionLog);
	}
}
