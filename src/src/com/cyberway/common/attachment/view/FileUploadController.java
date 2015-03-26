package com.cyberway.common.attachment.view;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cyberway.cms.Constants;
import com.cyberway.core.web.BaseBizController;

public abstract class FileUploadController<T> extends BaseBizController<T> {

	private static final long serialVersionUID = -2954095978840197751L;

	private String updateFileIds;

	public String getUpdateFileIds() {
		return updateFileIds;
	}

	public void setUpdateFileIds(String updateFileIds) {
		this.updateFileIds = updateFileIds;
	}

	public String uploadFile() {
		return "load_file";
	}

	public String saveOrUpdate() throws Exception{
		domain=getService().saveOrUpdate(domain);
		moveTempFileToRealPath();
		return EDIT_RESULT;
	}

	protected void moveTempFileToRealPath() {
		if (StringUtils.isNotBlank(updateFileIds)) {
			for (String fileId : updateFileIds.split(",")) {
				File file = getTempFile(fileId);
				if(file != null){
					file.renameTo(getTrueFile(fileId));
					//TODO Send file to site
				}
			}
		}
	}

	private File getTempFile(String fileId) {
		if (fileId != null && fileId.length() > 19) {
			StringBuilder sb = new StringBuilder();
			sb.append(fileId.substring(0, 8));
			sb.append(File.separatorChar);
			sb.append(fileId.substring(8));
			try{
				File file = new File(getRealPath(Constants.UPLOADS_TEMP_PATH), sb
						.toString());
				if (file.exists()) {
					return file;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private File getTrueFile(String fileId) {
		if (fileId != null && fileId.length() > 19) {
			StringBuilder sb = new StringBuilder();
			sb.append(fileId.substring(0, 8));
			sb.append(File.separatorChar);
			sb.append(fileId.substring(8));
			try{
				File file = new File(getRealPath(Constants.UPLOADS_FILE_PATH), sb
						.toString());
				return file;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getRealPath(String fpath) throws Exception {
		String fullpath = "";
		if (fpath != null && fpath.length() > 0) {
			if (Constants.IS_REALPATH) {
				fullpath = Constants.ABSOLUTE_PATH + fpath;
			} else {
				fullpath = ServletActionContext.getServletContext()
						.getRealPath(fpath);
			}
		} else {
			if (Constants.IS_REALPATH) {
				fullpath = Constants.UPLOADS_PATH;
			} else {
				fullpath = ServletActionContext.getServletContext()
						.getRealPath(Constants.UPLOADS_PATH);
			}
		}
		java.io.File f = new java.io.File(fullpath);
		if (!f.isDirectory()) {
			f.mkdir();
		}
		fullpath = fullpath + "/";
		java.io.File file = new java.io.File(fullpath);
		if (!file.isDirectory()) {
			file.mkdir();
		}
		return fullpath;
	}
}
