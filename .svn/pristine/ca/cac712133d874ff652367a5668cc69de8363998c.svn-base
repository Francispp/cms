<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="选择" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/buffalo.inc"%>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
</head>
<style type="text/css">
<!--
.title-blue {
	font-size: 13px;
	line-height: 20px;
	font-weight: bold;
	color: #0B82BB;
}
.font-gray {
	font-size: 12px;
	line-height: 20px;
	color: #000000;
}
.bord-line {
	font-family: "宋体";
	font-size: 12px;
	line-height: 20px;
	color: #000000;
	text-decoration: none;
	border: 1px solid #6699CC;
}
-->
</style>
<script src='${ctx}/common/attachment/xml.js'></script>
<script>
<%
//java.lang.String userOnly = request.getParameter("userOnly");
java.lang.String userOnly = "true";
userOnly = userOnly != null ? userOnly : "";
java.lang.String deptid = request.getParameter("deptid");
deptid = deptid != null ? deptid : "";
%>
var userOnly='<%=userOnly%>';
var isOne ='${isOne}';
function doReturn()
{
var s=formItem._selectedItem;
var t= "";
for(i=0; i<s.options.length;i++)
{
  t+=s.options[i].value + ";" + s.options[i].text + ";";
}
window.returnValue=t;
window.close();
}
function checkExist(id)
{
var isExist=false;
for(j=0;j<formItem._selectedItem.options.length;j++)
{
  if(id==formItem._selectedItem.options[j].value)
  {
    return true;
  }

}
return isExist;
}

function addUser()
{

  var s= formItem._selectedUser;
  for(i=0; i<s.options.length; i++)
  {
     if(s.options[i].selected)
     {
         var t=s.options[i].value.split(";");
         if(!checkExist(t[0]))
         {
         if(isOne == 'true')
         {
          if(formItem._selectedItem.options.length < 1)
          {
           formItem._selectedItem.add(new Option(t[1],t[0]));
           }
           else
           {
           alert("只能添加单一用户！");
           break;
           }
          }
          else
          {
          formItem._selectedItem.add(new Option(t[1],t[0]));
          }
         }
     }
  
  }
}

function addGroup()
{
  var s= formItem._selectedGroup;

  for(i=0; i<s.options.length; i++)
  {
     if(s.options[i].selected)
     {

         var t=s.options[i].value.split(";");
         if(!checkExist(t[0]))
         {
           
           formItem._selectedItem.add(new Option(t[1],t[0]));
         }
     }
  
  }

}

function selectUser(key)
{
   if(key=='')
   {
     alert("请输入查询条件...");
     formItem.key.focus();
     return;
   }
   if(key.indexOf("'")!=-1){
	     alert("输入了特殊字符 ” ' “,请重新输入查询条件...");
	     formItem.key.value="";
	     formItem.key.focus();
	     return;
   }
  

	var url="${ctx}/base/user!searchUser.action?keys="+encodeURI(key);
	
	LoadData(myXML,url);
	var inn = "";
	if(myXML.recordset!=null && myXML.recordset.recordcount > 0)
	{
		//inn = "<table width='100%'  border=0>"
		inn = "<select name=_selectedUser  size='";
		
		if(userOnly == '')
		{
		   inn +="10' style='width:100%' multiple ondblclick='addUser();'>";
		}
		else
		{
			inn += "25' style='width:100%' multiple ondblclick='addUser();'>";
		}
		while(!myXML.recordset.eof)
		  {
			try{
				inn +="<option value='" +  myXML.recordset('ID') + ";" + myXML.recordset('NAME')  + "'>";
				inn += myXML.recordset('USERNUMBER') + "   " + myXML.recordset('NAME') + "   " + myXML.recordset('DEPT');
				inn +="</option>";
			
			}
			catch(e)
			{}
			myXML.recordset.MoveNext()
		}

		inn +="</select>";
	}
	divUser.innerHTML= inn;
}

function selectGroup(groupname)
{
 /*  if(groupname=='')
   {
     alert('请输入查询条件...');
     formItem.groupname.focus();
     return;
   }
*/
	var url="/framework/user/internal/grouplistxml.jsp?deptid=<%=deptid%>&groupname=" + groupname;
	LoadData1(myXML,url);
	
	var inn = "";
	if(myXML.recordset!=null && myXML.recordset.recordcount > 0)
	{
		//inn = "<table width='100%'  border=0>"
	    inn +="<select name=_selectedGroup  size='10' style='width:100%' multiple ondblclick='addGroup();'>";
		while(!myXML.recordset.eof)
		  {
			//inn +="<tr>";

			try{
			 
				//inn +="<td bgcolor='#FFFFFF' align=left>";
				
				//inn +="<input type=checkbox name=_selectedGroup value='" + myXML.recordset('ID') + ";" + myXML.recordset('NAME') + "'>";
				
				inn +="<option value='" + myXML.recordset('ID') + ";" + myXML.recordset('NAME') +"'>";				
				inn += myXML.recordset('NAME');
				inn +="</option>";
				
				//inn += myXML.recordset('NAME');
				//inn +="</td>";
			
			}
			catch(e)
			{}
			//inn +="</tr>";
			myXML.recordset.MoveNext()
		}
		//inn +="</table>";
		inn +="</select>";
	}
	divGroup.innerHTML= inn;
	
}


