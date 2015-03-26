 //表单页的保存
 function save(){
	 try{
		 var length='';
		 if(fckEditor!=null){ 
			 //过滤textArea 不能验证字符长度的问题
				Form.getElements(this.myform).each(function(input) {
					if(input.id.startsWith('Html')){
						var cname=input.className;
						if(cname.indexOf("-")){
							cname=cname.substring(cname.lastIndexOf("-")+1,cname.length);
							var content=FCKeditorAPI.GetInstance(input.name).GetXHTML(true);
							content=content.replace(/<[^>]*>/gim,"");
							if(Number(cname)<content.length){
								length="正文最大能输入"+cname+"字符,现输入"+content.length;
							}
						}
					}
				});
		 }
		
		 if(length!=''){
			 alert(length);
			 return ; 
		 }
	 }catch(e){
		 
	 }
	 
	 
  if(!valid || valid.validate()){
   if( beforsave1() && beforsave())
   myform.submit();
   }
  
  
 }
  //保存并发布 rtType=1表示返回前台
 function saveAndPublic(rtType){
  if(!valid || valid.validate()){
   beforsave();
   var sURL = addParam(myform.action, 'isPublic', 'TRUE');
    var fromUrl="";
    if(!rtType || rtType=='1')
    //if((location.href).indexOf('docInfo!list.action')>=0)
     fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
   myform.action = sURL+fromUrl;
   myform.submit();
   }
 }
 function onlineRSS()
 {
 if (confirm("您确定要定阅该栏目吗？"))
 {
   new Ajax.Request('/component/subcreibe!rss.action', {
    method:'get',
    parameters:"channelId="+channelId,
    onSuccess: function(transport) {
    alert("定阅成功!");
      //alert(transport.responseText);
      
    },
    onFailure: function(){alert('定阅失败！');}
  });
}
}
function addRSS()
 {
   new Ajax.Request('/component/subcreibe!rss.action', {
    method:'get',
    parameters:"channelId="+$("RsId").value+"&startDate="+$("startDate").value+"&endDate="+$("endDate").value,
    onSuccess: function(transport) {
    alert("定阅成功!");
    location.reload();
      //alert(transport.responseText);
      
    },
    onFailure: function(){alert('定阅失败！');}
  });
}
function removeRSS(id)
 {
   if (confirm("您确定要移除定阅的栏目吗？"))
   {
   new Ajax.Request('/component/subcreibe!delete.action', {
    method:'get',
    parameters:"keys="+id,
    onSuccess: function(transport) {
    location.reload();
      //alert(transport.responseText);
      
    },
    onFailure: function(){alert('移除失败！');}
  });
}
}
 
 function PublicAndRefreshView(){
  if(!valid || valid.validate()){
   beforsave();
   var sURL = addParam(myform.action, 'isPublic', 'TRUE');
    var fromUrl="";
  
     fromUrl="&fromUrl=" +ctx+ "/refreshView.htm";
   myform.action = sURL+fromUrl;
   myform.submit();
   }
 }
  //保存并完成任务 rtType=1表示返回前台
 function saveAndComple(rtType){
  if(!valid || valid.validate()){
   beforsave();
   var sURL = addParam(myform.action, 'optName', 'comple');
    var fromUrl="";
    //if(!rtType || rtType=='1')
    // fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
   myform.action = sURL+fromUrl;
   myform.submit();
   }
 } 
 //表单页面回退
 function goBack(){
  //location.href='document!adminList.action?channelId='+channelId;
	try {
		window.opener.ECSideUtil.reload('myTable');
	}catch(e){
		window.close();
	}
	window.close();
  }
 function insertItem(id,openStyle)
  {
	 
  	var urlStr =ctx+"/cms/document!adminEdit.action?channelId="+channelId;
  	if(openStyle && openStyle==1)
 	  window.open(urlStr);
 	else 	   
 	  location=urlStr;
  }
  //设置文档权限
 function setDocPermission(id){
 	if(!id||id==null||id=='')
 	  id=Id;
 	if(id!=null&&id!=''){ 	
 	 var querystr=ctx+"/cms/permission.action?type=3&objectId="+id+"&objectType=3";
 	 var title="文档权限设置";
 	 window.showModalDialog(ctx + '/frame.jsp?title=' + title,querystr,'font-size:9pt;dialogWidth:500px;dialogHeight:400px;status:no;scroll=no;');
 	}else
 	 alert('请传入文档Id!');
 	}
