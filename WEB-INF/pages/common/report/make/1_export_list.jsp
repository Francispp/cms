
				
			<%@ page contentType="text/html; charset=UTF-8"%>
				<%@ include file="/common/taglibs.inc"%> 
				<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
				<html xmlns="http://www.w3.org/1999/xhtml">
				<head>
				<title>${domain.reportName}</title>
				<%@ include file="/common/meta.inc"%>
				<link href="${default_style}" rel="stylesheet" type="text/css">
				<script src="${ctx}/common/calendar2.js" type="text/javascript"></script>
				<%@ include file="/common/js.inc"%>
				<%@ include file="/common/ec/ec.inc"%> 
				<%@ include file="/common/ext/ext-res.inc"%>
				<script type="text/javascript">	  
				 function query(){ 
				 	var url = window.location.href; 
				 	if(url.indexOf("?") == -1)
				 		url += "?issearch=1";
				 	else
				 		url += "&issearch=1"; 
				 	myform.action = url;
				 	myform.submit();
				 }
				  function checkNumber(){
				 	if(event.keyCode < 46 || event.keyCode>57 || event.keyCode == 47){
				 		return false;
				 	}
				 }
				</script>
				</head>
				<body topmargin="0" leftmargin="0">
				<!-- 状态提示栏 --> 
				<%@ include file="/common/messages.inc"%>
				<div id="mainDiv">  
				
			
				
				<!-- 页面标题 --> 
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
					<tr>
						<td bgcolor="#ffffff" height="6"></td>
					</tr>
					<tr>
						<td bgcolor="#ffffff">
						<div id="titel1">
						<div id="titel1_left">
						<div id="titel1_right">
						<div id="titel1_txt">${domain.reportName}</div>
						</div>
						</div>
						</div>
						</td>
					</tr>
				</table> 
				
			
			
					<!-- 操作栏 --> 
					<div id="searchDiv"> 
					<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
					<tr>
						<td valign="top"> 
						<form method="post" action="report!exportlist.action" name="myform">  
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<tr>
								<td height="1"></td>
							</tr>
							<tr>
								<td>
								<div id="querynav">
								<ul>                          			
					
			<li><table width='190'><tr><td style='width:70px; text-align:left;'>部门名称:</td><td><input name="deptname" class='input' value='${deptname}' onkeypress="javaScript: if(event.keyCode=='13') query(); " /></td></tr></table></li>
				
									<li class="btncolse"><a href="javascript: window.close();">关闭</a></li>
									<li class="btnquery"><a href="javascript:query();">查询</a></li>
								</ul> 
								</div>  
								</td>
							</tr> 
						</table> 
						</form>
						</td>
					</tr> 
				</table>
				</div>
				
			
				
				<!-- 页面主要内容 -->  
				<div style="width: 99%" id="ectableDiv">
					<ec:table items="items" var="item" action="${ctx}/base/report!exportlist.action" 
					batchUpdate="false" xlsFileName="${domain.reportName}.xls" 
				    minColWidth="80" generateScript="true"
					classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
					showPrint="true" resizeColWidth="true" filterable="true"
					filterRowsCallback="limit" sortRowsCallback="limit"
					retrieveRowsCallback="limit" useAjax="false">
					<ec:row   rowId="rowid_${GLOBALROWCOUNT}">   
					<ec:column property="_1" title="序号" width="8%" value="${GLOBALROWCOUNT}" style="text-align:center"/>
				
			<ec:column property="username" title="用户名" editable="false" value="${item.username}" sortable="false" width="20%"/>
<ec:column property="loginid" title="登录编号" editable="false" value="${item.loginid}" sortable="false" width="20%"/>
<ec:column property="empcode" title="员工代码" editable="false" value="${item.empcode}" sortable="false" width="20%"/>
<ec:column property="deptcode" title="部门代码" editable="false" value="${item.deptcode}" sortable="false" width="20%"/>
<ec:column property="deptname" title="部门名称" editable="false" value="${item.deptname}" sortable="false" width="20%"/>
<ec:column property="mobilephone" title="手机号" editable="false" value="${item.mobilephone}" sortable="false" width="*%"/>

				
				    </ec:row>
					</ec:table> 
				</div>
					
			
				 
				</div>
				<!-- 页脚 -->
				<%@ include file="/common/foot.inc"%>
				</body>
				</html>	
				
			