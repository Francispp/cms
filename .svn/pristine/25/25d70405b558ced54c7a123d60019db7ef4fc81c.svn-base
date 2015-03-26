  <%@ page contentType="text/html; charset=UTF-8"%>
  <%@ include file="/common/cyber_taglibs.inc"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>${objectTypeName}权限设置</title>
		<ww:head />
		<link href="${default_style}" rel="stylesheet" type="text/css">
		 <%@ include file="/common/buffalo.inc"%>
		<%@ include file="/common/js.inc"%>
		<%@ include file="/common/dhtmlxtree/tree/tree.inc"%>
		<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
	body{background-image: none;background-color: #FFF;padding:0px; margin: 0px;}
	.artEdit-input_ab{width:200px !important;}
	.containerTableStyle {min-height:340px;}
	#roleTree_{overflow: auto;height:315px;}
	.info-box{height:315px;};
</style>
	</head>
	<body topmargin="0" leftmargin="0" onload="loadTree();">
		<%@ include file="/common/messages.inc"%>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tr><td width="100%">
			<div class="system-header">
			<div class="edit-header-lion">
				<ul id="lion-ul">
					<li class="artEdit-input_ab artEdit-btn-w3letters_ab"><input type='checkbox' name='isInheritPerm' id='isInheritPerm' value='1' onclick='changeIsInheritPerm();' <c:if test="${document.isInheritPerm==1}">checked</c:if>/>将继承父系的权限</li>
					<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
				             <a class="artEdit-btn-in_ab" href="javascript:reLoadTree();">
				              <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
				             <span>重置</span>
				             </a>
				             </li>
				    <li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
				          <a class="artEdit-btn-in_ab" href="javascript:savePermissions();">
				            <img src="${default_imagepath}/ico_004_floppy.gif" class="ico_ab ico-017_ab" />
				            <span>保存</span>
				         </a>
				    </li>
					<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
				          <a class="artEdit-btn-in_ab" href="javascript:window.close();">
				            <img src="${default_imagepath}/ico_097_closeSquare.gif" class="ico_ab ico-017_ab" />
				            <span>关闭</span>
				         </a>
				    </li>
				    <li class="fn-clear"></li>
				</ul>
			</div>
			</div>
			<div class="content">
				<div class="info-box">
					<div id="roleTree_"></div> 
				</div>
			</div>
</td></tr></table>
<script type="text/javascript">
var isUpdated = false;//当数据发生改变时才可以保存或重置
function loadTree(){
	document.getElementById("roleTree_").innerHTML = "";
	tree = null;
	tree=new dhtmlXTreeObject("roleTree_","100%","100%",0);
	tree.setImagePath("${ctx }/common/dhtmlxtree/tree/csh_vista/");
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(1);
	tree.enableDragAndDrop(0);
	tree.setOnCheckHandler(modifyPermission);
	tree.loadXML("${ctx}/base/role!getIdentityGradeTreeXml.action?objectId=${document.id}");
	isUpdated = false;
}
function reLoadTree(){
	if(isUpdated)loadTree();
}
function modifyPermission(id){
	isUpdated = true;
}
function changeIsInheritPerm(){
	 var hitMessage='将继承父系的权限吗？';
	  var obj=new Array;
	  	  obj[0]=3;
		  obj[1]='${document.id}';
		  obj[2]=1;
		  obj[3]=3;
	 if(document.getElementById('isInheritPerm').checked){
	   obj[2]=1;
	   hitMessage='设置'+hitMessage;
	 }else{
	   obj[2]=0;
	   hitMessage='设置不'+hitMessage;
	 }
	 var flag=document.getElementById('isInheritPerm').checked;
	 if(confirm(hitMessage)){
		  ExecuteService("if(reply.getResult()=='true'){alert('保存成功!');}else alert('保存失败!');",'cmsPermissionService','updateIsInheritPerm',obj); 	
		  }else{
		  if(flag==false){
			 document.getElementById('isInheritPerm').checked=true;
		  }else{
			 document.getElementById('isInheritPerm').checked=false;
		  }
	  }
	 }
function savePermissions(){
	if(isUpdated){
		var obj=new Array;
		obj[0]='${document.id}';
		obj[1]=tree.getAllChecked();
		ExecuteService("if(reply.getResult()=='true'){alert('保存成功!');}else alert('保存失败!');",'cmsPermissionService','saveDocumentPermission',obj);
	}else{
		alert('保存成功!');
	}
}
</script>
</body>
</html>