function openDocPerTree(id){
 	if(!id||id==null||id=='')
 		id=Id;
 	if(id!=null&&id!=''){ 	
 		var querystr=ctx+"/cms/permission!docPerTree.action?objectId="+id;
 		var title="文档查看权限设置";
 		window.showModalDialog(ctx + '/frame.jsp?title=' + title,querystr,'font-size:9pt;dialogWidth:500px;dialogHeight:400px;status:no;scroll=no;');
 	}else
 	 	 alert('请传入文档Id!');
}

 	//删除文档
  function deleteItem(id)
  {
	  
	 
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要删除选择的记录吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminDelete.action?keys='+ids+'&channelId='+channelId+fromUrl;
 	}
  }
    function exportData(id)
  {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要导出选择的记录吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminExport.action?keys='+ids+'&channelId='+channelId+fromUrl;
 	}
  }
  function importData(channel_id){
	 var title="选择导入的文件";
	 actionURL="document!adminImport.action?channelId="+channel_id;
	 uploadXML(title,actionURL);
	 }
  
  //移动文档
  function moveItem(id)
    {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要移动选择的记录吗？"))
 	{
 		var chnUrl=ctx+"/cms/site!selectChanelTree.action?siteid=&channelId="+channelId;		 		
 		var chnId=selectChannelEx2(chnUrl);
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		
 		if(chnId && chnId!=null && chnId!='')
 		 location.href='document!adminMove.action?keys='+ids+'&channelId='+channelId+'&id='+chnId+fromUrl;
 	}
  }
  
    //置顶
  function setTopItem(state,id)
    {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	} 
 	if (confirm(state == 0 ? "您确定要撤消置顶选择的记录吗？" : "您确定要置顶选择的记录吗？"))
 	{ 
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId; 
 		 
 	 		location.href='document!adminSetTop.action?keys='+ids+'&channelId='+channelId+'&type='+state+'&fieldName=toped'+fromUrl;
 	}
  } 
  
    //设置最新
  function setNewItem(state,id)
    {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm(state == 0 ? "您确定要撤消选择的记录最新吗？" : "您确定要设置选择的记录为最新吗？"))
 	{ 
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId; 
 		 
 	 		location.href='document!adminSetNew.action?keys='+ids+'&channelId='+channelId+'&type='+state+'&fieldName=newsed'+fromUrl;
 	}
  } 
  
    //设置 属性值
  function setProperteisItem(fieldName,state,id)
    {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm(state == 0 ? "您确定要撤消选择的记录吗？" : "您确定要设置选择的记录吗？"))
 	{ 
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId; 
 		 
 	 		location.href='document!adminSetProperties.action?keys='+ids+'&channelId='+channelId+'&type='+state+'&fieldName='+fieldName+fromUrl;
 	}
  }
  
  
  function editItems(oid,actid,openStyle)
  {
		var ids=getSelectedID();;
	 	 if(ids==null||ids=='')
	 	 {
	 		alert('请先选择记录！');
	 		return ;
	 	}
	 	 if(ids.split(",").length>1){
	 		alert('只能对一条文档记录进行编辑,请先选择记录！');
	 		return ;
	 	 }
	oid=ids;
  	var urlStr=null;
 	if (oid && oid > 0)
 	{
 		urlStr=ctx+"/cms/document!adminEdit.action?id=" + ids + "&channelId="+channelId;
 	}
 	if(actid && actid!=null && actid!='')
 	   urlStr+="&activityid="+actid;
 	 if(openStyle && openStyle==1)
 	  window.open(urlStr);
 	 else 	   
 	  location=urlStr;
  }

  function editItem(oid,actid,openStyle)
  {
  	
  	var urlStr=null;
 	if (oid && oid > 0)
 	{
 		urlStr=ctx+"/cms/document!adminEdit.action?id=" + oid + "&channelId="+channelId;
 	}
 	if(actid && actid!=null && actid!='')
 	   urlStr+="&activityid="+actid;
 	 if(openStyle && openStyle==1)
 	  window.open(urlStr);
 	 else 	   
 	  location=urlStr;
  }
  
  
  function editItem1(oid,channelid,openStyle)
  {
  	
  	var urlStr=null;
 	if (oid && oid > 0)
 	{
 		urlStr=ctx+"/cms/document!adminEdit.action?id=" + oid + "&channelId="+channelid;
 	}
 	 
 	if(openStyle && openStyle==1)
 	  window.open(urlStr);
 	else 	   
 	  location=urlStr;
  }
  
  //查看信息，默认进入细览模板 openStyle--1为打开新窗口
  function viewItem(oid,actid,openStyle)
  {
  	
  	var urlStr=null;
 	if (oid && oid > 0)
 	{
 		urlStr=ctx+"/cms/docInfo!view.action?id=" + oid;
 	}
 	if(actid && actid!=null && actid!='')
 	   urlStr+="&activityid="+actid;
 	 if(openStyle && openStyle==1)
 	  window.open(urlStr);
 	 else
 	 location=urlStr;
  }  
  //标为过期
  function overdueItem(id)
  {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要把文档标为过期吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminOverdue.action?keys='+ids+'&channelId='+channelId+fromUrl;
 	}
  }
  //设置共享，isShare＝1设置共享，isShare＝0取消共享
   function sharedocItem(id,isShare)
  {
  	var conf = "";
  	if(isShare == 1)
  	{
  	conf = "您确定要把文档设置为共享吗？";
  	}
       else
       	{
       	conf = "您确定要取消文档的共享吗？";	
       		}
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	 //自动取Id
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm(conf))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminShareDoc.action?keys='+ids+'&isShare='+isShare+'&channelId='+channelId+fromUrl;
 	}
  }
  //共享库
  function sharedoc()
    {
    var retval = window.showModalDialog("document!shareTree.action", null, "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes");
    new Ajax.Request('document!createDocumentByShare.action',{method: 'get', parameters: 'channelId='+channelId+'&id='+retval, onComplete: shareResponse});
    }
  function shareResponse(originalRequest)
    {
    if(originalRequest.responseText == "true")
    {
     alert('创建成功！');
     location.href=location.href;
     }
   else
   {
     alert('创建失败！');
   }
    }
  
  
  
  
