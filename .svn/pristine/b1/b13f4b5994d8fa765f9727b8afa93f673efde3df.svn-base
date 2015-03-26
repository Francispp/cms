<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/menu/menu.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">

<script type="text/javascript"> 
//点击树节点执行的方法
function onclickItem(value) {
<c:forEach var="album" items="${albums}" varStatus="status"> 
 var siId${album.id}=tree${album.id}.getSelectedItemId();
 //清除其他树上已选中的项
 if(siId${album.id}!=null && siId${album.id}!='' && value!=siId${album.id}){
  tree${album.id}.clearSelectedItems();
 }
</c:forEach> 
var myUrl='${ctx}/base/${albumType}!copy.action';
var param='';
 if(isNaN(value)){
	 var pid=value.substring(2);
	 if(pid==${isdefaultValue}){
		 if('${isdefault}'==1){
			 if(!confirm('确定复制到相同的专辑中吗？')){
				 return ;	 
			 }
		 }
		 param="pageSize=${pageSize}&isdefault=1&keys=${keys}";
	 }else{
		 if('${id}'==pid){
			 if(!confirm('确定复制到相同的专辑中吗？')){
				 return ;	 
			 }		 }
		 param="pageSize=${pageSize}&albumId="+pid+"&keys=${keys}";	 
	 }
 }else{
	 if('${id}'==value){
		 if(!confirm('确定复制到相同的专辑中吗？')){
			 return ;	 
		 }
	 }
	 param="pageSize=${pageSize}&albumId="+value+"&keys=${keys}";
 }
	 new Ajax.Request(myUrl, {
	    method:'post',
	    parameters:param,
	    onSuccess: function(transport) {
	    	alert('复制成功');
	    	window.returnValue='true';
		    window.close();
	    },
	    onFailure: function(){alert('复制失败');window.close();}
	  });

}


var treeSelectId="";
//关闭所有右击菜单
function closeAllMenu() {
  HideAll("RootTreeMenu",0);
  HideAll("ItemTreeMenu",0);
<c:forEach var="album" items="${albums}" varStatus="status">
<cms:CmsAuth resCode="CMS_album_VIEW" objectId="${album.id}" objectType="1"> 
 HideAll("RootTreeMenu${album.id}",0);
</cms:CmsAuth>
</c:forEach> 
}

</script>
</head>
<body style="background:#F1F1F1;" onmouseup="OnMouseUp('RootTreeMenu');" oncontextmenu="window.event.returnValue=false">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<c:if test="${not empty session.ActionError}">
  <script>alertMessage("${session.ActionError}");</script>
  <% session.removeAttribute("ActionError"); %>
</c:if>
<table align="center" width="100%"  hight="100%" border="0">
     <tr>
            <td width="100%" align="left" hight="100%"> 
 <c:forEach var="album" items="${albums}" varStatus="status"> 
  <div id="albumTree_${album.id}"></div> 
 </c:forEach>
 <br>
</td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript">

<!--
  <c:forEach var="album" items="${albums}" varStatus="status">
  tree${album.id}=new dhtmlXTreeObject("albumTree_${album.id}","100%","100%",'S_${album.id}');
  //tree${album.id}.enableDragAndDrop(1);
  tree${album.id}.loadXML("${ctx}/base/album!xml.action?isdefaultValue=${isdefaultValue}&isdefault=${album.isdefault}&id=${album.id}");
  tree${album.id}.setOnClickHandler(onclickItem);
  tree${album.id}.setXMLAutoLoading("${ctx}/base/album!itemAlbum.action?isdefault=${album.isdefault}&id=${album.id}");
  
  var RootTreeMenu${album.id} = new RightMenu("RootTreeMenu${album.id}");  
  document.writeln(RootTreeMenu${album.id}.GetMenu());   
  </c:forEach> 
  var RootTreeMenu = new RightMenu("RootTreeMenu");
  document.writeln(RootTreeMenu.GetMenu()); 
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");   
   document.writeln(ItemTreeMenu.GetMenu());     
-->
</script>
