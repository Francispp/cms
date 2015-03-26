<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="报表信息" />

<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
 <%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%> 
<script src="${ctx}/common/core_js/seltransfer.js" type="text/javascript"></script>
<script src="${ctx}/common/frame_js/select.js"></script> 


<script type='text/javascript' src='/dwr/interface/ReportManagerService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
 
<script type="text/javascript">
function save(){ 
if(valid.validate()){
 if(document.getElementById("selected").options.length <=0){
    alert("至少一个已选择字段");
    return ;
 }
 if(document.getElementById("isupdated").value == 1){
  if(!window.confirm("已直接编辑过报表源码, 是否重新生成报表源码！")){
  	return ;
  }else{
 	 document.getElementById("isupdated").value = 0;
  } 
}
 exchangeSelToInput(); 

 myform.submit();
 }  
}
function goBack(){
 location.href='report.action';
}

function exchangeSelToInput(){
   var choicestr="";
   var selectedstr="";
   var wherestr="";
   for (var i=0; i<document.getElementById("choice").options.length; i++){
      choicestr += document.getElementById("choice").options[i].value+";";
   }
   
   for (var i=0; i<document.getElementById("selected").options.length; i++){
      selectedstr += document.getElementById("selected").options[i].value+";";
   }
   
   for (var i=0; i<document.getElementById("where").options.length; i++){
      wherestr += document.getElementById("where").options[i].value+";";
   } 
  document.getElementById("choiseField").value=choicestr;
  document.getElementById("selectedField").value=selectedstr;
  document.getElementById("whereField").value=wherestr;
}


