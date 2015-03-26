//多附件上传并列表显示 dv为显示列表的ID，spec为是否以显示图标＝showimg时显示。fpath为附件存放的路径，docid为文档的ID，
function newAttachment(dv,spec,fpath,docid,siteid,ftype,fname,fmaxsize)
{
	var filePath = selectAttachmentList(fpath,docid,siteid,ftype,fname,fmaxsize);
	if(filePath != '')
	{
	viewAttachment(dv,spec,docid,fname);
	 try{
          parent.dyniframesizeforall("main_frame");
       }catch(E){}
	}
}
function selectAttachment(obj, fpath, docid,ftype,fname){ 
        if (docid == null || docid == 'undefined') {
         docid='';
        }
	var rtn = uploadFile(fpath, docid,ftype,fname);
	if (rtn == null || rtn == 'undefined') {
  }
  else if (rtn == '') {
		obj.value = '';
  }
  else {
	  obj.value = rtn;
	}
}

//附件列表显示
function viewAttachment(dv,spec,docid,fname){
	var url1 = "/cms/filedownload!listJson.action?name="+fname+"&docid="+docid;
	LoadData(url1, function(data){writeTable(dv,spec,docid,fname,data);});
}

//附件列表显示
function viewAttachmentForDetailsTemplate(dv,spec,docid,fname){
	var url1 = "/cms/filedownload!listJson.action?name="+fname+"&docid="+docid;
	LoadData(url1, function(data){writeTableForDetailsTemplate(dv,spec,docid,fname,data);});
}



//附件列表显示
function uploadOneFileByDocId(docid,fname)
{
		var url1 = "/cms/filedownload!listXml.action?name="+fname+"&docid="+docid;
	if (LoadData(myXML,url1))  
	{
		try{
		window.location =  "/cms/filedownload!fileDownloadById.action?id=" + myXML.recordset('AID') + "&docid="+docid;
		}catch(e){alert("E");}
	}
}


//上传附件
function uploadFile(fpath,docid,siteid,ftype,fname,fmaxsize) {
  wy = '80px';
  wx = '500px';
  var url = '/cms/attachment!upload.action?maxSize='+fmaxsize+'&name='+fname+'&fpath=' + fpath + '&docid=' + docid+'&siteid=' + siteid+'&ftype='+ftype;
  
  //alert(url);
  
  var rtn = showframe('文件上传', url);
  
  //alert(rtn);

  if (rtn == null || rtn == 'undefined') {
  }
  else if (rtn == '') {
    return '';
  }
  else
    return rtn;
}
function uploadPIC(fpath,docid,siteid,ftype,fname,fmaxsize,imgWidth,imgHeight,channelid) {
 ftype="jpg,bmp,jpeg,gif,png";
 fname="";
 fmaxsize="5";
  wy = '80px';
  wx = '500px';
  var url = '/cms/attachment!upload.action?isPic=true&maxSize='+fmaxsize+'&name='+fname+'&fpath=' + fpath + '&docid=' + docid+'&siteid=' + siteid+'&ftype='+ftype+'&imgWidth='+imgWidth+'&imgHeight='+imgHeight;
  if(channelid){
	  url+=('&channelid='+channelid);
  }
  
  var rtn = showframe('文件上传', url);
  
  if (rtn == null || rtn == 'undefined') {
  }
  else if (rtn == '') {
    return '';
  }
  else
    return rtn;
}
function uploadXML(pageTitle,actionURL) {
  wy = '80px';
  wx = '500px';

  var url = '/cms/attachment!upload.action?actionURL='+actionURL;
  
  //alert(url);
  
  var rtn = showframe(pageTitle, url);
  
  //alert(rtn);

  if (rtn == null || rtn == 'undefined') {
  }
  else if (rtn == '') {
    location.href = location.href;
  }
  else
    alert("导入出错！");
}



