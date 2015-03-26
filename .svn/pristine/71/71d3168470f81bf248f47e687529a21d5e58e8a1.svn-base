<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="站点管理" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>

<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/dwr/interface/SiteManagerService.js" type="text/javascript"></script>
<%@ include file="/common/validation.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-w617_ab {
	width:600px;
	height:430px;
	
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
if (${isCollectReload} == 1) {//页面采集区的菜单树是否需要刷新
	window.top.switchToTree.window.location.reload();
	window.top.main_frame.window.location.reload();
}

function save(){
if(valid.validate()){
	var stname = document.getElementById("domain_sitename").value;
	if(stname.indexOf("&") != -1){
   	  alert("站点名称不能含有“&”字符");
   	  document.getElementById("domain_sitename").focus();
   	  return ;
	}
	
	var sitehttp = document.getElementById("domain.sitehttp").value;
	var domainNames = document.getElementById("domain.domainNames").value;
	if(sitehttp == domainNames){
		alert("站点HTTP和站点扩展域名不可以一样!");
		document.getElementById("domain.sitehttp").focus();
		return;
	}
	var id = ${domain.oid} + "";
	SiteManagerService.realmNameIsUnique(id, sitehttp, domainNames, {
    	callback:
		function (args) {
			if (args == 1) {
				alert("站点HTTP重复,必须唯一!");
				document.getElementById("domain.sitehttp").focus();
				return;	
			}else if (args == 2) {
				alert("站点扩展域名重复,必须唯一!");
				document.getElementById("domain.domainNames").focus();
				return;	
			}
  			myform.submit();
		},
    	errorHandler:
		function(mess){}
	});
 }
}
function goBack(){
 location.href='site.action?pageStyle=<ww:property value="pageStyle" />';
}
function sitePublic(siteId){
  var obj=new Array;
  obj[0]= siteId;
  if(confirm('您确定要重新生成当前站点下所有模板吗？')){
   ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');}",'templateService','sitePublish',obj);
  }
}
  function inputData(){
	 var title="选择导入的文件";
	 actionURL="${ctx}/cms/site!importSiteData.action";
	 uploadXML(title,actionURL);
	 }
 function exportData(){
	  location.href="site!exportSiteData.action?id=${id}";
	 }
	 
	 
	   
  /*生成全站模板静态页面*/	 
  function publishSite(siteId){
	    var obj=new Array; 
		  obj[0]= siteId; 
  	 ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');}",
  			 'siteManagerService',
  			 'siteTemplate',
  			 obj);
  } 
  /*全站资源分发*/	 
  function siteDistribution(siteId){
		  if(confirm('确定要分发全站资源吗？')){
			  var obj=new Array;
			  obj[0]= siteId+""; 
			  obj[1]= 0+""; 
			  ExecuteService("if(reply.getResult()=='true'){alert('全站分发成功!')}else{ alert('操作失败!');}",
			  			 'siteManagerService',
			  			 'siteDistribution',
			  			 obj);
		  }

  }
//撤销站点分发
  function deleteSiteDistribution(siteId){

			  if(confirm('确定要撤销分发全站资源吗？')){
				  var obj=new Array;
				  obj[0]= siteId+""; 
				  ExecuteService("if(reply.getResult()=='true'){alert('撤销分发成功!')}else{ alert('操作失败!');}",
				  			 'siteManagerService',
				  			 'deleteSiteDistribution',
				  			 obj);
			  }
			 
	     
  } 
  
  
  //加载下拉框
  function loadpage(){
	  	  var flag=0;
 	      SiteManagerService.getListCmsSite(1,function(data){  
 		  window.parent.top.document.getElementById("currentsite").options.length = 0;//初始化select
 		  window.parent.top.document.getElementById("currentsite").selectedIndex = 0;//默认选中第一个
		  var siteId="${loginer.siteId}";
		  if(siteId=="0"){
			 	window.parent.top.document.getElementById("currentsite").options.add(new Option("cms内容管理系统","0"));
			 	window.parent.top.document.getElementById("currentsite").options.length = 1;//初始化select
		  }
     	  for(var i=0;i<data.length;i++){
     			 window.parent.top.document.getElementById("currentsite").options.add(new Option(data[i].sitename, data[i].oid));
     			 
     	  }
     	 window.parent.top.document.getElementById("currentsite").value=siteId;
     	  if(siteId!="0"){
     		var  currentsite = parent.window.top.document.getElementById("currentsite");
     		currentsite.options.add(new Option("cms内容管理系统", "0"));
     	  }
     	  window.parent.frames['main_frame'].location.href  = "/cms/site!list.action?_="+(new Date()).getTime();
     	  global_ab.fn.popWindow.removePopWindow(false);
      });  
 	     
 	 
}
  
