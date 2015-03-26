package com.cyberway.common.distribution.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.BaseBizController;

public class DistributionController extends BaseBizController {

	DistributionService service = (DistributionService) this
			.getServiceById("distributionService");
	private String _treeXml;

	@Override
	protected EntityDao getService() {
		return service;
	}

	public String execute() throws Exception {
		// 显示树型结构
		return "frame";

	}

	/**
	 * frame树型结构
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frameTree() throws Exception {
		return "frameTree";
	}

	/**
	 * 得到文件结构 用树形呈现
	 */
	public String tree() throws Exception {
		return "tree";
	}

	@SuppressWarnings("deprecation")
	public String addFile() {
		String path = getHttpServletRequest().getRealPath("");
		_treeXml = service.getMenuXmls("d:\\works");
		return HTMLXTREE_RESULT;
	}

	@SuppressWarnings("unchecked")
	public List getFileName(String filePath) {
		File file = new File(filePath);
		File[] files = file.listFiles();
		List list = new ArrayList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				getFileName(files[i].getPath());
				list.add(files[i].getName());
			} else {

			}
		}
		return list;
	}

	/**
	 * 右边列表
	 */
	public String rightList() {

		return "rightList";
	}

	/**
	 * 文件上传接口测试
	 * 
	 * @throws IOException
	 */
	public void uploadFTP() throws IOException {
		// service.getOfficeWordById(Long.valueOf("111030161934021"));
		// service.getCenterById(Long.valueOf(446),false);
		// service.deleteAnnexById(Long.valueOf("111208110004031"));
		// service.getOther();
		// service.getAnnexById(Long.valueOf(52),Long.valueOf(123456));
		// service.getAttachment();
		// service.getOther();
		// service.getOfficeWordById(Long.valueOf("446"),Long.valueOf(
		// "111030161934021"));
		FtpServiceService fs = (FtpServiceService) ServiceLocator
				.getBean("ftpServiceService");
		List<CoreSiteDistribution> list = fs.getAllSiteDistributionsFromCache();
		// OrgManagerService
		// orgManagerService=(OrgManagerService)ServiceLocator.
		// getBean("orgManagerService");
		// List<CoreOrg> list1=orgManagerService.getAllOrgsFromCache();
		// System.out.println(list1);
		//service.uploadByFtpflv("aa.flv","\\cms_file\\myVideo.flv","aa.flv",true
		// );
		// service.deleteInFtpflv("aa.flv");
	}

	public String getTreeXml() {
		return _treeXml;
	}

	public void setTreeXml(String _treeXml) {
		this._treeXml = _treeXml;
	}
}
