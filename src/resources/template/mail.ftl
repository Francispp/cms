<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统通知</title>
</head>

<body>
<table width="50%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
<tr><td>
<strong>系统通知,请不要回复。</strong>
<hr align="left"  width="100%" size="1" noshade color="#cc0000" >
<table class="adminlist" width="300" align="left" style="margin-top:20px;margin-left:50px">
    <tr>
        <th class="title" colspan='2'>***列表</th>
    </tr>
	<#list content as poi>
    <tr>
        <td>${poi.poiid}</td><td>${poi.chiname}</td><td>${poi.address}</td>
    </tr>
    </#list>
</table>                   
</td></tr>
</table>
</body>

</html>