<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<script type='text/javascript'>
	<ww:if test="uploadSuccess">
		window.parent.CKEDITOR.tools.callFunction('<ww:property value="#parameters.CKEditorFuncNum" />', '<ww:property value="request.contextPath" /><ww:property value="file.url" />', '');
	</ww:if>
	<ww:else>
		window.parent.CKEDITOR.tools.callFunction('<ww:property value="#parameters.CKEditorFuncNum" />', '', '');
		alert('<ww:property value="uploadMsg" />');
	</ww:else>
</script>