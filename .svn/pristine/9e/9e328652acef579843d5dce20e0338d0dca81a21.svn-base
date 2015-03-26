<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><ww:property value="getText('Index.title')"/></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxMenu/menu.inc"%>
<link href="${sytlePath}/main_style.css" rel="stylesheet" type="text/css"/>
<script language="javascript">
	<!--
	function Lsplit(view,hidden)
	{
	view.style.display= "block";
	hidden.style.display= "none";
	}
	//注销系统
	function logout(){
	confirmMessage( '<ww:property value="getText('HINTINFO.EXITSYSTEM')"/>','<ww:property value="getText('RESOURCE.COMMON.HINT')"/>', function(btn){
	 if(btn=='yes')
           document.location.href = '${ctx}/j_acegi_logout';
        });
        }
        //更改内容区内容
	function setMenuFrameUrl(murl) {
	if(murl)
	 document.frames['menu_frame'].location.href=murl;
	else
	 document.frames['menu_frame'].location.reload();
	}
	//刷新菜单区内容
	function setMainFrameUrl(murl) {
		if(document.frames){
			document.frames['main_frame'].location.href=murl;
		} else {
			document.getElementById('main_frame).src = murl;
		}
	}
	//设置语言
	function setLanguage(language){
	 setCookie('LOCALE_LANGUAGE',language);
 	 location='index!language.action?language='+language;
	}
	//设置风格
	function setStyle(style){
	 setCookie('LOCALE_STYLE',style);
	 location='index!language.action?style='+style;
	}	
function testM(btn){
 //alertMessage('tt:');
}		
	-->
</script>
</head>
<body>
<div id="header" title="右击可改变风格">
   <div id="header_left">
   <div id="header_right">
      <div id="header_nav">
      <a href="javascript:setLanguage('zh_CN');"><c:if test="${session.LOCALE_LANGUAGE=='zh_CN'}"><span></c:if>简体中文<c:if test="${session.LOCALE_LANGUAGE=='zh_CN'}"></span></c:if></a>　
      |　<a href="javascript:setLanguage('zh_TW');"><c:if test="${session.LOCALE_LANGUAGE=='zh_TW'}"><span></c:if>繁体中文<c:if test="${session.LOCALE_LANGUAGE=='zh_TW'}"></span></c:if></a>　
      |　<a href="javascript:setLanguage('en_US');"><c:if test="${session.LOCALE_LANGUAGE=='en_US'}"><span></c:if>English Ver<c:if test="${session.LOCALE_LANGUAGE=='en_US'}"></span></c:if></a>
      </div>
   </div>
   </div>
</div>
<div id="mainnav_bg">
   <div></div>
   <p><ww:property value="getText('Index.welcome')"/>${loginer.username} </p>
   <div id="menu">
    
   <ul>
      <li id="one"><a href="#"><ww:property value="getText('Index.newEmail')"/></a>(<span>0</span>) </li>
      <li id="two"><a href="#"><ww:property value="getText('Index.treatsManages')"/></a>(<span>0</span>)  </li>
      <li id="three"><a href="#"><ww:property value="getText('Index.news')"/></a>(<span>0</span>)  </li>
      <li id="four"><a href="#"><ww:property value="getText('Index.OnlineUser')"/></a>(<span><%=com.cyberway.core.web.listener.OnLine.getLogonline()%></span>)  </li>
      <li id="five">（<a href="javascript:logout();"><ww:property value="getText('Index.exit')"/></a>）</li>
  </ul>
  </div>
</div>
 <!-- -->
<div id="main">
<div id="main_left_line" style="display:none;" onclick="Lsplit( document.all.main_left,document.all.main_left_line)"></div>
	<div id="main_left">
	    <div id="main_left_footer" >
	         <div style="height:114px;"></div>
             <div id="main_left_center">
	              <div id="main_left_header"></div>
	              <div id="main_left_center_content">
				  <div id="main_left_split" onclick="Lsplit(document.all.main_left_line,document.all.main_left)"></div>
	                 <iframe src="index!menu.action" llowtransparency="yes" id="menu_frame" name="menu_frame" scrolling="no" width="100%" height="440" frameborder="0"></iframe>
	              </div>
          </div> 
      </div> 
    </div>
  <!-- -->
  <div id="main_right"> 
  <div style="height:114px;"></div> 
  <iframe src="index!main.action" llowtransparency="yes" id="main_frame" name="main_frame" scrolling="auto" width="100%" height="450" frameborder="0"></iframe>
  </div>
  </div>
<!-- -->	
</div>

</body>
</html>
<script language="javascript">
 //init context menu
 //dhtmlxMenuImPath="${ctx}/images/cms/";
 aMenu=new dhtmlXContextMenuObject('120',0,dhtmlxMenuImPath);
 aMenu.menu.setGfxPath(dhtmlxMenuImPath);		
 aMenu.menu.loadXML("${ctx}/portals/cms/right_menu_xml.xml");
 //set css
 //aMenu.menu.tableCSS="menuTable";				
 aMenu.setContextMenuHandler(onButtonClick);
		
 //set context zones
 aMenu.setContextZone("header",1);
 
 function onButtonClick(itemId,itemValue){
 //alert(itemId+":"+itemValue);
  if(itemId=='index')
   setStyle(itemId);
  if(itemId=='index1')
    setStyle(itemId);
 }
</script>		