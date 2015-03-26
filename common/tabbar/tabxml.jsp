<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>
<%response.setContentType("text/xml");
String id = request.getParameter("id");
if(id == null)
{
id = "";
}
%>
<tabbar  hrefmode="iframe">
    <row>
       <tab id="b1" width='100px' selected="1" href="${ctx}/cms/site!edit.action?id=<%=id%>">基本信息</tab>
        <tab id="b1" width='100px' selected="1" href="${ctx}/cms/site!edit.action?id=<%=id%>">基本信息</tab>
       <tab id="b2" width='100px' href="${ctx}/cms/site!edit.action">首页模板</tab>
       <tab id="b3" width='100px' href="${ctx}/cms/staticResource.action?siteid=<%=id%>">静态资源</tab>
    </row>
</tabbar>
