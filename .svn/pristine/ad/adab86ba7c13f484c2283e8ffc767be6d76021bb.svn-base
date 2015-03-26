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
</head>
<body>
<div class="hrCommain">
	<div class="training_titel">在线调查</div>
			<ww:hidden name="domain.questionnaireId" value="%{domain.id}" id="domain_questionnaireId"/>
			<div class="training_titel2">${domain.name }</div>
			<div class="training_info">
				<p class="training_font1">参与人数：[<font color="red">${domain.total }</font>] 人</p>
				<p>${domain.welcome }</p>
				<p class="training_font1">活动评选调查结果</p>
			</div>
			<div class="training_info">
			<c:forEach items="${domain.questions}" var="item" varStatus="sta">
				<c:set var="answer_total" value="0"/>
				<ul required="${item.required}">
					<p>${sta.index+1}. <c:if test="${item.required==1}"><span class="training_font2">*</span></c:if>${item.content }
					(<c:if test="${item.type=='0'}">短答</c:if>
			        <c:if test="${item.type=='1'}">单选</c:if>
			        <c:if test="${item.type=='2'}">多选</c:if>
			        <c:if test="${item.type=='3'}">单选短答</c:if>
			        <c:if test="${item.type=='4'}">多选短答</c:if>
			        <c:if test="${item.type=='5'}">自由长答</c:if>)
					</p>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" class="training_result_table">
                            <tr>
                                <th style="width:auto;">选项 </th>
                                <th style="width:78px;">小计</th>
                                <th style="width:275px;">比例</th>
                            </tr>
					<c:forEach items="${item.selectOptions}" var="opt" varStatus="optstatus">
							<tr>
                                <td>${opt.content}<c:if test="${(item.type eq '3' && optstatus.last) || (item.type eq '4' && optstatus.last)}">其它</c:if><c:if test="${item.type eq '0' || item.type eq '5'}">回答人数</c:if></td>
                                <td>
                                    ${opt.totalTickets }
									<c:set var="answer_total" value="${opt.totalTickets + answer_total}"/>
                                </td>
                                <td>
                                    <!-- percent0代表为0% -->
									<ww:if test="%{opt.totalTickets==null or opt.totalTickets==0}">
										<div class="percent percent0"></div>
									</ww:if>
                                    <ww:elseif test="%{domain.total==opt.totalTickets}">
										<div class="percent percent100"></div>
                                    </ww:elseif>
									<ww:else>
										<div class="percent percentX"><span style="width:${opt.totalTickets/domain.total*205 }px;"></span></div>
									</ww:else>
									<fmt:formatNumber value="${domain.total eq 0 ? 0 : (opt.totalTickets+0.0)/domain.total}" type="percent"/>
                                </td>
                            </tr>
					</c:forEach>
					<c:if test="${domain.total>answer_total}">
						<tr>
							<td>不作答人数</td>
							<td>${domain.total - answer_total}</td>
							<td>
								<ww:if test="%{answer_total==0}">
									<div class="percent percent100"></div>
		                        </ww:if>
								<ww:else>
									<div class="percent percentX"><span style="width:${(domain.total - answer_total)/domain.total*205 }px;"></span></div>
								</ww:else>
								<fmt:formatNumber value="${domain.total eq 0 ? 0 : (domain.total - answer_total)/domain.total}" type="percent"/>
							</td>
						</tr>
					</c:if>
					<tr>
						<td>
							<span class="training_font4">本题有效填写人数</span>
						</td>
						<td>
							<span class="training_font4"><ww:if test="${item.type=='2' || item.type=='4'}">${domain.total}</ww:if><ww:else>${answer_total }</ww:else></span>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</c:forEach>
			<div style="text-align:center">
				<input type="button" value="返回" class="training_btn2" id="ResultBack"/>
			</div>
			<div class="clear"></div>
		</div>
</div>
	</body>
<script type="text/javascript">
jQuery("#ResultBack").click(function(){window.close();});
</script>
</html>