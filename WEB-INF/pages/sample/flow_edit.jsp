<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<c:set var="title" value="Sample flow 编辑" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script src="${ctx}/common/cms_js/cms.js" type="text/javascript"></script>	
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<ww:head />
</head>
<script type="text/javascript">
var contextPath='${ctx}';
function save(){
 if(valid.validate())
     myform.submit();
}
function accept(){
 myform.action="flow!accept.action";
 setElementsDisabled(false);
 myform.submit();
}
function comple(){
  myform.action="flow!comple.action";
 myform.submit();
}
function goBack(){
 location.href='flow.action?flowstate=${flowstate}';
}
//指定当前地址的基地方（不包括Action的方法名和参数）
//指定流程处理的地址（包括需要的参数）
var FLOW_TRANSCAT_URL='${ctx}/flow/common!transact.action';
function transact(){
  var sURL=FLOW_TRANSCAT_URL+"?flowstate=${flowstate}";
 sURL = addParam(sURL, 'id', '${id}');
 sURL = addParam(sURL, 'activityid', '${activityid}');
 //alert(sURL);
  var title="流程处理";
 	 var result=window.showModalDialog(sURL,'','font-size:9pt;dialogWidth:500px;dialogHeight:400px;status:no;scroll=no;');
 if(result!=null && result!='' &&result!='undefined'){
  var confirmUrl=myform.action;
  
  var rts=result.split('!');
  if(rts!=null && rts.length>0){
    //method
    confirmUrl=replaceAll(myform.action,'!saveOrUpdate','!'+rts[0]);
    confirmUrl=addParam(confirmUrl, 'method', rts[0]);
    if(rts.length>1 && rts[1]!='')
     confirmUrl=addParam(confirmUrl, 'NodeSelectUserIds', rts[1]);
  }
  myform.action = confirmUrl;
  alert(confirmUrl);
  myform.submit();
 }
 //myform.action = sURL;
 //myform.submit();
}
</script>
<body>
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}"/> ${activityname}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>  
<!-- 操作栏 -->
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
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
  <c:if test="${!permObj.isAccept && permObj.isEdit}">
  <li id="button"><a href="javascript:accept()">接收</a></li>	
  </c:if> 
  <c:if test="${permObj.isEdit && permObj.isAccept}"> 
  <li id="button"><a href="javascript:save()">保存</a></li>
  </c:if>
  <c:if test="${permObj.isEdit && permObj.isAccept}"> 
  <li id="button"><a href="javascript:comple()">完成</a></li>
  <li id="button1"><a href="javascript:transact()">流程处理</a></li>  
  </c:if>
  <ww:if test="pageStyle!=0">
  <li id="button"><a href="javascript:goBack()">返回</a></li>
  </ww:if>
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
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td align="center" valign="top">
<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
<form method="post" action="flow!saveOrUpdate.action" name="myform">

<!--ww:hidden name="domain.oid" id="oid" /-->
<ww:hidden name="id" id="id" />
<ww:hidden name="activityid" id="activityid" />
<ww:hidden name="flowstate" id="flowstate" />
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="23%" align="left">编号：</td>
          <td width="77%" align="left">
            <ww:textfield name="domain.oid" readonly="true" />
           </td>
          </tr>
        <tr>
          <td align="left">名称：</td>
          <td align="left">
          <ww:textfield name="domain.name"  cssClass="max-length-24"/>
          </td>
          </tr>
        <tr>
          <td align="left">测试字段：</td>
          <td align="left">
          <ww:textfield name="domain.test" id="pdeptname"  cssClass="max-length-40"/>          
          </td>
          </tr>
        <tr>
          <td align="left">测试字段2：</td>
          <td align="left">
          <ww:textfield name="domain.test1"   cssClass="max-length-40"/>
          </td>
          </tr>
        <tr>
          <td align="left">测试字段3：</td>
          <td align="left">
          <ww:textfield name="domain.test2"   cssClass="max-length-40"/>
          </td>
          </tr> 
        <tr>
          <td align="left">测试字段4：</td>
          <td align="left">
          <ww:textfield name="domain.test3"  cssClass="max-length-40" />
          </td>
          </tr>                                                  
      </table></td>
    </tr>
    <tr>
      <td align="center" class="view_content_td1">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="21%" align="left" valign="top">备注：</td>
          <td width="79%" align="left">
          <ww:textarea name="domain.remark" cols="40" rows="6"  cssClass="max-length-120"/>
          </td>
        </tr>
      </table>
      </td>
    </tr>
    </table>
<iframe src='${ctx}/flow/track.action?id=<ww:property value="domain.oid"/>'
	frameborder=0 width=100% height=100> </iframe>
	</form>
</div>
</td></tr></table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
<c:if test="${!(permObj.isEdit && permObj.isAccept)}">
 setElementsDisabled(true);
 </c:if>
</script>