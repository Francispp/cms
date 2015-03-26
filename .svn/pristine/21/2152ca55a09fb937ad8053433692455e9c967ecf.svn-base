<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="${empty param.copy ?'编辑':'复制'}问卷问题" />
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
<%@ include file="/common/validation.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-w617_ab {
	width:600px;
	height:400px;
	
}
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:100px;
	display:block;
	text-align:right;
	height:22px;
	line-height:22px;
	color:#333;
}



.pw-con-input-item_ab.lab-pw_ab{
	width:450px;
	display:block;
	
	text-align:left;
	height:22px;
	line-height:22px;
	color:#333;
}
.textarea-hb{
	border:1px #999 solid;
	width:90%;
	margin:7px 0;
}
.select-w150_ab {
	width:150px;
	_width:150px;
}
.textField-w70_ab{
width:90px;
}
.textField-w30_ab{
width:60px;
}
.textField-w150_ab {
	width:120px;
}
.select-w100_ab {
	width:80px;
	height:100px;
}
.pw-con-input-item-plu_ab {
	border:1px solid #999;
	width:200px;
	height:290px;
	#height:290px;
}
.pw-con-input-rx_ab {
	width:200px;
	padding-left:100px;
}

</style>
<script type="text/javascript">
var valid = null;
function save(){
	if(valid==null)
		valid = new Validation('myform',{immediate:true});
	if(valid.validate()){
		myform.submit();
	}
	else{
		dyniframesizeforall("main_frame");
	}
}
function goBack(){
 location.href='/survey/question!list.action?pageStyle=<ww:property value="pageStyle" />';
}
function changeQuestionType(obj){
	var type = obj.options[obj.selectedIndex].value;
	var e1 = obj.form.elements['validationRegex'];
	var e2 = obj.form.elements['regexFromList']; 
	var disabled = (type == 1 || type == 2);
	e1.disabled = disabled;
	e2.disabled = disabled;
	tr_option.style.display=(type != 0 && type != 5)?'':'none';
}
</script>
<ww:head />
</head>
<body class="pw_ab" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->

