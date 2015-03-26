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
	function getCheck(){
		var _selectitem=document.getElementsByName("_selectitem");
		for(var i=0;i<_selectitem.length;i++){
			if(_selectitem[i].checked){
				return _selectitem[i].value+":"+document.getElementsByName("_selectitemvalue")[i].value;
			}
		}
	}
</script>
<style type="text/css">
.media-img-box ul li {
	width:140px;
	height:100px;
	margin:10px 10px 0px 0px;
	float:left;
}
.media-img-li-top {
	width:50px;
	height:40px;
	float:left;
	border: 1px solid #acacac;
}
.media-img-one {
	width:100%;
	height:16px;
	float:left;
	text-align:center;
	margin:0px;
}
.media-img-one img {
	width:40px;
	height:30px;
	border: 1px solid #acacac;
	margin-top:4px;
	}	
</style>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>

	<div class="content">
  <div class="info-box">
  <form id="myTable"  method="post" action="/base/video!listBySite.action">

 <div class="media-img-div">
    <div class="media-img-box">
    <ul>
    <ww:iterator status="rowstatus" value="items">
    <li>
    <div class="media-img-li-top">
    <div class="media-img-one"><img name="serverpath"  width="30" height="20" src="${mediaFilePath }<ww:property value="imagePath"></ww:property>"/></div>
    </div>
    <div class="media-img-li-bottom" style="overflow-x:hidden;"/>
    <input type="radio" name="_selectitem" value="<ww:property value="id" />"/>
     <input type="hidden" name="_selectitemvalue" value="<ww:property value="filePath" />"/>
    <ww:if test="%{title.length()>8}"> 
<ww:property value="title.substring(0,5)+'...'"/> 
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
