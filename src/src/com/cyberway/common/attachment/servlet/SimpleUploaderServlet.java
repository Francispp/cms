package com.cyberway.common.attachment.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ognl.Ognl;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.attachment.domain.FCKFile;
import com.cyberway.common.attachment.service.FCKFileManagerService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;

public class SimpleUploaderServlet extends HttpServlet
{

    public SimpleUploaderServlet()
    {
    }

    public void init()
        throws ServletException
    {
        debug = (new Boolean(getInitParameter("debug"))).booleanValue();
        if(debug)
            System.out.println("\r\n---- SimpleUploaderServlet initialization started ----");
        baseDir = getInitParameter("baseDir");
        enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
        if(baseDir == null)
            baseDir = "/UserFiles/";
        String realBaseDir = getServletContext().getRealPath(baseDir);
        File baseFile = new File(realBaseDir);
        if(!baseFile.exists())
            baseFile.mkdir();
        allowedExtensions = new Hashtable(3);
        deniedExtensions = new Hashtable(3);
        allowedExtensions.put("File", stringToArrayList(getInitParameter("AllowedExtensionsFile")));
        deniedExtensions.put("File", stringToArrayList(getInitParameter("DeniedExtensionsFile")));
        allowedExtensions.put("Image", stringToArrayList(getInitParameter("AllowedExtensionsImage")));
        deniedExtensions.put("Image", stringToArrayList(getInitParameter("DeniedExtensionsImage")));
        allowedExtensions.put("Flash", stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
        deniedExtensions.put("Flash", stringToArrayList(getInitParameter("DeniedExtensionsFlash")));
        if(debug)
            System.out.println("---- SimpleUploaderServlet initialization completed ----\r\n");
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	//得到siteId
    	URL url=new URL(request.getRequestURL().toString());
    	String siteHttp=url.getHost();
    	int port=url.getPort();
    	SiteManagerService siteManagerService=(SiteManagerService)ServiceLocator.getBean("siteManagerService");
    	Long siteId=(long) 0;
	    try {
			CmsSite cmsSite=siteManagerService.getSite(siteHttp, port);
			siteId=cmsSite.getOid();	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        if(debug)
            System.out.println("--- BEGIN DOPOST ---");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String typeStr = request.getParameter("Type");
        String docId = request.getParameter("docId");
        String currentPath = baseDir + typeStr;
        
       
		// linux系统下renameTo方法只能起到改名作用
		// inFile.renameTo(outputFile);
        
        String currentDirPath = getServletContext().getRealPath(currentPath)+"/"+siteId+"/"+docId;
        currentPath = request.getContextPath() + currentPath+"/"+siteId+"/"+docId;
        
        if(debug)
            System.out.println(currentDirPath);
        String retVal = "0";
        String newName = "";
        String fileUrl = "";
        String errorMessage = "";
        if(enabled)
        {
            DiskFileUpload upload = new DiskFileUpload();
            try
            {
                List items = upload.parseRequest(request);
                Map fields = new HashMap();
                for(Iterator iter = items.iterator(); iter.hasNext();)
                {
                    FileItem item = (FileItem)iter.next();
                    if(item.isFormField())
                        fields.put(item.getFieldName(), item.getString());
                    else
                        fields.put(item.getFieldName(), item);
                }

                FileItem uplFile = (FileItem)fields.get("NewFile");
                String fileNameLong = uplFile.getName();
                fileNameLong = fileNameLong.replace('\\', '/');
                String pathParts[] = fileNameLong.split("/");
                String fileName = pathParts[pathParts.length - 1];
                String nameWithoutExt = getNameWithoutExtension(fileName);
                String ext = getExtension(fileName);
                File pathToSave = new File(currentDirPath, fileName);
                 fileUrl = currentPath +"/" + fileName;
    
         		// 检测父文件夹是否正在
         		if (!pathToSave.getParentFile().exists()) {
         			pathToSave.getParentFile().mkdirs();

         		}
                if(extIsAllowed(typeStr, ext))
                {
                    for(int counter = 1; pathToSave.exists(); counter++)
                    {
                        newName = nameWithoutExt + "(" + counter + ")" + "." + ext;
                        fileUrl = currentPath  +"/"+ fileName;
                        retVal = "201";
                        pathToSave = new File(currentDirPath, newName);
                    }
                    
                    
                    uplFile.write(pathToSave);
                    FCKFileManagerService FCKFileService = (FCKFileManagerService)ServiceLocator.getBean("FCKFileManagerService");
                    FCKFile fckFile = FCKFile.class.newInstance();
                    Ognl.setValue("fileName", fckFile, fileName);
                    Ognl.setValue("fileExt", fckFile, ext);
                    Ognl.setValue("fileUrl", fckFile, fileUrl);
                    Loginer loginer = (Loginer)request.getSession().getValue(Loginer.LOGININFO_SESSION);
                    if(loginer != null)
                    	 Ognl.setValue("userId", fckFile, loginer.getUserid());
                    if(StringUtils.isNotEmpty(docId))
                    	Ognl.setValue("docId", fckFile, Long.parseLong(docId));
                    long totalToRead = request.getContentLength();
                    Ognl.setValue("fileSize", fckFile, totalToRead);
                    FCKFileService.saveOrUpdate(fckFile);
                } else
                {
                    retVal = "202";
                    errorMessage = "";
                    if(debug)
                        System.out.println("Invalid file type: " + ext);
                }
            }
            catch(Exception ex)
            {
                if(debug)
                    ex.printStackTrace();
                retVal = "203";
            }
        } else
        {
            retVal = "1";
            errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
        }
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.OnUploadCompleted(" + retVal + ",'" + fileUrl + "','" + newName + "','" + errorMessage + "');");
        out.println("</script>");
        out.flush();
        out.close();
        if(debug)
            System.out.println("--- END DOPOST ---");
    }

    private static String getNameWithoutExtension(String fileName)
    {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    private String getExtension(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private ArrayList stringToArrayList(String str)
    {
        if(debug)
            System.out.println(str);
        String strArr[] = str.split("\\|");
        ArrayList tmp = new ArrayList();
        if(str.length() > 0)
        {
            for(int i = 0; i < strArr.length; i++)
            {
                if(debug)
                    System.out.println(i + " - " + strArr[i]);
                tmp.add(strArr[i].toLowerCase());
            }

        }
        return tmp;
    }

    private boolean extIsAllowed(String fileType, String ext)
    {
        ext = ext.toLowerCase();
        ArrayList allowList = (ArrayList)allowedExtensions.get(fileType);
        ArrayList denyList = (ArrayList)deniedExtensions.get(fileType);
        if(allowList.size() == 0)
            return !denyList.contains(ext);
        if(denyList.size() == 0)
            return allowList.contains(ext);
        else
            return false;
    }

    private static String baseDir;
    private static boolean debug = false;
    private static boolean enabled = false;
    private static Hashtable allowedExtensions;
    private static Hashtable deniedExtensions;

}

