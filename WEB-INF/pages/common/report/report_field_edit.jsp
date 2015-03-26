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
		if(fieldprops.length == 3){
			fieldName.value = fieldprops[0];
			fieldAlias.value = fieldprops[1]; 
			fieldCellWidth.value = fieldprops[2]; 
		}else{
		  fieldName.value = fieldprops[0];
		  fieldAlias.value = fieldprops[0];
		  fieldCellWidth.value = 20;
		} 
    }
 }
 
 
 function doReturn(){
 	
    var returnval,type,searchfield;
    var name =fieldName.value; 
    
    var alias = fieldAlias.value; 
 	if(alias.length <=0){
 	    alias = name;
 	} 
	
	var cell = fieldCellWidth.value; 
	if(isNaN(cell) || cell.length<=0){
	   cell = 20;
	}  
    returnval = name +"-"+alias+"-"+cell;
    
    //返加参数。 并关闭 
    window.returnValue = returnval;
    window.close();
 }
 
</script>
</head>
<body topmargin="0"> 
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="10"> 
	<tr class="row-hd">
		<td height="2" colspan="2"></td>
	</tr> 
	<tr>
		<td width="30%" align="left" valign="top" class="inside_list">名称</td>
		<td width="70%" align="left"><input type="text" name="fieldName" id="fieldName" readonly="readonly"/></td>
	</tr>
	<tr>
		<td width="30%" align="left" valign="top" class="inside_list">别名</td>
		<td width="70%" align="left"><input type="text" name="fieldAlias" id="fieldAlias"/></td>
	</tr> 
	<tr>
		<td width="30%" align="left" valign="top" class="inside_list">列表宽度：</td>
		<td width="70%" align="left"><input type="text" name="fieldCellWidth" id="fieldCellWidth" size="5"/>%</td>
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