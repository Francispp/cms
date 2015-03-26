package com.cyberway.common.distribution.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.cyberway.cms.Constants;
import com.cyberway.cms.distributionlog.service.DistributionLogService;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.StaticResource;
import com.cyberway.cms.siteDistribution.view.SiteDistributionController;
import com.cyberway.cms.staticResource.service.StaticResourceService;
import com.cyberway.common.attachment.domain.Attachment;
import com.cyberway.common.attachment.service.AttachmentManagerService;
import com.cyberway.common.domain.CoreSiteDistribution;
import com.cyberway.common.ftpservice.service.FtpServiceService;
import com.cyberway.common.ftpservice.service.ThreeDES;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.audio.domain.Audio;
import com.cyberway.common.media.video.domain.Video;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.staticer.Configuration;
import com.cyberway.staticer.cache.file.FileCache;
import com.cyberway.staticer.ftp.SiteCompositeFTPClient;
import com.cyberway.staticer.ftp.SiteFTPClient;
import com.cyberway.staticer.ftp.SiteFTPClientImpl;
/**
 * 分发到ftp接口
 * 
 * @author taoz
 * 
 *         2011-11-3下午02:57:37
 */
public class DistributionService extends HibernateEntityDao {
	FtpServiceService	   ftpServiceService	   = (FtpServiceService) ServiceLocator.getBean("ftpServiceService");
	DistributionLogService	distributionLogService	= (DistributionLogService) ServiceLocator.getBean("distributionLogService");
	
	/**
	 * 结构tree
	 * 
	 * @param f
	 * @return
	 */
	public String getMenuXmls(String filePath) {

		DHtmlXTree tree = new DHtmlXTree();
		// 根ID
		tree.setTreeId("0");

		// 第一个节点
		Node node = tree.initNode();
		node.setId("T");
		node.setText("ftp");

		List ls = new ArrayList();
		File file = new File(filePath);
		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			Node fnode = tree.initNode();
			// 文件夹名字
			fnode.setId("M_" + i);
			fnode.setText(files[i].getName());
			getFileName(files[i], tree, fnode);
			ls.add(fnode);
		}

		node.setSubNodeList(ls);

