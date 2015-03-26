<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>

<c:set var="title" value="频道访问文档统计" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>

<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<ww:head/>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/mootools.js" type="text/javascript"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
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

function select(){
	var channelId=document.getElementById("channelId").value;
	var startdate=document.getElementById("startdate").value;
	var enddate=document.getElementById("enddate").value;
	
	if(enddate!=""&&startdate!=""){
		if(enddate<startdate||enddate==startdate){
			alert("结束时间应该大于开始时间");
			return ;
		}
	}
	if(startdate!=""){
		if(isDate(startdate)==false){
			return ;
	   }
		//startdate=startdate+" 00:00:00";
	}
	if(enddate!=""){
		if(isDate(enddate)==false){
			return ;
	   }
	//	enddate=enddate+" 23:59:59";
	}
	
	var type=document.getElementById("visittype").value;
	if(type==3){
		location.href="${ctx}/base/visit!listbysite.action?channelId=" + channelId + "&startdate=" + startdate + "&enddate=" + enddate+"&visittype="+type;
	}else if(type==2){
		location.href="${ctx}/base/visit!listbychanel.action?channelId=" + channelId + "&startdate=" + startdate + "&enddate=" + enddate+"&visittype="+type;
	}else{
		location.href="${ctx}/base/visit!list.action?channelId=" + channelId + "&startdate=" + startdate + "&enddate=" + enddate+"&visittype="+type;
	}
}


//查询

function selectChoose(){
	var channelId=document.getElementById("channelId").value;
	var startdate=document.getElementById("startdate").value;
	var enddate=document.getElementById("enddate").value;
	
	if(enddate!=""&&startdate!=""){
		if(enddate<startdate||enddate==startdate){
			alert("结束时间应该大于开始时间");
			return ;
		}
	}
	if(startdate!=""){
		if(isDate(startdate)==false){
			return ;
	   }
		//startdate=startdate+" 00:00:00";
	}
	if(enddate!=""){
		if(isDate(enddate)==false){
			return ;
	   }
	//	enddate=enddate+" 23:59:59";
	}
	
	var type=document.getElementById("visittype").value;
	if(type==0){
		return;
	}
	
	if(type==3){
		location.href="${ctx}/base/visit!listbysite.action?channelId=" + channelId + "&startdate=" + startdate + "&enddate=" + enddate+"&visittype="+type;
	}else if(type==2){
		location.href="${ctx}/base/visit!listbychanel.action?channelId=" + channelId + "&startdate=" + startdate + "&enddate=" + enddate+"&visittype="+type;
	}else{
		location.href="${ctx}/base/visit!list.action?channelId=" + channelId + "&startdate=" + startdate + "&enddate=" + enddate+"&visittype="+type;
	}
}



//导出excel
function outupExc(){
	var chkID = document.all.tags('input');
	
	var j=0;
	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=='checkbox'){
			j++;
		}
	}
	if(j<=1){
		alert("没有要查找的数据");
		return;
	}
	var channelName=document.getElementById("channelName").value;
	var startdate=document.getElementById("startdate").value;
	var enddate=document.getElementById("enddate").value;
	document.getElementById("type").value=1;
	//alert(document.getElementById("type").value);
	if(enddate!=""&&startdate!=""){
		if(enddate<startdate||enddate==startdate){
			alert("结束时间应该大于开始时间");
			return ;
		}
	}
	if(channelName==""&&startdate==""&&enddate==""){
		 if(confirm('你要导出全部文档访问信息？ ')){
		//if(valid.validate()){
			 myform.submit();
		//}
		 }
	}else{
		 myform.submit();
	}

}


//返回
function goBack(){
	
	location.href="${ctx}/base/visit!selectList.action";
	
}

//选择频道
function selectChannel(){
	var typeIds = "";
	var url = "${ctx}/base/visit!selectChannelTree.action";
	showModalDialog(url,window, "dialogWidth=350px;dialogHeight=500px;status:no;scroll=no;");
}

//重置
function resetmethod(){
	document.getElementById("channelName").value="";
	document.getElementById("channelId").value="";
	document.getElementById("startdate").value="";
	document.getElementById("enddate").value="";
}


</script>
</head>
<body style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<form method="post" action="visit!outupExc.action" name="myform">
<ww:hidden name="siteId"></ww:hidden>
<ww:hidden name="channelId"></ww:hidden>
<ww:hidden name="type" id="type" value="1"></ww:hidden>
<div class="edit-header">
  <div class="edit-header-top" >
