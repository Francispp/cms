package com.cyberway.common.media.audio.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.ecside.util.RequestUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ognl.Ognl;

import com.cyberway.cms.Constants;
import com.cyberway.common.attachment.service.AttachmentManagerService;
import com.cyberway.common.media.album.service.AlbumService;
import com.cyberway.common.media.album.service.MediaIntermediateService;
import com.cyberway.common.media.audio.domain.Audio;
import com.cyberway.common.media.audio.service.AudioService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.staticer.Configuration;

public class AudioController extends BaseBizController<Audio> {
	MediaIntermediateService mediaIntermediateService=(MediaIntermediateService)this.getServiceById("mediaIntermediateService");
	AttachmentManagerService attachmentManagerService = (AttachmentManagerService) this
			.getServiceById("attachmentManagerService");

	AudioService service = (AudioService) this.getServiceById("audioService");
	AlbumService albumService = (AlbumService) this
			.getServiceById("albumService");
	private int pageIndex;
	private int pageSize;
	private Long albumId;
	private String isdefault;
	private String albumType;
	private String mediaFilePath;

	private File[] _file;

	private String[] _fileContentType;

	private String[] _fileFileName;
	private String fpath;
	private float maxSize;
	
	private Long docId;
	private String docName;
	private String ftype;//前台上传时、先自动取标签中设置的格式
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

	public File[] get_file() {
		return _file;
	}

	public void set_file(File[] _file) {
		this._file = _file;
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

	@Override
	protected EntityDao<Audio> getService() {
		return service;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getMediaFilePath() {
		return Constants.UPLOADS_MEDIA_PATH;
	}

	public void setMediaFilePath(String mediaFilePath) {
		this.mediaFilePath = mediaFilePath;
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

	public String getAlbumType() {
		return albumType;
	}

	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}

	@Override
	public String execute() throws Exception {
		return listByAlbum();
	}

	public String add() throws Exception {
		edit();
		if (domain != null) {
			domain.setAlbumId(albumId);
		}
		return EDIT_RESULT;
	}

	public String edit() throws Exception {
		return super.edit();
	}

	public String saveOrUpdate() throws Exception{
		if (null != _file && _file.length > 0) {
			boolean bfag=upload();
			if(!bfag){
				return EDIT_RESULT;
			}
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
	 * 浏览音频 Frank
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
		//防止从内容采集区上传的时候、并且未保存的数据
		criterias.add(Restrictions.isNotNull("filePath"));
		
		criteria.setAddCriterions(criterias);
		
		if (pageIndex == 0)
			pageIndex = 1;
		if (pageSize == 0)
			pageSize = Page.DEFAULT_PAGE_SIZE;

		doListEntity(criteria, getTableId(), pageSize);
		setAlbumId(albumId);
		return LIST_RESULT;

	}
	
	/**
	 * 浏览站点下的所有音频文件 
	 * 
	 * Frank
	 */
	public String listBySite() throws Exception {
		Long siteId = getLoginerSiteId();
		if (siteId != null) {
			CriteriaSetup criteria = new CriteriaSetup();
			List<Criterion> criterias = new ArrayList<Criterion>();
			
			criterias.add(Restrictions.eq("siteId", siteId));
			//是默认专辑
			if(isdefault != null && !StringUtil.isEmpty(isdefault)){
				criterias.add(Restrictions.isNull("albumId"));
			}else if (albumId != null) {//普通专辑
				criterias.add(Restrictions.eq("albumId", albumId));
			}
			
			criterias.add(Restrictions.isNotNull("filePath"));
			criteria.setAddCriterions(criterias);
			criteria.setInOrder(Order.desc("createTime"));
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}
		return "list_site";
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
	 * 播放音频
	 * 
	 * @return
	 * @throws Exception
	 */
	public String broadcast() throws Exception {
		super.edit();
		return "broadcast";
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
			String fpaths = this.getHttpServletRequest().getRealPath(fpath)
					+ "/" + year;
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
	 * 音频上传
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean upload() throws Exception {
		boolean bfag=true;
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
			for (int i = 0; i < _file.length; i++) {

				int extIndex = _fileFileName[i].lastIndexOf(".");
				String newfileName = service.getSequence()
						+ _fileFileName[i].substring(extIndex);
				fpath = markFile(Constants.UPLOADS_MEDIA_PATH
						+ getLoginerSiteId() + Constants.MEDIA_AUDIO);
				boolean succ = attachmentManagerService.saveFile(_file[i],
						newfileName, fpath, maxSize);
				Ognl.setValue("fileName", domain, newfileName);
				Ognl.setValue("filePath", domain,
						fpath.substring(Constants.UPLOADS_MEDIA_PATH.length())
								+ newfileName);
				filename += _fileFileName[i];
				Ognl.setValue("issue", domain, Constants.MEDIA_ISSUE);
			}
		}
		//删除本地已经存在的文件
		FileUtil.delFile(Configuration.servletContext().getRealPath("") +Constants.UPLOADS_MEDIA_PATH + oldFilePath);
		
		return bfag;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
	
}
