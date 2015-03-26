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
<%@ include file="/common/ext-2.0.2/ext-res.inc"%>

<script src="${ctx}/common/dtree/dtree.js"></script>
<script src="${ctx}/common/frame_js/select.js"></script> 
<script src="${ctx}/common/core_js/seltransfer.js" type="text/javascript"></script>
<script src="/common/validation/prototype.js" type="text/javascript"></script>

<script type='text/javascript' src='/dwr/interface/ReportManagerService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<style type="text/css">
	 td{
		background-color: #ffffff;
	}
</style>

<script language="JavaScript">
 var contextPath = '${ctx}';
 
 window.onload = function(){   
	ReportManagerService.getTables(function(list){ 
		if(list != null){
		    //清空选项
			DWRUtil.removeAllOptions($("choicetables"));
			
			list.sort();
			
			//添加先项
		    DWRUtil.addOptions($("choicetables"),list); 
	    }
 	});  
 }
 
 //初始 SELECTED 字段的值
 function resetSelect(id,list){ 
  if(list.length > 0){ 
     DWRUtil.removeAllOptions($(id)); 
     DWRUtil.addOptions($(id),list);  
  }
 }
 
 //创建SQL并加载SQL中的字段
 function createSqlAndloadFields(){
   var tableops =  $("selectedtables").options;
   var sql, tablealias; 
   //备份selected  
   copy("selectbak","selected");
   
    //清空所有字段选项
	DWRUtil.removeAllOptions($("choice")); 
	DWRUtil.removeAllOptions($("selected")); 
   
   for (var i=0; i<tableops.length; i++){
   	sql = "SELECT * FROM "+tableops[i].text; 
   	tablealias =tableops[i].value.toLowerCase();
   	if(tablealias.indexOf("_") != -1)
   		tablealias =tablealias.substring(tablealias.lastIndexOf("_"));
   	 
  	loadFields(sql,tablealias); 
   }
 }
 
 function copy(target,src){
 	var srcops = $(src).options;
 	var targetops = $(target).options;
 	//清空目标
 	DWRUtil.removeAllOptions($(target));
 	//复制
 	for (var j=0; j<srcops.length; j++){ 
		targetops.add(new Option(srcops[j].text,srcops[j].value)); 
	}
 }
 
 //只取表结构
 function dellSql(sql){
 	 var tempsql ;
 	 if(sql.toUpperCase().indexOf("WHERE") != -1)
	 	 tempsql = sql.substring(0,sql.toUpperCase().indexOf("WHERE")+6)+" 1=2 "; 
	 else
	 	tempsql = sql +" WHERE 1=2";
	 	
	 return tempsql;
 }
 
 
 function loadFields(sql,tablename){ 
   //只取表结构
   var tempsql = dellSql(sql);  
   
   ReportManagerService.getFieldsBySql(tempsql,"",function(list){
	 if(list != null){
	    //判断是否出错
	 	if(list.length == 1){
	 	   if(list[0].substr(0,6)=="error:"){
	 	      alert(list[0].substring(6));
	 	      return false;
	 	   }
	 	}
	 	//获取 已选信息 
		var selectedops = $("selectbak").options;
		var chooselist = new Array();
		var selectedlist = new Array(); 
	 	//添加字段 
		for (var i=0; i<list.length; i++){
		  var iscontants = false; 
		  // 判断已选字段中是否已存在 
		   for (var j=0; j<selectedops.length; j++){ 
		    if(selectedops[j].value == tablename+"."+list[i]){
		       selectedlist.push(tablename+"."+list[i]); 
		       iscontants = true;
		       break;
		   	}
		  }
		  
		  //如果在已选字段中不存在 则添加到Choose中
		  if(!iscontants){
		     chooselist.push(tablename+"."+list[i]);
		     }
		 } 
		//添加先项
		DWRUtil.addOptions($("choice"),chooselist);
		DWRUtil.addOptions($("selected"),selectedlist);
	 } 
   }); 
}
 
 
  function move_Table(left,right){  
	if(left && right){
		for(var i = left.options.length-1 ; i >=0 ; i--){//循环左框所有值对象 
			if(left.options[i].selected){//判断值是否被选中 
			  //添加右边列表的信息 
			 	var schema = $F("Schema");
			 	var text = left.options[i].text;
			 	if(schema != ""){
			 		if(text.indexOf(".") == -1)
			   	 		text = schema + "." + text;
			 		else {
			 			text = schema + text.substring(text.indexOf("."));
			 		}
			   	}
			   	right.options[right.options.length] = new Option(text,left.options[i].value); 
				left.options[i].removeNode(true);
			}
		 }
	  }
   }
  

 function doReturn(){ 
 	//获取选择的字段
    var tables =  $("selectedtables"); 
    if(tables.options.length <=0){
       alert("至少选择一张表");
       return false;
    }
    
    //获取选择的字段
    var selecteds =  $("selected"); 
    if(selecteds.options.length <=0){
       alert("至少选择一个字段");
       return false;
    }
    
    //返加参数。 并关闭 
    window.returnValue = markSql();
    window.close();
 }
 
 var max = 5, page = 1;
 function pageJump(type)
 {
 	page = page+type;
	if(page < 1)
		page = 1;
	else if(page > max)
		page = max;
		
	if(page == max) {
		$("next").style.visibility="hidden"; 
		$("previous").style.visibility="visible";
	}else if(page == 1){
		$("previous").style.visibility="hidden";
		$("next").style.visibility="visible";
	}else{
		$("next").style.visibility="visible";
		$("previous").style.visibility="visible";
	} 
	
	for(var i = 1; i<=max; i++)
		$("step"+i).style.display = "none"; 
		
	//当进入到第五步时。 将选择列与分组列同步
	if(page == 5)
		synchoicegp();
		
	
	$("step"+page).style.display = "inline";
 }
 
 //选择列与分组列同步
 function synchoicegp(){ 
 	var selectedops = $("selected").options;
 	var choicegpops = $("choicegp").options;
 	var selectedgpops = $("selectedgp").options; 
 	
 	//跟据长度判断是否需要同步
 	if(selectedops.length != choicegpops.length+selectedgpops.length){ 
 	 
 		var list = $('selected').getElementsByTagName('option'); 
		var choosegplist = new Array();
		var selectedgplist = new Array(); 
	 	//添加字段 
		for (var i=0; i<list.length; i++){
		  var iscontants = false; 
		  // 判断已选字段中是否已存在 
		   for (var j=0; j<selectedgpops.length; j++){
		    if(selectedgpops[j].value == list[i].value){
		       selectedgplist.push(list[i].value); 
		       iscontants = true;
		       break;
		   	}
		  }
		  
		  //如果在已选字段中不存在 则添加到Choose中
		  if(!iscontants){
		     choosegplist.push(list[i].value);
		     }
		 }
		 
		    //清空所有字段选项
		DWRUtil.removeAllOptions($("choicegp"));
		DWRUtil.removeAllOptions($("selectedgp"));
		//添加先项
		DWRUtil.addOptions($("choicegp"),choosegplist);
		DWRUtil.addOptions($("selectedgp"),selectedgplist);
 	}
 }
 
 function add_where_row(){
    var table = $("wheretable");
	var newRow = table.insertRow(table.rows.length-1); //-1参数的含义是从最后一行添加,你也可以直接写某个数值参数
	var newCell = newRow.insertCell(0); //插入某单元格,顺序从0开始
    newCell.setAttribute("align","center");
    newCell.innerHTML ="<input type=\"button\" size=\"5\" value=\"删除\"    " +
                                " onclick=\"del_row(this.parentNode.parentNode.parentNode,this.parentNode.parentNode.rowIndex)\">"   //这边用innerHTML是因为要加入html标签.
	
	newCell = newRow.insertCell(1);
    newCell.setAttribute("align","center");
    newCell.innerHTML = "<select id='fkh" + newRow.rowIndex +"'><option value=''>无</option><option value=' ( ' >(</option></select>"; 
	                                                                            
    newCell = newRow.insertCell(2);
    newCell.innerHTML = copySelTagToString(["choice","selected"],"field"+newRow.rowIndex); 
    
    
    newCell = newRow.insertCell(3);
    newCell.setAttribute("align","center");
    newCell.innerHTML = "<select id='operator" + newRow.rowIndex +"'><option value=''>无</option><option value=' like '>like</option>"
    							+"<option value=' = '>=</option><option value=' >= '>>=</option><option value=' <= '><=</option>"
    							+"<option value=' > '>></option><option value=' < '><</option><option value=' != '>!=</option>"
    							+"<option value=' <> '><></option><option value=' !> '>!></option><option value=' !< '>!<</option>"
    							+"<option value=' IS NULL '>IS NULL</option><option value=' IS NOT NULL '>IS NOT NULL</option></select>";
    							
    							
    newCell = newRow.insertCell(4);
    newCell.innerHTML = "<input type='text' id='where"+newRow.rowIndex+"' name= 'where"+newRow.rowIndex+"' >"; 
                                             
    newCell = newRow.insertCell(5);
    newCell.setAttribute("align","center");
    newCell.innerHTML = "<select id='lkh" + newRow.rowIndex +"'><option value=''>无</option><option value=' ) '>)</option></select>";                                       
	
    newCell = newRow.insertCell(6);
    newCell.setAttribute("align","center");
    newCell.innerHTML = "<select id='lj" + newRow.rowIndex +"'><option value=''>无</option><option value=' AND '>AND</option><option value=' OR '>OR</option></select>";   
  }
  
  function add_orders_row(){
    var table = $("orderstable");
	var newRow = table.insertRow(table.rows.length-1); //-1参数的含义是从最后一行添加,你也可以直接写某个数值参数
	var newCell = newRow.insertCell(0); //插入某单元格,顺序从0开始
    newCell.setAttribute("align","center");
    newCell.innerHTML ="<input type=\"button\" size=\"5\" value=\"删除\"    " +
                                " onclick=\"del_row(this.parentNode.parentNode.parentNode,this.parentNode.parentNode.rowIndex)\">"   //这边用innerHTML是因为要加入html标签.
                                
    newCell = newRow.insertCell(1);
    newCell.innerHTML = copySelTagToString(["choice","selected"],"psl"+newRow.rowIndex);  
	
	newCell = newRow.insertCell(2);
    newCell.setAttribute("align","center");
    newCell.innerHTML = "<select id='psfs" + newRow.rowIndex +"'><option value=' DESC '>降序</option><option value=' ASC '>升序</option></select>"; 
	                                                                            
  }
  
  function del_row(table,row_index){ //这边的row_index为行号 
      table.deleteRow(row_index); 
  }
   
    
  //将Select标签转换成字符串. 关重设id
  function copySelTagToString(tagIds,id){
  	 var seltag = "<select id='" + id +"' style='width:120px;'>";
  	 for(var j= 0; j<tagIds.length; j++){ 
	  	 var opts = $(tagIds[j]).options;
	  	 for(var i=0; i<opts.length; i++){
	  	 	seltag +="<option title='"+opts[i].text+"' value='"+opts[i].value+"'>"+opts[i].text+"</option>";
	  	 }
  	 }
  	 seltag +="</select>";
  	 return seltag;
  }
  
  function markSql(){
  	//表
  	var tables = new Array();
  	var tableName;  
  	var tablealias;  
  	for (var i=0; i<$("selectedtables").options.length; i++){  
	   	tableName = $("selectedtables").options[i].text;
	   	tablealias =tableName.toLowerCase();
	   	if(tablealias.indexOf("_") != -1)
	   		tablealias =tablealias.substring(tablealias.lastIndexOf("_"));
	   	
	   	tables.push(tableName+" as "+tablealias);
	} 
   
  	
    
    //字段 
	var fields = new Array();  
  	for (var i=0; i<$("selected").options.length; i++){ 
	   fields.push($("selected").options[i].value); 
    } 
    //计算列
    if($F("count") != "")
    	fields.push($F("count")); 
  	
    
  	
  	//WHERE
	var wheres = readWhereDate(); 
  	
  	//Order by
  	var orders = readOrderDate();
   
  	
  	//Group by
	var groups = new Array();  
	for (var i=0; i<$("selectedgp").options.length; i++){ 
		groups.push($("selectedgp").options[i].value); 
	} 
    
	var sql = "SELECT "
	if(fields.length > 0){
		 sql+= fields.join(',')
	}
	if(tables.length > 0){
		sql+=  " FROM "+tables.join(',')
	}
	if(wheres.length > 0){
		sql+= " WHERE "+wheres.join(',')
	}
	if(groups.length > 0){
		sql+= " Group by "+groups.join(',')
	}
	if(orders.length > 0){
		sql+= " ORDER BY "+orders.join(',');
	}
	
	return sql;
  }
  
  function Show(){
  	var sql = markSql();
  	//显示SQL  
	var win=new Ext.Window({
				title:"<div style='text-align:left;font-size:14px;'>SQL预览</div>",width:300,height:240, 
				html:"<div style='margin: 1px; text-align:left; font-size:14px;'>"+sql+"</div>",
				buttons:[{ text:"验证",handler:function (){
				
					   //只取表结构
					   var tempsql = dellSql(sql); 
					   
					   ReportManagerService.getFieldsBySql(tempsql,"",function(list){
					     var msg;
					   
						 if(list != null){
						    //判断是否出错
						 	if(list.length == 1){
						 	   if(list[0].substr(0,6)=="error:"){
						 	      msg = list[0].substr(0,6);
						 	   }
						 	}
						 }
						 
						 if(!msg){
						 	msg ="execute right!"
						 }
						 
						  var win1=new Ext.Window({
							title:"<div style='text-align:left;font-size:14px;'>提示</div>",width:300,height:240, 
							html:"<div style='margin: 1px; text-align:left; font-size:14px;'>"+msg+"</div>",
							buttons:[{ text:"关闭",handler:function(){win1.close();}}] 
							});
						  win1.show();
					   }); 
				},scope:this},{ text:"关闭",handler:function(){win.close();}}]
		       });
	win.show(); 
  }
  
  
  function readWhereDate(){
  	var rowsdata =  new Array(); 
  	var fkh,field,operator,where,lkh,lj; 
  	
  	//因为前两行为类别标题和列标题 所以i 以2开头
  	for(var i=2; i<$("wheretable").rows.length-1; i++)
  	{ 
  		fkh = $("fkh"+i).value;
  		field = $("field"+i).value;
  		operator = $("operator"+i).value;
  		where = $("where"+i).value;
  		lkh = $("lkh"+i).value;
  		lj = $("lj"+i).value; 
  		
  		//拼凑一个条件
	  	rowsdata.push(fkh+field+operator+where+lkh+lj);
  	}
 
  	return rowsdata;
  }
  
   function readOrderDate(){
  	var rowsdata =  new Array(); 
  	var psl,psfs;
  	
  	//因为前两行为类别标题和列标题 所以i 以2开头
  	for(var i=2; i<$("orderstable").rows.length-1; i++)
  	{ 
  		psl = $("psl"+i).value;
  		psfs = $("psfs"+i).value; 
  		
  		//拼凑一个条件
	  	rowsdata.push(psl+psfs);
  	} 
  	return rowsdata;
  }
  
  
  //添加临时的Schema
  function addSchemaOps(){
  	if($F("newSchema") != ""){
	  	$("Schema").options.add(new Option($F("newSchema"),$F("newSchema"))); 
	  	$("Schema").options.selectedIndex = $("Schema").options.length-1;
	  	$("Schema").focus();
	  	$("newSchema").value="";
  	}
  } 
 
