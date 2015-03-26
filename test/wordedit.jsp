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
<script language="javascript" type="text/javascript">
        <!--

        function show_word() {
                /*var str=window.location.search;
                var pos_start=str.indexOf("id")+3;
                if (pos_start == 2)
                return ;*/
        
                //var id = "http://localhost/Getdc.aspx?id=" + str.substring(pos_start);
                var id="http://www.demo.com:86/test/word.doc";
               // document.all.MyOffice.Open( id,false, "Word.Document");
               //document.all.MyOffice.CreateNew("Word.Document");
              document.all.FramerControl1.CreateNew("Word.Document");
        }

        // -->
        </script>

<body>
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
    <td  colspan="3">
<SCRIPT LANGUAGE=javascript FOR=FramerControl1 EVENT=NotifyCtrlReady>
<!--
FramerControl1_NotifyCtrlReady()
//-->
</SCRIPT>
<input type="hidden" name=wordPath value="http://www.demo.com:86/test/word.doc">     
<OBJECT classid='clsid:00460182-9E5E-11D5-B7C8-B8269041DD57' codeBase='dsoframer.ocx#version=2,2,1,2'  id=FramerControl1 style="LEFT: 0px; TOP: 0px; WIDTH: 100%; HEIGHT: 750px" VIEWASTEXT>
        </OBJECT>       
<!--OBJECT id="FramerControl1" name = "FramerControl1" style="LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 100%" 
 classid="clsid:00460182-9E5E-11D5-B7C8-B8269041DD57" codeBase=dsoframer.ocx#version=2,2,1,2' >    
</OBJECT-->
    </td>
  </tr>
</table>
</form>
</body>
</html>
<SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>
<!--

function command_onclick() {
        //eval(document.all.commandtext.value);
}

function FramerControl1_NotifyCtrlReady() {
        //alert("事件 NotifyCtrlReady 触发 ");
        if(document.all.wordPath.value=='')
         document.all.FramerControl1.CreateNew("Word.Document");
        else
         //打开服务器的文件 
          document.all.FramerControl1.Open(document.all.wordPath.value, true);
}

//-->
function save(){
document.all.FramerControl1.Save("http://www.demo.com:86/test/word.doc",false,"","");
}
</SCRIPT>