function selectAttachmentList(fpath,docid,siteid,ftype,fname,fmaxsize){ 
	var rtn = uploadFile(fpath,docid,siteid,ftype,fname,fmaxsize);
	//alert(rtn);
	
	if (rtn == null || rtn == 'undefined') {
		return '';
  }
  else if (rtn == '') {
		return '';
  }
  else {
	  return rtn;

	 }
}


function deleteAttachment(id,dv,spec,docid,fname)
{
	if(confirm("您确认要删除该附件吗?")){
		var url2 = "/cms/attachment!listDeleteJson.action?name="+fname+"&docid="+docid+"&id=" + id;
		LoadData(url2,function(data){writeTable(dv,spec,docid,fname,data)});
	}
}


//增设spec参数，用于控制是否直接显示图片
function writeTable(dv,spec,docid,fname, data){
	var inn = "";
	if(data){
		if(data.RECORDSET && data.RECORDSET.length > 0){
			inn = "<table width='452' cellpadding='4'  cellspacing='1' bgcolor='#99CCCC' class='attachment_table'>"
			inn +="<tr >"
			inn +="<td width='260' bgcolor='#FFFFFF' class='attachment_name'>文件名</td>";
			inn +="<td  width='128' bgcolor='#FFFFFF' class='attachment_size'>文件大小</td>";
			inn +="<td width='44' bgcolor='#FFFFFF' class='attachment_delete'>删除</td>";
			inn +="</tr>";
			for(i=0;i<data.RECORDSET.length;i++){
				var record = data.RECORDSET[i];
				inn +="<tr class='attachment_list'>";
				try{
					inn +="<td bgcolor='#FFFFFF'><a href='/cms/filedownload!fileDownloadById.action?id=" + record['AID'] + "&docid="+docid+"'>";
					inn += record['AFILESHORTPATH'];
					inn +="</a>"
					if(spec != null && spec == "showimg"){
						inn += "<br><img src='" + record['AFILEPATH'] + "' border='0' onload='if(this.width>400)this.width=400;'>";
					}
					inn +="</td>";
					inn+="<td  bgcolor='#FFFFFF'>" + record['AFILESIZE'] + "</td>"
					inn+="<td  bgcolor='#FFFFFF' class='attachment_button'><input type=button name=btnid  value=删除  onClick=deleteAttachment(" + record['AID'] + "," + dv.id + ",'"+ spec +"','"+ docid +"','"+ fname +"');></td>"
				}catch(E){}
				inn +="</tr>";
			}
			inn +="</table>";
		}
	}
	dv.innerHTML= inn;
}

