<%@ page contentType="text/xml;charset=UTF-8"%><%@ include file="/common/taglibs_xml.inc"%><?xml version='1.0' encoding='UTF-8'?>
<tabbar  hrefmode="iframe">
    <row>
       <!--  <tab id="b1" width='100px' selected="1" href="${ctx}/cms/channel!edit.action?id=${id}&amp;siteid=${siteid}&amp;pchannelid=${pchannelid}">基本信息</tab>-->
      <c:if test="${id!=null}">
      <c:if test="${tabstatus==1}">
       <cms:CmsAuth resCode="CMS_TEMPLATE_MANAGER" objectId="${id}" objectType="2">
       
       <tab id="b2" selected="1" width='100px' href="${ctx}/cms/template.action?channelId=${id}&amp;templateType=1">表单模板</tab>
       <tab id="b3" width='100px' href="${ctx}/cms/template.action?channelId=${id}&amp;templateType=3">概览模板</tab> 
       <tab id="b4" width='100px' href="${ctx}/cms/template.action?channelId=${id}&amp;templateType=2">细览模板</tab>    
       <tab id="b5" width='100px' href="${ctx}/cms/template.action?channelId=${id}&amp;templateType=4">后台概览模板</tab>
       <!-- <tab id="b8" width='100px' href="${ctx}/cms/staticResource!channelStaResList.action?siteid=${siteid}&amp;chnid=${id}">静态资源</tab> -->
       
      
       
              <tab id="b9" width='100px' href="${ctx}/cms/template.action?channelId=${id}&amp;templateType=8">概览模板WAP</tab> 
       <tab id="b10" width='100px' href="${ctx}/cms/template.action?channelId=${id}&amp;templateType=9">细览模板WAP</tab>    
       </cms:CmsAuth>
    </c:if>
     <c:if test="${tabstatus==2}">
       <cms:CmsAuth resCode="CMS_PERMISSION_MANAGER" objectId="${id}" objectType="2">
       <tab id="b6" selected="1" width='100px' href="${ctx}/cms/permission.action?type=2&amp;objectId=${id}&amp;objectType=2">频道权限管理</tab> 
        </cms:CmsAuth>
       <cms:CmsAuth resCode="CMS_DOCUMENT_PERMISSION_MANAGER" objectId="${id}" objectType="2">
       <tab id="b7" width='100px' href="${ctx}/cms/permission.action?type=2&amp;objectId=${id}&amp;objectType=3">文档权限管理</tab> 
       </cms:CmsAuth>
     </c:if>
     </c:if>
    </row>
</tabbar>

