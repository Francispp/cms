<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>CMS内容管理系统</title>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
</head>
<link href="${sytlePath}/style.css" rel="stylesheet" type="text/css"/>
<STYLE TYPE="text/css">
<!--
.navPoint {
	COLOR: #cd0000;
    CURSOR: hand;
	 FONT-FAMILY: "Webdings"; 
	 FONT-SIZE: 6pt
}
/**body {  overflow: hidden; clip:  rect(   );}**/
-->
</style>

<SCRIPT language="javascript">
//===显示/隐藏左边菜单框
function switchSysBar(opt)
{
       if(opt){
        if(opt=='open'){
           switchPoint.innerText=3;
	   document.all("frmTitle").style.display="";
	 }
        if(opt=='close'){   
          switchPoint.innerText=4;
	  document.all("frmTitle").style.display="none";
        }
        return '';   
       }
	if (switchPoint.innerText==3){
		switchPoint.innerText=4;
		document.all("frmTitle").style.display="none";
	}else{
		switchPoint.innerText=3;
		document.all("frmTitle").style.display="";
	}
}

//===显示/隐藏上边菜单框
function switchTopBar()
{
	if (switchTopPoint.innerText==5){
		switchTopPoint.innerText=6;
		document.all("frmTop").style.display="none";
	}else{
		switchTopPoint.innerText=5;
		document.all("frmTop").style.display="";
	}
}

function toJump(tohref,tdsort){
	var tds;
	
	tds = FirstMenu.cells.length / 2;
	for(i = 0;i<tds;i++)
		FirstMenu.cells[i*2].background = "";
	FirstMenu.cells[tdsort*2].background = "${default_imagepath}/menu2_04_1.jpg";
	if(tdsort == 0){
		Main.location.href = tohref;
	}else{
		LeftMenu.location.href = tohref;
	}

}

function animate()
{
//   Tip.location.reload();
   window.setTimeout("animate();", 10*60*1000, "Javascript");
}
window.setTimeout("animate();", 10*60*1000, "Javascript");

 //更改内容区内容
 function setMenuFrameUrl(murl) {
   if(murl)
    document.frames['site_frame'].location.href=murl;
   else
    document.frames['site_frame'].location.reload();
    document.getElementById('content_frame').src=''
   }
   
</SCRIPT>
<body>

<%
String siteHttp =request.getServerName();
int siteport = request.getServerPort();
String indexUrl = "http://" + siteHttp + (siteport == 80 ? "" :  ":" + siteport);
request.setAttribute("url",indexUrl);
%>


<table width="100%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td valign="top" height="73"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="73" background="${default_imagepath}/kms_02.jpg"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="${default_imagepath}/kms_01.jpg" width="441" height="85"></td>
               <td align="right" valign="bottom" style="background:url(${default_imagepath}/kms_03.jpg) no-repeat right center; width:300px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td><div id="header_nav">欢迎您：${loginer.username}　|　【<a href="javascript:logout();">退出登录</a>】　|　【<a href="${url}" target="_blank">转至首页</a>】</div></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                </tr>
              </table></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr> 
    <td valign="top"> <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td id="frmTitle" width="178" valign="top" bgcolor="#f1f1f1" height="100%">
		  <table width="178" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td width="161"><img src="${default_imagepath}/inside_8.jpg" width="178" height="8" /></td>
  </tr>
  <tr>
    <td align="center" height="100%">
    <table id="main_left_center_content" style="height:100%">
	
	   <tr>
	      <td id="menu_li_1" name="menu_li" style="height:100%"><a href="javascript:clickRootMenu(1);" onFocus="this.blur()">内容采集</a>
	      <div id="menu_div_1" style="margin-left:5px;height:100%" ><div id="menu_tree_1" style="height:100%;margin-left:0px;display:none">
	       <iframe id="content_frame" name="content_frame" width="100%" height=100%  marginwidth=0 marginheight=0 src="" frameborder=0 hspace=0 vspace=0 scrolling="auto" ></iframe>
		  </div></div>
		  </td> 
		  
     </tr>
     <tr>
      <td id="menu_li_2" name="menu_li" style="height:100%"><a href="javascript:clickRootMenu(2);" onfocus="this.blur()">站点管理</a>
		  <div id="menu_div_2" style="margin-left:5px;height:100%"><div id="menu_tree_2" style="height:100%;margin-left:0px;display:none">
		  <iframe iframe src="" allowtransparency="yes" id="site_frame" name="site_frame" scrolling="auto" width="100%" height=100% frameborder="0"></iframe>
		  </div></div>
		  </td>
		  </tr>
		  <!--tr>
		  <td id="menu_li_3" name="menu_li" style="height:100%"><a href="javascript:clickRootMenu(3);" onfocus="this.blur()">个人服务</a> 
		  <div id="menu_div_3" style="margin-left:5px;height:100%"><div id="menu_tree_3" style="height:100%;margin-left:0px;display:none">
		   <iframe iframe src="" allowtransparency="yes" id="perm_content_frame" name="perm_content_frame" scrolling="auto" width="100%" height=100% frameborder="0"></iframe>
		  </div></div> 
		  </td>
		  </tr-->
		  <cms:auth resCode="SYS_MENU_BASIC_CONFIG">
		  
		  <tr>
		  <td id="menu_li_4" name="menu_li" style="height:100%"><a href="javascript:clickRootMenu(4);"onfocus="this.blur()">系统设置</a>
		  <div id="menu_div_4" style="margin-left:5px;height:100%;">
		  <div id="menu_tree_4" style="height:100%;margin-left:0px;display:none">	
		   <iframe iframe src="" allowtransparency="yes" id="systemmenu_content_frame" name="systemmenu_content_frame" scrolling="auto" width="100%" height=100% frameborder="0"></iframe>	  
		  </div></div>
		  </td>
		  </tr>
		  </cms:auth>
	</table>
	</td>
  </tr>
