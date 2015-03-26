<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="cybercms" /> 
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}" /> 
<c:set var="imgPath" value="${ctx}/images/cms3/" /> 
<link href="${sytlePath}/style.css" rel="stylesheet" type="text/css" />
<title>内容主页</title>
</head>
<body>
<div id="main_right" >
  	<!-- 动态信息 -->
     <div id="main_right_right" >
	 <div id="main_right_bottom">
	    
	    <div class="main_right_center">
		   <div></div>
           <p><span><a href="#">更多</a></span><b>新闻</b></p>
		   <ul>
			 <li><a href="#">知识管理的创造CyberKM系统产品发布</a><p><span><a href="#">评论</a></span>(2007-10-15)</p></li>
			 <li ><a href="#">知识管理的创造CyberKM系统产品发布</a><p><span><a href="#">评论</a></span>(2007-10-15)</p></li>
			 <li id="line" ><a href="#">知识管理的创造CyberKM系统产品发布</a><p><span><a href="#">评论</a></span>(2007-10-15)</p>
			 </li>
		   </ul>  
	   </div>
	   <div class="main_right_center">
	      <div></div>
	      <p><span><a href="#">更多</a></span><b>投票</b></p>
		  <ul>
			<li><a href="#">知识管理的</a></li>
			<li id="line"><a href="#">知识管理的创造</a> </li>
		  </ul> 	
	   </div> 
	   <div class="main_right_center"> 
		  <p><span><a href="#">撰写</a></span><b>内部邮件</b></p>
		  <ul>
			 <li><a href="#">知识管理的</a><span id="other">我是新来的同事</span></li>
			 <li><a href="#">知识管理的创造</a> <span id="other">评论</span></li>
			 <li id="line"><a href="#">知识管理的创造</a> <span id="other">评论</span></li>
		  </ul> 
	   </div> 
	 </div>
  </div> 
  <!-- 今日计划 与 待办事宜 -->
  <div class="main_center_title_right_txt" id="main_center" >	 
	<div class="main_center_title">
       <div class="main_center_title_left">
       <div class="main_center_title_right">
	      <div class="main_center_title_right_txt">今日计划</div>
		  <div class="main_center_title_left_txt"><a href="#">更多>></a></div>
	   </div>
       </div>
    </div>
	<ul>
	   <li><span>[2007-10-15]</span><b><a href="#">事件1：产品质量检查报告-产品质量检查报告</a></b></li>
	   <li><span>[2007-10-15]</span><b><a href="#">事件2：产品质量检查报告-产品质量检查报告</a></b></li>
	   <li><span>[2007-10-15]</span><b><a href="#">事件3：订单、生产、发货</a></b></li>
    </ul>
	 <div style="height:36px;"></div>
	 <div class="main_center_title">
        <div class="main_center_title_left">
        <div class="main_center_title_right">
	       <div class="main_center_title_right_txt">待办事宜</div>
		   <div class="main_center_title_left_txt">共<span style="color:#f00;"><ww:property value="assigns.size" /></span>条 <a href="${ctx}/flow/assign.action">更多>></a></div>
	    </div>
        </div>
     </div>
	 <ul>
	  <ww:iterator value="assigns" status="index">
	  <ww:if test="(#index.index<10)">
		<li><b><!--a href="#">工作流：</a-->
		<img src="${imgPath}/inside_22.jpg"/><a href="${ctx}/flow/assign!edit.action?id=<ww:property value="oid"/>&activityid=<ww:property value="activityId"/>">&nbsp;<ww:property value="bizname"/></a>  （<ww:property value="createtime"/>）
		</b></li>
		</ww:if>
		</ww:iterator>
		<!--li><b><a href="#">工作流：</a>
		<img src="${imgPath}/inside_22.jpg"/>&nbsp;产品质量检查报告-产品质量检查报告  （2007-10-15 15：25：36）</b></li-->
    </ul>
    </div> 
  </div>  
</body>
</html>