<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<%@ include file="/common/js.inc"%>

<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
}
</script>
<script language="javascript">
function onchangeExamineContent(){
 document.getElementById('examineContent').value=document.getElementById('select_examineContent').value;
}
</script>
<%@ include file="/common/cxtree.inc"%>
<base target="_self">
<body topmargin="0" leftmargin="0" topmargin="0" leftmargin="0">
<ww:form id="myform" name="myform" namespace="/flow" action="" method="POST">
<div align="center">
<%@ include file="head.inc"%>
<table width="100%" id="table1"   border="0" cellpadding="0" cellspacing="0">
	<tr class="title_bg">
	<td align="center" height="40" colspan=3>
	  <b>活动人员选择</b>
	</td>
	</tr>
	<tr class="title_bg">
	<td width="10%">&nbsp;</td>
	<td align="left" height="21">
	 <b>转移活动人员:</b>
	</td>
	</tr>	
	<tr>
	<td width="10%">&nbsp;</td>
	<td width="70%" >
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
 		    
 		      var role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="username"/>_useritree<ww:property value="userID"/>=new WebFXTreeItem('<ww:property value="CName"/>');
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userID"/>.checkbox='true';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userID"/>.value='<ww:property value="userID"/>';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userID"/>.selectName='role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="#nodeUsers[#userindex.index].username"/>'
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userID"/>.selectId='<ww:property value="#tnodeid"/>';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userID"/>.selectClick='checkUserOne(this)';
 		      role_<ww:property value="flows[#index.index].nodeId"/>_itree<ww:property value="#nodeUsers[#userindex.index].username"/>.add(role_<ww:property value="flows[#index.index].nodeId"/>_<ww:property value="#nodeUsers[#userindex.index].username"/>_useritree<ww:property value="userID"/>);
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
</ww:iterator>  
	  </script>
	</td>
	<td width="20%">&nbsp;</td>
	</tr>
 <tr> 
    <td>&nbsp;</td>
    <td>处理意见:
      <select name="select_examineContent" onchange="onchangeExamineContent();">
      <option value=""></option>
      <option value="同意">同意</option>
      <option value="不同意">不同意</option>
      </select></td>
  </tr>
  <tr>
    <td height="122">&nbsp;</td>
    <td><textarea name="textarea" name="permObj.params.examineContent" id='examineContent' rows="6" cols="60"></textarea></td>
  </tr>  	
	<ww:if test="(flows==null||flows.size==0)">
  <tr>
    <td width="10%">&nbsp;</td>
    <td colspan="2" height="40">&nbsp;&nbsp;您是否要结果整个流程？	  
	  </td>
  </tr>	
  </ww:if>
  <!--tr>
    <td width="10%">&nbsp;</td>
    <td colspan="2" height="40" align="center" >&nbsp;&nbsp;
	  <div align="center">
	  <img onmouseover="this.style.cursor='hand';" onmouseout="this.style.cursor='default';" onclick="doConfirm()" src="${ctx}/images/common/ok.gif">
	  <img onmouseover="this.style.cursor='hand';" onmouseout="this.style.cursor='default';" onclick="myform.reset()" src="${ctx}/images/common/reset.gif" >
	  <img onmouseover="this.style.cursor='hand';" onmouseout="this.style.cursor='default';" onclick="ClosePage()" src="${ctx}/images/common/cancel.gif">
	  </div>
	  </td>
  </tr-->		
</table>

<!--button onclick="alert(getAllNodeUsers());">查看选择节点值
</button-->
<%@ include file="foot.inc"%>
</div>
</ww:form>
</body>
</html>
<script language="javascript">
 var url=replaceAll(window.location.href,'transact','comple');
 myform.action=url;
 
 //提交页面
function doConfirm(){
 var tid=document.getElementById('id').value;
 var tactivityid=document.getElementById('activityid').value;
 var texamineContent=document.getElementById('examineContent').value;
 var nusers=getAllNodeUsers();
 buffalo.remoteCall("flowBasicCall.activityComplete",[tid,tactivityid,texamineContent],function(reply){
 var result= reply.getResult(); 
  //alert(result);
 if(result==true){
   window.returnValue = true;
   window.close();
 }else
  alert('操作未成功！');
 });
}
</script>