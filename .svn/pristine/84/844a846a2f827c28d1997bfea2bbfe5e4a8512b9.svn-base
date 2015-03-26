<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="模板管理" />

<html>
<head>
<title>${title}</title>
<style type="text/css">
.tab{
 border:1px solid #ccc;
 border-collapse:collapse;
 width:800px;
 height:600px;
 position:absolute;
 top:50%;
 left:50%;
 margin:-300px 0 0 -400px;
}
</style>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/tabbar.inc"%>
<ww:head/>
</head>
<body class="tab">	
<div id="a_tabbar" style="padding-left: 10px;height:100%;"></div>
<script>
     tabbar=new dhtmlXTabBar("a_tabbar","top",23);
     tabbar.setImagePath("${ctx}/common/tabbar/imgs/");
     tabbar.loadXML("${ctx}/base/comtemplate!comTabxml_Select.action?temLibraryId=${temLibraryId}");
     tabbar.setStyle("modern");
    
</script>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>	
<br><br>
 </body>
</html>
