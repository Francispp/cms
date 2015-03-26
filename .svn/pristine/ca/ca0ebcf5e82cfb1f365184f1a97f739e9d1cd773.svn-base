<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="系统配置树" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dtree.css" type="text/css">
<%@ include file="/common/cxtree.inc"%>

<script type="text/javascript">
//点击树节点执行的方法
function onclickItem(value) {
   parent.onClickTreeNode(value);
}
</script>
<ww:head/>
</head>
<body  oncontextmenu="window.event.returnValue=false" nowrap topmargin="0" leftmargin="0" bgcolor="#F4F4F0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<!--div id="titleDiv"><c:out value="${title}"/></div-->
<!-- 操作功能按钮条 -->
<!-- div id="operationDivNoBorder">
          
 <button id="backbutton" onclick="goBack()" class="button">返回</button>
</div-->
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<table align="center" width="100%" border="1">
     <tr>
            <td width="100%" align="left" >            
      <script  language="javascript"> 
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.precourceid==null}" >
                  var T${item.recourceid}=new WebFXTree('${item.resourcename}', "javascript:onclickItem('${item.actioin}')", null, null, null);
                  document.write(T${item.recourceid});
                  </c:when>
                  <c:otherwise>
                  var T${item.recourceid}= new WebFXTreeItem('${item.resourcename}', "javascript:onclickItem('${item.actioin}')", null, null, null,null,'false','${item.recourceid}',null,null);
                  </c:otherwise>
                 </c:choose>
                </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.precourceid==null}" >              
                  </c:when>
                  <c:otherwise>
                   T${item.precourceid}.add(T${item.recourceid});
                  </c:otherwise>
                 </c:choose>
                </c:forEach>         
      </script>       
            </td>
    </tr>               
</table>
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>
