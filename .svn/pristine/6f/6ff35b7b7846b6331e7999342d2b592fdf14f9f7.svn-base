<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="流程监控" />
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
var processId='${flow.processId}';
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
function setProcessOpt(opt){
   var obj=new Array;
   obj[0]='{userinfo}';
   obj[1]=processId;
   obj[2]=''+opt;
   var optName='';
   if(opt=='suspend'){
    optName='挂起';
   }else if(opt=='resume'){
    optName='恢复';
   }else if(opt=='terminate'){
    optName='终止';
   }else if(opt=='abort'){
    optName='中断';
   }else if(opt=='delete'){
    optName='删除';
   }else if(opt=='reevaluate'){
    optName='重新配任务';
   }else if(opt=='deadlines'){
    optName='最终期限';
   }else if(opt=='limits'){
    optName='期限';
   }
   if(confirm('您确定要'+optName+'当前的流程吗？')){
       ExecuteService("if(reply.getResult()=='true'){alert('操作成功!');location.reload();}else {alert('操作失败!');}",'processMonitorService','setProcessOpt',obj);   
   }
}
//管理当前流程的活动
function manageActivitys(){
 var dialogURL="processMonitor!manageActivity.action?processId="+processId;
 var dialog = window.showModalDialog(dialogURL, window, "dialogWidth:500px; dialogHeight:480px; center:yes; help:no; resizable:no; status:no") ;

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
 <li id="button"><a href="javascript:setProcessOpt('suspend');">挂起</a></li>
 <li id="button"><a href="javascript:setProcessOpt('resume');">恢复</a></li>
 <li id="button"><a href="javascript:setProcessOpt('terminate');">终止</a></li>
 <li id="button"><a href="javascript:setProcessOpt('abort');">中断</a></li>
 <li id="button"><a href="javascript:setProcessOpt('delete');">删除</a></li>
 <li id="button1"><a href="javascript:setProcessOpt('reevaluate');">重分配</a></li>
 <li id="button1"><a href="javascript:setProcessOpt('deadlines');">最终期限</a></li>
 <li id="button1"><a href="javascript:setProcessOpt('limits');">期限</a></li>
 <li id="button1"><a href="javascript:manageActivitys();">活动管理</a></li>
  </ul>
  </div>
</div>

<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify>
<form method="post" action="" name="myform">
<ww:hidden  name="pageStyle" id="pageStyle"/>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="20%" align="right">流程名称：</td>
          <td width="30%" align="left">
            <ww:textfield name="flow.processName" readonly="true" />
           </td>          
          <td align="right"  width="20%">流程实例ID：</td>
          <td align="left"  width="30%>
          <ww:textfield name="flow.processId" readonly="true" />
          </td>
          </tr>
        <tr>
          <td align="right">创建时间：</td>
          <td align="left">
           <ww:textfield name="flow.createTime" readonly="true" />
          </td>          
          <td align="right">流程状态：</td>
          <td align="left">
        <ww:textfield name="flow.state" readonly="true" />
          </td>
          </tr>                              
      </table></td>
    </tr>
        <tr>
      <td width="100%" align="center" class="view_content_td">
      <iframe src='${ctx}/workflow/webflow/mindex.jsp?activeActivityId=<ww:property value="flow.activityId"/>&flowdefmgrname=<ww:property value="flow.processDefmgrName"/>'
	frameborder=0 width=98% height=400> </iframe> 
      </td>
    </tr>
    <tr>
      <td align="center" class="view_content_td1">
<div id="nav">
 <div>
 <ul>
    <div id="titel_txt">流程变量</div>
  </ul>
  </div>
</div>             
<TABLE width="98%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#999999" style="font-size:12;"  class="none">
  <TBODY>
   <TR height=24 bgcolor="#E1E1E1">    
    <TD width="10%">
      <DIV align=center>序号</DIV></TD>
    <TD width="30%">
      <DIV align=center>变量名</DIV></TD>
    <TD width="40%" >
      <DIV align=center>值</DIV></TD>   
    <TD width="20%" >
      <DIV align=center>操作</DIV></TD>        
	  </TR>
<c:forEach var="item" items="${items}" varStatus="index">
  <TR height=20  bgcolor="#ffffff"  onmouseover="mouseoverColor(this,'#EEFFFF')" onmouseout="mouseoutColor(this,'#FFFFFF')" >    
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><script>document.write(${index.index}+1);</script></TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;">${item}</TD>
    <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><ww:textfield name="flow.ExtAttributes['${item}']" size="40" id="${item}_value"/></TD>
 <TD align=center style="WORD-BREAK:break-all;table-layput:fixed;"><input type="button" value="更新" onclick="javascript:updateData('${item}');"></TD>
 </TR>
</c:forEach>
</TBODY>
</TABLE>      
      <!--table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="21%" align="left" valign="top">备注：</td>
          <td width="79%" align="left">
         
          </td>
        </tr>
      </table--></div>
      </td>
    </tr>
    </table>
  <iframe src='${ctx}/flow/track.action?processid=<ww:property value="flow.processId"/>'
	frameborder=0 width=98% height=100> </iframe>    
    </form>
</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
//var valid = new Validation('myform',{immediate:true});
<c:if test="${isEdit!=true}">
 //setElementsDisabled(true);
</c:if>
</script>