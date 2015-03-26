<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<html>
<head>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script src="${ctx}/common/frame_js/select.js"  type="text/javascript"></script>
<%@ include file="/common/js.inc"%>

<script>
	function toDataInfo(resid){
		var myform=document.forms[0];		
		with(myform){
			action="PersonsEdit.action?resourceid="+resid;
			submit();
		}
		
	}
	
	function delDataInfo(){
		var selid=selectID();
		var myform=document.forms[0];		
		with(myform){
			action="ResourceAction.do?method=delStepInfo&resourceid="+selid;
			submit();
		}
		
	}
	
//多选的记录ID
function selectID(){
	var strRECORDID = new String();
	var chkID = document.all.tags('input');
	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).name=="RECORDID" && chkID(i).checked){
			strRECORDID = strRECORDID + chkID(i).value + ",";
		}
	}
	if (strRECORDID.lastIndexOf(",") == (strRECORDID.length - 1)) {
		strRECORDID = strRECORDID.slice(0,strRECORDID.length-1);
	}
	return strRECORDID;
}	

function pauseall(){
	location="../quartz/secheduler.action?method=pauseall";
}

function resumeall(){
	location="../quartz/secheduler.action?method=resumeall";
}
	
</script>

</head>
<body>
<center>定时器调度</center>
<form name="SechedulerAdmin" method="post">

<div align="center">

<TABLE cellSpacing=1 cellPadding=5 width="100%" border=1>
  <TBODY>
  <TR>
    <TD align=right bgColor=#ffffff colSpan=10 height=0>
      <A href="javascript:pauseall()">暂停所有</A>
      <A href="javascript:resumeall();">恢复所有</A> 
    </TD>
  </TR>
  <TR>
  <!--
    <TD align=middle width="1%" bgColor=#e7e7e7 height=20>
    <INPUT style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" onclick=checkAll(this) type=checkbox name=chkall> 
    </TD>
    -->
    <TD align=middle width="4%" bgColor=#e7e7e7>序号</TD>
    <TD width=%10 bgColor=#e7e7e7>
      <DIV align=center>JobName</DIV>
    </TD>
    <TD bgColor=#e7e7e7 width=%10>
      <DIV align=center>JobClass</DIV>
    </TD>
    <TD bgColor=#e7e7e7 width=%20>
      <DIV align=center>JobDescription</DIV>
    </TD>

        
    <TD bgColor=#e7e7e7 width=%20>
      <DIV align=center>JobGroup</DIV>
    </TD>
        
    <TD bgColor=#e7e7e7 width=%10>
      <DIV align=center>TriggerState</DIV>
    </TD>
<!--
    <TD bgColor=#e7e7e7 width=%10>
      <DIV align=center>TriggerName</DIV>
    </TD>
    <TD bgColor=#e7e7e7 width=%15>
      <DIV align=center>TriggerGroup</DIV>
    </TD>            
-->

<ww:iterator value="lsJobs" status="index">  

  <TR <ww:if test="#index.odd == true ">bgcolor="#CCFFCC"</ww:if> >
  <!--
    <TD align=middle bgColor=#ffffff height=20>
    <INPUT style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" onclick=checkOne(chkall) type=checkbox value='<ww:property value="lsJobs[#index.index].jobName"/>' name=RECORDID> 
    </TD>
    <TD align=middle bgColor=#ffffff>
    <A href="javascript:toDataInfo('<ww:property value="lsJobs[#index.index].jobName"/>');">
     <ww:property value="#index.count"/>
    </A>
    </TD>
    -->
    <TD align=middle>
     <ww:property value="#index.count"/>
    </TD>    
    <TD align=middle width=%10 height=20>
    	<ww:property value="lsJobs[#index.index].jobName"/>&nbsp;
    </TD>
    <TD align=middle height=20>
	    <ww:property value="lsJobs[#index.index].jobClass"/>&nbsp;
    </TD>
    <TD align=middle height=20>
	    <ww:property value="lsJobs[#index.index].jobDescription"/>&nbsp; 
    </TD>
    <TD align=middle height=20>
	    <ww:property value="lsJobs[#index.index].jobGroup"/>&nbsp;
    </TD>
    <TD align=middle height=20  <ww:if test="lsJobs[#index.index].state==1">bgcolor=red</ww:if> >
	    <ww:property value="lsJobs[#index.index].triggerState"/>&nbsp;
    </TD>      
    <!--
		<TD align=middle height=20  <ww:if test="method=='pauseall'">bgcolor=red</ww:if> >
	    <ww:property value="lsJobs[#index.index].triggerState"/>&nbsp;
    </TD>    
    <TD align=middle bgColor=#ffffff height=20>
	    <ww:property value="lsJobs[#index.index].triggerName"/>
    </TD>
    <TD align=middle bgColor=#ffffff height=20>
	    <ww:property value="lsJobs[#index.index].triggerGroup"/>
    </TD>    
    -->      
  </TR>

</ww:iterator>

 </TBODY>
</TABLE>

	</div>

</form>
      
</body>
</html>