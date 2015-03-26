<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="视频资源列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">			

 var parameterUrl="&albumId=${albumId}&isdefault=${isdefault}&albumType=${albumType}";
 //编辑
 function editItem(oid){
	 global_ab.fn.popWindow.addPopWindow("${ctx }/base/video!edit.action?id="+oid,"660px","384px",false);
 }
 //添加
 function addItem(){
	global_ab.fn.popWindow.addPopWindow("${ctx}/base/video!add.action?albumId=${albumId}","660px","384px",false);
 }

//下载
 function download(id)
 {
 	location.href='${ctx}/base/video!fileDownload.action?id='+id;
 }
 //播放视频
 function broadcast(id){
		wx='400px';
		wy='300px';
	  var surl = '${ctx}/base/video!broadcast.action?id='+id;
	  var title="播放视频";
	  showframe(title, surl);
 }
 //删除多条
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
	 location.href='${ctx}/base/video!delete.action?keys='+ids+parameterUrl;
 }
 }
 //删除单条
 function deleteItemById(id)
 {
 	var ids=id;
 	if(ids==null||ids==''){
 	    alert('请先选择记录！');
 	    return ;
 	    }
 	 if(confirm('您确定要删除选择的记录吗？')){
 		 location.href='${ctx}/base/video!delete.action?keys='+ids+parameterUrl;
 	 }
 }

 //改变专辑
 function changeAlbum(){
	 
	 var keys=getSelectedID();
	 if(keys==null||keys==''){
			alert('请选择想要转移的视频');
			return ;
		}
	  global_ab.fn.popWindow.addPopWindow("${ctx}/base/album!changeAlbumtree.action?id=${albumId}&albumType=${albumType}&keys="+keys,"385px","383",false);

	}
 function reload(){
	 location.href='${ctx}/base/video!listByAlbum.action?albumId=${albumId}&isdefault=${isdefault}&albumType=${albumType}';
 }
</script>

</head>
<body style="margin:0px;padding:0px;width:100%;">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<div class="edit-header">
  <div class="edit-header-top">
  <ul id="lion-ul-a">
  <cms:CmsAuth resCode="CMS_VIDEO_LIBRARY" objectId="${loginer.siteId}" objectType="1">
   <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:addItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>录入</span>
         </a>
    </li>
   <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:changeAlbum();">
            <img src="${default_imagepath}/ico_020_upcomingWork.gif" class="ico_ab ico-013_ab" />
            <span>转移</span>
         </a>
    </li>
    <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
    </li>
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:reload();">
           <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    </cms:CmsAuth> 
</ul>
  </div>
  <br></br>
  <div class="edit-header-bottom">
    <ul>
    <li class="select-all"><input type='checkbox' name='allbox' onclick='checkAll(this);' />全选</li>

      <li class="main-search">
        <input name="textfield" style="height:15px;font-size:11px;" type="text" class="main-search-input" id="searchStr" onfocus="if(this.value=='关键字搜索')value='';" onblur="if(this.value=='')value='关键字搜索';" onkeydown="if (event.keyCode==13) {searchAll(this.value);}" value="关键字搜索"/>
        <img src="${default_imagepath}/main-search-img.gif" onclick="javascript:searchAll(document.getElementById('searchStr').value)"/></li>
    </ul>
  </div>
  
</div>

 <div class="content" style="width:100%;margin:0px;padding:10px;">
 <form id="myTable"  method="post" action="/base/video.action">
  <div class="info-box" style="width:100%;margin:0px;padding:0px;">
 <div class="media-img-div" style="margin:0px;padding:0px;width:100%;">
    <div class="media-img-box" style="margin:0px;padding:0px;width:100%;">
    <ul style="margin:0px auto;text-align:center;width:100%;">
    <ww:iterator status="rowstatus" value="items">
   <li style="margin:10px 11px 0px 0px;">
    <div class="media-img-li-top">
    <div class="media-img-one"><img name="serverpath"  width="140" height="110" src="${mediaFilePath }<ww:property value="imagePath"></ww:property>"/></div>
    <div class="media-img-two"><a href="javascript:broadcast(<ww:property value="id" />)"><img src="${default_imagepath}/media-img-tubiao3.gif"/></a><a href="javascript:editItem(<ww:property value="id"></ww:property>);"><img src="${default_imagepath}/media-img-tubiao2.gif"/></a><a href="javascript:deleteItemById('<ww:property value="id"></ww:property>');"><img src="${default_imagepath}/media-img-tubiao1.gif"/></a></div>
    </div>
    <div class="media-img-li-bottom" style="overflow-x:hidden;">
    <input type="checkbox" name="_selectitem" value="<ww:property value="id" />"
								onclick='checkOne(allbox);' />
    <ww:if test="%{title.length()>20}"> 
<ww:property value="title.substring(0,18)+'...'"/> 
</ww:if>
<ww:else><ww:property value="title"></ww:property></ww:else>
    
    
    </li>
    </ww:iterator>  
   
    </ul>
    </div>
  
  
  
  
</div>
  <div class="media-img-box-bottom">
<img src="${default_imagepath}/info-table-left.gif" style="float:left;"/>
                	<img src="${default_imagepath}/info-table-right.gif" style="float:right;"/>
</div>
</div>
<div class="pag-box media-margin">
<cms:pager style="tablePager"  pageIndex="data.currentPageNo" pageSize="data.pageSize" recordCount="data.totalCount"></cms:pager>
</div>
	</form>			                       				
</div>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
<!--

function jsTrim(str){
   return str.replace(/\ /g,"");
} 
function searchAll(str){
	str=jsTrim(str);
if(str==''||str=='关键字搜索'){
alert('请输入有效字符');
return ;
}
var tstr=str.length;
str=str.replace(/[^a-zA-Z\d\u4e00-\u9fa5]/g,"");
if(str.length!=tstr){
alert('只能匹配中文、英文与数字');
return ;
}
location.href="/base/video.action?albumId=${albumId}&searchStr="+encodeURI(str);
}

//-->
</script>
	<c:if test="${not empty actionErrors}">
	    <c:forEach var="err" items="${actionErrors}">
	        <script> alert("删除数据不完全，可能数据被引用");</script>
        </c:forEach> 
	</c:if>
