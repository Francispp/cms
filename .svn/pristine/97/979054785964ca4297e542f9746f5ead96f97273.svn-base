<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="资源管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
function save(){
if(valid.validate())
  myform.submit();
}
function goBack(){
 location.href='resource.action?pageStyle=<ww:property value="pageStyle" />';
}
function chageType(){
	var typeValue=myform.resouretype.value;	
	switch(typeValue){
		case '0':			
			document.all.ifprotectedTR.style.display="none";
			document.all.protectedurlTR.style.display="none";
			break;
		case '1':			
			document.all.ifprotectedTR.style.display="";
			document.all.protectedurlTR.style.display="";
			break;
		default:
			break;		
	}
}
</script>
<style type="text/css">
.pw-w617_ab {
	width:600px;
	height:327px;
	
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

</style>
<ww:head/>
</head>
<body class="pw_ab"  style="overflow:hidden;padding:0px;margin:0px;border:0px;">

<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form method="post" action="resource!saveOrUpdate.action" name="myform" class="pw-borderOut_ab pw-w617_ab">
<ww:hidden  name="domain.coreModule.oid" id="coreModule.oid"/>
		
	<div class="pw-borderIn_ab" style="height:300px;">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="/images/cybercms/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="domain.resourceid!=null">编辑资源</ww:if><ww:else>新建资源</ww:else></span>
            </div>
            <img src="/images/cybercms/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:200px;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>资源编号：</span>
                        </span>
                          <ww:textfield name="domain.resourceid" readonly="true" cssClass="textField_ab textField-w150_ab"/>
                    </label>
                </li>
              <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>资源名称：</span>
                        </span>
                         <ww:textfield name="domain.resourcename"  cssClass="textField_ab textField-w150_ab required max-length-24"/>
                      
                    </label> 
                </li>
                
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>类型：</span>
                        </span>
                         <ww:select name="domain.resourcetype" id="resourcetype" list="trueOfFalseMap1" cssClass="required select_ab select-w50_ab"/>
                      
                    </label>
                </li>
               
             

               <li class="pw-con-input-item_ab"  >
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>备注：</span>
                        </span>
                         <ww:textarea name="domain.remark" cssStyle="height:50px;" rows="5" cssClass="textarea-hb select-w150_ab max-length-120"/>
                     </label>
                </li>
            
               
                
               
            </ul>
              <ul class="pw-con-input_ab pw-con-input-ex_ab" style="height:200px;">
            
              <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span >权限代码：</span>
                        </span>
                          <ww:textfield name="domain.resourcekey"  cssClass="required textField_ab textField-w150_ab max-length-40"/>
                    </label>
                </li> 

                
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span >排序号：</span>
                        </span>
                           <ww:textfield name="domain.orderno" cssClass="validate-digits textField_ab textField-w150_ab" />
                    </label>
                </li> 
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>动作：</span>
                        </span>
                          <ww:textfield name="domain.resourcestring"   cssClass="textField_ab textField-w150_ab required max-length-120"/>
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
</form>
<c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>
	   parent.parent.parent.frames['main_frame'].refreshMenu();
	   global_ab.fn.popWindow.removePopWindow(false);
	   </script>
  </c:forEach> 
</c:if>
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
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
</script>