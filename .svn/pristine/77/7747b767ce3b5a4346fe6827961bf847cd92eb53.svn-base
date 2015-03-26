<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>

<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />

</head>
<body class="tree-bg">
	<div class="tree-box">
		<div id="toCloseSwitch" class="tree-title">
			<img src="${default_imagepath}/yc1.gif" alt="cms" />
		</div>
		<div class="tree-centent">
			<c:forEach var="channel" items="${channels}" varStatus="status">
				<div id="siteTree_${channel.site.oid}">${channel.name}</div>
			</c:forEach>
			
		</div>
	</div>

</body>
</html>