function writeTableForDetailsTemplate(dv,spec,docid,fname,data){
	var inn = "";
	alert("data is:"+data);
	if(data){
		if(data.RECORDSET && data.RECORDSET.length > 0){
			inn = "<table width='452' cellpadding='4'  cellspacing='0'  class='attachment_table'>"
	
			for(i=0;i<data.recordset.length;i++){
				var record = data.RECORDSET[i];
				inn +="<tr class='attachment_list'>";
				try{
					inn +="<td bgcolor='#FFFFFF'><a href='/cms/filedownload!fileDownloadById.action?id=" + record['AID'] + "&docid="+docid+"'>";
					inn += record['AFILESHORTPATH'];
					inn +="</a>"
					if(spec != null && spec == "showimg"){
						inn += "<br><img src='" + record['AFILEPATH'] + "' border='0' onload='if(this.width>400)this.width=400;'>";
					}
					inn +="</td>";
				}catch(E){}
				inn +="</tr>";
			}
			inn +="</table>";
		}
	}
	dv.innerHTML= inn;
}
function showframe(title,querystr) {
  var pth = window.location.pathname;
  var pos1 = pth.lastIndexOf("/");
  var pos2 = pth.lastIndexOf("\\");
  var pos = Math.max(pos1, pos2);

  querystr.title = title;
  if (querystr.url!=null){
    if (!(querystr.url.indexOf("/")==0 || querystr.url.indexOf("\\")==0))
      querystr.url = pth.substring(0,pos)+"/"+querystr.url;
  }
  else {
    if (!(querystr.indexOf("/")==0 || querystr.indexOf("\\")==0))
      querystr = pth.substring(0,pos)+"/"+querystr;
  }
  var rt= window.showModalDialog('/frame.jsp?title=' + title,querystr,'font-size:9pt;dialogWidth:' + wx + ';dialogHeight:' + wy + ';status:no;scroll=no;');
  //alert(rt);
  if(rt=="timeout")
   {
    parent.location.href="/login.action";
}
  return rt;
}
//用于图片上传并在当前页面显示 objName为隐藏字段名称,fpath为上传地址.
var picBase ="/uploads/attachment/";
var picBase ="";
function uploadPic(objName,fpath,docid,siteid,imgWidth,imgHeight,imgTitle,optionType,fileSizeField,channelid)
{
	
	
  var objHidden = document.getElementById(objName);
  var objDiv = document.getElementById("div"+objName);
  
  
  
 
 // var objBtn = document.getElementById("btn"+objName);
  var rtn = uploadPIC(fpath,docid,siteid,optionType,imgTitle,fileSizeField,imgWidth,imgHeight,channelid);
  if(  fpath != '' && fpath != undefined)
     {
     picBase = fpath;
     }
  if(rtn !='' && rtn != undefined)
    {
    objHidden.value = picBase + rtn;
   /** if(imgWidth !='' && imgHeight != '')
    {
    objDiv.innerHTML = "<img width='"+imgWidth+"' height='"+imgHeight+"' src='"+picBase+rtn+"' title='"+imgTitle+"'/>";
    }
    else if(imgWidth.length < 1 && imgHeight != '')
    {
    objDiv.innerHTML = "<img height='"+imgHeight+"' src='"+picBase+rtn+"' title='"+imgTitle+"'/>";
    }
    else if(imgWidth !='' && imgHeight.length < 1)
    {
    objDiv.innerHTML = "<img width='"+imgWidth+"' src='"+picBase+rtn+"' title='"+imgTitle+"'/>";
    }
    else{
        objDiv.innerHTML = "<img  src='"+picBase+rtn+"' title='"+imgTitle+"'/>";
        }
        **/
       showPic(objName,imgWidth,imgHeight,imgTitle);
    }
  try{
    parent.dyniframesizeforall("main_frame");
}catch(E){}
}

function doEmpty() {
	window.returnValue = "";
	window.close();
}
function showPic(objName,imgWidth,imgHeight,imgTitle)
{
  var objHidden = document.getElementById(objName);
  var objDiv = document.getElementById("div"+objName);
  if(  objHidden != '' && objHidden != undefined && objHidden.value != '' && objHidden.value != 'null')
    {
    if(imgWidth !='' && imgHeight != '')
    {
    objDiv.innerHTML = "<img width='"+imgWidth+"' height='"+imgHeight+"' src='"+objHidden.value+"' />&nbsp;<input type=button name=btnid class='delbt' value=删除  onClick='clearPic(\""+objName+"\");'/>";
    }
    else if(imgWidth.length < 1 && imgHeight != '')
    {
    objDiv.innerHTML = "<img height='"+imgHeight+"' src='"+objHidden.value+"' />&nbsp;<input type=button name=btnid  class='delbt' value=删除  onClick='clearPic(\""+objName+"\");'/>";
    }
    else if(imgWidth !='' && imgHeight.length < 1)
    {
    objDiv.innerHTML = "<img width='"+imgWidth+"' src='"+objHidden.value+"' />&nbsp;<input type=button name=btnid  class='delbt' value=删除  onClick='clearPic(\""+objName+"\");'/>";
    }
    else{
        objDiv.innerHTML = "<img  src='"+objHidden.value+"' />&nbsp;<input type=button name=btnid  class='delbt' value=删除  onClick='clearPic(\""+objName+"\");'/>";
        }
   // objDiv.innerHTML = "<img width='"+imgWidth+"' height='"+imgHeight+"' src='"+objHidden.value+"' title='"+imgTitle+"'>";
    }
}
function clearPic(objName)
{
	if (confirm("您确定要删除图片吗？"))
	{
  var objHidden = document.getElementById(objName);
  var objDiv = document.getElementById("div"+objName);
  objHidden.value="";
  objDiv.innerHTML = "";
}
}
//默认的模板附件地址
var DEFUALT_TEMPLATE_ATTACHMENT_PATH="/templates/attachment/";

