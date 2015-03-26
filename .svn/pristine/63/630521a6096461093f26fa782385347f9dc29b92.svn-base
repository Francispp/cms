<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
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
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript"> 
//随机数
var r = 10*Math.random();

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
   if(siteTree && siteTree.hasChildren(value)>0 ){//带子节点的，点击展开子节点        
      if(siteTree.getOpenState(value)==-1)
        siteTree.openItem(value);
      else
       siteTree.closeItem(value);
    }
    //节点(无子节点)可进入信息介面
     //myUrl='${ctx}/cms/document!adminList.action?channelId='+value+"&myTable_s_timeCreated=desc";
     myUrl='${ctx}/cms/document!adminList.action?channelId='+value+'&a='+r;
     top.setMainFrameUrl(myUrl);
   }
}
  
//拖动时，事件
function myDragHandler(idFrom,idTo){
 return false;
 }


var treeSelectId="";

</script>
</head>


<body class="tree-bg baseFrame" style="overflow-y:auto;">
<div >
 <c:forEach var="site" items="${sites}" varStatus="status"> 
  <div id="siteTree_${site.oid}" ></div> 
 </c:forEach>
 </div>
</div>

</body>
</html>
<script language="javascript"> 
<!--		
  <c:forEach var="site" items="${sites}" varStatus="status">
  <cms:CmsAuth resCode="CMS_DOCUMENT_VIEW" objectId="${site.oid}" objectType="1">   
  tree${site.oid}=new dhtmlXTreeObject("siteTree_${site.oid}","100%","100%",'S_${site.oid}');
  //enable Drag&Drop
  tree${site.oid}.enableDragAndDrop(1);
   //set my Drag&Drop handler
  tree${site.oid}.setDragHandler(myDragHandler);
  tree${site.oid}.loadXML("${ctx}/cms/site!xml.action?id=${site.oid}&loginType=1&siteName="+encodeURI(encodeURI("文档库")));
  tree${site.oid}.setItemText('T_${site.oid}','test');
  
  tree${site.oid}.setOnClickHandler(onclickItem);
  tree${site.oid}.setXMLAutoLoading("${ctx}/cms/site!channelxml.action?id=${site.oid}&loginType=1");
  //tree${site.oid}.setOnRightClickHandler(myRightClickHandler);  
  </cms:CmsAuth>
  </c:forEach>   
-->


</script>
