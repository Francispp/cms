<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="SQL导向" />
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<link href="${ctx}/styles/dtree.css" type="text/css" rel="stylesheet">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/frame_js/select.js"></script> 
<script src="${ctx}/common/core_js/seltransfer.js" type="text/javascript"></script>


<script type='text/javascript' src='/dwr/interface/ReportManagerService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>


<script language="JavaScript">
 var contextPath = '${ctx}';
 
 window.onload = function(){   
	ReportManagerService.getTables(function(list){ 
		if(list != null){
		    //清空选项
			DWRUtil.removeAllOptions(document.getElementById("tables"));
			//添加先项
		    DWRUtil.addOptions(document.getElementById("tables"),list); 
		    //获取SQL语句中的表名
			var tablen = "${tableName}";  
		    //判断表名是否存在
		    if(tablen.length > 0){ 
		        //获取所有表名
		    	var tables =  document.getElementById("tables");
		    	for (var i=0; i<tables.options.length; i++){
				 if(tables.options[i].value == tablen){
				 	  //选中当前表
				 	   tables.options[i].selected = true;
				      //将已字段信息添加到selected Options中
				      
				      //字段值
				      var fields = "${selectedfields}";
				      if(fields.length> 0){
				          resetSelect("selected",fields.split(","));
				      }
			       	   
			       	   //创建SQL 然后加载 Fields字段
			           var sql = "SELECT * FROM "+tablen;
			           loadFields(sql);
			           
			           break;
				 }
			    }
			}
	    }
 	});  
 }
 
 //初始 SELECTED 字段的值
 function resetSelect(id,list){ 
  if(list.length > 0){ 
     DWRUtil.removeAllOptions(document.getElementById(id)); 
     DWRUtil.addOptions(document.getElementById(id),list);  
  }
 }
 
 //创建SQL并加载SQL中的字段
 function createSqlAndloadFields(){
   var tables =  document.getElementById("tables"); 
   var sql;
   for (var i=0; i<tables.length; i++){
	 if(tables.options[i].selected){
	   sql = "SELECT * FROM "+tables.options[i].value;  
	 }
   }
   loadFields(sql);
 }
 
function loadFields(sql){ 
   ReportManagerService.getFieldsBySql(sql,"",function(list){
	 if(list != null){
	    //判断是否出错
	 	if(list.length == 1){
	 	   if(list[0].substr(0,6)=="error:"){
	 	      alert(list[0].substring(6));
	 	      return false;
	 	   }
	 	} 
	 	//获取 已选信息 
		var selectedops = document.getElementById("selected").options;
		var chooselist = new Array();
		var selectedlist = new Array(); 
	 	//添加字段 
		if(selectedops.length <=0){
		  chooselist = list.concat();
		}else{
		  for (var i=0; i<list.length; i++){
		   var iscontants = false; 
		   // 判断已选字段中是否已存在 
	 	   for (var j=0; j<selectedops.length; j++){ 
		     if(selectedops[j].value == list[i]){
		        selectedlist.push(list[i]); 
		        iscontants = true;
		        break;
      	     }
		   }
		   
		   //如果在已选字段中不存在 则添加到Choose中
		   if(!iscontants){
		      chooselist.push(list[i]);
      	   } 
		  }
        } 
	    //清空选项
		DWRUtil.removeAllOptions(document.getElementById("choice")); 
		DWRUtil.removeAllOptions(document.getElementById("selected")); 
		//添加先项
		DWRUtil.addOptions(document.getElementById("choice"),chooselist);
		DWRUtil.addOptions(document.getElementById("selected"),selectedlist);
	 } 
   }); 
}
 

 function doReturn(){
    var tablen , selectedfields="", sql;
    //获取表名
    var tables =  document.getElementById("tables"); 
    for (var i=0; i<tables.length; i++){
	 if(tables.options[i].selected){
	   tablen =  tables.options[i].value;
	 }
    }
    
    //获取选择的字段
    var selecteds =  document.getElementById("selected"); 
    if(selecteds.options.length <=0){
       alert("至少选择一个字段");
       return false;
    }
    	
    for (var i=0; i<selecteds.options.length; i++){ 
	   selectedfields +=  selecteds.options[i].value+",";   
    }
    selectedfields = selectedfields.substring(0,(selectedfields.length-1))
    
     
    
    //拼凑成SQL
    sql = "SELECT "+selectedfields+" FROM " +tablen;
    
    //返加参数。 并关闭 
    window.returnValue = {'tablename':tablen,'sql':sql};
    window.close();
 }
</script>
</head>
<body topmargin="0" >

