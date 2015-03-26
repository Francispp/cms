<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
 <!DOCTYPE HTML>
<html>
  <head>
<%@ include file="/common/meta.inc"%>
    <title>CMS内容管理系统</title>
    <script src="${ctx}/common/cybercms_js/global_ab.js" type="text/javascript"></script>
    <script src="${ctx}/dwr/engine.js" type="text/javascript"></script>
    <script src="${ctx}/dwr/util.js" type="text/javascript"></script>
    <script src="${ctx}/common/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/dwr/interface/SiteManagerService.js" type="text/javascript"></script>
    <script type="text/javascript">
      var ctx = "${ctx}";
      //DWREngine.setErrorHandler(errorHandler);	
      function errorHandler(errorString, exception){
        alert(errorString);
      }
      
      function getExt(){
        return top.Ext;
      }
      
      function getExtMessageBox(){
        if (getExt()) 
          return getExt().MessageBox;
        else 
          return null;
      }
      
      function changeSelect(siteId){
        window.location = "index.action?siteId=" + siteId;
      }
      
      function modifyPassWord(){
        global_ab.fn.popWindow.addPopWindow("${ctx}/base/user!updateUserPWD.action", "505px", "224px", false);
      }
     // javascript:alert(document.documentMode); 
    </script>
  </head>
  <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
  <body class="baseFrame baseFrame-ex">
    <div id="baseFrame-header">
      <div class="header">
        <div class="header-bj">
          <div class="header-logo">
            <div class="header-nav">
              <ul>
                <li class="user">当前用户：${loginer.username}</li>
                <li class="user-link">
                  <a href="javascript:logout();">退出登录</a>
                </li>
                <li class="user-link">
                  <span><a href="#" onclick="modifyPassWord();">修改密码</a></span>
                </li>
                <li class="user-link">
                  <a href="${ctx}/index!siteHome.action" target="_blank">返回首页</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="nav-bj">
          <div class="nav-select">
            当前站点
            <select name="currentsite" id="currentsite" class="site-select" onchange="changeSelect(this.value);">
              <c:if test="${loginer.siteId==0}">
                <option value="0" selected="selected"> CMS内容管理系统</option>
              </c:if>
              <c:forEach var="site" items="${allSite}" varStatus="status">
                <c:if test="${loginer.siteId==site.oid }">
                  <option value="${site.oid}" selected="selected"> ${site.sitename}</option>
                </c:if>
                <c:if test="${loginer.siteId!=site.oid }">
                  <option value="${site.oid}"> ${site.sitename}</option>
                </c:if>
              </c:forEach>
              <c:if test="${loginer.siteId!=0}">
                <option value="0"> CMS内容管理系统</option>
              </c:if>
            </select>
          </div>
          <div class="nav">
            <ul id="pageLink_ab">
              <ww:iterator value="topMenu" status="rowstatus">
                <ww:if test="isView == 1">
                  <ww:if test="grade == 0">
                    <cms:auth resCode="${menuCode}">
                      <li class="nav-link" id="menu${rowstatus.index+1}" onclick="menuClick(this,'<ww:property value = "url"/>','<ww:property value = "subUrl"/>','<ww:property value = "threedUrl"/>')">
                        <a href="#"><span><ww:property value = "menuName"/></span></a>
                      </li>
                    </cms:auth>
                  </ww:if>
                  <ww:if test="grade == 1">
                  
                    <cms:CmsAuth resCode="${menuCode}" objectId="${loginer.siteId}" objectType="1">
                      <li class="nav-link" id="menu${rowstatus.index+1}" onclick="menuClick(this,'<ww:property value = "url"/>','<ww:property value = "subUrl"/>','<ww:property value = "threedUrl"/>')">
                        <a href="#"><span><ww:property value = "menuName"/></span></a>
                      </li>
                    </cms:CmsAuth>
                  </ww:if>
                  <ww:if test="grade == 2">
                    <li class="nav-link" id="menu${rowstatus.index+1}" onclick="menuClick(this,'<ww:property value = "url"/>','<ww:property value = "subUrl"/>','<ww:property value = "threedUrl"/>')">
                      <a href="#"><span><ww:property value = "menuName"/></span></a>
                    </li>
                  </ww:if>
                </ww:if>
              </ww:iterator>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div id="baseFrame-content">
      <div class="baseFrame-content-left" id="BFC_left"> 
          <div id="LL_show" style="display:none;">
            <img src="${default_imagepath}/xs.gif" width="16" height="17" border="0"/>
        </div>
    	<div id="frameLL">
        	<div id="LL_close">
            	<img src="${default_imagepath}/yc3.gif" alt="cms" />
            </div>
        	<iframe class="baseFrame-content-left-item" width="117px" id="baseFrame-part1" height="100%" frameborder="0" scrolling="no" src=""></iframe>
        </div> 
      
          <!-- 右部 开始 -->
        <div id="RR_show" style="display:none;">
            <img src="${default_imagepath}/xs.gif" width="16" height="17" border="0"/>
        </div>
        <div id="frameRR" >
        	<div id="RR_close" >
            	<img src="${default_imagepath}/yc1.gif" alt="cms" />
            </div>
        	 <iframe id="switchToTree"  width="153px" height="100%" frameborder="0" scrolling="auto" src=""></iframe>
       </div>
       </div>
      <div class="baseFrame-content-right">
        <iframe id="main_frame" name="main_frame" class="baseFrame-content-right-item" width="100%" height="100%" frameborder="0" src="" scrolling="auto"></iframe>
      </div>
    </div>
    <div id="baseFrame-footer">
      <div class="footer">版权所有 &copy; 1996-2014 Cyberway 赛百威信息科技</div>
    </div>
    <!-- 页脚 -->
    <%@ include file="/common/foot.inc" %>
  </body>
