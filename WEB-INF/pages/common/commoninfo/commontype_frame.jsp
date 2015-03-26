<%--
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="类别管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<script type="text/javascript">
	//点击树节点执行的方法
	function onClickTreeNode(value) {
		document.frames['_content'].location.href="${ctx}/base/commoninfo!list.action?coreCommonTypeId="+value;
	}
	var viewstyle=0;//显示方式，若为1表示列表，0表示树结构
	function changeViewStyle(){
		location.href="menu.action?pageStyle=1";
	}
</script>
</head>
 
 
 
 
<frameset rows="*" cols="100%" frameborder=0 bordercolor=silver> <!--yes  framespacing="0" frameborder="0" border="1" auto-->
	<frame src="${ctx}/base/commontype!tree.action?pageStyle=1&siteid=${siteid}" name="index_list_left"  frameborder="no" scrolling="NO"  marginwidth="0" marginheight="0" id="toolBar">
	
</frameset>
<noframes>
	<body bgcolor="#FFFFFF">
	</body>
</noframes>
</html>



--%>