<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page buffer="50kb"%>
<%@ include file="/common/taglibs.inc"%>
<%@page import="com.cyberway.cms.Constants"%>
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>资料信息编辑</title>
    <meta content="IE=7" http-equiv="X-UA-Compatible" />
    <%@ include file="/common/meta.inc"%>
    <link href="${default_style}" rel="stylesheet" type="text/css">
    <%@ include file="/common/js.inc"%>
	<script type="text/javascript" src="${ctx}/common/officecontrol/OfficeContorlFunctions.js"></script>
	<SCRIPT LANGUAGE=javascript>
		function checkUpHaveDoc(FileNameOcx, OFFICE_CONTROL_OBJ){
		  var myAjax = new Ajax.Request('/cms/document!checkUpHaveDoc.action',{
			  method: 'post',
			  parameters: "path=${templateUrl}",
			  onComplete:function(r){
				  if(r.responseText == "1"){
					  NTKO_OCX_OpenDoc(FileNameOcx);					  					  
					 }else
						 OFFICE_CONTROL_OBJ.CreateNew("Word.Document");
					}
			});
		 }
	</script>
 <%
 String templateUrl=request.getParameter("templateUrl");
 
 if(com.cyberway.cms.Constants.IS_REALPATH&& !com.cyberway.core.utils.StringUtil.isEmpty(templateUrl)){
 //templateUrl=java.net.URLDecoder.decode(templateUrl,"GB2312"); 
 //templateUrl=new String(templateUrl.getBytes("ISO-8859-1"));
 //System.out.println("templateUrl:"+templateUrl);
 if(templateUrl.indexOf(com.cyberway.cms.Constants.INFO_OFFICE_PATH)<0)
  //同步更新模板文件
  com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFile(templateUrl,request.getRealPath(templateUrl));
 else
  //同步更新记录目录下的文件
  com.cyberway.cms.utils.OfficeFileUtil.updateOfficeFileByDocId(request.getParameter("id"),request.getRealPath("/"));
 }
%>
		
   

   
   	
   	
   	
   	<SCRIPT LANGUAGE=javascript>
 //加载ocx
 function ev_onLoadOcx(){
   	OFFICE_CONTROL_OBJ = document.all("TANGER_OCX");
   	//initCustomMenus();
 if('${templateUrl}'==''){
 var _type=${type};
 if(_type==1)//Excel类型
	 OFFICE_CONTROL_OBJ.CreateNew("Excel.Sheet");
  else if(_type==2) //PPT类型
	  OFFICE_CONTROL_OBJ.CreateNew("PowerPoint.Show");
   else //Word,默认为word类型
    OFFICE_CONTROL_OBJ.CreateNew("Word.Document");
 }else{
  FileNameOcx="${ctx}"+"<%=templateUrl%>";
  checkUpHaveDoc(FileNameOcx, OFFICE_CONTROL_OBJ);
  
  //NTKO_OCX_OpenDoc(FileNameOcx);
 }
 <c:choose>
 <c:when test="${!isEdit}" >
   //TANGER_OCX_SetReadOnly(true);
 </c:when>
 <c:otherwise>
  <c:if test="${isEdit and (!empty templateUrl)}">
    //TANGER_OCX_SetReadOnly(false);
 </c:if>
 </c:otherwise>
 </c:choose>
 <c:if test="${!empty optName}" >
 try{
 //alert("${optName}");
 dyniframesizeforparent("${optName}");
 
 }catch(e){
 }
</c:if>
 }

  var cmdSaveocxfiles="${ctx}/cms/attachment!officeSaveFile.action?docid=${id}&siteId=${siteId}";
  var cmdSave="${ctx}/cms/attachment!officeSaveFile.action?docid=${id}&siteId=${siteId}";
function PublishAsHTMLToURL(filename){ 
 OFFICE_CONTROL_OBJ.PublishAsHTMLToURL(cmdSaveocxfiles,"_file","",filename,0);
}

function SaveToURL(filename){
	var restr=OFFICE_CONTROL_OBJ.SaveToURL (cmdSave,"_file","",filename,0); 
	 var maxResultData=<%=((Integer.parseInt((Constants.INFO_OFFICE_SIZE))*1024)*1024)%>;
 	if(parseInt(restr)>parseInt(maxResultData)){
 		alert("输入文件内容过多，最多只允许输入"+"<%= Constants.INFO_OFFICE_SIZE%>"+"M内容!");
		return false;
 		
 }else{
	 return true;
	 
 }
}

function beforsave1(){
	alert(filename);
	alert(SaveToURL(filename));
	return 	SaveToURL(filename);
	
}
 </SCRIPT>
</head>
<body onload="ev_onLoadOcx()" leftmargin=0 rightmargin=0 topmargin=0 bottommargin=0 scroll=no>
                <div id="officecontrol">
                <script type="text/javascript" src="${ctx}/common/officecontrol/ntkoofficecontrol.js"></script>
                
            </div>
<SCRIPT LANGUAGE=javascript>
//ev_onLoadOcx();
function dyniframesize(iframename) {
  var pTar = null;
  var pTd = null;
  
  if (document.getElementById){
    pTar = document.getElementById(iframename);
    pTd = document.getElementById("officetd")
  }else{
    eval('pTar = ' + iframename + ';');
    eval('pTd = officetd;');
  }
  if (pTar && !window.opera){
    //begin resizing iframe
    pTar.style.display="block"
       
    if (pTar.contentDocument && pTar.contentDocument.body.offsetWidth){
      //ns6 syntax
      pTar.width = pTar.contentDocument.body.offsetWidth+FFextraWidth; 
    }else if (pTar.Document && pTar.Document.body.scrollWidth){
      //ie5+ syntax
      pTar.width = pTar.Document.body.scrollWidth;
    }    
    
    if (pTar.width < 600 &&  pTar.width > 100) {
    	pTar.width = 600;
    }else{
    	pTar.width = 601;
    }
    if(pTd.style.width != ""){
        pTar.width = pTd.style.width;
    }else{
        if(pTd.width != "")
            pTar.width = pTd.width;
    }
    
    if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){
      //ns6 syntax
      pTar.height = pTar.contentDocument.body.offsetHeight+FFextraHeight; 
    }else if (pTar.Document && pTar.Document.body.scrollHeight){
      //ie5+ syntax
      pTar.height = pTar.Document.body.scrollHeight + 20;
    }
    
    if (pTar.height < 172 && pTar.height > 168) {
    	pTar.height = 400;
    }
  }
}

</SCRIPT>
<%@ include file="/common/foot.inc"%>
</body>
</html>
