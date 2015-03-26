<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script type="text/javascript"> 

  //点击树节点执行的方法

function onclickItem(value) {
   if(isNaN(value)){
    return ;
   }else{
     window.returnValue = value+";"+tree${siteid}.getItemText(value);
     window.close();
   }
   
}
//拖动时，事件
function myDragHandler(idFrom,idTo){

 return false;
 }


var treeSelectId="";
</script>
</head>
<body>
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
            <td width="100%" align="left" hight="100%"> 
  <div id="siteTree_${siteid}"></div> 
            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript"> 
<!--		
  <cms:CmsAuth resCode="CMS_SITE_VIEW" objectId="${siteid}" objectType="1">   
  tree${siteid}=new dhtmlXTreeObject("siteTree_${siteid}","100%","100%",'S_${siteid}');
   //enable Drag&Drop
  tree${siteid}.enableDragAndDrop(1);
   //set my Drag&Drop handler
  tree${siteid}.setDragHandler(myDragHandler);
  tree${siteid}.loadXML("${ctx}/cms/site!publicXml.action?id=${siteid}");
  tree${siteid}.setOnClickHandler(onclickItem);
  tree${siteid}.setXMLAutoLoading("${ctx}/cms/site!PublicChannelxml.action?id=${siteid}");
  //tree${site.oid}.setOnRightClickHandler(myRightClickHandler);
  </cms:CmsAuth>
-->
</script>
