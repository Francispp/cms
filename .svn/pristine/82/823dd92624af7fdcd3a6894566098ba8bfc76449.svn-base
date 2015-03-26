package com.cyberway.common.media.video.view;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.ecside.util.RequestUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.common.attachment.service.AttachmentManagerService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.common.media.album.service.AlbumService;
import com.cyberway.common.media.album.service.MediaIntermediateService;
import com.cyberway.common.media.video.domain.Video;
import com.cyberway.common.media.video.service.VideoService;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.staticer.Configuration;

public class VideoController extends BaseBizController<Video> {
	MediaIntermediateService mediaIntermediateService=(MediaIntermediateService)this.getServiceById("mediaIntermediateService");
	AttachmentManagerService attachmentManagerService = (AttachmentManagerService) this
			.getServiceById("attachmentManagerService");

	VideoService service = (VideoService) this.getServiceById("videoService");
	AlbumService albumService = (AlbumService) this
			.getServiceById("albumService");
	private int pageIndex;
	private int pageSize;
	private String searchStr;
	private Long albumId;
	private String albumType;
	private String isdefault;
	private String mediaFilePath;// 媒体服务器文件播放
	private File[] _file;
	private File[] _fileImage;
	private String[] _fileContentType;

	private String[] _fileFileName;
	private String[] _fileImageFileName;
	private String fpath;
	private float maxSize;
	private Long docId;
	private String docName;
	private String ftype;//前台上传时、先自动取标签中设置的格式

	// WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued"
	// list="#{true:'是',false:'否'}" />
	// 改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer, String> trueOfFalseMap1;