<table width="100%" border="0" align="left" cellpadding="0"
	cellspacing="10">
	<tr>
		<td height="28" bgcolor="#FFFFFF" colspan="2" align="right"><input
			type='button' name='btnReturn' value='确定' class='bt-enter'
			onClick='doReturn();'> <input type="button" class="bt-cancel"
			onclick="doExit()" value="取消"></td>
	</tr>
	<tr class="row-hd">
		<td height="2" colspan="2"></td>
	</tr>
</table>
<div id="step1" style="display: block; position: absolute; ">
<table width="100%" border="0" align="left" cellpadding="0"
	cellspacing="10">
	<tr>
		<td align="left" class="inside_list">选择表名：</td>
		<td align="left" style="float:left;">
		<table width="440" border="0" style="text-align:center;"
			cellpadding="0" cellspacing="1">
			<tr>
				<td width="130">可选择表</td>
				<td width="30">&nbsp;</td>
				<td width="135">已选择表</td>
				<td width="90"></td>
			</tr>
			<tr>
				<td rowspan="4" valign="top" align="left"> 
				<select name="choicetables" size="7" multiple="true" style="width:150px;" > </select></td>
				<td><input type="button" value=" &gt; " onClick="move_beeline(choicetables,selectedtables)" /></td>
				<td rowspan="4" valign="top"><select name="selectedtables" size="7" style="width:140px;" multiple="true"></select></td>
			</tr>
			<tr>
				<td><input type="button" value="&gt;&gt;" onClick="move_all(choice,selected)" /></td> 
			</tr>
			<tr>
				<td><input type="button" value=" &lt; " onClick="move_beeline(selected,choice)" /></td>
				<td><input type="button" value="下移" onClick="move_down(selected)" /></td>
			</tr>
			<tr>
				<td><input type="button" value="&lt;&lt;" onClick="move_all(selected,choice)" /></td>
			</tr>
		</table> 
		</td>
	</tr>
	<tr>
		<td align="left" class="inside_list" valign="top">报表显示字段：</td>
		<td align="left">
		<table width="440" border="0" style="text-align:center;"
			cellpadding="0" cellspacing="1">
			<tr>
				<td width="130">可选择字段</td>
				<td width="30">&nbsp;</td>
				<td width="135">已选择字段</td>
				<td width="90">字段操作</td>
			</tr>
			<tr>
				<td rowspan="4" valign="top" align="left"><select name="choice"
					size="7" multiple="true" style="width:150px;">
				</select></td>
				<td><input type="button" value=" &gt; " onClick="move_beeline(choice,selected)" /></td>
				<td rowspan="4" valign="top"><select name="selected" size="7" style="width:140px;" multiple="true"></select></td>
			</tr>
			<tr>
				<td><input type="button" value="&gt;&gt;" onClick="move_all(choice,selected)" /></td>
				<td><input type="button" value="上移" onClick="move_up(selected)" /></td>
			</tr>
			<tr>
				<td><input type="button" value=" &lt; " onClick="move_beeline(selected,choice)" /></td>
				<td><input type="button" value="下移" onClick="move_down(selected)" /></td>
			</tr>
			<tr>
				<td><input type="button" value="&lt;&lt;" onClick="move_all(selected,choice)" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div id="step2" style="display: block; position: absolute; ">
<table width="100%" border="0" align="left" cellpadding="0"
	cellspacing="10">
	<tr>
		<td align="left" class="inside_list">选择表名：</td>
		<td align="left" style="float:left;">
			<select id="tables" style="width:240px;" size="4" onchange="createSqlAndloadFields()"></select>
		</td>
	</tr>
	<tr>
		<td align="left" class="inside_list" valign="top">报表显示字段：</td>
		<td align="left">
		<table width="440" border="0" style="text-align:center;"
			cellpadding="0" cellspacing="1">
			<tr>
				<td width="130">可选择字段</td>
				<td width="30">&nbsp;</td>
				<td width="135">已选择字段</td>
				<td width="90">字段操作</td>
			</tr>
			<tr>
				<td rowspan="4" valign="top" align="left"><select name="choice"
					size="7" multiple="true" style="width:150px;">
				</select></td>
				<td><input type="button" value=" &gt; " onClick="move_beeline(choice,selected)" /></td>
				<td rowspan="4" valign="top"><select name="selected" size="7" style="width:140px;" multiple="true"></select></td>
			</tr>
			<tr>
				<td><input type="button" value="&gt;&gt;" onClick="move_all(choice,selected)" /></td>
				<td><input type="button" value="上移" onClick="move_up(selected)" /></td>
			</tr>
			<tr>
				<td><input type="button" value=" &lt; " onClick="move_beeline(selected,choice)" /></td>
				<td><input type="button" value="下移" onClick="move_down(selected)" /></td>
			</tr>
			<tr>
				<td><input type="button" value="&lt;&lt;" onClick="move_all(selected,choice)" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
</body>
</html>
