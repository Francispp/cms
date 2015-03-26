package com.cyberway.common.attachment.service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.tags.components.DocumentIterator.Limit;
import com.cyberway.common.attachment.domain.Attachment;
import com.cyberway.common.utils.ImageUtil;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.FileUtil;

public class AttachmentManagerService extends HibernateEntityDao<Attachment> {
	private static final int base = 1000; // 这个基数必须是10的N次方

	private static long millis = 0, old = 0;
	
	private final static Logger LOG = Logger.getLogger(AttachmentManagerService.class);

	/**
	 * 
	 * 保存文件
	 */
	public static boolean saveFile(File inFile, String newfileName,String fpath, String ex, Integer imgWidth, Integer imgHeight) {
		boolean succ = false;
		File outputFile;
        String fileName = File.separatorChar+ newfileName;
		if (inFile == null)
			return succ;
		try {
		
			outputFile = new File(getRealPath(fpath) + fileName);
			//检测父文件夹是否正在
			if (!outputFile.getParentFile().exists())
			{
				outputFile.getParentFile().mkdirs();
			}
             //linux系统下renameTo方法只能起到改名作用
			//inFile.renameTo(outputFile);
			if(ex!=null && ".jpg.png.bmp.gif.jpeg".indexOf(ex.toLowerCase())>-1){
				try{
					File temp = outputFile;
					if(".bmp".indexOf(ex.toLowerCase())>-1){
						fileName = fileName.substring(0, fileName.indexOf('.'))+".jpg";
						outputFile = new File(getRealPath(fpath) + fileName);
						String targetFile = getRealPath(Constants.UPLOADS_IMAGE_PATH) + System.currentTimeMillis() +".jpg";
						ImageUtil.convertJPG(inFile, targetFile);
						inFile = new File(targetFile);
					}
					
					/*
					 * 影响HR首页上传Banner,去掉裁图步骤
					 */
					if(imgWidth!=null&&imgHeight!=null){
						boolean rs = ImageUtil.scalePicture(inFile.getPath(), outputFile.getPath(), imgWidth, imgHeight);
						if(!rs){
							FileUtil.writeTo(inFile, temp);
						}
					}else{
						FileUtil.writeTo(inFile, outputFile);
					}
					
				}catch (Exception e) {
					LOG.error("--", e);
					FileUtil.writeTo(inFile, outputFile);
				}
			}else{
				FileUtil.writeTo(inFile, outputFile);
			}
			if(Constants.IS_REALPATH){
				FileUtil.update(getRealPath(fpath) + fileName, ServletActionContext.getServletContext().getRealPath(fpath)+fileName);	
			}
			succ = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return succ;
	}
	
	/**
	 * 
	 * 保存文件
	 */
	public static boolean saveFile(File inFile, String newfileName,
			String fpath, float maxSize){
		boolean succ = false;
		File outputFile;
		String fileName = File.separatorChar + newfileName;
		if (inFile == null)
			return succ;
		try {
			outputFile = new File(getRealPath(fpath) + fileName);
			// 检测父文件夹是否正在
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();

			}
			// linux系统下renameTo方法只能起到改名作用
			// inFile.renameTo(outputFile);
			FileUtil.writeTo(inFile, outputFile);
			if (maxSize == 0.0f) {
				if (Constants.IS_REALPATH) {
					FileUtil.update(getRealPath(fpath) + fileName,
							ServletActionContext.getServletContext()
									.getRealPath(fpath) + fileName);
				}
			}
			succ = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return succ;
	}
	
	/**
	 * 若文件存在，删除指定的文件
	 * @return
	 */
	public static boolean deleteFile( String newfileName,String fpath){
		boolean succ = false;
		File outputFile;
		try {
			if(Constants.IS_REALPATH)
			{
			outputFile = new File(Constants.ABSOLUTE_PATH+fpath + File.separatorChar+ newfileName);			
			}
			else
			{
			outputFile = new File(getRealPath(fpath) + File.separatorChar+ newfileName);
			}
			if(outputFile.exists())
				succ = outputFile.delete();
			else
				succ=true;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		outputFile=null;
		return succ;
	}
	/**
	 * 根据路径删除附件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath){
		boolean succ = false;
		File outputFile;
		try {
			if(Constants.IS_REALPATH)
			{
			outputFile = new File(Constants.ABSOLUTE_PATH+filePath);	
			}
			else
			{
				outputFile = new File(getRealPath(filePath));
			}
			if(outputFile.exists())
				succ = outputFile.delete();
			else
				succ=true;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		outputFile=null;
		return succ;
	}
	/**
	 * 根据docid清除附件
	 * @param docid
	 */
	public void deleteByDocument(Long docid)
	{
		if(docid != null && docid !=0)
		{
			Collection<Attachment> col = this.find("from Attachment where documentId = ?",new Object[] {docid});
			Iterator it = col.iterator();
			while(it.hasNext())
			{
				Attachment attachment = (Attachment)it.next();
				if(attachment != null)
				{   List<Attachment> ats = this.findBy("filePath", attachment.getFilePath());
				    if(ats.size() == 1)//针对一文多发
					this.deleteFile(attachment.getFilePath());
					this.remove(attachment);
				}
			}
		}
	}
	/**
	 * @author lan
	 * 将附件列表生成XML
	 * @param coll
	 * @return
	 */

	public static String toAttachmentXMLString(Collection coll) {
		String rtn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		rtn += "<RECORDSET>";
		Iterator iter = coll.iterator();

		while (iter.hasNext()) {
			Attachment vo = (Attachment) iter.next();
			if (vo != null && vo.getId() > 0) {
				rtn += "<DATA>";

				rtn += "<AID>";
				rtn += vo.getId();
				rtn += "</AID>";
				
				rtn += "<DOCID>";
				rtn += vo.getDocumentId();
				rtn += "</DOCID>";

				rtn += "<AFILEPATH>";
				rtn += vo.getFilePath();
				rtn += "</AFILEPATH>";

				rtn += "<AFILESHORTPATH>";
				if(vo.getFileName() != null && !ObjectUtils.equals("", vo.getFileName()))
					rtn += vo.getFileName();
				else 
					rtn += vo.getFileShortpath();
				rtn += "</AFILESHORTPATH>";

				rtn += "<AFILESIZE>";
				rtn += getTypeFileSize(vo.getFileSize());
				rtn += "</AFILESIZE>";
				
				rtn += "<REMARK>";
				rtn += vo.getRemark();
				rtn += "</REMARK>";

				String fileName = vo.getFileShortpath();
				String fileExtend = fileName.substring(
						fileName.lastIndexOf(".") + 1, fileName.length())
						.toLowerCase();
				File file = new File("/" + fileExtend + ".gif");

				rtn += "<PICPATH>";
				if (file.exists()) {
					rtn += fileExtend + ".gif";

				} else {
					rtn += "unknown.gif";
				}
				rtn += "</PICPATH>";
				rtn += "</DATA>";
			}

		}

		rtn += "</RECORDSET>";

		return rtn;
	}
	/**
	 * 将附件列表生成JSON
	 * @param coll
	 * @return
	 */

	public static String toAttachmentJSONString(Collection<Attachment> coll) {
		String rtn = "";
		rtn += "{\"RECORDSET\":[";
		boolean isFirst = true;
		for(Attachment vo:coll){
			if (vo != null && vo.getId() > 0) {
				if(isFirst){
					isFirst = false;
				} else {
					rtn += ",";
				}
				rtn += "{";

				rtn += "\"AID\":\"";
				rtn += vo.getId();
				rtn += "\", ";
				
				rtn += "\"DOCID\":\"";
				rtn += vo.getDocumentId();
				rtn += "\", ";

				rtn += "\"AFILEPATH\":\"";
				rtn += vo.getFilePath();
				rtn += "\", ";

				rtn += "\"AFILESHORTPATH\":\"";
				if(vo.getFileName() != null && !ObjectUtils.equals("", vo.getFileName()))
					rtn += vo.getFileName();
				else 
					rtn += vo.getFileShortpath();
				rtn += "\", ";

				rtn += "\"AFILESIZE\":\"";
				rtn += getTypeFileSize(vo.getFileSize());
				rtn += "\", ";
				
				rtn += "\"REMARK\":\"";
				rtn += vo.getRemark();
				rtn += "\", ";

				String fileName = vo.getFileShortpath();
				String fileExtend = fileName.substring(
						fileName.lastIndexOf(".") + 1, fileName.length())
						.toLowerCase();
				File file = new File("/" + fileExtend + ".gif");

				rtn += "\"PICPATH\":\"";
				if (file.exists()) {
					rtn += fileExtend + ".gif";

				} else {
					rtn += "unknown.gif";
				}
				rtn += "\"";
				rtn += "}";
			}

		}

		rtn += "]}";

		return rtn;
	}
    /**
     * @author lan
     * 附件大小格式转换
     * @param filesize
     * @return
     */
	public static String getTypeFileSize(double filesize)
	{   String typeFile = "";
	    DecimalFormat format = new DecimalFormat("0.00"); 
		if (filesize >= 1073741824)
			typeFile = Math.round(filesize / 1073741824 * 100) / 100 + "GB";
		else if (filesize>= 1048576)
			typeFile = format.format(filesize / 1048576) + "MB";
        else if (filesize >= 1024)
        	typeFile = Math.round(filesize / 1024 * 100) / 100 + "KB";
		else if (filesize > 0)
			typeFile = filesize + "B";
		else
			typeFile = "-";

	   return typeFile;
	}
	/**
	 * @author lan
	 * 检测服务器上目录是否存在，不存在则创建
	 * 
	 * @param siteid
	 * @return
	 * @throws Exception
	 */
	public static String getRealPath(String fpath) throws Exception {
		String fullpath = "";
			if(fpath !=null && fpath.length()>0)
			{
				if(Constants.IS_REALPATH)
				{
				fullpath = Constants.ABSOLUTE_PATH+fpath;
				}
				else
				{
					fullpath = ServletActionContext.getServletContext().getRealPath(fpath);
				}
			}
			else
			{
				if(Constants.IS_REALPATH)
				{
					fullpath = Constants.UPLOADS_PATH;
				}
				else
				{
			   fullpath = ServletActionContext.getServletContext().getRealPath(Constants.UPLOADS_PATH);
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

	/**
	 * @author lan
	 * 对文件进行唯一命名
	 * 
	 * @return
	 */
	public synchronized Long getSequence() {
		long result = System.currentTimeMillis();
		if (result == millis) {
			old++;
			result = millis * base + old;
		} else {
			millis = result;
			result *= base;
			old = 0;
		}
		return result;
	}
	public String getFileShortpath(String _filePath)
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
	public List<Attachment> getAttachsByDocId(Long docId)
	{
		List<Attachment> list = findBy("documentId", docId);
		return (list == null) ? new ArrayList():list; 
	}
	
	/**
	 * 分页查找附件列表(资料库用)
	 * @param id
	 * @param name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page findAsPage(Long id, String name, int pageIndex, int pageSize)
			throws Exception {
		Limit limit = new Limit (pageIndex, pageSize, null, "id", false);
		CriteriaSetup criteriaSetup = new CriteriaSetup ();
		criteriaSetup.setInOrder(org.hibernate.criterion.Order.desc("id"));
		List<Criterion> addCriterions = new ArrayList<Criterion>();
		addCriterions.add(Restrictions.eq("documentId", id));
		addCriterions.add(Restrictions.eq("name", name));
		criteriaSetup.setAddCriterions(addCriterions);
		return super.findECPage(limit, criteriaSetup);
	}
}
