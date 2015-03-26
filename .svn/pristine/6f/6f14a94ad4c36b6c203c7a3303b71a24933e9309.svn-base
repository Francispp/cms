package com.cyberway.common.file.view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.common.file.domain.ServerFile;
import com.cyberway.common.file.service.ServerFileService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.ActionContext;

public class FileUploadController {

	private ServerFile file;

	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static Map<String, Set<String>> allowExtMap = initAllowExtMap();

	private Boolean uploadSuccess = false;
	private String uploadMsg = "";
	private String json;

	private ServerFileService serverFileService;
	
	public String upload(){
		uploadCK();
		return "UPLOAD";
	}
	
	public String select(){
		return "SELECT";
	}
	
	public String list(){
		json = "[{\"url\":\"/cms_file/uploads/file/20140418/00000001.png\"},{\"url\":\"/cms_file/uploads/file/20140418/00000002.png\"}]";
		return "JSON";
	}

	public String uploadCK() {
		if (upload != null) {
			if (file != null && StringUtils.isNotBlank(file.getChannelId())
					&& StringUtils.isNotBlank(file.getDocId())
					&& StringUtils.isNotBlank(file.getFileType())) {
				saveFile();
			} else {
				uploadMsg = "无效的请求";
			}
		} else {
			uploadMsg = "请选择要上传的文件";
		}
		return "UPLOADCK";
	}

	private void saveFile() {
		try{
		file.setHasSend(false);
		file.setSourceName(uploadFileName);
		file.setUpdateTime(new Date());
		file.setFileExt(getExt(uploadFileName));
		if (!allowExtMap.containsKey(file.getFileType())) {
			uploadMsg = "不允许上传该类型的文件";
		} else if (!allowExtMap.get(file.getFileType()).contains(
				file.getFileExt())) {
			uploadMsg = "不允许上传该格式的文件";
		} else {
			Loginer loginer = (Loginer) getSessionAttribute(Loginer.LOGININFO_SESSION);
			if (loginer == null) {
				WebUser webUser = (WebUser) getSessionAttribute(WebUser.WEB_USER_IN_SESSION);
				if (webUser != null) {
					file.setUploaderId(webUser.getOid());
					file.setUploaderType(ServerFile.UPLOADER_TYPE_WEB);
				}
			} else {
				file.setUploaderId(loginer.getUserid());
				file.setUploaderType(ServerFile.UPLOADER_TYPE_INNER);
			}
			file.setUrl(createNewFileUrl(file.getFileExt()));
			File newFile = createNewFile(file.getUrl());
			while (newFile.exists()) {
				file.setUrl(createNewFileUrl(file.getFileExt()));
				newFile = createNewFile(file.getUrl());
			}
			try {
				file.setPath(newFile.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
				file.setPath(newFile.getAbsolutePath());
			}
			try {
				FileUtil.writeTo(upload, newFile);
				getServerFileService().saveOrUpdate(file);
				uploadSuccess = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String getExt(String filename) {
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex != -1) {
			return filename.substring(dotIndex + 1).toLowerCase();
		}
		return "";
	}

	private File createNewFile(String url) {
		if (Constants.IS_REALPATH) {
			url = Constants.ABSOLUTE_PATH + url;
		} else {
			url = ServletActionContext.getServletContext().getRealPath(url);
		}
		url = url.replace("/", File.separator);
		File file = new File(url);
		return file;
	}

	private synchronized String createNewFileUrl(String ext) {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.UPLOADS_FILE_PATH);
		String date = sdf.format(new Date());
		sb.append(date);
		sb.append("/");
		sb.append(getUUID());
		if (StringUtils.isNotBlank(ext)) {
			sb.append(".");
			sb.append(ext);
		}
		return sb.toString();
	}

	private String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}

	public ServerFileService getServerFileService() {
		if (serverFileService == null) {
			serverFileService = (ServerFileService) ServiceLocator
					.getBean("serverFileService");
		}
		return serverFileService;
	}

	private Object getSessionAttribute(String key) {
		return ActionContext.getContext().getSession().get(key);
	}

	private static Map<String, Set<String>> initAllowExtMap() {
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		Set<String> image = new HashSet<String>();
		image.add("jpg");
		image.add("jpeg");
		image.add("png");
		image.add("gif");
		image.add("bmp");
		map.put("image", image);
		return map;
	}

	public ServerFile getFile() {
		return file;
	}

	public void setFile(ServerFile file) {
		this.file = file;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Boolean getUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(Boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public String getUploadMsg() {
		return uploadMsg;
	}

	public void setUploadMsg(String uploadMsg) {
		this.uploadMsg = uploadMsg;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
