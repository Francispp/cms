<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<%@ include file="/common/js.inc"%>
<html>
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
//检测文件类型如果前台调用时有设置参数则取设置的参数、否则跟后台一样取预设参数
function checkFileType(){
	var fileType=new Array();
	var ftype='${ftype}';
	if(ftype==null||ftype==''){
		fileType[0]='.flv';
	}else{
		if(ftype.indexOf(",")>0){
			var tftype=ftype.split(",");
			for(var i=0;i<tftype.length;i++){
				fileType[i]="."+tftype[i];	
			}
		}else{
			fileType[0]="."+ftype;
		}
	}
	var fpath=document.getElementById('_video').value;
	return checkType(fpath,fileType);
}
function checkType(fpath,fileType){
	if(fpath==null||fpath==''){
		return true;
	}
	var findex = fpath.lastIndexOf(".");
	  if(findex == -1){
	  	alert("无效文件");
	  	return false;
	  }
	  if(findex != -1 && fileType.length > 0 && fileType != "undefined")
	  {
		  fpath = fpath.substring(findex,fpath.length);
		  fpath=fpath.toLowerCase();
	  	 var temp=''; 
	  	 for(var i=0;i<fileType.length;i++){
			  if(fpath==fileType[i].toLowerCase()){
				  temp=fpath;
				  break;
			  }
		  }
		 if(temp==null||temp==''){
			 alert('不支持'+fpath+'格式的文件');
			 return false;
		 } 
	  }
	  return true;
}
//检查封面格式
function checkImageType(){
	var imageType=new Array();
	imageType[0]='.jpeg';
	imageType[1]='.jpg';
	imageType[2]='.png';
	imageType[3]='.gif';
	imageType[4]='.bmp';
	return checkType(document.getElementById('_image').value,imageType);
}
	function save() {
		var valid = new Validation('myform', {
			immediate : true
		});
		if (valid.validate()){
			var docId='${docId}';
			if(docId!=null&&docId!=''){
				var _album=document.getElementById("_albumId");
				for(var i=0;i<_album.length;i++){
					if(_album[i].selected){
						document.getElementById("domain.albumId").value=_album[i].value;
						break;
					}
				}
			}
			if(!checkFileType()){
				return ;
			}
			if(!checkImageType()){
				return ;
			}
			var maxSize=document.getElementById('maxSize');
			if(maxSize.value=='0.0'){
				maxSize.value='100.0';
			}
			myform.submit();
		}
			
	}
	function closed(){
		window.returnValue='true';
		window.close();
	}
	
	
</script>

<style type="text/css">
.pw-w660_ab {
	width:660px;
	height:380px;
	
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

.textField-w150_ab {
	width:150px;
}
.textField-w150_ab {
	width:150px;
}

.select-w150_ab {
	width:150px;
	_width:150px;
}
.pw-con-input-item-plu_ab {
	border:1px solid #999;
	width:200px;
	height:200px;
#height:200px;
}

.textarea-w150_ab{
	width:150px;
	height:60px;
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
</head>
<body class="pw_ab" >
	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>
<form class="pw-borderOut_ab pw-w660_ab" method="post" action="${ctx}/base/video!saveOrUpdate.action" name="myform" enctype="multipart/form-data">
			<ww:hidden id="id" name="domain.id" />
			<ww:hidden name="domain.siteId" id="domain.siteId" />
			<ww:hidden id="domain.createTime" name="domain.createTime"></ww:hidden>
			<ww:hidden id="video" name="domain.filePath"></ww:hidden>
			<ww:hidden id="domain.imagePath" name="domain.imagePath"></ww:hidden>
			<ww:hidden id="domain.albumId" name="domain.albumId"></ww:hidden>
			<ww:hidden id="domain.issue" name="domain.issue"></ww:hidden>
			<ww:hidden id="domain.fileName" name="domain.fileName"></ww:hidden>
			<ww:hidden id="domain.fileSize" name="domain.fileSize"></ww:hidden>
			<ww:hidden id="domain.creative" name="domain.creative"></ww:hidden>
			<ww:hidden id="maxSize" name="maxSize"></ww:hidden>
			<ww:hidden id="docName" name="docName"></ww:hidden>
			<ww:hidden id="docId" name="docId"></ww:hidden>
	<div class="pw-borderIn_ab"  >
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>编辑视频</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:344px;overflow-y:auto;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">编辑视频</h2>
                </li>
                
                   <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>视频名称：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span> 
                  			<ww:textfield name="domain.title"
									cssClass="textField_ab textField-w150_ab  required max-length-24" />
                    </label>
                </li>
                <c:if test="${docId!=null }">
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>专辑选择：</span>
                        </span> 
                  		<ww:action name="album!getAll" namespace="/base" id="album">
                  		<ww:param name="albumType">video</ww:param>
                  		</ww:action>
                         <ww:select id="_albumId" list="#attr.album.albums" listKey="id" listValue="title" headerKey="" headerValue="默认专辑" cssClass="textField_ab textField-w150_ab"/>
                    </label>
                </li>
                </c:if>
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span><c:if test="${domain.id!=null }">重新</c:if>上传视频：</span>
                        	<c:if test="${domain.id==null }">
                        		<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        	</c:if>
                        </span>
                        <c:if test="${domain.id==null }"> 
                  		 <ww:file name="_file" id="_video" accept="text/*" size="40" cssClass="required textField_ab select-w150_ab"/>
                  		 </c:if>
                  		 <c:if test="${domain.id!=null }"> 
                  		<ww:file name="_file" id="_video" accept="text/*" size="40" cssClass="textField_ab select-w150_ab"/>
                  		 </c:if>
                    </label>
                </li>
                
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span><c:if test="${domain.id!=null }">重新</c:if>上传封面：</span>
                        	<c:if test="${domain.id==null }">
                        		<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        	</c:if>
                        </span>
                        <c:if test="${domain.id==null }"> 
                  		 <ww:file name="_fileImage" id="_image" accept="text/*" size="40" cssClass="required textField_ab select-w150_ab"/>
                  		 </c:if>
                  		 <c:if test="${domain.id!=null }"> 
                  		<ww:file name="_fileImage" id="_image" accept="text/*" size="40" cssClass="textField_ab select-w150_ab"/>
                  		 </c:if>
                    </label>
                </li>
                
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>视频描述：</span>
                        </span> 
                        <span class="lab-pw-tit_ab2">
                  	       <ww:textarea name="domain.discribe"
									cols="30" rows="6" cssClass="max-length-200 textField_hb textarea-w150_ab" />
						</span>
                    </label>
                </li>
               
               
            </ul>
            <ul class="pw-con-input_ab pw-con-input-ex_ab pw-con-input-rx_ab">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">视频预览</h2>
                </li>
            	<li class="pw-con-input-item_ab">
                	<div class="pw-con-input-item-plu_ab">
                			<div id="divvideo"></div>
									<script language='javascript'>
										m_showVideo('video', '200', '200','${domain.format}','${mediaFilePath}','${domain.filePath}');
									</script>
						
                    </div>
                </li>
            </ul>
            
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
 		
				<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save();"/>
            	<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>

					
            </div>
       
       
        </div>
    </div>
</form>
 <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>
	   try{
		   parent.frames['main_frame'].location.reload();
	   }catch(e){
	   }
	   
	   global_ab.fn.popWindow.removePopWindow(false);</script>
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
<c:if test="${not empty actionErrors}">
	    <c:forEach var="err" items="${actionErrors}">
	        <script> alert("上传文件太大");</script>
        </c:forEach> 
	</c:if>