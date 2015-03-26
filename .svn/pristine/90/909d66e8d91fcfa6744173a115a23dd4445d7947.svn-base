<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/cyber_taglibs.inc"%>
<tabbar  hrefmode="iframe">
    <row>
       <!--  <tab id="b1" width='100px' selected="1" href="${ctx}/cms/site!edit.action?id=${id}">基本信息</tab>-->
       <c:if test="${id!=null}">
        <c:if test="${tabstatus==1}">
       <cms:CmsAuth resCode="CMS_TEMPLATE_MANAGER" objectId="${id}" objectType="1">
       <tab id="b2" selected="1" width='100px' href="${ctx}/cms/template!list.action?siteId=${id}&amp;templateType=5">首页模板</tab>
       <tab id="b4" width='100px' href="${ctx}/cms/template!list.action?siteId=${id}&amp;templateType=0">公用模板</tab> 
       <tab id="b8" width='100px' href="${ctx}/cms/template!list.action?siteId=${id}&amp;templateType=7">Wap首页模板</tab>
       <tab id="b9" width='100px' href="${ctx}/cms/template!list.action?siteId=${id}&amp;templateType=6">Wap公用模板</tab> 
          
      <!--   <tab id="b3" width='100px' href="${ctx}/cms/staticResource.action?siteid=${id}">静态资源</tab> -->
       </cms:CmsAuth>
       </c:if>
      <c:if test="${tabstatus==2}">
      
         <cms:CmsAuth resCode="CMS_SITE_MODI" objectId="${id}" objectType="1"> </cms:CmsAuth>  
        <cms:CmsAuth resCode="CMS_PERMISSION_MANAGER" objectId="${id}" objectType="1">
         <tab id="b5" selected="1" width='100px' href="${ctx}/cms/permission.action?type=1&amp;objectId=${id}&amp;objectType=1">站点权限管理</tab>
       </cms:CmsAuth>   
       <cms:CmsAuth resCode="CMS_DOCUMENT_PERMISSION_MANAGER" objectId="${id}" objectType="1">
    	 <tab id="b7"  width='100px' href="${ctx}/cms/permission.action?type=1&amp;objectId=${id}&amp;objectType=3">文档权限管理</tab>
       </cms:CmsAuth>
       
       <!--<cms:CmsAuth resCode="CMS_SITE_ISSUE_PERMISSION_MANAGER" objectId="${id}" objectType="1">
         <tab id="b6" width='100px' href="${ctx}/cms/siteDistribution.action?siteId=${id}">站点分发管理</tab> 
       </cms:CmsAuth>-->
         
        </c:if>
       </c:if>

    </row>
</tabbar>