</script>
<ww:head/>
</head>
<body class="pw_ab"  style="overflow:hidden;padding:0px;margin:0px;border:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

 <form method="post" action="site!saveOrUpdate.action" name="myform" class="pw-borderOut_ab pw-w617_ab">
<ww:hidden  name="pageStyle" id="pageStyle"/>
<ww:hidden  name="domain.oid" id="domain.oid"/>
<ww:hidden  name="domain.template.id" id="domain.template.id"/>
		
	<div class="pw-borderIn_ab" style="height:418px;">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="/images/cybercms/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span><ww:if test="domain.oid!=null">编辑站点</ww:if><ww:else>新建站点</ww:else></span>
            </div>
            <img src="/images/cybercms/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="overflow-y:auto;height:390px;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                    	<span>站点名称： </span>
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                    	</span>
                         <ww:hidden  name="domain.site.oid" id="domain_site_oid"/>           
            			<ww:textfield name="domain.sitename" size="30" cssClass="required max-length-30 textField_ab textField-w150_ab"  />
                    </label>
                </li>
                
              <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>是否发布：</span>
                        </span>
                       <ww:select name="domain.ispublished" list="trueOfFalseMap1"  cssClass="select_ab select-w50_ab"/>
                    </label> 
                </li>
                
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>动态加载站点：</span>
                        </span>
                        
                    	<ww:select name="domain.isdynamic" list="trueOfFalseMap1"  cssClass="select_ab select-w50_ab"/>
                      
                    </label>
                </li>
               
              <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>开放方式：</span>
                        </span>
                        <ww:select name="domain.isLogined" list="trueOfFalseMap2" id="isLogined"  cssClass="select_ab select-w60_ab"/>
                    </label>
                </li>
                
               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span >暂时屏蔽访问：</span>
                        </span>
                        <ww:select name="domain.pingBiFangWen" list="trueOfFalseMap3" cssClass="select_ab select-w50_ab"/>
                    </label>
                </li> 
                
				<li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                    	<span>备注： </span>
                    	</span>
                    	<span class="lab-pw-tit_ab2">
                    	<ww:textarea name="domain.remark"  cssClass="max-length-120 textField_ab textField-w150_ab" rows="6" cssStyle="height:80px;"/>
                    	</span>
                    </label>
                </li>
            </ul>
            
            <ul class="pw-con-input_ab pw-con-input-ex_ab" style="height:300px;">
            
             <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>站点HTTP：</span>
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                        <ww:hidden name="domain.parent.id"/>
            			<ww:textfield name="domain.sitehttp"  size="30" cssClass="required max-length-120 textField_ab textField-w150_ab"/>
                    </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>端口：</span>
                    	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
                        </span>
                        <ww:textfield name="domain.siteport"  size="30" cssClass="required validate-integer max-value-100000 textField_ab textField-w150_ab"/>
							 </label>
                </li>
                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>是否开放WAP：</span>
                        </span>
                        <ww:select name="domain.isSustainWap" list="trueOfFalseMap4" cssClass="select_ab select-w50_ab"/>
                       
                    </label>
                </li>
                
                
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span >扩展域名：</span>
                        </span>
                       <ww:textfield name="domain.domainNames" cssClass="max-length-80 textField_ab textField-w200_ab" />
                    </label>
                </li> 
                <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span id="loginUrlText">前台登录地址：</span>
                        </span>
                        <span id="loginUrlInput" ><ww:textfield name="domain.loginUrl" cssClass="max-length-80 textField_ab textField-w150_ab"/></span>
                    </label>
                </li>
              
                  <li class="pw-con-input-item_ab" style="position:absolute;">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span >屏蔽访问URL：</span>
                        </span>
                         <ww:textfield name="domain.pingBiDiZhi" size="35" cssClass="max-length-80 textField_ab textField-w150_ab"/>
                    	</label>
                </li>
                
             
            </ul>
            <c:if test="${not empty actionMessages}">
	   			 <c:forEach var="err" items="${actionMessages}">
	        		<script>loadpage();</script>
        		</c:forEach> 
			  </c:if>
            <div class="fn-clear"></div>
            <div class="pw-con-btns_ab">
            	<input type="button" class="pwSubmit_ab" value="" onclick="javascript:save()"/>
              	<input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
            </div>
       
       
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
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
<c:if test="${isNew==true && domain.oid!=null}">
// top.setMenuFrameUrl('${ctx}/cms/site.action');
// parent.location="site!tabbar.action?id=${domain.oid}";
</c:if>

