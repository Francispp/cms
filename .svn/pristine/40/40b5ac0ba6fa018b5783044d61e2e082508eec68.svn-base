<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/menu/menu.inc"%>
<script src="${ctx}/common/core_js/core.js"></script>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script type="text/javascript"> 
var _selected = "";
  //点击树节点执行的方法
function onNodeSelect(value) {

}
function toncheck(id,state){
if(state && id.indexOf('T') == -1)
{
_selected += id+","
}
				//alert("Item "+id+" was " +((state)?"checked":"unchecked"));
			}

//取消操作
function doExit(){
  window.returnValue = null;  
  window.close();
}

function doReturnSite() {
  window.returnValue = _selected;
  window.close();
}

</script>
</head>
<body style="background:#F1F1F1;" onmouseup="OnMouseUp('RootTreeMenu');" oncontextmenu="window.event.returnValue=false">
<table align="center" width="100%"  hight="100%" border="0">

<tr>
    <td height="28" bgcolor="#FFFFFF"> 
      <input type='button' name='btnReturn' value='确 定' class='bt-enter' onClick='doReturnSite();'>
      <input type="button" class="bt-cancel" onclick="doExit()" value="取消">
	 <input type="button" class="bt-empty" onclick="doEmpty()" value="清空">
       </td>
  </tr>
     <tr>
            <td width="100%" align="left" hight="100%"> 
 <c:forEach var="site" items="${sites}" varStatus="status"> 
  <div id="siteTree_${site.oid}"></div> 
 </c:forEach>
            </td>
    </tr>               
</table> 
</body>
</html>
<script language="javascript">

 
<!--		
  <c:forEach var="site" items="${sites}" varStatus="status">  
  tree${site.oid}=new dhtmlXTreeObject("siteTree_${site.oid}","100%","100%",'S_${site.oid}');
  tree${site.oid}.loadXML("${ctx}/cms/site!xml.action?revi=true&id=${site.oid}");
  tree${site.oid}.setXMLAutoLoading("${ctx}/cms/site!channelxml.action?revi=true&id=${site.oid}");
  tree${site.oid}.enableCheckBoxes(1);
  tree${site.oid}.setOnClickHandler(onNodeSelect);
  tree${site.oid}.setOnCheckHandler(toncheck);
  </c:forEach>   
-->
</script>
