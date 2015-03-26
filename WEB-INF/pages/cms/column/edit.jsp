<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="栏目编辑" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">		
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='column.action?pageStyle=<ww:property value="pageStyle" />';
}
function chageType(){
	var typeValue=myform.resouretype.value;	
	switch(typeValue){
		case '0':			
			document.all.ifprotectedTR.style.display="none";
			document.all.protectedurlTR.style.display="none";
			break;
		case '1':			
			document.all.ifprotectedTR.style.display="";
			document.all.protectedurlTR.style.display="";
			break;
		default:
			break;		
	}
}
</script>
<ww:head/>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
 <%@ include file="/common/messages.inc"%>
<!-- 页面标题 -->
<c:if test="${pageStyle==1}">
<div id="titel">
  <div id="titel_left">
     <div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
  </div>
</div>
</c:if>
<!-- 操作功能按钮条 -->
<div id="nav">
 <div>
 <ul>
  <c:if test="${isEdit==true}"> 
  <li id="button"><a href="javascript:save()">保存</a></li>
  </c:if>
  <c:if test="${pageStyle==1}"> 
  <li id="button"><a href="javascript:goBack()">返回</a></li>
  </c:if>
  </ul>
  </div>
</div>
<!-- 页面主要内容 -->
<div id="view_scroll_content" align=justify style="height:auto;">
<form method="post" action="column!saveOrUpdate.action" name="myform">
<ww:hidden  name="domain.id" id="domain.id"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="view_content_table">
    <tr>
      <td width="50%" align="center" class="view_content_td">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
            <td align="left">名称：</td>
            <td align="left">
            <ww:textfield name="domain.name"  cssClass="required max-length-40"/>
            </td>
    </tr>
     <tr>
            <td width="23%"  align="left">编码：</td>
            <td width="77%"  align="left">
             <ww:textfield name="domain.columnCode"  cssClass="required max-length-40"/>
            </td>
    </tr>
     <tr>
            <td align="left">所属站点：</td>
            <td align="left">           
            <select name="domain.site.oid" id="domain_site_oid">
            <c:forEach var="site" items="${application.cms_sites}" varStatus="status"> 
             <option value="${site.oid}">${site.sitename}</option>
            </c:forEach>  
            </select>            
            </td>
    </tr>    
     <tr>            
            <td align="left">上级栏目：</td>
            <td align="left">
             <ww:hidden name="domain.parent.id"/>
             <ww:textfield name="domain.parent.name" readonly="true" />
            </td>
    </tr>
     <tr>            
            <td align="left">栏目地址：</td>
            <td align="left">
             <ww:textfield name="domain.path"   cssClass="required max-length-200"/>
            </td>
    </tr>   
     <tr>            
            <td align="left">排序号：</td>
            <td align="left">
             <ww:textfield name="domain.sortOrder"  cssClass="validate-integer max-value-1000000"/>
            </td>
    </tr>
    <tr>            
            <td align="left">状态：</td>
            <td align="left">
            <ww:select name="domain.state" list="trueOfFalseMap1" />
            </td>
    </tr>       
       
      </table></td>
    </tr>
    <tr>
      <td align="center" class="view_content_td1">
      <table width="70%" border="0" align="center" cellpadding="0" cellspacing="10">
        <tr>
          <td width="21%" align="left" valign="top">备注：</td>
          <td width="79%" align="left">
          <ww:textarea name="domain.remark" cols="40" rows="6"/>
          </td>
        </tr>
      </table>
      </td>
    </tr>                            
      </table>
      </td>
    </tr>
    </table>
    </form>
</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
 var valid = new Validation('myform',{immediate:true});
	<c:if test="${isEdit!=true}">
		setElementsDisabled(true);
	</c:if>
 myform.domain_site_oid.value ='<ww:property value="domain.site.oid" />';
 <c:if test="${isEdit!=true}">
  setElementsDisabled(true);
 </c:if>	
</script>