<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="静态资源基本属性" />

<html> 
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/validation.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-w417_ab {
	width:460px;
	height:480px;
	
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
<script type="text/javascript">
function save(){
if(valid.validate()){
	if(!checkFileType()){
		return ;
	}
	myform.submit();
}
 
}
function checkFileType(){
	var fpath=document.getElementById('_fileType').value;
	if(fpath==null||fpath==''){
		return true;
	}
	var uploadNameIndex=fpath.lastIndexOf("\\");
	var uploadName=fpath;
	var findex = fpath.lastIndexOf(".");
	var ftype='';
	  if(findex == -1){
	  	alert("无效文件");
	  	return false;
	 }else{
		  fpath = fpath.substring(findex,fpath.length).toLowerCase();
		  fpath = fpath.toLowerCase();
		  if(fpath=='.exe'||fpath=='.bat'){
			  alert("不能上传exe或者bat文件");
			  return false;
			  
		  }else{
			  uploadName=uploadName.substring(uploadNameIndex+1,uploadName.length).toLowerCase();
			 var name = document.getElementById('domain.name').value.toLowerCase();
			 if(uploadName!=name){
				alert('上传文件的名称应与资源名称一致');
				return false;
			 }
		  } 
	 }
	  

	
  return true;
	
}
function download()
{
location.href='staticResource!fileDownload.action?id=${domain.id}&siteid=${domain.siteid}&pageStyle=<ww:property value="pageStyle" />';
}
function goBack(){
 location.href='staticResource.action?siteid=${domain.siteid}&pageStyle=<ww:property value="pageStyle" />';
}
function doEditFile(){
	 location.href='staticResource!editFile.action?id=${domain.id}&siteid=${domain.siteid}&pageStyle=<ww:property value="pageStyle" />';
}
</script>
<ww:head/>
</head>
<body class="pw_ab" style="overflow:hidden;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<form method="post" class="pw-borderOut_ab pw-w417_ab" action="staticResource!saveOrUpdate.action" name="myform" enctype="multipart/form-data">
<ww:hidden  name="pageStyle" id="pageStyle"/>
<ww:hidden  name="domain.id" id="domain.id"/>
<ww:hidden  name="domain.siteid" id="domain.siteid"/>
<ww:hidden  name="domain.uploadman" id="domain.uploadman"/>
<ww:hidden  name="domain.uploadtime" id="domain.uploadtime"/>
<ww:hidden  name="domain.serverpath" id="domain.serverpath"/>
<ww:hidden  name="domain.type" id="domain.type"/>
<ww:hidden  name="domain.name" id="domain.name"/>
<div class="pw-borderIn_ab" style="">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>静态资源</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:390px;overflow-y:auto;">
        	<ul class="pw-con-input_ab">
            	<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">资源名称：</span>
                    	<ww:textfield value="%{domain.name}" cssClass="textField_ab textField-w191_ab required max-length-80" disabled="true"></ww:textfield>
                    	
                    </label>
                </li>
                <!-- 
            	 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	 <img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">静态类型：</span>
                    	 <ww:select name="domain.type" id="domain.type" list="trueOfFalseMap1" cssStyle="width:188px;" />
                    </label>
                </li>
                 -->
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" />
                    	<span class="lab-pw-tit_ab">重新上传：</span>
                    	 <ww:file name="_file" id="_fileType" accept="text/*" size="40" cssClass="textField_ab textField-w191_ab"/>
                    	
                    </label>
                    <div class="fn-clear"></div>
                </li>
				
            </ul>
            <table width="80%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
         
          <td width="100%" align="center">
        
      		<ww:if test="%{domain.type=='document'}">
         	 <img width="200" height="200" src="${default_imagepath}/static_resource_document.jpg" />
         	</ww:if>
         	<ww:if test="%{domain.type!='document'}">
         	<img width="200" height="200" src="${ctx}<ww:property value="domain.serverpath" />">
         	</ww:if>
         
         
          </td>
        </tr>
      </table>
            <div class="pw-con-btns_ab">
            	<input type="button" class="pwSubmit_ab" value="" onclick="save();"/>
                <input type="button" class="pwCancel_ab" value="" onclick="global_ab.fn.popWindow.removePopWindow(false);" />
            </div>
        </div>
    </div>
 <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>global_ab.fn.popWindow.removePopWindow(false);</script>
  </c:forEach> 
</c:if>
    </form>
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
</script>
<script type="text/javascript">

global_ab.init.popwindowPage_fn();
(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();

</script>