//发布文档
  function issueStaticItem(id)
  {
	  try{
		  var selectitem= document.getElementsByName("_selectitem");
		  var td;
			for(var i=0;i<selectitem.length;i++){
				if(selectitem[i].checked==true){
					td=selectitem[i].parentNode.parentNode.getElementsByTagName("td");
					var issued=td[td.length-1].innerText;
					if(issued!="已发"){
						alert("选择的记录中存在取消发布或者草稿的记录");
						return;
					}
				}
			}
	  }catch(e){
		  
	  }
   	var ids=id;
  	if(ids || ids==null || ids=='')
 	  ids=getSelectedID();
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要分发选择的文档吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminStaticIssue.action?keys='+ids+'&channelId='+channelId+fromUrl;
 	}
  }
  
  
  
  
  //发布文档
  function issueItem(id)
  {
	  try{
		  var selectitem= document.getElementsByName("_selectitem");
		  var td;
			for(var i=0;i<selectitem.length;i++){
				if(selectitem[i].checked==true){
					td=selectitem[i].parentNode.parentNode.getElementsByTagName("td");
					var issued=td[td.length-1].innerText;
					if(issued=="已发"){
						alert("选择的记录中存在已发布的记录");
						return;
					}
				}
			}
	  }catch(e){
		  
	  }
   	var ids=id;
  	if(ids || ids==null || ids=='')
 	  ids=getSelectedID();
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要发布选择的文档吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminIssue.action?keys='+ids+'&channelId='+channelId+fromUrl;
 	}
  }
  
  
  
  function isHeadlineItem(id)
  {
  	
 	 if(id==null||id=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要将选择的文档设置成头条吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminHeadline.action?keys='+id+'&channelId='+channelId+fromUrl;
 	}
  }
  function unIssueItem(id)
  {
	var selectitem= document.getElementsByName("_selectitem");
	var td;
	for(var i=0;i<selectitem.length;i++){
		if(selectitem[i].checked==true){
			td=selectitem[i].parentNode.parentNode.getElementsByTagName("td");
				var issued=td[td.length-1].innerText;
				if(issued=="取消发布"||issued=="草稿"){
					alert("选择的记录中存在取消发布或草稿的记录");
					return;
				}
		}
	}
   	var ids=id;
   
  	if(ids || ids==null || ids=='')
 	  ids=getSelectedID();
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要撤消发布选择的文档吗？"))
 	{
 		var fromUrl="";
 		if((location.href).indexOf('docInfo!list.action')>=0)
 		  fromUrl="&fromUrl="+encodeURI("docInfo!list.action?channelId=")+channelId;
 		location.href='document!adminUnIssue.action?keys='+ids+'&channelId='+channelId+fromUrl;
 	}
  }  	
  //--------------------------------流程处理---------------------------
  function accept(){
   myform.action="document!accept.action"+'?channelId='+channelId+'&id='+Id;
   setElementsDisabled(false);
   myform.submit();
  }
  function comple(){
   myform.action="document!comple.action"+'?channelId='+channelId+'&id='+Id;
   myform.submit();
  }
 //指定当前地址的基地方（不包括Action的方法名和参数）
 //指定流程处理的地址（包括需要的参数）
 var FLOW_TRANSCAT_URL='document!transact.action';
 function transact(){
  myform.action=FLOW_TRANSCAT_URL+'?channelId='+channelId+'&id='+Id;
  myform.submit();
 }
 
 //保存之前执行，供其他重载
 function beforsave(){
  var beforsavemethod="";
   if(BeforsaveExeJS!=null && BeforsaveExeJS.length>0){
   		for (i=0; i<BeforsaveExeJS.length; i++){
   			if(BeforsaveExeJS[i]!=null && BeforsaveExeJS[i]!=''){
   				beforsavemethod=eval(BeforsaveExeJS[i]);   
   				if(beforsavemethod!='succ'){
   					beforsavemethod='error';
   					break;
   				}
   			}
   			}
   	}
   if(BeforsaveExeJS!=null && BeforsaveExeJS.length>0){
	   if(beforsavemethod=="succ"){
		   return true;
	   }else{
		   return false;
	   }
   }else{
	   return true;
   }
 }
 function beforsave1(){
	   return true;
	 }
 //在调用保存之前调用内容
 function AddBeforsaveJS(jscontent){ 
 	if(jscontent==null || jscontent=='')
 	  return ;
  if(BeforsaveExeJS==null)
    BeforsaveExeJS=new Array;
  var blength=BeforsaveExeJS.length;
  BeforsaveExeJS[blength]=jscontent;
 	}
 // 在给定的URL中加入参数
