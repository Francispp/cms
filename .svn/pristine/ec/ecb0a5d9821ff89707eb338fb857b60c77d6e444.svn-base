<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<title>在线调查</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
	<link href="/styles/question.css" rel="stylesheet"/>
	<script src="/common/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="/scripts/hr.page.js" type="text/javascript"></script>
</head>
<body>
<div class="hrCommain">
	<form method="post" action="${ctx}/survey/answer!saveOrUpdate.action" name="myform" id="myform">
	<ww:hidden name="session_survey" value="%{session_survey }"/>
	<ww:hidden name="domain.id" value=""/> 
	<ww:hidden name="publishDate" value="%{domain.publishDate}"/>
	<ww:hidden name="cutoffDate" value="%{domain.cutoffDate}"/>
	<ww:hidden name="domain.questionnaireId" value="%{domain.id}"/>
	<ww:hidden name="tourl" value="%{domain.turnToPage}"/>
	<div class="training_titel">在线调查</div>
			<div class="training_titel2">${domain.name }</div>
			<div class="training_info">
				<p class="training_font1">参与人数：[<font color="red">${domain.total }</font>] 人</p>
				<p>${domain.welcome }</p>
				<p class="training_font1">所有问题无对错之分，请按实际情况填写！</p>
			</div>
			<div class="training_line"></div>
			<div class="training_survey">
			<c:forEach items="${domain.questions}" var="item" varStatus="sta">
				<input type="hidden" name="questionIds[${sta.index}]" value="${item.id }"/>
				<ul class="question_ul" required="${item.required}" name="${sta.index+1}">
					<h1>${sta.index+1}. <c:if test="${item.required==1}"><span class="training_font2">*</span></c:if>${item.content }</h1>
					<c:forEach items="${item.selectOptions}" var="opt" varStatus="optstatus">
						<c:if test="${item.type eq '1'}">
							<li><input type="radio" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }"/>${opt.content}</li>
						</c:if>
						<c:if test="${item.type eq '3' && !optstatus.last}">
							<li><input type="radio" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }"/>${opt.content}</li>
						</c:if>
						<c:if test="${item.type eq '3' && optstatus.last}">
							<li><input type="radio" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }" />其它<input type="text" name="txtAnswers_${opt.id }" id="opt_${opt.id }_at"/></li>
						</c:if>
						<c:if test="${item.type eq '2' }">
							<li><input type="checkbox" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }" />${opt.content}</li>
						</c:if>
						<c:if test="${item.type eq '4' && !optstatus.last}">
							<li><input type="checkbox" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }" />${opt.content}</li>
						</c:if>
						<c:if test="${item.type eq '4' && optstatus.last}">
							<li><input type="checkbox" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }" />其它<input type="text" name="txtAnswers_${opt.id }" id="opt_${opt.id }_at"/></li>
						</c:if>
						<c:if test="${item.type eq '0'}">
							<li><input type="hidden" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }" />${opt.content}回答：<input type="text" name="txtAnswers_${opt.id }" id="opt_${opt.id }_at" /></li>
						</c:if>
						<c:if test="${item.type eq '5'}">
							<li style="height:85px;"><input type="hidden" value="${opt.id }" name="optAnswers[${sta.index}]" id="opt_${opt.id }" />${opt.content}回答：<br/>
							<textarea rows="3" cols="50" name="txtAnswers_${opt.id }" id="opt_${opt.id }_at"></textarea>
						</c:if>
					</c:forEach>
				</ul>
			</c:forEach>
			<div class="btn">
				<input type="button" value="提交" class="training_btn1" id="SubmitServey" />
				<input type="button" value="查看结果" class="training_btn2" id="ServeyResult" style="margin-right:20px;" />
				<input type="button" value="返回" class="training_btn2" id="ServeyBack" />
			</div>
			<div class="clear"></div>
		</div>
	</form>
</div>
</body>
<script type="text/javascript">
jQuery("#SubmitServey").click(function(){alert("很抱歉，此页面仅用于预览，无法提交！");})
jQuery("#ServeyResult").click(function(){alert("很抱歉，此页面仅用于预览，无法查看结果！");});
jQuery("#ServeyBack").click(function(){window.close();});
</script>
</html>