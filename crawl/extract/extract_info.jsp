<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="提取内容管理列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />
<script type="text/javascript">			
 
 var parameterUrl="&pageStyle=1";
 //编辑
 function editItem(oid){
  location.href='jobs!edit.action?id='+oid+parameterUrl;
 }
 //新增
  function addItem(){
  location.href='jobs!edit.action?id='+parameterUrl;
 }
 //删除
 function deleteItem(){
 var ids=getSelectedID();
 if(ids==null||ids==''){
    alertMessage('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='jobs!delete.action?keys='+ids+parameterUrl;
 }
 }
 function extractContent(){
  location.href='extract!extractContent.action?jobId=${jobId}&id='+parameterUrl;
 }
</script>
<style type="text/css">
/*选择资源图标*/
.selectRes{
	/*background-image: url('${icon_16_actions}/tools-check-spelling.png');*/
	background-repeat: no-repeat; 
	background-position: center;
	text-align:center;
	cursor:hand;
}
</style>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">${title}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>
	
	<!-- 操作栏 -->

	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
						<li id="button1"><a href="javascript:extractContent();">提取内容</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>


<!-- 页面主要内容 -->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">



</td></tr></table></td></tr>
</table>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