</script>
<base target='_self'>
<body style="margin:0px;padding:0px;width:100%;height:100%;overflow:hidden;">
<form name="formItem">
<xml id="myXML"></xml>
<table width="100%" border="0" style="height:100%;"  cellpadding="4" cellspacing="2" bgcolor="#C4E1FF">
<tr >
<td   bgcolor="#FFFFFF" width='62%'  valign="top" height=100%>

<table width=100% height=100% border=0 cellpadding="2" cellspacing="2" bgcolor="#C4E1FF">
<tr  ><td bgcolor="#FFFFFF" valign=top >

	<table border="0"  width="100%" height="<%
	if(request.getParameter("userOnly") != null)
	{
	  out.print(" 100% ");
	}
	else
	{
	  out.print(" 100% ");
	}
	%>" cellspacing="2" cellpadding="2">
	<tr >
	<td   bgcolor="#FFFFFF" height=8 class="font-gray"  align="right">用户查询：
	<input type="text" style="height:18px;vertical-align:middle"  class="bord-line"  size="12" name="key" onKeydown='if(event.keyCode==13){selectUser(formItem.key.value);formItem._selectedUser.focus();}'>&nbsp;
	
	<!--
	<input type="button" name="selUser" onClick="selectUser(formItem.key.value);"  value="查询" class="bt-search" >&nbsp;
	-->
	<img style="CURSOR:HAND;vertical-align:middle" border=0 src='${ctx}/images/cms/btn_search.gif' onClick="selectUser(formItem.key.value);" >
	
	<!--
	<input type="button" onClick="addUser();"  value="添加" class="bt-add" >
	-->
	<img style="CURSOR:HAND;vertical-align:middle" border=0 src="${ctx}/images/cms/btn_add.gif" onClick="addUser();">
	</td>
	</tr>
	
	<tr>
	<td valign=top  bgcolor="#FFFFFF" style="height:100%;">
	
	   <div id='divUser' style="height:100%;"></div>
    
	</td>
	</tr>
	</table>
</td></tr>
<tr <%
	if(userOnly != null)
	{
	  out.print(" style='display:none' ");
	}
	%>><td bgcolor="#FFFFFF" >
	<table border="0" width="100%" height="100%" cellspacing="2" cellpadding="2" >
	<tr >
	<td  bgcolor="#FFFFFF" height=8 class="font-gray"  align="right">角色查询：
	<input type="text" style="height:18px;vertical-align:middle"  class="bord-line"  size="12" name="groupname"  onKeydown='if(event.keyCode==13){selectGroup(formItem.groupname.value);formItem._selectedGroup.focus();}'>&nbsp;
	
	<!--
	<input type="button" name="selGroup" onClick="selectGroup(formItem.groupname.value);"  value="查询" class="bt-search" >&nbsp;
	-->
	<img style="CURSOR:HAND;vertical-align:middle" border=0 src='${ctx}/images/cms/btn_search.gif' onClick="selectGroup(formItem.groupname.value);" >
	
	<!--
	<input type="button" onClick="addGroup();"  value="添加" class="bt-add" >
	-->
	<img style="CURSOR:HAND;vertical-align:middle" border=0 src="${ctx}/images/cms/btn_add.gif" onClick="addGroup();">
	</td>
	</tr>
	<tr style='display:none'>
	<td valign=top  bgcolor="#FFFFFF" style="height:100%;">
   
         <div id='divGroup' style="height:100%;">&nbsp;</div>
   
 
	</td>
	</tr>
	</table>
	</td></tr>
	</table>
	
</td>



<td valign="top"  bgcolor="#FFFFFF" style="height:100%;">
<table width=100% cellspacing="0" cellpadding="0" style="height:100%;">
<tr >
<td  bgcolor="#FFFFFF" class="font-gray"  align=right>

<!--
<input type="button" onClick="doReturn();" value="确定" class="bt-save">
-->
<img style="CURSOR:HAND;" src="${ctx}/images/cms/btn_confirm.gif" border=0 onClick="doReturn();">
<!--
<input type="button" onClick="try{_selectedItem.remove(formItem._selectedItem.selectedIndex);}catch(e){}"  value="删除" class="bt-del" >&nbsp;
-->

<img style="CURSOR:HAND;" src="${ctx}/images/cms/btn_del.gif" border=0 onClick="try{_selectedItem.remove(formItem._selectedItem.selectedIndex);}catch(e){}">

</td>
</tr>
  <tr>
   <td style="height:100%;">   
    <select name="_selectedItem" style="width:100%;height:100%;" size=26 ondblClick='try{_selectedItem.remove(formItem._selectedItem.selectedIndex);}catch(e){}'></select>
   </td>
  </tr>
</table>
</td>
</tr>
</table>
</form>


<script>
if(userOnly=='')
{
divUser.innerHTML="<select name=_selectedUser  size='10' style='width:100%;height:100%;' multiple></select>";
}
else
{
divUser.innerHTML="<select name=_selectedUser  size='25' style='width:100%;height:100%;' multiple></select>";
}
divGroup.innerHTML="<select name=_selectedGroup  size='10' style='width:100%;height:100%;' multiple></select>";
var id='${selected}';
var text='${selectedname}';

var s = id.split(";");
var t = text.split(";");
for(i=0;i<s.length-1;i++)
{
 //if(s[i].substring(0,1)=='G' || s[i].substring(0,1)=='U')
 //{
 formItem._selectedItem.add(new Option(t[i],s[i]));
 // }  
}

var deptid="<%=deptid%>";
if(deptid != ""){
  selectGroup("");
}
</script>
</body>
</html>
