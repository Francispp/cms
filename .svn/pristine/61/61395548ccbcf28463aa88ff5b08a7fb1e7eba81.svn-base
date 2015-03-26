<%@ page language="java" contentType="text/html; charset=UTF-8"%> 
<%@ page import="javax.script.ScriptEngine"%>
<%@ page import="org.apache.commons.lang.ObjectUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.cyberway.core.utils.ServiceLocator"%>
<%@ page import="com.cyberway.cms.document.service.DocumentCommonService"%>
<%@ page import="com.cyberway.cms.site.service.SiteManagerService"%>
<%@ page import="com.cyberway.cms.channel.service.ChannelManagerService"%>
<%@ page import="com.cyberway.cms.log.service.LogManagerService"%>
<%@ page import="com.cyberway.common.lucene.SearchService"%>
<%@ page import="com.cyberway.common.collections.PagedCollection"%>
<%@ page import="com.cyberway.common.collections.PagedCollectionImpl"%>
<%@ page import="com.cyberway.core.dao.support.Page"%>
<%@ page import="com.cyberway.core.dao.support.CriteriaSetup"%>
<%@ page import="com.cyberway.staticer.gather.VariableCollector"%>
<%@ page import="com.cyberway.cms.tags.components.DocumentIterator.Limit"%>
<%@ page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@ page import="com.cyberway.core.dao.support.CriteriaSetup"%>
<%@ page import="com.cyberway.cms.domain.Channel"%>
<%@ page import="com.cyberway.cms.component.webuser.domain.WebUser"%>
<%@ page import="com.cyberway.cms.component.webuser.service.WebUserService"%>
<%@ page import="com.cyberway.core.utils.ServiceLocator"%>
<%@ page import="com.cyberway.common.domain.CoreCommonInfo"%>
<%@ page import="com.cyberway.common.commoninfo.service.CommonInfoService"%>
<%@ page import="com.cyberway.common.domain.CoreArea"%>
<%@ page import="com.cyberway.common.area.service.AreaService"%>
<%@ page import="com.cyberway.core.objects.Loginer"%>
<%@ page import="com.cyberway.cms.component.webuser.domain.WebUser"%>
<%@ page import="com.cyberway.cms.exposed.*"%> 
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.hibernate.criterion.Restrictions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ec" uri="ecside" %>
<%@ taglib prefix="fck" uri="http://fckeditor.net/tags-fckeditor" %>
<%@ taglib prefix="ww" uri="/WEB-INF/tld/struts-tags.tld" %>
<%@ taglib prefix="cms" uri="cms" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="portalcode" value="kms" />
<c:set var="style" value="/${session.LOCALE_STYLE}" />
<c:if test="${empty session.LOCALE_STYLE or session.LOCALE_STYLE eq 'index'}">
 <c:set var="style" value="" />
</c:if>
<c:set var="sytlePath" value="${ctx}/styles/${portalcode}${style}" />
<c:set var="default_style" value="${sytlePath}/style.css" />
<c:set var="default_imagepath" value="${ctx}/images/${portalcode}${style}" />
<%
com.cyberway.common.service.ScriptService scriptService = (com.cyberway.common.service.ScriptService)com.cyberway.core.utils.ServiceLocator.getBean ("scriptService");
ScriptEngine scriptEngine = scriptService.getEngine ();
scriptEngine.put ("$REQUEST", request);
scriptEngine.put ("$PAGECONTEXT", pageContext);
scriptEngine.put ("$RESPONSE", response);
scriptEngine.put ("$SESSION", session);
//scriptEngine.put ("$WEBUSER", loginer);
scriptEngine.put ("$CHANNEL", new com.cyberway.cms.exposed.ChannelServiceJsUtil());
scriptEngine.put ("$DOCMENT", new com.cyberway.cms.exposed.DocumentServiceJsUtil());
scriptEngine.put ("$WEB", new com.cyberway.cms.exposed.WebJsUtil(pageContext));
scriptEngine.put("$HTML", new com.cyberway.cms.exposed.HTMLJsUtil(pageContext));
scriptEngine.put("$TOOLS", new com.cyberway.cms.exposed.ToolsJsUtil());
request.setAttribute ("scriptEngine", scriptEngine);
String BASEURL=request.getRequestURL().toString().substring(0, request.getRequestURL().toString().length()-request.getRequestURI().length());
%>
