<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>

<tabbar  hrefmode="iframe">
    <row>
    <c:if test="${siteid!=null}">
    <cms:CmsAuth resCode="CMS_SITE_RECYCLE_MANAGER" objectId="${siteid}" objectType="1">
       <tab id="b2" width='100px' selected="1" href="${ctx}/cms/channel!recycle.action?siteid=${siteid}">频道</tab>
       <!--tab id="b3" width='100px' href="${ctx}/cms/document!recycle.action?siteId=${siteid}">采编信息
       </tab-->
       <tab id="b4" width='100px' href="${ctx}/cms/staticResource!recycle.action?siteid=${siteid}">静态资源</tab>
      </cms:CmsAuth>       
    </c:if> 
    <c:if test="${session.loginer.isAdminUser}">
       <tab id="b1" width='100px' selected="1" href="${ctx}/cms/site!recyclelist.action">站点</tab>
    </c:if>            
    </row>
</tabbar>
