<%@ page contentType="text/xml;charset=UTF-8"%><%@ include
	file="/common/taglibs_xml.inc"%><?xml version='1.0' encoding='UTF-8'?>
<tabbar hrefmode="iframe"> <row> 

			<tab id="b2" selected="1" width='100px'
				href="${ctx}/base/comtemplate!Select_list.action?temLibraryId=${temLibraryId}&amp;comTemplateType=1">表单模板</tab>
			<tab id="b3" width='100px'
				href="${ctx}/base/comtemplate!Select_list.action?temLibraryId=${temLibraryId}&amp;comTemplateType=3">概览模板</tab>
			<tab id="b4" width='100px'
				href="${ctx}/base/comtemplate!Select_list.action?temLibraryId=${temLibraryId}&amp;comTemplateType=2">细览模板</tab>
			<tab id="b5" width='100px'
				href="${ctx}/base/comtemplate!Select_list.action?temLibraryId=${temLibraryId}&amp;comTemplateType=4">后台概览模板</tab>
			<tab id="b9" width='100px'
				href="${ctx}/base/comtemplate!Select_list.action?temLibraryId=${temLibraryId}&amp;comTemplateType=8">概览模板WAP</tab>
			<tab id="b10" width='100px'
				href="${ctx}/base/comtemplate!Select_list.action?temLibraryId=${temLibraryId}&amp;comTemplateType=9">细览模板WAP</tab>
		</row> </tabbar>

