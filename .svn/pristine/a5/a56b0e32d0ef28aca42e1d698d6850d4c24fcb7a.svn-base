<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/buffalo.inc"%>
<%@ include file="/common/js.inc"%>
<c:set var="title" value="流程处理" />
<link href="${default_style}" rel="stylesheet" type="text/css">	
<link href="${ctx}/styles/flow.css" type=text/css rel=stylesheet>
</head>
<script>
        //all nodeids
         var NODEIDS=new Array;
function getNodeSelectUserIds(nodeid){
        var selectStr = "";
 	var allUserids = document.all.tags('input');
	for (i=0; i<allUserids.length; i++){
		if (allUserids(i).type=="checkbox" && allUserids(i).checked 
		    && allUserids(i).value!=null&&allUserids(i).value!=""
		     && allUserids(i).id!=null && allUserids(i).id==nodeid){
                  selectStr+=allUserids[i].value+",";
		}
	}
	return  selectStr.substring(0,selectStr.length-1);
      //alert(selectStr.substring(0,selectStr.length-1));
}
function getAllNodeUsers(){
//alert(NODEIDS.length);
var nodeusers="";
 for(i=0;i<NODEIDS.length;i++){ 
   nodeusers+=NODEIDS[i]+":"+getNodeSelectUserIds(NODEIDS[i])+";";
 }
 return  nodeusers; 
}	  
function getSel(){
	var astr = tree.getSelectIds();
	alert(astr);
}
//root node click url
var rootUrl="javascript:return fasle;";
//node click url
var itemUrl="javascript:return fasle;";

function clickRole(roleobjstr,treeobj){
                var roleobj=null;                
  		var sels = document.getElementsByName(roleobjstr.id);
		size = sels.length;	
		//alert(roleobjstr.checked);	
		for(var i=0;i<size;i++){		
		    sels[i].checked=roleobjstr.checked;
			//if(sels[i].checked){
				//str+=sels[i].value+",";
			//}			
		}
		//treeobj.reload;
}
function checkUserOne(currobj){
   var chkID=document.getElementsByName(currobj.name);    
   var chkall=document.getElementById(currobj.name);
  //alert(chkall.checked);
   allchecked=true;
   	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i)!=chkall){			
			allchecked=false;
		}
	}
	if (allchecked==false) {
		chkall.checked=false;
	}else{
		chkall.checked=true;
	}
}
function selectUsers(){
 window.returnValue=getAllNodeUsers();
  //alert(window.returnValue);
  //ClosePage();
   window.close();
  
}
function ClosePage(){
    window.returnValue=null;
    window.close();
}function onchangeExamineContent(){
 document.getElementById('examineContent').value=document.getElementById('select_examineContent').value;
}
</script>
<%@ include file="/common/cxtree.inc"%>
<base target="_self">
<body topmargin="0" leftmargin="0" topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<ww:form id="myform" name="myform" namespace="/flow" action="" method="POST">
<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<!--style="background-color:#dfedef;"  --> 
	<tr><td  valign="top">
	<table  width="100%" align="left"   border="0" cellspacing="0" cellpadding="0"  bgcolor="#ffffff">
	
		<tr>
		<td  valign="top" bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
	</table>
	<tr>
		<td valign="top" align="center" height="100%" >

