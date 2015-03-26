<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择频道</title>
 <%@ include file="/common/buffalo.inc"%>
<%@ include file="/common/meta.inc"%>

<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script type="text/javascript"> 

  //点击树节点执行的方法
function onclickItem(value) {
 var siteTree='';
 <c:forEach var="site" items="${sites}" varStatus="status">
  <cms:CmsAuth resCode="CMS_DOCUMENT_VIEW" objectId="${site.oid}" objectType="1"> 
   var siId${site.oid}=tree${site.oid}.getSelectedItemId();
   if(siId${site.oid}!=null && siId${site.oid}!='' && value!=siId${site.oid}){
    tree${site.oid}.clearSelectedItems();
   }   
   if(siId${site.oid}!=null && value==siId${site.oid}){    
    siteTree=tree${site.oid};
   }
  </cms:CmsAuth>
  </c:forEach> 
  var myUrl='';
   if(isNaN(value)){
    return ;
   }else{//频道对象
         /** 子窗口向父窗口赋值 **/
 		dialogArguments.document.getElementById("channelId").value=value;
	    var channelNameText= siteTree.getItemText(value);
 		dialogArguments.document.getElementById("channelName").value=channelNameText;
 		//window.returnValue = value;
        close();
   }
}
//拖动时，事件
function myDragHandler(idFrom,idTo){

 return false;
 }


var treeSelectId="";
</script>
</head>
<body style="background:#F1F1F1;">
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
            <td width="100%" align="left" hight="100%"> 
 <c:forEach var="site" items="${sites}" varStatus="status"> 
  <div id="siteTree_${site.oid}"></div> 
 </c:forEach>
 <br>
            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript"> 
	
  <c:forEach var="site" items="${sites}" varStatus="status"> 
  tree${site.oid}=new dhtmlXTreeObject("siteTree_${site.oid}","100%","100%",'S_${site.oid}');
   //enable Drag&Drop
  tree${site.oid}.enableDragAndDrop(1);
   //set my Drag&Drop handler
  tree${site.oid}.setDragHandler(myDragHandler);
  tree${site.oid}.loadXML("${ctx}/cms/site!xml.action?id=${site.oid}&loginType=1");
  tree${site.oid}.setOnClickHandler(onclickItem);
  tree${site.oid}.setXMLAutoLoading("${ctx}/cms/site!channelxml.action?id=${site.oid}&loginType=1");
  //tree${site.oid}.setOnRightClickHandler(myRightClickHandler);  
  </c:forEach>   

</script>
