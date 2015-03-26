<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="字段编辑" /> 
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<link href="${ctx}/styles/dtree.css" type="text/css" rel="stylesheet">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/frame_js/select.js"></script>
<script language="JavaScript">
 var contextPath = '${ctx}';
 
 window.onload = function initField(){
    var field = "${field}"; 
    if(field != null){
		var fieldprops  = field.split("-");
		if(fieldprops.length == 6){
			fieldName.value = fieldprops[0];
			fieldAlias.value = fieldprops[1];
			
			for (var i=0; i<showType.options.length; i++){
				if(showType.options[i].value == fieldprops[2]){
				    showType.options[i].selected = true;
				    changeModle(showType);
				    break;
				}
			} 
			seltext.value = fieldprops[3];
			selvalue.value = fieldprops[4];
			definecontent.value = fieldprops[5];
			 
		}else{
		  fieldName.value = fieldprops[0];
		  fieldAlias.value = fieldprops[0];
		} 
    }
 }
 
 
 function doReturn(){ 
    var name =fieldName.value; 
    
    var alias = fieldAlias.value; 
 	if(alias.length <=0){
 	    alias = name;
 	}
  	
  	var type;
    for (var i=0; i<showType.options.length; i++){
		if(showType.options[i].selected){
		    type = showType.options[i].value; 
		     break;
		}
	}
	
	var text = seltext.value;
	var val = selvalue.value;
	var define =  definecontent.value;  
    
    var returnval = name +"-"+alias+"-"+type+"-"+text+"-"+val+"-"+define;
    
    //返加参数。 并关闭 
    window.returnValue = returnval;
    window.close();
 }
 
 
 function changeModle(type){
 	var id1= "selecttr" ,id2="definetr";
 
	document.getElementById(id1).style.display = "none";
	document.getElementById(id2).style.display = "none";
	
	if(type.value == "select"){
		document.getElementById(id1).style.display = ""; 
	}else if(type.value == "difine"){ 
		document.getElementById(id2).style.display = ""; 
	}
}
 
</script>
</head>
<body topmargin="0"> 
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="10"> 
	<tr class="row-hd">
		<td height="2" colspan="2"></td>
	</tr> 
	<tr>
		<td width="30%" align="left" valign="top" class="inside_list">属性名称</td>
		<td width="70%" align="left"><input type="text" name="fieldName" id="fieldName" readonly="readonly"/></td>
	</tr>
	<tr>
		<td width="30%" align="left" valign="top" class="inside_list">中文名称</td>
		<td width="70%" align="left"><input type="text" name="fieldAlias" id="fieldAlias"/></td>
	</tr>
	<tr>
		<td width="30%" align="left" valign="top" class="inside_list"> 显示类型：</td>
		<td width="70%" align="left" ><select name="showType" id="showType" onchange="changeModle(this)"><option value="text">输入框</option><option value="number">数字输入框</option><option value="date">日期输入框</option><option value="select">下拉框</option><option value="difine">自定义</option></select></td>
	</tr>
	<tr id="selecttr" style="display:none;">
		<td width="30%" align="left" valign="top" class="inside_list"> 初始化选项：</td>
		<td width="70%" align="left">
		<table width="100%" border="0" align="left" cellpadding="0"
						cellspacing="1">
			<tr><td width="10%"> 值:</td><td><input type="text" name="selvalue" id="selvalue"/>多项请以,分割</td></tr>
			<tr><td>文本:</td><td><input type="text" name="seltext" id="seltext"/></td></tr>
		</table>
		</td>
	</tr>
	<tr id="definetr" style="display:none;">
		<td width="30%" align="left" valign="top" class="inside_list"> 标签内容：</td>
		<td width="70%" align="left"><input type="text" name="definecontent" id="definecontent" title="输入一个完整的标签内容"/></td>
	</tr>  
	<tr>
		<td height="28" colspan="2" align="right"><input
			type='button' name='btnReturn' value='确 定' class='bt-enter'
			onClick='doReturn();'> <input type="button" class="bt-cancel"
			onclick="doExit()" value="取消"></td>
	</tr>
</table>
</body>
</html> 