package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * Word控件的过滤器
 *
 */
public class WordEditWriter extends ComponentWriter
{
	public WordEditWriter()
	{
		super ("Word", "DIV");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token) 
	{
		return false;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement) 
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
	
		getComponentIdStack().push(id);
		getMarkupWriter().element(getElementName(), "id", getComponentIdStack().peek());
		//		增加属性
		/*getMarkupWriter().attributes("id","TANGER_OCX");
		getMarkupWriter().attributes("name","TANGER_OCX");
		getMarkupWriter().attributes("classid","clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404");
		getMarkupWriter().attributes("codeBase","/common/OfficeControl.cab#version=4,0,0,8");
		getMarkupWriter().attributes("width","100%");
		getMarkupWriter().attributes("height","600");*/
	
	}
	
	@Override
	protected void writeComponentEnd() 
	{
		
		//获得名称
		//String name=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "name");
		String id = getComponentIdStack().peek();
		String name=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		String templateUrl=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "templateUrl");;//templateUrl
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		StringBuffer sb=new StringBuffer();
		//增加参数
		/*sb.append(" <param name=\"ProductCaption\" value=\"AMWAY\">\n");
		sb.append(" <param name=\"ProductKey\" value=\"446E3EBBFABC7719DBF702BA9BD10ED017342574\">\n");
		sb.append(" <param name=\"BorderStyle\" value=\"1\">\n");
		sb.append(" <param name=\"BorderColor\" value=\"14402205\">\n");
		sb.append(" <param name=\"TitlebarColor\" value=\"14402205\">\n");
		sb.append(" <param name=\"TitlebarTextColor\" value=\"0\">\n");
		sb.append(" <param name=\"Caption\" value=\"表单\">\n");
		sb.append(" <param name=\"IsShowToolMenu\" value=\"-1\">\n");
		sb.append(" <param name=\"IsNoCopy\" value=\"-1\">\n");
		sb.append(" <SPAN STYLE=\"color:red\">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>\n");*/
		String src="${ctx}/cms/document!editOfficeItem.action?type=0&id=${domain.id}&optName=wordEditor"+id+"&templateUrl=";	
		if(StringUtil.isEmpty(templateUrl))
			templateUrl="";
		else{
			try{
			sb.append(" <% com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(\""+templateUrl+"\",request.getRealPath(\""+templateUrl+"\"));%>\n");		 

			//templateUrl=java.net.URLEncoder.encode(templateUrl,"GB2312");
			}catch(Exception e){
				
			}
		}
		
	    sb.append(" <c:if test=\"${empty domain."+name+"}\" >\n");
		sb.append("<IFRAME ID='wordEditor"+id+"' src='"+src+templateUrl+"&siteId=${siteId}"+"' frameborder='0' scrolling='no' width='100%' height='100%' style='height:600px'></IFRAME>");
		sb.append(" </c:if>\n");
	    sb.append(" <c:if test=\"${!empty domain."+name+"}\" >\n");
		sb.append("<IFRAME ID='wordEditor"+id+"' src='"+src+"${domain."+name+"}&siteId=${siteId}' frameborder='0' scrolling='no' width='100%' height='100%' style='height:600px'></IFRAME>");
		sb.append(" </c:if>\n");		
		getMarkupWriter().writeRaw(sb.toString());
		super.writeComponentEnd();

		//增加函数内容及JS
		sb=new StringBuffer();
		sb.append("<ww:hidden name=\"domain."+name+"\" id=\""+name+"\"/>");
		/*sb.append("<script src=\"${ctx}/common/officecontrol/ntkoocx.js\" type=\"text/javascript\"></script>\n");
		sb.append("<script src=\"${ctx}/common/officecontrol/word.js\" type=\"text/javascript\"></script>\n");*/
		sb.append("<SCRIPT LANGUAGE=javascript>\n");
		sb.append("<!--\n");
		//sb.append(" var TANGER_OCX_OBJ,FileNameOcx ;");
		
		/*sb.append(" function ev_onLoadOcx(){\n");
		sb.append(" TANGER_OCX_OBJ = document.all.item(\"TANGER_OCX\");\n");
		sb.append(" if('${domain."+name+"}'=='')\n");
		if (StringUtils.isBlank(templateUrl))//若无模板，则创建新的
		{		
		 //sb.append(" TANGER_OCX_OBJ.CreateNew(\"Word.Document\");\n");
		}else{//若有模板，打开模板
		 //sb.append(" TANGER_OCX_OBJ.OpenFromURL(\"<%=BASEURL%>/"+templateUrl+"\");\n");
		 //同步更新模板文件
		 sb.append(" <% com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(\""+templateUrl+"\",request.getRealPath(\""+templateUrl+"\"));%>\n");
		}
		 sb.append(" else{\n");
		 //同步更新模板文件
		 //sb.append(" <% com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(\""+templateUrl+"\",request.getRealPath(\""+templateUrl+"\"));%>\n");		 
		 //sb.append(" FileNameOcx=\"<%=BASEURL%>/${domain."+name+"}\";\n");
		 //sb.append(" TANGER_OCX_OBJ.OpenFromURL(FileNameOcx);\n");		
		 sb.append(" }\n");*/
		 /*sb.append(" <c:choose>\n");
		 sb.append(" <c:when test=\"${!isEdit}\" >\n");//若为只读
		 sb.append("   TANGER_OCX_SetReadOnly(true);\n");
		 sb.append("   TANGER_OCX_EnableFileNewMenu(false);\n");
		 sb.append("   TANGER_OCX_EnableFileOpenMenu(false);\n");
		 sb.append("   TANGER_OCX_EnableFileCloseMenu(false);\n");
		 sb.append("   TANGER_OCX_EnableFileSaveMenu(false);\n");
		 sb.append(" </c:when>\n");
		 sb.append(" <c:otherwise>\n");
		 sb.append("  <c:if test=\"${isEdit and (!empty domain."+name+")}\">\n");//若不为新增
		 sb.append("    TANGER_OCX_SetReadOnly(false);\n");
		 sb.append("    TANGER_OCX_SetMarkModify(true);\n");
		 sb.append("    TANGER_OCX_ShowRevisions(false);\n");
		 sb.append(" </c:if>\n");
		 sb.append(" </c:otherwise>\n");
		 sb.append(" </c:choose>\n");		
		sb.append(" }\n");*/
		
		//文件路径:
		//保存时调用 Constants.INFO_OFFICE_PATH+${domain.id}+/word.doc
		//文件名
		String fileName=name;
		if(StringUtil.isEmpty(fileName))
			fileName="word";
		sb.append(" function "+fileName+"Beforsave() {\n");
		sb.append("  if($(\""+name+"\").value=='')\n");			
		sb.append("   $(\""+name+"\").value=\""+Constants.INFO_OFFICE_PATH+"${siteId}/${domain.id}/"+fileName+".doc\";\n");
		sb.append("  var iframe"+id+"=document.frames['wordEditor"+id+"'];//document.all.wordEditor"+id+";\n");
		//sb.append("  iframe"+id+".TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();\n");
		//sb.append("  var cmdSaveocxfiles=\"<%=BASEURL%>/base/attachment!officeSaveFile.action\";\n");
		//sb.append("  var cmdSave=\"<%=BASEURL%>/base/attachment!officeSaveFile.action\";\n");
		sb.append("  iframe"+id+".PublishAsHTMLToURL(\""+fileName+".html\");\n");
		sb.append("  var wordIframe= iframe"+id+".SaveToURL(\""+fileName+".doc\"); \n");  
		sb.append("if(wordIframe==false){return 'error';}else{ return 'succ';}");
		sb.append(" }\n");
		sb.append(" AddBeforsaveJS('"+fileName+"Beforsave();');\n");
		//sb.append(" ev_onLoadOcx();\n");
		sb.append(" -->\n");
		sb.append(" </SCRIPT>\n");
		getMarkupWriter().writeRaw(sb.toString());
	}
}