function addParam(surl, name, val) {
  if (surl != null && name != null) {
    if (surl.indexOf('?') == -1) {
      // 原有的URL没有带参数
      surl = surl + '?' + name + '=' + val;
    }
    else {
      // 原有的URL带有参数
      surl = surl + '&' + name + '=' + val;
    }
  }
  return surl;
}
function closeItem()
{
	window.close();
}

function getHtmlContent(hurl){
 var tempobjdom = CreateXMLDOM() //创建DOM对象
 tempobjdom.async=true;
 tempobjdom.preserveWhiteSpace=true

 tempobjdom.load(hurl)

   if (CheckError(tempobjdom))
 { 	
  msg=GetXMLValue(tempobjdom.xml,"MESSAGEDESC")
  //alert(msg);
  if ((msg.length==0)||(msg==false))
  {  	
    return tempobjdom.xml;
  }else
   return "";
}
}
//禁止右键 
// for IE5+
function nocontextmenu(){
event.cancelBubble = true
event.returnValue = false;
return false;
}
//禁止右键 
// for all others
function norightclick(e){
if (window.Event){
if (e.which == 2 || e.which == 3)
return false;}
else
if (event.button == 2 || event.button == 3){
event.cancelBubble = true
event.returnValue = false;
return false;}
}
//全文搜索功能
 function searchAll(keywordid,frameid,templateName){
   if(!keywordid || keywordid=='')
     keywordid='searchKeyword';
   if(!frameid || frameid=='')//在指定iframe中显示搜索内容
     frameid='main_frame';
   if(!templateName || templateName=='')  
     templateName='searchPage';
   //'searchKeyword' 搜索关键字的输入框ID
   var keyword=document.getElementById(keywordid).value; 
   if(document.getElementById('searchtype') && document.getElementById('searchtype').value=='0'){
   	var channelid=tree1.getSelectedItemId();
   	if(channelid=='' || isNaN(channelid)){
   		alert('未指定频道，请先选择频道！');
   		return '';
   		}
   	if(keyword!='')
   	  keyword='channel:'+channelid +' AND '+keyword
   	}  
    //在指定iframe中打开显示搜索列表
  document.frames[frameid].location.href='index.action?siteId=1&templateName='+templateName+'&condition='+encodeURI(keyword);
 }

//邮件发送AJAX回调方法
function showResponse(originalRequest)
{
alert(originalRequest.responseText);
}
//实现select框，联动功能 updateSelect(sele,classname,methodname,obj){
function updateSelectByValue(objValue,seleObj){
	var obj=new Array;
	if(objValue){
	  obj[0]=objValue.value;	  
	}else
	  obj[0]='';	
	updateSelect(seleObj,'commonInfoService','getCommonInfos',obj);
  }
  //根据标签名称，获得对象
function getElementByName(ename){
 var names=document.getElementsByName(ename);
 if(names.length && names.length>0){
   return names[0];
 }else{
  return names;
  }
} 
//动态两级select联动
function setDynaSelects(oneLevelSelectName,twoLevelSelectName){
 getElementByName(oneLevelSelectName).onchange=function(){updateSelectByValue(getElementByName(oneLevelSelectName),getElementByName(twoLevelSelectName));};
 setTimeout("getElementByName('"+oneLevelSelectName+"').onchange()",100);
 //updateSelectByValue(getElementByName(oneLevelSelectName),getElementByName(twoLevelSelectName));
}
//设置只读
function setReadOnlyByName(ename){
	 
	if(ename.indexOf('domain.')<0)
	 ename='domain.'+ename;
	var sread=getElementByName(ename);
        
	if(sread){
	 if(sread.type=='hidden')
	  sread=document.getElementById(sread.id+"_CK");
	 if(sread){
	 if(sread.type=='checkbox')
	 sread.disabled =true;	
	 else
	   sread.readOnly =true;	
	}	   
	}		
 }