<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="频道管理" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/tabbar.inc"%>
<ww:head/>
</head>
<body style="height:100%;">	

<div id="a_tabbar" style="margin:10px;height:100%;"/>
<script>
     tabbar=new dhtmlXTabBar("a_tabbar","top",23);
     tabbar.setImagePath("${ctx}/common/tabbar/imgs/");
     tabbar.loadXML("${ctx}/cms/channel!tabxml.action?id=${id}&siteid=${siteid}&pchannelid=${pchannelid}");
     //tabbar.setSkinColors("white","#FFFACD");
     tabbar.setStyle("modern");
    
</script>

<!-- 页脚 -->


 </body>
</html>
