package com.cyberway.common.attachment.service;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.attachment.domain.FCKFile;
import com.cyberway.core.dao.HibernateEntityDao;

public class FCKFileManagerService extends HibernateEntityDao<FCKFile>{
	
	public List<FCKFile> getFckFilesByDocId(Long docId)
	{
		List<FCKFile> list = findBy("docId", docId);
		return (list == null) ? new ArrayList():list; 
	}
	

}
