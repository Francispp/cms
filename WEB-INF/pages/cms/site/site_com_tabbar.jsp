<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="站点管理" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/tabbar.inc"%>
<ww:head/>
</head>
<body >	
<div id="a_tabbar" style="margin:10px; height:110%;"/>
<script>
     tabbar=new dhtmlXTabBar("a_tabbar","top",23);
     tabbar.setImagePath("${ctx}/common/tabbar/imgs/");
     tabbar.loadXML("${ctx}/cms/site!comTabxml.action?id=${id}&tabstatus=${comstatus}");
     //tabbar.setSkinColors("white","#FFFACD");
     tabbar.setStyle("modern");
</script>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>		
<br><br>
 </body>
</html>