</script>
</head>
<body style="padding-left: 5px;	"> 
<select id="selectbak" style="display: none;"></select>
<table width="100%" border="0" align="left" cellpadding="0"
	cellspacing="10" style="float: left;">
	<tr>
		<td height="28" bgcolor="#FFFFFF" colspan="2" align="right"><input
			type='button' name='btnReturn' value='确定' class='bt-enter'
			onClick='doReturn();'> <input type="button" class="bt-cancel"
			onclick="doExit()" value="取消"></td>
	</tr> 
</table>
<div id=step1 style="display: inline; text-align: center;overflow-x: hidden; overflow: scroll; width:630px; height: 200px; background-color:transparent">
<table  style=" background-color: #81B9E2;" width="610" border="0" align="left" cellpadding="2" cellspacing="1" >
	<tr>
		<td style="background-color: #81B9E2; text-align:center; font-weight: bold; font-size: 12px;" colspan="2">选择数据表</td>
	</tr>
	<tr>
		<td align="left" class="inside_list" width="90">选择Schema：</td>
		<td align="left" ><select name="Schema" id="Schema"><option value=""></option><option value="dbo">dbo</option><option value="root">root</option><option value="db2admin">db2admin</option> </select> &nbsp; &nbsp;<input type="text" id="newSchema" size="10" /> &nbsp;<input type="button" value="添加" onclick="addSchemaOps()"/></td>
	</tr>
	<tr>
		<td align="left" class="inside_list" >选择表名：</td>
		<td align="left" >
		<table border="0" style="text-align:center;"
			cellpadding="0" cellspacing="1">
			<tr>
				<td >可选择表</td>
				<td width="30">&nbsp;</td>
				<td >已选择表</td>
				<td >字段操作</td>
			</tr>
			<tr>
				<td rowspan="3" valign="top" align="left"> 
				<select name="choicetables" size="7" multiple="true" style="width:210px;"> </select></td>
				<td></td>
				<td rowspan="3" valign="top"><select name="selectedtables" size="7" style="width:210px;" multiple="true"></select></td>
			</tr>
			<tr> 
				<td><input type="button" value=" &gt; " onClick="move_Table(choicetables,selectedtables); createSqlAndloadFields()" /></td>
				<td><input type="button" value="上移" onClick="move_up(selectedtables)" /></td>
			</tr>
			<tr>
				<td><input type="button" value=" &lt; " onClick="move_beeline(selectedtables,choicetables); createSqlAndloadFields()" /></td> 
				<td><input type="button" value="下移" onClick="move_down(selectedtables)" /></td>
			</tr> 
		</table> 
		</td>
	</tr> 
