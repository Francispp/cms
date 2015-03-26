package com.cyberway.common.attachment.domain;

import java.io.Serializable;
import java.util.Date;

import com.cyberway.cms.domain.Document;
import com.cyberway.common.domain.CoreUser;

public class Attachment implements Serializable
{
    private Long _id;
    private String _filePath;
    // 路径格式/siteid/docid/fileName
   
	private String _fileExt;
    private double _fileSize;
    private Date _updateTime;
    private CoreUser _uploader;
    private long _documentId;
    private String _name;
    private String remark;
    private String fileName;

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public Long getId() 
    {
        return _id;
       
    }

    public void setId(Long id) 
    {
        _id = id;
    }
   
    
    
    public CoreUser getUploader()
    {
        return _uploader;
    }

    public void setUploader(CoreUser uploader)
    {
        _uploader = uploader;
    }
	

	public long getDocumentId() {
		return _documentId;
	}

	public void setDocumentId(long _documentId) {
		this._documentId = _documentId;
	}

	public String getFileExt() {
		return _fileExt;
	}

	public void setFileExt(String fileExt) {
		this._fileExt = fileExt;
	}

	public String getFilePath() {
		return _filePath;
	}

	public void setFilePath(String filePath) {
		this._filePath = filePath;
	}

	public double getFileSize() {
		return _fileSize;
	}

	public void setFileSize(double fileSize) {
		this._fileSize = fileSize;
	}

	public Date getUpdateTime() {
		return _updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this._updateTime = updateTime;
	}
	public String getFileShortpath()
	{
		try
		{
			String shortpath = _filePath.substring(_filePath.lastIndexOf("/")+1);
			shortpath = shortpath.substring(shortpath.indexOf("-") + 1);
			return shortpath;
		}
		catch(Exception ex)
		{
			return "";
		}
	}
}