<form method="post" action="${ctx }/survey/question!addQuestion.action" name="myform" id="myform" class="pw-borderOut_ab pw-w617_ab">
			<ww:hidden name="domain.id"/>
			<ww:hidden name="pageStyle" id="pageStyle" />
			<ww:hidden name="domain.questionnaireId"/>
	<div class="pw-borderIn_ab" style="height:418px;">
	<div class="pw-head_ab">
		<div class="pw-head-tit_ab">
			<img src="/images/cybercms/ico_013_plus.gif" class="ico_ab ico-013_ab" />
			<span><ww:if test="{domain.id!=null">编辑问卷问题</ww:if><ww:else>新建问卷问题</ww:else></span>
		</div>
		<img src="/images/cybercms/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
	</div>
	<div class="pw-con_ab" style="overflow-y:auto;height:380px;">
		<ul class="pw-con-input_ab pw-con-input-ex_ab">
            
			<li class="pw-con-input-item_ab">
				<label class="lab-pw_ab">
					<span class="lab-pw-tit_ab">
						<span>问题类型：</span>
					</span>
					  <ww:select list="#{0:'短答',1:'单选',2:'多选',3:'单选短答',4:'多选短答',5:'自由长答'}"
                        onchange="changeQuestionType(this);"
                        name="domain.type"
                        value="${domain.type}">
                      </ww:select>
			    </label>
			</li>
       		<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>问题标题：</span>
              		</span>
              		<div style="padding-left:105px;">
              		  <ww:textarea name="domain.content"  cssClass="required max-length-100 " cols="50" rows="3"></ww:textarea>
              		  </div>
				</label> 
			</li>
			
			<li class="pw-con-input-item_ab" style="display:${(empty domain.type || domain.type eq '0' || domain.type eq '5')?'none':'' }" id="tr_option">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>选项设置：</span>
              		</span>
              		(每行回车表示一个选项)<br>
              		<div style="padding-left:105px;">
              	<div id="wwgrp_selectOptions" class="wwgrp" >
			    <div id="wwctrl_selectOptions" class="wwctrl">
                  <textarea name="selectOptions" id="selectOptions" rows="9" cols="50" class="required"><ww:iterator value="domain.selectOptions" status="sta"><ww:property value='content'/>&#10;</ww:iterator></textarea>
				</div> 
				</div>
				</div>
				</label> 
			</li>
       		<li class="pw-con-input-item_ab" id="orderquestion">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>其它答案的校验规则：</span>
              		</span>  
                <SELECT onchange="var index = this.selectedIndex; var regex = (index == 0) ? '' : this.options[index].value; var e = this.form.elements['domain.validationRegex']; e.value = regex;" name="regexFromList">
                  <OPTION value="0">普通文本</OPTION>
				  <OPTION value="^\d+$">整数</OPTION><OPTION value="^(\d+)\.(\d+)$">浮点数</OPTION> 
                  <OPTION value="^(https?:\/\/)?([a-zA-Z0-9\.\-_]+)(:(\d+))?(\/[^?]*)?(\?(.*))?$">网址</OPTION> 
                  <OPTION value='^(([^\s\(\)<>@,;:\\\"\.\[\]]+|("[^"]*"))(\.([^\s\(\)<>@,;:\\\"\.\[\]]+|("[^"]*")))*)@(((\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})|[^\s\(\)<>@,;:\\\"\.\[\]]+(\.[^\s\(\)<>@,;:\\\"\.\[\]]+)*))$'>E-mail地址</OPTION>
				  <OPTION value="">其它正则表达式</OPTION>
                </SELECT>
                <INPUT class="text" maxLength="252" name="domain.validationRegex" id="validationRegex" value="" />
				</label> 
			</li>
			
			<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>是否必答：</span>
              		</span>
              		  <ww:radio name="domain.required" list="#{1:'必答',0:'非必答'}" value="\"${domain.required}\""/>
				</label> 
			</li>
			<!-- 
			<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>选项排列：</span>
              		</span>
              		  <ww:radio name="domain.optDirect" list="#{0:' 横向排列 ',1:'纵向排列'}" value="\"${domain.optDirect}\""/>
				</label> 
			</li>
			 -->
			<li class="pw-con-input-item_ab">
           		<label class="lab-pw_ab">
          			<span class="lab-pw-tit_ab">
              			<span>序号：</span>
              		</span>
              		 <ww:textfield name="domain.odr"/>
				</label> 
			</li>
		</ul>
		<div class="fn-clear"></div>
		<div class="pw-con-btns_ab">
			<c:if test="${isEdit==true}">
				<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save()"/>
			</c:if>
			<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
		</div>
	</div>
</div>
  <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>try{window.parent.frames['main_frame'].refreshIframe();}catch(e){window.close();}try{global_ab.fn.popWindow.removePopWindow(false);}catch(e){}</script>
  </c:forEach> 
</c:if>
</form>
<script type="text/javascript">
	//<![CDATA[
	global_ab.init.popwindowPage_fn();
	(function()
	{
		var closeBtn = document.getElementById("pwClose_ab");
		closeBtn.onclick = function()
		{
			try{
				window.parent.frames['main_frame'].refreshIframe();
			}catch(e){
				window.close();
			}
			try{
				global_ab.fn.popWindow.removePopWindow(false);
			}catch(e){}
			
		}
	})();
	//]]>
</script>

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
  var optionStr = '<li><label for="">'+'</label><input type="text" name="selectOptions" onclick="addOpt(this)" class=""/></li>';
  var isadd = true;
  var inputs = $A($('ul_optinos').getElementsByTagName('input'));
  inputs.each(function(node, index){
      if(inputs.length-1 > index && node.value == ''){isadd = false;node.className="validation-failed"}
  });
  if(isadd && li.nextSibling==null){
      inputs[inputs.length-1].className = 'required';
      $('ul_optinos').insertAdjacentHTML('beforeEnd', optionStr);
  }
}
</script>