<ul id="lion-ul-a">

  
    <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:select();">
            <img src="${default_imagepath}/ico_124_currentWork.gif" class="ico_ab ico-015_ab" />
            <span>查询</span>
         </a>
    </li>
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:outupExc();">
            <img src="${default_imagepath}/ico_020_upcomingWork1.gif" class="ico_ab ico-015_ab" />
            <span>导出</span>
         </a>
    </li>
    <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:resetmethod();">
            <img src="${default_imagepath}/ico_156_recycleBinEmpty.gif" class="ico_ab ico-156_ab" />
            <span>重置</span>
         </a>
    </li>
   
</ul>
  </div>
  <div class="edit-header-bottom" style="margin-top:10px;">
    <ul>
      <li class="file" style="margin-right:4px;">
       <ww:textfield name="channelName" cssClass="required"  size="15" readonly="true"  disabled="true"></ww:textfield>
      </li>
      <li style="margin-right:10px;"><a href="javascript:selectChannel(${loginer.siteId})">选择频道</a></li>
    <li >访问时间 </li>
      <li style="margin-right:10px;">
<input type="text" name="startdate" value="<ww:date name="startdate" format="yyyy-MM-dd"/>" id="startdate" onclick="WdatePicker({el:$dp.$('startdate'),dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'enddate\')}'})" class="date"/>
&nbsp;&nbsp;</li>
      <li>
<input type="text" name="enddate" value="<ww:date name="enddate" format="yyyy-MM-dd"/>" id="enddate" onclick="WdatePicker({el:$dp.$('enddate'),dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startdate\')}'})" class="date">
</li>
      <li style="margin-left: 10px">统计类别 </li>
       <li ><select id="visittype" name="visittype"  onchange="selectChoose()">
       
       
         <c:if test="${visittype==0}"><option value="0" selected="selected">请选择类别</option></c:if>
         <c:if test="${visittype!=0}"><option value="0" >请选择类别</option></c:if>
     
       
       <c:if test="${visittype==1}"><option value="1" selected="selected">按文档统计</option></c:if>
        <c:if test="${visittype!=1}"><option value="1" >按文档统计</option></c:if>
     
         
      	  <option value="2">按频道统计</option>
          <option value="3">按站点统计</option>
       
      
       
       
       </select> </li>
    </ul>
  </div>
  
  
  
</div>



</form>


<div class="content">
  <div class="info-box">
  
 
<ec:table

items="items"
	          var="item" 
	          action="${ctx}/base/visit!list.action"
		  editable="false" batchUpdate="false" xlsFileName="站点访问文档列表.xls"
		   minColWidth="60"
		  generateScript="true" classic="true" listWidth="100%"
		  rowsDisplayed="10"  useAjax="false" 
		  tableId="${tableId}" 
		  showPrint="true"
		  resizeColWidth="true" filterable="false"
	          filterRowsCallback="limit"
	          sortRowsCallback="limit"
	          retrieveRowsCallback="limit"	
			  >
		<ec:row recordKey="${item}" rowId="rowid_${GLOBALROWCOUNT}">
        	   <ec:column property="_1" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" viewsDenied="xls" sortable="false" filterable="true" editable="false" width="4%" style="text-align:center">
            	         <input type="checkbox" name="_selectitem" value="${item}"  onclick='checkOne(allbox);'/>
                        </ec:column>	      		
			<ec:column property="_2" sortable="false" filterable="false" editable="false" title="序号" style="text-align:center" viewsAllowed="html" tipTitle="点击查看详细信息" value="<a href='#' onclick='editItem()'>${GLOBALROWCOUNT}</a>">						 
			</ec:column>			
			<ec:column property="siteName" title="站点名称" sortable="true" filterable="true" editable="false" style="text-align:center" editTemplate="ecs_t_input"/>			
			<ec:column property="channelName" title="频道名称" sortable="true" filterable="true" editable="false" style="text-align:center" editTemplate="ecs_t_input"/>
			<ec:column property="title" title="文档名称" sortable="true" filterable="true" editable="false" style="text-align:left" editTemplate="ecs_t_input">
				<c:choose> 
		    	<c:when test="${fn:length(item.title) > 60}">
		    		<span title='${item.title}'>
					<c:out value="${fn:substring(item.title, 0, 60)}..." />
					</span>
		    	</c:when> 
				<c:otherwise>
					<c:out value="${item.title}" /> 
			    </c:otherwise> 
			</c:choose>
			</ec:column>
			<ec:column property="visitNumber" title="访问量(人次)" sortable="true" filterable="true" editable="false" style="text-align:center" editTemplate="ecs_t_input"/>
											
		</ec:row>
	</ec:table>
	<textarea id="ecs_t_input" rows="" cols=""
	style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
			</textarea> 
			

</div></div>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