<table width="98%" border="0" align="center" bgcolor="#FFFFFF" valign="top" style="margin:0px;padding:0px;">
  <tr>
    <td valign="top" ><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="29">
        <ww:hidden name="id" id="id" />
        <ww:hidden name="activityid" id="activityid" />
        <ww:hidden name="method"/> 
          <table width="280" border="0" cellspacing="0" cellpadding="0" style="float:left;">
            <tr>
              <td align="center" valign="bottom" id="at1" style="background-image:url(${ctx}/images/common/button<c:if test="${method ==null or method=='comple'}">1</c:if>.jpg); height:25px; width:70px;" onclick="changeTransactPage('')"><a class="link1" style="cursor:hand">完成</a></td>
              <td  align="center" valign="bottom" id="at2" style="background-image:url(${ctx}/images/common/button<c:if test="${method=='transfer'}">1</c:if>.jpg);height:25px; width:70px;" onclick="changeTransactPage('transfer')"><a class="link1" style="cursor:hand">转移</a></td>
              <td  align="center" valign="bottom" id="at3" style="background-image:url(${ctx}/images/common/button<c:if test="${method=='throwback'}">1</c:if>.jpg);height:25px; width:70px;" onclick="changeTransactPage('throwback')"><a class="link1" style="cursor:hand">回退</a></td>
              <td align="center" valign="bottom" id="at4" style="background-image:url(${ctx}/images/common/button<c:if test="${method=='terminate'}">1</c:if>.jpg);height:25px; width:70px;" onclick="changeTransactPage('terminate')"><a class="link1" style="cursor:hand">终止</a></td>
             
            </tr>
          </table>
       </td>
      </tr>
      <tr>
        <td align="center" valign="top">
          <table width="94%" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td><table width="100%" border="0" cellpadding="0" cellspacing="0" id="a1">
                <tr>
                  <td height="25">&nbsp;</td>
                </tr>
                <tr>
                  <td align="left" class="zi"><ww:if test="(flows!=null&&flows.size>0)">回退活动人员</ww:if><ww:if test="(flows==null||flows.size==0)">&nbsp;&nbsp;您不能回退！</ww:if></td>
                </tr>
                <tr>
                  <td height="12" align="left"></td>
                </tr>
                <ww:if test="(flows!=null&&flows.size>0)">
                <tr>
                  <td align="left"><table width="70%" border="0" cellpadding="1" cellspacing="1" class="td2">
                      <tr>
                        <td align="left" class="td3">
	  <script type="text/javascript">

	  //create tree
          <ww:iterator value="flows" status="index">  
              var rtree<ww:property value="flows[#index.index].nodeId"/>=new WebFXTree('<ww:property value="flows[#index.index].activityName"/>');	  
           <ww:set name="tnodeid" value="flows[#index.index].nodeId"/>
           <ww:set name="nodeUsers" value="users[#tnodeid]"/>
           NODEIDS[<ww:property value="#index.index"/>]='<ww:property value="#tnodeid"/>';
           <ww:iterator value="#nodeUsers" status="userindex">            
		   <ww:if test="(isGroupUser==true)">
		    var role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>=new WebFXTreeItem('<ww:property value="usercname"/>');
 		    role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.checkbox='true';
 		    role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.value='';
 		    role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.selectId='role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>';  
 		    //alert(role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.selectId);
 		    role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.selectClick="clickRole(this,rtree<ww:property value='flows[#index.index].nodeId'/>);";
 		    <ww:if test="(includeUsers!=null)">
 	             <ww:set name="roleUsers" value="#nodeUsers[#userindex.index].includeUsers"/> 	             
 		     <ww:iterator value="#roleUsers" status="ruserindex">
 		    
 		      var role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>=new WebFXTreeItem('<ww:property value="username"/>');
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>.checkbox='true';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>.value='<ww:property value="userid"/>';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>.selectName='role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="#nodeUsers[#userindex.index].username"/>'
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>.selectId='<ww:property value="#tnodeid"/>';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>.selectClick='checkUserOne(this)';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="#nodeUsers[#userindex.index].username"/>.add(role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userid"/>);
 		     </ww:iterator>
 		    </ww:if>
 		    rtree<ww:property value="flows[#index.index].nodeId"/>.add(role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>);
		    </ww:if>
 		    <ww:else>	
 		    var user_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>=new WebFXTreeItem('<ww:property value="usercname"/>');
 		    user_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.checkbox='true';
 		    user_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.value='<ww:property value="username"/>';
 		    user_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.selectId='<ww:property value="#tnodeid"/>';
 		    //user_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>.selectClick="alert(this.value)";
 		    rtree<ww:property value="flows[#index.index].nodeId"/>.add(user_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="username"/>);
 		    </ww:else>   
           </ww:iterator>
          document.write(rtree<ww:property value="flows[#index.index].nodeId"/>);
          document.write("<br>");
</ww:iterator>  
	  </script>
                        </td>
                      </tr>
                  </table></td>
                </tr>
                </ww:if>
                <tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td height="30" align="left">备注:
     
      <input type="hidden" name="permObj.nodeSelectUserIds" id="NodeSelectUserIds" value="">
                  </td>
                </tr>
                <tr>
                  <td align="left"><table width="70%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><textarea name="permObj.params.examineContent" rows="5"  id="examineContent"></textarea></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                </tr>
              </table></td>
            </tr>
<%@ include file="button.inc"%> 
          </table>
          </td>
      </tr>
      
    </table></td>
  </tr>
<%@ include file="foot.inc"%> 
</td></tr>
</table>   
</ww:form>   
       
</body>
</html>
<script language="javascript">
 <!--
 //提交页面
  function doConfirm(){
   document.getElementById('NodeSelectUserIds').value=getAllNodeUsers();
   var url=replaceAll(window.location.href,'!transact','!throwback');
   myform.action=url;
   myform.submit(); 
}
-->
</script>