</table>
</div>
<div id=step2 style="display: none; text-align: center;overflow-x: hidden; overflow: scroll; width: 630px; height: 200px; background-color:transparent">
<table  style=" background-color: #81B9E2;" width="610" border="0" align="left" cellpadding="2" cellspacing="1" >
	<tr>
		<td style="background-color: #81B9E2; text-align:center; font-weight: bold; font-size: 12px;" colspan="2">选择数据列</td>
	</tr>
	<tr>
		<td align="left" class="inside_list" width="90">输入计算列：</td>
		<td align="left" class="inside_list"><input type="text" id="count" name="count" style="width: 220px;" /> &nbsp;如：COUNT(columnName) , SUM(columnName) </td>
	</tr> 
	<tr>
		<td align="left" class="inside_list" >报表显示字段：</td>
		<td align="left">
		<table border="0" style="text-align:center;" cellpadding="0" cellspacing="1">
			<tr>
				<td >可选择字段</td>
				<td width="30">&nbsp;</td>
				<td >已选择字段</td>
				<td >字段操作</td>
			</tr>
			<tr>
				<td rowspan="4" valign="top" align="left"><select name="choice"
					size="7" multiple="true" style="width:210px;">
				</select></td>
				<td><input type="button" value=" &gt; " onClick="move_beeline(choice,selected)" /></td>
				<td rowspan="4" valign="top"><select name="selected" size="7" style="width:210px;" multiple="true"></select></td>
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
<div id=step3 style="display: none; text-align: center;overflow-x: hidden; overflow: scroll; width: 630px; height: 200px; background-color:transparent">
<table id="wheretable" style=" background-color: #81B9E2;" width="610" border="0" align="left" cellpadding="2" cellspacing="1" >
	<tr>
		<td style="background-color: #81B9E2; text-align:center; font-weight: bold; font-size: 12px;" colspan="7">设定条件(WHERE)</td>
	</tr>
	<tr>
		<td style="background-color: #DEE9F6; text-align:center">删除</td>
		<td style="background-color: #DEE9F6; text-align:center">括号</td>
		<td style="background-color: #DEE9F6; text-align:center">条件列</td>
		<td style="background-color: #DEE9F6; text-align:center">条件符</td>
		<td style="background-color: #DEE9F6; text-align:center">条件值</td>
		<td style="background-color: #DEE9F6; text-align:center">括号</td>
		<td style="background-color: #DEE9F6; text-align:center">逻辑符</td>
	</tr>
	<tr><td colspan="7" align="center"><input type="button" value="增加条件" onclick="add_where_row()"></td></tr>
