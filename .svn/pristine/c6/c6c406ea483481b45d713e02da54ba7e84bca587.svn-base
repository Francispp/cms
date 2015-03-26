<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>
<tabbar  hrefmode="iframe">
    <row>
      <c:if test="${type<2}">
       <tab id="b1" width='100px' selected="1" href="${ctx}/cms/permission.action?type=${type}&amp;objectId=${objectId}&amp;objectType=1">站点权限</tab>
      </c:if>
      <c:if test="${type<4}"> 
       <tab id="b2" width='100px' <c:if test="${type==2}"> selected="1" </c:if>  href="${ctx}/cms/permission.action?type=${type}&amp;objectId=${objectId}&amp;objectType=2">频道权限</tab>
       <c:if test="${type<3}">
       <tab id="b4" width='100px' href="${ctx}/cms/permission.action?type=${type}&amp;objectId=${objectId}&amp;objectType=4">模板权限</tab>
       </c:if>        
       <tab id="b3" width='100px' <c:if test="${type==3}"> selected="1" </c:if> href="${ctx}/cms/permission.action?type=${type}&amp;objectId=${objectId}&amp;objectType=3">文档权限</tab>
      </c:if>
      
    </row>
</tabbar>
