<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<%@ include file="/common/validation.inc"%>
<ww:head/>
</head>
<body  nowrap topmargin="0" leftmargin="0" >
	<script type="text/javascript">
		if (confirm ("需要为该表单生成相关的后台概览模板吗？"))
		{
			location = "${ctx}/cms/template!edit.action?formTemplateId=${domain.id}&channelId=${domain.channel.id}&templateType=4";
		}
		else
		{
			location = "${ctx}/cms/template!edit.action?id=${domain.id}&channelId=${domain.channel.id}&templateType=${domain.type}";
		}
	</script>
</body>
</html>