</table>
</div>
<div id=step4 style="display: none; text-align: center;overflow-x: hidden; overflow: scroll; width: 630px; height: 200px; background-color:transparent">
<table id="orderstable" style=" background-color: #81B9E2;" width="610" border="0" align="left" cellpadding="2" cellspacing="1" >
	<tr>
		<td style="background-color: #81B9E2; text-align:center; font-weight: bold; font-size: 12px;" colspan="3">设定排序(ORDER BY)列</td>
	</tr>
	<tr><td style="background-color: #DEE9F6; text-align:center">删除</td><td style="background-color: #DEE9F6; text-align:center">排序列</td><td style="background-color: #DEE9F6; text-align:center">排序方式</td></tr>
	<tr><td colspan="3" align="center"><input type="button" value="增加排序" onclick="add_orders_row()"></td></tr>
</table>
</div>
<div id=step5 style="display: none; text-align: center;overflow-x: hidden; overflow: scroll; width: 630px; height: 200px; background-color:transparent">
<table id="orderstable" style=" background-color: #81B9E2;" width="610" border="0" align="left" cellpadding="2" cellspacing="1" >
	<tr>
		<td style="background-color: #81B9E2; text-align:center; font-weight: bold; font-size: 12px;" colspan="3">设定分组(GROUP BY)列</td>
	</tr>
	<tr>
		<td align="left" class="inside_list" width="90">选择他分组列：</td>
		<td align="left">
			<table border="0" style="text-align:center;" cellpadding="0" cellspacing="1">
			<tr>
				<td >可选择字段</td>
				<td width="30">&nbsp;</td>
				<td >已选择字段</td>
				<td >字段操作</td>
			</tr>
			<tr>
				<td rowspan="4" valign="top" align="left"><select name="choicegp"
					size="7" multiple="true" style="width:210px;">
				</select></td>
				<td><input type="button" value=" &gt; " onClick="move_beeline(choicegp,selectedgp)" /></td>
				<td rowspan="4" valign="top"><select name="selectedgp" size="7" style="width:210px;" multiple="true"></select></td>
			</tr>
			<tr>
				<td><input type="button" value="&gt;&gt;" onClick="move_all(choicegp,selectedgp)" /></td>
				<td><input type="button" value="上移" onClick="move_up(selectedgp)" /></td>
			</tr>
			<tr>
				<td><input type="button" value=" &lt; " onClick="move_beeline(selectedgp,choicegp)" /></td>
				<td><input type="button" value="下移" onClick="move_down(selectedgp)" /></td>
			</tr>
			<tr>
				<td><input type="button" value="&lt;&lt;" onClick="move_all(selectedgp,choicegp)" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<table width="100%" border="0" align="left" cellpadding="0"
	cellspacing="10" style="float: left; margin-bottom: 10px;">
	<tr>
		<td height="28" bgcolor="#FFFFFF" colspan="2" align="right">
			<input type='button' value='预 览' onclick="Show()">
			<input type='button' style="visibility: visible;" value='上一步' onclick="pageJump(-1)" id="previous">
			<input type="button" style="visibility: visible;"  value="下一步" onclick="pageJump(1)" id="next">
	    </td>
	</tr> 
</table> 
</body>
</html>
