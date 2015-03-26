<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="${empty domain.id ?'新增':'设置'}问卷" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script src="${ctx}/common/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/common/validation/validation.js" type="text/javascript"></script>
<script src="${ctx}/common/attachment/attachment.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/common/validation/style.css" />
<script src="${ctx}/common/datepicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js" ></script>
<script type="text/javascript" src="/common/core_js/common.js" ></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-w617_ab {
	width:620px;
	height:450px;
}
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:100px;
	display:block;
	float:left;
	text-align:right;
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
	width:150px;
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
	padding-left:30px;
}
.pw-con-input-item_ab .lab-pw-tit_ab2 {
	width:120px;
	display:block;
	float:left;
	text-align:right;
	height:22px;
	line-height:22px;
	color:#333;
}

.pw-con-input-ex_ab .lab-pw-tit_ab2 {
	width:120px;
}
.pw-con-input-ex_ab .lab-pw-tit_ab2 span {
	float:right;
}

</style>
<script type="text/javascript">
function save(){
 if(valid.validate())
  myform.submit();
 else
  dyniframesizeforall("main_frame");
}
function goBack(){
 location.href='/survey/questionnaire!list.action?pageStyle=<ww:property value="pageStyle" />';
}
function showhideframe(title,querystr,wx,wy){
    querystr.title = title;
    return window.showModalDialog(querystr,title,'dialogWidth:'+wx+'px;dialogHeight:'+wy+'px;status:no;scroll:no;help:no;edge:sunken;center:yes;');
}
</script>
<ww:head />
</head>
<body class="pw_ab" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
     <form method="post" action="${ctx}/survey/questionnaire!saveOrUpdate.action" name="myform" id="myform" class="pw-borderOut_ab pw-w617_ab">
	<ww:hidden name="domain.id"/>
	<ww:hidden name="domain.anonymity" value="0"/>
	<ww:hidden name="domain.activated"/>
	<ww:hidden name="domain.createDate"/>
     <div class="pw-borderIn_ab" style="height:438px;">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="/images/cybercms/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="domain.id!=null">编辑问卷</ww:if><ww:else>新建问卷</ww:else></span>
            </div>
            <img src="/images/cybercms/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="overflow-y:auto;height:410px;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab" style="padding-left:0;width:260px;">
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                    	<span><font color="red">*</font>问卷主题： </span>
                    	</span>
                         <ww:textfield name="domain.name" cssStyle="width:90%" cssClass="required max-length-20 "></ww:textfield>        
                    </label>
                </li>
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>开始时间：</span>
                        </span>
                        <ww:textfield name="domain.publishDate" cssClass="Wdate  WdatePickerCss" onfocus="WdatePicker({vel:this,dateFmt:'yyyy-MM-dd HH:mm:00',maxDate:'#F{$dp.$D(\\'domain_cutoffDate\\')}'})"/>
                    </label>
                </li>
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span >欢迎词：</span>
                        </span>
                        <ww:textarea name="domain.welcome" cssStyle="width:90%;height:60px;"/>
                    </label>
                </li>
            </ul>
            <ul class="pw-con-input_ab pw-con-input-ex_ab" style="padding-left:10;width:290px;">
             <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab" style="width:120px;">
                        	<span><font color="red">*</font>所属站点：</span>
                        </span>
                         <ww:action name="site" namespace="/cms" id="siteAction"/>
                  <ww:select name="domain.siteId" list="#attr.siteAction.sites" listKey="oid" listValue="sitename"
                   emptyOption="true" cssClass="select-w150_ab validate-select" onchange="$('domain_siteName').value=this.options[this.selectedIndex].text"/>
                   <ww:hidden name="domain.siteName"/>
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab" style="width:120px;">
                        	<span>结束时间：</span>
                        </span>
                        <ww:textfield name="domain.cutoffDate" cssClass="Wdate  WdatePickerCss" onfocus="WdatePicker({vel:this,dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'#F{$dp.$D(\\'domain_publishDate\\')}'})"/>
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab" style="width:120px;">
                        	<span><font color="red">*</font>多久可再投(分钟)：</span>
                        </span>
                        <ww:textfield name="domain.repetition" cssStyle="width:40px" cssClass="required textField_ab textField-w150_ab" title="0或空则不允许重复再投"></ww:textfield>
					 </label>
                </li>
				<!-- 
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab2" style="width:110px;">
                        	<span>可否匿名答卷：</span>
                        </span>
                        <ww:radio name="domain.anonymity" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" value="\"${domain.anonymity}\"" cssClass=""/>
                    </label>
                </li>
                 -->
            </ul>
        	<ul class="pw-con-input_ab pw-con-input-ex_ab" style="padding-left:0;width:560px;">
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>预览图(214*118)：</span>
                        </span>
						<input type="button" class = "but_img" onclick="uploadPic('domain_pic','','-1','1','214','118','null','jpg,png,gif','2','133');" value="上传图片"/>
						<div id="divdomain_pic" style="padding-left:100px;"></div>
						<ww:hidden name="domain.pic" id="domain_pic"></ww:hidden>
						<script language='javascript'>showPic('domain_pic','214','118','null','jpg,png,gif','2');</script>
					</label>
                </li>
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>提交后显示：</span>
                        </span> 
						<!-- 2:'提示成功投票后关闭', -->
						<ww:radio name="domain.turnToPage" list="#{1:'显示投票统计结果',3:'显示我设置的提示信息'}" value='"${domain.turnToPage}"' onclick="showOrHideThanks(this.value)" />
	              <div id="div_thanks" style="display:${domain.turnToPage eq 3 ? '':'none'};width:90%;">
	              <ww:textarea name="domain.thanks" cssStyle="width:100%;height:60px;"/>
					</label>
                </li>
            </ul>
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
            	    <ww:if test="%{isEdit==true && empty domain.id}">
            			<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save()"/>
            			<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
					</ww:if>
					<ww:elseif test="%{not empty domain.id}">
					   <input type="button" class="pwSubmit_ab" value="" onclick="javascript:saveAjax()"/>
            		   <input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
					</ww:elseif>
            </div>
        <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>
	   try{
		   window.parent.frames['main_frame'].refreshIframe();
		   global_ab.fn.popWindow.removePopWindow(false);
	   }catch(e){}</script>
  </c:forEach> 
</c:if>
        </div>
    </div>
</form>

<script type="text/javascript">
//<![CDATA[
global_ab.init.popwindowPage_fn();
(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();
//]]>
</script>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
function showOrHideThanks(val){
	if(val==3){
		if(Element.visible($('div_thanks')))return;
		$('div_thanks').show();
	}else{$('div_thanks').hide()}
	dyniframesizeforall("main_frame");
	setParentFrameSize();
}

function saveAjax(){
	if(!valid.validate()){
		dyniframesizeforall("main_frame");
		return;
	}
	var param = Form.serialize('myform');
	new Ajax.Request('${ctx}/survey/questionnaire!saveArgs.action', { method:'post', parameters: param,
		onSuccess: function(transport){
			alert("操作成功");
			global_ab.fn.popWindow.removePopWindow(false);
		}
	});
}
</script>
