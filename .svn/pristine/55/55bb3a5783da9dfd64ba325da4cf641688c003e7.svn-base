<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="流程字段列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>

<script type="text/javascript">			
 var parameterUrl="";
 //编辑
 function editItem(oid){
  location.href='form!edit.action?id='+oid+parameterUrl;
 }
 
 function checkOne(chdname,chkall){
	var chkID = document.getElementsByName(chdname);
	allchecked=true; 
	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i) != chkall){ 
			allchecked=false;
			break;
		}
	}
	if (allchecked==false) {
		chkall.checked=false;
	}else{
	   chkall.checked=true;
	}
}


function getSelectedID(checkName){
	var checkObj = document.getElementsByName(checkName);
        var billIDs ="";	
	for(i=0;i<checkObj.length;i++){
	 if(checkObj[i].type == "checkbox" && checkObj[i].checked && checkObj[i].value!='P{fieldCode}'){
	   billIDs = billIDs+";"+checkObj[i].value;
	 }	
       } 
       return billIDs.substr(1);
}

function OK (){
   var obj=new Array;  
  obj[0]=getSelectedID('ReadWriteFields');
  obj[1]=getSelectedID('NotNullFields');
  obj[2]=getSelectedID('OnlyReadFields');
  obj[3]=getSelectedID('HiddenFields');
  obj[4]=getSelectedID('ThrowBackEmptyFields'); 
  window.returnValue = obj.toString();
  window.close(); 
}

function initFlowFields(){
 setCheckedFields('ReadWriteFields','${ReadWriteFields}');
 setCheckedFields('NotNullFields','${NotNullFields}');
 setCheckedFields('OnlyReadFields','${OnlyReadFields}');
 setCheckedFields('HiddenFields','${HiddenFields}');
 setCheckedFields('ThrowBackEmptyFields','${ThrowBackEmptyFields}');
}
//设置已先字段
function setCheckedFields(fname,selectedstr){
var selectReadWriteFields=selectedstr;//'roleCode;cmsTitle;deptOid'  ;
 if(selectReadWriteFields!=''){
  var selectRWs=selectReadWriteFields.split(";");
  if(selectRWs.length>0){
  for(n=0;n<selectRWs.length;n++){
  var ReadWriteFields = document.getElementsByName(fname);	
	for(i=1;i<ReadWriteFields.length;i++){
		if(ReadWriteFields[i].value==selectRWs[n]){
			ReadWriteFields[i].checked=true;
		}	
	}
   }
   }
   }
}

</script>
</head>
<body nowrap topmargin="0" leftmargin="0" onload="initFlowFields();" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff" align="right">
		    <input type="button" value='确定' onclick="OK()"  />  
			<input type="button" value='关闭' onclick="window.close()" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td> 
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">${title}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	
<!-- 页面主要内容 -->
<!--div id="list_scroll_content" align=justify>  -->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">

	<ec:table items="items"
	          var="item" 
	          action="${ctx}/form/fields!setFlowFields.action"        
		  editable="false" batchUpdate="false" xlsFileName="字段信息.xls"
		  pdfFileName="字段信息.pdf" csvFileName="字段信息.csv" minColWidth="80"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10" 
		  tableId="${tableId}" 
		  showPrint="true"
		  resizeColWidth="true" filterable="true"
	          filterRowsCallback="limit"
	          sortRowsCallback="limit"
	          retrieveRowsCallback="limit"	  			  
		  >	          
		<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_1" title="序号" width="3%" sortable="false" editable="false"
		 filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center" /> 
		
		<ec:column property="fieldName" title="字段中文名" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="fieldCode" title="字段名" width="20%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="fieldType" title="字段类型" width="30%"
		 sortable="true" filterable="true" editTemplate="ecs_t_input" />		
                	 
        <ec:column property="oid"
			title="<INPUT onclick=\"selectAllRow(this,'NotNullFields')\" type='checkbox' value='' name='NotNullFieldsAll'>必填"
			style="text-align:center">
			<input type="checkbox" name="NotNullFields" value="${item.fieldCode}" onclick="checkOne('NotNullFields',NotNullFieldsAll);" />
		</ec:column>
		
		<ec:column property="oid"
			title="<INPUT onclick=\"selectAllRow(this,'OnlyReadFields')\" type='checkbox' value='' name='OnlyReadFieldsAll'>只读"
			style="text-align:center">
			<input type="checkbox" name="OnlyReadFields" value="${item.fieldCode}" onclick="checkOne('OnlyReadFields',OnlyReadFieldsAll);" />
		</ec:column>
		
		<ec:column property="oid"
			title="<INPUT onclick=\"selectAllRow(this,'HiddenFields')\" type='checkbox' value='' name='HiddenFieldsAll'>隐藏"
			style="text-align:center">
			<input type="checkbox" name="HiddenFields" value="${item.fieldCode}" onclick="checkOne('HiddenFields',HiddenFieldsAll);" />
		</ec:column>
		
		<ec:column property="oid"
			title="<INPUT onclick=\"selectAllRow(this,'ThrowBackEmptyFields')\" type='checkbox' value='' name='ThrowBackEmptyFieldsAll'>回退清空"
			style="text-align:center">
			<input type="checkbox" name="ThrowBackEmptyFields" value="${item.fieldCode}" onclick="checkOne('ThrowBackEmptyFields',ThrowBackEmptyFieldsAll);" />
		</ec:column>
	 </ec:row>
	</ec:table>
</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
