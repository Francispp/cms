<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="媒体管理" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/tabbar.inc"%>
<ww:head/>
</head>
<body >	
<div id="a_tabbar" style="width:100%; height:100%;"/>
<script>
     tabbar=new dhtmlXTabBar("a_tabbar","top");
     tabbar.setImagePath("${ctx}/common/tabbar/imgs/");
     tabbar.loadXML("${ctx}/base/medaiManager!tabxml.action");
     tabbar.setStyle("modern");
</script>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>		
<br><br>
 </body>
</html>
