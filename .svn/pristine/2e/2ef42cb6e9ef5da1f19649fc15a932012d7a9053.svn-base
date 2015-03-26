<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
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
//点击树节点执行的方法
function onclickItem(value) {
	var myUrl='${ctx}/base/${albumType}!changeAlbums.action?';
	 if(value=='album_list'&&('${id}'==null||'${id}'=='')){
		 return ;
	 }else if(value=='default_album'&&('${id}'==null||'${id}'=='')){
		 alert('当前文件已在默认专辑中');
		 return ;
	 }else if('${id}'==value){
		 alert('当前文件已在专辑中');
		 return ;
	 }
	 param="&albumId="+value+"&keys=${keys}";
	 
	 if(confirm('您确定要转移选择的记录吗？')){
		 new Ajax.Request(myUrl, {
			    method:'post',
			    parameters:param,
			    onSuccess: function(transport) {
			    	alert('转移成功');
			    	global_ab.fn.popWindow.removePopWindow(false);
			    },
			    onFailure: function(){alert('转移失败');global_ab.fn.popWindow.removePopWindow(false);}
			  });
		}
}


var treeSelectId="";
//关闭所有右击菜单
function closeAllMenu() {
  HideAll("RootTreeMenu",0);
  HideAll("ItemTreeMenu",0);
}

function myDragHandler(){
	
}
</script>
<style type="text/css">
.pw-w385_ab {
	width:380px;
	height:350px;
	
}
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:100px;
	display:block;
	float:left;
	text-align:right;
	height:22px;
	line-height:22px;
	color:#333;
}
.textarea-hb{
	border:1px #999 solid;
	width:90%;
	margin:7px 0;
}

.textField-w150_ab {
	width:150px;
}
.textField-w150_ab {
	width:150px;
}

.select-w150_ab {
	width:150px;
	_width:150px;
}
.pw-con-input-item-plu_ab {
	border:1px solid #999;
	width:200px;
	height:200px;
#height:200px;
}
.textarea-w150_ab{
	width:150px;
	height:60px;
}
</style>
</head>
<body class="pw_ab" style="overflow:hidden;padding:0px;margin:0px;border:0px;" onmouseup="OnMouseUp('RootTreeMenu');" oncontextmenu="window.event.returnValue=false">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<c:if test="${not empty session.ActionError}">
  <script>alertMessage("${session.ActionError}");</script>
  <% session.removeAttribute("ActionError"); %>
</c:if>



<div class="pw-borderOut_ab pw-w380_ab">
<ww:hidden  name="type" id="type"/>
<ww:hidden  name="objectid" id="objectid"/>
<ww:hidden  name="keys" id="keys" value=""/>
	<div class="pw-borderIn_ab"  style="height:380px;">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>改变专辑</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab">
        	<ul class="pw-con-input_ab" style="height:300px; overflow-y:auto">
        	
        	<li class="pw-con-input-item_ab">
				<div id="albumTree_Root"></div>
            </li>
            

            </ul>
            <div class="pw-con-btns_ab">
              
            </div>
        </div>
    </div>



</div>

</body>
</html>
<script language="javascript">
function myRightClickHandler(){
	
}
<!--
treeRoot = new dhtmlXTreeObject('albumTree_Root','100%','100%','S_Root');
treeRoot.enableDragAndDrop(1);
treeRoot.setOnClickHandler(onclickItem);
treeRoot.setOnRightClickHandler(myRightClickHandler);
treeRoot.setDragHandler(myDragHandler);
treeRoot.insertNewChild('S_Root','album_list',"专辑列表",0,0,0,0,"");
treeRoot.loadXML("${ctx}/base/album!xml.action?albumType=${albumType}");
//treeRoot.setXMLAutoLoading("${ctx}/base/album!itemAlbum.action");
treeRoot.insertNewChild('album_list','default_album',"默认专辑",0,0,0,0,"");

var DefaultTreeMenu = new RightMenu("DefaultTreeMenu");
DefaultTreeMenu.AddItem("update10","","","刷新专辑","DefaultTreeMenu","javascript:refreshMenu();");
document.writeln(DefaultTreeMenu.GetMenu()); 

  var RootTreeMenu = new RightMenu("RootTreeMenu");
  document.writeln(RootTreeMenu.GetMenu()); 
  
  
  var ItemTreeMenu = new RightMenu("ItemTreeMenu");  
   document.writeln(ItemTreeMenu.GetMenu());  
-->
</script>

<script type="text/javascript">
//<![CDATA[
global_ab.init.popwindowPage_fn();

(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();

//]]>
</script>
