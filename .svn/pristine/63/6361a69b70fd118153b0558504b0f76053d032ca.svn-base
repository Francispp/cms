<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/js.inc"%>
<html>
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<link href="/styles/demo.css" rel="stylesheet" type="text/css">
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">


function cencle(){
	window.close();
}
function save(){
	var albumId;
	var temp=document.getElementById("albumId");
	for (i=0;i<temp.length;i++){
		if(temp[i].selected){
			albumId=temp[i].value;     
		}
	}
	if(${albumId} == albumId){
		alert("音频已在此专辑中，不能重复添加！");
		return false;
	}
	myform.submit();
	window.close();
}


</script>
</head>
<body>
	<table border="0">
		<tr>
			<td>选择您想转入的专辑:</td>
		</tr>
		<tr>
			<td>${albumId}
				<form action="${ctx}/base/audio!saveAlbumId.action" method="post"
					name="myform">
					<ww:hidden name="domain.id" id="domain.id" value="%{id }" />
					<ww:action id="listAlbum" executeResult="false" namespace="/base"
						name="album!getAllAlbum">
						<ww:param name="id">${albumId}</ww:param>
					</ww:action>
					<ww:select id="albumId" name="domain.albumId"
						list="#attr.listAlbum.items" listKey="id" listValue="title"
						cssClass="formcss1" />
					<script>
					var _temp=document.getElementById("albumId");
					for (i=0;i<_temp.length;i++){
						if(_temp[i].value==${albumId}){
							_temp[i].selected=true;     
						}
					}
					</script>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<button id="add" onclick="save()">转移</button>
				<button onclick="cencle()">取消</button>
			</td>
		</tr>
	</table>
</body>
</html>