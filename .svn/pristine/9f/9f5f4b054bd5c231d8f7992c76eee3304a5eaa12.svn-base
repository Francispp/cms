<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>

<%

        File f = new File(request.getRealPath("/") + "dynamicPage/template.xml");
		if(!f.exists())
		{
			response.sendError(404,"File not found!");
			return;
		}
		
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
		OutputStream os = response.getOutputStream();
		response.reset(); //非常重要
		
		//纯下载方式		
		response.setHeader("Content-Disposition", "attachment; filename=" + f.getName()); 
		response.setContentType("bin;charset=utf-8"); 
	
		while((len = bis.read(buf)) >0)
			os.write(buf,0,len);
		bis.close();
		os.close();

%>