//media媒体改变时上传
var media_ischannge=false,media_param='';

//编辑视频时显示
function m_showVideo(objName,width,height,ftype,mediaFilePath,filePath){
	var objHidden = document.getElementById(objName);
	var objDiv = document.getElementById("div"+objName);
	if(  objHidden != '' && objHidden != undefined && objHidden.value != '' && objHidden.value != 'null'){
		if(ftype=='flv'){																																														
			objDiv.innerHTML="<object id=\"FlashID\" height=\""+height+"\" width=\""+width+"\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"><param name=\"movie\" value=\"/cms_file/media/myvideo.swf\" /><param name=\"FlashVars\" value=\"videoURL="+filePath+"&portId="+mediaFilePath+"&styled=0 /><param name=\"allowFullScreen\" value=\"true\" /><param name=\"quality\" value=\"high\" /><param name=\"wmode\" value=\"opaque\" /><param name=\"swfversion\" value=\"6.0.65.0\" /></object>";		
		}else{
			objDiv.innerHTML="<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' codebase='' id='WindowsMediaPlayer1' Width='"+width+"' Height='"+height+"' align='top'><param name='ShowStatusBar' value='1'><param name='Filename' value='"+objHidden.value+"'><param name='PlayCount' value='1'><param name='AutoStart' value='0'><param name='ClickToPlay' value='1'><param name='DisplaySize' value='1'><param name='EnableFullScreen Controls' value='1'><param name='ShowAudio Controls' value='1'><param name='ShowControls' value='1'><param name='ShowTracker' value='1'><param name='EnableContext Menu' value='1'><param name='ShowDisplay' value='0'></object>";
		}
	}	
}

//前台上传视频
//路径、文档ID、容许上传的类型、div显示名称、后台存储名称、大小、分发、文件类型
function t_uploadVideo(fpath,docId,ftype,divId,name,fmaxsize,fissue,width,height,mediaType,siteId) {
	  wy = '520px';
	  wx = '800px';
	  var url = '/cms/mediaManager!uploadVideoTranspond.action?maxSize='+fmaxsize+'&name='+name+'&fpath=' + fpath + '&docid=' + docId+'&ftype='+ftype+'&issue='+fissue+"&siteId="+siteId+"&mediaType="+mediaType;
	  var rtn = showframe("文件上传", url);
	  if (rtn == null || rtn == 'undefined') {
	  }
	  else if (rtn == '') {
	    return '';
	  }else if (rtn == "timeout"){
		return '';
	  }else{
		  if(rtn.lastIndexOf(":")>0&&rtn.substring(0,rtn.lastIndexOf(":"))){
			  var showRtn=rtn.substring(rtn.lastIndexOf(":")+1,rtn.length);
			  t_showList(docId,divId,name,width,height,mediaType,true,showRtn);
		  }
		  media_param=rtn.substring(0,rtn.lastIndexOf(":"));
		  media_ischannge=true;
	   }
}
//公用显示上传列表
function t_showList(docId,divId,name,width,height,mediaType,isinclude,includeFilePath){
	var url = "/cms/mediaManager!listxml.action?name="+name+"&docid="+docId+"&mediaType="+mediaType;
	var tcount='true';
	if(isinclude){
		var inn = "<table width='300' cellpadding='4'  cellspacing='1' bgcolor='#99CCCC' class='attachment_table'>"
			inn +="<tr >"
			inn +="<td width='160' bgcolor='#FFFFFF' class='attachment_name'>文件路径</td>";
			inn +="</tr>"; 
			inn +="<tr class='attachment_list'>";
			inn +="<td bgcolor='#FFFFFF'><div id='showFileName'>"+includeFilePath+"</div></td>";
			inn +="</tr>";
			inn +="</table>";
		document.getElementById("div"+divId).innerHTML= inn;	
	}else{
		if (LoadData(myXML,url))  
		{
			if(myXML.recordset!=null && myXML.recordset.recordcount > 0){
				inn = "<table width='300' cellpadding='4'  cellspacing='1' bgcolor='#99CCCC' class='attachment_table'>"
				inn +="<tr >"
				inn +="<td width='160' bgcolor='#FFFFFF' class='attachment_name'>文件路径</td>";
				inn +="</tr>";
				while(!myXML.recordset.eof){
					inn +="<tr class='attachment_list'>";
					try{
						inn +="<td bgcolor='#FFFFFF'><div id='showFileName'>"+myXML.recordset('FileName')+"</div></td>";
					}catch(E){
						tcount='false';	
					}
					inn +="</tr>";
					myXML.recordset.MoveNext()
					
				}
				inn +="</table>";
			}
			if(tcount=='true'){
				document.getElementById("div"+divId).innerHTML= inn;	
			}
		}
	}
	
}

