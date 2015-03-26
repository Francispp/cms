<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="任务管理列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/heritrix.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
  <form name="frmNew" method="post" action="${ctx}/crawl/jobs!modules.action">
            <ww:hidden  name="domain.profile" id="domain.profile"/>
            <ww:hidden  name="domain.jobUID" id="domain.jobUID"/>
            <ww:hidden  name="domain.recovery" id="domain.recovery"/>
            <b>
                按默认的方式生成抓取任务
            
            </b>
            <p>            
            <table>
                <tr>
                    <td>
                        任务名称 :
                    </td>
                    <td>
                     <ww:textfield name="domain.metaName" cssStyle="width: 440px"/> 
                    </td>
                </tr>
                <tr>
                    <td>
                        描述:
                    </td>
                    <td>
                    <ww:textfield name="domain.jobDescription" cssStyle="width: 440px"/> 
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                         抓取站点URL:
                    </td>
                    <td>
                    <ww:textarea name="domain.seeds" cssStyle="width: 440px" rows="8"/>
                    </td>
                </tr>
                <tr>
                <td colspan="2" align="right">
<input type="button" value="提交"
   onClick="document.frmNew.action='${ctx}/crawl/jobs!modules.action';document.frmNew.submit()"/>

                </td>
                </tr>
            </table>
        </form>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
