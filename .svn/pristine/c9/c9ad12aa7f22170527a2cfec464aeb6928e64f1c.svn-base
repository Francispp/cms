<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
  <head>
    <title></title>
    <%@ include file="/common/ext/ext-res.inc" %>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/dhtmlxtree/tree.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
  </head>
  <script type="text/javascript">
    function huadong(liObj, treeURL, contentURL){
      var ulLink = document.getElementById("menu_id");
      var lis = ulLink.getElementsByTagName("li");
      var liLen = lis.length;
      var isIE = !!window.ActiveXObject;   
      var isIE6 = isIE && !window.XMLHttpRequest;
     
      for (var x = 0; x < liLen; x++) {
        lis.item(x).className = "menu-link";
        if(isIE6)
        lis.item(x).style.background="url()";
      }
      liObj.className = "menu-link li-hover";
      if(isIE6)
      liObj.style.background="url(/images/cybercms/menu-nav-bj.gif) no-repeat left top";
      parent.global_ab.fn.changeBaseFrameContent("", treeURL, contentURL);
    }
  </script>
  <body style="height:100%">
    <div class="menu">
      <ul id="menu_id">
        <ww:iterator value="subMenu" status="rowstatus">
          <ww:if test="isView == 1">
            <ww:if test="grade == 0">
              <cms:auth resCode="${menuCode}">
                <li class="menu-link" style="cursor:hand;" onclick="javascript:huadong(this,'<ww:property value = "subUrl"/>','<ww:property value = "threedUrl"/>')">
                  <a><span><img src='${default_imagepath}/<ww:property value = "icoPath"/>'/></span><ww:property value = "menuName"/></a>
                </li>
              </cms:auth>
            </ww:if>
            <ww:if test="grade == 1">
            <ww:if test="#session.loginer.siteId >0">
              <cms:CmsAuth resCode="${menuCode}" objectId="${loginer.siteId}" objectType="1">
                <li class="menu-link" style="cursor:hand;" onclick="javascript:huadong(this,'<ww:property value = "subUrl"/>','<ww:property value = "threedUrl"/>')">
                  <a><span><img src='${default_imagepath}/<ww:property value = "icoPath"/>'/></span><ww:property value = "menuName"/></a>
                </li>
              </cms:CmsAuth>
              </ww:if>
            </ww:if>
            <ww:if test="grade == 2">
              <li class="menu-link" style="cursor:hand;" onclick="javascript:huadong(this,'<ww:property value = "subUrl"/>','<ww:property value = "threedUrl"/>')">
                <a><span><img src='${default_imagepath}/<ww:property value = "icoPath"/>'/></span><ww:property value = "menuName"/></a>
              </li>
            </ww:if>
          </ww:if>
        </ww:iterator>
      </ul>
    </div>
  </body>
</html>
<script type="text/javascript">
try{
  var allLi = document.getElementById("menu_id").getElementsByTagName("li");
  if(allLi.length>0)
  allLi[0].click();
}catch(e){}
</script>
