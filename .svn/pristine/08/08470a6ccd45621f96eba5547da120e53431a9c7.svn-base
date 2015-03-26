<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="问卷调查" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<script src="${ctx}/common/prototype/prototype.js" type="text/javascript"></script>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js" ></script>

<script type="text/javascript">
function save(){
 if(valid.validate())
  myform.submit();
 else
  dyniframesizeforall("main_frame");
}
function goBack(){
 location.href='/survey/questionnaire!list.action?pageStyle=<ww:property value="pageStyle" />';
}
function showhideframe(title,querystr,wx,wy){
    querystr.title = title;
    return window.showModalDialog(querystr,title,'dialogWidth:'+wx+'px;dialogHeight:'+wy+'px;status:no;scroll:1;help:no;edge:sunken;center:yes;');
}
function editQuestionnaire(qnid){
	 global_ab.fn.popWindow.addPopWindow('/survey/questionnaire!editArgs.action?id=' + qnid, '620px', '450px', false);
}
function editQuestion(qnid,qid){
	 global_ab.fn.popWindow.addPopWindow('/survey/question!edit.action?id='+qid+'&questionnaireId=' + qnid, '600px', '420px', false);
}

function addQuestions(qnid){
	var rtnval = showhideframe('添加问卷问题','${ctx}/survey/question!listby.action?questionnaireId='+qnid, 600, 600);
	if(rtnval.indexOf('@')!=-1){
		var rtnvar=rtnval.split("@");
		
		var url = '/survey/question!editmore.action';
		  new Ajax.Request(url, { method:'post', parameters: {keys:rtnvar[0], questionnaireId:rtnvar[1]},
				onSuccess: function(transport){alert('操作成功！');dyniframesizeforall("main_frame");location.reload();}
		  });
	}

}
//复制
function copyQuestion(qid){
  var rtnval = showhideframe('复制问题','${ctx}/survey/question!copy.action?id='+qid, 600, 600);
  if(rtnval == 'success')
    location.reload();
}
//预览
function lookSurvey(qnid)
{
	window.open('/survey/questionnaire!survey.action?id=' + qnid + "",'newwindow','toolbar=yes,location=yes,scrollbars=yes,menubar=yes,resizable=yes,top=0,left=0');
}

//发布
function publishing(qnid)
{
  var url = '/survey/questionnaire!publishing.action';
  new Ajax.Request(url, { method:'post', parameters: {id:qnid, 'domain.activated':'1'},
		onSuccess: function(transport){alert('发布成功！');location.reload();}
  });
}
//停止发布
function CancelPublishing(qnid)
{
  var url = '/survey/questionnaire!publishing.action';
  new Ajax.Request(url, { method:'post', parameters: {id:qnid, 'domain.activated':'-1'},
    onSuccess: function(transport){alert('已停止发布问卷！');location.reload();}
  });
}

