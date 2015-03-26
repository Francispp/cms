<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="频道访问文档统计" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/mootools.js" type="text/javascript"></script>
<script src="${ctx}/common/datepicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
function jsTrim(str) 
{
return str.replace(/\ /g,"");
} 

function isDate(s){
	var datetime;
	var year,month,day;
	var gone,gtwo;
    var flag=true;
	if(jsTrim(s)!=""){
		datetime=jsTrim(s);
		if(datetime.length==10){
			year=datetime.substring(0,4);
			if(isNaN(year)==true){
			alert("请输入日期!格式为(yyyy-mm-dd) \n例(2001-01-01)！");
			flag=false;
			return flag;
			}
			gone=datetime.substring(4,5);
			month=datetime.substring(5,7);
			if(isNaN(month)==true){
			alert("请输入日期!格式为(yyyy-mm-dd) \n例(2001-01-01)！");
			flag=false;
			return flag;
			}
			gtwo=datetime.substring(7,8);
			day=datetime.substring(8,10);
			if(isNaN(day)==true){
			alert("请输入日期!格式为(yyyy-mm-dd) \n例(2001-01-01)！");
			flag=false;
			return flag;
			}
			if((gone=="-")&&(gtwo=="-")){
				if(month<1||month>12) {
				alert("月份必须在01和12之间!");
				flag=false;
				return flag;
				}
				if(day<1||day>31){
				alert("日期必须在01和31之间!");
				flag=false;
				return flag;
				}else{
					if(month==2){
						
						if(day>28){
						alert("二月份日期必须在01到28之间!");
						flag=false;
						return flag;
						}
						return flag;
					}
					if((month==4||month==6||month==9||month==11)&&(day>30)){
					alert("在四，六，九，十一月份 \n日期必须在01到30之间!");
					flag=false;
					return flag;
					}
				}
			}else{
			alert("请输入日期!格式为(yyyy-mm-dd) \n例(2001-01-01)");
			flag=false;
			return flag;
			}
			return flag;
		}
		else{
		alert("请输入日期!格式为(yyyy-mm-dd) \n例(2001-01-01)111111");
		flag=false;
		return flag;
		}
		return flag;
     }
	return flag;
}
//查询

function select(siteId){
	
	var channelId=document.getElementById("channelId").value;
	
	var startdate=document.getElementById("startdate").value;
	var enddate=document.getElementById("enddate").value;
	if(startdate!=""){
		if(isDate(startdate)==false){
			return ;
	   }
		
		startdate=startdate+" 00:00:00";
		
	}
	if(enddate!=""){
		
		if(isDate(enddate)==false){
			return ;
	   }
		enddate=enddate+" 23:59:59";
	}
	parent.document.frames['_list'].location.href="${ctx}/base/visit!list.action?siteId="+siteId+"&channelId="+channelId+"&startdate="+startdate+"&enddate="+enddate;
	//parent.select(siteId);
}
//导出excel
function outupExc(siteId){
	var siteName;
	if(siteId==null||siteId==''){
	    siteName=document.getElementById("siteName").value;
	}else{
		siteName=siteId;	
	}
	var channelName=document.getElementById("channelName").value;
	var startdate=document.getElementById("startdate").value;
	var enddate=document.getElementById("enddate").value;
	
	if(enddate!=""&&startdate!=""){
		if(enddate<startdate||enddate==startdate){
			alert("结束时间应该大于开始时间");
			return ;
		}
	}
	if(siteName==""&&channelName==""&&startdate==""&&enddate==""){
		 if(confirm('你要导出全部文档访问信息？ ')){
		//if(valid.validate()){
			 myform.submit();
		//}
		 }
	}else{
		 myform.submit();
	}

}

function goBack(siteId){
	
	location.href="${ctx}/base/visit!selectList.action?siteId="+siteId;
	
}
function selectChannel(siteId){
	var typeIds = "";
	var url = "${ctx}/base/visit!selectChannelTree.action?siteId="+siteId;
	showModalDialog(url,window, "dialogWidth=350px;dialogHeight=500px;status:no;scroll=no;");
}
</script>

<ww:head />
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<form method="post" action="visit!outupExc.action" name="myform">
<ww:hidden name="siteId"></ww:hidden>
<ww:hidden name="channelId"></ww:hidden>
<table>
<tr>
<c:if test="${siteId }==null||${siteId }==''">
<td class="inside_list">
站点名称：
</td>
<td>
<ww:select name="siteName" list="#request.siteList" headerKey="" headerValue="--请选择--" listKey="oid" listValue="sitename"></ww:select>
</td>
</c:if>

<td class="inside_list">
频道名称：
</td>
<td>
<ww:textfield name="channelName" cssClass="required"  size="15" readonly="true"  disabled="true"></ww:textfield>

</td>
<td>
<ul><li id="button"><a href="javascript:selectChannel(${siteId })">选择</a></li></ul>
</td>

<td class="inside_list">
访问时间：
</td>
<td>
<input type="text" name="startdate" value="<ww:date name="startdate" format="yyyy-MM-dd"/>" id="startdate" onclick="WdatePicker({el:$dp.$('startdate'),dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'enddate\')}'})" class="date"/>
</td>
<td class="inside_list">
至：
</td>
<td>
<input type="text" name="enddate" value="<ww:date name="enddate" format="yyyy-MM-dd"/>" id="enddate" onclick="WdatePicker({el:$dp.$('enddate'),dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startdate\')}'})" class="date">
</td>

<td>
<ul>
<li id="button"><a href="javascript:select(${siteId })">查询</a></li>
<li id="button"><a href="javascript:outupExc(${siteId })">导出</a></li>
<li id="button"><a href="javascript:goBack(${siteId })">刷新</a></li>
</ul>
</td>
</tr>

<tr>

</tr>

</table>
</form>



<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
