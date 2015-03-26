
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="回复留言" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css"></link>
<link href="/styles/cms3/leaveWord.css" rel="stylesheet" type="text/css"></link>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
 if($F("content") == ""){
    alert("回复内容不能为空！");
    $("content").focus();
   	return false;
 } 
 myform.submit();
} 

//邮件回复
function emailto(){
	var title = "邮件回复";
	var querystr = "/component/leaveword!sendEmail.action?id=${leaveWord.oid}";
	window.showModalDialog('/frame.jsp?title=' + title,querystr,'font-size:9pt;dialogWidth:505px;dialogHeight:400px;status:no;scroll=no;');
} 

function delete1(id){ 
  window.location = "/component/answerleaveword!deleteAnswer.action?id="+id;
}

function goBack(){
	window.location = "/component/leaveword!list.action";
}

 function issueItem(status,topicid,id){  
	 location.href='/component/leaveword!issueItem.action?id='+topicid+'&status='+status+"&keys="+id;
}
  
</script> 
<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
<!-- style="background-color:#dfedef;" -->	
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	<!-- 操作栏 -->
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul> 
					<li id="button"><a href="javascript:emailto()">邮件</a></li>
					<li id="button"><a href="javascript:goBack()">返回</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<!-- 页面主要内容 -->
	
	<tr><td style="padding-left:2px;padding-right:1px;">
<table width="100%" border="0" align="center" style="margin-top:-3px;" bgcolor="#dfedef" height="100%">
	<tr>
		<td align="center" valign="top">
	 <table style="margin-top: -3px" border="0" width="100%" bgcolor="#ffffff" align="center" height="100%">
            <tbody>
                <tr>
                    <td valign="top" align="center"> 
                    <table  cellspacing="0" cellpadding="0" class="mframe"><tr> 
					  <td class="rw">
						<div class="fbart fbarb"><b>${leaveWord.title}</b></div> 
						<div class="fbart"><dfn>发布于：${leaveWord.leaveTime}</dfn> 作者：${leaveWord.userName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 联系方式：${leaveWord.link} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; E-mail：${leaveWord.email}</div> 
						<table cellspacing="0" cellpadding="0" class="mtxt">
							<tr><td > <div class="msgfont">${leaveWord.content}</div> </td></tr>
						</table>
					  </td> </tr> <tr>
					  <td class="rb">
						<div class="fbarb">
						   <ul class="ulleft">  
						 		<li>是否发布：<c:choose><c:when test="${leaveWord.status == 1}">是</c:when><c:otherwise>否</c:otherwise></c:choose></li>  
						   </ul> 
						   <ul>  
						   		<li><a href="javascript:;" class="gr" onclick="issueItem(1,${leaveWord.oid},${leaveWord.oid})">发布</a></li>  
							  	<li><a href="javascript:;" class="br nob" onclick="issueItem(0,${leaveWord.oid},${leaveWord.oid})">取消发布</a></li>  
						   </ul> 
						</div>
					  </td></tr> 
					  
					</table>  
                    <ww:iterator status="status" id="answer" value="#request.leaveWord.answers">
                   		<table  cellspacing="0" cellpadding="0" class="mframe"><tr> 
						  <td class="rw"> 
							<div class="fbart"><dfn>回复于：${answer.answerTime}</dfn> 作者：${answer.userName}</div> 
							<table cellspacing="0" cellpadding="0" class="mtxt">
								<tr><td > <div class="msgfont">${answer.content}</div> </td></tr>
							</table>
						  </td> </tr> <tr>
						  <td class="rb">
							<div class="fbarb"> 
							  	<ul>  
									<li><a href="javascript:;" class="dr nob" onclick="delete1(${answer.oid})">删除</a></li>  
								</ul> 
							</div> 
						  </td></tr> 
						</table>  
                   	</ww:iterator>    
                    <form method="post" name="myform" action="/component/answerleaveword!saveOrUpdate.action">  
						<ww:hidden id="topicid" name="domain.topicid" value="%{leaveWord.oid}"></ww:hidden>
						<table  cellspacing="0" cellpadding="0" class="mframe"><tr> 
						  <td class="rw">
							<table cellspacing="0" cellpadding="0" class="mtxt">
								<tr><td align="center"> <ww:textarea id="content" name="domain.content" cssStyle="width:98%; height:150px; font-size:14px;max-length-240 "></ww:textarea></td></tr>
							</table>
						  </td> </tr> <tr>
						  <td  align="right" >
						  	<div class="fbarb">
						   	<ul>
							<li><a href="javascript:;" onclick="save()" class="dr">回复</a></li>
							<li><a href="javascript:;" onclick="reset()" class="dr nob">清空</a></li>
							</ul> 
							</div> 
							</td></tr>  
						</table>  
                    </form>
               	 	</td>
				</tr>
			</tbody>
		</table> 
		</td>
	</tr>
</table>
</td></tr></table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%> 

</body>
</html>  