//删除
function deleteItem(qid, title){
	if(!title)
		title = '您确定要删除选择的记录吗？';
	else{
		title = '您确定要删除 "' +title + '" 吗？';
	}
	if(confirm(title)){
		location.href='/survey/question!delete.action?keys='+qid + '&questionnaireId=' + ${domain.id};
	}
}
</script>
<ww:head />
</head>
<body class="pw_ab" style="margin:0px;padding:0px;overflow-y:auto;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<!-- 操作栏 -->
	<div class="system-header">
        <div class="edit-header-lion">
        <ul id="lion-ul">
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:editQuestionnaire('${domain.id }');"><img alt="" class="ico_ab ico-0143_ab" src="/images/cybercms/ico_143_settings.gif"></img> <span>问卷参数设置</span> </a></li>
            <ww:if test="domain.activated eq '1'">
            <li class="artEdit-btn_ab artEdit-btn-w4letters_ab"><a class="artEdit-btn-in_ab" href="javascript:CancelPublishing('${domain.id }');"><img alt="" class="ico_ab ico-095_ab" src="/images/cybercms/ico_095_denied.gif"></img> <span>停止发布</span> </a></li>
            </ww:if>
			<ww:else>
				<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:publishing('${domain.id }');"><img alt="" class="ico_ab ico-171_ab" src="/images/cybercms/ico_171_feed.gif"></img> <span>发布问卷</span> </a></li>
			</ww:else>
			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:lookSurvey('${domain.id }');"><img alt="" class="ico_ab ico-006_ab" src="/images/cybercms/ico_006_world.gif"></img> <span>预览问卷</span> </a></li>
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:editQuestion('${domain.id }','');" title="为此问卷新建一个问题"><img alt="" class="ico_ab ico-013_ab" src="/images/cybercms/ico_013_plus.gif"></img><span>添加新问题</span> </a></li>
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:addQuestions('${domain.id }');" title="从问题库中选择未分配的问题"><img alt="" class="ico_ab ico-013_ab" src="/images/cybercms/ico_013_plus.gif"></img><span>添加问卷问题</span> </a></li>
			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="${ctx }/survey/questionnaire!result.action?id=${domain.id }" target="_blank"><img alt="" class="ico_ab ico-055_ab" src="/images/cybercms/ico_055_ruledPage.gif"></img><span>查看统计结果</span> </a></li>
			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:refreshIframe();"><img alt="" class="ico_ab ico-019_ab" src="/images/cybercms/ico_019_refresh.gif"></img> <span>刷新</span> </a></li>
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:goBack();"><img alt="" class="ico_ab ico-118_ab" src="/images/cybercms/ico_118_signOut.gif"></img> <span>返回</span> </a></li>
            <li class="fn-clear"></li>
        </ul>
        </div>
	</div>
	<table width="100%" border="0" align="center"
			cellpadding="0" cellspacing="0" bgcolor="#dfedef">

			<tr>
				<td align="center" valign="top">
				<div id="questionnairesubject"
					style="padding:3px 15px 5px 15px;text-align:center;letter-spacing:3px;font-size:22px;font-family:'黑体';color:#000000;font-weight:bold;font-style:normal;line-height:22px;">
				${domain.name }</div>

				<div id="c" style="padding:0px 3px 13px 25px;text-align:center">
				所属站点：${domain.siteName } &nbsp;&nbsp;开始时间：${domain.publishDate
				}&nbsp;&nbsp;结束时间：${domain.cutoffDate }</div>

				<div id="welcome"
					style="height:55px;padding:0px 25px 25px 25px;text-align:left;word-break:break-all;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${domain.welcome }</div>

				<ww:set name="qtypes" value="{'短答','单选','多选','单选短答','多选短答','自由长答'}" />
				<c:forEach items="${domain.questions}" var="item" varStatus="sta">
					<table width="98%" cellspacing="1" cellpadding="3" border="0"
						bgcolor="#d8e7fe" align="center">
						<tbody>
							<tr bgcolor="#e9f1fd">
								<td bgcolor="#e9f1fd" background="../../images/title_bj_s.jpg"
									onMouseOut="this.style.background='#E9F1FD'"
									onMouseOver="this.style.background='#FDEED1'" id="abc">
								<table width="100%" cellspacing="0" cellpadding="0" border="0">
									<tbody>
										<tr>
											<td width="80%" align="left"><font color="#000000">&nbsp;<b>${sta.count
											}. ${item.content }</b></font></td>
											<td width="20%" align="right">
											<table height="27" width="222" cellspacing="0"
												cellpadding="0" border="0" id="edit_text_id">
												<tbody>
													<tr>
														<td width="3" valign="middle">&nbsp;</td>
														<td width="100" valign="middle">[${qtypes[item.type]}]</td>
														<td width="20" valign="middle"><a
															href="javascript:copyQuestion('${item.id }');"><img
															height="13" width="15" border="0" alt="复制"
															src="${ctx }/images/cms/copy.gif"></a></td>
														<td width="20" valign="middle"><a
															href="javascript:editQuestion('${domain.id }','${item.id }');"><img
															height="13" width="15" border="0" alt="编辑"
															src="${ctx }/images/cms/edit.gif"></a></td>
														<td width="20" valign="middle"><a
															href="javascript:deleteItem('${item.id }','${item.content }');"><img
															height="13" width="15" border="0" alt="删除"
															src="${ctx }/images/cms/delete.gif"></a></td>
														<td width="" valign="middle"></td>
													</tr>
												</tbody>
											</table>
											</td>
										</tr>
									</tbody>
								</table>
								</td>
							</tr>
							<tr>
								<td valign="top" bgcolor="#ffffff">
								<table width="100%" cellspacing="0" cellpadding="0" border="0">
									<tbody>
										<tr>
											<td width="26" align="left"></td>
											<td height="26" align="left"><c:forEach
												items="${item.selectOptions}" var="opt" varStatus="s">

												<c:if test="${item.type eq '1'}">
													<input type="radio" value="${opt.id }"
														name="optAnswers[${sta.index}]" id="opt_${opt.id }" />
													<span class="option">${opt.content}</span>
												</c:if>

												<c:if test="${item.type eq '3' && !s.last}">

													<input type="radio" value="${opt.id }"
														name="optAnswers[${sta.index}]" id="opt_${opt.id }" />
													<span class="option">${opt.content}</span>
												</c:if>

												<c:if test="${item.type eq '2' }">
													<input type="checkbox" value="${opt.id }"
														name="optAnswers[${sta.index}]" id="opt_${opt.id }" />
													<span class="option">${opt.content}</span>
												</c:if>

												<c:if test="${item.type eq '4' && !s.last}">
													<input type="checkbox" value="${opt.id }"
														name="optAnswers[${sta.index}]" id="opt_${opt.id }" />
													<span class="option">${opt.content}</span>
												</c:if>

												<c:if test="${item.type eq '0'}">
													<span class="option">${opt.content}</span>
													<input type="text" name="txtAnswers[${sta.index}]"
														id="opt_${opt.id }" />
												</c:if>

												<c:if test="${item.type eq '5'}">
													<span class="option">${opt.content}</span>
													<textarea rows="2" cols="10"
														name="txtAnswers[${sta.index}]" id="opt_${opt.id }"></textarea>
												</c:if>
												<c:if test="${item.optDirect ne 0 && !s.last}">
													<br />
												</c:if>


											</c:forEach> <c:if test="${(item.type eq '3' || item.type eq '4')}">
						其他：<input type="text" name="txtAnswers[${sta.index}]"
													id="opt_${opt.id }" />
												<input type="hidden" name="txtAnswersv[${sta.index}]"
													id="optv_${opt.id }" value="${opt.id }" />
											</c:if></td>
										</tr>
									</tbody>
								</table>
								</td>
							</tr>
						</tbody>
					</table>
				</c:forEach><%-- 问题结束 --%>

				<table width="98%" cellspacing="1" cellpadding="3" border="0"
					bgcolor="#d8e7fe" align="center">
					<tbody>
						<tr bgcolor="#e9f1fd">
							<td bgcolor="#e9f1fd" background="../../images/title_bj_s.jpg">
									&nbsp;<br/>
							</td>
						</tr>
					</tbody>
				</table>
				</td>
			</tr>

		</table>
	<div style="height:50px;">
        <div class="edit-header-lion">
        <ul id="lion-ul">
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:editQuestionnaire('${domain.id }');"><img alt="" class="ico_ab ico-0143_ab" src="/images/cybercms/ico_143_settings.gif"></img> <span>问卷参数设置</span> </a></li>
            <ww:if test="domain.activated eq '1'">
            <li class="artEdit-btn_ab artEdit-btn-w4letters_ab"><a class="artEdit-btn-in_ab" href="javascript:CancelPublishing('${domain.id }');"><img alt="" class="ico_ab ico-095_ab" src="/images/cybercms/ico_095_denied.gif"></img> <span>停止发布</span> </a></li>
            </ww:if>
			<ww:else>
				<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:publishing('${domain.id }');"><img alt="" class="ico_ab ico-171_ab" src="/images/cybercms/ico_171_feed.gif"></img> <span>发布问卷</span> </a></li>
			</ww:else>
			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:lookSurvey('${domain.id }');"><img alt="" class="ico_ab ico-006_ab" src="/images/cybercms/ico_006_world.gif"></img> <span>预览问卷</span> </a></li>
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:editQuestion('${domain.id }','');"><img alt="" class="ico_ab ico-013_ab" src="/images/cybercms/ico_013_plus.gif"></img> <span>添加新问题</span> </a></li>
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:addQuestions('${domain.id }');"><img alt="" class="ico_ab ico-013_ab" src="/images/cybercms/ico_013_plus.gif"></img> <span>添加问卷问题</span> </a></li>
			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="${ctx }/survey/questionnaire!result.action?id=${domain.id }" target="_blank"><img alt="" class="ico_ab ico-055_ab" src="/images/cybercms/ico_055_ruledPage.gif"></img><span>查看统计结果</span> </a></li>
			<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:refreshIframe();"><img alt="" class="ico_ab ico-019_ab" src="/images/cybercms/ico_019_refresh.gif"></img> <span>刷新</span> </a></li>
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:goBack();"><img alt="" class="ico_ab ico-118_ab" src="/images/cybercms/ico_118_signOut.gif"></img> <span>返回</span> </a></li>
            <li class="fn-clear"></li>
        </ul>
        </div>
	</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

