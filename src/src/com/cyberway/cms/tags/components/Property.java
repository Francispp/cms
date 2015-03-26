package com.cyberway.cms.tags.components;


import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.PageContext;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Component;

import com.cyberway.cms.Constants;
import com.cyberway.cms.utils.OfficeFileUtil;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.issue.util.TextUtils;
import com.opensymphony.xwork2.util.ValueStack;


public class Property extends Component
{

 public Property(ValueStack stack)
 {
     super(stack);
     escape = true;
 }

 public void setDefault(String defaultValue)
 {
     this.defaultValue = defaultValue;
 }

 public void setEscape(boolean escape)
 {
     this.escape = escape;
 }

 public void setValue(String value)
 {
     this.value = value;
 }

 public boolean start(Writer writer)
 {
     boolean result = super.start(writer);
     String actualValue = null;
     if(value == null)
         value = "top";
     else
     if(altSyntax() && value.startsWith("%{") && value.endsWith("}"))
         value = value.substring(2, value.length() - 1);
     if(isDate && StringUtils.isNotEmpty(formatType))
	 {
    	 java.util.Date date = (java.util.Date)getStack().findValue(value, java.util.Date.class);
    	 SimpleDateFormat formatDate = new SimpleDateFormat(formatType);
    	 if(date !=null)
    	 {
    		 actualValue = formatDate.format(date); 
    	 }
    	  
	 }
     if(actualValue==null)
     actualValue = (String)getStack().findValue(value, java.lang.String.class);
     try
     {
    	 if(actualValue==null && pageContext!=null){//若为空，则从pageContext中取
    		 String preValue=null;
    		 Object valueObj=null;
    		 if(value.indexOf(".")>0){
    			 preValue=value.substring(0, value.indexOf("."));
    			 try{
    			 if(pageContext.getAttribute(preValue)!=null)
    			 {
    				 
    				 valueObj=Ognl.getValue(value.substring(value.indexOf(".")+1), pageContext.getAttribute(preValue));
    				 
    			 }
    			 }catch(Exception e){
    				 e.printStackTrace();
    			 }
    			 }else{
    			 preValue=value;
    			 if(pageContext.getAttribute(preValue)!=null)
    			  valueObj=pageContext.getAttribute(preValue);
    		 }
    		 if(valueObj!=null)
    			 actualValue=valueObj.toString(); 
    	 }
    	 
         if(actualValue != null && !"".equals(actualValue)){
        	 if(isOffecOcx){//若为Office控件，输出为显示html内容的iframe
        		 if(actualValue.indexOf(".") !=-1)
        		 {
        		 String fileUrl=actualValue.substring(0, actualValue.indexOf("."));//文件路径
        		 StringBuffer sb=new StringBuffer();
        		 String htmlFilePath=fileUrl+".html";
        		 //解决amway 集群问题
        		 if(Constants.IS_REALPATH){
        			 String docid=actualValue.substring(0, actualValue.lastIndexOf("/"));
        			 docid=StringUtil.replace(docid, Constants.INFO_OFFICE_PATH, "");
        			 if(docid.indexOf("/")>0)
        				 docid=StringUtil.replace(docid, "/", "");
        			 OfficeFileUtil.updateOfficeFileByDocId(docid, getPageContext().getRequest().getRealPath("/"));
        			 //String docid,String outpath
        			/*try{
        			 FileUtil.update(Constants.ABSOLUTE_PATH+htmlFilePath, getPageContext().getRequest().getRealPath(htmlFilePath));
        			}catch(Exception e){
        				
        			}*/
        			}
        		 sb.append("<iframe id='officeframe_name' name='officeframe_name' width='100%' ");
        		 sb.append("src='"+htmlFilePath+"' ");//后缀为html
        		 sb.append(" marginwidth=0 marginheight=0 frameborder=0 allowtransparency='true' scrolling=auto onload=javascript:setTimeout('officeDyniframesize()',50);>");
        		 sb.append("</iframe>");
        		 writer.write(prepare(sb.toString()));
        		 }
        	 }else
             writer.write(prepare(actualValue));
         }else
         if(defaultValue != null)
             writer.write(prepare(defaultValue));
     }
     catch(IOException e)
     {
         LOG.info("Could not print out value '" + value + "'", e);
     }
     return result;
 }

 private String prepare(String value)
 {
     if(escape)
         return TextUtils.escapeForHTML(value);
     else
         return value;
 }

 private static final Log LOG;
 private String defaultValue;
 private String value;
 private boolean escape;
 private PageContext pageContext;
 private boolean isOffecOcx;//是否为Office控件，若为true,输现iframe
 private boolean isDate;
 private String formatType;

 static 
 {
     LOG = LogFactory.getLog(Property.class);
 }

public PageContext getPageContext() {
	return pageContext;
}

public void setPageContext(PageContext pageContext) {
	this.pageContext = pageContext;
}

public boolean getIsOffecOcx() {
	return isOffecOcx;
}

public void setIsOffecOcx(boolean isOffecOcx) {
	this.isOffecOcx = isOffecOcx;
}

public boolean getIsDate() {
	return isDate;
}

public void setIsDate(boolean isDate) {
	this.isDate = isDate;
}

public String getFormatType() {
	return formatType;
}

public void setFormatType(String formatType) {
	this.formatType = formatType;
}



}