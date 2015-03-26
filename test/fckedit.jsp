<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<html>
<head>
<title>FCK例子</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
</head>
<script type="text/javascript">
    function test(errorString, exception)
    {
        alert(errorString);
    }

</script>
<body>
<form name="_item" method="post" action="">
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
    <td  colspan="3"><textarea name="domain.body" cols="" rows="" id="bodyField"></textarea>
<script type="text/javascript">	
 var fckEditor = new FCKeditor("domain.body", "100%", "400px", "CmsEdit");
  fckEditor.BasePath = "/common/fckeditor/";
  fckEditor.ReplaceTextarea();
</script>    
    </td>
  </tr>
</table>
</form>
</body>
</html>
