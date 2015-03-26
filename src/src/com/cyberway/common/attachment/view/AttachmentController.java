package com.cyberway.common.attachment.view;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.cyberway.cms.Constants;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.utils.ParseHtmlUtil;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.attachment.domain.Attachment;
import com.cyberway.common.attachment.service.AttachmentManagerService;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.audio.service.AudioService;
import com.cyberway.common.media.video.service.VideoService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class AttachmentController extends BaseBizController<Attachment> {
	
	AttachmentManagerService service = (AttachmentManagerService) this.getServiceById("attachmentManagerService");
	DocumentCommonService docservice = (DocumentCommonService) getServiceById("documentCommonService");
	CmsPermissionService permissionService = (CmsPermissionService) getServiceById("cmsPermissionService");
	HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	DistributionService distributionService = (DistributionService) ServiceLocator.getBean("distributionService");
	
	Collection<Attachment> aList = new ArrayList<Attachment>();
	
	private static List<String> allowTypeList;

	private File[] _file;

	private String[] _fileContentType;

	private String[] _fileFileName;

	private String[] _fileFileSize;

	private List fileAttributes;

	private String xml;
	
	private String json;

	private String docid;
	
	private String channelid;
	
	private String siteid;

	private String fpath;

	private String ftype;

	private String name;

	private float maxSize;
	
	private boolean isPic;
	
	private String actionURL;
	
	private String	albumImage;
	
	private Integer imgWidth;
	private Integer imgHeight;
	


	@Override
	protected EntityDao getService() {
		return service;
	}

	public String execute() throws Exception {
		return "input";
	}

	public String upload() throws Exception {

		return "load_file";
	}

	// 根据年月日创建文件夹
	// 根据年月日创建文件夹
	@SuppressWarnings("deprecation")
	public String markFile(String path){
		Calendar date = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("MM");
		SimpleDateFormat format3 = new SimpleDateFormat("dd");
		String year = format1.format(date.getTime());
		String month=format2.format(date.getTime());
		String day=format3.format(date.getTime());
		int monthI=Integer.parseInt(month);
		int dayI=Integer.parseInt(day);
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//String name = format.format(date.getTime());
		if (StringUtils.isEmpty(fpath)) {
			//fpath = Constants.UPLOADS_PATH;
			fpath=path;
			String fpaths = this.getHttpServletRequest().getRealPath(fpath)+ "/" + year;
			File file = new File(fpaths);
			if (!file.getParentFile().exists()) {
				file.mkdir();
			}
			String fpathm = fpaths+ "/" + monthI;
			File filem = new File(fpathm);
			if (!filem.getParentFile().exists()) {
				filem.mkdir();
			}
			String fpathd = fpathm+ "/" + dayI;
			File filed = new File(fpathd);
			if (!filem.getParentFile().exists()) {
				filem.mkdir();
			}
			fpath = fpath + year+"/"+monthI+"/"+dayI+ "/";
		}
		return fpath;
	}
	
	
	/**
	 * 将word文档保存到CmsBaseDocument表中
	 * 
	 * dsoframe 保存文件方法
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public String officeSaveFile() throws Exception {
		int totalToRead = getHttpServletRequest().getContentLength();
		if (totalToRead > ((Integer.parseInt((Constants.INFO_OFFICE_SIZE)) * 1024) * 1024)) {
			xml = "maxSize";
			getHttpServletRequest().setAttribute("totalToRead",totalToRead);
			return "success1";
		}
		String siteId = StringUtils.trimToNull((String) getParameterValue("siteId"));
		Validate.notNull(siteId, "siteId is null!");
		String filecontent="";//文件文本内容
		fpath = Constants.INFO_OFFICE_PATH+"/"+siteId+"/"+docid;
		//logger.info("file size:" + _file.length);
		if (_fileFileName != null && _file != null
				&& _file.length == _fileFileName.length) {
			for (int i = 0; i < _file.length; i++) {
				// 保存文件
				String newfileName = _fileFileName[i];
				// 先删除原有文件，再保存
				service.deleteFile(newfileName, fpath);
				// 重新保存office文件
				boolean succ = service.saveFile(_file[i], newfileName, fpath, ".doc", null , null);
				//增加到全文搜索中
				if(Constants.INFO_OFFICE_FILE_CONTENT_ISSEARCH){
					 String filetype = new String("");
					 //取得文件类型
					 filetype = newfileName.substring(newfileName.lastIndexOf(".")+1);
					 //若为html,取正文内容
					 if(filetype.equalsIgnoreCase("html") || filetype.equalsIgnoreCase("htm")){
						 String htmlpath = AttachmentManagerService.getRealPath(fpath) + newfileName;
						 String content=ParseHtmlUtil.readTextFile(htmlpath, "GBK");
						 if(!StringUtil.isEmpty(content)){
							 int start = content.indexOf("<head>");
							 if(start==-1){
								 start = content.indexOf("<HEAD>");
							 }
							 int end = content.indexOf("</head>");
							 if(end==-1){
								 end = content.indexOf("</HEAD>");
							 }
							 if(start>0){
								 content = content.substring(0, start) + content.substring(end+7);
							 }
							 filecontent=ParseHtmlUtil.parseText(content);
						 }
					 }
					 //保存office正文内容
					 if(!StringUtil.isEmpty(filecontent) && StringUtil.isNumber(docid)){
						 DocumentCommonService docService=(DocumentCommonService)this.getServiceById("documentCommonService");
						 docService.updateDocumentDody(new Long(docid), filecontent);
					 }
				}
				if (!succ) {
					addActionMessage(getText("SaveFailed"));
					return "";
				}
			}

		} else {
			logger.debug("uploadFiles is null");
			addActionMessage(getText("ParameterError"));
			return "";
		}
		return "";
	}

	/**
	 * @author lan 附件列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public String listXml() throws Exception {
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")
				&& name != null && !StringUtils.isEmpty(name)) {
			if(docid.indexOf(",") == -1)
             aList = (Collection) service.find("from Attachment where documentId = ? and name = ?",new Object[] { Long.parseLong(docid), name });
			else
			aList = (Collection) service.find("from Attachment where documentId in ("+docid +") and name = ?",new Object[] {name });
		} else {
			// aList = (Collection)service.getAll();
			aList = new ArrayList();
		}
		xml = service.toAttachmentXMLString(aList);
		return "listXml";
	}

	/**
	 * 显示
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String listJson(){
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")
				&& name != null && !StringUtils.isEmpty(name)) {
			if(docid.indexOf(",") == -1)
             aList = (Collection) service.find("from Attachment where documentId = ? and name = ?",new Object[] { Long.parseLong(docid), name });
			else
			aList = (Collection) service.find("from Attachment where documentId in ("+docid +") and name = ?",new Object[] {name });
		} else {
			// aList = (Collection)service.getAll();
			aList = new ArrayList();
		}
		json = AttachmentManagerService.toAttachmentJSONString(aList);
		
		return "listJson";
	}

	/**
	 * 
	 * 删除附件
	 * 
	 * @author lan
	 */
	public String listDelete() throws Exception {
		Attachment attachment =service.get(id);
		service.deleteFile(attachment.getFilePath());
		service.remove(attachment);
		htmlSynchroismService.deleteStaticHtmlByDocumentId(attachment.getDocumentId(), 0L);
		return listXml();
	}

	public String fileDownload()throws Exception{
		
		if(StringUtils.isNotEmpty(fpath))
		{
			
		Loginer loginer=(Loginer)getSession().get(Loginer.LOGININFO_SESSION);
		//检测是否有文档的浏览权限
		if(permissionService.haveThePermission(loginer, Constants.DOCUMENT_PERMISSION, Constants.DOCUMENT_TYPE, Long.valueOf(docid))
				||permissionService.haveThePermissionAndDocAuthor(loginer, Constants.DOCUMENT_PERMISSION+"_AUTHOR", Long.valueOf(docid)))
		{
		File file;
		if(Constants.IS_REALPATH)
		{
		 file = new File(Constants.ABSOLUTE_PATH+fpath);
		}
		else{
			file = new File(this.getHttpServletRequest().getRealPath(fpath));
		}
		
		FileInputStream is = new FileInputStream(file);
		byte[] b = new byte[(int) file.length()];
		is.read(b, 0, (int) file.length());
		BlobFileObject bfo = new BlobFileObject();
		bfo.setContent(b);
        String fileName = service.getFileShortpath(fpath);
        is.close();
        if(StringUtils.isEmpty(fileName))
        	fileName="unknow_file";
		bfo.setFullfilename(fileName);
		this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
		}
		return "file_download";
		}
		else
		{
			addActionError("用户没有权限下载该附件！");
			logger.info("用户没有权限下载该附件！");
			return "download_error";
			
		}
		
	}
	public String fileDownloadById()throws Exception{
		if(id!=0)
		{
			Attachment attachment = service.get(id);	
			if(attachment != null)
			{
				fpath = attachment.getFilePath();
			}
			if(StringUtils.isBlank(fpath)){
				throw new Exception("附件不存在！");
			}
			Loginer loginer=(Loginer)getSession().get(Loginer.LOGININFO_SESSION);
			//检测是否有文档的浏览权限
			if(loginer == null || (loginer != null && permissionService.haveThePermission(loginer, Constants.DOCUMENT_PERMISSION, Constants.DOCUMENT_TYPE, Long.valueOf(docid))
					||permissionService.haveThePermissionAndDocAuthor(loginer, Constants.DOCUMENT_PERMISSION+"_AUTHOR", Long.valueOf(docid))))
			{
				File file;
				if(Constants.IS_REALPATH)
				{
					file = new File(Constants.ABSOLUTE_PATH+fpath);
				}
				else
				{
					file = new File(this.getHttpServletRequest().getRealPath(fpath));
				}
				if(!file.exists()){
					throw new Exception("附件不存在！");
				}
				//附件大于一定25M时，采用直接要开链接下载
				/*if(attachment.getFileSize()>26214400){
					if(Constants.IS_REALPATH){
						System.out.println("大于25M附件："+file.getAbsolutePath()+" 到："+getHttpServletRequest().getRealPath("/")+fpath);
						OfficeFileUtil.updateOfficeFile(fpath,getHttpServletRequest().getRealPath("/")+fpath);
					}			
					return "download";
				}*/
				//logger.info("fileName"+file.getName()+"size:"+file.length());
		       FileInputStream is = new FileInputStream(file);
				byte[] b = new byte[(int) file.length()];
				is.read(b, 0, (int) file.length());
				BlobFileObject bfo = new BlobFileObject();
				bfo.setContent(b);
		        String fileName = service.getFileShortpath(fpath);
		        is.close();
		        if(StringUtils.isEmpty(fileName))
		        	fileName="unknow_file";
				bfo.setFullfilename(fileName);
				this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
			}
			return "file_download";
		}
		else
		{
			addActionError("用户没有权限下载该附件！");
			logger.info("用户没有权限下载该附件！");
			return "download_error";
			
		}
		
	}

	/**
	 * 上传附件
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public String uploadFile() throws Exception {
		long totalToRead = getHttpServletRequest().getContentLength();
		if (getMaxSize() > 0) {
			if (totalToRead > ((getMaxSize()*1024)*1024)) {
				xml = "maxSize";
				return "success";
			}
		}
		domain.setFileSize(totalToRead);
		String filename = "";
		if (_fileFileName != null && _file != null
				&& _file.length == _fileFileName.length) {
			for (int i = 0; i < _file.length; i++) {
				domain.setFilePath(_fileFileName[i]);
				int index = _fileFileName[i].indexOf(".");
				if (index != -1) {
					String s = _fileFileName[i].substring(index);
					domain.setFileExt(s);
				}
				// 保存文件
				String newfileName ="";
				//if(getIsPic())
					newfileName = service.getSequence()+ domain.getFileExt();	
				 // else
				   // newfileName = service.getSequence() + "-"+ _fileFileName[i];
				//解决模板上传中文问题,若docid为空，只用序列作为文件名 
					
					domain.setFileName(_fileFileName[i]);
				if(StringUtils.isEmpty(docid)||docid.equals("0")){
					int extIndex=_fileFileName[i].lastIndexOf(".");
					newfileName=service.getSequence()+_fileFileName[i].substring(extIndex);
				}
				if(StringUtils.isEmpty(fpath)){ fpath=Constants.UPLOADS_ATTACHMENT_PATH+siteid+"/"+docid; }
				 
				boolean succ = service.saveFile(_file[i], newfileName, fpath, domain.getFileExt(), imgWidth, imgHeight);
				domain.setFilePath(fpath +"/"+ newfileName);
			
				filename += _fileFileName[i];
				if (!succ) {
					addActionMessage(getText("SaveFailed"));
					return "success";
				}
			}

		} else {
			logger.debug("uploadFiles is null");
			addActionMessage(getText("ParameterError"));
			return "success";
		}

		domain.setUpdateTime((new Date(System.currentTimeMillis())));
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")) {
			domain.setDocumentId(Long.parseLong(docid));
			Long cid = 0L;
			if(StringUtils.isNotBlank(channelid)){
				cid = Long.parseLong(channelid);
			}
			htmlSynchroismService.deleteStaticHtmlByDocumentId(domain.getDocumentId(), cid);
		}
		if (name != null && !StringUtils.isEmpty(name)) {
			domain.setName(name);
		}
		domain = service.saveOrUpdate(domain);
		xml = domain.getFilePath();
		Long siteId = null;
		if(StringUtils.isNotBlank(siteid)){
			siteId = Long.valueOf(siteid);
		}else{
			BaseDocument doc = docservice.get(Long.parseLong(docid));
			if(doc!=null){
				siteId = doc.getSite().getOid();
			}
		}
		if(siteId != null)
			distributionService.sendAttachment(siteId, domain);
		System.out.println(getActionMessages().size());
		return "success";
	}
	
	/**
	 * Ajax保存图片
	 * @return
	 * @throws Exception
	 */
	public String savePhotoAjax() throws Exception {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("{\"success\": ");
		long totalToRead = getHttpServletRequest().getContentLength();
		if (getMaxSize() > 0) {
			if (totalToRead > ((getMaxSize()*1024)*1024)) {
				xml = "maxSize";
				jsonString.append("false, \"msg\": \"文件过大\"}");
				this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
				return AJAX;
			}
		}
		domain.setFileSize(totalToRead);
		String filename = "";
		if (_fileFileName != null && _file != null
				&& _file.length == _fileFileName.length) {
			for (int i = 0; i < _file.length; i++) {
				domain.setFilePath(_fileFileName[i]);
				int index = _fileFileName[i].indexOf(".");
				if (index != -1) {
					String s = _fileFileName[i].substring(index);
					if(!isAllowType(s)){
						jsonString.append("false, \"msg\": \"无法上传该格式文件\"}");
						this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
						return AJAX;
					}
					domain.setFileExt(s);
				} else {
					jsonString.append("false, \"msg\": \"无法上传该格式文件\"}");
					this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
					return AJAX;
				}
				// 保存文件
				String newfileName ="";
				newfileName = service.getSequence()+ domain.getFileExt();	
				
				domain.setFileName(_fileFileName[i]);
				if(StringUtils.isEmpty(docid)||docid.equals("0")){
					int extIndex=_fileFileName[i].lastIndexOf(".");
					newfileName=service.getSequence()+_fileFileName[i].substring(extIndex);
				}
				if(StringUtils.isEmpty(fpath)){ fpath=Constants.UPLOADS_ATTACHMENT_PATH+siteid+"/"+docid; }
				 
				boolean succ = AttachmentManagerService.saveFile(_file[i], newfileName, fpath, domain.getFileExt(), imgWidth, imgHeight);
				domain.setFilePath(fpath +"/"+ newfileName);
			
				filename += _fileFileName[i];
				if (!succ) {
					jsonString.append("false, \"msg\": \"保存文件失败\"}");
					this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
					return AJAX;
				}
			}

		} else {
			logger.debug("uploadFiles is null");
			jsonString.append("false, \"msg\": \"上传文件不能为空\"}");
			this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
			return AJAX;
		}

		domain.setUpdateTime((new Date(System.currentTimeMillis())));
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")) {
			domain.setDocumentId(Long.parseLong(docid));
			Long cid = 0L;
			if(StringUtils.isNotBlank(channelid)){
				cid = Long.parseLong(channelid);
			}
			htmlSynchroismService.deleteStaticHtmlByDocumentId(domain.getDocumentId(), cid);
		}
		if (name != null && !StringUtils.isEmpty(name)) {
			domain.setName(name);
		}
		domain = service.saveOrUpdate(domain);
		xml = domain.getFilePath();
		Long siteId = null;
		if(StringUtils.isNotBlank(siteid)){
			siteId = Long.valueOf(siteid);
		}else{
			BaseDocument doc = docservice.get(Long.parseLong(docid));
			if(doc!=null){
				siteId = doc.getSite().getOid();
			}
		}
		if(siteId != null)
			distributionService.sendAttachment(siteId, domain);

		jsonString.append("true, \"msg\": \"上传文件完成\", \"url\":\"");
		jsonString.append(xml);
		jsonString.append("\"}");
		this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
		return AJAX;
	}
	
	public String getAlbumImage() {
		return albumImage;
	}

	public void setAlbumImage(String albumImage) {
		this.albumImage = albumImage;
	}

	public String[] get_fileFileSize() {
		return _fileFileSize;
	}

	public void set_fileFileSize(String[] fileSize) {
		_fileFileSize = fileSize;
	}

	public Collection<Attachment> getAList() {
		return aList;
	}

	public void setAList(Collection<Attachment> list) {
		aList = list;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
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

	public List getFileAttributes() {
		return fileAttributes;
	}

	public void setFileAttributes(List fileAttributes) {
		this.fileAttributes = fileAttributes;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public boolean getIsPic() {
		return isPic;
	}

	public void setIsPic(boolean isPic) {
		this.isPic = isPic;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	
	
	public String getActionURL() {
		return actionURL;
	}

	public void setActionURL(String actionURL) {
		this.actionURL = actionURL;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public Integer getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Integer getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	
	private boolean isAllowType(String ext){
		if(allowTypeList==null){
			allowTypeList = new ArrayList<String>();
			allowTypeList.add(".jpg");
			allowTypeList.add(".jpeg");
			allowTypeList.add(".bmp");
			allowTypeList.add(".gif");
			allowTypeList.add(".png");
		}
		return allowTypeList.contains(ext.toLowerCase());
	}
}
