<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<html>
<head>
<title>Word例子</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
</head>
<script type="text/javascript">
    function test(errorString, exception)
    {
        alert(errorString);
    }
    var filename="/test/word.doc";
function ev_onLoadOcx() {
  //初始化Word控件
  //TANGER_OCX_OpenDoc(filename);
  TANGER_OCX_OBJ = document.all.item("TANGER_OCX");
  TANGER_OCX_OBJ.OpenFromURL (filename);
  TANGER_OCX_OBJ.IsNoCopy=false;
  TANGER_OCX_OBJ.Menubar=false;
  TANGER_OCX_OBJ.Titlebar=false;
  }
</script>
<body >
<form name="_item" method="post" action="">
<input type="button" vaue="save" onclick="save()">
<table width="89%" border="0">
  <tr>
    <td width="14%">&nbsp;</td>
    <td width="58%">&nbsp;</td>
    <td width="28%">&nbsp;</td>
  </tr> 
        <tr>
            <td align="right">模板说明：</td>          
          <td align="left"  colspan="2">
       			<div id="wwgrp_domain_description" class="wwgrp">
<div id="wwctrl_domain_description" class="wwctrl">
<input type="text" name="domain.description" size="60" value="" id="domain_description"/>
</div> </div>

          </td>
          </tr>     
  <tr>
    <td  colspan="3" height="300">TANGER_OCX<br>
<table width="98%" height="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <object id="TANGER_OCX" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404" codebase="/common/OfficeControl.cab#version=4,0,0,8" width="100%" height="100%">
		<param name="ProductCaption" value="AMWAY">
		<param name="ProductKey" value="446E3EBBFABC7719DBF702BA9BD10ED017342574">
	    <param name="BorderStyle" value="1">
	    <param name="BorderColor" value="14402205">
	    <param name="TitlebarColor" value="14402205">
	    <param name="TitlebarTextColor" value="0">
	    <param name="Caption" value="表单">
	    <param name="IsShowToolMenu" value="-1">
	    <param name="IsNoCopy" value="-1">
	    <SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
	  </object>
	  <script type="text/javascript">
	  ev_onLoadOcx();
	  </script>
	</td>
  </tr>
</table>
    </td>
  </tr>
</table>
</form>
</body>
</html>