		tree.addNode(node);
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 得到文件名 递归调用
	 * 
	 * @param filePath
	 * @return
	 */
	public void getFileName(File file, DHtmlXTree tree, Node pNode) {
		File[] files = file.listFiles();
		List list = new ArrayList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				Node node = tree.initNode();
				node.setId("X_" + i);
				// 文件夹名字
				node.setText(files[i].getName());
				list.add(node);
				getFileName(files[i], tree, node);
				// list.add(pNode);
			}
		}
		pNode.setSubNodeList(list);
	}

	/**
	 * 上传到ftp
	 * 
	 * @param strFileName
	 * @throws IOException
	 */
	public void uploadByFtp(String cmsPath, String filePath, String strFileName, boolean overwrite, Long siteId) throws IOException {
		String pathAll = Configuration.servletContext().getRealPath("");
		String pathFall = pathAll + cmsPath;
		String fileName = strFileName.substring(pathFall.length() - 1, strFileName.length());
		String defaultRoot = Constants.DEFAULT_ROOT;
		String ftpPath = defaultRoot + filePath + fileName;
		File file = new File(strFileName);
		if (file.exists()) {
			try {
				String resourceType = searchResourceType(ftpPath);// 资源类型
				if (resourceType != null) {
					SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
					siteFTPClient.upload(siteId, resourceType, ftpPath, new File(strFileName), overwrite);
				} else {
					logger.info("=================没有找到对应的资源类型,无法分发!======================");
				}
			} catch (Exception e) {
				logger.info("-上传失败-",e);
			}
			//System.out.println("上传成功" + "------" + "本地url" + strFileName + "运行平台url" + ftpPath);
		} else {
			logger.info("文件不存在" + "------" + "本地url" + strFileName);
		}
	}
	
	/**
	 * 根据文件路径,分析该路径是属于哪种资源类型
	 * 
	 * @param path
	 *            待分析的文件路径
	 * @return 资源类型,如果没有对应的资源将返回null
	 */
	public String searchResourceType(String path) {
		String resourceType = null;// 资源类型
		if (path.indexOf(Constants.WORD_CENTER_PATH) > 0) {// word文档资源
			resourceType = SiteDistributionController.WORD_RESOURCE;
		} else if (path.indexOf(Constants.STATIC_PATH) > 0) {// 静态资源
			resourceType = SiteDistributionController.STATIC_RESOURCE;
		} else if (path.indexOf(Constants.OTHER_CENTER_PATH) > 0) {// 其他资源
			resourceType = SiteDistributionController.OTHER_RESOURCE;
		} else if (path.indexOf(Constants.MEDIA_PATH) > 0) {// 流媒体
			resourceType = SiteDistributionController.MEDIA_RESOURCE;
		} else if (path.indexOf(Constants.LUCENE_PATH_FTP) > 0) {// Lucene资源
			resourceType = SiteDistributionController.LUCENE_RESOURCE;
		} else if (path.indexOf(Constants.MOVEMEN_PATH) > 0) {// 动态jsp资源
			resourceType = SiteDistributionController.JSP_RESOURCE;
		} else if (path.indexOf(Constants.HTML_CENTER_PATH) > 0) {// 静态html
			resourceType = SiteDistributionController.HTML_RESOURCE;
		}

		return resourceType;
	}

	/**
	 * 增量分发FLV
	 */
	public void addFlv(List<MediaIntermediate> mediaIntermediates, Long siteId) throws IOException {
		String filePath="";
		for (MediaIntermediate mediaIntermediate:mediaIntermediates) {
			if(StringUtil.ifEqual(mediaIntermediate.getType(),"video")){
				Video _video=(Video)get(Video.class, mediaIntermediate.getMediaId());
				if(_video!=null){
					filePath=_video.getFilePath();
				}
			}else{
				Audio _audio=(Audio)get(Audio.class, mediaIntermediate.getMediaId());
				if(_audio!=null){
					filePath=_audio.getFilePath();
				}
			}
			uploadByFtpflv(filePath, Constants.UPLOADS_MEDIA_PATH+filePath,"",false,siteId);
		}
	}
	/**
	 * 撤销分发FLV
	 */
	public void removeFlv(MediaIntermediate mediaIntermediate) throws IOException {
		String filePath="";
		if(StringUtil.ifEqual(mediaIntermediate.getType(),"video")){
			Video _video=(Video)get(Video.class, mediaIntermediate.getMediaId());
			if(_video!=null){
				filePath=_video.getFilePath();
			}
		}else{
			Audio _audio=(Audio)get(Audio.class, mediaIntermediate.getMediaId());
			if(_audio!=null){
				filePath=_audio.getFilePath();
			}
		}
		deleteInFtpflv(filePath,null);
	
	}
	
	/**
	 * 分发flv文件到服务器
	 * 
	 * @param strFileName
	 * @throws IOException
	 */
	public void uploadByFtpflv(String strFileName, String filePath, String oldFileNmae, boolean overwrite,Long siteId) throws IOException {
		if (!StringUtil.isEmpty(oldFileNmae)) {
			deleteInFtpflv(oldFileNmae,siteId);
		}
		String pathAll = Configuration.servletContext().getRealPath("");
		String path = pathAll + filePath;
		String resourceType = SiteDistributionController.MEDIA_RESOURCE;// 资源类型
		if (resourceType != null) {
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			try {
				siteFTPClient.upload(siteId, resourceType, File.separator+strFileName, new File(path), overwrite);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("-上传媒体文件失败-",e);
			}
			System.out.println("flv上传成功" + "------" + "本地url" + strFileName + "运行平台url" + strFileName);
		} else {
			System.out.println("=================没有找到对应的资源类型,无法分发!======================");
		}
	}

	/**
	 * 删除ftp上文件
	 */
	public void deleteInFtpflv(String fileName,Long siteId) {
		String resourceType = SiteDistributionController.MEDIA_RESOURCE;// 资源类型
		if (resourceType != null) {
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			try {
				siteFTPClient.delete(siteId, resourceType, fileName, "");
			} catch (Exception e) {
				logger.error("-删除媒体文件失败-",e);
			}
			//System.out.println("删除成功" + "--------" + "删除url" + fileName);
		} else {
			System.out.println("=================没有找到对应的资源类型,无法分发!======================");
		}
	}


	/**
	 * 文档上传递归调用
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void getWordCenter(String cmsPath, String wordFtpPath, String filePath, boolean overwrite, Long siteId) throws IOException {
		File file = new File(filePath);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] == null) {
					logger.info("发布平台文件夹不存在");
					return;
				}
				if (files[i].isDirectory()) {
					if (!files[i].getName().endsWith(".svn")) {
						getWordCenter(cmsPath, wordFtpPath, files[i].getPath(), overwrite, siteId);
					}
				} else {
					if (!files[i].getName().endsWith(".doc") && !files[i].getName().endsWith(".docx")) {
						uploadByFtp(cmsPath, wordFtpPath, files[i].getPath(), overwrite, siteId);
					}

				}
			}
		}
	}

	/**
	 * 静态资源选择性分发
	 */
	public void getStaticResource(List list, Long siteId) throws Exception {
		String pathAll = Configuration.servletContext().getRealPath("");
		String cmsPath = Constants.STATICRESOURCE_PATH;
		String staticPath = Constants.STATIC_PATH;
		String defaultRoot = Constants.DEFAULT_ROOT;

		String resourceType = searchResourceType(defaultRoot + staticPath);// 资源类型
		if (resourceType != null) {
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);

			for (int i = 0; i < list.size(); i++) {
				String ftpPathLast = list.get(i).toString().substring(cmsPath.length() - 1, list.get(i).toString().length());
				try {
					siteFTPClient.upload(siteId, resourceType, defaultRoot + staticPath + ftpPathLast, new File(pathAll
					        + list.get(i).toString()), true);
				} catch (Exception e) {
					logger.error("上传失败",e);
				}
				System.out.println("上传成功!本地url" + pathAll + list.get(i).toString() + "--ftp路径:" + defaultRoot + staticPath + ftpPathLast);
			}
		} else {
			logger.info("=================没有找到对应的资源类型,无法分发!======================");
		}
	}

	
	/**
	 * 删除ftp静态资源
	 */
	public void deleteCenterById(List list, Long siteId) throws IOException {
		String rootPath = Configuration.servletContext().getRealPath("");
		StaticResourceService StaticResourceService = (StaticResourceService) ServiceLocator.getBean("staticResourceService");
		for (int i = 0; i < list.size(); i++) {
			Long id = (Long) list.get(i);
			StaticResource sr = (StaticResource) StaticResourceService.get(id);
			try {
				String resourceType = searchResourceType(Constants.DEFAULT_ROOT + sr.getServerpath());// 资源类型
				if(resourceType != null){
					SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
					siteFTPClient.delete(siteId, resourceType, Constants.DEFAULT_ROOT + sr.getServerpath(), rootPath + sr.getServerpath());
				}else{
					System.out.println("=================没有找到对应的资源类型,无法删除!======================");
				}
				
				//centerDistribution.delete(centerDistribution.getDefaultRoot()+sr.getServerpath());
			} catch (Exception e) {
				logger.error("-删除静态资源失败-", e);
				e.printStackTrace();
			}
			System.out.println("删除成功" + "--------" + "删除url" + Constants.DEFAULT_ROOT + sr.getServerpath());
		}
		
	}

	/***
	 * office 文档分发
	 * @param siteId
	 * @param docId
	 * @throws IOException
	 */
	public void getOfficeWordById(Long siteId, Long docId) throws IOException {
		String wordCenterPath = Constants.WORD_CENTER_PATH;
		String pathAll = Configuration.servletContext().getRealPath("");
		String cmsPath = Constants.INFO_OFFICE_PATH;
		String filePath = pathAll + cmsPath + siteId + File.separator + docId;
		getWordCenter(cmsPath, wordCenterPath, filePath, true, siteId);
	}

	/**
	 * 删除office ftp文档 DOCID
	 */
	public void deleteOfficeWordById(Long siteId, Long docId) throws IOException {
		String ftpPath=Constants.DEFAULT_ROOT + Constants.WORD_CENTER_PATH;
		String resourceType = searchResourceType(ftpPath+siteId+File.separator+docId);// 资源类型
		if(resourceType != null){
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			siteFTPClient.deleteByPath(siteId, resourceType, ftpPath+siteId+File.separator+docId, "");
		}else{
			//System.out.println("=================没有找到对应的资源类型,无法删除!======================");
		}
	}

	
	/**
	 * 增量分发附件
	 */
	public void getAttachment(Long siteId, Long docId) throws IOException {
		AttachmentManagerService attachmentManagerService = (AttachmentManagerService) ServiceLocator.getBean("attachmentManagerService");
		List<Attachment> attachmentList = attachmentManagerService.findBy("documentId", docId);
		String defaultRoot = Constants.DEFAULT_ROOT;
		
		String pathAll = Configuration.servletContext().getRealPath("");
		for (int i = 0; i < attachmentList.size(); i++) {
			String filePath = pathAll + attachmentList.get(i).getFilePath();
			File file = new File(filePath);
			if (file.exists()) {
				String ftpPath = defaultRoot + attachmentList.get(i).getFilePath();
				try {
					String resourceType = searchResourceType(ftpPath);// 资源类型
					if(resourceType != null){
						SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
						siteFTPClient.upload(siteId, resourceType, ftpPath, new File(filePath), false);
					}else{
						logger.error("=================没有找到对应的资源类型,无法分发!======================");
					}
				} catch (Exception e) {
					logger.error("-上传是失败！-",e);
				}
				//System.out.println("上传成功" + "------" + "本地url" + filePath + "运行平台url" + ftpPath);
			} else {
				logger.info("文件不存在" + "------" + "cmsURL" + filePath);
			}
		}
	}
	
	/**
	 * 发送单个附件
	 * @author Dicky
	 * @time 2012-11-2 16:22:57
	 * @version 1.0
	 * @param siteId
	 * @param docId
	 * @throws IOException
	 */
	public void sendAttachment(Long siteId, Attachment att) throws IOException {
		String defaultRoot = Constants.DEFAULT_ROOT;
		String pathAll = Configuration.servletContext().getRealPath("");
		String filePath = pathAll + att.getFilePath();
		File file = new File(filePath);
		if (file.exists()) {
			String ftpPath = defaultRoot + att.getFilePath();
			try {
				String resourceType = searchResourceType(ftpPath);// 资源类型
				if(resourceType != null){
					SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
					siteFTPClient.upload(siteId, resourceType, ftpPath, new File(filePath), false);
				}else{
					logger.error("=================没有找到对应的资源类型,无法分发!======================");
				}
			} catch (Exception e) {
				logger.error("-上传是失败！-",e);
			}
		} else {
			logger.info("文件不存在" + "------" + "cmsURL" + filePath);
		}
	}
	
	/**
	 * delete附件
	 */
	public void deleteAnnexById(Long siteId, Long docId) throws IOException {
		//String cmsAttachmentPath =centerDistribution.getDefaultRoot()+centerDistribution.getAttachmentpath();
		String cmsAttachmentPath = Constants.DEFAULT_ROOT + Constants.ATTACHMENT_PATH;
		
		String resourceType = searchResourceType(cmsAttachmentPath+File.separator+siteId+File.separator+docId);// 资源类型
		if(resourceType != null){
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			siteFTPClient.deleteByPath(siteId, resourceType, cmsAttachmentPath+File.separator+siteId+File.separator+docId, "");
		}else{
			System.out.println("=================没有找到对应的资源类型,无法删除!======================");
		}
		
		//centerDistribution.deleteByPath(cmsAttachmentPath+File.separator+siteId+File.separator+docId);
	}
	
	/**
	 * 其他资源增量分发
	 */
	public void getOther(Long siteId, Long docId) throws IOException {
		String otherCenterPath = Constants.OTHER_CENTER_PATH;
		String pathAll = Configuration.servletContext().getRealPath("");
		String cmsPath = Constants.UPLOADS_PATH;
		File file = new File(pathAll + cmsPath);
		File[] files = file.listFiles();
		String cmsf = "";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				if (!files[i].getName().equals(".svn")) {
					cmsf = files[i].getName();
					String filePath = pathAll + cmsPath + File.separator + cmsf + File.separator + siteId + File.separator + docId;
					getWordCenter(cmsPath, otherCenterPath, filePath, false, siteId);
				}
			}
		}
	}

	/**
	 * 删其他资源增量分发
	 */
	public void deleteOther(Long siteId, Long docId) throws IOException {
		//String ftpPath1=centerDistribution.getDefaultRoot()+centerDistribution.getAttachmentpath();
		//String ftpPath2=centerDistribution.getDefaultRoot()+centerDistribution.getOtherFlashPath();
		//String ftpPath3=centerDistribution.getDefaultRoot()+centerDistribution.getOtherImagePath();
		
		String ftpPath1=Constants.DEFAULT_ROOT + Constants.ATTACHMENT_PATH;
		String ftpPath2=Constants.DEFAULT_ROOT + Constants.OTHER_FLASH_PATH;
		String ftpPath3=Constants.DEFAULT_ROOT + Constants.OTHER_IMAGE_PATH;

		String resourceType = searchResourceType(ftpPath1+siteId+File.separator+docId);// 资源类型
		if(resourceType != null){
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			siteFTPClient.deleteByPath(siteId, resourceType, ftpPath1+siteId+File.separator+docId, "");
			siteFTPClient.deleteByPath(siteId, resourceType, ftpPath2+siteId+File.separator+docId, "");
			siteFTPClient.deleteByPath(siteId, resourceType, ftpPath3+siteId+File.separator+docId, "");
		}else{
			System.out.println("=================没有找到对应的资源类型,无法删除!======================");
		}
		
		//centerDistribution.deleteByPath(ftpPath1+siteId+File.separator+docId);
		//centerDistribution.deleteByPath(ftpPath2+siteId+File.separator+docId);
		//centerDistribution.deleteByPath(ftpPath3+siteId+File.separator+docId);
	}

	
	/**
	 * 将站点的所有静态资源发送到ftp上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendStaticResource(Long siteId, String resourceType, boolean flag) throws Exception {
		// 运行平台中的静态资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.STATIC_PATH + File.separator + siteId + File.separator;
		// 发布平台静态资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.STATICRESOURCE_PATH + siteId;

		uploadByPath(siteId, resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将站点的所有动态JSP资源发送到ftp上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendJspResource(Long siteId, String resourceType, boolean flag) throws Exception {
		// 运行平台中的jsp动态资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.MOVEMEN_PATH + File.separator + siteId + File.separator;
		// 发布平台静态资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.TEMPLATE_STATICHTML_PATH + siteId;

		uploadByPath(siteId, resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将站点的所有word文档资源发送到ftp上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendOfficeResource(Long siteId, String resourceType, boolean flag) throws Exception {
		// 运行平台中的doc文档资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.WORD_CENTER_PATH + File.separator + siteId + File.separator;
		// 发布平台doc文档资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.INFO_OFFICE_PATH + siteId;

		uploadWordByPath(siteId, resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将站点的所有静态html文件资源发送到ftp上
	 * 
	 * @param site
	 *            站点
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendHtmlResource(CmsSite site, String resourceType, boolean flag) throws Exception {
		FileCache fc = (FileCache) ServiceLocator.getBean("cms.staticer.cache");

		// 运行平台中的静态html资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.HTML_CENTER_PATH + File.separator + site.getSitehttp() + File.separator;
		// 发布平台静态html资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + fc.get_storePath() + site.getSitehttp();

		uploadByPath(site.getOid(), resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将站点的所有其他资源发送到ftp上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendOtherResource(Long siteId, String resourceType, boolean flag) throws Exception {
		// 运行平台中的其他资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.OTHER_CENTER_PATH + File.separator;
		// 发布平台其他资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.UPLOADS_PATH;

		uploadByPath(siteId, resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将站点的所有Media资源发送到ftp上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendMediaResource(Long siteId, String resourceType, boolean flag) throws Exception {
		// 运行平台中的Media资源存放目录
		//String ftpPath = Constants.DEFAULT_ROOT + Constants.MEDIA_PATH + File.separator;
		String ftpPath = File.separator;
		// 发布平台Media资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.UPLOADS_MEDIA_PATH;

		uploadByPath(siteId, resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将站点的所有Lucene资源发送到ftp上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param flag
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void sendLuceneResource(Long siteId, String resourceType, boolean flag) throws Exception {
		// 运行平台中的Lucene资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.LUCENE_PATH_FTP + File.separator;
		// 发布平台Lucene资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.LUCENE_PATH;

		uploadByPath(siteId, resourceType, ftpPath, filePath, flag);
	}

	/**
	 * 将指定的文件路径下的所有文件进行分发
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param ftpPath
	 *            需要通过ftp上传到运行平台中的目录
	 * @param filePath
	 *            被上传的文件路径
	 * @param overwrite
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void uploadByPath(Long siteId, String resourceType, String ftpPath, String filePath, boolean overwrite) throws Exception {
		File file = new File(filePath);
		File[] files = file.listFiles();
		if (!ArrayUtils.isEmpty(files)) {
			SiteFTPClient siteCompositeFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					if (!files[i].getName().endsWith(".svn")) {
						uploadByPath(siteId, resourceType, ftpPath + files[i].getName() + File.separator, files[i].getPath(), overwrite);
					}
				} else {
					uploadByFtp(siteCompositeFTPClient, siteId, resourceType, ftpPath, files[i], overwrite);
				}
			}
		}
	}

	/**
	 * 分发word文档资源时用到,将指定的文件路径下的所有文件(除了word文件)进行分发
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param ftpPath
	 *            需要通过ftp上传到运行平台中的目录
	 * @param filePath
	 *            被上传的文件路径
	 * @param overwrite
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void uploadWordByPath(Long siteId, String resourceType, String ftpPath, String filePath, boolean overwrite) throws Exception {
		File file = new File(filePath);
		File[] files = file.listFiles();
		if (!ArrayUtils.isEmpty(files)) {
			SiteFTPClient siteCompositeFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					if (!files[i].getName().endsWith(".svn")) {
						uploadWordByPath(siteId, resourceType, ftpPath + files[i].getName() + File.separator, files[i].getPath(), overwrite);
					}
				} else {
					if (!files[i].getName().endsWith(".doc") && !files[i].getName().endsWith(".docx")) {
						uploadByFtp(siteCompositeFTPClient, siteId, resourceType, ftpPath, files[i], overwrite);
					}
				}
			}
		}
	}

	/**
	 * 将单个文件通过ftp发送到站点运行平台上
	 * 
	 * @param siteCompositeFTPClient
	 * 			  ftp客户端
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param ftpPath
	 *            需要通过ftp上传到运行平台中的目录
	 * @param file
	 *            被上传的文件
	 * @param overwrite
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void uploadByFtp(SiteFTPClient siteCompositeFTPClient, Long siteId, String resourceType, String ftpPath, File file, boolean overwrite) throws Exception {
		if (file.exists()) {
			siteCompositeFTPClient.upload(siteId, resourceType, ftpPath + file.getName(), file, overwrite);
		} else {
			logger.info("文件不存在" + "------" + "本地url" + file.getPath());
		}
	}

	/**
	 * 将单个文件通过ftp发送到站点运行平台上
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源)
	 * @param ftpFilePath
	 *            需要通过ftp上传到运行平台中的文件路径
	 * @param localFilePath
	 *            被上传的文件路径
	 * @param overwrite
	 *            标志:true为覆盖ftp上的同名文件,flag为不覆盖ftp上的同名文件
	 * @throws Exception
	 */
	public void uploadByFtp(Long ftpLogId, Long ftpId, Long siteId, String resourceType, String ftpFilePath, String localFilePath, boolean overwrite) throws Exception {
		CoreSiteDistribution ftpService = ftpServiceService.getSiteDistributionByKey(siteId, resourceType, ftpId);
		Validate.notNull(ftpService, "没有该ftp服务器的信息");
		
		//解密
		String ftpPassWord = ftpService.getPassWord();
		if(StringUtils.isNotBlank(ftpPassWord)){
			ThreeDES des = new ThreeDES();
			des.getKey(Constants.CMS_FTP_ACTISECRETKEY);
			ftpPassWord = des.getDesString(ftpPassWord);
		}
		
		SiteFTPClient ftpClient = new SiteFTPClientImpl(ftpId, ftpService.getFtpIp(), ftpService.getPort(), ftpService.getUserName(), ftpPassWord, ftpService.getIsFtp().equals("2") ? true : false);
		File file = new File(localFilePath);
		if (file.exists()) {
			ftpClient.upload(ftpLogId, siteId, resourceType, ftpFilePath, file, true);
		} else {
			System.out.println("文件不存在" + "------" + "本地url" + file.getPath());
		}
	}

	/**
	 * 将ftp上的所有静态资源删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteStaticResource(Long siteId, String resourceType) throws Exception {
		// 运行平台中的静态资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.STATIC_PATH + File.separator + siteId + File.separator;
		// 发布平台静态资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.STATICRESOURCE_PATH + siteId;

		deleteByPath(siteId, resourceType, ftpPath, filePath);
	}

	/**
	 * 将ftp上的所有动态JSP资源删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteJspResource(Long siteId, String resourceType) throws Exception {
		// 运行平台中的jsp动态资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.MOVEMEN_PATH + File.separator + siteId + File.separator;
		// 发布平台静态资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.TEMPLATE_STATICHTML_PATH + siteId;

		deleteByPath(siteId, resourceType, ftpPath, filePath);
	}

	/**
	 * 将ftp上的所有word文档资源删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteOfficeResource(Long siteId, String resourceType) throws Exception {
		// 运行平台中的doc文档资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.WORD_CENTER_PATH + File.separator + siteId + File.separator;
		// 发布平台doc文档资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.INFO_OFFICE_PATH + siteId;

		deleteWordByPath(siteId, resourceType, ftpPath, filePath);
	}

	/**
	 * 将ftp上的所有静态html文件资源删除
	 * 
	 * @param site
	 *            站点
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteHtmlResource(CmsSite site, String resourceType) throws Exception {
		FileCache fc = (FileCache) ServiceLocator.getBean("cms.staticer.cache");

		// 运行平台中的静态html资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.HTML_CENTER_PATH + File.separator + site.getSitehttp() + File.separator;
		// 发布平台静态html资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + fc.get_storePath() + site.getSitehttp();

		deleteByPath(site.getOid(), resourceType, ftpPath, filePath);
	}

	/**
	 * 将ftp上的所有其他资源删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteOtherResource(Long siteId, String resourceType) throws Exception {
		// 运行平台中的其他资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.OTHER_CENTER_PATH + File.separator;
		// 发布平台其他资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.UPLOADS_PATH;

		deleteByPath(siteId, resourceType, ftpPath, filePath);
	}

	/**
	 * 将ftp上的所有Media资源删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteMediaResource(Long siteId, String resourceType) throws Exception {
		// 运行平台中的Media资源存放目录
		//String ftpPath = Constants.DEFAULT_ROOT + Constants.MEDIA_PATH + File.separator;
		String ftpPath = File.separator;
		// 发布平台Media资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.UPLOADS_MEDIA_PATH;

		deleteByPath(siteId, resourceType, ftpPath, filePath);
	}

	/**
	 * 将ftp上的所有Lucene资源删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @throws Exception
	 */
	public void deleteLuceneResource(Long siteId, String resourceType) throws Exception {
		// 运行平台中的Lucene资源存放目录
		String ftpPath = Constants.DEFAULT_ROOT + Constants.LUCENE_PATH_FTP + File.separator;
		// 发布平台Lucene资源存放的目录
		String filePath = Configuration.servletContext().getRealPath("") + Constants.LUCENE_PATH;

		deleteByPath(siteId, resourceType, ftpPath, filePath);
	}

	/**
	 * 指定ftp上的文件夹路径,将其删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param ftpPath
	 *            ftp上的文件路径
	 * @param localPath
	 *            发布平台上的文件路径
	 * @throws Exception
	 */
	public void deleteByPath(Long siteId, String resourceType, String ftpPath, String localPath) throws Exception {
		File file = new File(localPath);
		File[] files = file.listFiles();
		if (!ArrayUtils.isEmpty(files)) {
			SiteFTPClient siteCompositeFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					if (!files[i].getName().endsWith(".svn")) {
						deleteByPath(siteId, resourceType, ftpPath + files[i].getName() + File.separator, files[i].getPath());
					}
				} else {
					deleteFileByFtp(siteCompositeFTPClient, siteId, resourceType, ftpPath + files[i].getName(), files[i].getPath());
				}
			}
		}
	}

	/**
	 * 指定ftp上的文件夹路径,将其删除
	 * 
	 * @param siteId
	 *            站点id
	 * @param resourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param ftpPath
	 *            ftp上的文件路径
	 * @param localPath
	 *            发布平台上的文件路径
	 * @throws Exception
	 */
	public void deleteWordByPath(Long siteId, String resourceType, String ftpPath, String localPath) throws Exception {
		File file = new File(localPath);
		File[] files = file.listFiles();
		if (!ArrayUtils.isEmpty(files)) {
			SiteFTPClient siteCompositeFTPClient = new SiteCompositeFTPClient(siteId, resourceType);
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					if (!files[i].getName().endsWith(".svn")) {
						deleteWordByPath(siteId, resourceType, ftpPath + files[i].getName() + File.separator, files[i].getPath());
					}
				} else {
					if (!files[i].getName().endsWith(".doc") && !files[i].getName().endsWith(".docx")) {
						deleteFileByFtp(siteCompositeFTPClient, siteId, resourceType, ftpPath + File.separator + files[i].getName(), files[i].getPath());
					}
				}
			}
		}
	}

	/**
	 * 指定ftp上的文件路径,将其删除
	 * 
	 * @param siteCompositeFTPClient
	 * 			  ftp客户端	
	 * @param siteId
	 *            站点id
	 * @param siteResourceType
	 *            资源类型("static_resource"代表静态资源, "jsp_resource"代表站点jsp动态资源,
	 *            "word_resource"代表站点word文档资源, "html_resource"代表站点静态html文件资源,
	 *            "other_resource"代表站点其他资源, "lucene_resource"代表站点lucene资源,
	 *            "media_resource"代表站点media资源)
	 * @param ftpFilePath
	 *            ftp上的文件路径
	 * @param localFilePath
	 *            发布平台上的文件路径
	 * @throws Exception
	 */
	public void deleteFileByFtp(SiteFTPClient siteCompositeFTPClient, Long siteId, String siteResourceType, String ftpFilePath, String localFilePath) throws Exception {
		siteCompositeFTPClient.delete(siteId, siteResourceType, ftpFilePath, localFilePath);
	}
}
