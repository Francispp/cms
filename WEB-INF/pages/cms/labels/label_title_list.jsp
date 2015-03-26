<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="标签管理列表" /> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc" %>
<link href="${sytlePath}/content.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc" %>
<%@ include file="/common/ec/ec.inc" %>
<script type='text/javascript' src='/dwr/interface/LabelService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/common/jquery/jquery-1.4.2.js'></script>
<script type='text/javascript'>
function loadContent(id){
	var content = document.getElementById("content_"+id).innerHTML;
	document.getElementById("divInfo").innerHTML=content;
}
</script>

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<br></br>
<!-- 页面标题 -->
<div class="content">
	<div class="sideBar">
		<div class="dataTableBox_header">
		<div class="tabBox"><a class="tabBox_item"><span class="tab_box1"><span class="tab_box2"><span class="tab_box3">标签标题</span></span></span></a></div>
		</div>
		<div class="menu">
			<ul>
				<c:forEach items="${result}" var="item">
					<span  onclick="loadContent('${item.id}');"><li><i class="com_ele_pics" style="font-style:normal">${item.title}</i></li></span>
					<li id="content_${item.id}" style="display:none;">${item.content}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="main">
		<h1>标签描述</h1>
		<div class="menu">
		<div class="infor" id="divInfo">
		</div>
		</div>
	</div>
</div>
</body>
<!--/div-->
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