function exchangeInputToSel(){
   
   var choicearr =document.getElementById("choiseField").value.split(';');
   var selectedarr =document.getElementById("selectedField").value.split(';'); 
   var wherearr =document.getElementById("whereField").value.split(';'); 
   
   for (var i=0; i<choicearr.length; i++){
   	  if(choicearr[i].length>0){
   	  	var fieldInfo = choicearr[i].split("-");
      	document.getElementById("choice").options.add(new Option(fieldInfo[0]+"-"+fieldInfo[1],choicearr[i]));
      }
   }  
   for (var i=0; i<selectedarr.length; i++){
   	  if(selectedarr[i].length>0){
   	  	var fieldInfo = selectedarr[i].split("-");
		document.getElementById("selected").options.add(new Option(fieldInfo[0]+"-"+fieldInfo[1],selectedarr[i]));
	  }
   }
   for (var i=0; i<wherearr.length; i++){
   	  if(wherearr[i].length>0){
   	  	var fieldInfo = wherearr[i].split("-");
		document.getElementById("where").options.add(new Option(fieldInfo[0]+"-"+fieldInfo[1],wherearr[i]));
	  }
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
 
function loadFields(){
   var sql =  document.getElementById("reportSql").value;
   
    //只取表结构
   var tempsql = dellSql(sql);  
   
   //添加显示字段
   ReportManagerService.getFieldsBySql(tempsql,"",function(list){
	 if(list != null){
	    //判断是否出错
	 	if(list.length == 1){
	 	   if(list[0].substr(0,6)=="error:"){
	 	      alert(list[0].substring(6));
	 	      return false;
	 	   }
	 	}
	 	//获取可选和已选信息
	 	var choiceops = document.getElementById("choice").options;
		var selectedops = document.getElementById("selected").options; 
		
		//与本页的选项同步
		list = synPageOptions(list,choiceops,selectedops);
	 	 
	    //清空选项
		DWRUtil.removeAllOptions(document.getElementById("choice"));
		DWRUtil.removeAllOptions(document.getElementById("selected"));
		//添加先项
		for (var i=0; i<list.length; i++){
		    var val = list[i]; 
			if(val.length>0){
				var fieldInfo = val.split("-");
				//document.getElementById("choice").options.add(new Option(fieldInfo[0]+"-"+fieldInfo[1],val));
				
				document.getElementById("choice").options.add(new Option(fieldInfo[0]+"-"+(fieldInfo[1] == null ? fieldInfo[0] : fieldInfo[1]),fieldInfo.length == 3 ? val : fieldInfo[0]+"-"+fieldInfo[0]+"-20"));
			}
		} 
	    
	 }else{
		 var empty = new Array(); 
		 DWRUtil.addOptions(tarSel,empty);
	 }
   }); 
   
   //添加where条件
   var reg = /\b[\w\.\_]+\s*[=|like|>=|<=|>|<|!=|<>|!>|!<]{1,4}\s*%*\?%*/gi;   //匹配table.field =|like|>=|<=|>|<|!=|<>|!>|!< ?
   var betweenreg = /\b[\w\.\_]+\s*between/gi;   //匹配 table.field between
   var reg2 = /\b\s*[like|=|>=|<=|>|<|!=|<>|!>|!<]/gi;				 //匹配 = ?
   var betweenreg2 = /\b\s*between/gi;				 //匹配 between
    //获取where选项
   if(sql.indexOf("?") > 0){
   		
	   var whereitems = sql.match(reg);
	   var wherefileds = new Array();
	   //取得where选项中的字段
	   for(var i=0; i<whereitems.length; i++){
	      if(whereitems[i].indexOf(".") != -1)
	      	 wherefileds.push(whereitems[i].substring(whereitems[i].indexOf(".")+1,whereitems[i].search(reg2)));
	      else
	      	 wherefileds.push(whereitems[i].substring(0,whereitems[i].search(reg2)));
	   }
	   
	   //取between
	   if(sql.toUpperCase().indexOf("BETWEEN") != -1){
	   	  	 whereitems = sql.match(betweenreg);
	   	  	 
	   	  	 for(var i=0; i<whereitems.length; i++){
		      	if(whereitems[i].indexOf(".") != -1) 
		      	  wherefileds.push(whereitems[i].substring(whereitems[i].indexOf(".")+1,whereitems[i].search(betweenreg2)));
		      	else
		      	  wherefileds.push(whereitems[i].substring(0,whereitems[i].search(betweenreg2)));
		     }
	   }
	   
	   
	   //与本页的选项同步
	   var whereops = document.getElementById("where").options;
	   wherefileds = synPageOptions(wherefileds,whereops);
	   //清空选项
		DWRUtil.removeAllOptions(document.getElementById("where"));
	   //添加先项
		for (var i=0; i<wherefileds.length; i++){
		    var val = wherefileds[i]; 
			if(val.length>0){
				var fieldInfo = val.split("-");
				
				//document.getElementById("choice").options.add(new Option(fieldInfo[0]+"-"+fieldInfo[1],val));
				document.getElementById("where").options.add(new Option(fieldInfo[0]+"-"+(fieldInfo[1] == null ? fieldInfo[0] : fieldInfo[1]),(fieldInfo.length >= 5  ? val : fieldInfo[0]+"-"+fieldInfo[0]+"-text")));
			}
		}
	}
	
	
}

function synPageOptions(list,choiceops,selectedops){
	//添加字段
	for (var i=0; i<list.length; i++){
	  var flag = true; 
	  //判断可选字段中是否已存在 
   	  for (var j=0; j<choiceops.length; j++){
      	 if((choiceops[j].value.split("-"))[0] == list[i]){
      	    flag = false;
      	    list[i] = choiceops[j].value;
      	    break;
      	 }
	   }
   	  //判断是否已经存在了
	  if(flag && selectedops){
	    // 判断已选字段中是否已存在 
		for (var j=0; j<selectedops.length; j++){ 
		   if((selectedops[j].value.split("-"))[0] == list[i]){
      	      list[i] = selectedops[j].value;
      	      break;
      	   }
		}
	   }
	}
	return list;
}

function editCode(){  
  myform.action = "report!editFile.action";
  exchangeSelToInput();
  myform.submit();
}
 
//添加别名
function addalias(SelTag){
  var title = "字段编辑";
  var url = "report!field.action";
  var val;
  
  for (var i=0; i<SelTag.options.length; i++){
	  if(SelTag.options[i].selected ){
	  	 val = SelTag.options[i].value;
	  	 url +="?field="+encodeURI(val); 
		 var returnVal = editField(url);  
		 if(returnVal){
	  		 SelTag.options[i].value = returnVal;
			 var fieldInfo = returnVal.split("-");  
	  		 SelTag.options[i].text = fieldInfo[0]+"-"+fieldInfo[1];
	  	}
	     break;
	  }
  }
}

//添加别名
function adddeclare(SelTag){
  var title = "条件字段编辑";
  var url = "report!editwhere.action";
  var val;
  
  for (var i=0; i<SelTag.options.length; i++){
	  if(SelTag.options[i].selected ){
	  	 val = SelTag.options[i].value;
	  	 url +="?field="+encodeURI(val); 
		 var returnVal = editField(url);  
		 if(returnVal){
	  		 SelTag.options[i].value = returnVal;
			 var fieldInfo = returnVal.split("-");  
	  		 SelTag.options[i].text = fieldInfo[0]+"-"+fieldInfo[1];
	  	}
	     break;
	  }
  }
}




//SQL navigate
function selectFields(){ 
	var url = "${ctx}/select/report.action";
	var obj=selectTableFields(url);
	//判断   
	if(obj)
		document.getElementById("reportSql").value = obj;	//赋值  
}
 

function changeicon(divid)
{
var bdivid = "D"+divid; 
var Idivid = "I"+divid; 
document.getElementById("Dtopm1").style.display = "none";
document.getElementById("Dtopm2").style.display = "none";
document.getElementById("Dtopm3").style.display = "none";
document.getElementById("Itopm1").innerHTML="";
document.getElementById("Itopm2").innerHTML="";
document.getElementById("Itopm3").innerHTML="";
  
document.getElementById(bdivid).style.display = "";
document.getElementById(Idivid).innerHTML="<img src='/images/cms/inside_21.jpg'/>&nbsp;";
}

 function changeModle(type){
 	var id1= "tablestyle" ,id2="theadstyle",id3="tdstyle";
 
	document.getElementById(id1).style.display = "none";
	document.getElementById(id2).style.display = "none";
	document.getElementById(id3).style.display = "none";
	
	if(type.value == "1"){
		document.getElementById(id1).style.display = "";
		document.getElementById(id2).style.display = "";
		document.getElementById(id3).style.display = "";
	} 
}
</script>
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;" onLoad="exchangeInputToSel()">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!-- style="background-color:#dfedef;" -->	
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	<!-- 操作栏 -->
	<tr>
		<td valign="top">
		<table width="100%" align="left"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
					<c:if test="${isEdit==true}"> 
						<li id="button"><a href="javascript:save()">保存</a></li>
					</c:if>
					<li id="button"><a href="javascript:goBack()">返回</a></li>
					<c:if test="${domain.reportid > 0}"> 
						<li id="button1"><a href="javascript:editCode()">编辑源码</a></li>
					</c:if>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<!-- 页面主要内容 -->
	
	<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#dfedef">
	
	<tr>
		<td align="center" valign="top">


		<form method="post" action="report!saveOrUpdate.action" name="myform">
		<ww:hidden name="domain.reportid" id="reportid" />
		<ww:hidden name="domain.choiseField" id="choiseField" />
		<ww:hidden name="domain.selectedField" id="selectedField" />
		<ww:hidden name="domain.isupdated" id="isupdated" />
		<ww:hidden name="domain.whereField" id="whereField" />
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="194" height="100%" valign="top"  bgcolor="#FAFAFA" >
   		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr>
				<td bgcolor="#ffffff" colspan="2">
				<div id="titel1">
				<div id="titel1_left">
				<div id="titel1_right">
				<div id="titel1_txt">步骤</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
			<tr>
				<td id="Itopm1" width="24" align="right"><img src='/images/cms/inside_21.jpg' /></td>
				<td>
					<div id="topm1"  onclick="changeicon('topm1')">基本信息</div>
				</td>
			</tr>
			<tr>
				<td id="Itopm2" width="24" align="right">&nbsp;</td>
				<td >
					<div id="topm2" onclick="changeicon('topm2')">SQL配置</div>  
				</td>
			</tr>
			<tr>
				<td id="Itopm3" width="24" align="right">&nbsp;</td>
				<td >
					<div id="topm3" onclick="changeicon('topm3')">页面设置</div>  
				</td>
			</tr>
		</table>
    </td>
   <td width="85%" valign="top">
   		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" class="view_content_table">
			<tr>  
				<td width="100%" class="view_content_td">
				  <div id="Dtopm1" style="display:block; width:100%;height: 90% "> 
					  <table width="90%" border="0" align="left" cellpadding="0"
						cellspacing="10">
						<tr>
							<td width="23%" align="left" class="inside_list">报表代码：</td>
							<td width="77%" align="left"><ww:textfield
								name="domain.reportCode" cssClass="required" size="50"/></td>
						</tr>
						<tr>
							<td align="left" class="inside_list">报表名称：</td>
							<td align="left"><ww:textfield name="domain.reportName"
								cssClass="required" size="50"/></td>
						</tr> 
						<tr>
							<td width="21%" align="left" valign="top" class="inside_list">备注：</td>
							<td width="79%" align="left"><ww:textarea
								name="domain.remark" cols="40" rows="6" /></td>
						</tr> 
					
					</table> 
				  </div>
				  
				<div id="Dtopm2" style="display:none; width:100%; height: 90%" > 
				<table width="90%" border="0" align="left" cellpadding="0" cellspacing="10">
					<tr>
						<td align="left" class="inside_list">报表查询语句：</td>
						<td align="left" style="float:left;">
							<table   border="0" style="text-align:left;" cellpadding="0"
			cellspacing="1">
							 <tr>
 
	                            <td  ><ww:textarea  name="domain.reportSql" id="reportSql" cssClass="required" cols="40" rows="4" /></td> 
	                            <td valign="bottom">
	                            <input type="button" value="选择" onClick="selectFields()"/>
	                            <br/>
	                            <input type="button" value="确定" onClick="loadFields()"/></td>  
	                          </tr>
			 				</table>
						</td>
					</tr> 
					<tr>
					    <td align="left" class="inside_list" valign="top">报表显示字段： </td>
					    <td align="left">
					    <table width="440" border="0" style="text-align:center;" cellpadding="0"
			cellspacing="1">
                          <tr>
                            <td width="130">可选择字段</td>
                            <td width="30">&nbsp;</td>
                            <td width="135">已选择字段</td>
                            <td width="90">字段操作</td>
                          </tr>
                          <tr>
                            <td rowspan="4" valign="top" align="left"><select name="choice" size="7" multiple="true" style="width:150px;" ondblclick="addalias(this)"> </select></td>
                            <td><input type="button" value=" &gt; " onClick="move_beeline(choice,selected)" /></td>
                            <td rowspan="4" valign="top"><select name="selected" size="7" style="width:140px;" multiple="true" ondblclick="addalias(this)" ></select></td>
                          </tr>
                          <tr>
                            <td><input type="button" value="&gt;&gt;" onClick="move_all(choice,selected)"/></td>
                            <td><input type="button" value="上移" onClick="move_up(selected)"/></td>
                          </tr>
                          <tr>
                            <td><input type="button" value=" &lt; " onClick="move_beeline(selected,choice)"/></td>
                            <td><input type="button" value="下移" onClick="move_down(selected)"/></td>
                          </tr>
                          <tr>
                            <td><input type="button" value="&lt;&lt;" onClick="move_all(selected,choice)"/></td>
                          </tr>
                          
                        </table></td></tr>
                      <tr>
						<td align="left" class="inside_list">报表条件字段：</td>
						<td align="left" style="float:left;">
							 <select name="where" id="where" size="7"  style="width:150px;" ondblclick="adddeclare(this)"> </select>
						</td>
					  </tr> 
                      <tr>
						 <td colspan="2" align="center" class="inside_list"> 注: 双击可选择字段,已选择字段 选项添加别名 </td> 
					  </tr>
				 </table> 
				 </div> 	
				 
				  <div id="Dtopm3" style="display:none; width:100%;height: 90% "> 
					  <table width="90%" border="0" align="left" cellpadding="0"
						cellspacing="10"> 
						 <tr>
							<td width="21%" align="left" valign="top" class="inside_list">头部HTML代码：</td>
							<td width="79%" align="left"><ww:textarea
								name="domain.pageTop" cols="60" rows="10" /></td>
						</tr>
						<tr>
							<td align="left" class="inside_list">列表样式类型：</td>
							<td align="left"><ww:select list="#{'1':'自定义'}" name="domain.reportStyleType" id="reportStyleType" headerKey="0" headerValue="默认" onchange="changeModle(this)"></ww:select> </td>
						</tr> 
					    <tr id="tablestyle" style="display:none;">
							<td align="left" class="inside_list">表格内容：</td>
							<td align="left"><ww:textfield name="domain.tableContent"  size="100" title="内容插入到<table .. >中"/></td>
						</tr> 
						<tr id="theadstyle" style="display:none;">
							<td align="left" class="inside_list">标题内容：</td>
							<td align="left"><ww:textfield name="domain.theadContent"  size="100"  title="内容插入到<td .. >中"/></td>
						</tr> 
						<tr id="tdstyle" style="display:none;">
							<td align="left" class="inside_list">单元格内容：</td>
							<td align="left"><ww:textfield name="domain.tdContent"  size="100"  title="内容插入到<td .. >中"/></td>
						</tr> 
						<tr>
							<td width="21%" align="left" valign="top" class="inside_list">页脚HTML代码：</td>
							<td width="79%" align="left"><ww:textarea
								name="domain.pageFoot" cols="60" rows="10" /></td>
						</tr> 
					</table> 
				  </div> 
				
				</td>
			</tr> 
		</table>
    </td>
</tr>
</table>
		
		
		</form> 
		</td></tr></table>  
		</td>
	</tr>
</table> 
 
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>

</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
</script>