//公用页面显示
function t_show(docId,divId,name,width,height,mediaType){
	var url = "/cms/mediaManager!listxml.action?name="+name+"&docid="+docId+"&mediaType="+mediaType;
	if (LoadData(myXML,url))  
	{
		var inn = "";
		var ftype = "";
		if(myXML.recordset!=null && myXML.recordset.recordcount > 0){
			while(!myXML.recordset.eof){
				try{
					ftype=myXML.recordset('FORMAT');
					if(ftype=='flv'){																																																			
						inn+="<object id=\"FlashID\" height=\""+height+"\" width=\""+width+"\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"><param name=\"movie\" value=\"/cms_file/media/myvideo.swf\" /><param name=\"FlashVars\" value=\"myArrayVideo="+myXML.recordset('FULLPATH')+"\" /><param name=\"allowFullScreen\" value=\"true\" /><param name=\"quality\" value=\"high\" /><param name=\"wmode\" value=\"opaque\" /><param name=\"swfversion\" value=\"6.0.65.0\" /></object>";	
					}else if(ftype=='wmv'){
						inn+="<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' codebase='' id='WindowsMediaPlayer1' Width='"+width+"' Height='"+height+"' align='top'><param name='ShowStatusBar' value='1'><param name='Filename' value='"+myXML.recordset('FULLPATH')+"'><param name='PlayCount' value='1'><param name='AutoStart' value='0'><param name='ClickToPlay' value='1'><param name='DisplaySize' value='1'><param name='EnableFullScreen Controls' value='1'><param name='ShowAudio Controls' value='1'><param name='ShowControls' value='1'><param name='ShowTracker' value='1'><param name='EnableContext Menu' value='1'><param name='ShowDisplay' value='0'></object>";
					}else if(ftype=='mp3'){
						inn+="<EMBED  src='"+myXML.recordset('FULLPATH')+"' loop='-1'  height=\""+height+"\" width=\""+width+"\" type=audio/x-pn-realaudio-plugin controls='all' />";	
					}
				}catch(E){
					
				}
				myXML.recordset.MoveNext();
			}
			document.getElementById("div"+divId).innerHTML= inn;
		}
	}
}

//公用删除
function t_del(name,docId,mediaType,divId){
	
	if (confirm("您确定要删除该文件吗？"))
	{
	var myUrl = "/cms/mediaManager!del.action";
	var param="name="+name+"&docid="+docId+"&mediaType="+mediaType;
	
	 new Ajax.Request(myUrl, {
		    method:'post',
		    parameters:param,
		    onSuccess: function(transport) {
		    	alert('删除成功');
				document.getElementById('div'+divId).innerHTML= '';	
		    },
		    onFailure: function(){alert('删除失败');}
		  });
		}
}


