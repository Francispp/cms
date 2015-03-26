<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="问卷调查" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/dwr/interface/userManagerService.js"
	type="text/javascript"></script>
<script type="text/javascript">
var valid = null;
function save(){
	if(valid==null)
		valid = new Validation('myform',{immediate:true});
	if(valid.validate())
		myform.submit();
	else
		dyniframesizeforall("main_frame");
}
function goBack(){
	location.href='/survey/question!list.action?pageStyle=<ww:property value="pageStyle" />';
}
</script>
<ww:head />
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->

<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--style="background-color:#dfedef;"  --> 
	<tr><td>
	<table  width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">
	
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
	</table>
	</td>
	</tr>


	<!-- 操作栏 -->

	<tr>
		<td valign="top"  height="100%">
		<table  width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">
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
		<form method="post" action="${ctx }/survey/question!saveOrUpdate.action" name="myform">
			<ww:hidden name="domain.id" id="id" />
			<ww:hidden name="pageStyle" id="pageStyle" />
			<ww:hidden name="questionnaire.id" value="%{param.questionnaireId }" />
      
      <table style="WIDTH: 99%" class="FormArea" border="0" class="view_content_table" cellspacing="0" cellpadding="5">
          <tbody>
             <tr>
              <td width="30%" class="FormHdr" style="PADDING-LEFT: 60px; PADDING-TOP: 5px">问题类型${param.questionnaireId }asf</td>
              <td width="" style="text-align:left;">
              <ww:select list="#{0:'短答',1:'单选',2:'多选',3:'单选短答',4:'多选短答',5:'自由长答'}"
                        onchange="var type = this.options[this.selectedIndex].value; var e1 = this.form.elements['validationRegex']; var e2 = this.form.elements['regexFromList']; var disabled = (type == 1 || type == 2); e1.disabled = disabled; e2.disabled = disabled; tr_option.style.display=(type != 0 && type != 5)?'':'none';"
                        name="domain.type"
              ></ww:select>
              </td>
            </tr>
            <tr>
              <td width="30%" class="FormHdr" style="PADDING-LEFT: 60px; PADDING-TOP: 5px" valign="top"> 问题标题</td>
              <td width="" style="text-align:left;"><span title="REQUIRED: Enter Survey Name  ( character max)">
                <ww:textarea name="domain.content" cssStyle="width:70%;" cssClass="required max-length-100"></ww:textarea>
              </span></td>
            </tr>

            <tr id="tr_option" style="display:${(empty domain.type || domain.type eq '0' || domain.type eq '5')?'none':'' }">
              <td class="FormHdr" style="PADDING-LEFT: 60px; PADDING-TOP: 5px;" valign=top>选项</td>
              <td style="text-align:left;padding-left:30px;">
                <ul id="ul_optinos" style="list-style-type:upper-alpha;">
                  <ww:iterator value="domain.selectOptions" status="sta">
                      <li><label for=""> </label><input type="text" name="selectOptions" onclick="addOpt(this)" value="<ww:property value='content'/>" class="required" style="width:55%;"/> <a href="javascript:delOpt(this);">删除</a></li>
                  </ww:iterator>
                  <li><label for="radiobutton"> </label><input type="text" name="selectOptions" onclick="addOpt(this)" style="width:55%;"/> <a href="javascript:delOpt(this);">删除</a></li>
                </ul>
              </td>
            </tr>
            <tr>
              <td width="30%" class="FormHdr" style="PADDING-LEFT: 60px; PADDING-TOP: 5px" valign=top>文本输入</td>
              <td width="70%" style="text-align:left;">
              校验规则表达式
                <SELECT onchange="var index = this.selectedIndex; var regex = (index == 0) ? '' : this.options[index].value; var e = this.form.elements['validationRegex']; e.value = regex;" name="regexFromList">
                  <OPTION value="-1">&nbsp;</OPTION> 
                  <OPTION value="^(https?:\/\/)?([a-zA-Z0-9\.\-_]+)(:(\d+))?(\/[^?]*)?(\?(.*))?$">HTTP URL</OPTION> 
                  <OPTION value="^\d+$">整数</OPTION> <OPTION value="^(\d+)\.(\d+)$">浮点数</OPTION> 
                  <OPTION value='^(([^\s\(\)<>@,;:\\\"\.\[\]]+|("[^"]*"))(\.([^\s\(\)<>@,;:\\\"\.\[\]]+|("[^"]*")))*)@(((\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})|[^\s\(\)<>@,;:\\\"\.\[\]]+(\.[^\s\(\)<>@,;:\\\"\.\[\]]+)*))$'>E-mail地址</OPTION>
                </SELECT> 
                <INPUT class="text" maxLength="252" name="validationRegex" value="" style="width:35%;"/>
                
                </td>
            </tr>
            <!--update button-->
          </tbody>
          </table>
		</form>
		</td>
	</tr>		
	
	</table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
window.document.onkeypress=function(){
	var event = arguments[0]||window.event;
    var currentKey = event.charCode||event.keyCode;
    if(currentKey==27){
    	try{
     	    global_ab.fn.popWindow.removePopWindow(false);
		}catch(e){}
    }
};
function addOpt(input){
  var li = input.parentNode;
  var optionStr = '<li><label for="">'+'</label><input type="text" name="selectOptions" onclick="addOpt(this)" class="" style="width:55%;"/><a href="javascript:delOpt(this);">删除</a></li>';
  var isadd = true;
  var inputs = $A($('ul_optinos').getElementsByTagName('input'));
  inputs.each(function(node, index){
      if(inputs.length-1 > index && node.value == ''){isadd = false;node.className="validation-failed"}
  });
  if(isadd && li.nextSibling==null){
      inputs[inputs.length-1].className = 'required';
      $('ul_optinos').insertAdjacentHTML('beforeEnd', optionStr);
  }
  //alert(li.previousSibling);
}
function delOpt(org){
  var li = $(org).parentNode;
  alert(li.tagName);
  $('ul_optinos').removeChild(li);
}
</script>
