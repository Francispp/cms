<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="邮件发送" />
<html>
	<head>
	<title>${title}</title>
		<%@ include file="/common/meta.inc"%>
		<link href="${default_style}" rel="stylesheet" type="text/css">	
		<%@ include file="/common/js.inc"%>
		<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
		<script src="${ctx}/common/cms_js/cms.js" type="text/javascript"></script>
		<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
		<%@ include file="/common/validation.inc"%>
		<script type="text/javascript" src="../../dwr/interface/userManagerService.js"></script>
	</head>
	<body>
		<ww:form action="email!send.action" method="post" id="frm" name="frm">
			<table cellpadding="3" cellspacing="1" width="100%">
				<tr>
					<td nowrap="nowrap">收件人：</td>
					<td>
						<ww:hidden name="to" id="toField"/>
						<input id="toInput" type="text" readonly="readonly" style="width:200px">
						<input type="button" name="button"
							onclick="selectUser(document.getElementById('toField'),document.getElementById('toInput'))"
							value="选择用户"  cssClass="max-length-40"/>
					</td>
				</tr>
				<tr>
					<td>抄送：</td>
					<td>
						<ww:hidden name="cc" id="ccField"/>
						<input type="text" id="ccInput" readonly="readonly" style="width:200px"/>
						<input type="button" name="button"
							onclick="selectUser(document.getElementById('ccField'),document.getElementById('ccInput'))"
							value="选择用户"  cssClass="max-length-40"/>
					</td>
				</tr>
				<tr>
					<td>主题：</td>
					<td>
						<ww:textfield name="subject"  cssClass="required max-length-100" id="subject" cssStyle="width:300px"/>
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<ww:textarea name="body" cssStyle="width:300px" rows="8" cssClass="required max-length-1000" id="body"/>
					</td>
				</tr>
				<tr>
								<td>附带文档地址：</td>
								<td><input type="checkbox" id="address" name="address" checked="true"/></td>
							</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" id="submitButton" value="发送"/>
					</td>
				</tr>
			</table>
		</ww:form>
		<script type="text/javascript">
		$("submitButton").onclick = function ()
			{
				if ($("toField").value.length == 0 && $("ccField").value.length == 0)
				{
					alert ("请选择收件人或抄送");
					return;
				}
				
				if (new Validation('frm',{immediate:true}).validate ())
				{
					sendEmail();
				}
			}
		function sendEmail()
		{
		var to = document.all.toField.value;
		var cc = document.all.ccField.value;
		var subject= document.all.subject.value;
		var body = document.all.body.value;
		var address = "false";
		if(document.all.address.checked == true)
		{
		address = "true";
		}
		if (to.length == 0 && cc.length == 0)
				{
					alert ("请选择收件人或抄送");
					return;
				}
		var returnValue = new Ajax.Request('${ctx}/cms/email!send.action',{method: 'get', parameters: 'to='+to+'&cc='+cc+'&documentId=${documentId}&subject='+subject+'&body='+body+'&address='+address, onComplete: showResponse});
		}
		/**
			$("toSearchButton").onclick = function ()
			{
				var result = selectUser(document.getElementById('toField'),document.getElementById('toInput'));
				if (result)
				{
					result = result.toString ();
					if (result.length > 0)
					{
						$A(result.split (",")).each (function (item)
						{
							item = item.toString ().split (";");
							var exists = false;
							for (var index = 0; index < $("toList").options.length; index++)
							{
								if ($("toList").options[index].value == item[0])
								{
									exists = true;
									break;
								}
							}
							
							if (!exists)
							{
								var option = document.createElement ("OPTION");
								
								$("toList").appendChild (option);
								
								userManagerService.getById (item[0], function (user)
								{
									if (user.email)
									{
										option.value = user.userid;
									}
									else
									{
										option.value = "";
									}
								}); 
								
								option.innerText = item[1];	
							}
						});
					}
				}
				
				$("toField").value = $A($("toList").options).map (function (option)
				{
					return option.value;
				}).join (",");
			}
			
			$("toList").onchange = function ()
			{
				$("toInput").value = $("toList").value;
			};
			
			$("ccList").onchange = function ()
			{
				$("ccInput").value = $("ccList").value;
			};
			
			$("ccSearchButton").onclick = function ()
			{
				var result = selectUser(document.getElementById('ccField'),document.getElementById('ccInput'));
				if (result)
				{
					result = result.toString ();
					if (result.length > 0)
					{
						$A(result.split (",")).each (function (item)
						{
							item = item.toString ().split (";");
							var exists = false;
							for (var index = 0; index < $("ccList").options.length; index++)
							{
								if ($("ccList").options[index].value == item[0])
								{
									exists = true;
									break;
								}
							}
							
							if (!exists)
							{
								var option = document.createElement ("OPTION");
								
								$("ccList").appendChild (option);
								
								userManagerService.getById (item[0], function (user)
								{
									if (user.email)
									{
										option.value = user.userid;
									}
									else
									{
										option.value = "";
									}
								}); 
								option.innerText = item[1];	
							}
						});
					}
				}
				
				$("ccField").value = $A($("ccList").options).map (function (option)
				{
					return option.value;
				}).join (",");
			}
			
			$("toRemoveButton").onclick = function ()
			{
				$A($("toList").options).each (function (option)
				{	
					if (option.selected)
					{
						$("toList").removeChild (option);
					}
				});
			};
			
			$("ccRemoveButton").onclick = function ()
			{
				$A($("ccList").options).each (function (option)
				{
					if (option.selected)
					{
						$("ccList").removeChild (option);
					}
				});
			};
			
			$("submitButton").onclick = function ()
			{
				if ($("toList").options.length == 0 && $("ccList").options.length == 0)
				{
					alert ("请选择收件人或抄送");
					return;
				}
				
				if (new Validation('frm',{immediate:true}).validate ())
				{
					frm.submit();
				}
			}
		**/
		</script>
	</body>
</html>
