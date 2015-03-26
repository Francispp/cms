<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<%-- <script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script> --%>
<%@ include file="/common/validation.inc"%>
<ww:head/>
</head>
<script language="JavaScript">

function ev_checkval() {
  return true;
}

// 选项卡点击事件
function cardClick(cardID){
	var obj;
	for (var i=1;i<3;i++){
		obj=document.all("card"+i);
		obj.style.backgroundColor="#3A6EA5";
		obj.style.color="#FFFFFF";
	}
	obj=document.all("card"+cardID);
	obj.style.backgroundColor="#FFFFFF";
	obj.style.color="#3A6EA5";

	for (var i=1;i<3;i++){
		obj=document.all("content"+i);
		obj.style.display="none";
	}
	obj=document.all("content"+cardID);
	obj.style.display="";
}
// 点击确定
function doSave(){
  var obj = new Object();
  //obj.beforopenscript = formItem.beforopenscript.value;
  //obj.afteropenscript = formItem.afteropenscript.value;
  obj.beforsavescript = formItem.beforsavescript.value;
  obj.aftersavescript = formItem.aftersavescript.value;
  //obj.onexitscript = formItem.onexitscript.value;
  window.returnValue = obj;
  window.close(); 
}
</script>
<body  nowrap topmargin="0" leftmargin="0" >
<!-- 操作功能按钮条 -->
<div id="nav">
 <div>
 <ul>  
  <li id="button"><a href="javascript:doSave()">确定</a></li>  
  <li id="button"><a href="javascript:window.close()">取消</a></li>

  </ul>
  </div>
</div>
<form action="" name="formItem" method="post" >
 <table border=0 cellpadding=0 cellspacing=0 width="100%">
<tr valign=top><td>

<table border=0 cellpadding=3 cellspacing=0 width="100%">
<tr align=center>
	<td width=2></td>
	<td onclick="cardClick(1)" id="card1">保存前执行</td>
	<td width=2></td>
	<td onclick="cardClick(2)" id="card2">保存后执行</td>
	<td width=2></td>
	<td width=2></td>
</tr>

<tr>
	<td bgcolor=#ffffff align=center valign=middle colspan=10>
	<table width="100%"  cellpadding="2" cellspacing="1"  border="0"  bgcolor="#3A6EA5" id="content1">
	<tr>
		<!--td width="15%">保存前执行</td-->
		<td width="100%">
		<ww:textarea name="domain.beforsavescript" cols="40" rows="15" cssStyle="width: 100%;"  id="beforsavescript" />
		</td>
	</tr>
	</table>
	<table width="100%"  cellpadding="2" cellspacing="1"  border="0"  bgcolor="#dddddd"  id="content2" style="display:none">
	<tr>
		<td width="100%">
		<ww:textarea name="domain.aftersavescript" cols="40" rows="15" cssStyle="width: 100%;"  id="aftersavescript" />
		</td>
	</tr>
	</table>
	
	</td>
</tr>
</table>
</td>
</tr>
</table>
</form>

</body>
</html>
<script language="JavaScript">
 cardClick(1);
<script>