</script>

<script type="text/javascript">
	/*生成静态页面*/	 
  function addHttp(index){
  	
	  if(value!=null&&value!=''){
		  var site=document.getElementById("domain.oid").value;
		    var obj=new Array;
			  obj[0]= site;
			  obj[1]= value;
	  	 ExecuteService("if(reply.getResult()=='true'){}else{ alert('已经存在!');}",'siteHttpService','setHttpOfSite',obj);
  	 }
  } 
    function delHttp(index){
	
	  var site=document.getElementById("domain.oid").value;
	  if(site==null||site==''){
	  return ;
	  }
	  var value=document.getElementsByName("siteHttpsValue")[index].value;
	    var obj=new Array;
		  obj[0]= site;
		  obj[1]= value; 
  	 ExecuteService("if(reply.getResult()=='true'){}else{ alert('删除失败');}",'siteHttpService','rmoveHttpOfSite',obj);
  }

  function full(index){
  var https=document.getElementsByName("siteHttpsValue");
  	https[index-1].value='';
  }
  function checkHttps(value){
   var site=document.getElementById("domain.oid").value;
  	 var obj=new Array;
	 obj[0]= value;
	 obj[1]=site;
   	 ExecuteService("if(reply.getResult()=='true'){}else{alert('已经存在的HTTP');document.getElementsByName('domain.sitehttp')[0].value=='';}",'siteHttpService','checkSiteOfHttp',obj);
  }
</script>


<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>


var sectionRowIndex=null;
function addopt(){
  var length=$('optbody').rows.length;
  var newrow = $('optbody').insertRow();
  newrow.insertCell(0).innerHTML='&nbsp;'+(length+1);
  newrow.insertCell(1).innerHTML='<input name="siteHttpsValue" type="text" onfocus="selectrow(this)" onblur="checkHttp(this,'+(length+1)+')" style="border:0px;width:50%;">';
}

function delopt(){
  if(sectionRowIndex==null)return;
//  delHttp(sectionRowIndex);
  $('optbody').deleteRow(sectionRowIndex);
  sectionRowIndex=null;
  updateNum();
}
function updateNum(){
  var rows = $('optbody').rows;
  for(var i=0;i<rows.length;i++){
    rows[i].cells[0].innerHTML='&nbsp;'+(i+1);
  }
}
function selectrow(inpt){
deselectrow();
  var r=inpt.parentNode.parentNode;
  //r.style.backgroundColor='#3089CA';
  sectionRowIndex=r.rowIndex-2;
}
function deselectrow(inpt){
  //var r=inpt.parentNode.parentNode;
  if(sectionRowIndex==null) return;
  var r=$('optbody').rows[sectionRowIndex];
  r.style.backgroundColor='';
  sectionRowIndex=null;
}
//function setSectionRowIndex(){sectionRowIndex=currentRowIndex;}
//function resetSectionRowIndex(){sectionRowIndex=null;}
function moveSelectedRow(target){
  if(sectionRowIndex==null)return;
  $('optbody').moveRow(sectionRowIndex,sectionRowIndex+target);
  sectionRowIndex=sectionRowIndex+target;
}
</script>