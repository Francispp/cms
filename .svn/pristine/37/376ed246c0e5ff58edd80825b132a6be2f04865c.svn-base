package com.cyberway.common.media.album.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.album.service.AlbumService;
import com.cyberway.common.media.album.service.MediaIntermediateService;
import com.cyberway.common.media.audio.service.AudioService;
import com.cyberway.common.media.video.service.VideoService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class MediaManagerController extends  BaseBizController<MediaIntermediate> {
	MediaIntermediateService service = (MediaIntermediateService) getServiceById("mediaIntermediateService");
	AlbumService albumService = (AlbumService) this.getServiceById("albumService");

	List<MediaIntermediate> aList = new ArrayList();
	private Long mediaid;
	public Long getMediaid() {
		return mediaid;
	}

	public void setMediaid(Long mediaid) {
		this.mediaid = mediaid;
	}
	
	private List viewItems;
	public List getViewItems() {
		return viewItems;
	}

	public void setViewItems(List viewItems) {
		this.viewItems = viewItems;
	}

	private String xml;

	private String docid;

	private String fpath;

	private String ftype;
	
	private String name;

	private float maxSize;

	private Long issue;
	
	private File[] _file;

	private Long albumId;
	
	private String mediaType;
	
	private Long siteId;
	
	private String ismsg;
	
	public String getIsmsg() {
		return ismsg;
	}

	public void setIsmsg(String ismsg) {
		this.ismsg = ismsg;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public File[] get_file() {
		return _file;
	}

	public void set_file(File[] _file) {
		this._file = _file;
	}

	private String[] _fileContentType;

	private String[] _fileFileName;

	private String[] _fileFileSize;

	public String[] get_fileContentType() {
		return _fileContentType;
	}

	public void set_fileContentType(String[] _fileContentType) {
		this._fileContentType = _fileContentType;
	}

	public String[] get_fileFileName() {
		return _fileFileName;
	}

	public void set_fileFileName(String[] _fileFileName) {
		this._fileFileName = _fileFileName;
	}

	public String[] get_fileFileSize() {
		return _fileFileSize;
	}

	public void set_fileFileSize(String[] _fileFileSize) {
		this._fileFileSize = _fileFileSize;
	}

	

	public Long getIssue() {
		return issue;
	}

	public void setIssue(Long issue) {
		this.issue = issue;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * 上传视频时的页面跳转
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadVideoTranspond() throws Exception {
		if(mediaType!=null){
			List list=albumService.getRootAlbums(mediaType, siteId);
			getHttpServletRequest().setAttribute("albumList", list);
		}
		return "uploadVideo";
	}

	/**
	 * 显示
	 * @return
	 */
	public String listxml(){
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")
				&& name != null && !StringUtils.isEmpty(name)&&mediaType != null && !StringUtils.isEmpty(mediaType)) {
             aList = service.find("from MediaIntermediate where docId = ? and name = ? and type=? ",new Object[] { Long.parseLong(docid), name,mediaType });
		} else {
			aList = new ArrayList();
		}
		if(mediaType!=null&&StringUtil.ifEqual(mediaType, "video")){
			VideoService videoService = (VideoService) getServiceById("videoService");
			xml=videoService.toMediaXMLString(aList);
		}else{
			AudioService audioService = (AudioService) getServiceById("audioService");
			xml=audioService.toMediaXMLString(aList);
		}
		
		return "listXml";
	}

	/**
	 * 显示
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String listjson(){
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")
				&& name != null && !StringUtils.isEmpty(name)&&mediaType != null && !StringUtils.isEmpty(mediaType)) {
             aList = service.find("from MediaIntermediate where docId = ? and name = ? and type=? ",new Object[] { Long.parseLong(docid), name,mediaType });
		} else {
			aList = new ArrayList<MediaIntermediate>();
		}
		if(mediaType!=null&&StringUtil.ifEqual(mediaType, "video")){
			VideoService videoService = (VideoService) getServiceById("videoService");
			xml=videoService.toMediaJSONString(aList);
		}else{
			AudioService audioService = (AudioService) getServiceById("audioService");
			xml=audioService.toMediaJSONString(aList);
		}
		
		return "listJson";
	}

	/**
	 * 上传音频时的页面跳转
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadAudioTranspond() throws Exception {
		if(mediaType!=null){
			List list=albumService.getRootAlbums(mediaType, siteId);
			getHttpServletRequest().setAttribute("albumList", list);
		}
		return "uploadAudio";
	}

	@Override
	protected EntityDao<MediaIntermediate> getService() {
		return service;
	}

   

	
}
