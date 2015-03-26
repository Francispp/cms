<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>
<tabbar  hrefmode="iframe">
    <row>
       <tab id="b1" width='100px' selected="1" href="${ctx}/crawl/console.action">启动控制台</tab>
       <c:if test="${id!=null}">
       <tab id="b2" width='100px' <c:if test="${extract}" > selected="1"</c:if> href="${ctx}/crawl/extract!list.action?jobId=${id}">提取条件</tab>       
       <tab id="b3" width='100px' href="${ctx}/crawl/extractLog!list.action?id=${id}">提取内容</tab>    
       </c:if>
       
    </row>
</tabbar>
