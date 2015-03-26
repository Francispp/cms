<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.jspsmart.upload.*"%>
<%SmartUpload su = new SmartUpload();
    su.initialize(pageContext);
	//su.setContentDisposition(null);info.getFileContentType()		
	String filePathName=(String)request.getAttribute("FlowFilePathName");
	String fileName=(String)request.getAttribute("FlowFileName");
	if(filePathName!=null){	 
	 su.setContentDisposition(null);
	 System.out.println("filePathName:"+filePathName+"__fileName:"+fileName);
	 su.downloadFile(filePathName+java.io.File.separatorChar+fileName,null,com.cyberway.core.utils.StringUtil.toUtf8String(fileName));
}
%>