//路径、文档ID、容许上传的类型、div显示名称、后台存储名称、大小、分发、文件类型
function t_uploadAudio(fpath,docId,ftype,divId,name,fmaxsize,fissue,width,height,mediaType,siteId) {
	  wy = '520px';
	  wx = '800px';
	  var url = '/cms/mediaManager!uploadAudioTranspond.action?maxSize='+fmaxsize+'&name='+name+'&fpath=' + fpath + '&docid=' + docId+'&ftype='+ftype+'&issue='+fissue+'&siteId='+siteId+"&mediaType="+mediaType;
	  var rtn = showframe("文件上传", url);
	  if (rtn == null || rtn == 'undefined') {
	  }
	  else if (rtn == '') {
	    return '';
	  }
	  else if (rtn == "timeout")
	{
		return '';
	}
	  else{
		  if(rtn.lastIndexOf(":")>0&&rtn.substring(0,rtn.lastIndexOf(":"))){
			  var showRtn=rtn.substring(rtn.lastIndexOf(":")+1,rtn.length);
			  t_showList(docId,divId,name,width,height,mediaType,true,showRtn);
		  }
		  media_param=rtn.substring(0,rtn.lastIndexOf(":"));
		  media_ischannge=true;
	   }
}

//ajax上传图片
function ajaxUploadPhoto(inputId,btnId,tdId,targetId,width,height,maxSize){
	if(!maxSize){
		maxSize = 0.1;
	}
	var actionUrl="/cms/attachment!savePhotoAjax.action?isPic=true&name=&fpath=&docid="+jQuery("#domain_id").val()
					+"&channelid="+jQuery("#channel_id").val()+"&siteid="+jQuery("#site_oid").val()+"&maxSize="+maxSize;

	if(width){
		actionUrl+=("&imgWidth="+width);
	}
	if(height){
		actionUrl+=("&imgHeight="+height);
	}
	var uploadBtn = jQuery("#"+btnId);
	var uploadTd = jQuery("#"+tdId);
	var uploadForm = jQuery("#ajaxUploadPhotoForm");
	var uploadInput = jQuery("#"+inputId);
	var oldUploadBtnVal = uploadBtn.val();
	uploadBtn.val('上传中...');
	uploadBtn.attr('disabled', true);
	if(uploadForm.length==0){
		var formStr = "<form method='post' style='display:none;' id='ajaxUploadPhotoForm'><input type='file' name='_file' id='ajaxUploadPhotoInput' /></form>";
		jQuery("body").eq(0).prepend(formStr);
		uploadForm = jQuery("#ajaxUploadPhotoForm");
	}
	uploadInput.after("<div style='display:none;' id='ajaxUploadPhotoMarkDiv'></div>");
	var oldInputName = uploadInput.attr("name");
	uploadInput.attr("name", "_file");
	uploadForm.prepend(uploadInput);
	uploadForm.ajaxSubmit({
		url: actionUrl,
		dataType : "json",
		success: function(data){
			uploadInput.attr("name", oldInputName);
			jQuery("#ajaxUploadPhotoMarkDiv").before(uploadInput);
			jQuery("#ajaxUploadPhotoMarkDiv").remove();
			uploadBtn.val(oldUploadBtnVal);
			uploadBtn.attr('disabled', false);
			if(data.success){
				uploadTd.children().remove("img");
				var img = "<img src='"+data.url+"' width='160' height='160' />";
				uploadTd.prepend(img);
				jQuery("#"+targetId).val(data.url);
			} else {
				alert(data.msg);
			}
		},
		error: function(data){
			alert("上传出错");
			uploadInput.attr("name", oldInputName);
			jQuery("#ajaxUploadPhotoMarkDiv").before(uploadInput);
			jQuery("#ajaxUploadPhotoMarkDiv").remove();
			uploadBtn.val(oldUploadBtnVal);
			uploadBtn.attr('disabled', false);
		}
	});
}