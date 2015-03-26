package com.cyberway.cms.internal.template.token;


import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;

public class DownloadWriter extends ComponentWriter{
	public DownloadWriter()
	{
		super("Download","div");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		getMarkupWriter().writeRaw("<%@ page import=\"com.cyberway.common.attachment.service.AttachmentManagerService\"%>\n\r");
		getMarkupWriter().writeRaw("<%@ page import=\"com.cyberway.common.attachment.domain.Attachment\"%>\n\r");
		getMarkupWriter().writeRaw("<%@ page import=\"com.cyberway.core.utils.ServiceLocator\"%>\n\r");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		if("attachment_page".equals(style)){
			String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
			String pageSize = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "pageSize");
			
			if(StringUtils.isBlank(pageSize))
				pageSize="10";
			
			if (StringUtils.isBlank(id))
			{
				id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
			}
			getComponentIdStack().push(id);
			BodyBuilder jsp = new BodyBuilder ();
			jsp.begin(); 
			jsp.addln("String pageIndex = request.getParameter (\"pageIndex\");");
			jsp.addln("if(pageIndex == null || pageIndex.length() < 1) { pageIndex = \"1\"; }");
			
			jsp.addln("AttachmentManagerService attachmentManagerService = (AttachmentManagerService)ServiceLocator.getBean (\"attachmentManagerService\");");
			jsp.addln("CriteriaSetup criteriaSetup = new CriteriaSetup ();");  
			jsp.addln("Page datas = attachmentManagerService.findAsPage (Long.valueOf(request.getParameter(\"id\")), \""+name+"\", Integer.valueOf(pageIndex), "+ pageSize +" );");
			
			jsp.addln("request.setAttribute (\"_data"+id+"\",datas);");
			jsp.addln("java.util.List<Attachment> attachments = (java.util.List<Attachment>)datas.getResult();");
			StringBuffer sb = new StringBuffer();
			sb.append("out.write(\"<table width='100%%' class='training_result_table2' border='0' cellSpacing='0' cellPadding='0'>\");");
			sb.append("\n");
			sb.append("out.write(\"<tr><th align='center'>标题</th><th align='center'>上传时间</th><th align='center'>下载</th></tr>\");");
			sb.append("\n");
			sb.append("for(int i=0;i<attachments.size();i++){");
			sb.append("\n");
			sb.append("String classText = \"\";");
			sb.append("\n");
			sb.append("Attachment attachment = attachments.get(i);");
			sb.append("\n");
			sb.append("if(i%%2==1){classText=\"class='line1'\";}");
			sb.append("\n");
			sb.append("String fileName = attachment.getFileName();");
			sb.append("\n");
			sb.append("int pointIndex = fileName.lastIndexOf(\".\");");
			sb.append("\n");
			sb.append("if(pointIndex!=-1){");
			sb.append("\n");
			sb.append("fileName = fileName.substring(0,pointIndex);");
			sb.append("\n");
			sb.append("}");
			sb.append("\n");
			sb.append("String updateTime = new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(attachment.getUpdateTime());");
			sb.append("\n");
			sb.append("out.write(\"<tr>\");");
			sb.append("\n");
			sb.append("out.write(\"<td \"+classText+\"><img width='16' height='14' src='/images/training_dot1.gif'/>\"+fileName+\"</td>\");");
			sb.append("\n");
			sb.append("out.write(\"<td align='center' \"+classText+\">\"+updateTime+\"</td>\");");
			sb.append("\n");
			sb.append("out.write(\"<td align='center' \"+classText+\"><a class='download' href='/cms/filedownload!fileDownloadById.action?id=\"+attachment.getId()+\"&docid=\"+request.getParameter(\"id\")+\"'/></td>\");");
			sb.append("\n");
			sb.append("out.write(\"</tr>\");");
			sb.append("\n");
			sb.append("}");
			sb.append("\n");
			sb.append("out.write(\"</table>\");");
			jsp.addln(sb.toString());
			jsp.end();
			getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
			
			getMarkupWriter().element("cms:tablePager", 
					"pageIndex", "#request._data"+id+".currentPageNo",
					"pageSize", "#request._data"+id+".pageSize",
					"recordCount", "#request._data"+id+".totalCount");
			getMarkupWriter().end();
			
		} else {
			BodyBuilder jsp = new BodyBuilder();
			jsp.begin();
			jsp.addln("AttachmentManagerService attachmentService = (AttachmentManagerService) ServiceLocator.getBean(\"attachmentManagerService\");");
			jsp.addln("java.util.List<Attachment> list = attachmentService.find(\"from Attachment where documentId=? and name='"+name+"' order by id asc\", Long.valueOf(request.getParameter(\"id\")));");
			jsp.addln("if(list.size()>0){");
			
			if(!"attachment_default".equals(style))
			{
				StringBuffer sb = new StringBuffer();
				sb.append("out.write(\"<table class='mo_table'>\");");
				sb.append("\n");
				sb.append("out.write(\"<tr class='mo_title_tr'><td> 文件名</td><td> 更新日期</td><td>文件格式</td><td>文件大小</td><td>文件下载</td></tr>\");");
				sb.append("\n");
				sb.append("for (Attachment attachment : list) {");
				sb.append("\n");
				sb.append("String name = attachment.getFileName();");
				sb.append("\n");
				sb.append("name = name.substring(0,name.lastIndexOf(\".\"));");
				sb.append("\n");
				sb.append("String type = attachment.getFileName().substring(attachment.getFileName().lastIndexOf(\".\")+1);");
				sb.append("\n");
				sb.append("String updateTime = new java.text.SimpleDateFormat(\"yyyy-MM-dd\").format(attachment.getUpdateTime());");
				sb.append("\n");
				sb.append("double fileSize = attachment.getFileSize();");
				sb.append("\n");
				sb.append("out.write(\"<tr class='mo_blank_tr'><td colspan='5'>&nbsp;</td></tr>\");");
				sb.append("\n");
				sb.append("out.write(\"<tr class='mo_content_tr'><td class='firstTd'><span class='iconReader'></span>\" + name + \"\");");
				sb.append("\n");
				sb.append("out.write(\"</td><td>\" + updateTime + \"</td><td>\" + type + \"</td><td>\" + fileSize + \"</td>\");");
				sb.append("\n");
				sb.append("out.write(\"<td><a class='iconLoad' href='/cms/filedownload!fileDownloadById.action?id=\"+attachment.getId()+\"&docid=\"+request.getParameter(\"id\")+\"'></a></td></tr>\");}");
				sb.append("\n");
				sb.append("out.write(\"</table>\");");
				jsp.addln(sb.toString());
			} else {//系统默认
				jsp.addln("out.println(\"<div id='attachment' class='"+style+"'>附件:<ul>\");");
				jsp.addln("for (Attachment attachment : list) {");
					jsp.addln("out.print(\"<li><a href='/cms/filedownload!fileDownloadById.action?id=\");");
					jsp.addln("out.print(attachment.getId());");
					jsp.addln("out.print(\"&docid=\");");
					jsp.addln("out.print(request.getParameter(\"id\"));");
					jsp.addln("out.print(\"'>\");");
					jsp.addln("out.print(attachment.getFileName());");
					jsp.addln("out.println(\"&nbsp;&nbsp;<img src='/images/btn_in_down.png'/></a><li>\");");
					jsp.addln("}");
				jsp.addln("out.println(\"</ul></div>\");");
			}
			jsp.addln("}");
			jsp.end();
			getMarkupWriter().writeRaw("<%" +jsp.toString() + "%>");
		}
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}

}
