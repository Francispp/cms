<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="用户组管理" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<style type=text/css>
body { 
  background-color: #FFFFFF;
  scrollbar-face-color: #FFFFFF;
  scrollbar-shadow-color: #FFFFFF;
  scrollbar-highlight-color: #FFFFFF; 
  scrollbar-3dlight-color: #FFFFFF;
  scrollbar-darkshadow-color: #FFFFFF;
  scrollbar-track-color: #FFFFFF;
  scrollbar-arrow-color: #FFFFFF
}
.navigation {border-right: black 1px solid; margin: 5px 0px 0px 5px; background-color: #f7f7f7}
</style>
<script type="text/javascript">
//点击树节点执行的方法
function onClickTreeNode(value) {
  document.frames['_content'].location.href="group.action?style=0&deptid="+value;
}
var viewstyle=0;//显示方式，若为1表示列表，0表示树结构
function changeViewStyle(){
  location.href="group.action?style=1";
  //viewstyle=1;   
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<div id="titleDiv"><c:out value="${title}"/></div>
<!-- 操作功能按钮条 -->
<div id="operationDivNoBorder">
 <button id="backbutton" onclick="changeViewStyle()" class="button">列表</button>
</div>
<!-- 页面主要内容 -->
<div id="page-content-no-border">
<table width="100%"  height="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td valign="top"  height="100%" width="25%"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td valign="top" id="frmTitle"  valign="top" bgcolor="#FFFFFF"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
	<td><iframe allowtransparency="yes" width="100%"  height="100%" scrolling="auto" src="${ctx}/base/dept!tree.action" frameborder="0" name="menu"  id="menu"></iframe>
	 </td>
                </tr>
            </table>
</td>
        <td valign="top" bgcolor="#FFFFFF" width="75%"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td valign="top"><iframe allowtransparency="yes" height="100%"  width="100%" scrolling="auto" src="" frameborder="0" name="_content"></iframe></td>
            </tr>
           
        </table></td>            
            
          </tr>
 </table>
</div>
<!-- 页脚 -->
<DIV ID="footDIV"><%@ include file="/common/foot.inc"%></DIV>
</body>
</html>