	public String getAlbumType() {
		return albumType;
	}

	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}

	public String getMediaFilePath() {
		return Constants.UPLOADS_MEDIA_PATH;

	}

	public void setMediaFilePath(String mediaFilePath) {
		this.mediaFilePath = mediaFilePath;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	@Override
	protected VideoService getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		return listByAlbum();
	}

	protected void doListEntity(CriteriaSetup criteriaSetup, String tableId,
			int pageSize) throws Exception {
		int totalRows = RequestUtil.getTotalRowsFromRequest(
				getHttpServletRequest(), tableId);
		if (totalRows < 0) {
			totalRows = getService().getCount(criteriaSetup);
		}
		org.ecside.table.limit.Limit limit = RequestUtil.getLimit(
				getHttpServletRequest(), tableId, totalRows, 24);
		Page page = getService().findECPage(limit, criteriaSetup);
		this.setItems((List) page.getResult());
		RequestUtil.setTotalRows(getHttpServletRequest(), page.getTotalCount());
		setData(page);
	}

	public String add() throws Exception {
		super.edit();
		if (domain != null) {
			domain.setAlbumId(albumId);
		}
		return INPUT;
	}

	public String edit() throws Exception {
		return super.edit();
	}
	/**
	 * 保存视频
	 */
	public String saveOrUpdate() throws Exception {
		if (null != _file && _file.length > 0) {
			boolean bfag=upload();
			if(!bfag){
				return EDIT_RESULT;
			}
		}
		if (null != _fileImage && _fileImage.length > 0) {
			uploadImage();
		}
		if (domain.getId() == null) {
			Ognl.setValue("createTime", domain, new Date());
		}
		Loginer _loginer = getLoginer();
		if (_loginer != null) {
			Ognl.setValue("modifUserId", domain, _loginer.getUserid());
			Ognl.setValue("modifUserName", domain, _loginer.getUsername());
			Ognl.setValue("siteId", domain, _loginer.getSiteId());
		}
		super.saveOrUpdate();
		this.addActionMessage("保存成功！");
		return EDIT_RESULT;
	}

	/**
	 * 浏览专辑下的视频
	 * 
	 * Frank
	 * 
	 */
	public String listByAlbum() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> criterias = new ArrayList<Criterion>();
		if (isdefault != null && !StringUtil.isEmpty(isdefault)) {
			criterias.add(Restrictions.isNull("albumId"));
		} else if (albumId != null) {
			criteria.addFilter("albumId", albumId);
		}
		Long siteId = getLoginerSiteId();
		if (siteId != null) {
			criteria.addFilter("siteId", siteId);
		}
		criterias.add(Restrictions.isNotNull("filePath"));
		
		criteria.setAddCriterions(criterias);
		
		if(!StringUtil.isEmpty(searchStr)){
			criteria.setInCriterion(Restrictions.like("title","%"+searchStr+"%"));
		}
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}

	/**
	 * 浏览站点下的所有视频文件 
	 * 
	 * Frank
	 */
	public String listBySite() throws Exception {
		Long siteId = getLoginerSiteId();
		if (siteId != null) {
			CriteriaSetup criteria = new CriteriaSetup();
			List<Criterion> criterias = new ArrayList<Criterion>();
			criterias.add(Restrictions.eq("siteId", siteId));
			criterias.add(Restrictions.isNotNull("filePath"));
			//是默认专辑
			if(isdefault != null && !StringUtil.isEmpty(isdefault)){
				criterias.add(Restrictions.isNull("albumId"));
			}else if (albumId != null) {//普通专辑
				criterias.add(Restrictions.eq("albumId", albumId));
			}
			
			criteria.setAddCriterions(criterias);
			criteria.setInOrder(Order.desc("createTime"));
			
			int totalRows = RequestUtil.getTotalRowsFromRequest(
					getHttpServletRequest(), tableId);
			if (totalRows < 0) {
				totalRows = getService().getCount(criteria);
			}
			org.ecside.table.limit.Limit limit = RequestUtil.getLimit(
					getHttpServletRequest(), tableId, totalRows, 15);
			Page page = getService().findECPage(limit, criteria);
			this.setItems((List) page.getResult());
			RequestUtil.setTotalRows(getHttpServletRequest(), page.getTotalCount());
			setData(page);
			
		}
		return "list_site";
	}

	/**
	 * 视频下载
	 * 
	 * Frank
	 */
	public String fileDownload() throws Exception {
		if (id != null) {
			get();
			File file;
			if (Constants.IS_REALPATH) {
				file = new File(Constants.ABSOLUTE_PATH
						+ Constants.UPLOADS_MEDIA_PATH + domain.getFilePath());
			} else {
				file = new File(this.getHttpServletRequest().getRealPath(
						Constants.UPLOADS_MEDIA_PATH + domain.getFilePath()));
			}
			FileInputStream is = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			is.read(b, 0, (int) file.length());
			BlobFileObject bfo = new BlobFileObject();
			bfo.setContent(b);
			String fileName = domain.getFileName();
			is.close();
			if (StringUtils.isEmpty(fileName))
				fileName = "unknow_file";
			bfo.setFullfilename(fileName);
			this.getHttpServletRequest().setAttribute(
					Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
			return "file_download";
		} else {
			addActionError("找不到指定资源！");
			logger.info("找不到指定资源！");
			return "download_error";

		}

	}

	/**
	 * 转移专辑保存
	 * 
	 * Frank
	 */
	public void changeAlbums() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			if (isdefault != null && StringUtil.ifEqual(isdefault, "1")) {
				service.changeAlbum(list, null);
			} else {
				service.changeAlbum(list, albumId);
			}
		}
	}
	/**
	 * 删除视频
	 * 
	 */
	public String delete() throws Exception {
		boolean isInclude=false;
		if (!StringUtil.isEmpty(keys)) {
			List<Long> ids = StringUtil.splitToList(keys, ",");
			isInclude=service.deleteVideo(ids);
		}
		if(isInclude){
			addActionError("isInclude");
		}
		return listByAlbum();
	}

	/**
	 * 播放视频
	 * 
	 * @return
	 * @throws Exception
	 */
	public String broadcast() throws Exception {
		super.edit();
		return "broadcast";
	}

	public Map<Integer, String> getTrueOfFalseMap1() {
		if (trueOfFalseMap1 != null) {
			return trueOfFalseMap1;
		} else {
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "是");
			trueOfFalseMap1.put(new Integer(0), "否");
			return trueOfFalseMap1;
		}
	}

	// 根据年月日创建文件夹
	private String markFile(String path) {
		Calendar date = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("MM");
		SimpleDateFormat format3 = new SimpleDateFormat("dd");
		String year = format1.format(date.getTime());
		String month = format2.format(date.getTime());
		String day = format3.format(date.getTime());
		int monthI = Integer.parseInt(month);
		int dayI = Integer.parseInt(day);
		fpath = path;
		String fpaths = this.getHttpServletRequest().getRealPath(fpath) + "/"
				+ year;
		File file = new File(fpaths);
		if (!file.getParentFile().exists()) {
			file.mkdir();
		}
		String fpathm = fpaths + "/" + monthI;
		File filem = new File(fpathm);
		if (!filem.getParentFile().exists()) {
			filem.mkdir();
		}
		String fpathd = fpathm + "/" + dayI;
		File filed = new File(fpathd);
		if (!filem.getParentFile().exists()) {
			filem.mkdir();
		}
		fpath = fpath + year + "/" + monthI + "/" + dayI + "/";
		return fpath;
	}

	/**
	 * 视频上传
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean upload() throws Exception {
		boolean bfag = true;
		long totalToRead = getHttpServletRequest().getContentLength();

		if (getMaxSize() > 0) {
			if (totalToRead > ((getMaxSize() * 1024) * 1024)) {
				this.addActionError("err");
				return false;
			}
		}
		String oldFilePath=domain.getFilePath();
		String filename = "";
		if (_fileFileName != null && _file != null
				&& _file.length == _fileFileName.length) {
			String newfileName = "";
			for (int i = 0; i < _file.length; i++) {
				Ognl.setValue("fileSize", domain, totalToRead);
				int extIndex = _fileFileName[i].lastIndexOf(".");
				newfileName = service.getSequence()
						+ _fileFileName[i].substring(extIndex);
				fpath = markFile(Constants.UPLOADS_MEDIA_PATH
						+ getLoginerSiteId() + Constants.MEDIA_VIDEO);
				boolean succ = attachmentManagerService.saveFile(_file[i],
						newfileName, fpath, maxSize);

				Ognl.setValue("filePath", domain,
						fpath.substring(Constants.UPLOADS_MEDIA_PATH.length())
								+ newfileName);
				filename += _fileFileName[i];
				Ognl.setValue("fileName", domain, newfileName);
				Ognl.setValue("issue", domain, Constants.MEDIA_ISSUE);
			}
			if(_fileImage==null&&StringUtil.isEmpty(domain.getImagePath())){
				//未上传封面
				File inFile = new File(attachmentManagerService.getRealPath(fpath) + newfileName);
				String imageName = service.getSequence() + ".jpg";
				File outFile = new File(attachmentManagerService.getRealPath(fpath) + imageName);
				String imageSize = "512x288";
				String exeFilePath = ServletActionContext.getServletContext().getRealPath("/dynamicPage/ffmpeg");
				/*
				Runtime.getRuntime()
						.exec(String
								.format(exeFilePath
										+ " -i %s -y -f image2 -ss 8 -t 0.001 -s %s %s -ss",
										inFile.getAbsolutePath(), imageSize,
										outFile.getAbsolutePath()));
				
				*/
				//ffmpeg -i 4.flv -y -f image2 -ss 8 -t 0.001 -s 350x240 test.jpg -ss
				
				//Runtime.getRuntime().exec("cmd /c start d:\\ffmpeg.bat " + inFile.getPath() + " " + outFile.getParent());  

				Ognl.setValue("imagePath", domain,
						fpath.substring(Constants.UPLOADS_MEDIA_PATH.length())
								+ imageName);
			}
		}
		
			FileUtil.delFile(Configuration.servletContext().getRealPath("") +Constants.UPLOADS_MEDIA_PATH + oldFilePath);

		
		return bfag;
	}
	/**
	 * 上传封面
	 * Frank
	 * 
	 */
	public boolean uploadImage()throws Exception{

		boolean bfag = true;
		long totalToRead = getHttpServletRequest().getContentLength();

		if (getMaxSize() > 0) {
			if (totalToRead > ((getMaxSize() * 1024) * 1024)) {
				return false;
				
			}
		}
		if (_fileImageFileName != null && _fileImage != null
				&& _fileImage.length == _fileImageFileName.length) {
			for (int i = 0; i < _fileImageFileName.length; i++) {
				
				int extIndex = _fileImageFileName[i].lastIndexOf(".");
				String newfileName = service.getSequence()
						+ _fileImageFileName[i].substring(extIndex);
				fpath = markFile(Constants.UPLOADS_MEDIA_PATH
						+ getLoginerSiteId() + Constants.MEDIA_VIDEO);
				boolean succ = attachmentManagerService.saveFile(_fileImage[i],
						newfileName, fpath, maxSize);

				Ognl.setValue("imagePath", domain,
						fpath.substring(Constants.UPLOADS_MEDIA_PATH.length())
								+ newfileName);
			}
		}
		return bfag;
	
	}

	public File[] get_file() {
		return _file;
	}

	public void set_file(File[] _file) {
		this._file = _file;
	}
	public File[] get_fileImage() {
		return _fileImage;
	}

	public void set_fileImage(File[] _fileImage) {
		this._fileImage = _fileImage;
	}
	
	public String[] get_fileContentType() {
		return _fileContentType;
	}

	public void set_fileContentType(String[] contentType) {
		_fileContentType = contentType;
	}

	public String[] get_fileFileName() {
		return _fileFileName;
	}

	public void set_fileFileName(String[] fileName) {
		_fileFileName = fileName;
	}
	public String[] get_fileImageFileName() {
		return _fileImageFileName;
	}

	public void set_fileImageFileName(String[] fileImageName) {
		_fileImageFileName = fileImageName;
	}
	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}
	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
}