</html>
<script type="text/javascript">
//<![CDATA[
  global_ab.init.popwindowPage_fn();

//]]>
</script>
<script type="text/javascript">
  //注销系统
  function logout(){
    var isADUser = "${loginer.isADUser}";
    
    if (confirm('您确定退出系统吗？')) {
      if (isADUser == 'true') 
        document.location.href = '${ctx}/login!logout.action';
      else 
        document.location.href = '${ctx}/j_acegi_logout';
    }
  }
  
  global_ab.init.indexPage_fn();
  var allLi = document.getElementById("pageLink_ab").getElementsByTagName("li");
  
  function menuClick(liObj, menuURL, treeURL, contentURL){
    var ulLink = document.getElementById("pageLink_ab");
    var lis = ulLink.getElementsByTagName("li");
    
    var liLen = lis.length;
    for (var x = 0; x < liLen; x++) {
      lis.item(x).className = "nav-link";
    }
    liObj.className = "nav-hover";
    global_ab.fn.changeBaseFrameContent(menuURL, treeURL, contentURL);
  }
  if(allLi != null && allLi.length>0)
  allLi[0].click();
  //更改内容区内容
  function setMainFrameUrl(murl){
  	if(murl == null || murl==''){
	 	 document.all.main_frame.src='';
	 }else{
		 if(document.frames){
			 document.frames['main_frame'].location.href = murl;
		 } else {
			 document.getElementById('main_frame').src = murl;
		 }
	 }
  }
	function FunSelectDisabled(flag){
		try{
			var isIE = !!window.ActiveXObject;   
			var isIE6 = isIE && !window.XMLHttpRequest;
			if(isIE6) {
				var currentsite = document.getElementById("currentsite");
				if(undefined!=currentsite || null!=currentsite){
					currentsite.disabled=flag;
				}
				var main_frame = document.frames['main_frame'];
				if(undefined!=main_frame || null!=main_frame){
					main_frame.document.getElementsByName("myTable_rd")[0].disabled=flag;
				}
			}
		}catch(e){
		}finally{
			return true;
		}
	}
</script>
