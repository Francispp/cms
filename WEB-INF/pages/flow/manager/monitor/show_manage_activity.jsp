<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="流程活动管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/buffalo.inc"%>
<script type="text/javascript">
function save(){
 if(valid.validate())
   myform.submit();
}
function goBack(){
 location.href='dept.action?pageStyle=<ww:property value="pageStyle" />';
}
var processId='${processId}';
//更新流程数据
function updateData(varName){
   var obj=new Array;
   obj[0]= '{userinfo}';
   obj[1]=processId;
   obj[2]=varName;
   obj[3]=document.getElementById(varName+'_value').value+'';
   ExecuteService("if(reply.getResult()=='true'){alert('更新变量成功!');}else {alert('更新变量失败!');location.reload();}",'processMonitorService','setFlowData',obj);
}
//流程状态操作
function setActivityOpt(opt){
   if(document.getElementById('activityObject').value==''){
    alert('请选择操作的活动对象！');
   }
   var obj=new Array;
   obj[0]='{userinfo}';
   obj[1]=processId;
   obj[2]=document.getElementById('activityObject').value;
   obj[3]=''+opt;
   var optName='';
   if(opt=='start'){
    optName='开始';
   }else if(opt=='suspend'){
    optName='挂起';
   }else if(opt=='resume'){
    optName='恢复';
   }else if(opt=='complete'){
    optName='完成';
   }else if(opt=='terminate'){
    optName='终止';
   }else if(opt=='abort'){
    optName='中断';
   }else if(opt=='delete'){
    optName='删除';
   }
   if(confirm('您确定要'+optName+'当前的流程吗？')){
       ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else {alert('操作失败!');}",'processMonitorService','setActivityOpt',obj);   
   }
}
//管理当前流程的活动
function manageActivitys(){
 var dialogURL="";
 var dialog = window.showModalDialog(dialogURL, window, "dialogWidth:373px; dialogHeight:480px; center:yes; help:no; resizable:no; status:no") ;

}
</script>
<script language="javascript">
//****************************
//	鼠标移动到对象上显示的颜色
//	obj：对象（例：this）
//	color：颜色码（例：#ffffff）
//****************************
function mouseoverColor(obj,color){
	obj.bgColor=color;	
}

//****************************
//	鼠标移出对象时显示的颜色
//	obj：对象（例：this）
//	color：颜色码（例：#ffffff）
//****************************
function mouseoutColor(obj,color){
	obj.bgColor=color;	
}
</script>
<ww:head/>
</head>
<body>
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>
<!-- 操作栏 -->
<div id="nav">
 <div>
 <ul>
 <li id="button"><a href="javascript:setActivityOpt('start');">开始</a></li>
 <li id="button"><a href="javascript:setActivityOpt('suspend');">挂起</a></li>
 <li id="button"><a href="javascript:setActivityOpt('resume');">恢复</a></li>
 <li id="button"><a href="javascript:setActivityOpt('complete');">完成</a></li>
 <li id="button"><a href="javascript:setActivityOpt('terminate');">终止</a></li>
 <li id="button"><a href="javascript:setActivityOpt('abort');">中断</a></li>
 <!--li id="button1"><a href="javascript:setActivityOpt('reevaluate');">重分配</a></li-->
  </ul>
  </div>
</div>

<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
<ww:hidden  name="pageStyle" id="pageStyle"/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <select name="activityObject" name="activityObject" size="10" style="width:100% "  onChange="setActivityState(this)">
       <c:forEach var="item" items="${items}" varStatus="index">
       <option value="${item.activityId}">${item.activityName}</option>
       </c:forEach>       
      </select>
<input type="text" name="activityState" id='activityState' style="width:100% " readonly="true">
 <!--select name="activityState" id='activityState' size="1" style="width:100% " disabled>
       <option value=""></option>
       <c:forEach var="item" items="${items}" varStatus="index">
       <option value="${item.activityId}">${item.state}</option>
       </c:forEach>       
      </select-->     
 <script type="text/javascript">
     var obj=new Array;
     <c:forEach var="item" items="${items}" varStatus="index">
     obj['${item.activityId}']= '${item.state}';
     </c:forEach>
     function setActivityState(_this){
     
      document.getElementById('activityState').value=obj[_this.value];
     }
 </script>
   </td>
    </tr>      
    </table> 
</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