</table></td>
          <td width="4" align="center" background="${default_imagepath}/leftbg.jpg"> <span class="navPoint" id="switchPoint" title="关闭/打开左栏" onClick="switchSysBar()">3</span>          </td>
          <td valign="top" bgcolor="#FFFFFF">
            <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
              <tr> 
    <td height="6" bgcolor="e9e9e9"></td>
              </tr>
              <tr>
                <td><iframe id="main_frame" name="main_frame" width="100%" height="100%" marginwidth=0 marginheight=0 src="" frameborder=0 hspace=0 vspace=0 scrolling="no" onload=javascript:dyniframesizeforpage(this.id) ></iframe></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="8" align="center" background="${default_imagepath}/footbg.jpg" style="clear:both;">
	</td>
  </tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
 <script language="javascript">
	<!--
	//注销系统
	function logout(){
	var isADUser = "${loginer.isADUser}";
	
	if(confirm('您确定退出系统吗？')){
		 if(isADUser == 'true')
			 document.location.href = '${ctx}/login!logout.action';
	    else 
		     document.location.href = '${ctx}/j_acegi_logout';
	    	
		       
		 }
		
		
	}
	
        
	//更改内容区内容
	function setMainFrameUrl(murl) {
		if(document.frames){
			document.frames['main_frame'].location.href=murl;
		} else {
			document.getElementById('main_frame').src = murl;
		}
	}	
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
	setMainFrameUrl(myUrl);	
	}
	}	
	-->
 </script>
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
 var TREE_XML_URL="${ctx}/portals/cms3/menuxml.jsp";//树获得xml地址
 if(isOpen){
  //alert(M_li.style.background);
  M_li.style.background="url(${default_imagepath}/menu_bg1.jpg) no-repeat center top";
  M_tree.style.display="block";  
  M_li.style.height="100%";
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
	  //if(M_tree.innerHTML==''){
  	  if(document.getElementById('systemmenu_content_frame').src=='')
	   document.getElementById('systemmenu_content_frame').src='${ctx}/portals/cms3/system_menu.jsp';	  
	   //initTree(M_tree,TREE_XML_URL);
	 }
	//}
 
 }else{
 //若点击当前，则使得最后一个菜单的高度为100％
  M_tree.style.display= "none"; 
  M_li.style.height="32px";
  if(M_li.style.background == "url(${default_imagepath}/menu_bg1.jpg) no-repeat center top")
  {
  for(var i=1;i<5;i++)
  {
  if(document.getElementById(MENU_LI+i))
    document.getElementById(MENU_LI+i).style.height="32px";
  }
  for(var ii=4;ii>0;ii--)
  {
  if(document.getElementById("menu_li_"+ii)){
    document.getElementById("menu_li_"+ii).style.height="100%";
    break;
    }
  }
  }
  M_li.style.background="url(${default_imagepath}/menu_bg2.jpg) no-repeat center top";
  
 }
}
clickRootMenu(1);
-->

</script>
