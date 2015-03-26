<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<html>
<head>
<title><ww:text name="%{getText('Index.title')}" /></title>
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
  if(value!=null&&value!='')
   document.frames['_content'].location.href='<c:out value="${ctx}"/>'+value;
}
</script>
<ww:head/>
</head>
<body>
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<div id="titleDiv"><ww:text name="%{getText('Index.title')}" />--<ww:text name="%{getText('language')}"/></div>
<!-- 操作功能按钮条 -->
<div id="operationDivNoBorder">
  <a href="index!language.action?language=cn">简体</a> 
  <a href="index!language.action?language=en">EngLish</a> 
  <a href="index!language.action?language=tw">繁体</a>
</div>

<!-- 页面主要内容 -->
<div id="page-content-no-border">
<table width="100%"  height="100%"  border="0" cellpadding="0" cellspacing="0">
    <tr>
    <td valign="top"  height="100%" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td valign="top" id="frmTitle" width="193" valign="top" bgcolor="#FFFFFF"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
	<td><iframe allowtransparency="yes" width="100%"  height="100%" scrolling="auto" src="${ctx}/index!menu.action" frameborder="0" name="menu"  id="menu"></iframe>
	 </td>
                </tr>
            </table>
</td>
        <td valign="top" bgcolor="#FFFFFF"><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
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
