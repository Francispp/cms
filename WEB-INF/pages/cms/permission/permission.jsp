<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="站点权限管理" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/tabbar.inc"%>
<ww:head/>
</head>
<body>	
	<table width="100%" height="100%">
		<tr>
			<td width="100%" height="100%">
				<div id="a_tabbar" style="width:100%; height:100%;"/>
			</td>
        </tr>

	</table>
<script>
     tabbar=new dhtmlXTabBar("a_tabbar","top");
     tabbar.setImagePath("${ctx}/common/tabbar/imgs/");
     tabbar.loadXML("${ctx}/cms/permission!tabxml.action?objectId=${objectId}&type=${type}");
     //tabbar.setSkinColors("white","#FFFACD");
     tabbar.setStyle("modern");

</script>

	
<br><br>
 </body>
</html>
