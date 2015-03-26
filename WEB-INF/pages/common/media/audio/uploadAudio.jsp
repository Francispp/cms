<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script src="${ctx}/dwr/interface/mediaIntermediateService.js" type="text/javascript"></script>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<script type="text/javascript">
	
	function save(){
		var param='name=${name}&type=audio';
		var id=document.frames['iframe_audios'].getCheck();
		if(id==null||id==''){
			return ;//未选择引用音频
		}
		window.returnValue = param+"&mediaId="+id;
		window.close();	
	}
	//添加
	function addItem(){
		global_ab.fn.popWindow.addPopWindow("${ctx}/base/audio!add.action?docId=${docid}&docName=${name}&ftype=${ftype}&maxSize=${maxSize}","350px", "452px",false);
	}
	function reloadIframe(){
		document.frames['iframe_audios'].location.href="${ctx}/base/audio!listBySite.action";
	}
	function loadAudios(v){
		var param="";
		if(v.value=='default'){
			param="isdefault=1";
		}else{
			param="albumId="+v.value;
		}
		document.frames['iframe_audios'].location.href="${ctx}/base/audio!listBySite.action?"+param;
	} 
</script>
</head>
<body>
	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>
	
	
	
	

<div class="edit-header">
  <div class="edit-header-top">
  <ul id="lion-ul-a">
  <li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
					class="artEdit-btn-in_ab" href="#" onclick="addItem();"> <img
						src="${default_imagepath}/ico_013_plus.gif"
						class="ico_ab ico-013_ab" /> <span>上传</span> </a></li>
				  <li class="artEdit-btn_ab">
      	  <a class="artEdit-btn-in_ab" href="javascript:save();">
            <img src="${default_imagepath}/ico_020_upcomingWork.gif" class="ico_ab ico-013_ab" />
            <span>确定</span>
         </a>
		    </li>
		    <li class="artEdit-btn_ab">
		        <a class="artEdit-btn-in_ab" href="javascript:window.close();">
		            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
		            <span>关闭</span>
		         </a>
		    </li>
		    <li class="artEdit-btn_ab">
		        <a class="artEdit-btn-in_ab" href="javascript:reloadIframe();">
		            <img src="/images/cybercms/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
		            <span>刷新</span>
		         </a>
		    </li>
    <li class="fn-clear"></li>
</ul>
  </div>
  
  <div class="edit-header-bottom">
    <ul>
    <li class="">查询</li>

      <li class="">
      
      
       <c:forEach var="album" items="${albumList}" varStatus="status" >
      
      	<select id="_albumId" onchange="loadAudios(this)">
      		<option value="">所有专辑</option>
      		<option value="default">默认专辑</option>
      		<option value="${album.id}">${album.title}</option>
      	</select>
      </c:forEach>
                     </li>
    </ul>
  </div>
  
</div>

	
	
	
<iframe id="iframe_audios"  allowtransparency="yes" scrolling="auto"  src="${ctx}/base/audio!listBySite.action" width="100%" height="100%"  style="border: 0px none;" frameborder="0"></iframe>
		
	<!-- 页脚 -->
	<%@ include file="/common/foot.inc"%>
</body>
</html>
