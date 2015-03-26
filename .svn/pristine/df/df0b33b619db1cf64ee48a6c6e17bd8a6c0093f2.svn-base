<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>CMS内容管理系统</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<link href="${sytlePath}/main_style.css" rel="stylesheet" type="text/css"/>
 <script language="javascript">
	<!--
	function Lsplit(view,hidden)
	{
	window.parent.document.getElementById(view).style.display= "block";
	window.parent.document.getElementById(hidden).style.display= "none";
	}
	//初始化树
       function initTree(treeBox,xmlUrl){
	 tree=new dhtmlXTreeObject(treeBox,"100%","100%",0);
	 //enable Drag&Drop
	 tree.enableDragAndDrop(1);
	 //set my Drag&Drop handler
	 tree.setDragHandler(myDragHandler); 
	 tree.loadXML(xmlUrl);
	 tree.setOnClickHandler(doOnClickTree);
	 return tree;
	}
	//拖动时，事件
       function myDragHandler(idFrom,idTo){
 	//if we return false then drag&drop be aborted
	return true;
	}
	
 //树上单击事件
       function doOnClickTree(nodeId){
	var myUrl = tree.getUserData(nodeId,"url");
	if(nodeId=='logout')
	parent.logout();
	 //confirmMessage( '您确定退出系统么?','提示', parent.logout);
	 //parent.Ext.MessageBox.confirm('提示', '您确定退出系统么?', parent.logout);
	else{
	if(myUrl==null||myUrl=='')
	return ;
	parent.setMainFrameUrl(myUrl);	
	}
	}	
	-->
 </script>
</head>
<body>
<div id="main_left">

	              <div id="main_left_center_content">
			<div id="main_left_split" onclick="Lsplit('main_left_line','main_left')" style="height:414px !important"></div>
	                  <ul>
	                     <li id="menu_li_1" name="menu_li"><a href="javascript:clickRootMenu(1);" onFocus="this.blur()">内容采集</a>
	                      <div id="menu_div_1" style="margin-left:-20px;"><div id="menu_tree_1" style="height:314px;margin-left:0px;display:none">
	                      <iframe src="" llowtransparency="yes" id="content_frame" name="content_frame" scrolling="no" width="100%" height="314" frameborder="0"></iframe>
	                      </div></div>			     </li> 
	                     <li id="menu_li_2" name="menu_li"><a href="javascript:clickRootMenu(2);" onFocus="this.blur()">站点管理</a>
	                      <div id="menu_div_2" style="margin-left:-20px;"><div id="menu_tree_2" style="height:314px;margin-left:0px;display:none">
	                      <iframe src="" llowtransparency="yes" id="site_frame" name="site_frame" scrolling="no" width="100%" height="314" frameborder="0"></iframe>
	                      </div></div>
	                     </li>
	                     <li id="menu_li_3" name="menu_li"><a href="javascript:clickRootMenu(3);" onFocus="this.blur()">站点权限</a>
	                      <div id="menu_div_3" style="margin-left:-20px;"><div id="menu_tree_3" style="height:314px;margin-left:0px;display:none">
	                      <iframe src="" llowtransparency="yes" id="perm_content_frame" name="perm_content_frame" scrolling="no" width="100%" height="314" frameborder="0"></iframe>
	                      </div></div>
	                     </li>
	                     <li id="menu_li_4" name="menu_li"><a href="javascript:clickRootMenu(4);" onFocus="this.blur()">系统配置</a>
	                      <div id="menu_div_4"><div id="menu_tree_4" style="height:314px;margin-left:18px;display:none"></div></div>
	                     </li>	                     
	                  </ul>
	              </div>  
 </div>
</body>
</html>
<script language="javascript">
<!--
 var MENU_LI="menu_li_";
 var MNEU_DIV="menu_div_";
 var MENU_TREE="menu_tree_";
 var OPEN_MENU_ID='';//打开顶部菜单id,为空，未打开；
 
function clickRootMenu(mid){
  if(mid==null || mid=='')
   return ;
  if(OPEN_MENU_ID==''){
    OPEN_MENU_ID=''+mid;
    optRootMenu(mid,true);    
    return ;
  }
  if(OPEN_MENU_ID==mid){
    OPEN_MENU_ID='';
    optRootMenu(mid,false);
    return ;
  }
    optRootMenu(OPEN_MENU_ID,false);
    OPEN_MENU_ID=''+mid;
    optRootMenu(mid,true);

}
//isOpen true表示打开，false表示关闭
function optRootMenu(mid,isOpen){
 var M_li=document.getElementById(MENU_LI+mid);
 var M_div=document.getElementById(MNEU_DIV+mid);
 var M_tree=document.getElementById(MENU_TREE+mid);
 var TREE_XML_URL="${ctx}/portals/cms/menuxml.jsp";//树获得xml地址
 if(isOpen){
  //alert(M_li.style.background);
  M_li.style.background="url(${default_imagepath}/menu_bg1.jpg) no-repeat center top";
  M_tree.style.display="block";  
  if(mid==1)
  {
  	 if(document.getElementById('content_frame').src=='')
	   document.getElementById('content_frame').src='${ctx}/cms/site!itree.action';
  }
  else   if(mid==2)
  {
  	 if(document.getElementById('site_frame').src=='')
	   document.getElementById('site_frame').src='${ctx}/cms/site.action';
  }
  else  if(mid==3)
  {
  	 if(document.getElementById('perm_content_frame').src=='')
	   document.getElementById('perm_content_frame').src='${ctx}/cms/site!itree.action';
  }
  else
  {
	  if(M_tree.innerHTML=='')
	   initTree(M_tree,TREE_XML_URL);
	}
 
 }else{
  M_li.style.background="url(${default_imagepath}/menu_bg2.jpg) no-repeat center top";
  M_tree.style.display= "none"; 
 }
}
clickRootMenu(1);
-->
</script>
